package com.entity;

import java.io.Serializable;

public class Publisher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id, name, address;

    public Publisher() {
    }

    public Publisher(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Publisher) {
            Publisher p = (Publisher)obj;
            return p.getId().equalsIgnoreCase(this.id);
        }
        return false;
    }
    
}
