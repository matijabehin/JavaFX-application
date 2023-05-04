package com.example.behin8;

import hr.java.production.database.Database;
import hr.java.production.model.Category;
import hr.java.production.model.Discount;
import hr.java.production.model.Item;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddNewItemController {
    @FXML
    private TextField name;

    @FXML
    private ChoiceBox category;

    @FXML
    private TextField width;

    @FXML
    private TextField height;

    @FXML
    private TextField length;

    @FXML
    private TextField productionCost;

    @FXML
    private TextField sellingPrice;

    public static List<Item> listItems = new ArrayList<>();

    @FXML
    public void initialize(){
        //listItems.clear();
        try {
            listItems = Database.getAllItemsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Category> listOfCategories = new ArrayList<>();

        try {
            listOfCategories = Database.getAllCategoriesFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> listOfCategoriesName = new ArrayList<>();

        for(Category c : listOfCategories){
            listOfCategoriesName.add(c.getName());
        }

        category.setItems(FXCollections.observableList(listOfCategoriesName));
    }

    @FXML
    protected void clickedButtonToSaveNewItem(){
        StringBuilder errorMessages = new StringBuilder();

        List<Category> listCategories = new ArrayList<>();
        Optional<Category> categoryOfItem = Optional.empty();

        try {
            listCategories = Database.getAllCategoriesFromDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        BigDecimal wItem = new BigDecimal(0);
        BigDecimal hItem = new BigDecimal(0);
        BigDecimal lItem = new BigDecimal(0);
        BigDecimal pcItem = new BigDecimal(0);
        BigDecimal spItem = new BigDecimal(0);

        String nameOfItem = name.getText();
        if(nameOfItem.isEmpty()){
            errorMessages.append("Name of Item should not be empty!\n");
        }
        String categoryOfItemString = (String) category.getValue();
        if(Optional.ofNullable(categoryOfItemString).isPresent() == false){
            errorMessages.append("Type of a category is not selected!\n");
        }else{
            for (Category c : listCategories){
                if(categoryOfItemString.compareTo(c.getName()) == 0){
                    categoryOfItem = Optional.of(c);
                }
            }
        }

        if(width.getText().isEmpty()){
            errorMessages.append("Width of Item should not be empty!\n");
        }else{
            wItem = new BigDecimal(width.getText());
        }

        if(height.getText().isEmpty()){
            errorMessages.append("Height of Item should not be empty!\n");
        }else{
            hItem = new BigDecimal(height.getText());
        }

        if(length.getText().isEmpty()){
            errorMessages.append("Length of Item should not be empty!\n");
        }else{
            lItem = new BigDecimal(length.getText());
        }

        if(productionCost.getText().isEmpty()){
            errorMessages.append("Production Cost of Item should not be empty!\n");
        }else{
            pcItem = new BigDecimal(productionCost.getText());
        }

        if(sellingPrice.getText().isEmpty()){
            errorMessages.append("Selling Price of Item should not be empty!\n");
        }else{
            spItem = new BigDecimal(sellingPrice.getText());
        }

        if(errorMessages.isEmpty()){
            Long idOfItem = Long.valueOf(listItems.size() + 1);
            Discount discountOfItem = new Discount(BigDecimal.valueOf(0));

            Item newItem = new Item(nameOfItem,categoryOfItem.get(),wItem,hItem,lItem,pcItem,spItem,discountOfItem,idOfItem);

            try {
                Database.insertNewItem(newItem);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Item data not saved!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }

    }
}
