package com.swack.workshop.model;

import java.util.ArrayList;

public class VehicalLoadRangeModel
{
    private String response;
    private ArrayList<VehicalLoadList> VehicleLodRngList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ArrayList<VehicalLoadList> getVehicleLodRngList() {
        return VehicleLodRngList;
    }

    public void setVehicleLodRngList(ArrayList<VehicalLoadList> vehicleLodRngList) {
        VehicleLodRngList = vehicleLodRngList;
    }

    public static class VehicalLoadList{
        private String loadrang_id;
        private String vehicle_cat_id;
        private String vehicle_id;
        private String loadrang_name;

        public VehicalLoadList(String loadrang_id, String vehicle_cat_id, String vehicle_id, String loadrang_name) {
            this.loadrang_id = loadrang_id;
            this.vehicle_cat_id = vehicle_cat_id;
            this.vehicle_id = vehicle_id;
            this.loadrang_name = loadrang_name;
        }

        public String getLoadrang_id() {
            return loadrang_id;
        }

        public void setLoadrang_id(String loadrang_id) {
            this.loadrang_id = loadrang_id;
        }

        public String getVehicle_cat_id() {
            return vehicle_cat_id;
        }

        public void setVehicle_cat_id(String vehicle_cat_id) {
            this.vehicle_cat_id = vehicle_cat_id;
        }

        public String getVehicle_id() {
            return vehicle_id;
        }

        public void setVehicle_id(String vehicle_id) {
            this.vehicle_id = vehicle_id;
        }

        public String getLoadrang_name() {
            return loadrang_name;
        }

        public void setLoadrang_name(String loadrang_name) {
            this.loadrang_name = loadrang_name;
        }
    }
}
