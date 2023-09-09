package com.example.electroniccommerce;

import java.sql.ResultSet;

public class Login {
    public  customer customerLogin(String userName , String password){
        String query = "SELECT * FROM customer WHERE Email = '"+userName+"' AND password ='"+password+"'";
        DataBaseConnection connection = new DataBaseConnection();
        try {
            ResultSet rs = connection.geqQueryTable(query);
            if(rs.next()) {
                return new customer(rs.getInt("id"),rs.getString("name"),rs.getString("Email"),rs.getString("mobile"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Login login = new Login();
        customer cuStomer = login.customerLogin("abc124@gmail.com","Raju");
        System.out.println("Welcome : "+cuStomer.getName());
//        System.out.println(login.customerLogin("abc1245@gmail.com","Raju") );
    }
}
