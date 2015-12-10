package com.thomas.volley;
 

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.content.Context;


/**
 * 消息队列
  Copyright © 2015 aite. All rights reserved.
  
  @Title: RequestManager.java 
  @author: pc   @version: V1.0   @date: 2015-11-3 下午5:53:29 
  @Description: TODO
  @Prject: SmartLife
  @Package: com.atelen.smartlife.volly 
  @FunctionList: TODO
  @History: TODO
*
 */
public class RequestManager {

    /**
     * 请求队列，单例
     */
    private static RequestQueue mRequestQueue;


    private RequestManager() {
    }

    /**
     * @param context application 上下文
     */
    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * @return 请求队列
     * @throws IllegalStatException 若队列未初始化抛出异常
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }

    /**
     * 增加请求
     * @param request 请求
     */
    public static void addRequest(Request request) {
        if (mRequestQueue != null) {
            mRequestQueue.add(request);
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }
}

