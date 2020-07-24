package com.darknet.bvw.activity.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.CoinsAdapter;
import com.darknet.bvw.adapter.TokenCoinsAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.CoinsModel;
import com.darknet.bvw.model.LeftBean;
import com.darknet.bvw.model.TokenCoinResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.DialogLoadding;
import com.darknet.bvw.view.HorizontalListView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class PopwindowsLeftFragment extends DialogFragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    private ListView mListView;
    private List<CoinsModel.DataBean> mList = new ArrayList<>();
    private CoinsAdapter mAdapter;

    private TokenCoinsAdapter mCoinsAdapter;
    private List<LeftBean> mStringList = new ArrayList<>();
    private String choseCoin = "USDT";

//    public DialogLoadding dialogLoadding;

    private boolean isfirst = true;

    private HorizontalListView mHorizontalListView;

    private OnChoseCoinsListener mCoinsListener;


    public void setCoinsListener(OnChoseCoinsListener coinsListener) {
        mCoinsListener = coinsListener;
    }

    public interface OnChoseCoinsListener {
        void onChose(String coinsSyblm, String closeStr, String close, boolean iscollectino, String usdRateeee, CoinsModel.DataBean dataBean);
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager == null) {
            return;
        }
        if (Integer.valueOf(Build.VERSION.SDK) > Build.VERSION_CODES.JELLY_BEAN) {
            if (manager.isDestroyed())
                return;
        }
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置style
        setStyle(DialogFragment.STYLE_NORMAL, R.style.LeftDialog);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }


    @Override
    public void onStart() {
        super.onStart();

        try {
            Dialog dialog = getDialog();
            if (dialog != null) {
                DisplayMetrics dm = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

                dialog.getWindow().setLayout(((dm.widthPixels / 3) * 2), ViewGroup.LayoutParams.MATCH_PARENT);
            }
            getDialog().getWindow().setBackgroundDrawable(null);
        }catch (Exception e){
            e.printStackTrace();
        }



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去除标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        lp.height = dm.heightPixels;
        lp.width = ((dm.widthPixels / 3) * 2);
        lp.gravity = Gravity.LEFT; //底部
        window.setAttributes(lp);
        View view = createView(inflater, container);
        initSoftInputListener();
//        dialogLoadding = new DialogLoadding(getActivity());
        return view;
    }


//    public void showLoading() {
//        if (dialogLoadding == null) {
//            dialogLoadding = new DialogLoadding(getActivity());
//        }
//        dialogLoadding.showDialog();
//    }
//
//    public void hideLoading() {
//        if (dialogLoadding != null)
//            dialogLoadding.closeDialog();
//    }

    private View createView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.popwindows_left, container, false);
        mListView = view.findViewById(R.id.popuwindow_lv);
        mHorizontalListView = view.findViewById(R.id.horizontallListview);


        mAdapter = new CoinsAdapter(getActivity(), mList);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        try {
            mCoinsAdapter = new TokenCoinsAdapter(getActivity(), mStringList);
            mHorizontalListView.setAdapter(mCoinsAdapter);


            mHorizontalListView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
                choseCoin = mStringList.get(position).getCoins();
                for (int i = 0; i < mStringList.size(); i++) {
                    if (i == position) {
                        mStringList.get(i).setColor(Color.BLACK);
                    } else {
                        mStringList.get(i).setColor(Color.GRAY);
                    }
                }
                mCoinsAdapter.notifyDataSetChanged();
                getCoinsList();
            });

            mListView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {

                try {
                    mCoinsListener.onChose(mList.get(position).getBase_token_symbol() + "-" + mList.get(position).getQuote_token_symbol(), mList.get(position).getThumb().getCloseStr(), mList.get(position).getThumb().getClose(), mList.get(position).isIs_favor_market(), mList.get(position).getThumb().getUsdRate(), mList.get(position));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }



        getTokenList();
        return view;
    }

    private void getTokenList() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.TOKEN_LIST_URL)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {

                            try {
                                Gson gson = new Gson();
                                TokenCoinResponse coinResponse = gson.fromJson(response.body(), TokenCoinResponse.class);
                                mStringList.clear();
                                if (coinResponse.getCode() == 0) {
                                    for (int i = 0; i < coinResponse.getData().size(); i++) {
                                        if (i == 0) {
                                            mStringList.add(new LeftBean(coinResponse.getData().get(i), Color.BLACK));
                                        } else {
                                            mStringList.add(new LeftBean(coinResponse.getData().get(i), Color.GRAY));
                                        }
                                    }
                                    mCoinsAdapter.notifyDataSetChanged();
                                    choseCoin = coinResponse.getData().get(0);
                                    getCoinsList();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    /**
     * 获取币种列表
     */
    private void getCoinsList() {
//        showLoading();
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.COIN_LIST_URL)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {

                            try {
                                Gson gson = new Gson();
                                CoinsModel model = gson.fromJson(response.body(), CoinsModel.class);
                                mList.clear();
                                mAdapter.setList(mList);
                                mAdapter.notifyDataSetChanged();
                                Log.e("TAG", "getCoinsList: " + choseCoin);
                                for (CoinsModel.DataBean datum : model.getData()) {
                                    if (datum.getQuote_token_symbol().equals(choseCoin)) {
                                        mList.add(datum);
                                    }
                                }
                                mAdapter.setList(mList);
                                mAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                e.printStackTrace();
                            }



                        }
//                        hideLoading();
                    }
                });
    }

    /**
     * 点击非输入框区域时，自动收起键盘
     */
    private void initSoftInputListener() {

        try {
            getDialog().getWindow().getDecorView()
                    .setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent event) {
                            InputMethodManager manager = (InputMethodManager) getActivity()
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                if (getDialog().getCurrentFocus() != null
                                        && getDialog().getCurrentFocus().getWindowToken() != null) {
                                    manager.hideSoftInputFromWindow(
                                            getDialog().getCurrentFocus().getWindowToken(),
                                            InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                            }
                            return false;
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
