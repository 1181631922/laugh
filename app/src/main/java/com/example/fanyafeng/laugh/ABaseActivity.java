package com.example.fanyafeng.laugh;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ABaseActivity extends Activity implements View.OnClickListener{
    protected String BaseUrl="http://video.ktdsp.com/";
    protected String GetHomeInfo="get_home_videoInfo.php";
    protected String GetRealUrl="video_api/url_to_m3u8.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
