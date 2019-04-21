//package com.ly.http;
//
//
//import com.ly.http.utils.IOUtils;
//import com.ly.http.utils.LogUtils;
//import com.ly.http.utils.SSLUtils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.lang.reflect.Field;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.util.Map;
//
///**
// * Created by Administrator on 2018/12/21 0021.
// */
//
//public class CallProtoBufEnqueueImpl<T> extends Call<T> {
//
//    private Request request;
//    private CallThread callThread;
//    private Callback callback;
//    private IOUtils ioUtils;
//
//    private byte[] bytesWrite = null;
//
//    public CallProtoBufEnqueueImpl(Request request) {
//        this.request = request;
//    }
//
//
//    @Override
//    public void cancel() {
//        if (callThread != null) {
//            ioUtils.stop();
//            callThread = null;
//        }
//
//    }
//
//    @Override
//    public Request getRequest() {
//        return request;
//    }
//
//    @Override
//    public void protobuf(byte[] bytes, Callback<T> callback) {
//        cancel();
//        this.bytesWrite = bytes;
//        if (bytesWrite == null || bytesWrite.length == 0) return;
//        if (callback == null) return;
//        this.callback = callback;
//        callThread = new CallThread();
//        this.ioUtils = new IOUtils();
//        callThread.start();
//    }
//
//
//    private class CallThread extends Thread {
//
//
//        @Override
//
//        public void run() {
//
//            HttpURLConnection httpURLConnection = null;
//            InputStream inputStream = null;
//            try {
//                URL url = new URL(request.getUrl());
//                httpURLConnection = (HttpURLConnection) url.openConnection();
//                SSLUtils.trustAllSSL(httpURLConnection);
//
//                Map<String, String> header = request.getHeader();
//                for (String key : header.keySet()) {
//                    LogUtils.log(key,header.get(key));
//                    httpURLConnection.setRequestProperty(key, header.get(key));
//                }
//                httpURLConnection.setRequestProperty("Content-Type", "application/x-protobuf");
//                httpURLConnection.setRequestProperty("Accept", "application/x-protobuf");
//                // 设置请求方式
//                httpURLConnection.setRequestMethod(request.getMethod());
//                httpURLConnection.setDoOutput(true);
//                if (bytesWrite != null){
//                    OutputStream outputStream=httpURLConnection.getOutputStream();
//                    outputStream.write(bytesWrite);
//                    outputStream.flush();
//                    outputStream.close();
//                }
//
//                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    inputStream = httpURLConnection.getInputStream();
//                    callSuccess(callback, inputStream);
//
//                } else {
//                    callFail(callback, httpURLConnection.getResponseMessage());
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                callFail(callback, "网络请求失败MalformedURLException，" + e.getMessage());
//
//            } catch (ProtocolException e) {
//
//                try {
//                    Field methodField = HttpURLConnection.class.getDeclaredField("method");
//                    methodField.setAccessible(true);
//                    methodField.set(httpURLConnection, request.getMethod());
//                } catch (NoSuchFieldException e1) {
//                    e1.printStackTrace();
//                    callFail(callback, "网络请求失败NoSuchFieldException，" + e1.getMessage());
//
//                } catch (IllegalAccessException e2) {
//                    e2.printStackTrace();
//                    callFail(callback, "网络请求失败IllegalAccessException，" + e2.getMessage());
//
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//
//                callFail(callback, "网络请求失败IOException，" + e.getMessage());
//
//            } finally {
//                if (httpURLConnection != null) {
//                    httpURLConnection.disconnect();
//                }
////                IOUtils.close(inputStream);//贼鸡儿尴尬的bug
//                HttpUtils.getInstance().removeCall(CallProtoBufEnqueueImpl.this);
//            }
//        }
//
//    }
//
////    protected void callSuccess(final Callback callback, final Object response) {
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                callback.onSuccess(response);
////
////            }
////        });
////    }
////
////    protected void callOnLoading(final Callback callback, final long current, final long length) {
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                callback.onLoading(current, length);
////
////            }
////        });
////    }
////
////    protected void callFail(final Callback callback, final String ErrorMsg) {
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                callback.onFail(ErrorMsg);
////
////            }
////        });
////    }
////
////    protected void runOnUiThread(Runnable run) {
////        HttpUtils.getInstance().getHandler_deliver().post(run);
////    }
//
//
//}
