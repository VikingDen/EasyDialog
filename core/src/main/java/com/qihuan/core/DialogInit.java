package com.qihuan.core;

import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by qihuan on 16/4/18.
 */
public class DialogInit {
    public static void init(EasyDialog dialog) {
        EasyDialog.Builder builder = dialog.getBuilder();

        //dialog
        dialog.setCancelable(builder.cancelable);
        dialog.setCanceledOnTouchOutside(builder.canceledOnTouchOutside);

        //btn
        dialog.title = findView(dialog, R.id.ed_title);
        dialog.content = findView(dialog, R.id.ed_content);
        dialog.neutralButton = findView(dialog, R.id.ed_btn_neutral);
        View neutralSp = findView(dialog, R.id.ed_btn_sp_neutral);
        dialog.negativeButton = findView(dialog, R.id.ed_btn_negative);
        View negativeSp = findView(dialog, R.id.ed_btn_sp_negative);
        dialog.positiveButton = findView(dialog, R.id.ed_btn_positive);

        //set up title
        if (!TextUtils.isEmpty(builder.title)) {
            dialog.title.setText(builder.title);
            dialog.title.setTextColor(builder.titleColor);
            dialog.title.setGravity(builder.titleGravity);
        } else {
            dialog.title.setVisibility(View.GONE);
            findView(dialog, R.id.ed_title_sp).setVisibility(View.GONE);
        }

        //set up content
        if (!TextUtils.isEmpty(builder.content)) {
            dialog.content.setText(builder.content);
            dialog.content.setTextColor(builder.contentColor);
            dialog.content.setLineSpacing(0F, builder.contentLineSpacingMultiplier);
            dialog.content.setGravity(builder.contentGravity);
        } else {
            dialog.content.setVisibility(View.GONE);
        }

        //set up button
        dialog.positiveButton.setVisibility(!TextUtils.isEmpty(builder.positiveText) ? View.VISIBLE : View.GONE);
        dialog.positiveButton.setText(builder.positiveText);
        dialog.positiveButton.setTextColor(builder.positiveColor);
        dialog.positiveButton.setEasyButtonType(EasyButton.EasyButtonType.PositiveBtn);
        dialog.positiveButton.setOnClickListener(dialog);
        dialog.neutralButton.setVisibility(!TextUtils.isEmpty(builder.neutralText) ? View.VISIBLE : View.GONE);
        neutralSp.setVisibility(!TextUtils.isEmpty(builder.neutralText) ? View.VISIBLE : View.GONE);
        dialog.neutralButton.setText(builder.neutralText);
        dialog.neutralButton.setTextColor(builder.neutralColor);
        dialog.neutralButton.setEasyButtonType(EasyButton.EasyButtonType.NeutralBtn);
        dialog.neutralButton.setOnClickListener(dialog);
        dialog.negativeButton.setVisibility(!TextUtils.isEmpty(builder.negativeText) ? View.VISIBLE : View.GONE);
        negativeSp.setVisibility(!TextUtils.isEmpty(builder.negativeText) ? View.VISIBLE : View.GONE);
        dialog.negativeButton.setText(builder.negativeText);
        dialog.negativeButton.setTextColor(builder.negativeColor);
        dialog.negativeButton.setEasyButtonType(EasyButton.EasyButtonType.NegativeBtn);
        dialog.negativeButton.setOnClickListener(dialog);

        if (TextUtils.isEmpty(builder.positiveText) && TextUtils.isEmpty(builder.negativeText) && TextUtils.isEmpty(builder.neutralText)) {
            findView(dialog, R.id.ed_btn_layout).setVisibility(View.GONE);
        }

        //set up dialog
        if (builder.showListener != null)
            dialog.setOnShowListener(builder.showListener);
        if (builder.cancelListener != null)
            dialog.setOnCancelListener(builder.cancelListener);
        if (builder.dismissListener != null)
            dialog.setOnDismissListener(builder.dismissListener);
        if (builder.keyListener != null)
            dialog.setOnKeyListener(builder.keyListener);

        dialog.setViewInternal(dialog.rootView);
        dialog.setOnShowListenerInternal();
    }

    private static <T extends View> T findView(EasyDialog dialog, @IdRes int id) {
        return (T) dialog.rootView.findViewById(id);
    }

    public static int getInflateLayout(EasyDialog.Builder builder) {
        if (builder.customView != null) {
            //自定义的coustomView
        } else if (builder.items != null && builder.items.length > 0 || builder.adapter != null) {
            //列表View
        }
        //默认view
        return R.layout.easy_dialog_basic;
    }
}
