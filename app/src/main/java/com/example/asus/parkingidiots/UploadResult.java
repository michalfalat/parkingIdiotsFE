package com.example.asus.parkingidiots;

/**
 * Created by Asus on 17.12.2017.
 */

public class UploadResult {
    private boolean success;
    private String responseText;

    public UploadResult(boolean success, String responseText) {
        this.success = success;
        this.responseText = responseText;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
