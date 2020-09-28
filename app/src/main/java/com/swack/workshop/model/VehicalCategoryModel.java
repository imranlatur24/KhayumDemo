package com.swack.workshop.model;

import java.util.ArrayList;

public class VehicalCategoryModel {

    private String response;
    private ArrayList<VehicalCategoryList> VehicleCatList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ArrayList<VehicalCategoryList> getVehicleCatList() {
        return VehicleCatList;
    }

    public void setVehicleCatList(ArrayList<VehicalCategoryList> vehicleCatList) {
        VehicleCatList = vehicleCatList;
    }

    public static class VehicalCategoryList{
        private String vehicle_cat_id;
        private String vehicle_id;
        private String vehicle_cat_name;

        public VehicalCategoryList(String vehicle_cat_id, String vehicle_id, String vehicle_cat_name) {
            this.vehicle_cat_id = vehicle_cat_id;
            this.vehicle_id = vehicle_id;
            this.vehicle_cat_name = vehicle_cat_name;
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

        public String getVehicle_cat_name() {
            return vehicle_cat_name;
        }

        public void setVehicle_cat_name(String vehicle_cat_name) {
            this.vehicle_cat_name = vehicle_cat_name;
        }
    }
}
