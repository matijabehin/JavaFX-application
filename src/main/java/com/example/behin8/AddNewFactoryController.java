package com.example.behin8;

import hr.java.production.database.Database;
import hr.java.production.model.Adress;
import hr.java.production.model.Factory;
import hr.java.production.model.Item;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddNewFactoryController {
    @FXML
    private TextField name;

    @FXML
    private ChoiceBox adress;

    public static List<Adress> listAdress = new ArrayList<>();
    public static List<String> listAdressString = new ArrayList<>();
    public static List<Factory> listFactories = new ArrayList<>();

    @FXML
    public void initialize(){
        listFactories.clear();
        listAdress.clear();

        try {
            listFactories = Database.getAllFactoriesFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            listAdress = Database.getAllAdressesFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Adress a : listAdress){
           listAdressString.add(a.getStreet() + " " + a.getHouseNumber() + ", " + a.getCity());
        }

        adress.setItems(FXCollections.observableList(listAdressString));
    }

    @FXML
    protected void clickedButtonToSaveNewFactory(){
        StringBuilder errorMessages = new StringBuilder();

        String nameOfFactory = name.getText();


        if(nameOfFactory.isEmpty()){
            errorMessages.append("Name of Factory should not be empty!\n");
        }
        String adressOfFactoryString = (String) adress.getValue();
        if(Optional.ofNullable(adressOfFactoryString).isPresent() == false){
            errorMessages.append("Adress of Factory should be selected!\n");
        }

        if(errorMessages.isEmpty()){
            List<Item> listItem = new ArrayList<>();
            try {
                listItem = Database.getAllItemsFromDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            List<Item> hardcodedListOfItems = new ArrayList<>();
            hardcodedListOfItems.add(listItem.get(0));
            hardcodedListOfItems.add(listItem.get(2));
            hardcodedListOfItems.add(listItem.get(4));

            Optional<Adress> adressOfFactory = Optional.empty();

            int indexOfAdress = 0;
            for(Adress a : listAdress){
                String adressString = a.getStreet() + " " + a.getHouseNumber() + ", " + a.getCity();

                if(adressOfFactoryString.compareTo(adressString) == 0){
                    adressOfFactory = Optional.of(listAdress.get(indexOfAdress));
                }
                indexOfAdress += 1;
            }

            Factory newFactory = new Factory(nameOfFactory,adressOfFactory.get(),hardcodedListOfItems,Long.valueOf(listFactories.size() + 1));

            try {
                Database.insertNewFactory(newFactory);
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
