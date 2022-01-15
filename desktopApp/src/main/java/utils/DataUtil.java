package utils;

import phonebook.dao.PhonebookDao;
import phonebook.entity.Person;

import java.util.Arrays;
import java.util.Locale;

public class DataUtil {
    public static String[][] getDataFromDatabase() throws NoSuchFieldException, IllegalAccessException {
        var dao = new PhonebookDao();
        var personList = dao.findAll();

        //use Reflection API
        var mPerson = Person.class;
        var fields = mPerson.getDeclaredFields();

        var data = new String[personList.size()][fields.length];

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                var field = fields[j];
                field.setAccessible(true);
                data[i][j] = field.get(personList.get(i)).toString();
            }
        }
        return data;
    }

    public static String[] getColumnNames() {
        var mPerson = Person.class;
        var fields = mPerson.getDeclaredFields();
        var columnNames = new String[fields.length];
        for (int i = 0; i < columnNames.length; i++) {
            columnNames[i] = fields[i].getName().toUpperCase();
        }
        return columnNames;
    }

}
