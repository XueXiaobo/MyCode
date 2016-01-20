package com.xc.lib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewTreeObserver;

import com.xc.lib.layout.ScreenConfig;

public class Tools {
	/**
	 * 返回需要图片大小
	 * 
	 * @param url
	 * @param width
	 *            理想状态下的宽度
	 * @param height
	 *            理想状态下的高度
	 * @return 尽量趋向理想大小的图片
	 */
	public static Bitmap decodeBitmap(String file, float width, float height) {
		File temp = new File(file);
		if (temp == null || !temp.exists())
			return null;

		float maxWidth = 0;
		float maxHeight = 0;
		if (width > 0 && height > 0) {
			maxWidth = width;
			maxHeight = height;
		} else {
			maxWidth = ScreenConfig.SCRREN_W;
			maxHeight = ScreenConfig.SCRREN_H;
		}
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		FileInputStream stream1;
		try {
			stream1 = new FileInputStream(temp);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();
			int max = (int) Math.max(o.outWidth / maxWidth, o.outHeight / maxHeight);
			if (max < 1)
				max = 1;
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = max;
			FileInputStream stream2 = new FileInputStream(temp);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			return bitmap;
		} catch (Throwable ex) {
			if (ex instanceof OutOfMemoryError)
				System.gc();
		}
		return null;
	}

	public static Bitmap decodeBitmap(Context context, int resId, float width, float height) {
		float maxWidth = 0;
		float maxHeight = 0;
		if (width > 0 && height > 0) {
			maxWidth = width;
			maxHeight = height;
		} else {
			maxWidth = ScreenConfig.SCRREN_W;
			maxHeight = ScreenConfig.SCRREN_H;
		}
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		InputStream stream1;
		try {
			stream1 = context.getResources().openRawResource(resId);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();
			int max = (int) Math.max(o.outWidth / maxWidth, o.outHeight / maxHeight);
			if (max < 1)
				max = 1;
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = max;
			InputStream stream2 = context.getResources().openRawResource(resId);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			return bitmap;
		} catch (Throwable ex) {
			if (ex instanceof OutOfMemoryError)
				System.gc();
		}
		return null;
	}

	@SuppressLint("NewApi")
	public static void cancelOver(View v) {
		if (VERSION.SDK_INT > 8) {
			v.setOverScrollMode(View.OVER_SCROLL_NEVER);
		}
	}

	public static Bitmap decodeBitmap(File file, float width, float height) {
		if (file == null)
			return null;
		return decodeBitmap(file.getAbsolutePath(), width, height);
	}

	public static Bitmap decodeBitmap(String url) {
		return decodeBitmap(url, 0, 0);
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static void getViewSize(final View view, final ViewSizeListener listener) {
		if (listener == null)
			return;
		view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				listener.onSize(view.getWidth(), view.getHeight());
				view.getViewTreeObserver().removeOnPreDrawListener(this);
				return false;
			}
		});

	}

	/**
	 * 判断是否安装目标应用
	 * 
	 * @param packageName
	 *            目标应用安装后的包名
	 * @return 是否已安装目标应用
	 */
	public static boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
	}

	public static void deleteFile(File file) {
		if (file != null) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				if (files != null) {
					for (File file2 : files) {
						deleteFile(file2);
					}
				}
			} else {
				file.delete();
			}

		}
	}

	public interface ViewSizeListener {
		void onSize(int width, int height);
	}
}
