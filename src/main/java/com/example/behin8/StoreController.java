package com.example.behin8;

import hr.java.production.database.Database;
import hr.java.production.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StoreController {
    @FXML
    private TextField nameOfStore;

    @FXML
    private TableView<Store> tableOfStore;

    @FXML
    private TableColumn<Store, String> nameofStoreTableColumn;

    @FXML
    private TableColumn<Store, String> webAdrofStoreTableColumn;

    public static List<Store> trgovine = new ArrayList<>();

    @FXML
    public void initialize() {
        trgovine.clear();

        try {
            trgovine = Database.getAllStoresFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        nameofStoreTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });
        webAdrofStoreTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getWebAddress());
        });


        tableOfStore.setItems(FXCollections.observableList(trgovine));
    }

    @FXML
    protected void clickedSearchButton(){
        String enteredSearchWord = nameOfStore.getText();


        List<Store> filteredList = new ArrayList<>();
        for(Store s : trgovine){
            filteredList.add(s);
        }

        if(!enteredSearchWord.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(object -> object.getName().toLowerCase().contains(enteredSearchWord.toLowerCase()))
                    .collect(Collectors.toList());
        }
        tableOfStore.setItems(FXCollections.observableList(filteredList));
    }
}