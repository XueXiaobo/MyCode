package com.xc.lib.gson;

import com.xc.lib.xutils.exception.HttpException;

public interface SimpleGsoninface<T>{
	public void onResult(T t);
	public void onLoading(long total, long current, boolean isUploading);
	public void onFailure(HttpException errorNo, String strMsg);
	public void onStart();
}
