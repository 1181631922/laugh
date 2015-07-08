package com.example.fanyafeng.laugh.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
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
import com.example.fanyafeng.laugh.activity.VideoViewPlayingActivity;
import com.example.fanyafeng.laugh.bean.IndexListViewBean;
import com.example.fanyafeng.laugh.bean.IndexUrlBean;
import com.example.fanyafeng.laugh.layout.PullToRefreshLayout;
import com.example.fanyafeng.laugh.util.ImageLoaderCache;
import com.example.fanyafeng.laugh.util.L;
import com.example.fanyafeng.laugh.util.ListViewImageTaskUtil;
import com.example.fanyafeng.laugh.util.PostUtil;
import com.example.fanyafeng.laugh.util.SyncImageLoader;
import com.example.fanyafeng.laugh.util.T;

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
    private List<IndexListViewBean> indexListViewBeanList_loadmore = new ArrayList<IndexListViewBean>();
    private List<IndexListViewBean> indexListViewBeanList_final = new ArrayList<IndexListViewBean>();
    private ProgressBar progressBar;
    private List<List<Map<String, Object>>> showListList = new ArrayList<List<Map<String, Object>>>();
    private List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
    private List<IndexUrlBean> indexUrlBeanList = new ArrayList<IndexUrlBean>();
    private List<IndexUrlBean> indexUrlBeanList_more = new ArrayList<IndexUrlBean>();
    private String MinId, m3u8, MaxId;
    //    private IndexUrlBean indexUrlBean = new IndexUrlBean(0, null, null, null, null);
    private Handler handler1, handler2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        Thread loadThread = new Thread(new LoadThread());
        loadThread.start();
    }

    class LoadThread implements Runnable {
        @Override
        public void run() {
            loadData("update_home", "0");
        }
    }

    private String loadData(String updatetype, String id) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("devicetype", "android");
        map.put("updatetype", updatetype);
        map.put("periodicalid", id);
        try {
            String backMsg = PostUtil.postData(BaseUrl + GetHomeInfo, map);
            L.d(backMsg.toString());
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                JSONArray periodical = jsonObject.getJSONArray("periodical");
                L.d(periodical.toString());
                indexUrlBeanList_more.clear();
                indexListViewBeanList_loadmore.clear();
                for (int i = 0; i < periodical.length(); i++) {
                    IndexListViewBean indexListViewBean = new IndexListViewBean(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
                    JSONObject periodicalinfo = periodical.getJSONObject(i);
                    if (id.equals("0") && i == 0) {
                        MaxId = periodicalinfo.getString("id");
                        L.d("最大的id", MaxId);
                    }
                    MinId = periodicalinfo.getString("id");
                    L.d("最小的id", MinId);
                    String name = periodicalinfo.getString("name");
                    indexListViewBean.setTitle(name);
                    JSONArray video = periodicalinfo.getJSONArray("video");
                    for (int j = 0; j < video.length(); j++) {
                        JSONObject videoinfo = video.getJSONObject(j);
                        Map<String, Object> urlmap = new HashMap<String, Object>();
                        urlmap.put("urlmap", (String) videoinfo.getString("url"));
                        showList.add(urlmap);

                        if (j == 0) {
                            indexListViewBean.setLeftTopUrl(videoinfo.getString("url"));
                            indexListViewBean.setLeftTopTimes("播放次数：" + videoinfo.getString("play_number"));
                            indexListViewBean.setLeftTopTitle(videoinfo.getString("title"));
                            indexListViewBean.setLeftTopImg(BaseUrl + videoinfo.getString("image"));
                        }
                        if (j == 1) {
                            indexListViewBean.setRightTopUrl(videoinfo.getString("url"));
                            indexListViewBean.setRightTopTimes("播放次数：" + videoinfo.getString("play_number"));
                            indexListViewBean.setRightTopTitle(videoinfo.getString("title"));
                            indexListViewBean.setRightTopImg(BaseUrl + videoinfo.getString("image"));
                        }
                        if (j == 2) {
                            indexListViewBean.setLeftBottonUrl(videoinfo.getString("url"));
                            indexListViewBean.setLeftBottomTimes("播放次数：" + videoinfo.getString("play_number"));
                            indexListViewBean.setLeftBottomTitle(videoinfo.getString("title"));
                            indexListViewBean.setLeftBottomImg(BaseUrl + videoinfo.getString("image"));
                        }
                        if (j == 3) {
                            indexListViewBean.setRightBottonUrl(videoinfo.getString("url"));
                            indexListViewBean.setRightBottomTimes("播放次数：" + videoinfo.getString("play_number"));
                            indexListViewBean.setRightBottomTitle(videoinfo.getString("title"));
                            indexListViewBean.setRightBottomImg(BaseUrl + videoinfo.getString("image"));
                        }
                    }
                    if (id.equals("0")) {
                        indexListViewBeanList.add(indexListViewBean);
                    } else {
                        indexListViewBeanList_loadmore.add(indexListViewBean);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = Message.obtain();
        message.what = 0;
        message.obj = id;
        if (message.obj.equals("0")) {
            handler.sendMessage(message);
        } else {
            handler1.sendMessage(message);
        }
        return MinId;
    }

    private String refreshData(String updatetype, String id) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("devicetype", "android");
        map.put("updatetype", updatetype);
        map.put("periodicalid", id);
        try {
            String backMsg = PostUtil.postData(BaseUrl + GetHomeInfo, map);
            L.d(backMsg.toString());
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                JSONArray periodical = jsonObject.getJSONArray("periodical");
                L.d(periodical.toString());
                if (periodical.toString().equals("[]")) {
                    Message message = Message.obtain();
                    message.what = 0;
                    handler2.sendMessage(message);
                } else {
                    for (int i = 0; i < periodical.length(); i++) {
                        IndexListViewBean indexListViewBean = new IndexListViewBean(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
                        IndexUrlBean indexUrlBean = new IndexUrlBean(0, null, null, null, null);
                        JSONObject periodicalinfo = periodical.getJSONObject(i);
                        if (id.equals("0") && i == 0) {
                            MaxId = periodicalinfo.getString("id");
                            L.d("最大的id", MaxId);
                        }
                        MinId = periodicalinfo.getString("id");
                        String name = periodicalinfo.getString("name");
                        indexListViewBean.setTitle(name);
                        JSONArray video = periodicalinfo.getJSONArray("video");
                        indexUrlBean.setPosition(i);
                        for (int j = 0; j < video.length(); j++) {
                            JSONObject videoinfo = video.getJSONObject(j);
                            Map<String, Object> urlmap = new HashMap<String, Object>();
                            urlmap.put("urlmap", (String) videoinfo.getString("url"));
                            showList.add(urlmap);

                            if (j == 0) {
                                indexListViewBean.setLeftTopUrl(videoinfo.getString("url"));
                                indexListViewBean.setLeftTopTimes("播放次数：" + videoinfo.getString("play_number"));
                                indexListViewBean.setLeftTopTitle(videoinfo.getString("title"));
                                indexListViewBean.setLeftTopImg("http://video.ktdsp.com/" + videoinfo.getString("image"));
                            }
                            if (j == 1) {
                                indexListViewBean.setRightTopUrl(videoinfo.getString("url"));
                                indexListViewBean.setRightTopTimes("播放次数：" + videoinfo.getString("play_number"));
                                indexListViewBean.setRightTopTitle(videoinfo.getString("title"));
                                indexListViewBean.setRightTopImg("http://video.ktdsp.com/" + videoinfo.getString("image"));
                            }
                            if (j == 2) {
                                indexListViewBean.setLeftBottonUrl(videoinfo.getString("url"));
                                indexListViewBean.setLeftBottomTimes("播放次数：" + videoinfo.getString("play_number"));
                                indexListViewBean.setLeftBottomTitle(videoinfo.getString("title"));
                                indexListViewBean.setLeftBottomImg("http://video.ktdsp.com/" + videoinfo.getString("image"));
                            }
                            if (j == 3) {
                                indexListViewBean.setRightBottonUrl(videoinfo.getString("url"));
                                indexListViewBean.setRightBottomTimes("播放次数：" + videoinfo.getString("play_number"));
                                indexListViewBean.setRightBottomTitle(videoinfo.getString("title"));
                                indexListViewBean.setRightBottomImg("http://video.ktdsp.com/" + videoinfo.getString("image"));
                            }
                        }
                        indexUrlBeanList.clear();
                        indexListViewBeanList.clear();
                        indexUrlBeanList.add(indexUrlBean);
                        indexListViewBeanList.add(indexListViewBean);
                        Message message = Message.obtain();
                        message.what = 0;
                        handler2.sendMessage(message);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MinId;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (msg.obj.equals("0")) {
                        indexListViewAdapter.update();
                    }
                    break;
                case 1:
                    break;
            }
        }
    };


    private void initView() {
        progressBar = (ProgressBar) getActivity().findViewById(R.id.index_progressbar);
        ptrl = ((PullToRefreshLayout) getActivity().findViewById(R.id.refresh_homepage_view));
        ptrl.setOnRefreshListener(new MyListener());
        listView = (ListView) getActivity().findViewById(R.id.homepage_listview);

        indexListViewAdapter = new IndexListViewAdapter(getActivity(), indexListViewBeanList);
        listView.setAdapter(indexListViewAdapter);
        listView.setOnItemClickListener(new IndexOnItemClickListener());
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
                    Thread refresh = new Thread(new Refresh());
                    refresh.start();
                    handler2 = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 0:
//                                    indexListViewAdapter.notifyDataSetChanged();
                                    indexListViewAdapter.update();
                                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                                    break;
                            }
                        }
                    };
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 500);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Thread lodeMore = new Thread(new LoadMore());
                    lodeMore.start();

                    handler1 = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 0:
                                    if (!msg.obj.equals("0") && !msg.equals(MaxId)) {
                                        indexUrlBeanList.addAll(indexUrlBeanList_more);
                                        L.d("indexurlbeanlist的长度", indexUrlBeanList.size() + "");
                                        L.d(indexUrlBeanList.toString());
                                        indexListViewBeanList.addAll(indexListViewBeanList_loadmore);
                                        indexListViewAdapter.update();
                                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                                    }
                                    break;
                            }
                        }
                    };
                }
            }.sendEmptyMessageDelayed(0, 500);
        }

    }

    class LoadMore implements Runnable {
        @Override
        public void run() {
            loadData("update_home_2down", MinId);
        }
    }

    class Refresh implements Runnable {
        @Override
        public void run() {
            refreshData("update_home_2top", MaxId);
        }
    }


    public class IndexListViewAdapter extends BaseAdapter {

        private Context context;
        private List<IndexListViewBean> indexListViewBeans;
        private ListView listView;
        private String LeftTopUrl, RightTopUrl, LeftBottomUrl, RightBottomUrl;
        private int i;

        //缓存到本地sd卡，并且可以更新ListView图片
        private ImageLoaderCache mImageLoader;

        // 获取当前应用程序所分配的最大内存
        private final int maxMemory = (int) Runtime.getRuntime().maxMemory();
        // 只用五分之一用来做图片缓存
        private final int cacheSize = maxMemory / 5;

        private LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(
                cacheSize) {

            // 重写sizeof（）方法
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // TODO Auto-generated method stub
                // 这里用多少kb来计算
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }

        };


        public IndexListViewAdapter(Context context, List<IndexListViewBean> indexListViewBeans) {
            this.context = context;
            this.indexListViewBeans = indexListViewBeans;
            mImageLoader = new ImageLoaderCache(context);
        }

        public ImageLoaderCache getImagerLoader() {
            return mImageLoader;
        }

        public void update() {
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return indexListViewBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return indexListViewBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
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
                        Intent intent = new Intent(getActivity(), VideoViewPlayingActivity.class);
                        intent.putExtra("url_info", LeftTopUrl);
                        L.d("leftTopUrl", LeftTopUrl);
                        L.d("点击获取的位置",position);
                        startActivity(intent);
                    }
                });
                holder.LeftTopTitle = (TextView) view.findViewById(R.id.index_left_up_tv);
                holder.LeftTopTimes = (TextView) view.findViewById(R.id.index_left_up_time);
                holder.RightTopImg = (ImageView) view.findViewById(R.id.index_right_up_iv);
                holder.RightTopImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), VideoViewPlayingActivity.class);
                        intent.putExtra("url_info", RightTopUrl);
                        startActivity(intent);
                    }
                });
                holder.RightTopTitle = (TextView) view.findViewById(R.id.index_right_up_tv);
                holder.RightTopTimes = (TextView) view.findViewById(R.id.index_right_up_time);
                holder.LeftBottomImg = (ImageView) view.findViewById(R.id.index_left_down_iv);
                holder.LeftBottomImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), VideoViewPlayingActivity.class);
                        intent.putExtra("url_info", LeftBottomUrl);
                        startActivity(intent);
                    }
                });
                holder.LeftBottomTitle = (TextView) view.findViewById(R.id.index_left_down_tv);
                holder.LeftBottomTimes = (TextView) view.findViewById(R.id.index_left_down_time);
                holder.RightBottomImg = (ImageView) view.findViewById(R.id.index_right_down_iv);
                holder.RightBottomImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), VideoViewPlayingActivity.class);
                        intent.putExtra("url_info", RightBottomUrl);
                        startActivity(intent);
                    }
                });
                holder.RightBottomTitle = (TextView) view.findViewById(R.id.index_right_down_tv);
                holder.RightBottomTimes = (TextView) view.findViewById(R.id.index_right_down_time);
                holder.index_more_but = (Button) view.findViewById(R.id.index_more_but);
                holder.index_more_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), VideoListActivity.class);

                        startActivity(intent);
                    }
                });
            } else {
                holder = (ViewHolder) view.getTag();
            }
            LeftTopUrl = indexListViewBeans.get(position).getLeftTopUrl();
            RightTopUrl=indexListViewBeans.get(position).getRightTopUrl();
            LeftBottomUrl=indexListViewBeans.get(position).getLeftBottonUrl();
            RightBottomUrl =indexListViewBeans.get(position).getRightBottonUrl();
            holder.Title.setText(indexListViewBeans.get(position).getTitle());
            holder.LeftTopTitle.setText(indexListViewBeans.get(position).getLeftTopTitle());
            holder.LeftTopTimes.setText(indexListViewBeans.get(position).getLeftTopTimes());
            holder.RightTopTitle.setText(indexListViewBeans.get(position).getRightTopTitle());
            holder.RightTopTimes.setText(indexListViewBeans.get(position).getRightTopTimes());
            holder.LeftBottomTitle.setText(indexListViewBeans.get(position).getLeftBottomTitle());
            holder.LeftBottomTimes.setText(indexListViewBeans.get(position).getLeftBottomTimes());
            holder.RightBottomTitle.setText(indexListViewBeans.get(position).getRightBottomTitle());
            holder.RightBottomTimes.setText(indexListViewBeans.get(position).getRightBottomTimes());

            loadBitmap(indexListViewBeans.get(position).getLeftTopImg(), holder.LeftTopImg);
            loadBitmap(indexListViewBeans.get(position).getRightTopImg(), holder.RightTopImg);
            loadBitmap(indexListViewBeans.get(position).getLeftBottomImg(), holder.LeftBottomImg);
            loadBitmap(indexListViewBeans.get(position).getRightBottomImg(), holder.RightBottomImg);

            return view;
        }

        private void loadBitmap(String urlStr, ImageView image) {

            ListViewImageTaskUtil asyncLoader = new ListViewImageTaskUtil(image, mLruCache);// 一个异步图片加载对象
            Bitmap bitmap = asyncLoader.getBitmapFromMemoryCache(urlStr);// 首先从内存缓存中获取图片
            if (bitmap != null) {
                image.setImageBitmap(bitmap);// 如果缓存中存在这张图片则直接设置给ImageView
            } else {
                image.setImageResource(R.drawable.wait);// 否则先设置成默认的图片
                asyncLoader.execute(urlStr);// 然后执行异步任务AsycnTask 去网上加载图片
            }
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
}
