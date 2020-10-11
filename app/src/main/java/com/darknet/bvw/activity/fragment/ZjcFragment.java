package com.darknet.bvw.activity.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.darknet.bvw.activity.PrivacyPolicyActivity;
import com.darknet.bvw.activity.StopActivity;
import com.darknet.bvw.activity.WalletCreateActivity;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.BtcDo;
import com.darknet.bvw.util.SharedPreferencesUtil;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.util.UserSPHelper;
import com.darknet.bvw.wallet.BtcWalletUtils;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ZjcFragment extends Fragment {

    TextView textView;

    private EditText zjcContentView;
    private EditText accountNameView;
    private EditText pwdOneView;
    private EditText pwdTwoView;

    private Button submitView;

    private String privateKey;
    private String publicKey;
    private String addressVals;

    private String accountNameVals;
    private String pwdVals;
    private TextView createWalletView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_zjc_v2, container, false);
        // 获取文本控件
        textView = view.findViewById(R.id.content_text);

        // 获取Bundle,该对象是Activity创建Fragment时，传入的
        Bundle bundle = getArguments();
        if (bundle != null) {
            String textValue = bundle.getString("textValue");// 将文本框的值赋值为传入的textValue
            textView.setText(textValue);
        }

        initView(view);
        return view;
    }


    private void initView(View view) {
        zjcContentView = (EditText) view.findViewById(R.id.import_zjc_content);
        accountNameView = (EditText) view.findViewById(R.id.input_account_name);
        pwdOneView = (EditText) view.findViewById(R.id.input_account_pwd);
        pwdTwoView = (EditText) view.findViewById(R.id.input_account_pwd_two);

        submitView = (Button) view.findViewById(R.id.input_submit_view);

        createWalletView = (TextView) view.findViewById(R.id.zjc_lead_in_create_view);

        view.findViewById(R.id.tv_fuwu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), PrivacyPolicyActivity.class));

            }
        });

        submitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zjcVals = zjcContentView.getText().toString().trim();

//                findWalletByZjc(zjcVals);

                accountNameVals = accountNameView.getText().toString().trim();
                pwdVals = pwdOneView.getText().toString().trim();
                String twoVals = pwdTwoView.getText().toString().trim();
                boolean checkVal = verifyInfo(zjcVals, accountNameVals, pwdVals, twoVals);
                if (checkVal) {
                    findWalletByZjc(zjcVals, accountNameVals);
                }

            }
        });


        createWalletView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent createIntent = new Intent(getActivity(),WalletCreateActivity.class);
//                startActivity(createIntent);
                Intent creatIntent = new Intent(getActivity(), WalletCreateActivity.class);
                startActivity(creatIntent);

            }
        });

    }

    //通过助记词，找回钱包
    private void findWalletByZjc(String zjcVal, String walletName) {

        //先判断本地是否已存在该钱包名
//        boolean isExit = WalletDaoUtils.checkWalletName(walletName);
//
//        if (!isExit) {
//            Toast.makeText(getActivity(), "钱包名已存在，请修改", Toast.LENGTH_SHORT).show();
//            return;
//        }

        BtcDo btcDo = BtcWalletUtils.findWalletByZjc(zjcVal);

        if (btcDo == null) {
            Toast.makeText(getActivity(), getString(R.string.wrong_zjc_wrong), Toast.LENGTH_SHORT).show();
            return;
        }


        boolean checkVal = WalletDaoUtils.checkRepeatByPrivateKey(btcDo.getPrivateKey());

        if (checkVal) {
            Toast.makeText(getActivity(), getString(R.string.repeat_wallet_val), Toast.LENGTH_SHORT).show();
            return;
        }
        Integer data = (Integer) UserSPHelper.get(requireContext(), "wallet", 0);
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("wallet", MODE_PRIVATE);
//        int data =sharedPreferences.getInt("wallet-name",0);
        String name = "BIW-Wallet-1";
        if (data!=null) {
            int length = data + 1;
            UserSPHelper.setParam(requireContext(), "wallet", length);
            name = "BIW-Wallet-" + length;
        }


        ETHWalletModel walletModel = new ETHWalletModel();
        walletModel.setAddress(btcDo.getAddress());
        walletModel.setMnemonic(zjcVal);
        walletModel.setName(name);
        walletModel.setPassword(pwdVals);
        //记住把其他钱包，设置为不可选
        walletModel.setCurrentSelect(1);
        walletModel.setPublicKey(btcDo.getPublicKey());
        walletModel.setPrivateKey(btcDo.getPrivateKey());
        walletModel.setKeyStoreVal(btcDo.getKeyStoreVal());

        //加入新钱包
        WalletDaoUtils.insertNewWallet(walletModel);

        //这里需要检查本地是否存在
//        WalletDaoUtils.insertNewWallet(walletModel);
//        AtyContainer.getInstance().finishAllActivity();
//        EventBus.getDefault().post(new CloseViewEvent());


        Intent mainIntent = new Intent(getActivity(), StopActivity.class);
//        mainIntent.putExtra("addressVals", btcDo.getAddress());
//        mainIntent.putExtra("privateKey", btcDo.getPrivateKey());
        startActivity(mainIntent);
//        (BaseActivity)


    }


    private boolean verifyInfo(String mnemonic, String name, String walletPwd, String confirmPwd) {
        if (TextUtils.isEmpty(mnemonic)) {
            ToastUtils.showToast(getString(R.string.load_wallet_by_mnemonic_input_tip));
            return false;
        } else if (!WalletDaoUtils.isValid(mnemonic)) {
            ToastUtils.showToast(getString(R.string.load_wallet_by_mnemonic_input_tip));
            return false;
        }
//        } else if (WalletDaoUtils.checkRepeatByMenmonic(mnemonic)) {
//            ToastUtils.showToast(R.string.load_wallet_already_exist);
//            return false;
//        }
        /*else if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(R.string.account_name_notice);
            return false;
        } */else if (TextUtils.isEmpty(walletPwd) || walletPwd.length() < 6) {
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
