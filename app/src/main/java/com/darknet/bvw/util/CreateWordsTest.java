package com.darknet.bvw.util;

import com.darknet.bvw.db.Entity.ETHWalletModel;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class CreateWordsTest {

    private static String ETH_TYPE = "m/44'/0'/0'/0";

    private static SecureRandom secureRandom = new SecureRandom();

    public static void main(String args[]) {

        String[] pathArray = ETH_TYPE.split("/");
//        if (pathArray.length <= 1) {
//            //内容不对
//            return null;
//        }

        String pwd = "222222";

        String passphrase = "";
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        DeterministicSeed ds = new DeterministicSeed(secureRandom, 128, passphrase);
        createEthWallet(ds, pathArray, pwd);
    }


    private static ETHWalletModel createEthWallet(DeterministicSeed ds, String[] pathArray, String password) {
        //种子
        byte[] seedBytes = ds.getSeedBytes();
        System.out.println("种子:" + Arrays.toString(seedBytes));
        //助记词
        List<String> mnemonic = ds.getMnemonicCode();
        System.out.println("助记词:" + Arrays.toString(mnemonic.toArray()));

        if (seedBytes == null)
            return null;
        DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);
        for (int i = 1; i < pathArray.length; i++) {
            ChildNumber childNumber;
            if (pathArray[i].endsWith("'")) {
                int number = Integer.parseInt(pathArray[i].substring(0,
                        pathArray[i].length() - 1));
                childNumber = new ChildNumber(number, true);
            } else {
                int number = Integer.parseInt(pathArray[i]);
                childNumber = new ChildNumber(number, false);
            }
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
        }
        System.out.println("path " + dkKey.getPathAsString());
        System.out.println("path " + dkKey.getPrivateKeyAsHex());
        System.out.format("公钥 => %s\n", dkKey.getPublicKeyAsHex());

        try {
            NetworkParameters params = MainNetParams.get();
            ECKey key = new ECKey();
            Address address = Address.fromKey(params,key, Script.ScriptType.P2PKH);
            System.out.format("地址 => %s\n", address);
            String signMessage = key.signMessage("haha");
            System.out.format("signMessage => %s\n", signMessage);
            key.verifyMessage("haha",signMessage);
        }catch (Exception e){
            e.printStackTrace();
        }




        return null;
    }

}
