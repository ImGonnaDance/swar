package com.wellbia.xigncode.util;

public class WBDisplayResolution {
    private int deviceHeight = 0;
    private int deviceWidth = 0;

    public WBDisplayResolution(int i, int i2) {
        this.deviceWidth = i;
        this.deviceHeight = i2;
    }

    public int getDeviceWidth() {
        return this.deviceWidth;
    }

    public void setDeviceWidth(int i) {
        this.deviceWidth = i;
    }

    public int getDeviceHeight() {
        return this.deviceHeight;
    }

    public void setDeviceHeight(int i) {
        this.deviceHeight = i;
    }
}
