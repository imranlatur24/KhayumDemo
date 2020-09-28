package com.swack.workshop.model;


import com.swack.workshop.model.FinalListModel;
import com.swack.workshop.model.GarList;
import com.swack.workshop.model.GarageProfileList;
import com.swack.workshop.model.LoginModel;
import com.swack.workshop.model.OrderListModel;
import com.swack.workshop.model.RegistrationModel;
import com.swack.workshop.model.SliderList;
import com.swack.workshop.model.SupportListModel;
import com.swack.workshop.model.TransportDetailsModel;
import com.swack.workshop.model.VehicalListModel;
import com.swack.workshop.model.VersionModel;

import java.util.ArrayList;

public class ResponseResult {

    private String response;
    private ArrayList<GarageProfileList> GarageProfileList;
    public ArrayList<RegistrationModel> GarRegMsgList;
    public ArrayList<LoginModel> login;
    public ArrayList<VehicalListModel> VehicleList;
    public ArrayList<SliderList> slider_list;
    public ArrayList<VersionModel> VersionDetails;
    public ArrayList<TransportDetailsModel> transport_detail_list;
    public ArrayList<SupportListModel> SupportList;
    public ArrayList<OrderListModel> ConfirmOrderList;
    public ArrayList<OrderListModel> OrderList;
    public ArrayList<GarList> DashOrderList;
    public ArrayList<FinalListModel> JobItemFinalList;

    public ArrayList<com.swack.workshop.model.GarageProfileList> getGarageProfileList() {
        return GarageProfileList;
    }

    public void setGarageProfileList(ArrayList<com.swack.workshop.model.GarageProfileList> garageProfileList) {
        GarageProfileList = garageProfileList;
    }

    public ArrayList<FinalListModel> getJobItemFinalList() {
        return JobItemFinalList;
    }

    public void setJobItemFinalList(ArrayList<FinalListModel> jobItemFinalList) {
        JobItemFinalList = jobItemFinalList;
    }

    public ArrayList<GarList> getDashOrderList() {
        return DashOrderList;
    }

    public void setDashOrderList(ArrayList<GarList> dashOrderList) {
        DashOrderList = dashOrderList;
    }

    public ArrayList<OrderListModel> getOrderList() {
        return OrderList;
    }

    public void setOrderList(ArrayList<OrderListModel> orderList) {
        OrderList = orderList;
    }

    public ArrayList<OrderListModel> getConfirmOrderList() {
        return ConfirmOrderList;
    }

    public void setConfirmOrderList(ArrayList<OrderListModel> confirmOrderList) {
        ConfirmOrderList = confirmOrderList;
    }

    public ArrayList<RegistrationModel> getGarRegMsgList() {
        return GarRegMsgList;
    }

    public void setGarRegMsgList(ArrayList<RegistrationModel> garRegMsgList) {
        GarRegMsgList = garRegMsgList;
    }

    public ArrayList<SupportListModel> getSupportList() {
        return SupportList;
    }

    public void setSupportList(ArrayList<SupportListModel> supportList) {
        SupportList = supportList;
    }

    public ArrayList<VersionModel> getVersionDetails() {
        return VersionDetails;
    }

    public void setVersionDetails(ArrayList<VersionModel> versionDetails) {
        VersionDetails = versionDetails;
    }

    public ArrayList<TransportDetailsModel> getTransport_detail_list() {
        return transport_detail_list;
    }

    public void setTransport_detail_list(ArrayList<TransportDetailsModel> transport_detail_list) {
        this.transport_detail_list = transport_detail_list;
    }


    public ArrayList<SliderList> getSlider_list() {
        return slider_list;
    }

    public void setSlider_list(ArrayList<SliderList> slider_list) {
        this.slider_list = slider_list;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    public ArrayList<LoginModel> getLogin() {
        return login;
    }

    public void setLogin(ArrayList<LoginModel> login) {
        this.login = login;
    }


    public ArrayList<VehicalListModel> getVehicleList() {
        return VehicleList;
    }

    public void setVehicleList(ArrayList<VehicalListModel> vehicleList) {
        VehicleList = vehicleList;
    }
}
