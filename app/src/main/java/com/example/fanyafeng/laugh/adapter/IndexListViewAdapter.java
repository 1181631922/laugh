package com.example.fanyafeng.laugh.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fanyafeng.laugh.R;
import com.example.fanyafeng.laugh.activity.VideoDisplayActivity;
import com.example.fanyafeng.laugh.bean.IndexListViewBean;
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
    private ListView listView;
    SyncImageLoader syncImageLoader;

    public IndexListViewAdapter(Context context, List<IndexListViewBean> indexListViewBeanList, ListView listView) {
        this.context = context;
        this.indexListViewBeanList = indexListViewBeanList;
        this.listView = listView;
        syncImageLoader = new SyncImageLoader();
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
        try {
        ViewHolder holder = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_homepage, null);
            holder = new ViewHolder();
            holder.Title = (TextView) convertView.findViewById(R.id.index_title);
            holder.LeftTopImg = (ImageView) convertView.findViewById(R.id.index_left_up_iv);
            holder.LeftTopImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "32个赞！", Toast.LENGTH_SHORT).show();
                    Intent it_my_activity = new Intent(Intent.ACTION_CALL);
                    it_my_activity.setClass(context, VideoDisplayActivity.class);
                    context.startActivity(it_my_activity);
                }
            });
            holder.LeftTopTitle = (TextView) convertView.findViewById(R.id.index_left_up_tv);
            holder.LeftTopTimes = (TextView) convertView.findViewById(R.id.index_left_up_time);
            holder.RightTopImg = (ImageView) convertView.findViewById(R.id.index_right_up_iv);
            holder.RightTopTitle = (TextView) convertView.findViewById(R.id.index_right_up_tv);
            holder.RightTopTimes = (TextView) convertView.findViewById(R.id.index_right_up_time);
            holder.LeftBottomImg = (ImageView) convertView.findViewById(R.id.index_left_down_iv);
            holder.LeftBottomTitle = (TextView) convertView.findViewById(R.id.index_left_down_tv);
            holder.LeftBottomTimes = (TextView) convertView.findViewById(R.id.index_left_down_time);
            holder.RightBottomImg = (ImageView) convertView.findViewById(R.id.index_right_down_iv);
            holder.RightBottomTitle = (TextView) convertView.findViewById(R.id.index_left_down_tv);
            holder.RightBottomTimes = (TextView) convertView.findViewById(R.id.index_left_down_time);
        }
        convertView.setTag(position);
        holder.LeftTopImg.setBackgroundResource(R.drawable.wait);
        syncImageLoader.loadImage(position,indexListViewBeanList.get(position).getLeftTopImg(),imageLoadListener, StringTools.getFileNameFromUrl(indexListViewBeanList.get(position).getLeftTopImg()));

        holder.RightTopImg.setBackgroundResource(R.drawable.wait);
        syncImageLoader.loadImage(position,indexListViewBeanList.get(position).getRightTopImg(),imageLoadListener1, StringTools.getFileNameFromUrl(indexListViewBeanList.get(position).getRightTopImg()));

        holder.LeftBottomImg.setBackgroundResource(R.drawable.wait);
        syncImageLoader.loadImage(position,indexListViewBeanList.get(position).getLeftBottomImg(),imageLoadListener2, StringTools.getFileNameFromUrl(indexListViewBeanList.get(position).getLeftBottomImg()));

        holder.RightBottomImg.setBackgroundResource(R.drawable.wait);
        syncImageLoader.loadImage(position,indexListViewBeanList.get(position).getRightBottomImg(),imageLoadListener3, StringTools.getFileNameFromUrl(indexListViewBeanList.get(position).getRightBottomImg()));
    } catch (Exception e) {
        e.printStackTrace();
        L.d(e.toString());
    }
        return convertView;
    }

    SyncImageLoader.OnImageLoadListener imageLoadListener = new SyncImageLoader.OnImageLoadListener() {
        @Override
        public void onImageLoad(Integer t, Drawable drawable) {
            try {
                View view = listView.findViewWithTag(t);
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.index_left_up_iv);
                    imageView.setBackground(drawable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onError(Integer t) {
        }
    };

    SyncImageLoader.OnImageLoadListener imageLoadListener1 = new SyncImageLoader.OnImageLoadListener() {
        @Override
        public void onImageLoad(Integer t, Drawable drawable) {
            try {
                View view = listView.findViewWithTag(t);
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.index_right_up_iv);
                    imageView.setBackground(drawable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onError(Integer t) {
        }
    };

    SyncImageLoader.OnImageLoadListener imageLoadListener2 = new SyncImageLoader.OnImageLoadListener() {
        @Override
        public void onImageLoad(Integer t, Drawable drawable) {
            try {
                View view = listView.findViewWithTag(t);
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.index_left_down_iv);
                    imageView.setBackground(drawable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onError(Integer t) {
        }
    };

    SyncImageLoader.OnImageLoadListener imageLoadListener3 = new SyncImageLoader.OnImageLoadListener() {
        @Override
        public void onImageLoad(Integer t, Drawable drawable) {
            try {
                View view = listView.findViewWithTag(t);
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.index_right_down_iv);
                    imageView.setBackground(drawable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onError(Integer t) {
        }
    };


    static class ViewHolder {
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
    }
}
