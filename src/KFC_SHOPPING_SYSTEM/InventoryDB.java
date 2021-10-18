package KFC_SHOPPING_SYSTEM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author singh
 */
public class InventoryDB {
    private final DBManager db;
    private static final String URL = "jdbc:derby:KFC_Inventory;create=true";
    private static final String KFC_MENU = "./resources/KFC_MENU.txt";
    private static ArrayList<String> list = new ArrayList<>();
    private static final String tableName = "INVENTORY";

    public InventoryDB(){
        this.db = new DBManager(URL);
    }

    public void createItemsTable(){
        try {
            
            if (!db.checkTable(tableName)){
                db.getStatement().executeUpdate("CREATE TABLE " + tableName + "(ID INT, "
                        + "CATEGORY VARCHAR(50),ITEM_NAME VARCHAR(50), ITEM_PRICE DOUBLE)");
                fillTable();
            }
            db.getStatement().close();
        } catch (SQLException e) {
            Logger.getLogger(InventoryDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void fillTable(){
        int count = 1;
        try{
            
            list = File_IO.readFile(KFC_MENU);
            for(String o: list){
                String[] itemInfo = o.split(",");
                Double price = Double.parseDouble(itemInfo[2]);
                String sqlInsert = "INSERT INTO " + tableName + " VALUES ("+ count++ + ",'"+ itemInfo[0]+ "','" + itemInfo[1]+ "', "+price+")";
               db.getStatement().executeUpdate(sqlInsert);
            }
        }catch(SQLException e){
            Logger.getLogger(InventoryDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public Vector getCategory(){
        ResultSet rs = null;
        Vector<String> category = new Vector<String>();
        try{
            String sqlQuery = "SELECT CATEGORY FROM " + tableName;
            rs = db.getStatement().executeQuery(sqlQuery);
            while(rs.next()){
                category.add(rs.getString("CATEGORY"));
            }
        }catch(SQLException e){
             Logger.getLogger(InventoryDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return category;
    }
    public Vector getItems(String categoryName){
        ResultSet rs = null;
        Vector<Products> items = new Vector<Products>();
        try{
            String sqlQuery = "Select ITEM_NAME, ITEM_PRICE from " + tableName + " where CATEGORY='" + categoryName + "'";
            rs = db.getStatement().executeQuery(sqlQuery);
            while(rs.next()){
                String iname = rs.getString("ITEM_NAME");
                double price = rs.getDouble("ITEM_PRICE");
                items.add(new Products(categoryName,iname,price));
            }
        }catch(SQLException e){
            Logger.getLogger(InventoryDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return items;
    }
   
}