package dto;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDTO {
    private String orderId;
    private String customerId;
    private LocalDate orderDate;
    private String orderTime;
    private double discount;
    private double totalAmount;
    private ArrayList<OrderDetailsDTO> orderDetailsDTO;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String customerId, LocalDate orderDate, String orderTime, double discount, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.discount = discount;
        this.totalAmount = totalAmount;
    }

    public OrderDTO(String orderId, String customerId, LocalDate orderDate, String orderTime, double discount, double totalAmount, ArrayList<OrderDetailsDTO> detailsDTOS) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.orderDetailsDTO = detailsDTOS;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
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

    public ArrayList<OrderDetailsDTO> getOrderDetailsDTO() {
        return orderDetailsDTO;
    }

    public void setOrderDetailsDTO(ArrayList<OrderDetailsDTO> orderDetailsDTO) {
        this.orderDetailsDTO = orderDetailsDTO;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate=" + orderDate +
                ", orderTime='" + orderTime + '\'' +
                ", discount=" + discount +
                ", totalAmount=" + totalAmount +
                ", orderDetailsDTO=" + orderDetailsDTO +
                '}';
    }
}
