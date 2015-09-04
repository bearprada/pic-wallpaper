package com.demo.cblue.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Created by prada on 15/9/2.
 */
public class CBCollagesResponse {

    private WebPhotosData mCollages;

    private Date mDownloadedDate = null;

    public int getOffset() {
        return mCollages.getOffset();
    }

    public int getTotal() {
        return mCollages.getTotal();
    }

    public String getNextPageUrl() {
        return mCollages.getNextPageUrl();
    }

    public List<WebPhoto> getPhotos() {
        return mCollages.getPhotos();
    }

    public String getListRevision() {
        return mCollages.getListRevision();
    }

    public void addMoreCollage(CBCollagesResponse data) {
        mCollages.addMoreCollage(data);
        setDownloadedDate(new Date());
    }

    public void deletePhoto(int pos) {
        mCollages.deletePhoto(pos);
    }

    public void deleteById(String id) {
        int index = -1;
        for (int i = 0; i < mCollages.getPhotos().size(); i++) {
            if (mCollages.getPhotos().get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            mCollages.deletePhoto(index);
        }
    }

    public CBCollagesResponse() {
        this(new WebPhotosData());
    }

    CBCollagesResponse(WebPhotosData collages) {
        this.mCollages = collages;
    }

    public WebPhotosData getCollages() {
        return mCollages;
    }

    public void setCollages(WebPhotosData collages) {
        this.mCollages = collages;
    }

    public Date getDownloadedDate() {
        return mDownloadedDate;
    }

    public void setDownloadedDate(Date downloadedDate) {
        this.mDownloadedDate = downloadedDate;
    }

    public static class CBCollagesResponseDeserializer implements JsonDeserializer<CBCollagesResponse> {

        private static final String PC_API_FEED_NODE      = "collages";
        private static final String PC_API_SEARCH_NODE    = "search";
        private static final String PC_API_LIKES_NODE     = "likes";
        private static final String PC_API_PROMOTION_NODE = "promotion";

        @Override
        public CBCollagesResponse deserialize(JsonElement json, Type typeofT,
                                              final JsonDeserializationContext context) throws JsonParseException {
            WebPhotosData collages = new WebPhotosData();
            final JsonObject jsonObject = json.getAsJsonObject();
            JsonElement collagesData = null;
            if (jsonObject.has(PC_API_FEED_NODE)) {
                collagesData = jsonObject.get(PC_API_FEED_NODE);
            } else if (jsonObject.has(PC_API_LIKES_NODE)) {
                collagesData = jsonObject.get(PC_API_LIKES_NODE);
            } else if (jsonObject.has(PC_API_SEARCH_NODE)) {
                collagesData = jsonObject.get(PC_API_SEARCH_NODE);
            }
            if (collagesData != null) {
                collages = context.deserialize(collagesData, WebPhotosData.class);
            }
            return new CBCollagesResponse(collages);
        }
    }

    public static CBCollagesResponse newInstance(WebPhoto webPhoto) {
        List<WebPhoto> data = new ArrayList<WebPhoto>();
        data.add(webPhoto);
        WebPhotosData photosData = new WebPhotosData("", data);
        return new CBCollagesResponse(photosData);
    }
}