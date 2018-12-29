package com.xy.bizport.share.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SPUtils {

    /**
     * 获取保存的值
     */
    public static String getSharedPreferencesValue(Context context, String key)
    {
        if(TextUtils.isEmpty(key) || context == null){
            return "";
        }
        String returnStr = "";
        try{
            SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(
                    "share_info", 0);
            returnStr = sharedPreferences.getString(key, "");
        }catch(Exception e){
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * 保存信息
     * @param value
     */
    public static void saveSharedPreferencesValue(Context context, String key,String value)
    {
        if(TextUtils.isEmpty(key) || context == null){
            return;
        }
        try{
            SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(
                    "share_info", 0);
            SharedPreferences.Editor editor1 = sharedPreferences.edit();
            editor1.putString(key, value);
            editor1.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
