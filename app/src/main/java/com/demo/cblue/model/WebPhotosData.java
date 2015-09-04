package com.demo.cblue.model;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class WebPhotosData {

    @SerializedName("offset")
    private int mOffset;
    @SerializedName("total")
    private int mTotal;
    @SerializedName("next_page")
    private String mNextPageUrl;
    @SerializedName("list_revision")
    private String mListRevision;
    @SerializedName("data")
    private List<WebPhoto> mPhotos = new LinkedList<>();

    WebPhotosData() {
        init();
    }

    public WebPhotosData(String nextPageUrl, List<WebPhoto> photos) {
        init();
        this.mNextPageUrl = nextPageUrl;
        this.mPhotos = photos;
    }

    private void init() {
        mOffset = 0;
        mTotal = 0;
        mNextPageUrl = "";
        mListRevision = "";
    }

    public int getOffset() {
        return mOffset;
    }

    public int getTotal() {
        return mTotal;
    }

    public String getNextPageUrl() {
        return mNextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.mNextPageUrl = nextPageUrl;
    }

    public List<WebPhoto> getPhotos() {
        return mPhotos;
    }

    public String getListRevision() {
        return mListRevision;
    }
    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof WebPhotosData
                && mListRevision.equals(((WebPhotosData) o).mListRevision);
    }

    void addMoreCollage(CBCollagesResponse data) {
        mNextPageUrl = data.getCollages().getNextPageUrl();
        for(WebPhoto photo : data.getCollages().getPhotos()) {
            int index = mPhotos.indexOf(photo);
            if (index != -1) {
                if (index >= 0 || index < mPhotos.size()) {
                    WebPhoto currentPhoto = mPhotos.get(index);
                    currentPhoto.setIsLiked(photo.isLiked());
                    currentPhoto.setLikeNum(photo.getLikeNum());
                    currentPhoto.setEchoesNum(photo.getEchoesNum());
                }
            } else {
                mPhotos.add(photo);
            }
        }
    }

    void deletePhoto(int pos) {
        if(mPhotos != null && mPhotos.size() > pos) {
            mPhotos.remove(pos);
        }
    }
}