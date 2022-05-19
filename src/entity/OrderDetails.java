package entity;

public class OrderDetails {
    private String orderId;
    private String itemCode;
    private String itemDescription;
    private int itemQty;
    private double itemPrice;
    private double itemDiscount;
    private double total;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String itemCode, String itemDescription, int itemQty, double itemPrice, double itemDiscount, double total) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.itemQty = itemQty;
        this.itemPrice = itemPrice;
        this.itemDiscount = itemDiscount;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(double itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemQty=" + itemQty +
                ", itemPrice=" + itemPrice +
                ", itemDiscount=" + itemDiscount +
                ", total=" + total +
                '}';
    }
}
