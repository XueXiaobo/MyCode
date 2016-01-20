package com.xc.lib.imageloader;

import java.io.File;

public class FileCache {

	private File cacheDir;

	public FileCache(File cacheDir) {
		this.cacheDir = cacheDir;
	}

	public File getFile(String url) {
		return new File(cacheDir, String.valueOf(url.hashCode()));

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}