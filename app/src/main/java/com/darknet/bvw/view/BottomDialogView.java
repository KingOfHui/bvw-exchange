package com.darknet.bvw.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.darknet.bvw.R;

public class BottomDialogView {
    private View contentView;
    private Dialog dialog;

    private BottomDialogView() {
    }

    public View getContentView() {
        return this.contentView;
    }

    public void setCancelable(boolean cancelable) {
        this.dialog.setCancelable(cancelable);
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        this.dialog.setCanceledOnTouchOutside(cancel);
    }

    public void show() {
        this.dialog.show();
    }

    public void dismiss() {
        this.dialog.dismiss();
    }

    public static class Builder {
        private LinearLayout bottomLayout;
        private View contentView;
        private Dialog dialog;
        private boolean hasAnimation = true;
        private Context context;
        private int layoutId;

        public Builder(Context context) {
            this.context = context;
            this.bottomLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.layout_bottomdialog, (ViewGroup)null);
        }

        public BottomDialogView.Builder setContentView(int layoutId) {
            this.layoutId = layoutId;
            this.contentView = LayoutInflater.from(this.context).inflate(this.layoutId, this.bottomLayout);
            return this;
        }

        public BottomDialogView.Builder setContentView(View contentView) {
            this.contentView = contentView;
            this.bottomLayout.addView(contentView);
            return this;
        }

        public BottomDialogView.Builder setHasAnimation(boolean hasAnimation) {
            this.hasAnimation = hasAnimation;
            return this;
        }

        public BottomDialogView create() {
            BottomDialogView bottomDialog = new BottomDialogView();
            this.dialog = new Dialog(this.context, R.style.BottomDialog);
            this.contentView.measure(0, 0);
            this.bottomLayout.measure(0, 0);
            this.dialog.setContentView(this.bottomLayout);
            Window dialogWindow = this.dialog.getWindow();
            dialogWindow.setGravity(80);
            if (this.hasAnimation) {
                dialogWindow.setWindowAnimations(R.style.DialogAnimation);
            }

            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            lp.width = this.context.getResources().getDisplayMetrics().widthPixels;
            lp.height = this.bottomLayout.getMeasuredHeight();
            Log.i("BottomDialog", "width = " + lp.width);
            Log.i("BottomDialog", "height = " + lp.height);
            lp.alpha = 9.0F;
            dialogWindow.setAttributes(lp);
            this.dialog.show();
            bottomDialog.dialog = this.dialog;
            bottomDialog.contentView = this.contentView;
            return bottomDialog;
        }
    }
}
