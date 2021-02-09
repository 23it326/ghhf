
package com.plantix.demo.Beans;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class Recipe implements Parcelable
{

    @SerializedName("image_url")

    private String imageUrl;
    @SerializedName("social_rank")

    private Double socialRank;
    @SerializedName("_id")

    private String id;
    @SerializedName("publisher")

    private String publisher;
    @SerializedName("source_url")

    private String sourceUrl;
    @SerializedName("recipe_id")

    private String recipeId;
    @SerializedName("publisher_url")

    private String publisherUrl;
    @SerializedName("title")

    private String title;
    private final static long serialVersionUID = 7016887435001120299L;

    protected Recipe(Parcel in) {
        imageUrl = in.readString();
        if (in.readByte() == 0) {
            socialRank = null;
        } else {
            socialRank = in.readDouble();
        }
        id = in.readString();
        publisher = in.readString();
        sourceUrl = in.readString();
        recipeId = in.readString();
        publisherUrl = in.readString();
        title = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getSocialRank() {
        return socialRank;
    }

    public void setSocialRank(Double socialRank) {
        this.socialRank = socialRank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        if (socialRank == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(socialRank);
        }
        dest.writeString(id);
        dest.writeString(publisher);
        dest.writeString(sourceUrl);
        dest.writeString(recipeId);
        dest.writeString(publisherUrl);
        dest.writeString(title);
    }
}
