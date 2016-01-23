package com.xc.lib.layout;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

public class ReSizeFilter {
	private List<Class<?>> clss = new ArrayList<Class<?>>();

	public void add(Class<?> cls) {
		if (!clss.contains(cls))
			clss.add(cls);
	}

	public void clear() {
		clss.clear();
	}

	public boolean isResize(View view) {
		int len = clss.size();
		for (int i = 0; i < len; i++) {
			if (clss.get(i).isAssignableFrom(view.getClass()))
				return false;
		}
		return true;
	}
}
