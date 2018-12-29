package com.xy.bizport.share.lib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

public class CheckInstallShareApp {

    public static boolean canShare(Context context){
        if (context == null){
            return false;
        }
        try{
            PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
            if (isAliPayInstalled(context) || isWeixinInstalled(pinfo) || isQQInstalled(pinfo) || isSinaInstalled(pinfo)){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * 检测是否安装支付宝
     * @param context
     * @return
     */
    private static boolean isAliPayInstalled(Context context) {
        try{
            Uri uri = Uri.parse("alipays://platformapi/startApp");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            return componentName != null;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    private static boolean isWeixinInstalled(List<PackageInfo> pinfo) {
        try{
            if (pinfo == null || pinfo.size() == 0){
                return false;
            }
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    private static boolean isQQInstalled(List<PackageInfo> pinfo) {
        try{
            if (pinfo == null || pinfo.size() == 0){
                return false;
            }
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * sina
     * 判断是否安装新浪微博
     */
    private static boolean isSinaInstalled(List<PackageInfo> pinfo){
        try{
            if (pinfo == null || pinfo.size() == 0){
                return false;
            }
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.sina.weibo")) {
                    return true;
                }
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }
}
