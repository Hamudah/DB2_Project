package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Order {
    private int orderId;
    private ObservableList<OrderedItem> orderedItemList;
    private int customerId;
    private double grossTotal;
    private double netTotal;
    private String orderDate;
    private int orderedItemsId;

    public Order(){
        this.orderedItemList = FXCollections.observableArrayList();
        this.grossTotal = 0;
    }

    public Order( int orderId, int customerId, String orderDate){
        this.orderedItemList = FXCollections.observableArrayList();
        this.grossTotal = 0;
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public Order(int orderId, double netTotal, double grossTotal, int customerId , String orderDate ){
        this.orderedItemList = FXCollections.observableArrayList();
        this.orderId = orderId;
        this.grossTotal = grossTotal;
        this.netTotal = netTotal;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public void addProduct(Product product){
        boolean itemExists = false;
        for (OrderedItem ordItem : orderedItemList){
            if(ordItem.equals(product)){
                ordItem.incOrder();
                itemExists = true;
            }
        }
        if(!itemExists){
            orderedItemList.add(new OrderedItem(product));
        }
        grossTotal = grossTotal + product.getPrice();
        netTotal = this.getNetPrice();
    }

    public void removeOrderedItem(OrderedItem orderedItem){
        if(orderedItemList.contains(orderedItem)){
            if(orderedItem.getOrderAmount() > 1){
                orderedItem.decOrder();
            } else {
                orderedItemList.remove(orderedItem);
            }
            grossTotal = grossTotal - orderedItem.getPrice();
            netTotal = this.getNetPrice();
        }
    }

    public ObservableList<TotalEntry> getTotalCost(){
        ObservableList<TotalEntry> totalCostList = FXCollections.observableArrayList();
        totalCostList.add(new TotalEntry(netTotal, "grossTotal (16% tax):"));
        String vat = "netTotal: ";
        totalCostList.add(new TotalEntry(grossTotal, vat));
        return totalCostList;
    }

    public String toSql(){
        return "("+ orderId+ "," + orderedItemsId + "," + customerId + "," + grossTotal + ", " + netTotal + ", "+ orderDate +  ")";
    }

    public double getGrossTotal() {
        return grossTotal;
    }

    public ObservableList<OrderedItem> getOrderedItemList() {
        return orderedItemList;
    }

    public double getNetPrice(){
        return netTotal = Math.round(grossTotal * 116) / 100.0;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }
}
