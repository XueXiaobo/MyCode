package com.xc.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

//com.xc.lib.view.CircleImageView
public class CircleImageView extends ImageView {
	private static final Xfermode MASK_XFERMODE;
	private Bitmap mask;
	private Paint paint;
	Paint arcpaint;
	private int paintSize = 4;

	static {
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		MASK_XFERMODE = new PorterDuffXfermode(localMode);
	}

	public CircleImageView(Context paramContext) {
		this(paramContext, null);
	}

	public CircleImageView(Context paramContext, AttributeSet paramAttributeSet) {
		this(paramContext, paramAttributeSet, 0);
	}

	public CircleImageView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		init();
	}

	public void init() {
		paintSize *= getResources().getDisplayMetrics().widthPixels / 720f;
		// arcpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		arcpaint = new Paint();
		arcpaint.setAntiAlias(true);
		arcpaint.setColor(Color.WHITE);
		arcpaint.setStyle(Paint.Style.STROKE);
		arcpaint.setStrokeWidth(paintSize);
	}

	public void setArcColor(int color) {
		arcpaint.setColor(color);
	}

	public Bitmap createMask() {
		int i = getWidth();
		int j = getHeight();
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint(1);
		localPaint.setColor(-16777216);
		float f1 = getWidth();
		float f2 = getHeight();
		RectF localRectF = new RectF(0.0F, 0.0F, f1, f2);
		localCanvas.drawOval(localRectF, localPaint);
		return localBitmap;
	};

	protected void onDraw(Canvas paramCanvas) {
		// paramCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,
		// Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG)); // canvas���ÿ����

		Drawable localDrawable = getDrawable();
		if (localDrawable == null)
			return;
		try {
			if (this.paint == null) {
				Paint localPaint1 = new Paint();
				this.paint = localPaint1;
				this.paint.setFilterBitmap(false);
				Paint localPaint2 = this.paint;
				Xfermode localXfermode1 = MASK_XFERMODE;
				Xfermode localXfermode2 = localPaint2
						.setXfermode(localXfermode1);
			}
			float f1 = getWidth();
			float f2 = getHeight();
			int i = paramCanvas.saveLayer(0.0F, 0.0F, f1, f2, null, 31);
			int j = getWidth();
			int k = getHeight();
			localDrawable.setBounds(0, 0, j, k);
			localDrawable.draw(paramCanvas);
			if ((this.mask == null) || (this.mask.isRecycled())) {
				Bitmap localBitmap1 = createMask();
				this.mask = localBitmap1;
			}
			Bitmap localBitmap2 = this.mask;
			Paint localPaint3 = this.paint;
			paramCanvas.drawBitmap(localBitmap2, 0.0F, 0.0F, localPaint3);
			paramCanvas.restoreToCount(i);
			final RectF rectf = new RectF(paintSize / 2, paintSize / 2,
					localBitmap2.getWidth() - paintSize / 2,
					localBitmap2.getHeight() - paintSize / 2);
			paramCanvas.drawArc(rectf, 0, 360, false, arcpaint);
			return;
		} catch (Exception localException) {
			StringBuilder localStringBuilder = new StringBuilder()
					.append("Attempting to draw with recycled bitmap. View ID = ");
			System.out.println("localStringBuilder==" + localStringBuilder);
		}
	}
}