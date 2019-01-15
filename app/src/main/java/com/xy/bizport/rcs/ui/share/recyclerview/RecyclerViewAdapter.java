package com.xy.bizport.rcs.ui.share.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.bizport.rcs.ui.share.ShareTool;
import com.xy.bizport.share.lib.ShareBean;
import com.xy.bizport.share.lib.ShareMedia;
import com.xy.bizport.share.lib.SharePlatform;
import com.xy.rcstest.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ShareViewHolder>{

    private ShareBean shareBean;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(ShareBean shareBean, Context context){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        if (shareBean == null) {
            this.shareBean = new ShareBean();
        } else {
            this.shareBean = shareBean;
        }
    }

    public void notifyDataChage(ShareBean shareBean) {
        if (shareBean == null) {
            this.shareBean = new ShareBean();
        } else {
            this.shareBean = shareBean;
        }
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.share_popwindow_item, null);
        return new ShareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShareViewHolder viewHolder, int position) {
        try {
            if (shareBean != null && shareBean.getPlatforms() != null && shareBean.getPlatforms().size() >0) {
                SharePlatform platform = shareBean.getPlatforms().get(position);

                if (ShareMedia.WEIXIN_CIRCLE.equals(platform.shareMedia)){
                    viewHolder.share_name_tv.setText(ShareTool.getString(mContext, R.string.share_platform_name_weixin_circle));
                    viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.share_wxcircle));
                } else if (ShareMedia.WEIXIN.equals(platform.shareMedia)){
                    viewHolder.share_name_tv.setText(ShareTool.getString(mContext, R.string.share_platform_name_weixin));
                    viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.share_weixin));
                } else if (ShareMedia.QQ.equals(platform.shareMedia)){
                    viewHolder.share_name_tv.setText(ShareTool.getString(mContext, R.string.share_platform_name_qq));
                    viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.share_qq));
                } else if (ShareMedia.QZONE.equals(platform.shareMedia)){
                    viewHolder.share_name_tv.setText(ShareTool.getString(mContext, R.string.share_platform_name_qzone));
                    viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.share_qzone));
                } else if (ShareMedia.SINA.equals(platform.shareMedia)){
                    viewHolder.share_name_tv.setText(ShareTool.getString(mContext, R.string.share_platform_name_sina));
                    viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.share_sina));
                }
            }

            if (mOnItemClickListener != null){
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = viewHolder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(viewHolder.itemView, pos);
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (shareBean == null || shareBean.getPlatforms() == null || shareBean.getPlatforms().size() == 0) {
            return 0;
        }
        return shareBean.getPlatforms().size();
    }

    public Object getItem(int position) {
        return shareBean.getPlatforms().get(position);
    }

    class ShareViewHolder extends RecyclerView.ViewHolder{
        ImageView share_logo_iv;
        TextView share_name_tv;
        public ShareViewHolder(View view){
            super(view);
            share_name_tv = (TextView) view.findViewById(R.id.share_name_tv);
            share_logo_iv = (ImageView) view.findViewById(R.id.share_logo_iv);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
