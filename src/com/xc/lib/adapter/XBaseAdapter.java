package com.xc.lib.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xc.lib.layout.ViewHolder;
import com.xc.lib.utils.IAdapterMeasure;

/**
 * 基本适配器，许多共有功能
 * @author xxb
 * @version v1.0 创建时间：2016年2月3日 下午5:42:49
 */
public abstract class XBaseAdapter<T> extends BaseAdapter {
	protected List<T> datas;
	protected Context context;
	protected LayoutInflater inflater;
	private ViewModel vm = new ViewModel();

	public XBaseAdapter(Context context, List<T> datas) {
		this.context = context;
		this.datas = datas;
		this.inflater = LayoutInflater.from(context);
	}

	/**
	 * 设置绑定数据
	 * 
	 * @param datas
	 */
	public void setDatas(List<T> datas) {
		setDatas(datas, false);
	}
	/**
	 * 
	 * @param datas 设置的数据
	 * @param isNotify  是否需要刷新数据
	 */
	public void setDatas(List<T> datas, boolean isNotify) {
		this.datas = datas;
		if (isNotify)
			notifyDataSetChanged();
	}

	public List<T> getDatas() {
		return datas;
	}

	/**
	 * 添加绑定数据
	 * 
	 * @param datas
	 */
	public void addDatas(List<T> datas) {
		addDatas(datas, false);
	}

	/**
	 * 添加绑定数据
	 * 
	 * @param datas 设置的数据
	 * @param 是否需要刷新适配器
	 */
	public void addDatas(List<T> datas, boolean isNotify) {
		if (datas == null)
			return;
		if (this.datas == null)
			this.datas = new ArrayList<T>();
		this.datas.addAll(datas);
		if (isNotify)
			notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public T getItem(int position) {
		return datas.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = getLayoutView(position);
		}
		if (parent instanceof IAdapterMeasure && ((IAdapterMeasure) parent).isMeasure()) {
			// 如果只是测试尺寸则不需要绑定数据
			return convertView;
		}
		bindData(getItem(position), position, vm.setView(convertView));
		return convertView;
	}

	/**
	 * 返回layoutid
	 * 
	 * @param position
	 * @return
	 */
	protected abstract int getLayout(int position);

	protected View getLayoutView(int position) {
		return inflater.inflate(getLayout(position), null);
	}

	/**
	 * 绑定数据
	 * 
	 * @param item 数据
	 * @param position 下标
	 * @param vm 类似viewholder
	 */
	protected abstract void bindData(T item, int position, ViewModel vm);

	public class ViewModel {
		private View view;

		public ViewModel setView(View view) {
			this.view = view;
			return this;
		}

		public View getView() {
			return view;
		}

		public <E extends View> E getViewForRes(int id, Class<E> cls) {
			return ViewHolder.getView(view, id);
		}

		public TextView getViewForResTv(int id) {
			return ViewHolder.getView(view, id);
		}

		public ImageView getViewForResIv(int id) {
			return ViewHolder.getView(view, id);
		}

	}

}
