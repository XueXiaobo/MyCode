package com.xc.lib.adapter;

import java.util.ArrayList;
import java.util.List;

import com.xc.lib.layout.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
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

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void addDatas(List<T> datas) {
		if (datas == null)
			return;
		if (this.datas == null)
			this.datas = new ArrayList<T>();
		this.datas.addAll(datas);
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public T getItem(int position) {
		return datas.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(getLayout(position), null);
		}
		bindData(getItem(position), position, vm.setView(convertView));
		return convertView;
	}

	protected abstract int getLayout(int position);

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
		public <E extends View> E getViewForRes(int id) {
			return ViewHolder.getView(view, id);
		}

	}

}
