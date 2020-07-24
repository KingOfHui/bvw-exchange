package com.darknet.bvw.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.commonlib.util.ResourceUtils;
import com.darknet.bvw.commonlib.util.StringUtil;
import com.darknet.bvw.model.PerformanceAtom;
import com.darknet.bvw.model.PerformanceDto;
import com.darknet.bvw.util.TimeUtil;
import com.darknet.bvw.util.language.SPUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class CommunityLeaderAdapter extends BaseQuickAdapter<PerformanceAtom, BaseViewHolder> {
    private Context context;

    private PerformanceDto performanceDto;

    public CommunityLeaderAdapter(Context context, @Nullable List<PerformanceAtom> data, PerformanceDto performanceDto) {
        super(R.layout.recycler_item_community_leader, data);
        this.context = context;
        this.performanceDto = performanceDto;
    }

    @Override
    protected void convert(BaseViewHolder helper, PerformanceAtom item) {
        try {
            int type = SPUtil.getInstance(context).getSelectLanguage();

            if (type == 1) {
                helper.setBackgroundRes(R.id.iv_reward_day, R.mipmap.reward_day_cn);
            } else {
                helper.setBackgroundRes(R.id.iv_reward_day, R.mipmap.reward_day);
            }
            if (isLastPosition(helper)) {
                helper.setVisible(R.id.iv_reward_day, false);

                if (StringUtil.isEmpty(performanceDto.getSnapshort_hold_amount())) {
                    helper.setText(R.id.tv_num, "0");
                    helper.setTextColor(R.id.tv_num, ResourceUtils.getColor(context, R.color.white));
                    helper.setTextColor(R.id.tv_usdt, ResourceUtils.getColor(context, R.color.white));
                } else {
                    if (isFuShu(performanceDto.getSnapshort_hold_amount())) {
                        String substring = performanceDto.getSnapshort_hold_amount().substring(1);
                        helper.setText(R.id.tv_num, substring.split("\\.")[0]);
                        helper.setTextColor(R.id.tv_num, ResourceUtils.getColor(context, R.color.color_FC6767));
                        helper.setTextColor(R.id.tv_usdt, ResourceUtils.getColor(context, R.color.color_FC6767));
                    } else {
                        helper.setText(R.id.tv_num, performanceDto.getSnapshort_hold_amount().split("\\.")[0]);
                        helper.setTextColor(R.id.tv_num, ResourceUtils.getColor(context, R.color.white));
                        helper.setTextColor(R.id.tv_usdt, ResourceUtils.getColor(context, R.color.white));
                    }
                }

                String substring = TimeUtil.getStringToDate(item.getDate(), "yyyy-MM-dd").substring(5);
                SpannableStringBuilder style = new SpannableStringBuilder(context.getString(R.string.fast_photo, substring));
                style.setSpan(new ForegroundColorSpan(ResourceUtils.getColor(context, R.color.color_01FCDA)), substring.length(), style.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                helper.setText(R.id.tv_date, style)
                        .setText(R.id.tv_price, context.getString(R.string.unit_price, performanceDto.getSnapshort_bvw_price()));

                helper.setTextColor(R.id.tv_change_rate, ResourceUtils.getColor(context, R.color.color_01FCDA));
                helper.setText(R.id.tv_change_rate, "+0%");

                if (StringUtil.isEmpty(performanceDto.getSnapshort_hold_amount_bvw())) {
                    helper.setText(R.id.tv_bvw_num, "0");
                    helper.setTextColor(R.id.tv_bvw_num, ResourceUtils.getColor(context, R.color.color_01FCDA));
                } else {
                    if (isFuShu(performanceDto.getSnapshort_hold_amount_bvw())) {
                        String subs = performanceDto.getSnapshort_hold_amount_bvw().substring(1);
                        helper.setText(R.id.tv_bvw_num, subs.split("\\.")[0]);
                        helper.setTextColor(R.id.tv_bvw_num, ResourceUtils.getColor(context, R.color.color_FC6767));
                    } else {
                        helper.setText(R.id.tv_bvw_num, performanceDto.getSnapshort_hold_amount_bvw().split("\\.")[0]);
                        helper.setTextColor(R.id.tv_bvw_num, ResourceUtils.getColor(context, R.color.color_01FCDA));
                    }
                }
                return;
            }

            if (StringUtil.isEmpty(item.getPerformance())) {
                helper.setText(R.id.tv_num, "0");
                helper.setTextColor(R.id.tv_num, ResourceUtils.getColor(context, R.color.white));
                helper.setTextColor(R.id.tv_usdt, ResourceUtils.getColor(context, R.color.white));
            } else {
                if (isFuShu(item.getPerformance())) {
                    String substring = item.getPerformance().substring(1);
                    helper.setText(R.id.tv_num, substring.split("\\.")[0]);
                    helper.setTextColor(R.id.tv_num, ResourceUtils.getColor(context, R.color.color_FC6767));
                    helper.setTextColor(R.id.tv_usdt, ResourceUtils.getColor(context, R.color.color_FC6767));
                } else {
                    helper.setText(R.id.tv_num, item.getPerformance().split("\\.")[0]);
                    helper.setTextColor(R.id.tv_num, ResourceUtils.getColor(context, R.color.white));
                    helper.setTextColor(R.id.tv_usdt, ResourceUtils.getColor(context, R.color.white));
                }
            }

            if (StringUtil.isEmpty(item.getPerformance_bvw())) {
                helper.setText(R.id.tv_bvw_num, "0");
                helper.setTextColor(R.id.tv_bvw_num, ResourceUtils.getColor(context, R.color.color_01FCDA));
                helper.setTextColor(R.id.tv_bvw, ResourceUtils.getColor(context, R.color.color_01FCDA));
            } else {
                if (isFuShu(item.getPerformance_bvw())) {
                    String substring = item.getPerformance_bvw().substring(1);
                    helper.setText(R.id.tv_bvw_num, substring.split("\\.")[0]);
                    helper.setTextColor(R.id.tv_bvw_num, ResourceUtils.getColor(context, R.color.color_FC6767));
                    helper.setTextColor(R.id.tv_bvw, ResourceUtils.getColor(context, R.color.color_FC6767));
                } else {
                    helper.setText(R.id.tv_bvw_num, item.getPerformance_bvw().split("\\.")[0]);
                    helper.setTextColor(R.id.tv_bvw_num, ResourceUtils.getColor(context, R.color.color_01FCDA));
                    helper.setTextColor(R.id.tv_bvw, ResourceUtils.getColor(context, R.color.color_01FCDA));
                }
            }

            helper.setText(R.id.tv_date, TimeUtil.getStringToDate(item.getDate(), "yyyy-MM-dd"));
            helper.setText(R.id.tv_price, context.getString(R.string.unit_price, item.getBvwUsdPrice()));


            if (isFuShu(item.getBvwUsdRate() + "")) {
                helper.setTextColor(R.id.tv_change_rate, ResourceUtils.getColor(context, R.color.color_FC6767));
                helper.setText(R.id.tv_change_rate, StringUtil.getNumberDecimal(item.getBvwUsdRate(), 2));
            } else {
                helper.setTextColor(R.id.tv_change_rate, ResourceUtils.getColor(context, R.color.color_01FCDA));
                helper.setText(R.id.tv_change_rate, "+" + StringUtil.getNumberDecimal(item.getBvwUsdRate(), 2));
            }
            if (StringUtil.isEmpty(item.getBonus()) || isFuShu(item.getBonus()) || equalO(item.getBonus())) {
                helper.setVisible(R.id.iv_reward_day, false);
            } else {
                helper.setVisible(R.id.iv_reward_day, true);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private boolean isLastPosition(BaseViewHolder helper) {
        return helper.getAdapterPosition() == getData().size() - 1;
    }

    private boolean isFuShu(String value) {
        return value.contains("-");
    }

    private boolean equalO(String value) {
        return "0".equals(value);

    }
}
