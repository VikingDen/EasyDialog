package com.qihuan.adapter;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by qihuan on 4/17/15.
 * 通用的baseAdapter
 */
public abstract class EDCommonAdapter<T> extends BaseAdapter {

    private List<T> items = null;

    /**
     * @param items 数据
     */
    public EDCommonAdapter(List<T> items) {
        this.items = items;
    }

    protected EDCommonAdapter() {
    }


    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return null == items ? 0 : items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
