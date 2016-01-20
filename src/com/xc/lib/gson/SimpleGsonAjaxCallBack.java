package com.xc.lib.gson;

import com.google.gson.Gson;
import com.xc.lib.xutils.exception.HttpException;
import com.xc.lib.xutils.http.ResponseInfo;
import com.xc.lib.xutils.http.callback.RequestCallBack;

public class SimpleGsonAjaxCallBack<T> extends RequestCallBack<String> {
	private T t;
	private Class<T> type;
	private SimpleGsoninface<T> simple;

	public SimpleGsonAjaxCallBack(Class<T> type) {
		this.type = type;
	}

	public SimpleGsonAjaxCallBack(Class<T> type, SimpleGsoninface<T> simple) {
		this.type = type;
		this.simple = simple;
	}

	private Gson gson = null;

	public T chuli(String result, Class<T> type) {
		if (gson == null)
			gson = new Gson();
		return gson.fromJson(result, type);
	}

	@Override
	public void onStart() {
		if (simple != null)
			simple.onStart();
	}

	public void onResult(T t) {
		if (simple != null)
			simple.onResult(t);
	}

	@Override
	public void onLoading(long total, long current, boolean isUploading) {
		if (simple != null)
			simple.onLoading(total, current, isUploading);
	}

	@Override
	public void onSuccess(ResponseInfo<String> responseInfo) {
		onFinish();
		onResult(t);
	}

	@Override
	public void onSuccessPre(ResponseInfo<String> responseInfo) {
		if (type != null)
			t = chuli(responseInfo.result, type);
	}

	@Override
	public void onFailure(HttpException error, String msg) {
		onFinish();
		if (simple != null)
			simple.onFailure(error, msg);
	}

	@Override
	public void onFinish() {

	}
}
