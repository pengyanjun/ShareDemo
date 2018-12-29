package com.xy.bizport.share.lib;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ShareTool {

    /**
     * 读取资源图片
     *
     * @param ResourceId
     * @return 资源字符串
     */
    public static Drawable getDrawable(Context context, int ResourceId) {
        try {
            return context.getResources().getDrawable(ResourceId);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 读取资源字符串
     *
     * @param ResourceId
     * @return 资源字符串
     */
    public static String getString(Context context, int ResourceId) {
        try {
            return context.getResources().getString(ResourceId);
        } catch (Exception e) {
            return null;
        }
    }

}
