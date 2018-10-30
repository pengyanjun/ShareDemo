package com.umeng.message.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;
import java.util.List;

public class SharePopAdapter extends BaseAdapter {
	private List<ShareBean> shareMediaList;
	private Context mContext;
	private LayoutInflater mInflater;

	public SharePopAdapter(List<ShareBean> shareMediaList, Context context) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		if (shareMediaList == null) {
			this.shareMediaList = new ArrayList<>();
		} else {
			this.shareMediaList = shareMediaList;
		}
	}

	public void notifyDataChage(List<ShareBean> shareMediaList) {
		if (shareMediaList == null) {
			this.shareMediaList = new ArrayList<>();
		} else {
			this.shareMediaList = shareMediaList;
		}
		this.notifyDataSetChanged();
	}

	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (shareMediaList == null) {
			return 0;
		}
		return shareMediaList.size();
	}

	@Override
	public Object getItem(int position) {
		return shareMediaList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.share_popwindow_item, null);
			viewHolder.share_name_tv = (TextView) convertView.findViewById(R.id.share_name_tv);
			viewHolder.share_logo_iv = (ImageView) convertView.findViewById(R.id.share_logo_iv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		try {
			if (shareMediaList != null && shareMediaList.size() > 0) {
				ShareBean shareBean = shareMediaList.get(position);
				if (shareBean != null) {
					SnsPlatform platform = shareBean.getPlatform();
					viewHolder.share_name_tv.setText(platform.mShowWord);
					if (SHARE_MEDIA.WEIXIN_CIRCLE.equals(platform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.umeng_socialize_wxcircle));
					} else if (SHARE_MEDIA.WEIXIN.equals(platform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.umeng_socialize_wechat));
					} else if (SHARE_MEDIA.QQ.equals(platform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.umeng_socialize_qq));
					} else if (SHARE_MEDIA.QZONE.equals(platform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.umeng_socialize_qzone));
					} else if (SHARE_MEDIA.SINA.equals(platform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.umeng_socialize_sina));
					} else if (SHARE_MEDIA.ALIPAY.equals(platform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.umeng_socialize_alipay));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		public ImageView share_logo_iv;
		public TextView share_name_tv;
	}
}