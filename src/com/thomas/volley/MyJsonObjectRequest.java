package com.thomas.volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

public class MyJsonObjectRequest extends JsonRequest<JSONObject> {

	String stringRequest;

	/**
	 * 这里的method必须是Method.POST，也就是必须带参数。
	 * 如果不想带参数，可以用JsonObjectRequest，给它构造参数传null。GET方式请求。
	 * 
	 * @param stringRequest
	 *            格式应该是 "key1=value1&key2=value2"
	 */

	public MyJsonObjectRequest(String url, String stringRequest,
			Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener) {
		super(Method.POST, url, stringRequest, listener, errorListener);
		this.stringRequest = stringRequest;
	}

	/**
	 * 这里的method必须是Method.GET GET方式请求。
	 * token取自用户 
	 */

	public MyJsonObjectRequest(String url,
			Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener, String token) {
		super(Method.GET, url + "getmsg?token=" + token, null, listener,
				errorListener);
	}

	@Override
	public String getBodyContentType() {
		return "application/x-www-form-urlencoded; charset="
				+ getParamsEncoding();
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			response.headers.put(HTTP.CONTENT_TYPE,
					response.headers.get("Content-Type"));
			String jsonString = new String(response.data,
					HTTP.UTF_8);
			return Response.success(new JSONObject(jsonString),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json; charset=UTF-8");
		return headers;
	}
}