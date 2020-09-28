package com.swack.workshop.model;

import java.util.ArrayList;

public class ForgotModel {

    private String response;
    public ArrayList<Forgot> result;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ArrayList<Forgot> getResult() {
        return result;
    }

    public void setResult(ArrayList<Forgot> result) {
        this.result = result;
    }

}
