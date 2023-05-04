package com.example.behin8;

import hr.java.production.database.Database;
import hr.java.production.model.Item;
import hr.java.production.model.Store;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddNewStoreController {
    @FXML
    private TextField name;

    @FXML
    private TextField web_adress;

    public static List<Store> listStores = new ArrayList<>();

    @FXML
    public void initialize(){
        listStores.clear();

        try {
            listStores = Database.getAllStoresFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void clickedButtonToSaveNewStore(){
        StringBuilder errorMessages = new StringBuilder();

        String nameOfStore = name.getText();
        if(nameOfStore.isEmpty()){
            errorMessages.append("Name of Store should not be empty!\n");
        }

        String webAdressOfStore = web_adress.getText();
        if(webAdressOfStore.isEmpty()){
            errorMessages.append("Web Adress of Store should not be empty!\n");
        }

        if(errorMessages.isEmpty()){
            List<Item> listItem = new ArrayList<>();
            try {
                listItem = Database.getAllItemsFromDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Item> hardcodedListOfItems = new ArrayList<>();
            hardcodedListOfItems.add(listItem.get(0));
            hardcodedListOfItems.add(listItem.get(2));
            hardcodedListOfItems.add(listItem.get(4));

            Store newStore = new Store(nameOfStore,webAdressOfStore,hardcodedListOfItems,Long.valueOf(listStores.size()+1));

            try {
                Database.insertNewStore(newStore);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Factory data not saved!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }
    }
}
