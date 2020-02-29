package Project;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends Application {

    TableView<Product> table=new TableView<Product>();
    private  final ObservableList<Product> data = getProduct();
    static  Scanner in = new Scanner(System.in);
    public static int validateInteger(){
        while (!in.hasNextInt()){
            System.out.println("Invalid month,re-enter");
            in.next();
        }
        return in.nextInt();
    }

    public static void main(String[] args) throws IOException {
        //to show list
        launch(args);
        // create new WMS
        WestminsterMusicStoreManager wms = new WestminsterMusicStoreManager();
        wms.getAllDatacount(); //calculate count
        wms.getAllDocuments(wms.collection);

        System.out.println("\t\tMusic Store Management System");
        char sentinal = 1;
        while (sentinal > 0) {
            System.out.println("Choose option from following");
            System.out.println("" +                                 //printing the options
                    "1. Add a new item\n" +
                    "2. Delete an item\n" +
                    "3. Print the list of items\n" +
                    "4. Sort the items by Title\n" +
                    "5. Sort the items by Genre\n" +
                    "6. Buy an item\n" +
                    "7. Generate a report\n" +
                    "8. Exit\n" +
                    "Option>>");
            Scanner sc = new Scanner(System.in);
            char option = sc.next().charAt(0);
            if (option == '1') {
                Date date = new Date();

                Scanner inn = new Scanner(System.in);

                //enter ID
                System.out.println("Enter item id");
                String itemId = inn.nextLine();

                //enter title
                System.out.println("Enter item title");
                String title = in.nextLine();

                //enter genre
                System.out.println("Enter item genre");
                String genre = in.nextLine();

                //enter year
                System.out.println("Enter item release year");
                // validateInteger();                                  //validating the input
                int year = in.nextInt();
                date.setYear(year);

                //enter month
                System.out.println("Enter item release month");
                //validateInteger();                                  //validating the input
                int month = in.nextInt();
                date.setMonth(month);

                //enter date
                System.out.println("Enter item release date");
                //validateInteger();                                  //validating the input
                int datee = in.nextInt();
                date.setDate(datee);
                String releaseDate = datee + "." + month + "." + year;

                //enter artist
                System.out.println("Enter item artist");
                Scanner in_ = new Scanner(System.in);
                String artist = in_.nextLine();

                //enter item price
                System.out.println("Enter item price");
                String price = in_.next();

                //enter type
                System.out.println("Enter item Type(Press 1 for CD,2 for Vinyl");
                char type = in_.next().charAt(0);

                if (type == '1') {
                    System.out.println("Enter Duration in Seconds");
                    int duration = in_.nextInt();
                    CD tempCD = new CD(itemId, title, genre, releaseDate, artist, Double.valueOf(price), duration);
                    wms.addCD(tempCD);

                } else if (type == '2') {
                    System.out.println("Enter Speed");
                    double speed = in_.nextDouble();
                    System.out.println("Enter Diameter");
                    double diameter = in_.nextDouble();

                    Vinyl tempVinyl = new Vinyl(itemId, title, genre, releaseDate, artist, Double.valueOf(price), speed, diameter);
                    wms.addVinyl(tempVinyl);
                    System.out.println(tempVinyl);
                }

            }
            if (option == '2') {
                wms.print();
                System.out.println();
                System.out.print("Delete Item Index:");
                int i = sc.nextInt();
                MusicItem deleteItem;
                for (int j = 0; j < wms.getStoreItem().size(); j++) {
                    if (Integer.parseInt(wms.getStoreItem().get(j).itemID) == i) {
                        deleteItem = wms.getStoreItem().get(j);
                        wms.setDeleteItemIndex(j);
                        wms.delete(deleteItem);
                        break;
                    }
                }
                wms.print();


            }
            if (option == '3') {
                wms.printSelected();
            }
            if (option == '4') {
                wms.sortByTitle(wms.getStoreItem(), new SortCategory());
            }
            if (option == '5') {
                wms.sortByGenre(wms.getStoreItem(), new SortCategory());
            }
            if (option == '6') {
                System.out.print("Buy Item Index:");
                int i = sc.nextInt();
                System.out.print("No of Copies:");
                int k = sc.nextInt();
                MusicItem boughtItem;
                for (int j = 0; j < wms.getStoreItem().size(); j++) {
                    if (Integer.parseInt(wms.getStoreItem().get(j).itemID) == i) {
                        boughtItem = wms.getStoreItem().get(j);
                        wms.buy(boughtItem, k);
                        break;
                    }
                }
            }
            if (option == '7') {
                wms.generateReport();
            }
            if (option == '8') {
                System.exit(0);
            }
            if (option == '9') {
               // launch(args);

            }

        }
    }

   @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(new Group());
        primaryStage.setTitle("MUSIC STORE MANAGEMENT");
        primaryStage.setWidth(740);
        primaryStage.setHeight(550);

        table.setEditable(true);

        //Name column
        TableColumn<Product, String> nameColumn = new TableColumn<>("Item ID");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        //Quantity column
       TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
       priceColumn.setMinWidth(100);
       priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

       TableColumn<Product, Double>  titleColumn = new TableColumn<>("Title");
       titleColumn.setMinWidth(500);
       titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

       FilteredList<Product> flProduct = new FilteredList(data, p -> true);//Pass the data to a filtered list
       table.setItems(flProduct);//Set the table's items using the filtered list
       table.getColumns().addAll(nameColumn,priceColumn,titleColumn);

       //Adding ChoiceBox and TextField here!
       ChoiceBox<String> choiceBox = new ChoiceBox();
       choiceBox.getItems().addAll("ID", "Title");
       choiceBox.setValue("ID");

       TextField textField = new TextField();
       textField.setPromptText("Search here!");
       textField.setOnKeyReleased(keyEvent ->
       {
           switch (choiceBox.getValue())//Switch on choiceBox value
           {
               case "Title":
                   System.out.println(textField.getText());
                   flProduct.setPredicate(p -> p.getTitle().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                   break;
               case "ID":
                   flProduct.setPredicate(p -> p.getID().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                   break;
           }
       });

       choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
       {
           if (newVal != null)
           {
               textField.setText("");
               flProduct.setPredicate(null);//This is same as saying flProduct.setPredicate(p->true);
           }
       });
       HBox hBox = new HBox(choiceBox, textField);//Add choiceBox and textField to hBox
       hBox.setAlignment(Pos.BOTTOM_LEFT);//Center HBox
       final VBox vbox = new VBox();
       vbox.setSpacing(5);
       vbox.setPadding(new Insets(10, 0, 0, 10));
       vbox.getChildren().addAll(table, hBox);

       ((Group) scene.getRoot()).getChildren().addAll(vbox);


       primaryStage.setScene(scene);
       primaryStage.show();
    }

    //Get all of the products
    public ObservableList<Product> getProduct(){
        ObservableList<Product> products = FXCollections.observableArrayList();
        WestminsterMusicStoreManager wms = new WestminsterMusicStoreManager();

        wms.getAllDocuments(wms.collection);
        ArrayList<MusicItem> array =wms.getStoreItem();
        for(MusicItem x:array){
            products.add(new Product(x.getItemID(),x.getPrice(),x.getTitle()));
        }
        return products;
    }

}

