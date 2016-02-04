package com.xc.lib.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.xc.lib.iface.Callback;

/**
 * @ClassName: AppMessager
 * @Description: TODO 应用消息收发处理
 * @author Brook xu
 * @date 2014-04-16 下午2:54:57
 * @version 2.0
 * 
 *          1 因广播数据在本应用范围内传播，你不用担心隐私数据泄露的问题。 2 不用担心别的应用伪造广播，造成安全隐患。 3
 *          相比在系统内发送全局广播，它更高效。
 */
public class AppMessager extends BroadcastReceiver {
	private LocalBroadcastManager mLocalBroadcastManager;
	public static final String ACTION_APP_DEFAULT_MSG = "action.app.broadcast.default.msg";
	private Map<String, ArrayList<Callback<Intent>>> mCallbackMap;

	private static AppMessager mInstance;

	public static AppMessager getmInstance(Context mContext) {
		if (mInstance == null) {
			mInstance = new AppMessager(mContext.getApplicationContext());
		}
		return mInstance;
	}

	private AppMessager(Context mContext) {
		super();
		mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);
		mCallbackMap = new HashMap<String, ArrayList<Callback<Intent>>>();
		// 添加默认的广播，do nothing
		// addRegister(ACTION_APP_DEFAULT_MSG, null);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		dealMsg(intent.getAction(), intent);
	}

	/**
	 * 消息处理
	 * 
	 * @param msgType
	 * @param param
	 * @return void
	 */
	private synchronized void dealMsg(String action, Intent intent) {
		// TODO Auto-generated method stub
		ArrayList<Callback<Intent>> callback = mCallbackMap.get(action);
		if (callback != null) {
			for (Callback<Intent> callbackIntent : callback) {
				callbackIntent.onSuccess(intent);
			}
		}
	}

	/**
	 * 发送本地注册广播消息
	 * 
	 * @param type
	 * @param param
	 * @return void
	 */
	public static synchronized void sendBroadcast(Context context, Intent intent) {
		getmInstance(context).mLocalBroadcastManager.sendBroadcast(intent);
	}

	/**
	 * 发送系统广播消息
	 * 
	 * @param type
	 * @param param
	 * @return void
	 */
	public static synchronized void sendSystemBroadcast(Context context, Intent intent) {
		context.sendBroadcast(intent);
	}

	/**
	 * 在使用类中注册消息处理监听
	 * 
	 * @param msgType
	 * @param msgCallBack
	 * @return void
	 */
	public synchronized void addRegister(String action, Callback<Intent> msgCallBack) {
		if (TextUtils.isEmpty(action)) {
			action = ACTION_APP_DEFAULT_MSG;
		}

		ArrayList<Callback<Intent>> callback = null;
		if (mCallbackMap.containsKey(action)) {
			callback = mCallbackMap.get(action);
			if (!callback.contains(msgCallBack))
				callback.add(msgCallBack);
		} else {
			callback = new ArrayList<Callback<Intent>>();
			callback.add(msgCallBack);
		}
		mCallbackMap.put(action, callback);

		if (filter != null) {
			mLocalBroadcastManager.unregisterReceiver(this);
			filter = null;
		}
		filter = new IntentFilter();
		Iterator<String> actions = mCallbackMap.keySet().iterator();
		while (actions.hasNext()) {
			filter.addAction(actions.next());
		}
		mLocalBroadcastManager.registerReceiver(this, filter);
	}

	private IntentFilter filter = null;

	/**
	 * to unregister this BroadcastReceiver
	 * 
	 * @throws Exception
	 * @return void
	 */
	public void unRegisterSelf() {
		try {
			mLocalBroadcastManager.unregisterReceiver(this);
			removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeAll() {
		Iterator<String> it = mCallbackMap.keySet().iterator();
		while (it.hasNext()) {
			removeActionAll(it.next());
		}

		mCallbackMap.clear();
	}

	public void removeActionAll(String action) {
		if (mCallbackMap.containsKey(action)) {
			mCallbackMap.get(action).clear();
		}
	}

	public void removeAction(String action, Callback<Intent> callback) {
		if (mCallbackMap.containsKey(action)) {
			mCallbackMap.get(action).remove(callback);
		}
	}

}
