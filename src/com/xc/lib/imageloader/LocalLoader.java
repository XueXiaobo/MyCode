package com.xc.lib.imageloader;

import com.xc.lib.utils.MyHandler;
import com.xc.lib.utils.Tools;
import com.xc.lib.utils.MyHandler.HandleMessageListener;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

public class LocalLoader implements HandleMessageListener {
	Handler handler = new MyHandler(this);
	private Bitmap bitmap = null;
	private View target;
	private String uri;

	public LocalLoader(View target, String uri) {
		this.target = target;
		this.uri = uri;
	}

	public void load(final float width, final float height) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				bitmap = Tools.decodeBitmap(uri, width, height);
				handler.sendEmptyMessage(0);
			}
		}).start();
	}

	@Override
	public void handleMessage(Message message) {
		if (bitmap != null) {
			if (target instanceof ImageView) {
				((ImageView) target).setImageBitmap(bitmap);
			} else {
				target.setBackgroundDrawable(new BitmapDrawable(bitmap));
			}
			bitmap = null;
		}
	}
}
