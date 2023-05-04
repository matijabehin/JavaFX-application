package hr.java.production.database;


import hr.java.production.model.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class Database {
    public static Connection connectToDatabase() throws SQLException, IOException{
        Properties configuration = new Properties();
        configuration.load(new FileReader("dat/database.properties"));

        String url = configuration.getProperty("databaseURL");
        String username = configuration.getProperty("databaseUsername");
        String password = configuration.getProperty("databasePassword");

        Connection connection = DriverManager.getConnection(url, username,password);

        return connection;
    }

    public static void closeConnection(Connection connection) throws SQLException, IOException{
        connection.close();
    }

    public static List<Category> getAllCategoriesFromDatabase() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Category> categoryList = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet categoryResultSet = sqlStatement.executeQuery("SELECT * FROM CATEGORY");

        while(categoryResultSet.next()) {
            categoryList.add(getCategoryFromResultSet(categoryResultSet));
        }

        connection.close();

        return categoryList;
    }

    public static List<Item> getAllItemsFromDatabase() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Item> itemList = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet itemResultSet = sqlStatement.executeQuery("SELECT * FROM ITEM");

        while(itemResultSet.next()) {
            itemList.add(getItemFromResultSet(itemResultSet));
        }

        connection.close();

        return itemList;
    }

    public static List<Adress> getAllAdressesFromDatabase() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Adress> adressList = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet adressResultSet = sqlStatement.executeQuery("SELECT * FROM ADDRESS");

        while(adressResultSet.next()) {
            adressList.add(getAdressFromResultSet(adressResultSet));
        }

        connection.close();

        return adressList;
    }

    public static List<Factory> getAllFactoriesFromDatabase() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Factory> factoryList = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet factoryResultSet = sqlStatement.executeQuery("SELECT * FROM FACTORY");

        while(factoryResultSet.next()) {
            factoryList.add(getFactoryFromResultSet(factoryResultSet));
        }

        connection.close();

        return factoryList;
    }

    public static List<Store> getAllStoresFromDatabase() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Store> storeList = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet storeResultSet = sqlStatement.executeQuery("SELECT * FROM STORE");

        while(storeResultSet.next()) {
            storeList.add(getStoreFromResultSet(storeResultSet));
        }

        connection.close();

        return storeList;
    }

    public static List<Item> getFactoryItemsFromDatabase(Long id) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        List<Item> items = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        StringBuilder sql = new StringBuilder("SELECT * FROM FACTORY_ITEM FI, ITEM I WHERE FI.FACTORY_ID = " + id + " AND FI.ITEM_ID = I.ID");

        PreparedStatement statement = connection.prepareStatement(sql.toString());

        ResultSet itemsResultSet = statement.executeQuery();

        while(itemsResultSet.next()){
            items.add(getItemFromResultSet(itemsResultSet));
        }

        connection.close();

        return items;
    }

    public static List<Item> getStoreItemsFromDatabase(Long id) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        List<Item> items = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        StringBuilder sql = new StringBuilder("SELECT * FROM STORE_ITEM SI, ITEM I WHERE SI.STORE_ID = " + id + " AND SI.ITEM_ID = I.ID");

        PreparedStatement statement = connection.prepareStatement(sql.toString());

        ResultSet itemsResultSet = statement.executeQuery();

        while(itemsResultSet.next()){
            items.add(getItemFromResultSet(itemsResultSet));
        }

        connection.close();

        return items;
    }

    public static Item getItemByIDFromDatabase(Long id) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement getItem =
                connection.prepareStatement(
                        "SELECT * FROM ITEM WHERE ID = ?");

        getItem.setString(1, Long.toString(id));

        getItem.executeQuery();

        ResultSet itemResultSet = getItem.executeQuery();

        Optional<Item> newItem = Optional.empty();
        while (itemResultSet.next()) {
            newItem = Optional.of(getItemFromResultSet(itemResultSet));
        }

        connection.close();

        return newItem.get();
    }

    public static Category getCategoryByIDFromDatabase(Long id) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement getItem =
                connection.prepareStatement(
                        "SELECT * FROM CATEGORY WHERE ID = ?");

        getItem.setString(1, Long.toString(id));

        getItem.executeQuery();

        ResultSet categoryResultSet = getItem.executeQuery();

        Optional<Category> newCategory = Optional.empty();
        while (categoryResultSet.next()) {
            newCategory = Optional.of(getCategoryFromResultSet(categoryResultSet));
        }

        connection.close();

        return newCategory.get();
    }

    public static Adress getAdressByIDFromDatabase(Long id) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement getItem =
                connection.prepareStatement(
                        "SELECT * FROM ADDRESS WHERE ID = ?");

        getItem.setString(1, Long.toString(id));

        getItem.executeQuery();

        ResultSet adressResultSet = getItem.executeQuery();

        Optional<Adress> newAdress = Optional.empty();
        while (adressResultSet.next()) {
            newAdress = Optional.of(getAdressFromResultSet(adressResultSet));
        }

        connection.close();

        return newAdress.get();
    }

    public static Factory getFactoryByIDFromDatabase(Long id) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement getItem =
                connection.prepareStatement(
                        "SELECT * FROM FACTORY WHERE ID = ?");

        getItem.setString(1, Long.toString(id));

        getItem.executeQuery();

        ResultSet factoryResultSet = getItem.executeQuery();

        Optional<Factory> newFactory = Optional.empty();
        while (factoryResultSet.next()) {
            newFactory = Optional.of(getFactoryFromResultSet(factoryResultSet));
        }

        connection.close();

        return newFactory.get();
    }

    public static Store getStoreByIDFromDatabase(Long id) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement getItem =
                connection.prepareStatement(
                        "SELECT * FROM STORE WHERE ID = ?");

        getItem.setString(1, Long.toString(id));

        getItem.executeQuery();

        ResultSet storeResultSet = getItem.executeQuery();

        Optional<Store> newStore = Optional.empty();
        while (storeResultSet.next()) {
            newStore = Optional.of(getStoreFromResultSet(storeResultSet));
        }

        connection.close();

        return newStore.get();
    }

    public static void insertNewCategory(Category category) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO CATEGORY(NAME, DESCRIPTION) VALUES(?, ?);");

        stmt.setString(1, category.getName());
        stmt.setString(2, category.getDescription());

        stmt.executeUpdate();

        connection.close();
    }

    public static void insertNewAdress(Adress adress) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO ADDRESS(STREET, HOUSE_NUMBER, CITY, POSTAL_CODE) VALUES(?, ?, ?, ?)");

        stmt.setString(1, adress.getStreet());
        stmt.setString(2, adress.getHouseNumber());
        stmt.setString(3, adress.getCity());
        stmt.setString(4, adress.getPostalCode().toString());

        stmt.executeUpdate();

        connection.close();
    }

    public static void insertNewItem(Item item) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO ITEM(CATEGORY_ID, NAME, WIDTH, HEIGHT, LENGTH, PRODUCTION_COST, SELLING_PRICE) VALUES(?, ?, ?, ?, ?, ?, ?)");

        stmt.setString(1, Long.toString(item.getCategory().getId()));
        stmt.setString(2, item.getName());
        stmt.setString(3, item.getWidth().toString());
        stmt.setString(4, item.getHeight().toString());
        stmt.setString(5, item.getLength().toString());
        stmt.setString(6, item.getProductionCost().toString());
        stmt.setString(7, item.getSellingPrice().toString());

        stmt.executeUpdate();

        connection.close();
    }

    public static void insertNewFactory(Factory factory) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO FACTORY(NAME, ADDRESS_ID) VALUES(?, ?);");

        stmt.setString(1, factory.getName());
        stmt.setString(2, Long.toString(factory.getAdress().getId()));

        stmt.executeUpdate();

        connection.close();
    }

    public static void insertNewStore(Store store) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO STORE(NAME, WEB_ADDRESS) VALUES(?, ?);");

        stmt.setString(1, store.getName());
        stmt.setString(2, store.getWebAddress());

        stmt.executeUpdate();

        connection.close();
    }

    public static void insertItemIntoFactory(Item item, Factory factory) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO FACTORY_ITEM(FACTORY_ID, ITEM_ID) VALUES(?, ?);");

        stmt.setString(1, Long.toString(factory.getId()));
        stmt.setString(2, Long.toString(item.getId()));

        stmt.executeUpdate();

        connection.close();
    }

    public static void insertItemIntoStore(Item item, Store store) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO STORE_ITEM(STORE_ID, ITEM_ID) VALUES(?, ?);");

        stmt.setString(1, Long.toString(store.getId()));
        stmt.setString(2, Long.toString(item.getId()));

        stmt.executeUpdate();

        connection.close();
    }

    private static Item getItemFromResultSet(ResultSet itemResultSet) throws SQLException, IOException {
        Long newID = itemResultSet.getLong("ID");
        Long newIDCategory = itemResultSet.getLong("CATEGORY_ID");
        String newName = itemResultSet.getString("NAME");
        BigDecimal newWidth = itemResultSet.getBigDecimal("WIDTH");
        BigDecimal newHeight = itemResultSet.getBigDecimal("HEIGHT");
        BigDecimal newLength = itemResultSet.getBigDecimal("LENGTH");
        BigDecimal newProdCost = itemResultSet.getBigDecimal("PRODUCTION_COST");
        BigDecimal newSellingPrice = itemResultSet.getBigDecimal("SELLING_PRICE");

        List<Category> categories = getAllCategoriesFromDatabase();
        Optional<Category> newCategory = Optional.empty();
        for(Category category : categories){
            if(category.getId().equals(newIDCategory)){
                newCategory = Optional.of(category);
            }
        }

        return new Item(newName,newCategory.get(),newWidth,newHeight,newLength,
                newProdCost,newSellingPrice,new Discount(new BigDecimal("0")),newID);
    }

    private static Category getCategoryFromResultSet(ResultSet categoryResultSet) throws SQLException, IOException {
        Long newID = categoryResultSet.getLong("ID");
        String newName = categoryResultSet.getString("NAME");
        String newDescription = categoryResultSet.getString("DESCRIPTION");

        return new Category(newName,newDescription,newID);
    }

    private static Adress getAdressFromResultSet(ResultSet adressResultSet) throws SQLException, IOException {
        Long newID = adressResultSet.getLong("ID");
        String newStreet = adressResultSet.getString("STREET");
        String newHouseNumber = adressResultSet.getString("HOUSE_NUMBER");
        String newCity = adressResultSet.getString("CITY");
        Integer newPostalCode = adressResultSet.getInt("POSTAL_CODE");

        return new Adress(newID,newStreet,newHouseNumber,newCity,newPostalCode);
    }

    private static Factory getFactoryFromResultSet(ResultSet factoryResultSet) throws SQLException, IOException {
        Long newID = factoryResultSet.getLong("ID");
        String newName = factoryResultSet.getString("NAME");
        Long newAdressID = factoryResultSet.getBigDecimal("ADDRESS_ID").longValue();

        List<Adress> adresses = getAllAdressesFromDatabase();
        Optional<Adress> newAdress = Optional.empty();
        for(Adress adress : adresses){
            if(adress.getId().equals(newAdressID)){
                newAdress = Optional.of(adress);
            }
        }

        return new Factory(newName,newAdress.get(),getFactoryItemsFromDatabase(newID),newID);
    }

    private static Store getStoreFromResultSet(ResultSet storeResultSet) throws SQLException, IOException {
        Long newID = storeResultSet.getLong("ID");
        String newName = storeResultSet.getString("NAME");
        String newWebAdress = storeResultSet.getString("WEB_ADDRESS");

        return new Store(newName,newWebAdress,getStoreItemsFromDatabase(newID),newID);
    }
}
