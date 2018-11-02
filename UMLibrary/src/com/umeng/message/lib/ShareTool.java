package com.umeng.message.lib;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShareTool {
    public static final String SHARE_TITLE = "title";
    public static final String SHARE_CONTENT = "content";
    public static final String SHARE_IMAGE = "image";
    public static final String SHARE_URL = "url";
    public static ShareTool mTool = null ;
    private SharePopupWindow sharePopupWindow;

    private ShareTool(){

    }

    public static ShareTool getInstance(){
        if(mTool == null){
            synchronized (ShareTool.class){
                if(mTool == null) {
                    mTool = new ShareTool();
                }
            }
        }
        return mTool ;
    }

    /**
     *
     * @param context
     * @param platform 分享平台
     * @param url 分享的链接
     * @param title 分享的标题
     * @param umImage 分享的图片
     * @param content 分享的文本内容
     * @param shareListener 分享结果回调
     */
    public void share(Context context, SHARE_MEDIA platform, String url, String title, UMImage umImage, String content, UMShareListener shareListener){

        Log.e("pyj","\n"+"platform = "+platform  + ","+ "\n"+" title = " + title + ","+ "\n"+" content = " + content + ","+ "\n"+" url = " + url + ","+ "\n"+" image = " + umImage.asUrlImage());
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(umImage); //缩略图
        web.setDescription(content);//描述
        new ShareAction((Activity) context)
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

    /**
     * 显示分享面板
     * @param context
     * @param title 分享的标题
     * @param content 分享的文本内容
     * @param url 分享的链接
     * @param image 分享的图片链接地址
     * @throws Exception
     */
    public void showSharePopupWindow(Context context, String title, String content, String url, String image) throws Exception{
        JSONObject shareData = new JSONObject();
        shareData.put(SHARE_TITLE, title);
        shareData.put(SHARE_CONTENT, content);
        shareData.put(SHARE_URL, url);
        shareData.put(SHARE_IMAGE, image);
        showSharePopupWindow(context, shareData);
    }

    private void showSharePopupWindow(Context context, JSONObject jsonObject){
        if (!(context instanceof Activity) || !(context instanceof UMShareListener)) {
            return;
        }
        Activity activity = (Activity)context;
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (sharePopupWindow == null){
            sharePopupWindow = new SharePopupWindow(activity);
        }

        ShareBean shareBean = getShareData(context, jsonObject);
        if (shareBean == null || shareBean.getPlatforms() == null || shareBean.getPlatforms().size() == 0){
            return;
        }

        sharePopupWindow.setShareBean(shareBean);

        sharePopupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public ShareBean getShareData(Context context, JSONObject data){
        if (data == null || data.length() == 0) {
            return null;
        }

        //分享的图片，UMImage的构建有如下几种形式
        //UMImage image = new UMImage(ShareActivity.this, "imageurl");//网络图片
        //UMImage image = new UMImage(ShareActivity.this, file);//本地文件
        //UMImage image = new UMImage(ShareActivity.this, R.drawable.xxx);//资源文件
        //UMImage image = new UMImage(ShareActivity.this, bitmap);//bitmap文件
        //UMImage image = new UMImage(ShareActivity.this, byte[]);//字节流
        String sharetitle = data.optString(SHARE_TITLE);
        String shareContent = data.optString(SHARE_CONTENT);
        String shareUrl = data.optString(SHARE_URL);
        String image = data.optString(SHARE_IMAGE);

        if(TextUtils.isEmpty(shareContent)){
            shareContent = sharetitle;
        }

        if (TextUtils.isEmpty(sharetitle) || TextUtils.isEmpty(shareContent) || TextUtils.isEmpty(shareUrl)
                || TextUtils.isEmpty(image)){
            return null;
        }
        UMImage umImage = new UMImage(context, image);//网络图片

        ShareBean shareBean= new ShareBean(getSharePlatformList(), shareUrl, sharetitle, shareContent, umImage);

        return shareBean;
    }

    private List<SnsPlatform> getSharePlatformList() {
        List<SnsPlatform> sharePlatformList = new ArrayList<>();
        sharePlatformList.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        sharePlatformList.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        sharePlatformList.add(SHARE_MEDIA.QQ.toSnsPlatform());
        sharePlatformList.add(SHARE_MEDIA.QZONE.toSnsPlatform());
        sharePlatformList.add(SHARE_MEDIA.SINA.toSnsPlatform());
        sharePlatformList.add(SHARE_MEDIA.ALIPAY.toSnsPlatform());
        return sharePlatformList;
    }
}
