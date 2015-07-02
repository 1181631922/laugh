package com.example.fanyafeng.laugh.bean;

import com.example.fanyafeng.laugh.util.S;

/**
 * Created by fanyafeng on 2015/6/30.
 */
public class IndexListViewBean  extends BaseBean  {
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

    public IndexListViewBean(String title, String leftTopImg, String leftTopTitle, String leftTopTimes, String rightTopImg, String rightTopTitle, String rightTopTimes, String leftBottomImg, String leftBottomTitle, String leftBottomTimes, String rightBottomImg, String rightBottomTitle, String rightBottomTimes) {
        Title = title;
        LeftTopImg = leftTopImg;
        LeftTopTitle = leftTopTitle;
        LeftTopTimes = leftTopTimes;
        RightTopImg = rightTopImg;
        RightTopTitle = rightTopTitle;
        RightTopTimes = rightTopTimes;
        LeftBottomImg = leftBottomImg;
        LeftBottomTitle = leftBottomTitle;
        LeftBottomTimes = leftBottomTimes;
        RightBottomImg = rightBottomImg;
        RightBottomTitle = rightBottomTitle;
        RightBottomTimes = rightBottomTimes;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLeftTopImg() {
        return LeftTopImg;
    }

    public void setLeftTopImg(String leftTopImg) {
        LeftTopImg = leftTopImg;
    }

    public String getLeftTopTitle() {
        return LeftTopTitle;
    }

    public void setLeftTopTitle(String leftTopTitle) {
        LeftTopTitle = leftTopTitle;
    }

    public String getLeftTopTimes() {
        return LeftTopTimes;
    }

    public void setLeftTopTimes(String leftTopTimes) {
        LeftTopTimes = leftTopTimes;
    }

    public String getRightTopImg() {
        return RightTopImg;
    }

    public void setRightTopImg(String rightTopImg) {
        RightTopImg = rightTopImg;
    }

    public String getRightTopTitle() {
        return RightTopTitle;
    }

    public void setRightTopTitle(String rightTopTitle) {
        RightTopTitle = rightTopTitle;
    }

    public String getRightTopTimes() {
        return RightTopTimes;
    }

    public void setRightTopTimes(String rightTopTimes) {
        RightTopTimes = rightTopTimes;
    }

    public String getLeftBottomImg() {
        return LeftBottomImg;
    }

    public void setLeftBottomImg(String leftBottomImg) {
        LeftBottomImg = leftBottomImg;
    }

    public String getLeftBottomTitle() {
        return LeftBottomTitle;
    }

    public void setLeftBottomTitle(String leftBottomTitle) {
        LeftBottomTitle = leftBottomTitle;
    }

    public String getLeftBottomTimes() {
        return LeftBottomTimes;
    }

    public void setLeftBottomTimes(String leftBottomTimes) {
        LeftBottomTimes = leftBottomTimes;
    }

    public String getRightBottomImg() {
        return RightBottomImg;
    }

    public void setRightBottomImg(String rightBottomImg) {
        RightBottomImg = rightBottomImg;
    }

    public String getRightBottomTitle() {
        return RightBottomTitle;
    }

    public void setRightBottomTitle(String rightBottomTitle) {
        RightBottomTitle = rightBottomTitle;
    }

    public String getRightBottomTimes() {
        return RightBottomTimes;
    }

    public void setRightBottomTimes(String rightBottomTimes) {
        RightBottomTimes = rightBottomTimes;
    }

    @Override
    public String toString() {
        return "IndexListViewBean{" +
                "Title='" + Title + '\'' +
                ", LeftTopImg='" + LeftTopImg + '\'' +
                ", LeftTopTitle='" + LeftTopTitle + '\'' +
                ", LeftTopTimes='" + LeftTopTimes + '\'' +
                ", RightTopImg='" + RightTopImg + '\'' +
                ", RightTopTitle='" + RightTopTitle + '\'' +
                ", RightTopTimes='" + RightTopTimes + '\'' +
                ", LeftBottomImg='" + LeftBottomImg + '\'' +
                ", LeftBottomTitle='" + LeftBottomTitle + '\'' +
                ", LeftBottomTimes='" + LeftBottomTimes + '\'' +
                ", RightBottomImg='" + RightBottomImg + '\'' +
                ", RightBottomTitle='" + RightBottomTitle + '\'' +
                ", RightBottomTimes='" + RightBottomTimes + '\'' +
                '}';
    }
}
