package com.constantine.ordercapture.model;

/*
    The Order should be identified by its ID, which should be a unique alphanumeric string among Order instances.
    The Order will also have a related customerID, which will be the ID of the Customer that places the order,
    a list of related productIDs (the list of Products that the Customer orders), an orderDate (date), a lastUpdateDate (date) and
    an orderStatus (possible values are NEW, FAILED, COMPLETED).
 */


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * Order class defines the order object of the system
 */
@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name="ORDER_PRODUCT",
            joinColumns=@JoinColumn(name="order_id", referencedColumnName="id", nullable = false),
            inverseJoinColumns=@JoinColumn(name="product_id", referencedColumnName="id", nullable = false))
    private List<Product> products;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "last_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(String id,
                 Customer customer,
                 List<Product> products,
                 Date orderDate,
                 Date lastUpdateDate,
                 OrderStatus orderStatus) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.orderDate = orderDate;
        this.lastUpdateDate = lastUpdateDate;
        this.orderStatus = orderStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public static class Builder {
        private String id;

        private Customer customer;

        private List<Product> products;

        private Date orderDate;

        private Date lastUpdateDate;

        private OrderStatus orderStatus;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder withProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        public Builder withOrderDate(Date orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder withLastUpdateDate(Date lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
            return this;
        }

        public Builder withOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Order build() {
            return new Order(this.id, this.customer, this.products, this.orderDate, this.lastUpdateDate, this.orderStatus);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customer=" + customer +
                ", products=" + products +
                ", orderDate=" + orderDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
