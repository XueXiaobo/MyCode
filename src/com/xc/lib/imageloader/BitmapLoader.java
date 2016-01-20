package com.xc.lib.imageloader;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

public class BitmapLoader {
	private String DEFAULT = "default";
	private MemoryCache mc = new MemoryCache();
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	private Loader loader;
	private ExecutorService ecservice;
	Handler handler = new Handler();

	public BitmapLoader() {
		ecservice = Executors.newFixedThreadPool(3);
	}

	public void display(ImageView image, String path, int width, int height,
			int tag) {
		imageViews.put(image, path);
		image.setImageBitmap(null);
		ecservice.submit(new LoaderThread(image, path, width, height, tag));
	}

	class LoaderThread implements Runnable {
		public ImageView imageview;
		public String path;
		public int width, height;
		public int tag;

		public LoaderThread(ImageView img, String path, int width, int height,
				int tag) {
			this.imageview = img;
			this.path = path;
			this.width = width;
			this.height = height;
			this.tag = tag;
		}

		@Override
		public void run() {
			Bitmap map = getBitmap(path, width, height, tag);
			mc.put(path, map);
			if (imageViewReused(this)) {
				return;
			}
			handler.post(new BitmapDisplayer(map, this));
		}
	}

	public void setDefaultBitmap(Bitmap map) {
		mc.put(DEFAULT, map);
	}

	public Bitmap getDefaultBitmap() {
		return mc.get(DEFAULT);
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		LoaderThread photoToLoad;

		public BitmapDisplayer(Bitmap b, LoaderThread p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null)
				photoToLoad.imageview.setImageBitmap(bitmap);
		}
	}

	private Bitmap getBitmap(String path, int width, int height, int tag) {
		try {
			Bitmap map = mc.get(path);
			if (map != null || loader == null)
				return map;
			map = loader.getBitmap(path, width, height, tag);
			return map;
		} catch (Throwable e) {
			e.printStackTrace();
			if (e instanceof OutOfMemoryError)
				mc.clear();
			return null;
		}

	}

	boolean imageViewReused(LoaderThread lor) {
		String tag = imageViews.get(lor.imageview);
		if (tag == null || !tag.equals(lor.path))
			return true;
		return false;
	}

	public BitmapLoader setLoader(Loader loader) {
		this.loader = loader;
		return this;
	}

	public interface Loader {
		Bitmap getBitmap(String path, int width, int height, int tag);
	}

}
