package com.example.electroniccommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar;
    HBox FooterBar;
    VBox body;
    Button signInButton;
    customer loggedInCustomer;
    Label welcomeLabel;
    ProductList productList = new ProductList();
    VBox productPage;
    Button placeOrderButton = new Button("Place Order");
ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
    BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(800,600);

//        root.getChildren().add(loginPage);
//        root.setCenter(loginPage);

        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setTop(headerBar);
        productPage = productList.getAllProducts();
        root.setCenter(body);
        body.getChildren().add(productPage);

        root.setBottom(FooterBar);
        return root;
    }

    public  UserInterface(){
        createLoginPage();
        HeaderBar();
        CreateFooterBar();
    }
    private  void createLoginPage(){
        
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

            TextField userName = new TextField("abc124@gmail.com");
            userName.setPromptText("Type your userName here !");
        PasswordField password = new PasswordField();
        password.setText("Raju");
        password.setPromptText("Type your password here !");
        Label messageLabel = new Label("Hi");
        Button loginButton = new Button("Login");

         loginPage = new GridPane();
//         loginPage.setStyle(" -fx-background-color:red;");
         loginPage.setAlignment(Pos.CENTER);
         loginPage.setHgap(10);
         loginPage.setVgap(10);
         loginPage.add(userNameText , 0,0);
         loginPage.add(userName , 1,0);
         loginPage.add(passwordText , 0 , 1);
         loginPage.add(password , 1, 1);
         loginPage .add(messageLabel,0,2);
         loginPage.add(loginButton , 1 ,2);

         loginButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 String name = userName.getText();
//                 messageLabel.setText(name);
                 String  pass = password.getText();
                 Login login = new Login();
                 loggedInCustomer  = login.customerLogin(name , pass);
                 if(loggedInCustomer != null){
                     messageLabel.setText("Welcome "+loggedInCustomer.getName());
                     welcomeLabel.setText("Welcome "+loggedInCustomer.getName());
                     headerBar.getChildren().add(welcomeLabel);
                     body.getChildren().clear();
                     body.getChildren().add(productPage);
                 }
                 else{
                     messageLabel.setText("Please Enter the Valid credentials!");
                 }
             }
         });
    }
    private  void  HeaderBar(){
      Button homeButton = new Button();
        Image image = new Image("C:\\Users\\gopir\\IdeaProjects\\ElectronicCommerce\\src\\img_4.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        imageView.setFitWidth(30);
        imageView.setFitHeight(20);

        homeButton.setGraphic(imageView);

        TextField searchBar = new TextField();
        searchBar.setPromptText("search here");
        searchBar.setPrefWidth(280);

        Button serachButton = new Button("Search ");
        Button cartButton = new Button("cart");

        signInButton = new Button("sign In");
        Button ordersButton = new Button("Orders");


        welcomeLabel = new Label();
        headerBar = new HBox();
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);

//        headerBar.setStyle(" -fx-background-color:grey;");
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton,searchBar , serachButton,signInButton,cartButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(loginPage);
                headerBar.getChildren().remove(signInButton);
            }
        });
        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
              body.getChildren().clear();
              VBox prodPage = productList.getProductsInCart(itemsInCart);
              prodPage.setAlignment(Pos.CENTER);
              prodPage.setSpacing(10);
              prodPage.getChildren().add(placeOrderButton);
              body.getChildren().add(prodPage);
              FooterBar.setVisible(false);
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (itemsInCart == null) {

                    showDialog("Please add some products to the cart!");
                    return;
                }
                if(loggedInCustomer == null){
                    showDialog("please login first to place the order !");
                    return;
                }
                int count = Order.placeMultipleOrder(loggedInCustomer , itemsInCart);
                if(count != 0){
                    showDialog("Order for "+count+" products placed successfully !");
                }
                else{
                    showDialog("Order failed!");
                }
            }
        });
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                FooterBar.setVisible(true);
                if (loggedInCustomer == null) {
                    System.out.println(headerBar.getChildren().indexOf(signInButton));
                    if (headerBar.getChildren().indexOf(signInButton) == -1) {
                        headerBar.getChildren().add(signInButton);
                    }
                }
            }
        });
    }
    private  void  CreateFooterBar(){

            Button byNowButton = new Button("Buy Now ");
        Button addToCartButton = new Button("Add to cart");
        FooterBar = new HBox();
        FooterBar.setPadding(new Insets(10));
        FooterBar.setSpacing(10);
//        headerBar.setStyle(" -fx-background-color:grey;");
        FooterBar.setAlignment(Pos.CENTER);
        FooterBar.getChildren().addAll(byNowButton,addToCartButton);

         byNowButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 Product product = productList.getSelectedProduct();
                 if (product == null) {

                         showDialog("Please select a product first to place order !");
                         return;
                 }
                 if(loggedInCustomer == null){
                     showDialog("please login first to place the order !");
                     return;
                 }
                 boolean status = Order.placeOrder(loggedInCustomer , product);
                 if(status == true){
                     showDialog("Order placed successfully !");
                 }
                 else{
                     showDialog("Order failed!");
                 }
             }
         });
    addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Product product = productList.getSelectedProduct();
            if (product == null) {

                showDialog("Please select a product first to add to cart!");
                return;
            }
            itemsInCart.add(product);
            showDialog("selected Items has successfully add to the cart!");
        }
    });
    }
 private  void  showDialog(String  message){
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
     alert.setHeaderText(null);
     alert.setContentText(message);
     alert.setTitle("Message");
     alert.showAndWait();
 }

}
