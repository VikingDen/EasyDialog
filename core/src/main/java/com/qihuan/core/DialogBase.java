package com.qihuan.core;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StyleRes;
import android.view.View;

/**
 * Created by qihuan on 16/4/18.
 */
public class DialogBase extends Dialog implements DialogInterface.OnShowListener {

    protected EasyRootLayout rootView;
    private OnShowListener mShowListener;


    public DialogBase(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    public View findViewById(int id) {
        return rootView.findViewById(id);
    }


    @Override
    public void onShow(DialogInterface dialog) {
        if (mShowListener != null)
            mShowListener.onShow(dialog);
    }

    protected final void setOnShowListenerInternal() {
        super.setOnShowListener(this);
    }

    protected final void setViewInternal(View view) {
        super.setContentView(view);
    }
}
