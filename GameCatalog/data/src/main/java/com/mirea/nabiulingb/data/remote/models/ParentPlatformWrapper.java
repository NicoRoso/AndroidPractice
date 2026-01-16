package com.mirea.nabiulingb.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class ParentPlatformWrapper {
    @SerializedName("platform")
    private PlatformShort platform;
    public PlatformShort getPlatform() { return platform; }
}
