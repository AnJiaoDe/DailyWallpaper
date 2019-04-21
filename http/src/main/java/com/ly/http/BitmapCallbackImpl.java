package com.ly.http;

import android.content.Context;
import android.graphics.Bitmap;

import com.ly.http.utils.BitmapUtils;
import com.ly.http.utils.FileUtils;
import com.ly.http.utils.IOListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public abstract class BitmapCallbackImpl extends Callback<Bitmap> {

    private String cachePath;
    private boolean cache = true;
    private int reqWidth;
    private int reqHeight;
    private String url;
    private Context context;

    public BitmapCallbackImpl(Context context) {
        this.context = context;
    }

    public BitmapCallbackImpl cachePath(String cachePath) {
        this.cachePath = cachePath;
        return this;
    }

    public BitmapCallbackImpl cache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public BitmapCallbackImpl reqWidth(int reqWidth) {
        this.reqWidth = reqWidth;
        return this;
    }

    public BitmapCallbackImpl reqHeight(int reqHeight) {
        this.reqHeight = reqHeight;
        return this;
    }

    public BitmapCallbackImpl url(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public int getReqHeight() {
        return reqHeight;
    }

    public int getReqWidth() {
        return reqWidth;
    }

    public boolean isCache() {
        return cache;
    }

    public String getCachePath() {
        return cachePath;
    }

    @Override
    public void convertSuccess(final long contentLenth, InputStream inputStream) {
        ioUtils.read2ByteArray(contentLenth, inputStream, new IOListener<byte[]>() {
                    @Override
                    public void onCompleted(byte[] result) {
                        Bitmap bitmap = BitmapUtils.decodeBitmapFromBytes(result, reqWidth, reqHeight);
                        if (bitmap != null && bitmap.getWidth() > 0) {

                            if (cache) {
                                if (cachePath == null) {
                                    if (url != null && url.contains("/")) {
                                        int index_ = url.lastIndexOf("/");
                                        int indexDot = url.lastIndexOf(".");
                                        url = url.substring(index_ + 1, indexDot > index_ ? indexDot : url.length()) + ".png";
                                        cachePath = context.getCacheDir().getAbsolutePath() + "/" + url;
                                    }
                                }
                                try {
                                    File file = FileUtils.createFile(cachePath);
                                    BitmapUtils.saveBitmapToFile(bitmap, file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            callOnSuccess(bitmap);

                        } else {
                            callOnFail("图片下载失败");
                        }
                    }

                    @Override
                    public void onLoading(Object readedPart, int percent, long current, long length) {

                        callOnLoading(readedPart, percent, current, length);
                    }

                    @Override
                    public void onInterrupted(Object readedPart, int percent, long current, long length) {

                        callOnCancel(readedPart, percent, current, length);
                    }

                    @Override
                    public void onFail(String errorMsg) {
                        callOnFail(errorMsg);

                    }
                }

        );
    }
}
