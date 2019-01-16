package com.xy.bizport.share.lib;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.xy.bizport.androidcommon.util.LogManager;
import com.xy.bizport.share.lib.settings.ShareSettingsRepository;

import java.util.ArrayList;
import java.util.List;

public class BizportShare {

    public static BizportShare mBizportShare = null ;
    private static Context context;

    private BizportShare(){

    }

    public static BizportShare getInstance(){
        if(mBizportShare == null){
            synchronized (BizportShare.class){
                if(mBizportShare == null) {
                    mBizportShare = new BizportShare();
                }
            }
        }
        return mBizportShare ;
    }

    public static Context getApplicationContext() {
        return context;
    }

    /**
     *
     * @param context 上下文，该参数必须传入。
     * @param enableLog 设置分享的Log开关 参数: boolean 默认为false，如需查看LOG设置为true
     * @param enableEncrypt 设置日志加密， 参数：boolean 默认为false（不加密）
     * @param UMENG_APP_KEY 【友盟+】 AppKey，该参数必须传入。
     * @param UMENG_CHANNEL 【友盟+】 Channel，该参数必须传入。
     * @param WEIXIN_APP_ID 微信APP ID
     * @param WEIXIN_APP_SECRET 微信APP SECRET
     * @param SINA_APP_KEY 新浪微博APP KEY
     * @param SINA_APP_SECRET 新浪微博APP SECRET
     * @param SINA_CALLBACK 新浪微博后台的授权回调地址
     * @param QQ_APP_ID QQ APP ID
     * @param QQ_APP_KEY QQ APP KEY
     */
    public void initUmengShare(Context context, boolean enableLog, boolean enableEncrypt, String UMENG_APP_KEY, String UMENG_CHANNEL, String WEIXIN_APP_ID, String WEIXIN_APP_SECRET,
                               String SINA_APP_KEY, String SINA_APP_SECRET, String SINA_CALLBACK,
                               String QQ_APP_ID, String QQ_APP_KEY){
        try{
            BizportShare.context = context;
            UMConfigure.setLogEnabled(enableLog);
            UMConfigure.setEncryptEnabled(enableEncrypt);
            initUmengShare(context, UMENG_APP_KEY, UMENG_CHANNEL, WEIXIN_APP_ID, WEIXIN_APP_SECRET, SINA_APP_KEY, SINA_APP_SECRET, SINA_CALLBACK, QQ_APP_ID, QQ_APP_KEY);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     * @param context 上下文，该参数必须传入。
     * @param UMENG_APP_KEY 【友盟+】 AppKey，该参数必须传入。
     * @param UMENG_CHANNEL 【友盟+】 Channel，该参数必须传入。
     * @param WEIXIN_APP_ID 微信APP ID
     * @param WEIXIN_APP_SECRET 微信APP SECRET
     * @param SINA_APP_KEY 新浪微博APP KEY
     * @param SINA_APP_SECRET 新浪微博APP SECRET
     * @param SINA_CALLBACK 新浪微博后台的授权回调地址
     * @param QQ_APP_ID QQ APP ID
     * @param QQ_APP_KEY QQ APP KEY
     */
    private void initUmengShare(Context context, String UMENG_APP_KEY, String UMENG_CHANNEL, String WEIXIN_APP_ID, String WEIXIN_APP_SECRET,
                               String SINA_APP_KEY, String SINA_APP_SECRET, String SINA_CALLBACK,
                               String QQ_APP_ID, String QQ_APP_KEY){
        try{
            ShareSettingsRepository.getInstance().save(ShareConstant.CONSTANT_UMENG_APP_KEY, UMENG_APP_KEY);
            ShareSettingsRepository.getInstance().save(ShareConstant.CONSTANT_UMENG_CHANNEL, UMENG_CHANNEL);
            if (!TextUtils.isEmpty(UMENG_APP_KEY) && !TextUtils.isEmpty(UMENG_CHANNEL)){
                /**
                 * 初始化common库
                 * 参数1:上下文，不能为空
                 * 参数2:【友盟+】 AppKey，非必须参数，如果Manifest文件中已配置AppKey，该参数可以传空，则使用Manifest中配置的AppKey，否则该参数必须传入。
                 * 参数3:【友盟+】 Channel，非必须参数，如果Manifest文件中已配置Channel，该参数可以传空，则使用Manifest中配置的Channel，否则该参数必须传入，Channel命名请详见Channel渠道命名规范。
                 * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
                 * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
                 */
                UMConfigure.init(context, UMENG_APP_KEY, UMENG_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE,
                        null);
            }

            ShareSettingsRepository.getInstance().save(ShareConstant.CONSTANT_WEIXIN_APP_ID, WEIXIN_APP_ID);
            ShareSettingsRepository.getInstance().save(ShareConstant.CONSTANT_WEIXIN_APP_SECRET, WEIXIN_APP_SECRET);
            if (!TextUtils.isEmpty(WEIXIN_APP_ID) && !TextUtils.isEmpty(WEIXIN_APP_SECRET)){
                PlatformConfig.setWeixin(WEIXIN_APP_ID, WEIXIN_APP_SECRET);
            }

            ShareSettingsRepository.getInstance().save(ShareConstant.CONSTANT_SINA_APP_KEY, SINA_APP_KEY);
            ShareSettingsRepository.getInstance().save(ShareConstant.CONSTANT_SINA_APP_SECRET, SINA_APP_SECRET);
            ShareSettingsRepository.getInstance().save(ShareConstant.CONSTANT_SINA_CALLBACK, SINA_CALLBACK);
            if (!TextUtils.isEmpty(SINA_APP_KEY) && !TextUtils.isEmpty(SINA_APP_SECRET) && !TextUtils.isEmpty(SINA_CALLBACK)){
                PlatformConfig.setSinaWeibo(SINA_APP_KEY, SINA_APP_SECRET, SINA_CALLBACK);
            }

            ShareSettingsRepository.getInstance().save(ShareConstant.CONSTANT_QQ_APP_ID, QQ_APP_ID);
            ShareSettingsRepository.getInstance().save(ShareConstant.CONSTANT_QQ_APP_KEY, QQ_APP_KEY);
            if (!TextUtils.isEmpty(QQ_APP_ID) && !TextUtils.isEmpty(QQ_APP_KEY)){
                PlatformConfig.setQQZone(QQ_APP_ID, QQ_APP_KEY);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

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
    public void share(Context context, ShareMedia platform, String url, String title, ShareImage umImage, String content, UMShareListener shareListener){
        LogManager.e("pyj", "BizportShare share sharetitle = " + title
                + ","+ "\n"+" shareContent = " + content
                + ","+ "\n"+" shareUrl = " + url
                + ","+ "\n"+" shareUMImage = " + umImage
                + ","+ "\n"+" SHARE_MEDIA = " + platform.toString());
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(umImage); //缩略图
        web.setDescription(content);//描述
        new ShareAction((Activity) context)
                .withMedia(web)
                .setPlatform(ShareMedia.getUmengMedia(platform))
                .setCallback(shareListener).share();
    }

    public ShareBean getShareData(Context context, String sharetitle, String shareContent, String shareUrl, String imageUrl){
        if(TextUtils.isEmpty(shareContent)){
            shareContent = sharetitle;
        }

        if (TextUtils.isEmpty(sharetitle) || TextUtils.isEmpty(shareUrl)
                || TextUtils.isEmpty(imageUrl)){
            Toast.makeText(context,"分享数据为空",Toast.LENGTH_SHORT).show();
            return null;
        }
        ShareBean shareBean= new ShareBean(getSharePlatformList(context), shareUrl, sharetitle, shareContent, imageUrl);
        LogManager.e("pyj", "---------------------------------------------------------------------------------"
                + "\n"
                + "---------------------------------------------------------------------------------"+ "\n"
                + "---------------------------------------------------------------------------------");
        LogManager.e("pyj", "BizportShare getShareData sharetitle = "+shareBean.getSharetitle()
                + ","+ "\n"+" shareContent = " + shareBean.getShareContent()
                + ","+ "\n"+" shareUrl = " + shareBean.getShareUrl()
                + ","+ "\n"+" shareImageUrl = " + shareBean.getImageUrl());
        if (!TextUtils.isEmpty(imageUrl)){
            LogManager.e("pyj", "BizportShare getShareData getImageBitmap imageUrl = "+imageUrl);
            getImageBitmap(context, imageUrl, shareBean, 100, 100);
        }

        return shareBean;
    }

    private void getImageBitmap(final Context context, final String image, final ShareBean shareBean, final int width, final int height) {
        Glide.with(context.getApplicationContext())
                .load(image)
                .asBitmap()
                .toBytes()
                .into(new SimpleTarget<byte[]>(width, height) {
                    @Override
                    public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                        // 下载成功回调函数
                        LogManager.e("pyj", "BizportShare getImageBitmap onResourceReady");
                        shareBean.setUmImage(new ShareImage(context, bytes));
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // 下载失败回调
                        LogManager.e("pyj", "BizportShare getImageBitmap onLoadFailed");
                    }
                });
    }

    public void getImageBitmap(final Context context, final String image, final ShareBean shareBean, final int width, final int height, final IDownloadBitmapCallBack callBack) {
        Glide.with(context.getApplicationContext())
                .load(image)
                .asBitmap()
                .toBytes()
                .into(new SimpleTarget<byte[]>(width, height) {
                    @Override
                    public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                        // 下载成功回调函数
                        LogManager.e("pyj", "BizportShare getImageBitmap onResourceReady");
                        if (callBack != null){
                            callBack.downloadBitmapCallBack(0, bytes, null);
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // 下载失败回调
                        LogManager.e("pyj", "BizportShare getImageBitmap onLoadFailed");
                        if (callBack != null){
                            callBack.downloadBitmapCallBack(1, null, e.toString());
                        }
                    }
                });
    }

    private List<SharePlatform> getSharePlatformList(Context context) {
        List<SharePlatform> sharePlatformList = new ArrayList<>();

        if (!TextUtils.isEmpty(ShareSettingsRepository.getInstance().getString(ShareConstant.CONSTANT_WEIXIN_APP_ID, ""))
                && !TextUtils.isEmpty(ShareSettingsRepository.getInstance().getString(ShareConstant.CONSTANT_WEIXIN_APP_SECRET, ""))
                && isInstall(context, SHARE_MEDIA.WEIXIN)){
            sharePlatformList.add(new SharePlatform(ShareMedia.WEIXIN));
            sharePlatformList.add(new SharePlatform(ShareMedia.WEIXIN_CIRCLE));
        }


        if (!TextUtils.isEmpty(ShareSettingsRepository.getInstance().getString(ShareConstant.CONSTANT_QQ_APP_ID, ""))
                && !TextUtils.isEmpty(ShareSettingsRepository.getInstance().getString(ShareConstant.CONSTANT_QQ_APP_KEY, ""))
                && isInstall(context, SHARE_MEDIA.QQ)){
            sharePlatformList.add(new SharePlatform(ShareMedia.QQ));
            sharePlatformList.add(new SharePlatform(ShareMedia.QZONE));
        }

        if (!TextUtils.isEmpty(ShareSettingsRepository.getInstance().getString(ShareConstant.CONSTANT_SINA_APP_KEY, ""))
                && !TextUtils.isEmpty(ShareSettingsRepository.getInstance().getString(ShareConstant.CONSTANT_SINA_APP_SECRET, ""))
                && !TextUtils.isEmpty(ShareSettingsRepository.getInstance().getString(ShareConstant.CONSTANT_SINA_CALLBACK, ""))
                && isInstall(context, SHARE_MEDIA.SINA)){
            sharePlatformList.add(new SharePlatform(ShareMedia.SINA));
        }

        return sharePlatformList;
    }

    private boolean isInstall(Context context, SHARE_MEDIA shareMedia){
        if (context == null || !(context instanceof Activity)){
            return false;
        }
        return UMShareAPI.get(context).isInstall((Activity) context, shareMedia);
    }


}
