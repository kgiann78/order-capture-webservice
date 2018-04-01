package com.constantine.ordercapture.model;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrder {
    private String customerID;

    private List<String> productIDs;

    public PlaceOrder() {
        this.productIDs = new ArrayList<>();
    }

    public PlaceOrder(String customerID, List<String> productIDs) {
        this.customerID = customerID;
        this.productIDs = productIDs;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public List<String> getProductIDs() {
        return productIDs;
    }

    public void setProductIDs(List<String> productIDs) {
        this.productIDs = productIDs;
    }

    public static class Builder {
        private String customer;

        private List<String> products = new ArrayList<>();

        Builder withCustomerId(String customerId) {
            this.customer = customerId;
            return this;
        }

        Builder withProductId(String productId) {
            this.products.add(productId);
            return this;
        }

        PlaceOrder build() {
            return new PlaceOrder(customer, products);
        }
    }
}
