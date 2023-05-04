package com.example.behin8;

import hr.java.production.database.Database;
import hr.java.production.model.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryController {
    @FXML
    private TextField nameOfCategory;

    @FXML
    private TableView<Category> tableOfCategory;

    @FXML
    private TableColumn<Category, String> idTableColumn;

    @FXML
    private TableColumn<Category, String> nameTableColumn;

    @FXML
    private TableColumn<Category, String> descTableColumn;

    public static List<Category> kategorije = new ArrayList<>();

    @FXML
    public void initialize(){
        kategorije.clear();

        try {
            kategorije = Database.getAllCategoriesFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        nameTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });
        descTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDescription());
        });
        idTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getId().toString());
        });
        tableOfCategory.setItems(FXCollections.observableList(kategorije));
    }

    @FXML
    protected void clickedSearchButton(){
        String enteredSearchWord = nameOfCategory.getText();


        List<Category> filteredList = new ArrayList<>();
        for(Category c : kategorije){
            filteredList.add(c);
        }

        if(!enteredSearchWord.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(object -> object.getName().toLowerCase().contains(enteredSearchWord.toLowerCase()))
                    .collect(Collectors.toList());
        }
        tableOfCategory.setItems(FXCollections.observableList(filteredList));
    }
}
