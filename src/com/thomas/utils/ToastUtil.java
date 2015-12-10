package com.thomas.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.thomas.base.R;

public class ToastUtil {

	public static void show(Activity context, String text) {
		show(context, text, 0);
	}

	public static void show(Activity context, String text, int duration) {
		LayoutInflater inflater = context.getLayoutInflater();
		View view = inflater.inflate(R.layout.toast_info, null);
		TextView txt = (TextView) view.findViewById(R.id.txt_tips);
		txt.setText(text);
		StringUtils.setTextTypeface(FontType.XIYUAN, context, txt);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 180);
		toast.setDuration(duration);
		toast.setView(view);
		toast.show();
	}
}
