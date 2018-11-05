package com.umeng.message.lib;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
    public static final String SHARE_IMAGE_URL = "image_url";
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

        Log.e("pyj","\n"+"platform = "+platform  + ","+ "\n"+" title = " + title + ","+ "\n"+" content = " + content + ","+ "\n"+" url = " + url + ","+ "\n"+" image = " + umImage.asBitmap());
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(umImage); //缩略图
        web.setDescription(content);//描述
        new ShareAction((Activity) context)
                .withMedia(web)
                .setPlatform(platform)
                .setCallback(shareListener).share();
        Log.e("pyj","share 111111111111111111111");
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
        shareData.put(SHARE_IMAGE_URL, image);
        showSharePopupWindow(context, shareData);
    }

    private void showSharePopupWindow(Context context, JSONObject jsonObject){
        if (!(context instanceof Activity) || !(context instanceof UMShareListener)) {
            Log.e("pyj","showSharePopupWindow 当前Activity未实现UMShareListener");
            return;
        }
        Activity activity = (Activity)context;
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (sharePopupWindow == null){
            sharePopupWindow = new SharePopupWindow(activity);
        }

        ShareBean shareBean = getShareData(context, jsonObject);
        Log.e("pyj","showSharePopupWindow getShareData shareBean = " + shareBean);
        if (shareBean == null || shareBean.getPlatforms() == null || shareBean.getPlatforms().size() == 0){
            Log.e("pyj","showSharePopupWindow 分享平台为空");
            return;
        }

        sharePopupWindow.setShareBean(shareBean);

        sharePopupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public ShareBean getShareData(Context context, JSONObject data){
        if (data == null || data.length() == 0) {
            return null;
        }

        String sharetitle = data.optString(SHARE_TITLE);
        String shareContent = data.optString(SHARE_CONTENT);
        String shareUrl = data.optString(SHARE_URL);
        String imageUrl = data.optString(SHARE_IMAGE_URL);

        if(TextUtils.isEmpty(shareContent)){
            shareContent = sharetitle;
        }

        if (TextUtils.isEmpty(sharetitle) || TextUtils.isEmpty(shareContent) || TextUtils.isEmpty(shareUrl)
                || TextUtils.isEmpty(imageUrl)){
            Log.e("pyj","getShareData sharetitle = " + sharetitle);
            Log.e("pyj","getShareData shareContent = " + shareContent);
            Log.e("pyj","getShareData shareUrl = " + shareUrl);
            Log.e("pyj","getShareData imageUrl = " + imageUrl);
            Toast.makeText(context,"分享数据为空",Toast.LENGTH_SHORT).show();
            return null;
        }
        ShareBean shareBean= new ShareBean(getSharePlatformList(), shareUrl, sharetitle, shareContent, imageUrl);
        if (!TextUtils.isEmpty(imageUrl)){
            getImageBitmap(context, imageUrl, shareBean, 100, 100);
        }

        return shareBean;
    }

    private List<SnsPlatform> getSharePlatformList() {
        List<SnsPlatform> sharePlatformList = new ArrayList<>();
        sharePlatformList.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        sharePlatformList.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        sharePlatformList.add(SHARE_MEDIA.QQ.toSnsPlatform());
        sharePlatformList.add(SHARE_MEDIA.QZONE.toSnsPlatform());
        sharePlatformList.add(SHARE_MEDIA.SINA.toSnsPlatform());
        sharePlatformList.add(SHARE_MEDIA.ALIPAY.toSnsPlatform());
        return sharePlatformList;
    }

    public void getImageBitmap(final Context context, final String image, final ShareBean shareBean, final int width, final int height) {
        Glide.with(context.getApplicationContext())
                .load(image)
                .asBitmap()
                .toBytes()
                .into(new SimpleTarget<byte[]>(width, height) {
                    @Override
                    public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                        // 下载成功回调函数
                        Log.e("pyj","onResourceReady bytes = " + bytes);
                        shareBean.setUmImage(new UMImage(context, bytes));
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // 下载失败回调
                    }
                });
    }

    //        final CountDownLatch startSignal = new CountDownLatch(1);

//        SimpleTarget target = new SimpleTarget<Bitmap>(width,height) {
//
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                try{
//                    Log.e("pyj","onResourceReady Thread = " + Thread.currentThread().getName());
//                    Log.e("pyj","onResourceReady resource = " + resource);
//                    shareBean.setImage(new UMImage(context, resource));
//                    startSignal.countDown();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        try{
//            Log.e("pyj","getImageBitmap Thread = " + Thread.currentThread().getName());
//            Glide.with(context.getApplicationContext())
//                    .load(image).asBitmap().toBytes();
//            startSignal.await(15, SECONDS);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        Schedulers.net().execute(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Log.e("pyj","onResourceReady Thread = " + Thread.currentThread().getName());
//                    Bitmap bitmap = Glide.with(context.getApplicationContext())
//                            .load(image).asBitmap().into(width, height).get();
//                    Log.e("pyj","onResourceReady bitmap = " + bitmap);
//                    shareBean.setImage(new UMImage(context, bitmap));
//                    startSignal.countDown();
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//        try{
//            startSignal.await(10, SECONDS);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    public void clearCache(){
        try{
            if (sharePopupWindow != null){
                if (sharePopupWindow.isShowing()){
                    sharePopupWindow.dismiss();
                }
                sharePopupWindow = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
