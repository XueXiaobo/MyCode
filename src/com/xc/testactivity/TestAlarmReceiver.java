package com.xc.testactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TestAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("闹钟响起了-----");
	}

}
