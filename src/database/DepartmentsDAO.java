package database;

import model.JsonUtils;

import java.util.List;

/**
 * Created by Kendra on 10/23/2018.
 */
public class DepartmentsDAO {

    public List<String> getDepartmentsList() {
        return JsonUtils.loadDepartments();
    }

}
