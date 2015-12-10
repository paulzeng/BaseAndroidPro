package com.thomas.base;

import java.util.HashMap;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.thomas.utils.Constant;
import com.thomas.utils.DialogUtil;
import com.thomas.utils.JsonUtil;
import com.thomas.utils.LogUtil;
import com.thomas.volley.MyJsonObjectRequest;
import com.thomas.volley.RequestManager;

public abstract class BaseActivity extends FragmentActivity {
	// volley消息队列
	protected RequestQueue queue;
	// 请求进度提示的对话框
	private Dialog dialog;
	// 默认不显示加载进度框，错误提示，不能取消请求
	public static final int FLAG_DEFAULT = 0;
	// 是否显示加载进度框
	public static final int FLAG_SHOW_PROGRESS = 1;
	// 是否显示错误提示
	public static final int FLAG_SHOW_ERROR = 2;
	// 是否当关闭进度框时取消相应请求
	public static final int FLAG_CANCEL = 4;
	// 是否加密请求
	public static final int FLAG_ENCRYPT = 8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		initVolley(this);
		queue = RequestManager.getRequestQueue();
		// 将所有Activity加入任务栈中
		BaseApplication.allActivity.add(this);
		// 进度条
		dialog = DialogUtil.getLoadingDialog(this);
	}

	public void initVolley(Context context) {
		RequestManager.init(context);
	}

	/**
	 * 网络请求返回处理方法<json>
	 * 
	 * @param response
	 */
	public abstract void onRecvData(String type, JSONObject response);

	/**
	 * 初始化控件
	 */
	public abstract void findViews();

	/**
	 * 初始化数据
	 */
	public abstract void init();

	/**
	 * 设置控件监听事件
	 */
	public abstract void setListener();

	/**
	 * 请求JSON数据
	 * 
	 * @param url
	 *            请求接口路径
	 * @param params
	 *            请求参数
	 */
	protected <T> void request(String url, HashMap<String, String> params,
			String type, boolean isLoading) {
		request(url, params, FLAG_DEFAULT, type, isLoading);
	}

	/**
	 * 请求JSON数据
	 * 
	 * @param url
	 *            请求接口路径
	 * @param params
	 *            请求参数
	 * @param flags
	 *            FLAG_DEFAULT,FLAG_SHOW_PROGRESS,FLAG_SHOW_ERROR,FLAG_CANCEL
	 */
	public <T> void request(String url, HashMap<String, String> params,
			int flags, String type, boolean isLoading) {
		boolean isShowProgress = (flags & FLAG_SHOW_PROGRESS) != 0;
		boolean isShowError = (flags & FLAG_SHOW_ERROR) != 0;
		boolean cancelable = (flags & FLAG_CANCEL) != 0;
		boolean isEncrypt = (flags & FLAG_ENCRYPT) != 0;

		request(url, params, isShowProgress, isShowError, cancelable,
				isEncrypt, type, isLoading);
	}

	/**
	 * 请求JSON数据
	 * 
	 * @param url
	 *            请求接口路径
	 * @param params
	 *            请求参数
	 * @param isShowProgress
	 *            是否显示加载进度框
	 * @param isShowError
	 *            是否显示错误提示
	 * @param cancelable
	 *            是否当关闭进度框时取消相应请求
	 * @param isEncrypt
	 *            是否加密请求
	 */
	private <T> void request(String url, final HashMap<String, String> params,
			boolean isShowProgress, final boolean isShowError,
			boolean cancelable, final boolean isEncrypt, final String type,
			boolean isLoading) {
		if (isLoading) {
			dialog.show();
		}
		LogUtil.e("####", "#####没转之前###" + params.toString());
		final String sParams = JsonUtil.mapToJson(params).toString();
		LogUtil.e("####", "#####Map转成JSON###" + sParams);
		final String finalUrl = Constant.API_URL_PREFIX + url;
		LogUtil.i("volley-request", finalUrl + "[" + sParams + "]");
		// FakeX509TrustManager.allowAllSSL();
		String jsonparams = sParams;
		MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(
				finalUrl, jsonparams, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						onRecvData(type, response);
						if (dialog.isShowing()) {
							dialog.dismiss();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
					}
				});
		RequestManager.addRequest(jsonObjectRequest);
	}

	/**
	 * GET请求
	 * 
	 * @Title: getRequest
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:@param url 基地址
	 * @Input:@param token 用户的token
	 * @Input:@param type 请求的类型
	 * @Date: 下午2:07:58
	 * 
	 */
	public <T> void getRequest(final String url, final String token,
			final String type) {
		MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(url,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						onRecvData(type, response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (error instanceof TimeoutError) {
							// note : may cause recursive invoke if always
							// timeout.
							getRequest(url, token, type);
						} else {
							getRequest(url, token, type);
						}
					}
				}, token);
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 60 * 1000,
				5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		RequestManager.addRequest(jsonObjectRequest);
	}
}
