package com.xc.lib.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xxb.myutils.R;

public class MyDialog {

	public static CommonDialog comfirm(final Context context, final String title, final String text, final SureCallback callback) {
		return comfirm(context, title, text, callback, true, false);
	}

	public static CommonDialog comfirm(final Context context, final String title, final String text, final SureCallback callback, boolean isShow) {
		return comfirm(context, title, text, callback, isShow, false);
	}

	public static CommonDialog comfirm(final Context context, final String title, final String text, final SureCallback callback, boolean isShow, final boolean isSign) {
		CommonDialog dialog = new CommonDialog(context, R.layout.xu_confirm, R.style.MyDialog) {
			Button cancelButton, okButton;

			public void initListener() {
				final TextView titleTextView = (TextView) findViewById(R.id.title);
				final TextView textTextView = (TextView) findViewById(R.id.text);

				final View signLine = findViewById(R.id.sign_line);
				titleTextView.setText(title);
				textTextView.setText(text);

				cancelButton = (Button) findViewById(R.id.cancel_btn);
				okButton = (Button) findViewById(R.id.ok_btn);

				if (isSign) {
					signLine.setVisibility(View.GONE);
					cancelButton.setVisibility(View.GONE);
				}
				cancelButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						callback.onClick(0);
						closeDialog();
					}
				});

				okButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						callback.onClick(1);
						closeDialog();
					}
				});
			}

		};
		if (isShow)
			dialog.show();
		return dialog;
	}

	public static class SureCallback {
		public int LEFT = 0;
		public int RIGHT = 1;

		public void onClick(int tag) {
		}

	}

}
