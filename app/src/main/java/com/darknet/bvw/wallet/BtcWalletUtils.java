package com.darknet.bvw.wallet;

import android.util.Log;

import com.darknet.bvw.model.BtcDo;
import com.google.gson.Gson;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletFile;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;

import static org.web3j.crypto.MnemonicUtils.generateSeed;

public class BtcWalletUtils {

    private static String BTC_TYPE = "m/44'/0'/0'/0";

//    private static SecureRandom secureRandom = ;


    public static List<String> createMnemonics() throws MnemonicException.MnemonicLengthException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] entropy = new byte[DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS / 8];
        secureRandom.nextBytes(entropy);
        return MnemonicCode.INSTANCE.toMnemonic(entropy);
    }


    static SecureRandom secureRandom = new SecureRandom();


    public static BtcDo create() {
        try {
            StringBuilder sb = new StringBuilder();
            byte[] entropy = new byte[Words.TWELVE.byteLength()];
            secureRandom.nextBytes(entropy);
            new MnemonicGenerator(English.INSTANCE).createMnemonic(entropy, sb::append);
            String mnemonics = sb.toString();

            byte[] seed = generateSeed(mnemonics, null);


            DeterministicKey rootPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
            byte[] privateKeyByte = rootPrivateKey.getPrivKeyBytes();
            ECKey ecKey = ECKey.fromPrivate(privateKeyByte);

            NetworkParameters params = MainNetParams.get();
            Address address = Address.fromKey(params, ecKey, Script.ScriptType.P2PKH);


            ECKeyPair keyPair = ECKeyPair.create(privateKeyByte);
            WalletFile walletFile = org.web3j.crypto.Wallet.createLight("123", keyPair);
            Gson gson = new Gson();
            String keystore = gson.toJson(walletFile);

            List<String> mnemonicList = null;

            if (mnemonics != null) {
                String[] tempNic = mnemonics.split(" ");
                mnemonicList = Arrays.asList(tempNic);
//                Log.e("wallet", "助记词集合转换 =>" + mnemonicList.toString());
            }

            Log.e("wallet", "私钥 =>" + ecKey.getPrivateKeyEncoded(params).toBase58());
            Log.e("wallet", "地址 =>" + address);
            Log.e("wallet", "公钥 =>" + ecKey.getPublicKeyAsHex());
            Log.e("wallet", "助记词 =>" + mnemonics);
            Log.e("wallet", "keystore =>" + keystore);

            BtcDo btcDo = new BtcDo();
            btcDo.setListZjc(mnemonicList);
            btcDo.setAddress(address.toString());
            btcDo.setPrivateKey(ecKey.getPrivateKeyEncoded(params).toBase58());
            btcDo.setPublicKey(ecKey.getPublicKeyAsHex());
            btcDo.setKeyStoreVal(keystore);
            return btcDo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    //创建新钱包
//    public static BtcDo createNewWallet() {
//        String passphrase = "";
//        String[] pathArray = BTC_TYPE.split("/");
//        DeterministicSeed ds = new DeterministicSeed(new SecureRandom(), 128, passphrase);
////        DeterministicSeed ds = new DeterministicSeed(secureRandom, null, "", 0L);
//
//        List<String> mnemonic = ds.getMnemonicCode();
//        byte[] seedBytes = ds.getSeedBytes();
//
//        if (seedBytes == null)
//            return null;
//        DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);
//        for (int i = 1; i < pathArray.length; i++) {
//            ChildNumber childNumber;
//            if (pathArray[i].endsWith("'")) {
//                int number = Integer.parseInt(pathArray[i].substring(0,
//                        pathArray[i].length() - 1));
//                childNumber = new ChildNumber(number, true);
//            } else {
//                int number = Integer.parseInt(pathArray[i]);
//                childNumber = new ChildNumber(number, false);
//            }
//            dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
//        }
//
//        String addressVals = null;
//
//
//        Log.e("wallet", "路径 " + dkKey.getPathAsString());
////        Log.e("wallet", "私钥 " + dkKey.getPrivateKeyAsHex());
//        Log.e("wallet", "公钥 =>" + dkKey.getPublicKeyAsHex());
//        NetworkParameters params = MainNetParams.get();
//
//        try {
//
////            ECKey2 key = new ECKey2();
//            Address address = Address.fromKey(params, dkKey, Script.ScriptType.P2PKH);
//
////            System.out.format("私钥 => %s\n", key.getPublicKeyAsHex());
////            System.out.format("私钥 => %s\n", key.getPrivateKeyEncoded(params).toBase58());
//            Log.e("wallet", "私钥22 " + dkKey.getPrivateKeyEncoded(params).toBase58());
//            Log.e("wallet", "地址 => " + address);
//            addressVals = address.toString();
////            String signMessage = dkKey.signMessage("haha");
////            Log.e("wallet", "signMessage => " + signMessage);
////            dkKey.verifyMessage("haha", signMessage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        BtcDo btcDo = new BtcDo();
//        btcDo.setListZjc(mnemonic);
//        btcDo.setAddress(addressVals);
//        btcDo.setPrivateKey(dkKey.getPrivateKeyEncoded(params).toBase58());
//        btcDo.setPublicKey(dkKey.getPublicKeyAsHex());
//
//        return btcDo;
//    }


    //通过助记词导入钱包
    public static BtcDo findWalletByZjc(String zjcVal) {

        try {
            List<String> listVal = Arrays.asList(zjcVal.split(" "));

            byte[] seed = generateSeed(zjcVal, null);

            DeterministicKey rootPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
            byte[] privateKeyByte = rootPrivateKey.getPrivKeyBytes();
            ECKey ecKey = ECKey.fromPrivate(privateKeyByte);

            NetworkParameters params = MainNetParams.get();
            Address address = Address.fromKey(params, ecKey, Script.ScriptType.P2PKH);

            ECKeyPair keyPair = ECKeyPair.create(privateKeyByte);
            WalletFile walletFile = org.web3j.crypto.Wallet.createLight("123456", keyPair);
            Gson gson = new Gson();
            String keystore = gson.toJson(walletFile);

            Log.e("wallet","私钥 =>"+ecKey.getPrivateKeyEncoded(params).toBase58());
            Log.e("wallet","地址 =>"+address);
            Log.e("wallet","助记词 =>"+listVal.toString());
            Log.e("wallet","keystore =>"+keystore);

            BtcDo btcDo = new BtcDo();
            btcDo.setListZjc(listVal);
            btcDo.setAddress(address.toString());
            btcDo.setPrivateKey(ecKey.getPrivateKeyEncoded(params).toBase58());
            btcDo.setPublicKey(ecKey.getPublicKeyAsHex());
            btcDo.setKeyStoreVal(keystore);
            btcDo.setZjcContent(zjcVal);

            return btcDo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


//    public static void prikey() {
//        try {
//            String keyVal = "L3Xb3v9Lut8iKVLPvFwKKU2ojfRGPQtUhgJvjCTV2ohpSq3BwWnU";
//            NetworkParameters params = MainNetParams.get();
//            DumpedPrivateKey btcPrivateKey = DumpedPrivateKey.fromBase58(params, keyVal);
//            ECKey key2 = btcPrivateKey.getKey();
//            Address address = Address.fromKey(params, key2, Script.ScriptType.P2PKH);
//            ECKeyPair keyPair = ECKeyPair.create(key2.getPrivKey());
//            WalletFile walletFile = org.web3j.crypto.Wallet.createLight("123456", keyPair);
//            Gson gson = new Gson();
//            String keystore = gson.toJson(walletFile);
//            Log.e("wallet", "私钥 =>" + key2.getPrivateKeyEncoded(params));
//            Log.e("wallet", "地址 =>" + address);
//            Log.e("wallet", "keystore =>" + keystore);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //通过私钥导入钱包
    public static BtcDo findWalletByKey(String keyVal) {

        try {
            NetworkParameters params = MainNetParams.get();
            DumpedPrivateKey btcPrivateKey = DumpedPrivateKey.fromBase58(params, keyVal);
            ECKey key2 = btcPrivateKey.getKey();

            Address address2 = Address.fromKey(params, key2, Script.ScriptType.P2PKH);
            ECKeyPair keyPair = ECKeyPair.create(key2.getPrivKey());
            WalletFile walletFile = org.web3j.crypto.Wallet.createLight("123456", keyPair);
            Gson gson = new Gson();
            String keystore = gson.toJson(walletFile);

            Log.e("wallet", "keystore =>" + keystore);
            Log.e("wallet", "地址 =>" + address2);
            Log.e("wallet", "私钥 =>" + key2.getPrivateKeyEncoded(params).toBase58());
            Log.e("wallet", "公钥 =>" + key2.getPublicKeyAsHex());

            BtcDo btcDo = new BtcDo();
            btcDo.setAddress(address2.toString());
            btcDo.setPrivateKey(key2.getPrivateKeyEncoded(params).toBase58());
            btcDo.setPublicKey(key2.getPublicKeyAsHex());
            btcDo.setKeyStoreVal(keystore);

            return btcDo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
