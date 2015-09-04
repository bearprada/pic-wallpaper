package com.demo.cblue.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * @author Thuy Trinh
 */
public class WebPhoto {

    public static final String EXTRA_WEB_PHOTO = "extra_webphoto";

    private static final String PATH_TO_INTERACTIVE = "/interactive";

    @SerializedName("id")
    private String mId;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("image_thumb")
    private String mThumbnailImageUrl;
    @SerializedName("image_original")
    private String mOriginalImageUrl;
    @SerializedName("image_large")
    private String mLargeImageUrl;
    @SerializedName("image_medium")
    private String mMediumImageUrl;
    @SerializedName("caption")
    private String mCaption;
    @SerializedName("like_total")
    private int mLikeNum;
    @SerializedName("liked")
    private boolean  mIsLiked;
    @SerializedName("created_at")
    private long mCreatedAt;
    @SerializedName("user")
    private PicUser mUser = new PicUser();
    @SerializedName("echoes_total")
    private int mEchoesNum;
    @SerializedName("page_url")
    private String mPageUrl;
    @SerializedName("echo_original_id")
    private String mEchoOriginal;
    @SerializedName("echo_progenitor_id")
    private String mEchoProgenitor;
    @SerializedName("is_interactive")
    private boolean mIsInteractive;
    @SerializedName("share_url")
    private String mShareUrl;
    @SerializedName("update_url")
    private String mUpdateUrl;
    @SerializedName("size")
    private CollageSize mSize;

    private transient boolean mIsSelected;
    protected Category mCategory = Category.PICCOLLAGE;

    public boolean isAd() {
        return !mCategory.equals(Category.PICCOLLAGE);
    }

    public void setCategory(Category category) {
        this.mCategory = category;
    }

    public void updateUser(PicUser user) {
        if (user == null && !user.isValid()) {
            return;
        }
        mUser = user;
    }

    public enum Category {
        PICCOLLAGE,
        AD_FACEBOOK,
        AD_ADMOB}

    public WebPhoto() {
        mId = UUID.randomUUID().toString();
        mUrl = "";
        mThumbnailImageUrl = "";
        mOriginalImageUrl = "";
        mLargeImageUrl = "";
        mMediumImageUrl = "";
        setPageUrl("");
        mCaption = "";
        mLikeNum = 0;
        mIsLiked = false;
        mCreatedAt = 0;
        mEchoesNum = 0;
        mEchoOriginal = "";
        mEchoProgenitor = "";
        mIsInteractive = false;
        mCategory = Category.PICCOLLAGE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof WebPhoto)) {
            return false;
        }
        return this.mId.equals(((WebPhoto) o).mId);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getThumbnailImageUrl() {
        return mThumbnailImageUrl;
    }

    public String getOriginalImageUrl() {
        return mOriginalImageUrl;
    }

    public String getLargeImageUrl() {
        return mLargeImageUrl;
    }

    public String getMediumImageUrl() {
        return mMediumImageUrl;
    }

    public String getCaption() {
        return mCaption;
    }

    public int getLikeNum() {
        return mLikeNum;
    }

    public void setLikeNum(int likeNum) {
        this.mLikeNum = likeNum;
    }

    public int getEchoesNum() {
        return mEchoesNum;
    }

    public void setEchoesNum(int echoesTotal) {
        mEchoesNum = echoesTotal;
    }

    public boolean isLiked() {
        return mIsLiked;
    }

    public long getCreatedTime() {
        return mCreatedAt;
    }

    public PicUser getUser() {
        return mUser;
    }

    public void setIsLiked(boolean isLiked) {
        this.mIsLiked = isLiked;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    public void toggleIsLiked() {
        mIsLiked = !mIsLiked;
        if (mIsLiked) {
            mLikeNum++;
        } else {
            mLikeNum--;
        }
    }

    public String getPageUrl() {
        return mPageUrl;
    }

    public void setPageUrl(String pageUrl) {
        mPageUrl = pageUrl;
    }

    public String getEchoOriginal() {
        return mEchoOriginal;
    }

    public String getEchoProgenitor() {
        return mEchoProgenitor;
    }

    public boolean isInteractive() {
        return mIsInteractive;
    }

    public String getInteractiveUrl() {
        if (TextUtils.isEmpty(mUrl)) throw new IllegalStateException("Url is null. collage id: " + mId);
        return mUrl + PATH_TO_INTERACTIVE;
    }

    public String getUpdateUrl() {
        return mUpdateUrl;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public boolean isSquare() {
        if (mSize == null) {
            return false;
        }
        return mSize.isSquare();
    }

    public static class CollageSize {
        private int width = -1;
        private int height = -1;

        public CollageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
        public boolean isSquare() {
            if (width == -1 || height == -1) {
                return false;
            }
            return width == height;
        }
        public int getWidth() {
            return width;
        }
        public int getHeight() {
            return height;
        }
    }
}