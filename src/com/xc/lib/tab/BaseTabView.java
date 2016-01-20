package com.xc.lib.tab;

import android.view.View;

public abstract class BaseTabView implements BaseTabInterface {

	@Override
	public void onCreate() {
	}

	@Override
	public void onResume() {
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onStop() {
	}

	@Override
	public void onDestroy() {
	}
	public abstract View getView();

}
