package com.example.homework_chapter_5.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageListResponse {
    @SerializedName("feeds")
    public List<Message> feeds;
    @SerializedName("success")
    public boolean success;
}
