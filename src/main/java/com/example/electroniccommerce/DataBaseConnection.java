package com.example.electroniccommerce;
import java.sql.*;
public class DataBaseConnection {

    private final  String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";//javadatabaseconnector sqlsoftware portnumber database
    private  final  String userName = "root";
    private final  String password = "Raju@123";

    private  Statement getStatement(){
        try {
            Connection connection = DriverManager.getConnection(dbUrl,userName,password);//driver manager manages the jdbc driver
            return  connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet geqQueryTable(String query){
       try {
         Statement statement = getStatement();
         return statement.executeQuery(query);
       }catch (Exception e){
           e.printStackTrace();
       }
        return null;
    }
    public  int updateDatabase(String query){
        try {
            Statement statement = getStatement();
            return statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        DataBaseConnection conn = new DataBaseConnection();
        ResultSet rs = conn.geqQueryTable("SELECT * FROM customer");

        if(rs != null){
            System.out.println("Connection sucessfull");
        }else{
            System.out.println("Connection not SuccesFull");
        }
    }
}

