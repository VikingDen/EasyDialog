package com.qihuan.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 存储view中的子view，并做索引
 */
public class ViewHolder {
    private SparseArray<View> views;
    private View parent;

    public ViewHolder(View view) {
        parent = view;
        views = new SparseArray<>();
    }

    /**
     * 根据id，查找对应的view
     *
     * @param id
     * @param <E>
     * @return
     */
    public <E extends View> E get(int id) {
        View childView = views.get(id);
        if (null == childView) {
            childView = parent.findViewById(id);
            views.put(id, childView);
        }
        return (E) childView;
    }

    /**
     * 便捷获取textView
     *
     * @param id
     * @return
     */
    public TextView getTextView(int id) {
        return get(id);
    }

    /**
     * 便捷获取imageView
     *
     * @param id
     * @return
     */
    public ImageView getImageView(int id) {
        return get(id);
    }
}
