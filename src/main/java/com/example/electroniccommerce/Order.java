package com.example.electroniccommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    public  static  boolean placeOrder(customer Customer , Product product){
    String groupOrderId = "SELECT max(groud_order_id) + 1 id FROM orders";
    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    try {
        ResultSet rs = dataBaseConnection.geqQueryTable(groupOrderId);
        if(rs.next()) {
            String placeOrder = "INSERT INTO `ecommerce`.`orders` (`groud_order_id`, `customer_id`, `product_id`) VALUES ("+rs.getInt("id")+", '"+Customer.getId()+"', '"+product.getId()+"')";
            return dataBaseConnection.updateDatabase(placeOrder) != 0;
        }
    }catch (Exception e){
        e.printStackTrace();
    }
  return  false;
    }
    public  static  int placeMultipleOrder(customer Customer , ObservableList<Product> productList){
        String groupOrderId = "SELECT max(groud_order_id) + 1 id FROM orders";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        try {
            ResultSet rs = dataBaseConnection.geqQueryTable(groupOrderId);
            int count = 0;
            if(rs.next()) {
                for(Product product : productList){
                    String placeOrder = "INSERT INTO `ecommerce`.`orders` (`groud_order_id`, `customer_id`, `product_id`) VALUES ("+rs.getInt("id")+", '"+Customer.getId()+"', '"+product.getId()+"')";
                    count +=dataBaseConnection.updateDatabase(placeOrder);
                }
                return count;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  0;
    }

}
