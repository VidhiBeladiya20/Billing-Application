package com.example.Bill_System.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bill_id;

    @ManyToOne
    @JoinColumn(name = "c_id" ,referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "p_id" , referencedColumnName = "product_id")
    private Product product;

    private Integer quantity;
    private Double totalAmount;
    private Double gst;
    private Double finalAmount;
    private boolean paymentConfirmed;
    private LocalDateTime createdAt;

    public Bill() {
    }

    public Bill(Customer customer, Product product, Integer quantity, Double totalAmount, Double gst, Double finalAmount, boolean paymentConfirmed, LocalDateTime createdAt) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.gst = gst;
        this.finalAmount = finalAmount;
        this.paymentConfirmed = paymentConfirmed;
        this.createdAt = createdAt;
    }

    public Long getBill_id() {
        return bill_id;
    }

    public void setBill_id(Long bill_id) {
        this.bill_id = bill_id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getGst() {
        return gst;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public Double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public boolean isPaymentConfirmed() {
        return paymentConfirmed;
    }

    public void setPaymentConfirmed(boolean paymentConfirmed) {
        this.paymentConfirmed = paymentConfirmed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return paymentConfirmed == bill.paymentConfirmed && Objects.equals(bill_id, bill.bill_id) && Objects.equals(customer, bill.customer) && Objects.equals(product, bill.product) && Objects.equals(quantity, bill.quantity) && Objects.equals(totalAmount, bill.totalAmount) && Objects.equals(gst, bill.gst) && Objects.equals(finalAmount, bill.finalAmount) && Objects.equals(createdAt, bill.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill_id, customer, product, quantity, totalAmount, gst, finalAmount, paymentConfirmed, createdAt);
    }
}
