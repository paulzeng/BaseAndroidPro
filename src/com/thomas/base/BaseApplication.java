package com.thomas.base;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.thomas.utils.CustomCrashHandler;

/**
 * 全局设置 Copyright © 2015 aite. All rights reserved.
 * 
 * @Title: BaseApplication.java
 * @author: pc @version: V1.0 @date: 2015-12-10 上午10:36:31
 * @Description: TODO
 * @Prject: BaseAndroidPro
 * @Package: com.thomas.base
 * @FunctionList: TODO
 * @History: TODO
 * 
 */
public class BaseApplication extends Application {
	public static BaseApplication mInstance;
	public static List<Activity> allActivity = new LinkedList<Activity>();
	public static Typeface lightTypeface, thinypeface, wendyTypeface,
			xiyuanTypeface;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		initImageLoader(getApplicationContext());
		initTypeface(this.getAssets());
		initCrashHandler();
	}

	/**
	 * 初始化异步图片加载
	 * 
	 * @Title: initImageLoader
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:@param context
	 * @Date: 下午1:48:26
	 * 
	 */
	public void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "Imageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3).denyCacheImageMultipleSizesInMemory()
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs().diskCacheFileCount(100)
				.diskCache(new UnlimitedDiscCache(cacheDir)).build();
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 初始化全局异常处理类
	 * 
	 * @Title: initCrashHandler
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:
	 * @Date: 上午11:26:08
	 * 
	 */
	public void initCrashHandler() {
		CustomCrashHandler mCustomCrashHandler = CustomCrashHandler
				.getInstance();
		mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
		// 使用bugly监听错误日志
		CrashReport.initCrashReport(this, "900008905", false);
	}

	/**
	 * 销毁所有activity，应用退出
	 * 
	 * @Title: exit
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:
	 * @Date: 下午1:48:55
	 * 
	 */
	public void exit() {
		for (Activity activity : allActivity) {
			activity.finish();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 将信息储存到sp文件
	 * 
	 * @Title: saveUserInfo
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:@param context
	 * @Input:@param user
	 * @Date: 下午1:49:40
	 * 
	 */
	public void saveInfoToSp(String key, Object obj) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		sp.edit().putString(key, new Gson().toJson(obj)).commit();
	}

	/**
	 * 获取sp保存的信息
	 * 
	 * @Title: getUserInfo
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:@param context
	 * @Input:@return
	 * @Date: 下午1:50:15
	 * 
	 */
	public Object getSpInfo(String key) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		String userInfo = sp.getString(key, null);
		if (userInfo != null) {
			return new Gson().fromJson(userInfo, Object.class);
		}
		return null;
	}

	/**
	 * 清除sp保存的信息
	 * 
	 * @Title: clearUserInfo
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:@param context
	 * @Date: 下午1:50:30
	 * 
	 */
	public void clearSpInfo(String key) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		sp.edit().putString(key, null).commit();
	}

	/**
	 * 初始化字体库
	 * 
	 * @param context
	 */
	public static void initTypeface(AssetManager asset) {
		lightTypeface = Typeface
				.createFromAsset(asset, "font/Roboto-Light.ttf");
		thinypeface = Typeface.createFromAsset(asset, "font/Roboto-Thin.ttf");
		wendyTypeface = Typeface.createFromAsset(asset, "font/Wendy.ttf");
		xiyuanTypeface = Typeface.createFromAsset(asset, "font/xiyuan.ttf");
	}

}
