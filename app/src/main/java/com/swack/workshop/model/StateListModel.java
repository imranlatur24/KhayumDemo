package com.swack.workshop.model;

import java.util.ArrayList;

public class StateListModel {


    public String response;

    public ArrayList<StateListOk> StateListOk;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ArrayList<StateListOk> getStateListOk() {
        return StateListOk;
    }

    public void setStateListOk(ArrayList<StateListOk> StateListOk) {
        StateListOk = StateListOk;
    }

    public static class StateListOk {
        private String state_id;
        private String state_name;

        public StateListOk(String state_id, String state_name) {
            this.state_id = state_id;
            this.state_name = state_name;
        }

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getState_name() {
            return state_name;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }
    }
}
