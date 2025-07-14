package com.example.Bill_System.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;
    private String name;
    private String mobileNumber;
    private String productName;
    private Integer productCount;
    private boolean billApplied = false;

    public Customer() {
    }

    public Customer(String name, String mobileNumber, String productName, Integer productCount, boolean billApplied) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.productName = productName;
        this.productCount = productCount;
        this.billApplied = billApplied;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public boolean isBillApplied() {
        return billApplied;
    }

    public void setBillApplied(boolean billApplied) {
        this.billApplied = billApplied;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return billApplied == customer.billApplied && Objects.equals(customer_id, customer.customer_id) && Objects.equals(name, customer.name) && Objects.equals(mobileNumber, customer.mobileNumber) && Objects.equals(productName, customer.productName) && Objects.equals(productCount, customer.productCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer_id, name, mobileNumber, productName, productCount, billApplied);
    }
}
