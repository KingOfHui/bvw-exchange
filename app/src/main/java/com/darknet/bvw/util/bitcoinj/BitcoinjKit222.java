package com.darknet.bvw.util.bitcoinj;

import androidx.annotation.NonNull;

import com.darknet.bvw.util.ValidateUtil;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;

/**
 * X链签名与验证
 */
public class BitcoinjKit222 {


    public static void main(String[] args) {
        String privateKey = "9a3c6ea2eaf26c55bf7a6efa79e38f3472de68a7285112af9291b44c702fb6be";
        String pubKey = "0371486e08aab565463781a6cbf2116c3445a31f65390a28b4c12e67de1038d274";
        String msg = "CHAIN-AUTHENTICATION@"+System.currentTimeMillis();
        String address = "17bSYjJvKd8RvFBJjF3n22t9y3FqHwjGms";
        String signMessage = signMessage(msg, privateKey);
        boolean ret = verifyMessage(msg, signMessage, pubKey);
        boolean ret2 = verifyMessageByAddress(msg, signMessage, address);
        System.out.format("msg => %s\n", msg);
        System.out.format("signMessage => %s\n", signMessage);
        System.out.format("verifyMessage => %s\n", ret);
        System.out.format("verifyMessageByAddress => %s\n", ret2);
        System.out.format("address => %s\n", getAddress(pubKey));

        String msgVal = "CHAIN-AUTHENTICATION@1572953022935";
        String signMs = "ICZuNmhsjDRyEQmqu0CwUaezQ2hAxBuKexBoqHpIVsNxF7UhPNB73NnRkM28+0jL9N7uTJe+kDf6nyxIeXLuQX8=";
        String adddd = "12Gssh5SpigDRhMTuaD86vLbsNHH7fi9E6";
        boolean ret3 = verifyMessageByAddress(msgVal, signMs, adddd);

        System.out.println("ret3"+ret3);

//        String signVal = BitcoinjKit.signMessage(msg,privateKey);


    }

    /**
     * 根据签名信息获取公钥
     *
     * @param msg
     * @param signatureMsg
     * @return
     */
    public static String getPubKeyBySignMessage(@NonNull String msg, @NonNull String signatureMsg) {
        ECKey2 key = null;
        try {
            key = ECKey2.signedMessageToKey(msg, signatureMsg);
        } catch (Exception e) {
            System.out.println("getPubKeyBySignMessage error"+e.getMessage());
            return null;
        }
        return Utils.HEX.encode(key.getPubKey());
    }

    /**
     * 根据公钥获取地址
     *
     * @param pubKey
     * @return
     */
    public static String getAddress(@NonNull String pubKey) {
        try {
            org.bitcoinj.core.ECKey ecKey = org.bitcoinj.core.ECKey.fromPublicOnly(Utils.HEX.decode(pubKey));
            NetworkParameters params = MainNetParams.get();
            Address address = Address.fromKey(params, ecKey, Script.ScriptType.P2PKH);
            return address.toString();
        } catch (Exception e) {

            System.out.println("getAddress error"+e.getMessage());
            return null;
        }
    }

    /**
     * 签名
     *
     * @param msg
     * @param privateKey
     * @return
     */
    public static String signMessage(@NonNull String msg, @NonNull String privateKey) {
        byte[] b = Utils.HEX.decode(privateKey);
        ECKey2 key = ECKey2.fromPrivate(b);
        return key.signMessage(msg);
    }

    /**
     * 签名验证
     *
     * @param msg
     * @param signatureMsg
     * @param pubKey
     * @return
     */
    public static boolean verifyMessage(@NonNull String msg, @NonNull String signatureMsg, @NonNull String pubKey) {
        boolean result = false;
        try {
            ECKey2 ecKey = ECKey2.fromPublicOnly(Utils.HEX.decode(pubKey));
            ecKey.verifyMessage(msg, signatureMsg);
            result = true;
        } catch (Exception e) {
            System.out.println("verifyMessage error"+e.getMessage());
            result = false;
        } finally {
            return result;
        }
    }

    /**
     * 签名验证
     *
     * @param msg
     * @param signatureMsg
     * @param address
     * @return
     */
    public static boolean verifyMessageByAddress(@NonNull String msg, @NonNull String signatureMsg, @NonNull String address) {
        if (!ValidateUtil.isValid(address)) {
            return false;
        }
        String pubKey = getPubKeyBySignMessage(msg, signatureMsg);
        if (!ValidateUtil.isValid(pubKey)) {
            return false;
        }
        return address.equals(getAddress(pubKey));
    }
}
