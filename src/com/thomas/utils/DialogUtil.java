package com.thomas.utils;

import com.thomas.base.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DialogUtil {
	public static Dialog getLoadingDialog(Context context) {
		return getLoadingDialog(context, "正在加载");
	}

	public static Dialog getLoadingDialog(Context context, Object loadingTextRes) {
		final Dialog dialog = new Dialog(context, R.style.netLoadingDialog);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.custom_progress_dialog);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = getScreenWidth(context) - dpToPx(context, 100);
		window.setGravity(Gravity.CENTER_VERTICAL);
		TextView titleTxtv = (TextView) dialog.findViewById(R.id.dialog_tv);
		if (loadingTextRes instanceof String) {
			titleTxtv.setText((String) loadingTextRes);
		}
		if (loadingTextRes instanceof Integer) {
			titleTxtv.setText((Integer) loadingTextRes);
		}

		return dialog;
	}

	public static void toggleDialog(Dialog loadDialog) {
		if (loadDialog != null && loadDialog.isShowing()) {
			loadDialog.dismiss();
		}

	}

	public static int dpToPx(Context context, int dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return Math.round(dpValue * scale);
	}

	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 弹出提示窗体
	 * 
	 * @Title: getCommWarnDialog
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:@param context
	 * @Input:@param l
	 * @Input:@return
	 * @Date: 上午10:04:21
	 * 
	 */
	public static Dialog getCommWarnDialog(Context context,
			final OnClickListener l) {
		final Dialog dialog = new Dialog(context, R.style.float_base);
		View view = View.inflate(context, R.layout.custom_warn_dialog, null);
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(true);
		view.findViewById(R.id.warn_sure_bt).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						l.onClick(v);
						dialog.dismiss();
					}
				});
		view.findViewById(R.id.warn_cancle_bt).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = getScreenWidth(context) - dpToPx(context, 50);
		window.setGravity(Gravity.CENTER_VERTICAL);
		return dialog;
	}

	/**
	 * 弹出提示窗体
	 * 
	 * @Title: getCommWarnDialog
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:@param context
	 * @Input:@param l
	 * @Input:@return
	 * @Date: 上午10:04:21
	 * 
	 */
	public static Dialog getCommWarnDialog(Context context, String warningInfo,
			final OnClickListener l, boolean flag) {
		final Dialog dialog = new Dialog(context, R.style.float_base);
		View view = View.inflate(context, R.layout.dialog_warning_layout, null);
		TextView tv = (TextView) view.findViewById(R.id.warn_content_tv);
		tv.setText(warningInfo);
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(true);
		view.findViewById(R.id.warn_sure_bt).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						l.onClick(v);
						dialog.dismiss();
					}
				});

		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = getScreenWidth(context) - dpToPx(context, 50);
		window.setGravity(Gravity.CENTER_VERTICAL);
		return dialog;
	}

	/**
	 * 弹出发现新版本提示窗体
	 * 
	 * @Title: getCommWarnDialog
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:@param context
	 * @Input:@param contentRes
	 * @Input:@param l
	 * @Input:@return
	 * @Date: 上午10:52:23
	 * 
	 */
	public static Dialog getCommWarnDialog(Context context, String contentRes,
			final OnClickListener l) {
		final Dialog dialog = new Dialog(context, R.style.float_base);
		View view = View.inflate(context, R.layout.layout_common_dialog, null);
		TextView title = (TextView) view.findViewById(R.id.warn_title_tv);
		TextView content = (TextView) view.findViewById(R.id.warn_content_tv);
		TextView sure = (TextView) view.findViewById(R.id.warn_sure_bt);
		TextView cancle = (TextView) view.findViewById(R.id.warn_cancle_bt);
		title.setText("发现新的版本");
		content.setText(contentRes);
		sure.setText("立即下载");
		cancle.setText("以后再说");
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(true);
		view.findViewById(R.id.warn_sure_bt).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						l.onClick(v);
						dialog.dismiss();
					}
				});
		view.findViewById(R.id.warn_cancle_bt).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = getScreenWidth(context) - dpToPx(context, 50);
		window.setGravity(Gravity.CENTER_VERTICAL);
		return dialog;
	}

}
