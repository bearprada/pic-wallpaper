package com.demo.cblue.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class PicUser implements Parcelable {

    public static final Parcelable.Creator<PicUser> CREATOR = new Creator();

    @SerializedName(JSON_KEY_USER_ID)
    private String mId;
    @SerializedName(JSON_KEY_USER_USERNAME)
    private String mUsername;
    @SerializedName(JSON_KEY_USER_NAME)
    private String mName;
    @SerializedName(JSON_KEY_USER_EMAIL)
    private String mEmail;
    @SerializedName(JSON_KEY_USER_PROFILE_IMAGE_URL)
    private String mProfileImageUrl;
    @SerializedName(JSON_KEY_IS_FOLLOWING)
    private Boolean mIsFollowing;
    @SerializedName(JSON_KEY_FOLLOWERS_COUNT)
    private int mFollowersCount;
    @SerializedName(JSON_KEY_FOLLOWING_COUNT)
    private int mFollowingCount;
    @SerializedName(JSON_KEY_COLLAGES_COUNT)
    private int mCollagesCount;
    @SerializedName(JSON_KEY_LIKED_COLLAGES_COUNT)
    private int mLikedCollagesCount;
    @SerializedName(JSON_KEY_PRIVACY_MODE)
    private String mPrivateMode;
    @SerializedName(JSON_KEY_IS_BLOCKED)
    private boolean mIsBlocked = false;

    public boolean isBlocked() {
        return mIsBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.mIsBlocked = isBlocked;
    }

    public enum PicRelation {
        ME,
        FOLLOWING,
        UNFOLLOW
    }

    public enum PicPrivateMode {
        ONLY_ME,
        EVERYONE,
        MYSELF
    }

    private static final String JSON_KEY_USER_ID = "id";
    private static final String JSON_KEY_USER_USERNAME = "username";
    private static final String JSON_KEY_USER_NAME = "name";
    private static final String JSON_KEY_USER_EMAIL = "email";
    private static final String JSON_KEY_USER_PROFILE_IMAGE_URL = "profile_image_url";
    private static final String JSON_KEY_PRIVACY_MODE = "privacy_mode";
    private static final String JSON_KEY_IS_FOLLOWING = "is_following";
    private static final String JSON_KEY_FOLLOWERS_COUNT = "followers_count";
    private static final String JSON_KEY_FOLLOWING_COUNT = "followed_users_count";
    private static final String JSON_KEY_COLLAGES_COUNT = "collages_count";
    private static final String JSON_KEY_LIKED_COLLAGES_COUNT = "liked_collages_count";
    private static final String JSON_KEY_IS_BLOCKED = "is_blocked";

    private static final String EMPTY_ID = "";

    public PicUser() {
        mId = EMPTY_ID;
        mUsername = "";
        mName = "";
        mEmail = "";
        mProfileImageUrl = "";
        mIsFollowing = false;
        mFollowersCount = 0;
        mFollowingCount = 0;
        mCollagesCount = 0;
        mLikedCollagesCount = 0;
    }

    public PicUser(Parcel parcel) {
        readFromParcel(parcel);
    }

    /**
     * Parse object {@link PicUser} to JSONString
     */
    public String toJSONString(){
        return new GsonBuilder().create().toJson(this, PicUser.class).toString();
    }

    public String getId() {
        return (mId == null) ? "" : mId;
    }

    public String getUsername() {
        return (mUsername == null) ? "" : mUsername;
    }

    public String getName() {
        return (mName == null) ? "" : mName;
    }

    public String getEmail() {
        return (mEmail == null) ? "" : mEmail;
    }

    public String getProfileImageUrl() {
        return (mProfileImageUrl == null) ? "" : mProfileImageUrl;
    }

    public void setIsFollowing(boolean isFollowing) {
        mIsFollowing = isFollowing;
    }

    public PicPrivateMode getPicPrivateMode() {
        // The privacy_mode has three condition :
        // if query the data from feature/user likes/other users post.
        // it will contain "only_me" or "everyone".
        // but if you query data from yous. the feild will be null.
        if(mPrivateMode==null) {
            return PicPrivateMode.MYSELF;
        } else if(mPrivateMode.equals("only_me")) {
            return PicPrivateMode.ONLY_ME;
        } else {
            return PicPrivateMode.EVERYONE;
        }
    }

    public void toggleFollowing() {
        mIsFollowing = !mIsFollowing;
    }

    public Boolean isFollowing() {
        return mIsFollowing;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) {
            return false;
        }
        PicUser comp = (PicUser) obj;
        return comp.getId().equals(getId());
    }

    public void setFollowersCount(int followersCount) {
        mFollowersCount = followersCount;
    }

    public int getFollowersCount() {
        return mFollowersCount;
    }

    public int getFollowingCount() {
        return mFollowingCount;
    }

    public int getCollagesCount() {
        return mCollagesCount;
    }

    public int getLikedCollagesCount() {
        return mLikedCollagesCount;
    }

    public String getDisplayName() {
        return (!TextUtils.isEmpty(mUsername)) ? mUsername : mName;
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(mId);
    }

    public boolean isPrivate() {
        return getPicPrivateMode() == PicUser.PicPrivateMode.ONLY_ME;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(getId());
        parcel.writeString(getUsername());
        parcel.writeString(getName());
        parcel.writeString(getEmail());
        parcel.writeString(getProfileImageUrl());
        parcel.writeByte((byte) (isFollowing()? 1:0));
        parcel.writeInt(getFollowersCount());
        parcel.writeInt(getFollowingCount());
        parcel.writeInt(getCollagesCount());
        parcel.writeInt(getLikedCollagesCount());
        parcel.writeString(mPrivateMode);
    }

    private void readFromParcel(Parcel parcel) {
        mId = parcel.readString();
        mUsername = parcel.readString();
        mName = parcel.readString();
        mEmail = parcel.readString();
        mProfileImageUrl = parcel.readString();
        mIsFollowing = parcel.readByte() == 1;
        mFollowersCount = parcel.readInt();
        mFollowingCount = parcel.readInt();
        mCollagesCount = parcel.readInt();
        mLikedCollagesCount = parcel.readInt();
        mPrivateMode = parcel.readString();
    }

    public static class Creator implements Parcelable.Creator<PicUser> {

        @Override
        public PicUser createFromParcel(Parcel parcel) {
            return new PicUser(parcel);
        }

        @Override
        public PicUser[] newArray(int size) {
            return new PicUser[size];
        }
    }

    public static class PicUserDataChangedEvent {}
}