package com.xc.lib.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Utils {

	/**
	 * 高度计算
	 * 
	 */
	public static class AdapterViewUtil {
		/**
		 * scroll嵌套Listview
		 * 
		 * @param listView
		 */
		public static void setListViewHeightBasedOnChildren(ListView listView) {
			// 获取ListView对应的Adapter
			ListAdapter listAdapter = listView.getAdapter();
			if (listAdapter == null) {
				// pre-condition
				return;
			}

			int totalHeight = 0;
			for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0); // 计算子项View 的宽高
				totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			// listView.getDividerHeight()获取子项间分隔符占用的高度
			// params.height最后得到整个ListView完整显示需要的高度
			listView.setLayoutParams(params);
		}

		/**
		 * scroll嵌套gridview
		 * 
		 * @param gridView
		 */
		public static void setGridViewHeightBasedOnChildren(GridView gridView) {
			// 获取GridView对应的Adapter
			ListAdapter listAdapter = gridView.getAdapter();
			if (listAdapter == null) {
				return;
			}
			int rows;
			int columns = 0;
			int horizontalBorderHeight = 0;
			Class<?> clazz = gridView.getClass();
			try {
				// 利用反射，取得每行显示的个数
				Field column = clazz.getDeclaredField("mRequestedNumColumns");
				column.setAccessible(true);
				columns = (Integer) column.get(gridView);
				// 利用反射，取得横向分割线高度
				Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
				horizontalSpacing.setAccessible(true);
				horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
			if (listAdapter.getCount() % columns > 0) {
				rows = listAdapter.getCount() / columns + 1;
			} else {
				rows = listAdapter.getCount() / columns;
			}
			int totalHeight = 0;
			for (int i = 0; i < rows; i++) { // 只计算每项高度*行数
				View listItem = listAdapter.getView(i, null, gridView);
				listItem.measure(0, 0); // 计算子项View 的宽高
				totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
			}
			ViewGroup.LayoutParams params = gridView.getLayoutParams();
			params.height = totalHeight + horizontalBorderHeight * (rows - 1);// 最后加上分割线总高度
			gridView.setLayoutParams(params);
		}
	}

	public static String uriToDir(Uri uri, Context context) {
		try {
			ContentResolver cr = context.getContentResolver();
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor actualimagecursor = cr.query(uri, proj, null, null, null);
			int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			String img_path = null;
			if (actual_image_column_index >= 0) {
				img_path = actualimagecursor.getString(actual_image_column_index);
			}
			actualimagecursor.close();

			// ContentResolver cr = context.getContentResolver();
			// Cursor cursor = cr.query(uri, null, null, null, null);
			// if (cursor == null)
			// return "";
			// cursor.moveToFirst();
			// String path = cursor.getString(cursor.getColumnIndex("_data"));
			// cursor.close();
			return img_path;
		} catch (Exception e) {
			return uri.getPath();
		}
	}

	public static GradientDrawable getBg(Context context, int color, float tl, float tr, float br, float bl, int alpha) {
		GradientDrawable grad = new GradientDrawable();
		grad.setCornerRadii(new float[] { tl, tl, tr, tr, br, br, bl, bl });
		// grad.setCornerRadius(tl);
		// gradGradientDrawable.setCornerRadii(radi);
		grad.setColor(color);
		grad.setAlpha(alpha);
		return grad;
	}

	public static String toUTF8(String str) {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean isRunningForeground(Context context) {
		String packageName = context.getPackageName();
		String topActivityClassName = getTopActivityName(context);
		if (packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName)) {
			return true;
		} else {
			return false;
		}
	}

	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String getTopActivityName(Context context) {
		String topActivityClassName = null;
		ActivityManager activityManager = (ActivityManager) (context.getSystemService(android.content.Context.ACTIVITY_SERVICE));
		List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
		if (runningTaskInfos != null) {
			ComponentName f = runningTaskInfos.get(0).topActivity;
			topActivityClassName = f.getClassName();
		}
		return topActivityClassName;
	}

	/**
	 * 压缩图片
	 * 
	 * @param bitMap
	 *            需要压缩的bitmap
	 * @param maxSize
	 *            保存到多大KB单位
	 * @return
	 */
	public static Bitmap imageZoom(Bitmap bitMap, double maxSize) {
		// 图片允许最大空间 单位：KB
		// double maxSize = 70.00;
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		double mid = b.length / 1024;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			// 获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i), bitMap.getHeight() / Math.sqrt(i));
		}
		return bitMap;
	}

	/**
	 * 压缩图片到指定大小
	 * 
	 * @param bgimage
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		return bitmap;
	}

	/**
	 * 获取圆角图片
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 添加文字过滤器
	 * 
	 * @param view
	 * @param filter
	 */
	public static void addFilter(TextView view, InputFilter filter) {
		view.setFilters(addFilter(view.getFilters(), filter));
	}

	public static InputFilter[] addFilter(InputFilter[] fileters, InputFilter filter) {
		InputFilter[] fs = null;
		if (fileters == null || fileters.length == 0) {
			fs = new InputFilter[1];
			fs[0] = filter;
		} else {
			int len = fileters.length;
			fs = new InputFilter[len + 1];
			for (int i = 0; i < len; i++) {
				fs[i] = fileters[i];
			}
			fs[len] = filter;
		}

		return fs;
	}

}
