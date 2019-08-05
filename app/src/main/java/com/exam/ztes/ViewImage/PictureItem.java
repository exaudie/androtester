package com.exam.ztes.ViewImage;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


class PictureItem implements Parcelable{
    public Uri uri;
    public String date;

    public PictureItem() {
    }

    protected PictureItem(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
        date = in.readString();
    }

    public static final Creator<PictureItem> CREATOR = new Creator<PictureItem>() {
        @Override
        public PictureItem createFromParcel(Parcel in) {
            return new PictureItem(in);
        }

        @Override
        public PictureItem[] newArray(int size) {
            return new PictureItem[size];
        }
    };

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(uri, i);
        parcel.writeString(date);
    }
}
