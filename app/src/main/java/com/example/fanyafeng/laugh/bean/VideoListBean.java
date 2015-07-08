package com.example.fanyafeng.laugh.bean;

/**
 * Created by fanyafeng on 2015/7/7/0007.
 */
public class VideoListBean extends BaseBean {
    private String VideoImage;
    private String VideoTitle;
    private String VideoDesc;
    private boolean IsVideoPlay;
    private String VideoPlayTimes;
    private boolean IsVideoCollect;
    private String VideoCollectTimes;
    private boolean IsVideoGood;
    private String VideoGoodTimes;

    public VideoListBean(String videoImage, String videoTitle, String videoDesc, boolean isVideoPlay, String videoPlayTimes, boolean isVideoCollect, String videoCollectTimes, boolean isVideoGood, String videoGoodTimes) {
        VideoImage = videoImage;
        VideoTitle = videoTitle;
        VideoDesc = videoDesc;
        IsVideoPlay = isVideoPlay;
        VideoPlayTimes = videoPlayTimes;
        IsVideoCollect = isVideoCollect;
        VideoCollectTimes = videoCollectTimes;
        IsVideoGood = isVideoGood;
        VideoGoodTimes = videoGoodTimes;
    }

    public String getVideoImage() {
        return VideoImage;
    }

    public void setVideoImage(String videoImage) {
        VideoImage = videoImage;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        VideoTitle = videoTitle;
    }

    public String getVideoDesc() {
        return VideoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        VideoDesc = videoDesc;
    }

    public boolean isVideoPlay() {
        return IsVideoPlay;
    }

    public void setVideoPlay(boolean isVideoPlay) {
        IsVideoPlay = isVideoPlay;
    }

    public String getVideoPlayTimes() {
        return VideoPlayTimes;
    }

    public void setVideoPlayTimes(String videoPlayTimes) {
        VideoPlayTimes = videoPlayTimes;
    }

    public boolean isVideoCollect() {
        return IsVideoCollect;
    }

    public void setVideoCollect(boolean isVideoCollect) {
        IsVideoCollect = isVideoCollect;
    }

    public String getVideoCollectTimes() {
        return VideoCollectTimes;
    }

    public void setVideoCollectTimes(String videoCollectTimes) {
        VideoCollectTimes = videoCollectTimes;
    }

    public boolean isVideoGood() {
        return IsVideoGood;
    }

    public void setVideoGood(boolean isVideoGood) {
        IsVideoGood = isVideoGood;
    }

    public String getVideoGoodTimes() {
        return VideoGoodTimes;
    }

    public void setVideoGoodTimes(String videoGoodTimes) {
        VideoGoodTimes = videoGoodTimes;
    }

    @Override
    public String toString() {
        return "VideoListBean{" +
                "VideoImage='" + VideoImage + '\'' +
                ", VideoTitle='" + VideoTitle + '\'' +
                ", VideoDesc='" + VideoDesc + '\'' +
                ", IsVideoPlay=" + IsVideoPlay +
                ", VideoPlayTimes='" + VideoPlayTimes + '\'' +
                ", IsVideoCollect=" + IsVideoCollect +
                ", VideoCollectTimes='" + VideoCollectTimes + '\'' +
                ", IsVideoGood=" + IsVideoGood +
                ", VideoGoodTimes='" + VideoGoodTimes + '\'' +
                '}';
    }
}
