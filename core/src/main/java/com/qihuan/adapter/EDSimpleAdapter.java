package com.qihuan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by qihuan on 4/17/15.
 * 简化后的adapter，用于更加方便的编写adapter
 */
public abstract class EDSimpleAdapter<T> extends EDCommonAdapter<T> {
    /**
     * 布局文件
     */
    private int layoutId;

    /**
     * @param items    数据
     * @param layoutId 布局文件
     */
    public EDSimpleAdapter(List<T> items, int layoutId) {
        super(items);
        this.layoutId = layoutId;
    }

    protected EDSimpleAdapter(int layoutId) {
        super();
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            //初始化view和viewHolder
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            //从view中获取viewHolder备份
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindView(convertView, position, viewHolder);
        return convertView;
    }

    /**
     * 子类需要实现的方法，根据position获取数据，根据viewHolder获取view，根据convertView做一些界面处理
     *
     * @param convertView
     * @param position
     * @param viewHolder
     */
    protected abstract void bindView(View convertView, int position, ViewHolder viewHolder);

}
