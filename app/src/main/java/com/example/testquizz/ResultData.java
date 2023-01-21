package com.example.testquizz;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultData implements Parcelable {
    private int totalTrues;
    private int totalWrongs;
    private int totalQuestions;


    public ResultData(int totalTrues, int totalWrongs, int totalQuestions) {
        this.totalTrues = totalTrues;
        this.totalWrongs = totalWrongs;
        this.totalQuestions = totalQuestions;
    }

    protected ResultData(Parcel in) {
        totalTrues = in.readInt();
        totalWrongs = in.readInt();
        totalQuestions = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalTrues);
        dest.writeInt(totalWrongs);
        dest.writeInt(totalQuestions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultData> CREATOR = new Creator<ResultData>() {
        @Override
        public ResultData createFromParcel(Parcel in) {
            return new ResultData(in);
        }

        @Override
        public ResultData[] newArray(int size) {
            return new ResultData[size];
        }
    };

    public void setTotalTrues(int totalTrues) {
        this.totalTrues = totalTrues;
    }

    public int getTotalTrues() {
        return totalTrues;
    }

    public int getTotalWrongs() {
        return totalWrongs;
    }

    public void setTotalWrongs(int totalWrongs) {
        this.totalWrongs = totalWrongs;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
