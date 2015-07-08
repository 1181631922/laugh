package com.example.fanyafeng.laugh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fanyafeng.laugh.R;
import com.example.fanyafeng.laugh.bean.VideoListBean;
import com.example.fanyafeng.laugh.util.ImageLoaderCache;
import com.example.fanyafeng.laugh.util.L;

import java.util.List;

/**
 * Created by fanyafeng on 2015/7/7/0007.
 */
public class VideoListAdapter extends BaseAdapter {
    private Context context;
    private List<VideoListBean> videoListBeanList;
    private ImageLoaderCache mImageLoader;

    public VideoListAdapter(Context context, List<VideoListBean> videoListBeanList) {
        this.context = context;
        this.videoListBeanList = videoListBeanList;
        mImageLoader = new ImageLoaderCache(context);
    }

    @Override
    public int getCount() {
        return videoListBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoListBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_activity_video_list, null);
            holder = new ViewHolder();
            view.setTag(holder);
            holder.videoListIcon = (ImageView) view.findViewById(R.id.videolist_icon);
            holder.videoListIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    L.d(position);
                }
            });
            holder.videoListTitle = (TextView) view.findViewById(R.id.videolist_title);
            holder.videoListDesc = (TextView) view.findViewById(R.id.videolist_desc);
            holder.videoPlayTimes = (TextView) view.findViewById(R.id.videolist_play_times);
            holder.videoCollectTimes = (TextView) view.findViewById(R.id.videolist_collect_times);
            holder.videoGoodTimes = (TextView) view.findViewById(R.id.videolist_good_times);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.videoListIcon.setImageResource(R.drawable.wait);
        mImageLoader.DisplayImage(videoListBeanList.get(position).getVideoImage(), holder.videoListIcon, false);
        holder.videoListTitle.setText(videoListBeanList.get(position).getVideoTitle());
        holder.videoListDesc.setText(videoListBeanList.get(position).getVideoDesc());
        holder.videoPlayTimes.setText(videoListBeanList.get(position).getVideoPlayTimes());
        holder.videoCollectTimes.setText(videoListBeanList.get(position).getVideoCollectTimes());
        holder.videoGoodTimes.setText(videoListBeanList.get(position).getVideoGoodTimes());

        return view;
    }

    static class ViewHolder {
        ImageView videoListIcon;
        TextView videoListTitle;
        TextView videoListDesc;
        TextView videoPlayTimes;
        TextView videoCollectTimes;
        TextView videoGoodTimes;
    }
}
