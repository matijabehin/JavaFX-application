package com.example.behin8;

import hr.java.production.database.Database;
import hr.java.production.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddNewCategoryController {
    @FXML
    private TextField name;

    @FXML
    private TextField description;

    public static List<Category> listCategories = new ArrayList<>();

    @FXML
    public void initialize(){
        listCategories.clear();

        try {
            listCategories = Database.getAllCategoriesFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void clickedButtonToSaveNewCategory(){
        StringBuilder errorMessages = new StringBuilder();

        Long id = Long.valueOf(0);
        id = Long.valueOf(listCategories.size() + 1);
        String nameOfCategory = name.getText();


        if(nameOfCategory.isEmpty()){
            errorMessages.append("Name of category should not be empty!\n");
        }

        String descriptionOfCategory = description.getText();

        if(descriptionOfCategory.isEmpty()){
            errorMessages.append("Description of category should not be empty!\n");
        }


        if(errorMessages.isEmpty()){
            Category newCategory = new Category(nameOfCategory,descriptionOfCategory,id);

            try {
                Database.insertNewCategory(newCategory);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Category data not saved!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }
    }
}
