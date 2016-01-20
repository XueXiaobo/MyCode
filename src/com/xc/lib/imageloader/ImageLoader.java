package com.xc.lib.imageloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.xc.lib.utils.Tools;

/**
 * 图片加载
 * 
 * @author Administrator
 * 
 */
public class ImageLoader {
	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;
	Handler handler = new Handler();// handler to display images in UI thread
	/**
	 * 必须写一个缓存地址，否者会出错
	 */
	public ImageLoader(String cachedir) {
		File cache = new File(cachedir);
		if (!cache.exists())
			cache.mkdirs();
		fileCache = new FileCache(cache);
		executorService = Executors.newFixedThreadPool(3);
	}
	
	/**
	 * 加载网络图片
	 * 
	 * @param url
	 *            网络图片地址
	 * @param imageView
	 *            需要加载的对象
	 * @param sizeW
	 *            理想状态下的宽度
	 * @param sizeH
	 *            理想状态下的高度
	 * @param failresouce
	 *            加载失败的默认图片 0代表不设置
	 */

	public void displayImage(String url, ImageView imageView, float sizeW,
			float sizeH, int failsource) {
		if (failsource != 0)
			imageView.setImageResource(failsource);
		if (url == null)
			return;
		url = url.replace("\\", "");
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null)
			imageView.setImageBitmap(bitmap);
		else {
			queuePhoto(url, imageView, sizeW, sizeH);
		}
	}

	/**
	 * 加载网络图片
	 * 
	 * @param url
	 *            网络图片地址
	 * @param imageView
	 *            需要加载的对象
	 * @param failresouce
	 *            加载失败的默认图片 0代表不设置
	 */
	public void displayImage(String url, ImageView imageView, int failsource) {
		displayImage(url, imageView, 0, 0, failsource);
	}

	//
	// /**
	// * 加载本地图片
	// *
	// * @param dir
	// * 本地图片路径
	// * @param imageView
	// * 需要加载的对象
	// * @param failresouce
	// * 加载失败的默认图片 0代表不设置
	// */
	// public void displayLocalImage(String dir, ImageView imageView,
	// int failresouce) {
	// displayLocalImage(dir, imageView, 0, 0, failresouce);
	// }

	/**
	 * 根据url返回图片本地路径
	 * 
	 * @param url
	 * @return 如果存在返回路劲，不出在返回null
	 */
	public String getImageDir(String url) {
		url = url.replace("\\", "");
		File file = fileCache.getFile(url);
		return file == null ? null : file.getAbsolutePath();
	}

	//
	// /**
	// * 加载本地图片
	// *
	// * @param dir
	// * 本地图片路径
	// * @param imageView
	// * 需要加载的对象
	// * @param sizeW
	// * 理想状态下的宽度
	// * @param sizeH
	// * 理想状态下的高度
	// * @param failresouce
	// * 加载失败的默认图片 0代表不设置
	// */
	// public void displayLocalImage(String dir, ImageView imageView, float
	// sizeW,
	// float sizeH, int failresouce) {
	// File file = new File(dir);
	// if (failresouce != 0)
	// imageView.setImageResource(failresouce);
	// if (!file.exists() || imageView == null)
	// return;
	// try {
	// Bitmap bmp = memoryCache.get(dir);
	// if (bmp == null) {
	// new LocalLoader(imageView, dir).load(sizeW, sizeH);
	// }
	// } catch (Throwable ex) {
	// ex.printStackTrace();
	// if (ex instanceof OutOfMemoryError)
	// memoryCache.clear();
	// }
	// }

	private void queuePhoto(String url, ImageView imageView, float sizeW,
			float sizeH) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p, sizeW, sizeH));
	}

	private Bitmap getBitmap(String url, float sizeW, float sizeH) {
		File f = fileCache.getFile(url);
		// from SD cache
		Bitmap b = Tools.decodeBitmap(f, sizeW, sizeH);
		if (b != null)
			return b;
		b = Tools.decodeBitmap(url, sizeW, sizeH);
		if (b != null)
			return b;
		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();

			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Tools.CopyStream(is, os);
			os.close();
			bitmap = Tools.decodeBitmap(f, sizeW, sizeH);
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		}
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;
		float sizeW;
		float sizeH;

		PhotosLoader(PhotoToLoad photoToLoad, float sizeW, float sizeH) {
			this.photoToLoad = photoToLoad;
			this.sizeH = sizeH;
			this.sizeW = sizeW;
		}

		@Override
		public void run() {
			try {
				if (imageViewReused(photoToLoad))
					return;
				Bitmap bmp = getBitmap(photoToLoad.url, sizeW, sizeH);
				memoryCache.put(photoToLoad.url, bmp);
				if (imageViewReused(photoToLoad))
					return;
				BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
				handler.post(bd);
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

}
