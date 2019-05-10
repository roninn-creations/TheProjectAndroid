package com.roninn_creations.theproject.models;

public class Place {

    public class  Address {
        public String street;
        public String post;
        public String city;

        @Override
        public String toString() {
            return String.format("%s\n%s, %s", street, post, city);
        }
    }

    private String id;
    private String name;
    private Address address;
    private String[] tags;

    public Place(String id, String name, Address address, String[] tags) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tags = tags;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
