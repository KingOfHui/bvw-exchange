//package com.darknet.bvw.socket;
//
//import android.util.Log;
//
//import com.neovisionaries.ws.client.WebSocket;
//import com.neovisionaries.ws.client.WebSocketAdapter;
//import com.neovisionaries.ws.client.WebSocketException;
//import com.neovisionaries.ws.client.WebSocketExtension;
//import com.neovisionaries.ws.client.WebSocketFactory;
//import com.neovisionaries.ws.client.WebSocketFrame;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.List;
//import java.util.Map;
//
//public class EchoClient {
//    private final String TAG = this.getClass().getSimpleName();
//    /**
//     * The echo server on websocket.org.
//     */
//    private static final String SERVER = "wss://ws-test.bvw.im/websocket";
////    private static final String SERVER = "wss://https://ws-test.bvw.im/websocket";
//
//    /**
//     * The timeout value in milliseconds for socket connection.
//     */
//    private static final int TIMEOUT = 5000;
//
//    private WsListener mListener;
//
//
//    /**
//     * The entry point of this command line application.
//     */
//    public  void connectClient() throws Exception {
//        // Connect to the echo server.
//        WebSocket ws = connect();
//
//        // The standard input via BufferedReader.
//        BufferedReader in = getInput();
//
//        // A text read from the standard input.
//        String text;
//
//        // Read lines until "exit" is entered.
//        while ((text = in.readLine()) != null) {
//            // If the input string is "exit".
//            if (text.equals("exit")) {
//                // Finish this application.
//                break;
//            }
//
//            // Send the text to the server.
//            ws.sendText(text);
//        }
//
//        // Close the WebSocket.
//        ws.disconnect();
//    }
//
//
//    /**
//     * Connect to the server.
//     */
//    private  WebSocket connect() throws IOException, WebSocketException {
//        mListener = new WsListener();
//
//        return new WebSocketFactory()
//                .setConnectionTimeout(TIMEOUT)
//                .createSocket(SERVER)
//                .addListener(mListener)
////                .addListener(new WebSocketAdapter() {
////                    // A text message arrived from the server.
////                    public void onTextMessage(WebSocket websocket, String message) {
////                        System.out.println(message);
////                    }
////                })
//                .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
//                .connectAsynchronously();
//    }
//
//
//    /**
//     * Wrap the standard input with BufferedReader.
//     */
//    private static BufferedReader getInput() throws IOException {
//        return new BufferedReader(new InputStreamReader(System.in));
//    }
//
//
//    /**
//     * websocket连接监听
//     */
//    class WsListener extends WebSocketAdapter {
//
//        @Override
//        public void onTextMessage(WebSocket websocket, String text) throws Exception {
//            super.onTextMessage(websocket, text);
//            Log.i(TAG, "onTextMessage: 接收到的推送消息" + text);
//            parseNotifyTransaction(text);
//        }
//
//        @Override
//        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
//            super.onConnected(websocket, headers);
//            Log.i(TAG, "onConnected: 连接成功");
////            setStatus(WsStatus.CONNECT_SUCCESS);
////            cancleReconnect();
////            startHeartbet();
//        }
//
//        @Override
//        public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
//            super.onConnectError(websocket, exception);
//            Log.i(TAG, "onConnectError: 连接异常"+exception.getMessage());
////            setStatus(WsStatus.CONNECT_FAIL);
////            reconnect();
//        }
//
//        @Override
//        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
//            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
//            Log.i(TAG, "onDisconnected: 断开连接");
////            setStatus(WsStatus.CONNECT_FAIL);
////            reconnect();
//        }
//    }
//
//    /**
//     * @param text json 数据，根据json进行解析使用即可
//     */
//    private void parseNotifyTransaction(String text) {
//        Log.i(TAG, "parseNotifyTransaction:" + text);
//    }
//
//
//}
