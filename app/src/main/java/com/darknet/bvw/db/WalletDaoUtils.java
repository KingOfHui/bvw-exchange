package com.darknet.bvw.db;

import android.database.Cursor;
import android.text.TextUtils;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.Entity.ETHWalletModelDao;

import java.util.ArrayList;
import java.util.List;

public class WalletDaoUtils {
    public static ETHWalletModelDao ethWalletDao = MyApp.getInstance().getDaoSession().getETHWalletModelDao();

    /**
     * 插入新创建钱包
     *
     * @param ethWallet 新创建钱包
     */
    public static void insertNewWallet(ETHWalletModel ethWallet) {

        //在插入之前，先将其他钱包更新为未选状态
        List<ETHWalletModel> walletList = loadAll();
        for (int i=0;i<walletList.size();i++){
            ETHWalletModel walletModel = walletList.get(i);
            walletModel.setCurrentSelect(0);
            updateWallet(walletModel);
        }
        //将新加入的钱包，设为当前选中状态
        ethWallet.setCurrentSelect(1);
        ethWalletDao.insert(ethWallet);
    }

    //设置为当前钱包
    public static void setCurrentWallet(ETHWalletModel ethWallet){
        //在插入之前，先将其他钱包更新为未选状态
        List<ETHWalletModel> walletList = loadAll();
        for (int i=0;i<walletList.size();i++){
            ETHWalletModel walletModel = walletList.get(i);
            walletModel.setCurrentSelect(0);
            updateWallet(walletModel);
        }
        //将新加入的钱包，设为当前选中状态
        ethWallet.setCurrentSelect(1);
        ethWalletDao.update(ethWallet);
    }



    public static boolean checkWalletName(String walletName) {
        List<ETHWalletModel> btcWallets = ethWalletDao.loadAll();
        if (btcWallets == null || btcWallets.size() == 0) {
            return true;
        } else {
            boolean signVal = true;
            for (int i = 0; i < btcWallets.size(); i++) {
                ETHWalletModel walletModel = btcWallets.get(i);
                if (walletModel.getName().equals(walletName)) {
                    signVal = false;
                }
            }
            return signVal;
        }
    }


    /**
     * 更新选中钱包
     *
     * @param id 钱包ID
     */
    public static ETHWalletModel updateCurrent(long id) {
        List<ETHWalletModel> ethWallets = ethWalletDao.loadAll();
        ETHWalletModel currentWallet = null;
        for (ETHWalletModel ethwallet : ethWallets) {
            if (id != -1 && ethwallet.getId() == id) {
                ethwallet.setCurrent(true);
                currentWallet = ethwallet;
            } else {
                ethwallet.setCurrent(false);
            }
            ethWalletDao.update(ethwallet);
        }
        return currentWallet;
    }

    /**
     * 获取当前钱包
     *
     * @return 钱包对象
     */
    public static ETHWalletModel getCurrent() {
        List<ETHWalletModel> ethWallets = ethWalletDao.loadAll();
        for (ETHWalletModel ethwallet : ethWallets) {
            if (ethwallet.getCurrentSelect() == 1) {
                return ethwallet;
            }
        }
        return null;
    }

    /**
     * 查询所有钱包
     */
    public static List<ETHWalletModel> loadAll() {



        return ethWalletDao.loadAll();
    }

    public static ETHWalletModel getWalletById(long walletId) {
        return ethWalletDao.load(walletId);
    }

    /**
     * 更新钱包信息
     */
    public static void updateWallet(ETHWalletModel walletModel) {
        ethWalletDao.update(walletModel);
    }


    /**
     * 更新钱包信息
     */
    public static void deleteWallet(ETHWalletModel walletModel) {
        ethWalletDao.delete(walletModel);
    }



