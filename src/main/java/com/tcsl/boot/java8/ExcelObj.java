package com.tcsl.boot.java8;

public class ExcelObj {
    private String shopId;


    private String shopName;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    private String itemId;

    private String qty;

    private String itemName;

    @Override
    public String toString() {
        return "com.tcsl.boot.java8.ExcelObj{" +
                "shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", itemId='" + itemId + '\'' +
                ", qty='" + qty + '\'' +
                ", itemName='" + itemName + '\'' +
                '}';
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
