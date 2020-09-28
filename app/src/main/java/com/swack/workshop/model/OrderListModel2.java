package com.swack.workshop.model;

import java.util.ArrayList;

public class OrderListModel2
{
    private String response;
    public ArrayList<Order> OrderList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ArrayList<Order> getOrderList() {
        return OrderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        OrderList = orderList;
    }

    public static class Order{
        private String ordreq_date;

        private String vehicle_cat_name;

        private String status_name;

        private String vehicled_regno;

        private String ordreq_status;

        private String gar_id;

        private String cus_id;

        private String ordreq_id;

        private String cus_name;

        private String garage_id;

        private String cus_mob;

        private String ordrequniq_id;

        private String ordreq_location;

        private String vehicled_id;

        private String service_typ_name;

        private String Distance;

        private String vehicle_name;

        private String is_create;

        private String gar_lat;

        private String gar_long;


        public String getGar_lat() {
            return gar_lat;
        }

        public void setGar_lat(String gar_lat) {
            this.gar_lat = gar_lat;
        }

        public String getGar_long() {
            return gar_long;
        }

        public void setGar_long(String gar_long) {
            this.gar_long = gar_long;
        }

        public String getOrdreq_date ()
        {
            return ordreq_date;
        }

        public void setOrdreq_date (String ordreq_date)
        {
            this.ordreq_date = ordreq_date;
        }

        public String getVehicle_cat_name ()
        {
            return vehicle_cat_name;
        }

        public void setVehicle_cat_name (String vehicle_cat_name)
        {
            this.vehicle_cat_name = vehicle_cat_name;
        }

        public String getStatus_name ()
        {
            return status_name;
        }

        public void setStatus_name (String status_name)
        {
            this.status_name = status_name;
        }

        public String getVehicled_regno ()
        {
            return vehicled_regno;
        }

        public void setVehicled_regno (String vehicled_regno)
        {
            this.vehicled_regno = vehicled_regno;
        }

        public String getOrdreq_status ()
        {
            return ordreq_status;
        }

        public void setOrdreq_status (String ordreq_status)
        {
            this.ordreq_status = ordreq_status;
        }

        public String getGar_id ()
        {
            return gar_id;
        }

        public void setGar_id (String gar_id)
        {
            this.gar_id = gar_id;
        }

        public String getCus_id ()
        {
            return cus_id;
        }

        public void setCus_id (String cus_id)
        {
            this.cus_id = cus_id;
        }

        public String getOrdreq_id ()
        {
            return ordreq_id;
        }

        public void setOrdreq_id (String ordreq_id)
        {
            this.ordreq_id = ordreq_id;
        }

        public String getCus_name ()
        {
            return cus_name;
        }

        public void setCus_name (String cus_name)
        {
            this.cus_name = cus_name;
        }

        public String getGarage_id ()
        {
            return garage_id;
        }

        public void setGarage_id (String garage_id)
        {
            this.garage_id = garage_id;
        }

        public String getCus_mob ()
        {
            return cus_mob;
        }

        public void setCus_mob (String cus_mob)
        {
            this.cus_mob = cus_mob;
        }

        public String getOrdrequniq_id ()
        {
            return ordrequniq_id;
        }

        public void setOrdrequniq_id (String ordrequniq_id)
        {
            this.ordrequniq_id = ordrequniq_id;
        }

        public String getOrdreq_location ()
        {
            return ordreq_location;
        }

        public void setOrdreq_location (String ordreq_location)
        {
            this.ordreq_location = ordreq_location;
        }

        public String getVehicled_id ()
        {
            return vehicled_id;
        }

        public void setVehicled_id (String vehicled_id)
        {
            this.vehicled_id = vehicled_id;
        }

        public String getService_typ_name ()
        {
            return service_typ_name;
        }

        public void setService_typ_name (String service_typ_name)
        {
            this.service_typ_name = service_typ_name;
        }

        public String getDistance ()
        {
            return Distance;
        }

        public void setDistance (String Distance)
        {
            this.Distance = Distance;
        }

        public String getVehicle_name ()
        {
            return vehicle_name;
        }

        public void setVehicle_name (String vehicle_name)
        {
            this.vehicle_name = vehicle_name;
        }

        public String getIs_create ()
        {
            return is_create;
        }

        public void setIs_create (String is_create)
        {
            this.is_create = is_create;
        }
    }
}
