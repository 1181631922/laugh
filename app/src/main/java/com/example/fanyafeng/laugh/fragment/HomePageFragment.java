package com.example.fanyafeng.laugh.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fanyafeng.laugh.R;
import com.example.fanyafeng.laugh.activity.VideoDisplayActivity;
import com.example.fanyafeng.laugh.activity.VideoListActivity;
import com.example.fanyafeng.laugh.bean.IndexListViewBean;
import com.example.fanyafeng.laugh.bean.IndexUrlBean;
import com.example.fanyafeng.laugh.layout.PullToRefreshLayout;
import com.example.fanyafeng.laugh.util.ImageLoaderCache;
import com.example.fanyafeng.laugh.util.L;
import com.example.fanyafeng.laugh.util.PostUtil;
import com.example.fanyafeng.laugh.util.StringTools;
import com.example.fanyafeng.laugh.util.SyncImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomePageFragment extends BaseFragment {
    private PullToRefreshLayout ptrl;
    private ListView listView;
    private IndexListViewAdapter indexListViewAdapter;
    private List<IndexListViewBean> indexListViewBeanList = new ArrayList<IndexListViewBean>();
    private String url = "http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg";
    private ProgressBar progressBar;
    private String Title;
    private String LeftTopImg;
    private String LeftTopTitle;
    private String LeftTopTimes;
    private String RightTopImg;
    private String RightTopTitle;
    private String RightTopTimes;
    private String LeftBottomImg;
    private String LeftBottomTitle;
    private String LeftBottomTimes;
    private String RightBottomImg;
    private String RightBottomTitle;
    private String RightBottomTimes;
    private IndexUrlBean indexUrlBean;
    private List<List<Map<String, Object>>> showListList = new ArrayList<List<Map<String, Object>>>();
    private List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
    private List<IndexUrlBean> indexUrlBeanList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        progressBar.setVisibility(View.VISIBLE);
        Thread loadThread = new Thread(new LoadThread());
        loadThread.start();
    }

    class LoadThread implements Runnable {
        @Override
        public void run() {
            loadData();
        }
    }

    private void loadData() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("devicetype", "android");
        map.put("updatetype", "update_home");
        map.put("periodicalid", "0");
        try {
            String backMsg = PostUtil.postData(BaseUrl + GetHomeInfo, map);
            L.d(backMsg.toString());
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                JSONArray periodical = jsonObject.getJSONArray("periodical");
                L.d(periodical.toString());
                for (int i = 0; i < periodical.length(); i++) {
                    IndexListViewBean indexListViewBean = new IndexListViewBean(Title, LeftTopImg, LeftTopTitle, LeftTopTimes, RightTopImg, RightTopTitle, RightTopTimes, LeftBottomImg, LeftBottomTitle, LeftBottomTimes, RightBottomImg, RightBottomTitle, RightBottomTimes);
                    JSONObject periodicalinfo = periodical.getJSONObject(i);
                    String id = periodicalinfo.getString("id");
                    String name = periodicalinfo.getString("name");
                    indexListViewBean.setTitle(name);
                    JSONArray video = periodicalinfo.getJSONArray("video");
                    IndexUrlBean indexUrlBean = new IndexUrlBean(0, null, null, null, null);
                    indexUrlBean.setPosition(i);
                    for (int j = 0; j < video.length(); j++) {
                        JSONObject videoinfo = video.getJSONObject(j);
                        Map<String, Object> urlmap = new HashMap<String, Object>();
                        urlmap.put("urlmap", (String) videoinfo.getString("url"));
                        showList.add(urlmap);

                        if (j == 0) {
                            String infoid = videoinfo.getString("id");
                            indexUrlBean.setLeftTop(videoinfo.getString("url"));
                            indexListViewBean.setLeftTopTimes("播放次数："+videoinfo.getString("play_number"));
                            indexListViewBean.setLeftTopTitle(videoinfo.getString("title"));
                            String image = videoinfo.getString("image");
                            indexListViewBean.setLeftTopImg("http://video.ktdsp.com/" + image);
                        }
                        if (j == 1) {
                            String infoid = videoinfo.getString("id");
                            indexUrlBean.setRightTop(videoinfo.getString("url"));
                            indexListViewBean.setRightTopTimes("播放次数："+videoinfo.getString("play_number"));
                            indexListViewBean.setRightTopTitle(videoinfo.getString("title"));
                            String image = videoinfo.getString("image");
                            indexListViewBean.setRightTopImg("http://video.ktdsp.com/" + image);
                        }
                        if (j == 2) {
                            String infoid = videoinfo.getString("id");
                            indexUrlBean.setLeftBottom(videoinfo.getString("url"));
                            indexListViewBean.setLeftBottomTimes("播放次数："+videoinfo.getString("play_number"));
                            indexListViewBean.setLeftBottomTitle(videoinfo.getString("title"));
                            String image = videoinfo.getString("image");
                            indexListViewBean.setLeftBottomImg("http://video.ktdsp.com/" + image);
                        }
                        if (j == 3) {
                            String infoid = videoinfo.getString("id");
                            indexUrlBean.setRightBottom(videoinfo.getString("url"));
                            indexListViewBean.setRightBottomTimes("播放次数："+videoinfo.getString("play_number"));
                            indexListViewBean.setRightBottomTitle(videoinfo.getString("title"));
                            String image = videoinfo.getString("image");
                            indexListViewBean.setRightBottomImg("http://video.ktdsp.com/" + image);
                        }
                    }
                    indexUrlBeanList.add(indexUrlBean);
                    indexListViewBeanList.add(indexListViewBean);
                }
                Message message = Message.obtain();
                message.what = 0;
                handler.sendMessage(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initDownView();
                    break;
                case 1:
                    break;
            }
        }
    };

    private void initDownView() {
        progressBar.setVisibility(View.GONE);
        indexListViewAdapter = new IndexListViewAdapter(getActivity(), indexListViewBeanList);
        listView.setAdapter(indexListViewAdapter);
        listView.setOnItemClickListener(new IndexOnItemClickListener());
    }

    private void initView() {
        progressBar = (ProgressBar) getActivity().findViewById(R.id.index_progressbar);
        ptrl = ((PullToRefreshLayout) getActivity().findViewById(R.id.refresh_homepage_view));
        ptrl.setOnRefreshListener(new MyListener());
        listView = (ListView) getActivity().findViewById(R.id.homepage_listview);
    }

    public class IndexOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), VideoDisplayActivity.class);
            startActivity(intent);
        }
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }

    }

    public class IndexListViewAdapter extends BaseAdapter {

        private Context context;
        private List<IndexListViewBean> indexListViewBeanList;
        private ListView listView;
        SyncImageLoader syncImageLoader;

        //缓存到本地sd卡，并且可以更新ListView图片
        private ImageLoaderCache mImageLoader;


        public IndexListViewAdapter(Context context, List<IndexListViewBean> indexListViewBeanList) {
            this.context = context;
            this.indexListViewBeanList = indexListViewBeanList;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
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
                            Intent intent = new Intent(getActivity(), VideoDisplayActivity.class);
                            intent.putExtra("url_info", indexUrlBeanList.get(position).getLeftTop());
                            startActivity(intent);
                        }
                    });
                    holder.LeftTopTitle = (TextView) convertView.findViewById(R.id.index_left_up_tv);
                    holder.LeftTopTimes = (TextView) convertView.findViewById(R.id.index_left_up_time);
                    holder.RightTopImg = (ImageView) convertView.findViewById(R.id.index_right_up_iv);
                    holder.RightTopImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), VideoDisplayActivity.class);
                            intent.putExtra("url_info", indexUrlBeanList.get(position).getRightTop());
                            startActivity(intent);
                        }
                    });
                    holder.RightTopTitle = (TextView) convertView.findViewById(R.id.index_right_up_tv);
                    holder.RightTopTimes = (TextView) convertView.findViewById(R.id.index_right_up_time);
                    holder.LeftBottomImg = (ImageView) convertView.findViewById(R.id.index_left_down_iv);
                    holder.LeftBottomImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), VideoDisplayActivity.class);
                            intent.putExtra("url_info", indexUrlBeanList.get(position).getLeftBottom());
                            startActivity(intent);
                        }
                    });
                    holder.LeftBottomTitle = (TextView) convertView.findViewById(R.id.index_left_down_tv);
                    holder.LeftBottomTimes = (TextView) convertView.findViewById(R.id.index_left_down_time);
                    holder.RightBottomImg = (ImageView) convertView.findViewById(R.id.index_right_down_iv);
                    holder.RightBottomImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), VideoDisplayActivity.class);
                            intent.putExtra("url_info", indexUrlBeanList.get(position).getRightBottom());
                            startActivity(intent);
                        }
                    });
                    holder.RightBottomTitle = (TextView) convertView.findViewById(R.id.index_right_down_tv);
                    holder.RightBottomTimes = (TextView) convertView.findViewById(R.id.index_right_down_time);
                    holder.index_more_but = (Button) convertView.findViewById(R.id.index_more_but);
                    holder.index_more_but.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), VideoListActivity.class);

                            startActivity(intent);
                        }
                    });
                }
                convertView.setTag(position);
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
//                syncImageLoader.loadImage(position, indexListViewBeanList.get(position).getLeftTopImg(), imageLoadListener, StringTools.getFileNameFromUrl(indexListViewBeanList.get(position).getLeftTopImg()));
                mImageLoader.DisplayImage(indexListViewBeanList.get(position).getLeftTopImg(), holder.LeftTopImg, false);

                holder.RightTopImg.setImageResource(R.drawable.wait);
//                syncImageLoader.loadImage(position, indexListViewBeanList.get(position).getRightTopImg(), imageLoadListener1, StringTools.getFileNameFromUrl(indexListViewBeanList.get(position).getRightTopImg()));
                mImageLoader.DisplayImage(indexListViewBeanList.get(position).getRightTopImg(), holder.RightTopImg, false);

                holder.LeftBottomImg.setImageResource(R.drawable.wait);
//                syncImageLoader.loadImage(position, indexListViewBeanList.get(position).getLeftBottomImg(), imageLoadListener2, StringTools.getFileNameFromUrl(indexListViewBeanList.get(position).getLeftBottomImg()));
                mImageLoader.DisplayImage(indexListViewBeanList.get(position).getLeftBottomImg(), holder.LeftBottomImg, false);

                holder.RightBottomImg.setImageResource(R.drawable.wait);
//                syncImageLoader.loadImage(position, indexListViewBeanList.get(position).getRightBottomImg(), imageLoadListener3, StringTools.getFileNameFromUrl(indexListViewBeanList.get(position).getRightBottomImg()));
                mImageLoader.DisplayImage(indexListViewBeanList.get(position).getRightBottomImg(), holder.RightBottomImg, false);

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
}
