package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String orderTime;
    private double discount;
    private double totalAmount;
    private ArrayList<OrderDetails>orderDetails;

    public Order() {
    }

    public Order(String orderId, String customerId, String orderDate, String orderTime, double discount, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.discount = discount;
        this.totalAmount = totalAmount;
    }

    public Order(String orderId, String customerId, String orderDate, String orderTime, double discount, double totalAmount, ArrayList<OrderDetails>details) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.orderDetails=details;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate=" + orderDate +
                ", orderTime='" + orderTime + '\'' +
                ", discount=" + discount +
                ", totalAmount=" + totalAmount +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
