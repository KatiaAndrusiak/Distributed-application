package com.example.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Problem implements Parcelable {
    String category;
    String problem;
    String description;
    String date;
    String address;
    Double latitude;
    Double longitude;

    public String getAddress() {
        return address;
    }

    public Problem() {
        this.category = "";
        this.problem = "";
        this.description = "";
        this.date = "";
        this.address = "";
        this.latitude = -1.0;
        this.longitude = -1.0;
    }


    public Problem(String category, String problem, String description, String date, String address, Double latitude, Double longitude) {
        this.category = category;
        this.problem = problem;
        this.description = description;
        this.date = date;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Problem(Parcel in) {
        category = in.readString();
        problem = in.readString();
        description = in.readString();
        date = in.readString();
        address = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
    }

    public static final Creator<Problem> CREATOR = new Creator<Problem>() {
        @Override
        public Problem createFromParcel(Parcel in) {
            return new Problem(in);
        }

        @Override
        public Problem[] newArray(int size) {
            return new Problem[size];
        }
    };

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }



    @Override
    public String toString() {
        return "{" +
                "category:'" + category + '\'' +
                ", problem:'" + problem + '\'' +
                ", description:'" + description + '\'' +
                ", date:'" + date + '\'' +
                ", address:'" + address + '\'' +
                ", latitude:" + latitude +
                ", longitude:" + longitude +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(problem);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(address);
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
    }
}
