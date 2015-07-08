package com.example.fanyafeng.laugh.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fanyafeng.laugh.R;
import com.example.fanyafeng.laugh.activity.VideoDisplayActivity;
import com.example.fanyafeng.laugh.activity.VideoListActivity;
import com.example.fanyafeng.laugh.activity.VideoViewPlayingActivity;
import com.example.fanyafeng.laugh.bean.IndexListViewBean;
import com.example.fanyafeng.laugh.bean.IndexUrlBean;
import com.example.fanyafeng.laugh.util.ImageLoaderCache;
import com.example.fanyafeng.laugh.util.L;
import com.example.fanyafeng.laugh.util.StringTools;
import com.example.fanyafeng.laugh.util.SyncImageLoader;

import java.util.List;

/**
 * Created by fanyafeng on 2015/7/1.
 */
public class IndexListViewAdapter extends BaseAdapter {

    private Context context;
    private List<IndexListViewBean> indexListViewBeanList;
    private List<IndexUrlBean> indexUrlBean;

    //缓存到本地sd卡，并且可以更新ListView图片
    private ImageLoaderCache mImageLoader;


    public IndexListViewAdapter(Context context, List<IndexListViewBean> indexListViewBeanList, List<IndexUrlBean> indexUrlBean) {
        this.context = context;
        this.indexListViewBeanList = indexListViewBeanList;
        this.indexUrlBean = indexUrlBean;
        mImageLoader = new ImageLoaderCache(context);
    }

    public ImageLoaderCache getImagerLoader() {
        return mImageLoader;
    }

    @Override
    public int getCount() {
        return indexListViewBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return indexListViewBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_fragment_homepage, null);
            holder = new ViewHolder();
            view.setTag(holder);
            holder.Title = (TextView) view.findViewById(R.id.index_title);
            holder.LeftTopImg = (ImageView) view.findViewById(R.id.index_left_up_iv);
            holder.LeftTopImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), VideoViewPlayingActivity.class);
//                    intent.setData(Uri.parse(indexUrlBean.get(position).getLeftTop()));
//                    L.d(indexUrlBean.get(position).getLeftTop());
//                    startActivity(intent);
                }
            });
            holder.LeftTopTitle = (TextView) view.findViewById(R.id.index_left_up_tv);
            holder.LeftTopTimes = (TextView) view.findViewById(R.id.index_left_up_time);
            holder.RightTopImg = (ImageView) view.findViewById(R.id.index_right_up_iv);
            holder.RightTopImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), VideoViewPlayingActivity.class);
//                    intent.setData(Uri.parse(indexUrlBean.get(position).getRightTop()));
//                    startActivity(intent);
                }
            });
            holder.RightTopTitle = (TextView) view.findViewById(R.id.index_right_up_tv);
            holder.RightTopTimes = (TextView) view.findViewById(R.id.index_right_up_time);
            holder.LeftBottomImg = (ImageView) view.findViewById(R.id.index_left_down_iv);
            holder.LeftBottomImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), VideoViewPlayingActivity.class);
//                    intent.setData(Uri.parse(indexUrlBean.get(position).getLeftBottom()));
//                    startActivity(intent);
                }
            });
            holder.LeftBottomTitle = (TextView) view.findViewById(R.id.index_left_down_tv);
            holder.LeftBottomTimes = (TextView) view.findViewById(R.id.index_left_down_time);
            holder.RightBottomImg = (ImageView) view.findViewById(R.id.index_right_down_iv);
            holder.RightBottomImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), VideoViewPlayingActivity.class);
//                    intent.setData(Uri.parse(indexUrlBean.get(position).getRightBottom()));
//                    startActivity(intent);
                }
            });
            holder.RightBottomTitle = (TextView) view.findViewById(R.id.index_right_down_tv);
            holder.RightBottomTimes = (TextView) view.findViewById(R.id.index_right_down_time);
            holder.index_more_but = (Button) view.findViewById(R.id.index_more_but);
            holder.index_more_but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), VideoListActivity.class);
//
//                    startActivity(intent);
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.Title.setText(indexListViewBeanList.get(position).getTitle());
        holder.LeftTopTitle.setText(indexListViewBeanList.get(position).getLeftTopTitle());
        holder.LeftTopTimes.setText(indexListViewBeanList.get(position).getLeftTopTimes());
        holder.RightTopTitle.setText(indexListViewBeanList.get(position).getRightTopTitle());
        holder.RightTopTimes.setText(indexListViewBeanList.get(position).getRightTopTimes());
        holder.LeftBottomTitle.setText(indexListViewBeanList.get(position).getLeftBottomTitle());
        holder.LeftBottomTimes.setText(indexListViewBeanList.get(position).getLeftBottomTimes());
        holder.RightBottomTitle.setText(indexListViewBeanList.get(position).getRightBottomTitle());
        holder.RightBottomTimes.setText(indexListViewBeanList.get(position).getRightBottomTimes());

        holder.LeftTopImg.setImageResource(R.drawable.wait);
        mImageLoader.DisplayImage(indexListViewBeanList.get(position).getLeftTopImg(), holder.LeftTopImg, false);

        holder.RightTopImg.setImageResource(R.drawable.wait);
        mImageLoader.DisplayImage(indexListViewBeanList.get(position).getRightTopImg(), holder.RightTopImg, false);

        holder.LeftBottomImg.setImageResource(R.drawable.wait);
        mImageLoader.DisplayImage(indexListViewBeanList.get(position).getLeftBottomImg(), holder.LeftBottomImg, false);

        holder.RightBottomImg.setImageResource(R.drawable.wait);
        mImageLoader.DisplayImage(indexListViewBeanList.get(position).getRightBottomImg(), holder.RightBottomImg, false);

        return view;
    }



    class ViewHolder {
        TextView Title;
        ImageView LeftTopImg;
        TextView LeftTopTitle;
        TextView LeftTopTimes;
        ImageView RightTopImg;
        TextView RightTopTitle;
        TextView RightTopTimes;
        ImageView LeftBottomImg;
        TextView LeftBottomTitle;
        TextView LeftBottomTimes;
        ImageView RightBottomImg;
        TextView RightBottomTitle;
        TextView RightBottomTimes;
        Button index_more_but;
    }


}