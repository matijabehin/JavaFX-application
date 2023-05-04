package com.example.behin8;

import hr.java.production.database.Database;
import hr.java.production.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {
    @FXML
    private TextField nameOfItem;

    @FXML
    private ChoiceBox chosenCategory;

    @FXML
    private TableView<Item> tableOfItems;

    @FXML
    private TableColumn<Item, String> nameTableColumn;

    @FXML
    private TableColumn<Item, String> categoryTableColumn;


    @FXML
    private TableColumn<Item, String> wTableColumn;

    @FXML
    private TableColumn<Item, String> hTableColumn;

    @FXML
    private TableColumn<Item, String> lTableColumn;

    @FXML
    private TableColumn<Item, String> prodCostTableColumn;

    @FXML
    private TableColumn<Item, String> sellingPriceTableColumn;


    List<Category> kategorije = new ArrayList<>();
    List<Item> stvari = new ArrayList<>();

    @FXML
    public void initialize(){
        stvari.clear();
        kategorije.clear();

        try {
            stvari =  Database.getAllItemsFromDatabase();
            kategorije = Database.getAllCategoriesFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> categoryName = new ArrayList<>();

        for(Category category : kategorije){
            categoryName.add(category.getName());
        }

        chosenCategory.setItems(FXCollections.observableList(categoryName));

        nameTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });

        categoryTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getCategory().getName());
        });

        wTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getWidth().toString());
        });

        hTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getHeight().toString());
        });

        lTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getLength().toString());
        });

        prodCostTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getProductionCost().toString());
        });

        sellingPriceTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getSellingPrice().toString());
        });

        ObservableList<Item> listOfItems = FXCollections.observableList(stvari);

        tableOfItems.setItems(listOfItems);

    }

    @FXML
    protected void clickedSearchButton(){
        String enteredSearchWord = nameOfItem.getText();
        String enteredChosenCategory =  (String) chosenCategory.getValue();

        List<Item> filteredList = new ArrayList<>(stvari);

        if(!enteredSearchWord.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(object -> object.getName().toLowerCase().contains(enteredSearchWord.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if(enteredChosenCategory != null){
            filteredList = filteredList.stream()
                    .filter(object -> object.getCategory().getName().toLowerCase().contains(enteredChosenCategory.toLowerCase()))
                    .collect(Collectors.toList());
        }
        tableOfItems.setItems(FXCollections.observableList(filteredList));
    }


}