package com.roninn_creations.theproject.models;

public class Place {

    private String id;
    private String name;
    private Address address;
    private String[] tags;

    public Place(String id, String name, String street, String post, String city, String[] tags) {
        this.id = id;
        this.name = name;
        this.address = new Address(street, post, city);
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

    public static class  Address {
        private String street;
        private String post;
        private String city;

        private Address(String street, String post, String city){
            this.street = street;
            this.post = post;
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return String.format("%s\n%s, %s", street, post, city);
        }
    }
}
