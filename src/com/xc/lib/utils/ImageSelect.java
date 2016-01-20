package com.xc.lib.utils;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageSelect {
	private Activity ac;

	public ImageSelect(Activity ac) {
		this.ac = ac;
	}

	// 打开相册
	public void openPhotoAlbum(int requestcode) {
		Intent openAlbumIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// openAlbumIntent.setType("image/*");
		ac.startActivityForResult(openAlbumIntent, requestcode);
	}

	// 打开相机
	public void openCamera(File outFile, int requestcode) {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 设置保存路径 指定图片的输出路径
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(outFile));
		ac.startActivityForResult(openCameraIntent, requestcode);
	}

	public void openVideo(long time, int requestcode) {
		Intent mIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		mIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.5);// 画质0.5
		mIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, time);// 70s
		ac.startActivityForResult(mIntent, requestcode);// CAMERA_ACTIVITY = 1
	}

	/**
	 * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 * 
	 * @param videoPath
	 *            视频的路径
	 * @param width
	 *            指定输出视频缩略图的宽度
	 * @param height
	 *            指定输出视频缩略图的高度度
	 * @param kind
	 *            参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图
	 */
	public static Bitmap getVideoThumbnail(String videoPath, int width,
			int height, int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

}
