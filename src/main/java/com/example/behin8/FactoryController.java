package com.example.behin8;

import hr.java.production.database.Database;
import hr.java.production.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FactoryController {
    @FXML
    private TextField nameofFactory;

    @FXML
    private TableView<Factory> tableofFactories;

    @FXML
    private TableColumn<Factory, String> nameFactoryTableColumn;

    @FXML
    private TableColumn<Factory, String> adressTableColumn;

   public static List<Factory> tvornice = new ArrayList<>();

    @FXML
    public void initialize(){
        tvornice.clear();

        try {
            tvornice = Database.getAllFactoriesFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        nameFactoryTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });
        adressTableColumn.setCellValueFactory(cellData -> {
            Adress adresa = cellData.getValue().getAdress();
            String adresaString = adresa.getStreet() + " " + adresa.getHouseNumber() + ", " + adresa.getCity();
            return new SimpleStringProperty(adresaString);
        });
        tableofFactories.setItems(FXCollections.observableList(tvornice));
    }

    @FXML
    protected void clickedSearchButton(){
        String enteredSearchWord = nameofFactory.getText();

        List<Factory> filteredList = new ArrayList<>();
        for(Factory f : tvornice){
            filteredList.add(f);
        }

        if(!enteredSearchWord.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(object -> object.getName().toLowerCase().contains(enteredSearchWord.toLowerCase()))
                    .collect(Collectors.toList());
        }

        tableofFactories.setItems(FXCollections.observableList(filteredList));
    }
}
