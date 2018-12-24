package com.xy.bizport.share;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.xy.bizport.share.net.NetStateUtils;
import com.xy.bizport.share.utils.LogManager;


public class SharePopupWindow extends PopupWindow {

	private View mMenuView;
	private Context mContext;
	/**
	 * 分享结果回调
	 */
	private UMShareListener shareListener;
	private TextView cancelBtn;
	private ShareNoScroolGridView shareGridView;
	private SharePopAdapter sharePopAdapter;
	private ShareBean shareBean;


	/**
	 * 分享面板
	 * @param context 传Activity的Context
	 */
	public SharePopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context ;
		this.shareListener = (UMShareListener)context ;

        mMenuView = inflater.inflate(R.layout.share_popwindow, null);
        initView();

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.anim.share_pop_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

              int height = mMenuView.findViewById(R.id.share_content_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

	public void setShareBean(ShareBean shareBean) {
		this.shareBean = shareBean;
		sharePopAdapter.notifyDataChage(this.shareBean);
	}

	private void initView(){
		shareGridView = (ShareNoScroolGridView)mMenuView.findViewById(R.id.share_gv);
		sharePopAdapter = new SharePopAdapter(null, mContext);
		shareGridView.setAdapter(sharePopAdapter);
		shareGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dealShareClick(position);
			}
		});

		cancelBtn = (TextView) mMenuView.findViewById(R.id.cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					dismiss();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});

	}

	private void dealShareClick(int position){
		try{
			//接入时，要判断网络，如果无网络则弹出提示
			if (!NetStateUtils.isNetworkConnected(mContext)){
				Toast.makeText(mContext, ShareTool.getString(mContext, R.string.share_network_disabled),Toast.LENGTH_SHORT).show();
				return;
			}
			if (shareBean == null || shareBean.getPlatforms() == null || shareBean.getPlatforms().size() == 0){
				Toast.makeText(mContext,"分享数据为空",Toast.LENGTH_SHORT).show();
				return;
			}
			final SnsPlatform platform = (SnsPlatform)sharePopAdapter.getItem(position);

			if (platform.mPlatform == null){
				Toast.makeText(mContext,"分享平台为空",Toast.LENGTH_SHORT).show();
				return;
			}

			LogManager.e("pyj", "---------------------------------------------------------------------------------"
					+ "\n"
					+ "---------------------------------------------------------------------------------"+ "\n"
					+ "---------------------------------------------------------------------------------");
			LogManager.e("pyj", "SharePopupWindow dealShareClick sharetitle = "+shareBean.getSharetitle()
					+ ","+ "\n"+" shareContent = " + shareBean.getShareContent()
					+ ","+ "\n"+" shareUrl = " + shareBean.getShareUrl()
					+ ","+ "\n"+" shareImageUrl = " + shareBean.getImageUrl()
					+ ","+ "\n"+" shareUMImage = " + shareBean.getUmImage());

			if (shareBean.getUmImage() != null){
				LogManager.e("pyj", "SharePopupWindow dealShareClick UmImage不为空 share");
				share(platform, shareBean.getUmImage());

			}else if (shareBean.getUmImage() == null && !TextUtils.isEmpty(shareBean.getImageUrl())){
				LogManager.e("pyj", "SharePopupWindow dealShareClick UmImage为空， getImageBitmap");
				ShareTool.getInstance().getImageBitmap(mContext, shareBean.getImageUrl(), shareBean, 100, 100, new IDownloadBitmapCallBack() {
					@Override
					public void downloadBitmapCallBack(int code, byte[] bytes, String message) {
						if (code == 0){
							//下载成功
							LogManager.e("pyj", "SharePopupWindow dealShareClick downloadBitmapCallBack 下载成功");
							shareBean.setUmImage(new UMImage(mContext, bytes));
							share(platform, shareBean.getUmImage());
						}else {
							//下载失败
							Toast.makeText(mContext,"分享图片下载失败，请稍后重试",Toast.LENGTH_SHORT).show();
							LogManager.e("pyj", "SharePopupWindow dealShareClick downloadBitmapCallBack 下载失败 message = "+message);
						}

					}
				});
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void share(SnsPlatform platform, UMImage umImage){
		LogManager.e("pyj", "SharePopupWindow share");
		dismiss();
		ShareTool.getInstance().share(mContext, platform.mPlatform,shareBean.getShareUrl(),
				shareBean.getSharetitle(), umImage,shareBean.getShareContent(), shareListener);
	}

}
