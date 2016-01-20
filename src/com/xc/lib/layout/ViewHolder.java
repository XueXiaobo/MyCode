package com.xc.lib.layout;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {
	@SuppressWarnings("unchecked")
	public static <T extends View> T getView(View layout, int id) {
		SparseArray<View> holder = (SparseArray<View>) layout.getTag();
		if (holder == null) {
			holder = new SparseArray<View>();
			layout.setTag(holder);
		}
		View childView = holder.get(id);
		if (childView == null) {
			childView = layout.findViewById(id);
			holder.put(id, childView);
		}
		return (T) childView;
	}
}
