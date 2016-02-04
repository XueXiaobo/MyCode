package com.xc.lib.view;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xc.lib.annotation.Resize;
import com.xc.lib.layout.FindView;
import com.xc.lib.layout.LayoutUtils;
import com.xxb.myutils.R;

/**
 * @author xxb
 * @version v1.0 创建时间：2016年2月4日 下午2:06:58
 */
public class XTitleBar implements OnClickListener, FindView {
	@Resize(id = R.id.xtitle_bar_layout)
	private View layoutView;

	@Resize(id = R.id.leftLayout)
	private LinearLayout leftLayout;

	@Resize(id = R.id.rightLayout)
	private RelativeLayout rightLayout;

	@Resize(id = R.id.leftTv)
	private TextView leftTv;

	@Resize(id = R.id.rightTv)
	private TextView rightTv;

	@Resize(id = R.id.midTv)
	private TextView midTv;

	@Resize(id = R.id.leftImg)
	private ImageView leftImg;

	@Resize(id = R.id.rightImg)
	private ImageView rightImg;
	private Activity mContext;

	private View rootView;

	private XTitlebarDelegate mDelegate;
	
	public int leftDefaultBg = R.drawable.xu_pic_left_back_lige;

	public void setDelegate(XTitlebarDelegate delegate) {
		this.mDelegate = delegate;
	}

	public XTitleBar(Activity mContext, int leftVis, int midVis, int rightVis) {
		this(mContext, null, leftVis, midVis, rightVis);
	}

	public static XTitleBar createDefault(Activity activity) {
		return new XTitleBar(activity, View.GONE, View.GONE, View.GONE);
	}

	public XTitleBar(Activity mContext, View root, int leftVis, int midVis, int rightVis) {
		this.mContext = mContext;
		this.rootView = root;
		LayoutUtils.reSize(mContext, this);
		leftLayout.setOnClickListener(this);
		rightLayout.setOnClickListener(this);
		setLeftVis(leftVis);
		setRightVis(rightVis);
		setMidVis(midVis);
		setDelegate(new XTitlebarDelegate() {
			
			@Override
			public void onTitleClick(int clickTag) {
				if(clickTag == left)//设置默认点击返回
					XTitleBar.this.mContext.onBackPressed();
			}
		});
	}

	public void setLeftVis(int vis) {
		leftLayout.setVisibility(vis);
	}

	public void setRightVis(int vis) {
		rightLayout.setVisibility(vis);
	}

	public void setMidVis(int vis) {
		midTv.setVisibility(vis);
	}

	public void setLayoutBackground(int color) {
		layoutView.setBackgroundColor(color);
	}

	public void setLayoutBackgroundResource(int resId) {
		layoutView.setBackgroundResource(resId);
	}

	public void setLeft(int leftBg, String text) {
		if (leftBg != 0) {
			leftImg.setImageResource(leftBg);
			leftImg.setVisibility(View.VISIBLE);
		} else {
			leftImg.setVisibility(View.GONE);
		}
		if (text != null) {
			leftTv.setText(text);
			leftTv.setVisibility(View.VISIBLE);
		} else {
			leftTv.setVisibility(View.GONE);
		}
		setLeftVis(View.VISIBLE);
	}

	public void setLeft(int leftBg) {
		setLeft(leftBg, null);
	}

	public void setLeft(String text) {
		setLeft(0, text);
	}

	private void setRight(int rightBg, String text) {
		if (rightBg != 0) {
			rightImg.setImageResource(rightBg);
			rightImg.setVisibility(View.VISIBLE);
		} else {
			rightImg.setVisibility(View.GONE);
		}
		if (text != null) {
			rightTv.setText(text);
			rightTv.setVisibility(View.VISIBLE);
		} else {
			rightTv.setVisibility(View.GONE);
		}
		setRightVis(View.VISIBLE);
	}

	public void setRight(int rightBg) {
		setRight(rightBg, null);
	}

	public void setRight(String text) {
		setRight(0, text);
	}

	public void setMid(String text) {
		if (text != null) {
			midTv.setText(text);
			midTv.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public View IfindView(int id) {
		if (rootView != null)
			return rootView.findViewById(id);
		return mContext.findViewById(id);
	}

	@Override
	public void onClick(View v) {
		if (mDelegate != null)
			mDelegate.onTitleClick(v == leftLayout ? XTitlebarDelegate.left : XTitlebarDelegate.right);
	}

	public LinearLayout getLeftLayout() {
		return leftLayout;
	}


	public RelativeLayout getRightLayout() {
		return rightLayout;
	}


	public TextView getLeftTv() {
		return leftTv;
	}


	public TextView getRightTv() {
		return rightTv;
	}

	public TextView getMidTv() {
		return midTv;
	}


	public ImageView getLeftImg() {
		return leftImg;
	}


	public ImageView getRightImg() {
		return rightImg;
	}

	public interface XTitlebarDelegate {
		int left = 0, right = 1;

		void onTitleClick(int clickTag);
	}

}
