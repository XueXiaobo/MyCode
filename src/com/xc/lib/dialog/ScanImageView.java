package com.xc.lib.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xc.lib.layout.LayoutUtils;
import com.xc.lib.view.MyCustViewPager;
import com.xc.lib.view.MyPageAdapter;
import com.xc.lib.view.PhotoView;
import com.xc.lib.xutils.BitmapUtils;
import com.xc.lib.xutils.bitmap.BitmapDisplayConfig;
import com.xc.lib.xutils.bitmap.core.BitmapSize;

public class ScanImageView extends Dialog {
	private int bgcolor;
	private TextView numTv;
	private MyCustViewPager pager;
	private BitmapUtils bu;
	private List<View> list;
	private int last;

	public ScanImageView(Context context) {
		this(context, Color.BLACK);
	}

	public ScanImageView(Context context, int bgcolor, int theme) {
		super(context, theme);
		this.bgcolor = bgcolor;
		init(context);
	}

	public ScanImageView setLoader(BitmapUtils bu) {
		this.bu = bu;
		return this;
	}

	public void setData(String[] imgs, int current) {
		list = onCreateView(imgs);
		pager.setAdapter(new MyPageAdapter(list));
		int size = list.size();
		if (size > 2 && current < size) {
			if (current > 0)
				pager.setCurrentItem(current);
			numTv.setText((current + 1) + "/" + list.size());
		}

	}

	public ScanImageView(Context context, int bgcolor) {
		this(context, bgcolor, android.R.style.Theme_NoTitleBar);
	}

	public void init(Context context) {
		setOwnerActivity((Activity) context);
		android.view.WindowManager.LayoutParams lay = getWindow().getAttributes();
		lay.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
		lay.height = android.view.WindowManager.LayoutParams.MATCH_PARENT;
		setContentView(createView());
		getWindow().setWindowAnimations(android.R.style.Animation_InputMethod);
	}

	View createView() {
		RelativeLayout view = new RelativeLayout(getContext());
		pager = new MyCustViewPager(getContext());
		view.addView(pager, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		numTv = new TextView(getContext());
		numTv.setTextSize(16);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		params.bottomMargin = LayoutUtils.getRate4px(30);
		view.addView(numTv, params);
		view.setBackgroundColor(bgcolor);
		pager.setOnPageChangeListener(changeListener);
		return view;
	}

	private BitmapDisplayConfig bdc;

	List<View> onCreateView(String[] imgs) {
		List<View> list = new ArrayList<View>();
		for (int i = 0; i < imgs.length; i++) {
			if (imgs[i] == null)
				continue;
			PhotoView photo = new PhotoView(getContext());
			if (bdc == null) {
				bdc = new BitmapDisplayConfig();
				bdc.setBitmapMaxSize(new BitmapSize(720, 1024));
			}
			bu.display(photo, imgs[i], bdc);
			//
			// bu.displayImage(imgs[i], photo, image_w > 0 ? image_w : 1024,
			// image_h > 0 ? image_h : 720, defaultSource);
			list.add(photo);
		}
		return list;
	}

	OnPageChangeListener changeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			if (last != arg0) {
				numTv.setText((arg0 + 1) + "/" + list.size());
				last = arg0;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

}
