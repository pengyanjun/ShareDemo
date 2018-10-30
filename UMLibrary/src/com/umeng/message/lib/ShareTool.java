package com.umeng.message.lib;

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
        if(mTool == null){
            synchronized (ShareTool.class){
                if(mTool == null) {
                    mTool = new ShareTool(context);
                }
            }
        }
        return mTool ;
    }

    /**
     *
     * @param platform 分享平台
     * @param url 分享的链接
     * @param title 分享的标题
     * @param umImage 分享的图片
     * @param content 分享的文本内容
     * @param shareListener 分享结果回调
     */
    public void share(SHARE_MEDIA platform, String url, String title, UMImage umImage, String content, UMShareListener shareListener){
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(umImage); //缩略图
        web.setDescription(content);//描述
        new ShareAction((Activity) mContext)
                .withMedia(web)
                .setPlatform(platform)
                .setCallback(shareListener).share();
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
