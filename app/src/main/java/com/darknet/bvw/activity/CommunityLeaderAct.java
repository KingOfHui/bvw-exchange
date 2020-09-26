package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.CommunityLeaderAdapter;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.commonlib.util.ResourceUtils;
import com.darknet.bvw.commonlib.widget.ProgressLayout;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.config.constant.PerformanceDateEnum;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.PerformanceAtom;
import com.darknet.bvw.model.PerformanceDto;
import com.darknet.bvw.util.AppUtil;
import com.darknet.bvw.util.TimeUtil;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.language.SPUtil;
import com.darknet.bvw.view.CenterPopu;
import com.darknet.bvw.view.CustomAttachPopup;
import com.darknet.bvw.view.GridItemDecoration;
import com.darknet.bvw.view.PoupAnim;
import com.darknet.bvw.view.TypefaceTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lxj.xpopup.XPopup;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class CommunityLeaderAct extends BaseActivity {

    @BindView(R.id.layBack)
    RelativeLayout layBack;

    @BindView(R.id.title)
    TypefaceTextView tvTitle;

    @BindView(R.id.title_right)
    TypefaceTextView titleRight;

    @BindView(R.id.tv_dialog)
    TextView tvDialog;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_award)
    TextView tvAward;

    @BindView(R.id.tv_release_award)
    TextView tvReleaseAward;

    @BindView(R.id.tv_release_ratio)
    TextView tvReleaseRatio;

    @BindView(R.id.tv_day_60)
    TextView tvDa60;

    @BindView(R.id.tv_apply)
    TextView tvApply;

    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;

    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;

    @BindView(R.id.pull_refresh_scrollview)
    PullToRefreshScrollView pullToRefreshScrollView;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    /**
     * 筛选类型
     */
    private int type = PerformanceDateEnum.day_14.getValue();

    private List<PerformanceAtom> performanceAtoms = new ArrayList<>();

    private XPopup.Builder builder;

    private CommunityLeaderAdapter adapter;
    private CustomAttachPopup customAttachPopup;

    private boolean isLeader;

    List<PerformanceAtom> tempList;

    private PerformanceDto performanceDto;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, CommunityLeaderAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        initTitle();
        initRecyclerView();
        initPoup();
        intRefreshScrollView();
        scrollView.setNestedScrollingEnabled(false);
        SpannableStringBuilder style = new SpannableStringBuilder(getString(R.string.fast_photo, getString(R.string.mine_award_ratio, "0%")));
        style.setSpan(new ForegroundColorSpan(ResourceUtils.getColor(mAppContext, R.color.color_01FCDA)), getString(R.string.mine_award_ratio, "0%").indexOf("*") + 1, getString(R.string.mine_award_ratio, "0%").indexOf("-"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvReleaseRatio.setText(style);
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);
        GridItemDecoration divider = new GridItemDecoration.Builder(this)
                .setHorizontalSpan(10)
                .setVerticalSpan(10)
                .setColorResource(R.color.transparent1)
                .setShowLastLine(false)
                .build();
        recyclerView.addItemDecoration(divider);
    }

    private void initPoup() {
        XPopup.Builder builder = new XPopup.Builder(this)
                .atView(tvDialog)
                .offsetY(-20)
                .hasShadowBg(false);
        this.builder = builder;
    }

    private void initTitle() {
        tvTitle.setText(R.string.title_com_leader);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText(R.string.help);
        titleRight.setTextSize(15);
    }

    private void intRefreshScrollView() {
        //1.设置模式
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pullToRefreshScrollView.setScrollingWhileRefreshingEnabled(false);
        pullToRefreshScrollView.setPullToRefreshOverScrollEnabled(false);


        //2.通过调用getLoadingLayoutProxy方法，设置下拉刷新状况布局中显示的文字 ，第一个参数为true,代表下拉刷新
        ILoadingLayout headLables = pullToRefreshScrollView.getLoadingLayoutProxy(true, false);
        headLables.setPullLabel(getString(R.string.refresh_one_view));
        headLables.setRefreshingLabel(getString(R.string.refresh_two_view));
        headLables.setReleaseLabel(getString(R.string.refresh_three_view));


        //3.设置监听事件
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (!AppUtil.isNetworkAvailable(mAppContext)) {
                    return;
                }
                performanceAtoms.clear();
                initDatas();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_community_leder;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        if (!AppUtil.isNetworkAvailable(mAppContext)) {
            initListData();
        } else {
            getPerformance();
            isLeader();
        }
    }

    private void initListData() {
        if (tempList == null) {
            tempList = new ArrayList<>();

        } else {
            tempList.clear();
        }
        for (int i = 0; i < 2; i++) {
            PerformanceAtom performanceAtom = new PerformanceAtom();
            performanceAtom.setBvwUsdPrice("0");
            performanceAtom.setBvwUsdRate(0);
            performanceAtom.setPerformance("0");
            performanceAtom.setBonus("0");
            performanceAtom.setSnapshort_hold_amount("0");
            if (i == 0) {
                performanceAtom.setDate(TimeUtil.currentDay());
            } else {
                performanceAtom.setDate(TimeUtil.getZuoTian("yyyy-MM-dd"));
            }
            tempList.add(performanceAtom);
        }

        refreshAdapter();
    }

    @Override
    public void configViews() {

    }


    @OnClick({R.id.layBack, R.id.tv_dialog, R.id.title_right, R.id.tv_apply})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layBack:
                finish();
                break;
            case R.id.tv_dialog:
                showDialogView();
                break;
            case R.id.title_right:
                CommunityLeaderHelpAct.startAct(this);
                break;
            case R.id.tv_apply:
                if (isLeader) {
                    return;
                }
                new XPopup.Builder(this)
                        .asCustom(new CenterPopu(this))
                        .show();
                break;
        }
    }

    private void showDialogView() {
        if (customAttachPopup == null) {
            customAttachPopup = new CustomAttachPopup(this);
            customAttachPopup.setOnClickListener(new CustomAttachPopup.OnClickListener() {
                @Override
                public void click(View v) {
                    switch (v.getId()) {
                        case R.id.tv_14:
                            type = PerformanceDateEnum.day_14.getValue();
                            tvDialog.setText(R.string.near_14);
                            performanceAtoms.clear();
                            refreshAdapter();
                            break;
                        case R.id.tv_30:
                            type = PerformanceDateEnum.day_30.getValue();
                            performanceAtoms.clear();
                            tvDialog.setText(R.string.near_30);
                            refreshAdapter();
                            break;
                        case R.id.tv_45:
                            type = PerformanceDateEnum.day_45.getValue();
                            performanceAtoms.clear();
                            tvDialog.setText(R.string.near_45);
                            refreshAdapter();
                            break;
                        case R.id.tv_60:
                            type = PerformanceDateEnum.day_60.getValue();
                            performanceAtoms.clear();
                            tvDialog.setText(R.string.near_60);
                            refreshAdapter();
                            break;
                    }
                }
            });
        }
        builder.customAnimator(new PoupAnim())
                .asCustom(customAttachPopup)
                .show();
    }

    /**
     * 获取业绩数据
     */
    private void getPerformance() {
        progressLayout.showLoading();
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        OkGo.<String>get("https://api.bvw.im/xchain-apileaderPlan/test/performance/1PiKUYfNTNvgfhK2LHSMNKdDtP12Wg6peM")
                .tag(this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        pullToRefreshScrollView.onRefreshComplete();
                        Log.d(TAG, ConfigNetWork.JAVA_API_URL + UrlPath.BTW_PERFORMANCE_URL + ":" + backresponse.body());
                        try {

                            Gson gson = new Gson();
                            BaseResponse<PerformanceDto> response = gson.fromJson(backresponse.body(), new TypeToken<BaseResponse<PerformanceDto>>() {
                            }.getType());
                            if (response == null) {
                                showEmpty();
                                return;
                            }
                            if (response.getCode() == 0) {
                                if (response.getData() == null) {
                                    showEmpty();
                                    return;
                                }
                                PerformanceDto performanceDto = response.getData();
                                if (performanceDto == null) {
                                    showEmpty();
                                    return;
                                }
                                dealPerformanceResult(performanceDto);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        pullToRefreshScrollView.onRefreshComplete();
                    }
                });
    }

    private void showEmpty() {
        int type = SPUtil.getInstance(mAppContext).getSelectLanguage();

        if (type == 1) {
            progressLayout.showEmpty(ContextCompat.getDrawable(mAppContext, R.drawable.icon_empty_cn), "");
        } else {
            progressLayout.showEmpty(ContextCompat.getDrawable(mAppContext, R.drawable.icon_empty), "");

        }
    }

    /**
     * 处理结果
     *
     * @param performanceDto
     */
    private void dealPerformanceResult(PerformanceDto performanceDto) {
        this.performanceDto = performanceDto;
        tvAward.setText(" " + performanceDto.getBonus_sum().split("\\.")[0] + " USDT");
        tvReleaseAward.setText(performanceDto.getBonus_release().split("\\.")[0]);
        tvWithdraw.setText(performanceDto.getBonus_confirm().split("\\.")[0]);

        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMaximumFractionDigits(2);
        String format = percentInstance.format(performanceDto.getBonus_rate());
        String value = getString(R.string.fast_photo, getString(R.string.mine_award_ratio, format));
        SpannableStringBuilder style = new SpannableStringBuilder(value);
        style.setSpan(new ForegroundColorSpan(ResourceUtils.getColor(mAppContext, R.color.color_01FCDA)), value.indexOf("*") + 1, value.indexOf("-"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvReleaseRatio.setText(style);

        tvDa60.setText(getString(R.string.day_60_usdt, performanceDto.getBonus().split("\\.")[0]));
        List<PerformanceAtom> performanceList = performanceDto.getPerformanceList();
        if (performanceList == null || performanceList.size() == 0) {
            showEmpty();
            return;
        }
        tempList = performanceList;
        refreshAdapter();
    }

    /**
     * 刷新列表
     */
    private void refreshAdapter() {
        if (tempList == null) {
            return;
        }
        if (type == PerformanceDateEnum.day_14.getValue()) {
            if (tempList.size() < 14) {
                performanceAtoms.addAll(tempList);
            } else {
                performanceAtoms.addAll(tempList.subList(0, 14));
            }

        } else if (type == PerformanceDateEnum.day_30.getValue()) {
            if (tempList.size() < 30) {
                performanceAtoms.addAll(tempList);
            } else {
                performanceAtoms.addAll(tempList.subList(0, 30));
            }
        } else if (type == PerformanceDateEnum.day_45.getValue()) {
            if (tempList.size() < 45) {
                performanceAtoms.addAll(tempList);
            } else {
                performanceAtoms.addAll(tempList.subList(0, 45));
            }
        } else if (type == PerformanceDateEnum.day_60.getValue()) {
            if (tempList.size() < 60) {
                performanceAtoms.addAll(tempList);
            } else {
                performanceAtoms.addAll(tempList.subList(0, 60));
            }
        }
        if (adapter == null) {
            adapter = new CommunityLeaderAdapter(mAppContext, performanceAtoms, performanceDto);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        if (adapter.getData().size() == 0) {
            showEmpty();
        } else {
            progressLayout.showContent();
        }
    }

    /**
     * 是否是领导
     */
    private void isLeader() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.BTW_STATE_URL)
                .tag(this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        Log.d(TAG, ConfigNetWork.JAVA_API_URL + UrlPath.BTW_STATE_URL + ":" + backresponse.body());
                        try {
                            Gson gson = new Gson();
                            BaseResponse<String> response = gson.fromJson(backresponse.body(), new TypeToken<BaseResponse<String>>() {
                            }.getType());
                            if (response.getCode() == 0) {
                                String data = response.getData();
                                if ("true".equals(data)) {
                                    tvApply.setText(R.string.is_leader);
                                    isLeader = true;
                                } else {
                                    tvApply.setText(R.string.accept_leader);
                                    isLeader = false;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        if (response == null) {
                            return;
                        }
                    }
                });
    }
}
