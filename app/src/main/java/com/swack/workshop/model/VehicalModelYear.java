package com.swack.workshop.model;

import java.util.ArrayList;

public class VehicalModelYear
{
    private String response;
    private ArrayList<ModelYearList> ModalYearList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ArrayList<ModelYearList> getModalYearList() {
        return ModalYearList;
    }

    public void setModalYearList(ArrayList<ModelYearList> modalYearList) {
        ModalYearList = modalYearList;
    }

    public static class ModelYearList{
        private String vehicle_mody_id;
        private String vehicle_mody_name;

        public ModelYearList(String vehicle_mody_id, String vehicle_mody_name) {
            this.vehicle_mody_id = vehicle_mody_id;
            this.vehicle_mody_name = vehicle_mody_name;
        }

        public String getVehicle_mody_id() {
            return vehicle_mody_id;
        }

        public void setVehicle_mody_id(String vehicle_mody_id) {
            this.vehicle_mody_id = vehicle_mody_id;
        }

        public String getVehicle_mody_name() {
            return vehicle_mody_name;
        }

        public void setVehicle_mody_name(String vehicle_mody_name) {
            this.vehicle_mody_name = vehicle_mody_name;
        }
    }
}
