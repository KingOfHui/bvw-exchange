package com.darknet.bvw.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.AboutActivity;
import com.darknet.bvw.activity.AccountManageActivity;
import com.darknet.bvw.activity.ContactPeopleActivity;
import com.darknet.bvw.activity.MessageCenterActivity;
import com.darknet.bvw.activity.SettingActivity;
import com.darknet.bvw.order.ui.activity.ConfirmOrderActivity;
import com.darknet.bvw.order.ui.activity.MyAddressesActivity;
import com.darknet.bvw.order.ui.activity.OrderListActivity;
import com.darknet.bvw.util.UserSPHelper;


/**
 * @author zhangchen
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout manageWallet;
    private RelativeLayout jiaoYiWallet;
//    private BaseActivity baseActivity;
    private ImageView setView;
    private TextView walletName;
    private LinearLayout aboutUsLayout;
    private LinearLayout helpLayout;
    private LinearLayout msgLayout;
    private LinearLayout systemLayout;
    private LinearLayout addressLayout;

    private LinearLayout clientIdLayout;


    private Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }


    public MineFragment() {
    }

//    public MineFragment(BaseActivity bactivity) {
////        super(bactivity);
//        this.baseActivity = bactivity;
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        manageWallet = (RelativeLayout) view.findViewById(R.id.layWallet);
        jiaoYiWallet = (RelativeLayout) view.findViewById(R.id.layJyDetail);
        setView = (ImageView) view.findViewById(R.id.imgSetting);
        walletName = (TextView) view.findViewById(R.id.mine_wallet_name);

        aboutUsLayout = (LinearLayout) view.findViewById(R.id.layAboutUs);
        helpLayout = (LinearLayout) view.findViewById(R.id.layHelpCenter);
        msgLayout = (LinearLayout) view.findViewById(R.id.layMsgCenter);
        systemLayout = (LinearLayout) view.findViewById(R.id.laySystemSetting);
        addressLayout = (LinearLayout) view.findViewById(R.id.lay_address_layout);

        clientIdLayout = (LinearLayout) view.findViewById(R.id.client_id);
        LinearLayout llAllOrder = view.findViewById(R.id.llAllOrder);
        LinearLayout llWaitPay = view.findViewById(R.id.llWaitPay);
        LinearLayout llWaitTaken = view.findViewById(R.id.llWaitTaken);
        LinearLayout llWaitDelivered = view.findViewById(R.id.llWaitDelivered);
        llWaitPay.setOnClickListener(view1 -> OrderListActivity.start(requireContext(),1));
        llWaitDelivered.setOnClickListener(view1 -> OrderListActivity.start(requireContext(),2));
        llWaitTaken.setOnClickListener(view1 -> OrderListActivity.start(requireContext(),3));
        llAllOrder.setOnClickListener(v -> OrderListActivity.start(requireContext(),0));
        try {
            String nameVal = (String) UserSPHelper.get(activity, "nickName", "no");
            if (nameVal.equals("no")) {
                walletName.setText("dark net user");
            } else {

//                Log.e("nameval",".....here..do...here....");
                walletName.setText(nameVal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//        walletName.setText(walletModel.getName());

        manageWallet.setOnClickListener(this);
        jiaoYiWallet.setOnClickListener(this);
        setView.setOnClickListener(this);

        aboutUsLayout.setOnClickListener(this);
        helpLayout.setOnClickListener(this);
        msgLayout.setOnClickListener(this);
        systemLayout.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        clientIdLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.client_id:

//                String jiguangId = JPushInterface.getRegistrationID(activity);

//                Toast.makeText(activity, "激光id:" + jiguangId, Toast.LENGTH_SHORT).show();

                break;
            case R.id.lay_address_layout:
                Intent addressIntent = new Intent(activity, ContactPeopleActivity.class);
                addressIntent.putExtra("type", 0);
                startActivity(addressIntent);
                break;
            case R.id.laySystemSetting:
                Intent sysIntent = new Intent(activity, SettingActivity.class);
                startActivity(sysIntent);
                break;
            case R.id.layAboutUs:
                Intent aboutIntent = new Intent(activity, AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.layHelpCenter:
//                Intent helpIntent = new Intent(activity, HelpActivity.class);
//                startActivity(helpIntent);
                break;
            case R.id.layMsgCenter:
//                Intent msgIntent = new Intent(activity, MessageCenterTwoActivity.class);
//                startActivity(msgIntent);
                break;
            case R.id.layWallet:
                Intent manageIntent = new Intent(activity, AccountManageActivity.class);
                startActivity(manageIntent);
                break;
            case R.id.layJyDetail:
                Intent tradeIntent = new Intent(activity, MessageCenterActivity.class);
                startActivity(tradeIntent);
                break;
            case R.id.imgSetting:
                Intent setIntent = new Intent(activity, SettingActivity.class);
                startActivity(setIntent);
                break;
        }
    }
}
