package com.xc.lib.dialog;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.xc.lib.layout.LayoutUtils;
import com.xc.lib.utils.Utils;
import com.xc.lib.view.MyActionTextView;

public class ActionSheet extends Dialog {
	private int bgcolor;
	private ListView listview;
	private MyActionTextView bottom_tv;
	private List<String> data;
	private String normal_color = "#CFFFFFFF", press_color = "#CFBEBEBE";
	// private String txt;
	// private View v;
	private float textSize = 18;
	private MyAdapter adapter;
	private int height;
	private OnitemclickListener onitemclick;
	private TextConfig[] configs;
	private TextConfig bottomConfigs;

	public ActionSheet(Context context) {
		this(context, android.R.style.Theme_Translucent_NoTitleBar, Color
				.parseColor("#7F000000"));
	}

	public ActionSheet(Context context, int theme, int bgcolor) {
		super(context, theme);
		this.bgcolor = bgcolor;
		init(context);
	}

	public void shuoming() {
		String shuo = "<style name=\"ActionSheet\" parent=\"@android:style/Theme.Dialog\"><item name=\"android:windowFrame\">@null</item><item name=\"android:windowIsFloating\">true</item><item name=\"android:windowIsTranslucent\">true</item><item name=\"android:windowNoTitle\">true</item><item name=\"android:background\">@color/transparent</item><item name=\"android:windowBackground\">@color/transparent</item><item name=\"android:backgroundDimEnabled\">true</item></style>";

		System.out.println("请加入 - " + shuo);
	}

	public ActionSheet(Context context, int bgcolor) {
		this(context, android.R.style.Theme_Translucent_NoTitleBar, bgcolor);
	}

	public void init(Context context) {
		bottomConfigs = new TextConfig(textSize, Color.parseColor("#FD4A2E"));
		setOwnerActivity((Activity) context);
		height = LayoutUtils.getRate4px(80);
		if (height == 0) {
			height = (int) (40 * context.getResources().getDisplayMetrics().density + 0.5f);
		}
		android.view.WindowManager.LayoutParams lay = getWindow()
				.getAttributes();
		lay.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
		lay.height = android.view.WindowManager.LayoutParams.MATCH_PARENT;
		// getWindow().getDecorView().getBackground().setAlpha(0);
		setContentView(createView());
		getWindow().setWindowAnimations(android.R.style.Animation_InputMethod);
	}

	public void setTextSize(int size) {
		this.textSize = size;
	}

	public void setData(List<String> list, String cancel) {
		if (list == null)
			return;
		this.data = list;
		if (bottom_tv != null)
			bottom_tv.setText(cancel);
		configs = new TextConfig[list.size()];
		for (int i = 0; i < configs.length; i++) {
			configs[i] = new TextConfig(textSize, Color.parseColor("#037BFF"));
		}
	}

	public void setData(String[] data, String cancel) {
		if (data == null)
			return;
		setData(Arrays.asList(data), cancel);

	}

	int ratio = 0;

	/**
	 * @return
	 */
	public View createView() {
		RelativeLayout view = new RelativeLayout(getContext());
		listview = new ListView(getContext());
		bottom_tv = new MyActionTextView(getContext());
		bottom_tv.setId(1000);
		bottom_tv.setGravity(Gravity.CENTER);
		view.addView(bottom_tv, RelativeLayout.LayoutParams.MATCH_PARENT,
				height);
		RelativeLayout.LayoutParams lp = (LayoutParams) bottom_tv
				.getLayoutParams();
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		bottom_tv.setLayoutParams(lp);
		RelativeLayout.LayoutParams lpp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lpp.addRule(RelativeLayout.ABOVE, bottom_tv.getId());
		lpp.bottomMargin = LayoutUtils.getRate4px(17);
		view.addView(listview, lpp);
		view.setPadding(0, lpp.bottomMargin, 0, lpp.bottomMargin);
		listview.setOnItemClickListener(onitemlistener);
		// ColorDrawable(Color.parseColor(press_color)));
		bottom_tv.setOnClickListener(click);
		bottom_tv.setTextSize(bottomConfigs.textsize);
		bottom_tv.setTextColor(bottomConfigs.textcolor);

		ratio = LayoutUtils.getRate4px(5);

		bottom_tv.setBackgroundDrawable(getBg(ratio, ratio, ratio, ratio));
		adapter = new MyAdapter();
		listview.setAdapter(adapter);
		listview.setDivider(new ColorDrawable(Color.parseColor(press_color)));
		listview.setDividerHeight(1);
		listview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		listview.setCacheColorHint(Color.TRANSPARENT);
		view.setBackgroundColor(bgcolor);
		// v = view;
		return view;
	}

