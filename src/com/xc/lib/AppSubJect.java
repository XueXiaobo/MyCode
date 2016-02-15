package com.xc.lib;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * @author xxb
 * @version v1.0 创建时间：2016年2月14日 下午4:58:52
 */
public class AppSubJect {
	private List<Activity> ats = new LinkedList<Activity>();

	public void attch(Activity activity) {
		if (!ats.contains(activity))
			ats.add(activity);
	}

	public void detach(Activity activity) {
		ats.remove(activity);
	}

	public void clear() {
		ats.clear();
	}

	public void exit() {
		for (Activity ac : ats) {
			if (ac != null)
				ac.finish();
		}
	}
}
