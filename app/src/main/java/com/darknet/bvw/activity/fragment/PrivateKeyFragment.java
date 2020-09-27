package com.darknet.bvw.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.StopActivity;
import com.darknet.bvw.activity.WalletCreateActivity;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.BtcDo;
import com.darknet.bvw.util.SharedPreferencesUtil;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.util.language.SPUtil;
import com.darknet.bvw.wallet.BtcWalletUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PrivateKeyFragment extends Fragment {


    private Button privateBtn;

    private EditText contentView;

    private EditText walletName;
    private EditText pwdContent;
    private EditText pwdContentTwo;

    private TextView createView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_privatekey, container, false);

        initView(view);
        return view;
    }


    private void initView(View view) {
        privateBtn = (Button) view.findViewById(R.id.import_private_sub);
        contentView = (EditText) view.findViewById(R.id.import_private_key_content);
        walletName = (EditText) view.findViewById(R.id.import_private_key_name);
        pwdContent = (EditText) view.findViewById(R.id.import_private_key_pwd);
        pwdContentTwo = (EditText) view.findViewById(R.id.import_private_key_pwd_two);
        createView = (TextView) view.findViewById(R.id.private_lead_in_create_view);

        privateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String keyVal = contentView.getText().toString().trim();
                String walletNameVal = walletName.getText().toString();
                String pwdVal = pwdContent.getText().toString();
                String pwdTwoVal = pwdContentTwo.getText().toString();
                boolean checkVal = verifyInfo(keyVal, walletNameVal, pwdVal, pwdTwoVal);

                if (checkVal) {
                    getWallet(keyVal);
                }
            }
        });

        createView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent createIntent = new Intent(getActivity(), WalletCreateActivity.class);
//                startActivity(createIntent);
                Intent creatIntent = new Intent(getActivity(), WalletCreateActivity.class);
                startActivity(creatIntent);
            }
        });

    }


//    private static String ETH_TYPE = "m/44'/0'/0'/0";

    private void getWallet(String privateKey) {
        BtcDo btcDo = BtcWalletUtils.findWalletByKey(privateKey);

        if (btcDo == null) {
            Toast.makeText(getActivity(), getString(R.string.wrong_private_key), Toast.LENGTH_SHORT).show();
            return;
        }


        boolean checkVal = WalletDaoUtils.checkRepeatByPrivateKey(btcDo.getPrivateKey());

        if(checkVal){
            Toast.makeText(getActivity(), getString(R.string.repeat_wallet_val), Toast.LENGTH_SHORT).show();
            return;
        }
//        List<ETHWalletModel> ethWalletModels = WalletDaoUtils.loadAll();

        Integer data = (Integer) SharedPreferencesUtil.getData("wallet-name", 0);
        int length = data + 1;
        SharedPreferencesUtil.putData("wallet_name", length);
        String name = "BTW-Wallet-" + length;

//        String walletNameVal = walletName.getText().toString();
        String pwdVal = pwdContent.getText().toString();

        ETHWalletModel walletModel = new ETHWalletModel();
        walletModel.setAddress(btcDo.getAddress());
        walletModel.setCurrentSelect(1);
        walletModel.setKeyStoreVal(btcDo.getKeyStoreVal());
        walletModel.setPrivateKey(btcDo.getPrivateKey());
        walletModel.setPublicKey(btcDo.getPublicKey());
        walletModel.setName(name);
        walletModel.setPassword(pwdVal);



        //加入新钱包
        WalletDaoUtils.insertNewWallet(walletModel);

//        walletModel.setName(accountNameVals);
//        walletModel.setPassword(pwdVals);
        //这里需要检查本地是否存在
//        WalletDaoUtils.insertNewWallet(walletModel);

//        AtyContainer.getInstance().finishAllActivity();
//        EventBus.getDefault().post(new CloseViewEvent());

        EventBus.getDefault().post(walletModel);

        Intent mainIntent = new Intent(getActivity(), StopActivity.class);
        startActivity(mainIntent);

//        getActivity().finish();

    }


    private boolean verifyInfo(String mnemonic, String name, String walletPwd, String confirmPwd) {
        if (TextUtils.isEmpty(mnemonic)) {
            ToastUtils.showToast(getString(R.string.lead_in_check_private_key));
            return false;
        }
//        } else if (WalletDaoUtils.checkRepeatByMenmonic(mnemonic)) {
//            ToastUtils.showToast(R.string.load_wallet_already_exist);
//            return false;
//        }
        /*else if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(R.string.account_name_notice);
            return false;
        }*/ else if (TextUtils.isEmpty(walletPwd) || walletPwd.length() < 6) {
            ToastUtils.showToast(R.string.wallet_pwd_six);
            return false;
        }
//        else if (TextUtils.isEmpty(walletPwd)) {
//            ToastUtils.showToast(R.string.create_wallet_pwd_input_tips);
//            // 同时判断强弱
//            return false;
//        }
        else if (TextUtils.isEmpty(confirmPwd) || !TextUtils.equals(confirmPwd, walletPwd)) {
            ToastUtils.showToast(R.string.create_wallet_pwd_confirm_input_tips);
            return false;
        }
        return true;
    }
}
