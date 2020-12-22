package com.darknet.bvw.net.retrofit.errorhandler;

import android.net.ParseException;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.R;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

public class ExceptionHandlerUtil {


    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int GATEWAY_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static Throwable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case GATEWAY_UNAVAILABLE:
                case GATEWAY_TIMEOUT:
                default:
                    ex.message = MyApp.getInstance().getString(R.string.network_error);
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;

        } else if (e instanceof JsonParseException || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.message = MyApp.getInstance().getString(R.string.analytical_error);
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, ERROR.NETWORK_ERROR);
            ex.message = MyApp.getInstance().getString(R.string.connect_failed);
            return ex;
        } else if (e instanceof SSLHandshakeException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.message = MyApp.getInstance().getString(R.string.verification_failed);
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = MyApp.getInstance().getString(R.string.connect_timeout);
            return ex;
        } else {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN);
            ex.message = MyApp.getInstance().getString(R.string.unknown_error);
            return ex;
        }


    }

    /**
     * 约定异常
     */
    public class ERROR {

        public static final int UNKNOWN = 1000;//未知错误
        public static final int PARSE_ERROR = 1001;//解析错误
        public static final int NETWORK_ERROR = 1002;//网络错误
        public static final int HTTP_ERROR = 1003;//协议出错
        public static final int SSL_ERROR = 1005;//证书出错
        public static final int TIMEOUT_ERROR = 1006;//连接超时
    }

    public static class ResponseThrowable extends Exception {
        int code;
        String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public static class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}
