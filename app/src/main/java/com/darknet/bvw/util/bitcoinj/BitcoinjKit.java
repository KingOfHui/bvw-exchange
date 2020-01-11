package com.darknet.bvw.util.bitcoinj;

import com.darknet.bvw.util.ValidateUtil;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;

import java.math.BigDecimal;

/**
 * X链签名与验证
 */
public class BitcoinjKit {


    /**
     * 私钥 => c3fb02d7fd7d34709b59b8576c80fb64e21484649e87a40a97765bf0d4664d15
     * 私钥 => L3nftoNJvSgJs1nzPRVwHZkDwcGSUfRuEYVkswWRJBPRXhTUw2L6
     * 公钥 => 0346b0504de625fe7233f1097315901eb25df9666cd93634723e493d3cd21c2973
     * 地址 => 1FdFg6vJ4CWfn8x9vaipPQ1gpiRsSkZzMe
     */
    public static void main(String[] args) {
//        String privateKey = "c3fb02d7fd7d34709b59b8576c80fb64e21484649e87a40a97765bf0d4664d15";
//        String signMessage = signMessage(msg, privateKey);
//        String privateKey58 = "L3nftoNJvSgJs1nzPRVwHZkDwcGSUfRuEYVkswWRJBPRXhTUw2L6";
//        String pubKey = "0346b0504de625fe7233f1097315901eb25df9666cd93634723e493d3cd21c2973";
//        String msg = "CHAIN-AUTHENTICATION@" + System.currentTimeMillis();
//        String address = "1FdFg6vJ4CWfn8x9vaipPQ1gpiRsSkZzMe";
//        String signMessage = signMessageBy58(msg, privateKey58);
//        boolean ret = verifyMessage(msg, signMessage, pubKey);
//        boolean ret2 = verifyMessageByAddress(msg, signMessage, address);
//        System.out.format("msg => %s\n", msg);
//        System.out.format("signMessage => %s\n", signMessage);
//        System.out.format("verifyMessage => %s\n", ret);
//        System.out.format("verifyMessageByAddress => %s\n", ret2);
//        System.out.format("address => %s\n", getAddress(pubKey));


//        DecimalFormat df=new DecimalFormat("0.000");


//        String val = "123.89797799";
//
//        if(val.contains(".")){
//            //说明有小数
//            String afterPoint = val.substring(val.indexOf(".")+1,val.length());
//
//            if(afterPoint.length() > 6){
//
//            }
//
//            System.out.println(afterPoint);
//        }else {
//            //说明没小数
//            System.out.println(val);
//        }


//        BigDecimal b = new BigDecimal("23");
        BigDecimal a = new BigDecimal("10.00001000");
        System.out.println("back.result..="+a.stripTrailingZeros().toPlainString());

//        if (b.compareTo(a) == 0) {
//
//        }


    }

    /**
     * 根据签名信息获取公钥
     */
    public static String getPubKeyBySignMessage(String msg, String signatureMsg) {
        ECKey2 key;
        try {
            key = ECKey2.signedMessageToKey(msg, signatureMsg);
        } catch (Exception e) {
//            log.error("getPubKeyBySignMessage error", e);
            return null;
        }
        return Utils.HEX.encode(key.getPubKey());
    }

    /**
     * 根据公钥获取地址
     */
    public static String getAddress(String pubKey) {
        try {
            org.bitcoinj.core.ECKey ecKey = org.bitcoinj.core.ECKey.fromPublicOnly(Utils.HEX.decode(pubKey));
            NetworkParameters params = MainNetParams.get();
            Address address = Address.fromKey(params, ecKey, Script.ScriptType.P2PKH);
            return address.toString();
        } catch (Exception e) {
//            log.error("getAddress error", e);
            return null;
        }
    }

    /**
     * 签名
     */
    public static String signMessage(String msg, String privateKey) {
        return fromPrivateKey(privateKey).signMessage(msg);
    }

    /**
     * 签名
     */
    public static String signMessageBy58(String msg, String privateKey58) {
        return fromPrivateKey58(privateKey58).signMessage(msg);
    }

    /**
     * 签名验证
     */
    public static boolean verifyMessage(String msg, String signatureMsg, String pubKey) {
        boolean result = false;
        try {
            ECKey2 ecKey = ECKey2.fromPublicOnly(Utils.HEX.decode(pubKey));
            ecKey.verifyMessage(msg, signatureMsg);
            result = true;
        } catch (Exception e) {
//            log.error("verifyMessage error", e);
            result = false;
        } finally {
            return result;
        }
    }

    /**
     * 签名验证
     */
    public static boolean verifyMessageByAddress(String msg, String signatureMsg, String address) {
        if (!ValidateUtil.isValid(address)) {
            return false;
        }
        String pubKey = getPubKeyBySignMessage(msg, signatureMsg);
        if (!ValidateUtil.isValid(pubKey)) {
            return false;
        }
        return address.equals(getAddress(pubKey));
    }

    /**
     * 获取EK
     */
    public static ECKey2 fromPrivateKey(String privateKey) {
        byte[] b = Utils.HEX.decode(privateKey);
        ECKey2 key = ECKey2.fromPrivate(b);
        return key;
    }

    /**
     * 获取EK
     */
    public static ECKey2 fromPrivateKey58(String privateKey58) {
        DumpedPrivateKey2 btcPrivateKey = DumpedPrivateKey2.fromBase58(MainNetParams.get(), privateKey58);
        ECKey2 ecKey = btcPrivateKey.getKey();
        return ecKey;
    }

}
