package com.example.fanyafeng.laugh.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.example.fanyafeng.laugh.activity.VideoViewPlayingActivity;
import com.example.fanyafeng.laugh.bean.IndexListViewBean;
import com.example.fanyafeng.laugh.bean.IndexUrlBean;
import com.example.fanyafeng.laugh.layout.PullToRefreshLayout;
import com.example.fanyafeng.laugh.util.ImageLoaderCache;
import com.example.fanyafeng.laugh.util.L;
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
    private List<List<Map<String, Object>>> showListList = new ArrayList<List<Map<String, Object>>>();
    private List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
    private List<IndexUrlBean> indexUrlBeanList = new ArrayList<>();
    private List<IndexUrlBean> indexUrlBeanList_more = new ArrayList<>();
    private String MinId, m3u8;
    private IndexUrlBean indexUrlBean = new IndexUrlBean(0, null, null, null, null);
    private Handler handler1;


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

    private String loadData(String updatetype, String minid) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("devicetype", "android");
        map.put("updatetype", updatetype);
        map.put("periodicalid", minid);
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
                            indexUrlBean.setLeftTop(getRealUrl(videoinfo.getString("url")));
                            indexListViewBean.setLeftTopTimes("播放次数：" + videoinfo.getString("play_number"));
                            indexListViewBean.setLeftTopTitle(videoinfo.getString("title"));
                            indexListViewBean.setLeftTopImg("http://video.ktdsp.com/" + videoinfo.getString("image"));
                        }
                        if (j == 1) {
                            indexUrlBean.setRightTop(getRealUrl(videoinfo.getString("url")));
                            indexListViewBean.setRightTopTimes("播放次数：" + videoinfo.getString("play_number"));
                            indexListViewBean.setRightTopTitle(videoinfo.getString("title"));
                            indexListViewBean.setRightTopImg("http://video.ktdsp.com/" + videoinfo.getString("image"));
                        }
                        if (j == 2) {
                            indexUrlBean.setLeftBottom(getRealUrl(videoinfo.getString("url")));
                            indexListViewBean.setLeftBottomTimes("播放次数：" + videoinfo.getString("play_number"));
                            indexListViewBean.setLeftBottomTitle(videoinfo.getString("title"));
                            indexListViewBean.setLeftBottomImg("http://video.ktdsp.com/" + videoinfo.getString("image"));
                        }
                        if (j == 3) {
                            indexUrlBean.setRightBottom(getRealUrl(videoinfo.getString("url")));
                            indexListViewBean.setRightBottomTimes("播放次数：" + videoinfo.getString("play_number"));
                            indexListViewBean.setRightBottomTitle(videoinfo.getString("title"));
                            indexListViewBean.setRightBottomImg("http://video.ktdsp.com/" + videoinfo.getString("image"));
                        }
                    }
                    indexUrlBeanList.add(indexUrlBean);
                    if (minid.equals("0")) {
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
        message.obj = minid;
        if (message.obj.equals("0")) {
            handler.sendMessage(message);
        } else {
            handler1.sendMessage(message);
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
                        indexListViewAdapter.notifyDataSetChanged();
                    }
//                    else {
//                        indexListViewBeanList.addAll(indexListViewBeanList_loadmore);
//                        indexUrlBeanList.addAll(indexUrlBeanList_more);
//                        indexListViewAdapter.notifyDataSetChanged();
//                    }
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
                                    if (!msg.obj.equals("0")) {
                                        indexListViewBeanList.addAll(indexListViewBeanList_loadmore);
                                        indexUrlBeanList.addAll(indexUrlBeanList_more);
                                        indexListViewAdapter.notifyDataSetChanged();
                                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                                    }
                                    break;
                            }

                        }
                    };

//                    indexListViewBeanList.addAll(indexListViewBeanList_loadmore);
//                    indexUrlBeanList.addAll(indexUrlBeanList_more);
//                    indexListViewAdapter.notifyDataSetChanged();
//                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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
            loadData("update_home_2top", MinId);
        }
    }


    private String getRealUrl(String url_info) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("url", url_info);
        try {
            String backMsg = PostUtil.postData(BaseUrl + GetRealUrl, map);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                Message message = Message.obtain();
                if (jsonObject.getInt("result") == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    String img = data.getString("img");
                    String title = data.getString("title");
                    m3u8 = data.getString("m3u8");
                    Bundle bundle = new Bundle();
                    bundle.putString("img", img);
                    bundle.putString("title", title);
                    bundle.putString("m3u8", m3u8);
                    message.setData(bundle);
                    message.what = 1;
                    uiHandler.sendMessage(message);

                } else {
                    message.what = 0;
                    uiHandler.sendMessage(message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return m3u8;
    }

    Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case 0:
                    T.showLong(getActivity(), "视频解析失败");
                    break;
                case 1:
                    bundle.getString("img");
                    bundle.getString("m3u8");

                    break;
            }
        }
    };

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
                        intent.setData(Uri.parse(indexUrlBeanList.get(position).getLeftTop()));
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
                        intent.setData(Uri.parse(indexUrlBeanList.get(position).getRightTop()));
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
                        intent.setData(Uri.parse(indexUrlBeanList.get(position).getLeftBottom()));
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
                        intent.setData(Uri.parse(indexUrlBeanList.get(position).getRightBottom()));
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
