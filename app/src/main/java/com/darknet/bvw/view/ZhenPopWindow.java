package com.darknet.bvw.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.darknet.bvw.R;

public class ZhenPopWindow extends PopupWindow {


    private View view;
//    private TextView delete;
//    private TextView change;
    private View line;

    /**
     * @param context
     * @param listener
     */
    public ZhenPopWindow(Context context, final ClickListener listener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_select_layout, null);
//        delete = (TextView) view.findViewById(R.id.delete);
//        change = (TextView) view.findViewById(R.id.change);
//        line = view.findViewById(R.id.line);
//        if (!isBusiness) {
//            change.setVisibility(View.GONE);
//            line.setVisibility(View.GONE);
//        } else {
//            change.setVisibility(View.VISIBLE);
//            line.setVisibility(View.VISIBLE);
//        }
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.deleteListener();
//                if (isShowing()) {
//                    dismiss();
//                }
//            }
//        });
//        change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.changeListener();
//                if (isShowing()) {
//                    dismiss();
//                }
//            }
//        });
        this.setContentView(view);
        this.setFocusable(true);//必须写

        this.setTouchable(true);
        this.setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
        //设置宽与高
//        setWidth(SizeUtils.dp2px(115));
//        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public interface ClickListener {
        void deleteListener();

        void changeListener();
    }



    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

}
