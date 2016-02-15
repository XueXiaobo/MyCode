package com.xc.lib.tab;

import android.view.View;

/**
 *@author xxb
 *@version v1.0
 *创建时间：2016年2月14日 上午11:14:58
 */
public abstract class TabChild {
	/**
	 * 保证每次返回都是同一个view
	 */
	public abstract View getView();
	/**
	 * 选中状态
	 */
	public void onSelected(){}
	/**
	 * 未选中状态
	 */
	public void unSelected(){}
}
