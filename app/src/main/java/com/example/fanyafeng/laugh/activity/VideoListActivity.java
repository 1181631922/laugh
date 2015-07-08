package com.example.fanyafeng.laugh.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.fanyafeng.laugh.R;
import com.example.fanyafeng.laugh.adapter.VideoListAdapter;
import com.example.fanyafeng.laugh.bean.IndexListViewBean;
import com.example.fanyafeng.laugh.bean.VideoListBean;
import com.example.fanyafeng.laugh.layout.PullToRefreshLayout;
import com.example.fanyafeng.laugh.util.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoListActivity extends BaseNoActionbarActivity {
    private PullToRefreshLayout ptrl;
    private ListView listView;
    private VideoListAdapter indexListViewAdapter;
    private List<VideoListBean> videoListBeanList = new ArrayList<VideoListBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        initView();
        initData();
    }

    private void initView() {
        ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_videolist_view));
        ptrl.setOnRefreshListener(new MyListener());
        listView = (ListView) findViewById(R.id.video_listview);
    }

    private void initData() {
//        listViewBeanList = new ArrayList<ListViewBean>();
        VideoListBean[] videoListBeanArray=new VideoListBean[]{
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"666",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"666"),
                new VideoListBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg","标题1","标题一的内容",false,"888",false,"888",false,"888")
//                new VideoListBean("http://b.hiphotos.baidu.com/image/pic/item/6c224f4a20a44623e056c09d9a22720e0df3d7b5.jpg","标题1","标题一的内容"),
//                new VideoListBean("http://h.hiphotos.baidu.com/image/pic/item/63d0f703918fa0ecaf13d7e4249759ee3c6ddb07.jpg","标题1","标题一的内容")
        };

        Arrays.sort(videoListBeanArray);
        videoListBeanList=Arrays.asList(videoListBeanArray);
        indexListViewAdapter=new VideoListAdapter(VideoListActivity.this,videoListBeanList);

        listView.setAdapter(indexListViewAdapter);
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
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 500);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
