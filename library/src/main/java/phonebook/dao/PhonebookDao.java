package phonebook.dao;

import phonebook.entity.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class PhonebookDao {
    private final Connection connection;

    public PhonebookDao() {
        var property = new Properties();
        this.connection = this.getConnection(property);
    }

    private Connection getConnection(Properties properties) {
        Connection connection = null;
        var path = Paths.get("library/src/main/resources/property/database.properties");
        System.out.println(path.toAbsolutePath().toString());
        try (var in = Files.newInputStream(Paths.get("library/src/main/resources/property/database.properties"))) {
            properties.load(in);

            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));


        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();

        }
        return connection;
    }

    public Person findByLastname(String lastname) {
        try {
            var stmt = connection.prepareStatement("SELECT * FROM phonebook.phonebook WHERE lastname LIKE CONCAT('%', ?, '%')");
            stmt.setString(1, lastname);

            var rs = stmt.executeQuery();
            while (rs.next()) {
                var person = new Person(rs.getInt("id"));
                person.setLastname(rs.getString("lastname"));
                person.setFirstname(rs.getString("firstname"));
                person.setAge(rs.getInt("age"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                return person;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public List<Person> findAll() {
        try {
            var stmt = connection.prepareStatement("SELECT * FROM phonebook.phonebook");

            var people = new ArrayList<Person>();
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var person = new Person(rs.getInt("id"));
                person.setLastname(rs.getString("lastname"));
                person.setFirstname(rs.getString("firstname"));
                person.setAge(rs.getInt("age"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                people.add(person);
            }
            return people;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Collections.emptyList();
    }

    public Person find(Integer id) {
        try {
            var stmt = connection.prepareStatement("SELECT * FROM phonebook.phonebook WHERE id = ?");
            stmt.setInt(1, id);

            var rs = stmt.executeQuery();
            while (rs.next()) {
                var person = new Person(rs.getInt("id"));
                person.setLastname(rs.getString("lastname"));
                person.setFirstname(rs.getString("firstname"));
                person.setAge(rs.getInt("age"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                return person;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public void delete(int id) {
        try {
            var stmt = connection.prepareStatement("DELETE FROM phonebook.phonebook WHERE Id = ?");
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void save(Person person) {
        try {
            if (person.getId() != null) {
                var stmt = this.connection.prepareStatement(
                        "UPDATE phonebook.phonebook SET age = ?, firstname = ?, lastname = ?, address = ?, phone_number = ? WHERE id = ?"
                );
                stmt.setInt(1, person.getAge());
                stmt.setString(2, person.getFirstname());
                stmt.setString(3, person.getLastname());
                stmt.setString(4, person.getAddress());
                stmt.setString(5, person.getPhoneNumber());
                stmt.setInt(6, person.getId());
                stmt.execute();
            } else {
                var stmt = this.connection.prepareStatement(
                        "INSERT INTO phonebook.phonebook (age, firstname, lastname, address, phone_number) VALUES (?, ?, ?, ?, ?)"
                );
                stmt.setInt(1, person.getAge());
                stmt.setString(2, person.getFirstname());
                stmt.setString(3, person.getLastname());
                stmt.setString(4, person.getAddress());
                stmt.setString(5, person.getPhoneNumber());
                stmt.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
