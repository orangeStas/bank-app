package by.bsuir.bankapp.dao.connectionpool;

import java.util.ResourceBundle;

public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();

    private DBResourceManager(){}

    private ResourceBundle bundle = ResourceBundle.getBundle("resources/db");

    public static DBResourceManager getInstance(){
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