    /**
     * 以私钥检查钱包是否存在
     *
     * @return true if repeat
     */
    public static boolean checkRepeatByPrivateKey(String privateKey) {
        List<ETHWalletModel> ethWallets = loadAll();
        for (ETHWalletModel ethWallet : ethWallets) {
            if (TextUtils.isEmpty(ethWallet.getPrivateKey())) {
                continue;
            }
            if (TextUtils.equals(ethWallet.getPrivateKey().trim(), privateKey.trim())) {
                return true;
            }
        }
        return false;
    }

    public static ETHWalletModel getETHWalletByPrivateKey(String privateKey) {
        privateKey = privateKey.trim();
        List<ETHWalletModel> ethWallets = loadAll();
        for (ETHWalletModel ethWallet : ethWallets) {
            if (TextUtils.isEmpty(ethWallet.getPrivateKey())) {
                continue;
            }
            if (TextUtils.equals(ethWallet.getPrivateKey().trim(), privateKey)) {
                return ethWallet;
            }
        }
        return null;
    }


    /**
     * 根据钱包名，查找钱包信息
     *
     * @return
     */
    public static ETHWalletModel findWalletByName(String name) {

        ETHWalletModel walletModel = null;

        String sqlTemp = "SELECT * FROM ETHWALLET_MODEL WHERE NAME ="+name;

        Cursor cursor = ethWalletDao.getDatabase().rawQuery(sqlTemp, null);

        List<String> tempDateVal = new ArrayList<>();
        while (cursor.moveToNext()) {
            String timeVal = cursor.getString(cursor.getColumnIndex("SELECT_DATE"));
            tempDateVal.add(timeVal);
        }
        cursor.close();



        return walletModel;
    }


    /**
     * 检查钱包名称是否存在
     *
     * @param name
     * @return
     */
    public static boolean walletNameChecking(String name) {
        List<ETHWalletModel> ethWallets = loadAll();
        for (ETHWalletModel ethWallet : ethWallets
        ) {
            if (TextUtils.equals(ethWallet.getName(), name)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 设置isBackup为已备份
     *
     * @param walletId 钱包Id
     */
    public static void setIsBackup(long walletId) {
        ETHWalletModel ethWallet = ethWalletDao.load(walletId);
        ethWallet.setIsBackup(true);
        ethWalletDao.update(ethWallet);
    }

    /**
     * 以助记词检查钱包是否存在
     *
     * @param mnemonic
     * @return true if repeat
     */
    public static boolean checkRepeatByMenmonic(String mnemonic) {
        List<ETHWalletModel> ethWallets = loadAll();
        for (ETHWalletModel ethWallet : ethWallets
        ) {
            if (TextUtils.isEmpty(ethWallet.getMnemonic())) {
//                LogUtils.d("wallet mnemonic empty");
                continue;
            }
            if (TextUtils.equals(ethWallet.getMnemonic().trim(), mnemonic.trim())) {
//                LogUtils.d("aleady");
                return true;
            }
        }
        return false;
    }


    public static boolean isValid(String mnemonic) {
        return mnemonic.split(" ").length >= 12;
    }

    public static boolean checkRepeatByKeystore(String keystore) {
        return false;
    }

    /**
     * 修改钱包名称
     *
     * @param walletId
     * @param name
     */
    public static void updateWalletName(long walletId, String name) {
        ETHWalletModel wallet = ethWalletDao.load(walletId);
        wallet.setName(name);
        ethWalletDao.update(wallet);
    }

    public static void setCurrentAfterDelete() {
        List<ETHWalletModel> ethWallets = ethWalletDao.loadAll();
        if (ethWallets != null && ethWallets.size() > 0) {
            ETHWalletModel ethWallet = ethWallets.get(0);
            ethWallet.setCurrent(true);
            ethWalletDao.update(ethWallet);
        }
    }

    public static boolean checkPassword(String password) {
        ETHWalletModel current = getCurrent();
        if(current == null || password == null) return false;
        return password.equals(current.getPassword());
    }
}
