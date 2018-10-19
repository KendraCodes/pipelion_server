package database;

import model.JsonUtils;

import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class DatabaseManager {

    public List<String> getDepartmentsList() {
        return JsonUtils.loadDepartments();
    }

}
