package entity;

public class Item {
    private String itemCode;
    private String itemDescription;
    private int itemQty;
    private double itemBuyingPrice;
    private double itemUnitPrice;
    private double itemDiscount;

    public Item() {
    }

    public Item(String itemCode, String itemDescription, int itemQty, double itemBuyingPrice, double itemUnitPrice, double itemDiscount) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.itemQty = itemQty;
        this.itemBuyingPrice = itemBuyingPrice;
        this.itemUnitPrice = itemUnitPrice;
        this.itemDiscount = itemDiscount;
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

    public double getItemBuyingPrice() {
        return itemBuyingPrice;
    }

    public void setItemBuyingPrice(double itemBuyingPrice) {
        this.itemBuyingPrice = itemBuyingPrice;
    }

    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public double getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(double itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemQty=" + itemQty +
                ", itemBuyingPrice=" + itemBuyingPrice +
                ", itemUnitPrice=" + itemUnitPrice +
                ", itemDiscount=" + itemDiscount +
                '}';
    }
}
