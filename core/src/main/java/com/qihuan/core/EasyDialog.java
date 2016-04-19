package com.qihuan.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by qihuan on 16/4/18.
 */
public class EasyDialog extends DialogBase implements View.OnClickListener {

    private final Builder mBuilder;
    //protected final Builder mBuilder;
    protected ListView listView;
    protected TextView title;
    protected FrameLayout customViewFrame;
    protected TextView content;

    protected EasyButton positiveButton;
    protected EasyButton neutralButton;
    protected EasyButton negativeButton;


    @SuppressLint("InflateParams")
    protected EasyDialog(Builder builder) {
        super(builder.context, R.style.ED_Light);
        mBuilder = builder;
        final LayoutInflater inflater = LayoutInflater.from(builder.context);
        rootView = (EasyRootLayout) inflater.inflate(DialogInit.getInflateLayout(builder), null);
        DialogInit.init(this);
    }

    public final Builder getBuilder() {
        return mBuilder;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof EasyButton) {
            //按钮点击事件
            EasyButton button = (EasyButton) v;
            switch (button.getEasyButtonType()) {
                case PositiveBtn:
                    if (null != mBuilder.onPositiveCallback) {
                        mBuilder.onPositiveCallback.onClick(this, EasyButton.EasyButtonType.PositiveBtn);
                    }
                    break;
                case NegativeBtn:
                    if (null != mBuilder.onNegativeCallback) {
                        mBuilder.onNegativeCallback.onClick(this, EasyButton.EasyButtonType.NegativeBtn);
                    }
                    break;
                case NeutralBtn:
                    if (null != mBuilder.onNeutralCallback) {
                        mBuilder.onNeutralCallback.onClick(this, EasyButton.EasyButtonType.NeutralBtn);
                    }
                    break;
            }

            if (null != mBuilder.onAnyCallback) {
                mBuilder.onAnyCallback.onClick(this, button.getEasyButtonType());
            }

            if (mBuilder.autoDismiss) {
                dismiss();
            }
        }

    }


    public final EasyButton getButton(@NonNull EasyButton.EasyButtonType which) {
        switch (which) {
            default:
                return positiveButton;
            case NeutralBtn:
                return neutralButton;
            case NegativeBtn:
                return negativeButton;
        }
    }

    @Nullable
    public final View getCustomView() {
        return mBuilder.customView;
    }


    /**
     * An alternate way to define a single callback.
     * 单个按钮
     */
    public interface SingleButtonCallback {

        void onClick(@NonNull EasyDialog dialog, @NonNull EasyButton.EasyButtonType which);
    }

    /**
     * list 按钮
     */
    public interface ListCallback {
        void onItemClick(@NonNull EasyDialog dialog, @NonNull int position);
    }


    /**
     * 构建类
     */
    public static class Builder {

        protected final Context context;
        //标题相关
        protected CharSequence title;
        protected int titleColor = -1;
        protected int titleGravity = Gravity.CENTER;
        //内容相关
        protected CharSequence content;
        protected int contentColor = -1;
        protected float contentLineSpacingMultiplier = 1.2f;
        protected int contentGravity = Gravity.CENTER;
        protected View customView;
        //list相关
        protected CharSequence[] items;
        protected ListCallback onListCallback;
        protected int dividerColor = -1;
        protected int itemColor = -1;
        //按钮相关
        protected CharSequence positiveText;
        protected CharSequence neutralText;
        protected CharSequence negativeText;
        protected ColorStateList positiveColor;
        protected ColorStateList negativeColor;
        protected ColorStateList neutralColor;
        protected SingleButtonCallback onPositiveCallback;
        protected SingleButtonCallback onNegativeCallback;
        protected SingleButtonCallback onNeutralCallback;
        protected SingleButtonCallback onAnyCallback;

        //dialog 相关
        protected boolean autoDismiss = true;
        protected OnDismissListener dismissListener;
        protected OnCancelListener cancelListener;
        protected OnKeyListener keyListener;
        protected OnShowListener showListener;
        //color 配置相关
        protected boolean titleColorSet = false;
        protected boolean contentColorSet = false;
        protected boolean itemColorSet = false;
        protected boolean positiveColorSet = false;
        protected boolean neutralColorSet = false;
        protected boolean negativeColorSet = false;
        protected boolean dividerColorSet = false;
        protected boolean cancelable = true;
        protected boolean canceledOnTouchOutside = true;
        //TODO
        protected BaseAdapter adapter;


        public Builder(Context context) {
            this.context = context;
            int fontBlue = EasyUtil.getColor(context, R.color.font_blue);
            int fontNormal = EasyUtil.getColor(context, R.color.font_normal);
            int fontBlack = EasyUtil.getColor(context, R.color.font_black);

            ColorStateList wrapedFontBlue = EasyUtil.wrapColor(fontBlue);
            ColorStateList wrapedFontNormal = EasyUtil.wrapColor(fontNormal);
            positiveColor = wrapedFontBlue;
            negativeColor = wrapedFontNormal;
            neutralColor = wrapedFontBlue;
            contentColor = fontNormal;
            titleColor = fontBlack;
            itemColor = fontNormal;
            dividerColor = EasyUtil.getColor(context, R.color.pers20_black);
        }

        /*     标题相关    */
        public Builder title(@StringRes int titleRes) {
            title(this.context.getText(titleRes));
            return this;
        }

        public Builder title(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder titleGravity(@NonNull int gravity) {
            this.titleGravity = gravity;
            return this;
        }

        public Builder titleColor(@ColorInt int color) {
            this.titleColor = color;
            this.titleColorSet = true;
            return this;
        }

        public Builder titleColorRes(@ColorRes int colorRes) {
            return titleColor(EasyUtil.getColor(this.context, colorRes));
        }

        /*       * 内容相关        * */

        public Builder content(@StringRes int contentRes) {
            content(this.context.getText(contentRes));
            return this;
        }

        public Builder content(@NonNull CharSequence content) {
            if (this.customView != null)
                throw new IllegalStateException("You cannot set content() when you're using a custom view.");
            this.content = content;
            return this;
        }

        public Builder content(@StringRes int contentRes, Object... formatArgs) {
            content(this.context.getString(contentRes, formatArgs));
            return this;
        }

        public Builder contentColor(@ColorInt int color) {
            this.contentColor = color;
            this.contentColorSet = true;
            return this;
        }

        public Builder contentColorRes(@ColorRes int colorRes) {
            contentColor(EasyUtil.getColor(this.context, colorRes));
            return this;
        }

        public Builder contentGravity(@NonNull int gravity) {
            this.contentGravity = gravity;
            return this;
        }

        public Builder contentLineSpacing(float multiplier) {
            this.contentLineSpacingMultiplier = multiplier;
            return this;
        }

        /* 按钮相关 */
        public Builder positiveText(@StringRes int postiveRes) {
            if (postiveRes == 0) return this;
            positiveText(this.context.getText(postiveRes));
            return this;
        }

        public Builder positiveText(@NonNull CharSequence message) {
            this.positiveText = message;
            return this;
        }

        public Builder positiveColor(@ColorInt int color) {
            return positiveColor(EasyUtil.wrapColor(color));
        }

        public Builder positiveColorRes(@ColorRes int colorRes) {
            return positiveColor(EasyUtil.wrapColor(EasyUtil.getColor(context, colorRes)));
        }

        public Builder positiveColor(@NonNull ColorStateList colorStateList) {
            this.positiveColor = colorStateList;
            this.positiveColorSet = true;
            return this;
        }

        public Builder neutralText(@StringRes int neutralRes) {
            if (neutralRes == 0) return this;
            return neutralText(this.context.getText(neutralRes));
        }

        public Builder neutralText(@NonNull CharSequence message) {
            this.neutralText = message;
            return this;
        }

        public Builder negativeColor(@ColorInt int color) {
            return negativeColor(EasyUtil.wrapColor(color));
        }

        public Builder negativeColorRes(@ColorRes int colorRes) {
            return negativeColor(EasyUtil.wrapColor(EasyUtil.getColor(this.context, colorRes)));
        }

        public Builder negativeColor(@NonNull ColorStateList colorStateList) {
            this.negativeColor = colorStateList;
            this.negativeColorSet = true;
            return this;
        }

        public Builder negativeText(@StringRes int negativeRes) {
            if (negativeRes == 0) return this;
            return negativeText(this.context.getText(negativeRes));
        }

        public Builder negativeText(@NonNull CharSequence message) {
            this.negativeText = message;
            return this;
        }

        public Builder neutralColor(@ColorInt int color) {
            return neutralColor(EasyUtil.wrapColor(color));
        }

        public Builder neutralColorRes(@ColorRes int colorRes) {
            return neutralColor(EasyUtil.wrapColor(EasyUtil.getColor(this.context, colorRes)));
        }

        public Builder neutralColor(@NonNull ColorStateList colorStateList) {
            this.neutralColor = colorStateList;
            this.neutralColorSet = true;
            return this;
        }

        public Builder onPositive(@NonNull SingleButtonCallback callback) {
            this.onPositiveCallback = callback;
            return this;
        }

        public Builder onNegative(@NonNull SingleButtonCallback callback) {
            this.onNegativeCallback = callback;
            return this;
        }

        public Builder onNeutral(@NonNull SingleButtonCallback callback) {
            this.onNeutralCallback = callback;
            return this;
        }

        public Builder onAny(@NonNull SingleButtonCallback callback) {
            this.onAnyCallback = callback;
            return this;
        }

        /* dialog 相关 */

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            this.canceledOnTouchOutside = cancelable;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        /**
         * 是否在触发点击事件之后自动隐藏，默认是true
         *
         * @param dismiss
         * @return
         */
        public Builder autoDismiss(boolean dismiss) {
            this.autoDismiss = dismiss;
            return this;
        }

        public Builder showListener(@NonNull OnShowListener listener) {
            this.showListener = listener;
            return this;
        }

        public Builder dismissListener(@NonNull OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }

        public Builder cancelListener(@NonNull OnCancelListener listener) {
            this.cancelListener = listener;
            return this;
        }

        public Builder keyListener(@NonNull OnKeyListener listener) {
            this.keyListener = listener;
            return this;
        }

        @UiThread
        public EasyDialog build() {
            return new EasyDialog(this);
        }

        @UiThread
        public EasyDialog show() {
            EasyDialog dialog = build();
            dialog.show();
            return dialog;
        }

    }

}
