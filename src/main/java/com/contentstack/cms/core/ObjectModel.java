package com.contentstack.cms.core;

import org.json.simple.JSONObject;

/**
 * The class Json contains methods to create the builder object. The example code below shows how to build an empty
 * JsonObject instance
 * <p>
 * JsonObject object = Json.createObjectBuilder().build();
 */
final class ObjectModel {

    // final instance fields
    private final int id;
    private final String name;
    private final String address;

    public ObjectModel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
    }

    // Static class Builder
    public static class Builder {

        /// instance fields
        private int id;
        private String name;
        private String address;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {
        }

        // Setter methods
        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        // build method to deal with outer class
        // to return outer instance
        public ObjectModel build() {
            return new ObjectModel(this);
        }
    }


    @Override
    public String toString() {
        return "ObjectModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}