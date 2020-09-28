package com.swack.workshop.model;

public class TransportDetailsModel
{
    private String transport_id;
    private String transport_name;
    private String transport_mob;
    private String transport_lat;
    private String transport_long;
    private String Distance;

    public String getTransport_id() {
        return transport_id;
    }

    public void setTransport_id(String transport_id) {
        this.transport_id = transport_id;
    }

    public String getTransport_name() {
        return transport_name;
    }

    public void setTransport_name(String transport_name) {
        this.transport_name = transport_name;
    }

    public String getTransport_mob() {
        return transport_mob;
    }

    public void setTransport_mob(String transport_mob) {
        this.transport_mob = transport_mob;
    }

    public String getTransport_lat() {
        return transport_lat;
    }

    public void setTransport_lat(String transport_lat) {
        this.transport_lat = transport_lat;
    }

    public String getTransport_long() {
        return transport_long;
    }

    public void setTransport_long(String transport_long) {
        this.transport_long = transport_long;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }
}
