package com.constantine.ordercapture.model;

/*
    The Product should be identified by its ID, which should be a unique alphanumeric string among Product instances.
    The Product will also have a name (string), an SKU (string) and a free text description (string).
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Product class that defines product objects of the system
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    private String sku;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy="products")
    @JsonIgnore
    private List<Order> orders;

    public Product() {
    }

    public Product(String id, String name, String sku, String description) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.description = description;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public static class Builder {
        private String id;

        private String name;

        private String sku;

        private String description;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSKU(String sku) {
            this.sku = sku;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Product build() {
            return new Product(this.id, this.name, this.sku, this.description);
        }
    }


    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
