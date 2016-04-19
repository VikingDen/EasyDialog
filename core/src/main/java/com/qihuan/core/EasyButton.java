package com.qihuan.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by qihuan on 16/4/18.
 * 按钮（确认、取消、其他）
 */
public class EasyButton extends TextView {
    private EasyButtonType easyButtonType;

    public EasyButton(Context context) {
        super(context);
        init();
    }

    public EasyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EasyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public EasyButtonType getEasyButtonType() {
        return easyButtonType;
    }

    public void setEasyButtonType(EasyButtonType easyButtonType) {
        this.easyButtonType = easyButtonType;
    }

    /**
     * 初始化工作
     */
    private void init() {
        setBackgroundResource(R.drawable.press_rect_selector);
    }

    /**
     * 按钮类型
     */
    public static enum EasyButtonType {
        PositiveBtn, NegativeBtn, NeutralBtn
    }


}
