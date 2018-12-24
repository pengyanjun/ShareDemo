package com.xy.bizport.share.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {
    public static final int TRACE_NONE = 0;
    public static final int TRACE_ERROR = 2;
    public static final int TRACE_DEBUG = 3;

    private static int traceLevel = TRACE_NONE;

    private static File logFile;

    public static void setTraceLevel(int level) {
        traceLevel = level;
    }

    public static void e(String tag, String format, Object... args) {
        if (traceLevel >= TRACE_ERROR) {
            String message = format(format, args);
            Log.e(tag, format(format, args));
            saveLog(tag, message);
        }
    }


    private static String format(String format, Object... args) {
        try {
            if (args.length == 0) {
                return format;
            }
            return String.format(format, args);
        }
        catch (Exception e) {
            return format;
        }
    }

    private static void saveLog(final String tag, final String message) {
        String dir = createRecordFileDir();
        if (TextUtils.isEmpty(dir)){
            return;
        }
        if (logFile == null) {
            logFile = new File(dir, "log-1.txt");
        }
        writeString(logFile, getCurrentDateToString().toString() + "  " + tag + " :: " + message + "\n", true);
    }

    public static void writeString(File file, String content, boolean append) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file, append);
            outputStream.write(content.getBytes(Charset.forName("UTF-8")));
        }
        catch (IOException ignored) {}
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (Exception e) {
                }
            }
        }
    }
    /**
     * 创建录音文件目录
     */
    public static String createRecordFileDir() {
        String dir = null;
        try{
            String status = Environment.getExternalStorageState();
            if (status.equals(Environment.MEDIA_MOUNTED)){
                File rootDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pyj-1");
                if(!rootDir.exists()){
                    rootDir.mkdirs();
                }
                File rootDir1 = new File(rootDir.getAbsolutePath() + "/log-1");
                if(!rootDir1.exists()){
                    rootDir1.mkdirs();
                }
                dir = rootDir1.getAbsolutePath();

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return dir;
    }

    /**
     * 获取当前日期，格式为"yyyy-MM-dd HH:mm:ss SSS"
     * @return
     */
    public static String getCurrentDateToString() {
        DateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String date = null;
        try {
            date = dd.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
