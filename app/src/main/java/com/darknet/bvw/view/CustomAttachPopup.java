package com.darknet.bvw.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.util.language.SPUtil;
import com.lxj.xpopup.core.AttachPopupView;

import androidx.annotation.NonNull;

public class CustomAttachPopup extends AttachPopupView {

    private OnClickListener onClickListener;

    private Context context;
    public CustomAttachPopup(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tv14 = findViewById(R.id.tv_14);
        TextView tv30 = findViewById(R.id.tv_30);
        TextView tv45 = findViewById(R.id.tv_45);
        TextView tv60 = findViewById(R.id.tv_60);
        int type = SPUtil.getInstance(context).getSelectLanguage();
        if (type == 1) {
        } else {
            tv14.setTextSize(14);
            tv30.setTextSize(14);
            tv45.setTextSize(14);
            tv60.setTextSize(14);
        }
        tv14.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (onClickListener != null) {
                                            onClickListener.click(tv14);
                                        }
                                        dismiss(); // 关闭弹窗
                                    }
                                }

        );
        tv30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.click(tv30);
                }
                dismiss(); // 关闭弹窗
            }
        });
        tv45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.click(tv45);
                }
                dismiss(); // 关闭弹窗
            }
        });
        tv60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.click(tv60);
                }
                dismiss(); // 关闭弹窗
            }
        });
    }

    @Override
    protected int getImplLayoutId() {


        return R.layout.dialog_select_date;
    }

    @Override
    protected Drawable getPopupBackground() {
        return getResources().getDrawable(R.color.transparent1);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public interface OnClickListener {
        void click(View v);
    }
}
