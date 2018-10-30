package com.umeng.soexample;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class ShareTool {
    private Context mContext;
    public static ShareTool mTool = null ;

    private ShareTool(Context context){
        this.mContext = context;
    }

    public static ShareTool getInstance(Context context){
        if(mTool == null) {
            mTool = new ShareTool(context);
        }
        return mTool ;
    }

    public void share(SHARE_MEDIA platform, String url, String title, UMImage umImage, String description, UMShareListener shareListener){
        UMWeb web = new UMWeb("http://mobile.umeng.com/social");
        web.setTitle("This is music title");//标题
        web.setThumb(new UMImage(mContext, R.drawable.ic_launcher)); //缩略图
        web.setDescription("my description");//描述
        new ShareAction((Activity) mContext)
                .withMedia(web)
                .setPlatform(platform)
                .setCallback(shareListener).share();
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
            return "";
        }
    }
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
}