	public void setId(int id) {
		if (listview != null)
			listview.setId(id);
	}

	private Drawable getRadi(String color, float tl, float tr, float br,
			float bl) {
		return Utils.getBg(getContext(), Color.parseColor(color), tl, tr, br,
				bl, 200);
	}

	public StateListDrawable getBg(float tl, float tr, float bl, float br) {
		return bottom_tv.getDrawable(getRadi(normal_color, tl, tr, br, bl),
				getRadi(press_color, tl, tr, br, bl));
	}

	public void setPostion(int txtSize, int txtColor, int position) {
		configs[position].textsize = txtSize;
		configs[position].textcolor = txtColor;
	}

	public void setPostion(int txtColor, int position) {
		configs[position].textcolor = txtColor;
	}

	public void setBottom(int txtColor, int txtSize) {
		bottomConfigs.textcolor = txtColor;
		bottomConfigs.textsize = txtSize;
		setBottom(bottomConfigs);
	}

	public void setBottom(int txtColor) {
		bottomConfigs.textcolor = txtColor;
		setBottom(bottomConfigs);
	}

	public void setBottom(TextConfig con) {
		bottom_tv.setTextSize(con.textsize);
		bottom_tv.setTextColor(con.textcolor);
	}

	public void setAllConfig(int txtColor, int txtSize) {
		for (int i = 0; i < configs.length; i++) {
			configs[i].textcolor = txtColor;
			configs[i].textsize = txtSize;
		}
		bottomConfigs.textcolor = txtColor;
		bottomConfigs.textsize = txtSize;
	}

	public void setAllConfig(int txtColor) {
		for (int i = 0; i < configs.length; i++) {
			configs[i].textcolor = txtColor;
		}
		bottomConfigs.textcolor = txtColor;
	}

	public void refresh() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void show() {
		super.show();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return data == null ? 0 : data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				holder = new Holder();
				RelativeLayout layout = new RelativeLayout(getContext());
				TextView tv = new MyActionTextView(getContext());
				layout.addView(tv);

				RelativeLayout.LayoutParams params = (LayoutParams) tv
						.getLayoutParams();
				params.height = height;
				params.width = LayoutParams.MATCH_PARENT;
				tv.setLayoutParams(params);
				tv.setGravity(Gravity.CENTER);
				holder.tv = tv;
				layout.setTag(holder);
				convertView = layout;
			} else {
				holder = (Holder) convertView.getTag();
			}

			holder.tv.setTextSize(configs[position].textsize);
			holder.tv.setTextColor(configs[position].textcolor);

			if (getCount() == 1) {
				holder.tv.setBackgroundDrawable(getBg(ratio, ratio, ratio,
						ratio));
			} else {
				if (position == 0) {
					holder.tv.setBackgroundDrawable(getBg(ratio, ratio, 0, 0));
				} else if (position == getCount() - 1) {
					holder.tv.setBackgroundDrawable(getBg(0, 0, ratio, ratio));
				} else {
					holder.tv.setBackgroundDrawable(getBg(0, 0, 0, 0));
				}
			}

			holder.tv.setText(data.get(position));
			return convertView;
		}

		class Holder {
			TextView tv;
		}

	}

	private AdapterView.OnItemClickListener onitemlistener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			cancel();
			if (onitemclick != null)
				onitemclick.onItemClick(parent, position);
		}
	};

	private View.OnClickListener click = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			cancel();
		}
	};

	public void cancel() {
		if (onitemclick != null)
			onitemclick.onCancel();
		super.cancel();
	};

	public void setOnItemclickListener(OnitemclickListener itemclick) {
		this.onitemclick = itemclick;
	}

	public interface OnitemclickListener {
		void onItemClick(AdapterView<?> parent, int position);

		void onCancel();
	}

	public class TextConfig {
		float textsize;
		int textcolor;

		public TextConfig(float size, int color) {
			this.textsize = size;
			this.textcolor = color;
		}
	}

}
