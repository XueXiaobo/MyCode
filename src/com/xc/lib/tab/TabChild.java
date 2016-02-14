package com.xc.lib.tab;

import android.view.View;

/**
 *@author xxb
 *@version v1.0
 *创建时间：2016年2月14日 上午11:14:58
 */
public abstract class TabChild {
	public abstract View getView();
	
	public void onSelected(){}
	
	public void unSelected(){}
}
