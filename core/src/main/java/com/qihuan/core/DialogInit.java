package com.qihuan.core;

import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qihuan.adapter.EDSimpleAdapter;
import com.qihuan.adapter.ViewHolder;

/**
 * Created by qihuan on 16/4/18.
 */
public class DialogInit {
    public static void init(EasyDialog dialog) {
        final EasyDialog.Builder builder = dialog.getBuilder();

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

        //list
        dialog.listView = findView(dialog, R.id.ed_list);

        //custom
        dialog.customViewFrame = findView(dialog, R.id.ed_custom_frame);

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
        if (null != dialog.content) {
            if (!TextUtils.isEmpty(builder.content)) {
                dialog.content.setText(builder.content);
                dialog.content.setTextColor(builder.contentColor);
                dialog.content.setLineSpacing(0F, builder.contentLineSpacingMultiplier);
                dialog.content.setGravity(builder.contentGravity);
            } else {
                dialog.content.setVisibility(View.GONE);
            }
        }

        //set up list
        if (dialog.listView != null && (builder.items != null && builder.items.size() > 0 || builder.adapter != null)) {
            dialog.listView.setSelector(builder.itemsBackgroundRes);
            dialog.listView.setDivider(EasyUtil.getDrawable(dialog.getContext(), builder.dividerColorRes));
            dialog.listView.setDividerHeight(builder.dividerHeight);
            //使用的是item
            if (null == builder.adapter) {
                EDSimpleAdapter<CharSequence> adapter = new EDSimpleAdapter<CharSequence>(builder.items, R.layout.ed_list_item) {
                    @Override
                    protected void bindView(View convertView, int position, ViewHolder viewHolder) {
                        TextView itemTextView = viewHolder.getTextView(R.id.ed_item_text);
                        itemTextView.setText(getItem(position));
                        itemTextView.setTextColor(builder.itemColor);
                        itemTextView.setGravity(builder.itemsGravity);
                        itemTextView.setTextSize(builder.itemsTextSize);
                        itemTextView.getLayoutParams().height = builder.itemsHeight;
                    }
                };

                builder.adapter = adapter;
            }

            dialog.setupListcallback();
        }

        //set up customView
        if (null != dialog.customViewFrame && null != builder.customView) {
            View innerView = builder.customView;
            if (innerView.getParent() != null) {
                ((ViewGroup) innerView.getParent()).removeView(innerView);
            }
            if (builder.wrapCustomViewInScroll) {
                /* Apply the frame padding to the content, this allows the ScrollView to draw it's
                   over scroll glow without clipping */
                final Resources r = dialog.getContext().getResources();
                final int framePadding = r.getDimensionPixelSize(R.dimen.ed_dialog_content_padding);
                final ScrollView sv = new ScrollView(dialog.getContext());
                sv.setClipToPadding(false);
                if (innerView instanceof EditText) {
                    // Setting padding to an EditText causes visual errors, set it to the parent instead
                    sv.setPadding(framePadding, framePadding, framePadding, framePadding);
                } else {
                    // Setting padding to scroll view pushes the scroll bars out, don't do it if not necessary (like above)
                    sv.setPadding(0, framePadding, 0, framePadding);
                    innerView.setPadding(framePadding, 0, framePadding, 0);
                }
                sv.addView(innerView, new ScrollView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                //TODO 这里如果启用了ScrollView，默认高度为320dp
                dialog.customViewFrame.addView(sv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        EasyUtil.dp2px(320)));
            } else {
                dialog.customViewFrame.addView(innerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }

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
            findView(dialog, R.id.ed_content_sp).setVisibility(View.GONE);
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
            return R.layout.easy_dialog_custom;
        } else if (builder.items != null && builder.items.size() > 0 || builder.adapter != null) {
            //列表View
            return R.layout.easy_dialog_list;
        }
        //默认view
        return R.layout.easy_dialog_basic;
    }
}
