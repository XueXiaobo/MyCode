package com.xc.lib.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.xxb.myutils.R;

public class CommonImageLogdingDialog extends CommonDialog {

	ImageView imageView;
	private int backAnimRes = 0;

	public CommonImageLogdingDialog(Context context, int backAnimRes) {
		super(context, R.layout.xu_common_image_loading, R.style.MyDialog);
		this.backAnimRes = backAnimRes;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void showWithAnimation() {
		imageView = (ImageView) findViewById(R.id.img);
		imageView.setBackgroundResource(backAnimRes);
		AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
		drawable.start();
		this.show();
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}
}
