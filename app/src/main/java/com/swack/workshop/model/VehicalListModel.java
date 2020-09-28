package com.swack.workshop.model;

import java.util.ArrayList;

public class VehicalListModel {

    public String response;
    public ArrayList<VehicalList> VehicleList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ArrayList<VehicalList> getVehicleList() {
        return VehicleList;
    }

    public void setVehicleList(ArrayList<VehicalList> vehicleList) {
        VehicleList = vehicleList;
    }

    public static class VehicalList{
        private String vehicle_id;
        private String vehicle_name;

        public VehicalList(String vehicle_id, String vehicle_name) {
            this.vehicle_id = vehicle_id;
            this.vehicle_name = vehicle_name;
        }

        public String getVehicle_id() {
            return vehicle_id;
        }

        public void setVehicle_id(String vehicle_id) {
            this.vehicle_id = vehicle_id;
        }

        public String getVehicle_name() {
            return vehicle_name;
        }

        public void setVehicle_name(String vehicle_name) {
            this.vehicle_name = vehicle_name;
        }
    }
}
