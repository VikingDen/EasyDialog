package com.qihuan.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by qihuan on 16/4/18.
 * dialog root View
 */
public class EasyRootLayout extends LinearLayout {
    /**
     * 如果包含scrollView
     */
    private ScrollView mScrollView;
    private View mBottom;

    public EasyRootLayout(Context context) {
        super(context);
    }

    public EasyRootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyRootLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyRootLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        mBottom = findViewById(R.id.ed_btn_layout);
        mScrollView = (ScrollView) findViewById(R.id.ed_content_scroll);
    }

    public void setCustomScrollView(ScrollView scrollView) {
        mScrollView = scrollView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (null != mScrollView) {
            int measuredScrollViewHeight = mScrollView.getMeasuredHeight();
            int dp_320 = EasyUtil.dp2px(320);
            //超过了一定长度，启用scrollView的高度限定
            if (measuredScrollViewHeight > dp_320) {
                int width = getMeasuredWidth();
                int height = getMeasuredHeight() - measuredScrollViewHeight + dp_320;

                if (null != mBottom && mBottom.getVisibility() == VISIBLE) {
                    //有底栏
                    height += mBottom.getMeasuredHeight();
                } else {
                    //处理padding
                    height += EasyUtil.dp2px(10);
                }

                mScrollView.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(dp_320, MeasureSpec.AT_MOST));

                setMeasuredDimension(width, height);
            }
        }
    }
}
