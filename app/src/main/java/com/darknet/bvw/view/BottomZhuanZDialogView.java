package com.darknet.bvw.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darknet.bvw.R;

public abstract class BottomZhuanZDialogView extends Dialog implements View.OnClickListener {

    private Activity activity;
    private RelativeLayout btnPickBySelect, btnPickByTake;

private TextView zhulianView;
private TextView brcView;

    private String moneyTypeVal;

    public BottomZhuanZDialogView(Activity activity,String moneyType) {
        super(activity, R.style.ActionSheetDialogStyle);
        this.activity = activity;
        this.moneyTypeVal = moneyType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zz_select_layout);

        btnPickBySelect = (RelativeLayout) findViewById(R.id.btnPickBySelect);
        btnPickByTake = (RelativeLayout) findViewById(R.id.btnPickByTake);

        zhulianView = (TextView)findViewById(R.id.zz_zhulian_vew);
        brcView = (TextView)findViewById(R.id.zz_brc_vew);


        if(moneyTypeVal.equalsIgnoreCase("BTC")){
            zhulianView.setText(activity.getResources().getString(R.string.zz_select_dialog_btc_one));
            brcView.setText(activity.getResources().getString(R.string.zz_select_dialog_bvw_two));
        }else if(moneyTypeVal.equalsIgnoreCase("ETH")){
            zhulianView.setText(activity.getResources().getString(R.string.zz_select_dialog_eth_one));
            brcView.setText(activity.getResources().getString(R.string.zz_select_dialog_bvw_two));
        }else if(moneyTypeVal.equalsIgnoreCase("USDT")){
            zhulianView.setText(activity.getResources().getString(R.string.zz_select_dialog_usdt_one));
            brcView.setText(activity.getResources().getString(R.string.zz_select_dialog_bvw_two));
        }

        btnPickBySelect.setOnClickListener(this);
        btnPickByTake.setOnClickListener(this);

        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPickBySelect:
                btnPickBySelect();
                this.cancel();
                break;
            case R.id.btnPickByTake:
                btnPickByTake();
                this.cancel();
                break;
        }
    }

    public abstract void btnPickBySelect();

    public abstract void btnPickByTake();

}