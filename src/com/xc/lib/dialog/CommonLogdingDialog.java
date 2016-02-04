package com.xc.lib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxb.myutils.R;

public class CommonLogdingDialog extends CommonDialog {

	ImageView imageView;
	private TextView tv;

	public void showWithText(String text) {
		if (tv != null) {
			tv.setText(text);
		}
		showWithAnimation();

	}

	public CommonLogdingDialog(Context context) {
		super(context, R.layout.xu_common_dlg_loading, R.style.MyDialog);
		tv = (TextView) findViewById(R.id.tipTextView);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void showWithAnimation() {
		imageView = (ImageView) findViewById(R.id.img);
		Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.xu_wait);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
		imageView.startAnimation(operatingAnim);
		this.show();
	}

	@Override
	public void initListener() {

	}
}
