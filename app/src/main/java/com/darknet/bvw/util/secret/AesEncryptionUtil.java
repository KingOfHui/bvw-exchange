//package com.darknet.bvw.util.secret;
//
//import javax.crypto.spec.IvParameterSpec;
//import java.io.DataInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.security.Key;
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//
//
//public class AesEncryptionUtil {
//    private static IvParameterSpec m4841a(String str) {
//        if (str == null) {
//            str = "";
//        }
//        StringBuffer stringBuffer = new StringBuffer(16);
//        stringBuffer.append(str);
//        while (stringBuffer.length() < 16) {
//            stringBuffer.append(AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_NONE);
//        }
//        if (stringBuffer.length() > 16) {
//            stringBuffer.setLength(16);
//        }
//        try {
//            str = stringBuffer.toString().getBytes("UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//            str2 = null;
//        }
//        return new IvParameterSpec(str2);
//    }
//
//    public static byte[] encrypt(byte[] bArr, String str, String str2) {
//        try {
//            Key secretKeySpec = new SecretKeySpec(str.getBytes(), "AES");
//            str = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            str.init(1, secretKeySpec, AesEncryptionUtil.m4841a(str2));
//            return str.doFinal(bArr);
//        } catch (byte[] bArr2) {
//            bArr2.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String encrypt(String str, String str2, String str3) {
//        byte[] bytes;
//        try {
//            bytes = str.getBytes("UTF-8");
//        } catch (String str4) {
//            str4.printStackTrace();
//            bytes = null;
//        }
//        return AesEncryptionUtil.byte2hex(AesEncryptionUtil.encrypt(bytes, str2, str3));
//    }
//
//    public static boolean test(String str, String str2) throws Exception {
//        File file = new File(str);
//        if (file.exists() == null) {
//            return false;
//        }
//        InputStream dataInputStream = new DataInputStream(new FileInputStream(file));
//        byte[] bytes = IOUtil.getBytes(dataInputStream);
//        IOUtil.close(dataInputStream);
//        if (AesEncryptionUtil.decrypt(bytes, str2, "HTML5PLUSRUNTIME") != null) {
//            return true;
//        }
//        return false;
//    }
//
//    public static byte[] decrypt(byte[] bArr, String str, String str2) {
//        try {
//            Key secretKeySpec = new SecretKeySpec(str.getBytes(), "AES");
//            str = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            str.init(2, secretKeySpec, AesEncryptionUtil.m4841a(str2));
//            return str.doFinal(bArr);
//        } catch (byte[] bArr2) {
//            bArr2.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String decrypt(String str, String str2, String str3) {
//        byte[] hex2byte;
//        try {
//            hex2byte = AesEncryptionUtil.hex2byte(str);
//        } catch (String str4) {
//            str4.printStackTrace();
//            hex2byte = null;
//        }
//        str4 = AesEncryptionUtil.decrypt(hex2byte, str2, str3);
//        if (str4 == null) {
//            return null;
//        }
//        try {
//            str2 = new String(str4, "UTF-8");
//        } catch (String str42) {
//            str42.printStackTrace();
//            str2 = null;
//        }
//        return str2;
//    }
//
//    public static String byte2hex(byte[] bArr) {
//        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
//        for (byte b : bArr) {
//            String toHexString = Integer.toHexString(b & 255);
//            if (toHexString.length() == 1) {
//                stringBuffer.append(AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_NONE);
//            }
//            stringBuffer.append(toHexString);
//        }
//        return stringBuffer.toString().toUpperCase();
//    }
//
//    public static byte[] hex2byte(String str) {
//        int i = 0;
//        if (str != null) {
//            if (str.length() >= 2) {
//                str = str.toLowerCase();
//                int length = str.length() / 2;
//                byte[] bArr = new byte[length];
//                while (i < length) {
//                    int i2 = i * 2;
//                    bArr[i] = (byte) (Integer.parseInt(str.substring(i2, i2 + 2), 16) & 255);
//                    i++;
//                }
//                return bArr;
//            }
//        }
//        return new byte[0];
//    }
//}
