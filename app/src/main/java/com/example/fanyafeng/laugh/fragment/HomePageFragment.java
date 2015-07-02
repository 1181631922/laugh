package com.example.fanyafeng.laugh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fanyafeng.laugh.R;
import com.example.fanyafeng.laugh.activity.VideoDisplayActivity;
import com.example.fanyafeng.laugh.adapter.IndexListViewAdapter;
import com.example.fanyafeng.laugh.bean.IndexListViewBean;
import com.example.fanyafeng.laugh.layout.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HomePageFragment extends BaseFragment {
    private PullToRefreshLayout ptrl;
    private ListView listView;
    private IndexListViewAdapter indexListViewAdapter;
    private List<IndexListViewBean> indexListViewBeanList;
    private String url="http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView(){
//        listView = (ListView) getActivity().findViewById(R.id.homepage_listview);
        ptrl = ((PullToRefreshLayout) getActivity().findViewById(R.id.refresh_homepage_view));
        ptrl.setOnRefreshListener(new MyListener());
        listView = (ListView) getActivity().findViewById(R.id.homepage_listview);

    }

    private void initData(){
        indexListViewBeanList = new ArrayList<IndexListViewBean>();
        IndexListViewBean[] listViewBeanArray=new IndexListViewBean[]{
                new IndexListViewBean(url,"http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg",url,url,url,url,url,url,url,url,url,url,url),
                new IndexListViewBean(url,url,url,url,url,url,url,url,"http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg",url,url,url,url),
                new IndexListViewBean(url,url,url,url,"http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg",url,url,url,url,url,url,url,url),
                new IndexListViewBean("http://a.hiphotos.baidu.com/image/pic/item/b3119313b07eca8045c14945932397dda044834f.jpg",url,url,url,url,url,url,url,url,url,url,url,url),
                new IndexListViewBean("1",url,"1","",url,"","",url,"","",url,"","")
        };

        Arrays.sort(listViewBeanArray);
        indexListViewBeanList=Arrays.asList(listViewBeanArray);
        indexListViewAdapter=new IndexListViewAdapter(getActivity(),indexListViewBeanList,listView);

        listView.setAdapter(indexListViewAdapter);
        listView.setOnItemClickListener(new IndexOnItemClickListener());
    }

    public class IndexOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(getActivity(), VideoDisplayActivity.class);
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
}
