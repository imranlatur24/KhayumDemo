package com.swack.workshop.model;

public class CountryList
{




    private String id;
    private String sortname;
    private String name;
    private String phonecode;

    public CountryList(String id, String sortname, String name, String phonecode) {
        this.id = id;
        this.sortname = sortname;
        this.name = name;
        this.phonecode = phonecode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }
}
