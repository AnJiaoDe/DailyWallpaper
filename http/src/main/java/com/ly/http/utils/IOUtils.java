package com.ly.http.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 * Created by cy on 2018/12/24.
 */

public class IOUtils {
    private boolean isRunning = true;
    private String encodeType = "utf-8";

    public IOUtils() {
        isRunning = true;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.isRunning = false;
    }

    public void read2String(long contentLength, InputStream inputStream, IOListener ioListener) {

        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, encodeType);
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            int len = 0;
            long current = 0;
            int percent = 0;
            int percentLast = 0;
            while (isRunning && (len = bufferedReader.read(buffer)) != -1) {
                sb.append(buffer, 0, len);
                current += len;
                percent = (int) (current * 1f / contentLength * 100);
                if (percent - percentLast >= 1) {
                    percentLast = percent;
                    ioListener.onLoading(buffer, percent, current, contentLength);
                }
            }

            //中断
            if (len != -1) {
                ioListener.onInterrupted(buffer, percent, current, contentLength);

            } else {
                ioListener.onCompleted(sb.toString());

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ioListener.onFail("网络请求失败UnsupportedEncodingException" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            ioListener.onFail("网络请求失败IOException" + e.getMessage());

        } finally {
            close(bufferedReader);
            close(inputStreamReader);
            close(inputStream);
        }
    }


    public void read2File(String filePath, long contentLength, InputStream inputStream, IOListener ioListener) {
        File file;
        try {
            file = FileUtils.createFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            ioListener.onFail("文件创建失败，请检查路径是否合法以及读写权限");
            return;
        }
        if (file.length() == contentLength) {
            //有缓存
            LogUtils.log("缓存");
            ioListener.onCompleted(file);
            return;
        }
        RandomAccessFile randomAccessFile = null;
//        FileOutputStream fileOutputStream = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");

//            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            try {
                int len = 0;
                long current = file.length();
                int percent = (int) (current * 1f / contentLength * 100);
                int percentLast = percent;
                randomAccessFile.seek(current);
                while (isRunning && (len = inputStream.read(buffer)) != -1) {
                    randomAccessFile.write(buffer, 0, len);
                    current += len;
                    percent = (int) (current * 1f / contentLength * 100);
                    if (percent - percentLast >= 1) {
                        percentLast = percent;
                        LogUtils.log("当前线程"+Thread.currentThread().getName(),percent+"%");

                        ioListener.onLoading(buffer, percent, current, contentLength);
                    }
                }
                //中断
                if (len != -1) {
                    ioListener.onInterrupted(buffer, percent, current, contentLength);
                    return;
                }
                if (file.length() == contentLength) ioListener.onCompleted(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ioListener.onFail("文件创建失败，请检查路径是否合法以及读写权限,FileNotFoundException" + e.getMessage());

        } finally {
            close(randomAccessFile);
            close(inputStream);
        }
    }

    public void read2ByteArray(long contentLength, InputStream inputStream, IOListener ioListener) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        try {
            int len = 0;
            long current = 0;
            int percent = 0;
            int percentLast = 0;
            while (isRunning && (len = inputStream.read(buffer)) != -1) {

                byteArrayOutputStream.write(buffer, 0, len);
                current += len;
                percent = (int) (current * 1f / contentLength * 100);
                if (percent - percentLast >= 1) {
                    percentLast = percent;
                    ioListener.onLoading(buffer, percent, current, contentLength);
                }
            }

            byteArrayOutputStream.flush();
            //中断
            if (len != -1) {
                ioListener.onInterrupted(buffer, percent, current, contentLength);
                return;
            }
            if (byteArrayOutputStream.toByteArray().length > 0) {

                ioListener.onCompleted(byteArrayOutputStream.toByteArray());
            } else {
                ioListener.onFail("可能是文件损坏等原因");
            }
        } catch (IOException e) {
            e.printStackTrace();
            ioListener.onFail("网络请求失败,IOException" + e.getMessage());

        } finally {
            close(byteArrayOutputStream);
            close(inputStream);
        }
    }
}
