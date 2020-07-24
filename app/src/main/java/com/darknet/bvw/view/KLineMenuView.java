package com.darknet.bvw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.commonlib.util.ResourceUtils;
import com.darknet.bvw.config.constant.KLineTypeEnum;

import java.util.List;

import androidx.annotation.Nullable;

public class KLineMenuView extends LinearLayout {

    private View tabView;

    private LayoutParams tabParams;

    private int selIndex = -1;

    private Context context;

    private int defaultIndex = 2;

    private List<KLineTypeEnum> dataList;

    public KLineMenuView(Context context) {
        this(context, null);
    }

    public KLineMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.setOrientation(HORIZONTAL);
    }

    /**
     * 设置数据
     *
     * @param dataList
     */
    public void setData(List<KLineTypeEnum> dataList) {
        this.dataList = dataList;
        for (int i = 0; i < dataList.size(); i++) {
            KLineTypeEnum kLineTypeEnum = dataList.get(i);
            addTab(kLineTypeEnum, i);
        }
        updateTabSelection(defaultIndex, dataList.get(defaultIndex));
    }

    /**
     * 添加TAb
     *
     * @param kLineTypeEnum
     * @param index
     */
    private void addTab(KLineTypeEnum kLineTypeEnum, int index) {
        tabView = View.inflate(getContext(), R.layout.content_k_line_menu_item, null);
        TextView tv_content = tabView.findViewById(R.id.tv_content);
        tv_content.setText(getName(kLineTypeEnum));
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = indexOfChild(v);
                updateTabSelection(index, kLineTypeEnum);
            }
        });

        tabParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        this.addView(tabView, index, tabParams);
    }

    private void updateTabSelection(int index, KLineTypeEnum kLineTypeEnum) {
        if (index == selIndex && selIndex != 5)
            return;
        this.selIndex = index;
        int mTabCount = getChildCount();
        for (int i = 0; i < mTabCount; i++) {
            View tabView = getChildAt(i);
            View indicatorView = tabView.findViewById(R.id.indicator);
            ImageView imageView = tabView.findViewById(R.id.iv_more);
            TextView tv_content = tabView.findViewById(R.id.tv_content);
            tv_content.setText(getName(dataList.get(i)));
            final boolean isSelect = i == index;
            if (isSelect) {
                if (dataList.get(i).getType() == KLineTypeEnum.more.getType()) {
                    indicatorView.setVisibility(INVISIBLE);
                    imageView.setVisibility(VISIBLE);
                    imageView.setImageResource(R.mipmap.more_select);
                } else {
                    indicatorView.setVisibility(VISIBLE);
                    imageView.setVisibility(INVISIBLE);
                }
            } else {
                indicatorView.setVisibility(INVISIBLE);
                if (dataList.get(i).getType() == KLineTypeEnum.more.getType()) {
                    imageView.setVisibility(VISIBLE);
                    imageView.setImageResource(R.mipmap.more_unselect);
                } else {
                    imageView.setVisibility(INVISIBLE);
                }
            }

        }
        if (mOnTabSelectListener != null) mOnTabSelectListener.onTabClick(index, kLineTypeEnum);
    }

    private OnTabSelectListener mOnTabSelectListener;

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.mOnTabSelectListener = onTabSelectListener;
    }

    public interface OnTabSelectListener {
        void onTabClick(int position, KLineTypeEnum kLineTypeEnum);
    }

    private String getName(KLineTypeEnum kLineTypeEnum) {
        switch (kLineTypeEnum) {
            case LINE:
                return ResourceUtils.getString(context, R.string.k_time_line);
            case min1:
                return ResourceUtils.getString(context, R.string.k_time_1);
            case min5:
                return ResourceUtils.getString(context, R.string.k_time_5);
            case min10:
                return ResourceUtils.getString(context, R.string.k_time_10);
            case min15:
                return ResourceUtils.getString(context, R.string.k_time_15);
            case min30:
                return ResourceUtils.getString(context, R.string.k_time_30);
            case hour1:
                return ResourceUtils.getString(context, R.string.k_time_1h);
            case daily:
                return ResourceUtils.getString(context, R.string.k_time_1d);
            case week:
                return ResourceUtils.getString(context, R.string.k_time_1w);
            case month:
                return ResourceUtils.getString(context, R.string.k_time_1m);
            default:
                return ResourceUtils.getString(context, R.string.k_time_more);
        }
    }


    public void selectMoreView(KLineTypeEnum kLineTypeEnum) {
        View childAt = getChildAt(5);
        View indicatorView = childAt.findViewById(R.id.indicator);
        ImageView imageView = childAt.findViewById(R.id.iv_more);
        TextView tv_content = tabView.findViewById(R.id.tv_content);
        tv_content.setText(getName(kLineTypeEnum));
        indicatorView.setVisibility(VISIBLE);
        imageView.setImageResource(R.mipmap.more_select);
    }

    public void setDefaultIndex(int index) {
        this.defaultIndex = index;
    }

}
