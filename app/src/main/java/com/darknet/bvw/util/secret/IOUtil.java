//package com.darknet.bvw.util.secret;
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//
//
//public class IOUtil {
//    static final int BUF_SIZE = 20480;
//
//    public static void close(InputStream inputStream) {
//        if (inputStream != null) {
//            try {
//                inputStream.close();
//            } catch (InputStream inputStream2) {
//                inputStream2.printStackTrace();
//            }
//        }
//    }
//
//    public static byte[] getBytes(InputStream inputStream) throws IOException {
//        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] bArr = new byte[BUF_SIZE];
//        while (true) {
//            int read = inputStream.read(bArr);
//            if (read == -1) {
//                inputStream = byteArrayOutputStream.toByteArray();
//                IOUtil.close(byteArrayOutputStream);
//                return inputStream;
//            }
//            byteArrayOutputStream.write(bArr, 0, read);
//        }
//    }
//
//    public static void close(OutputStream outputStream) {
//        if (outputStream != null) {
//            try {
//                outputStream.close();
//            } catch (OutputStream outputStream2) {
//                outputStream2.printStackTrace();
//            }
//        }
//    }
//
//    private static StringBuilder toStringBuffer(InputStream inputStream) throws IOException {
//        if (inputStream == null) {
//            return null;
//        }
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder stringBuilder = new StringBuilder();
//        while (true) {
//            String readLine = bufferedReader.readLine();
//            if (readLine != null) {
//                stringBuilder.append(readLine);
//                stringBuilder.append("\n");
//            } else {
//                inputStream.close();
//                return stringBuilder;
//            }
//        }
//    }
//
//    public static String toString(InputStream inputStream) throws IOException {
//        return inputStream == null ? null : IOUtil.toStringBuffer(inputStream).toString();
//    }
//
//    public static java.lang.String readStringFile(java.lang.String r3) {
//        /* JADX: method processing error */
///*
//Error: java.lang.NullPointerException
//	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
//	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
//	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
//	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
//	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
//	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
//	at jadx.core.ProcessClass.process(ProcessClass.java:37)
//	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
//	at jadx.core.ProcessClass.process(ProcessClass.java:42)
//	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
//	at jadx.api.JavaClass.decompile(JavaClass.java:62)
//	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
//*/
//        /*
//        r0 = 0;
//        r1 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0018, all -> 0x0015 }
//        r1.<init>(r3);	 Catch:{ IOException -> 0x0018, all -> 0x0015 }
//        r3 = p080io.dcloud.common.util.IOUtil.getBytes(r1);	 Catch:{ IOException -> 0x0013 }
//        r2 = new java.lang.String;	 Catch:{ IOException -> 0x0013 }
//        r2.<init>(r3);	 Catch:{ IOException -> 0x0013 }
//        r1.close();	 Catch:{ IOException -> 0x0012 }
//    L_0x0012:
//        return r2;
//    L_0x0013:
//        r3 = move-exception;
//        goto L_0x001a;
//    L_0x0015:
//        r3 = move-exception;
//        r1 = r0;
//        goto L_0x0024;
//    L_0x0018:
//        r3 = move-exception;
//        r1 = r0;
//    L_0x001a:
//        r3.printStackTrace();	 Catch:{ all -> 0x0023 }
//        if (r1 == 0) goto L_0x0022;
//    L_0x001f:
//        r1.close();	 Catch:{ IOException -> 0x0022 }
//    L_0x0022:
//        return r0;
//    L_0x0023:
//        r3 = move-exception;
//    L_0x0024:
//        if (r1 == 0) goto L_0x0029;
//    L_0x0026:
//        r1.close();	 Catch:{ IOException -> 0x0029 }
//    L_0x0029:
//        throw r3;
//        */
//        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.IOUtil.readStringFile(java.lang.String):java.lang.String");
//    }
//
//    public static boolean writeStringFile(String str, String str2) {
//        OutputStream outputStream = null;
//        try {
//            OutputStream fileOutputStream = new FileOutputStream(str);
//            try {
//                str = new OutputStreamWriter(fileOutputStream, "utf-8");
//                str.write(str2, 0, str2.length());
//                str.flush();
//                str.close();
//                IOUtil.close(fileOutputStream);
//                return true;
//            } catch (IOException e) {
//                str = e;
//                outputStream = fileOutputStream;
//                try {
//                    str.printStackTrace();
//                    IOUtil.close(outputStream);
//                    return false;
//                } catch (Throwable th) {
//                    str = th;
//                    fileOutputStream = outputStream;
//                    IOUtil.close(fileOutputStream);
//                    throw str;
//                }
//            } catch (Throwable th2) {
//                str = th2;
//                IOUtil.close(fileOutputStream);
//                throw str;
//            }
//        } catch (IOException e2) {
//            str = e2;
//            str.printStackTrace();
//            IOUtil.close(outputStream);
//            return false;
//        }
//    }
//
//    public static String dealString(String str, byte b, String str2) throws Exception {
//        str = str.getBytes(str2);
//        for (int i = 0; i < str.length; i++) {
//            str[i] = (byte) (str[i] ^ b);
//        }
//        return new String(str, str2);
//    }
//}
