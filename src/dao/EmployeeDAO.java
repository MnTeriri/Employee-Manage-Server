package dao;

import com.mysql.cj.xdevapi.PreparableStatement;
import model.Employee;
import model.User;
import sun.misc.BASE64Encoder;
import utils.DatabaseUtils;
import utils.ImageUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EmployeeDAO {
    public ArrayList<Employee> findAll() {
        ArrayList<Employee> list = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "SELECT *,Employee.eno AS eno,Employee.id AS id FROM Employee INNER JOIN User ON employee.eno = user.eno";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            list = executeResultSet(resultSet);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ArrayList<Employee> selectEmployeeByLimit(String eno, Integer page, Integer count) {
        ArrayList<Employee> list = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "SELECT *,Employee.eno AS eno,Employee.id AS id FROM Employee INNER JOIN User " +
                    "ON employee.eno = user.eno " +
                    "WHERE employee.eno LIKE ? LIMIT ?,?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + eno + "%");
            statement.setInt(2, (page - 1) * count);
            statement.setInt(3, count);
            ResultSet resultSet = statement.executeQuery();
            list = executeResultSet(resultSet);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean addEmployee(Employee employee) {
        boolean result = false;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "{call insert_employee(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement statement = connection.prepareCall(sql);
            statement.setString(1, employee.getEno());
            statement.setString(2, employee.getEname());
            statement.setString(3, employee.getSex());
            statement.setInt(4, employee.getAge());
            statement.setString(5, employee.getDept());
            statement.setString(6, employee.getAppointment());
            statement.setObject(7, employee.getEntryTime());
            statement.setString(8, employee.getQualification());
            statement.setString(9, employee.getPhone());
            statement.setBlob(10, ImageUtils.decodeImageString(employee.getImageString()));
            statement.execute();
            result = true;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean updateEmployee(Employee employee) {
        boolean result = false;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "{call update_employee(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement statement = connection.prepareCall(sql);
            statement.setString(1, employee.getEno());
            statement.setString(2, employee.getEname());
            statement.setString(3, employee.getSex());
            statement.setInt(4, employee.getAge());
            statement.setString(5, employee.getDept());
            statement.setString(6, employee.getAppointment());
            statement.setObject(7, employee.getEntryTime());
            statement.setString(8, employee.getQualification());
            statement.setString(9, employee.getPhone());
            statement.setBlob(10, ImageUtils.decodeImageString(employee.getImageString()));
            statement.execute();
            result = true;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean deleteEmployee(String eno) {
        boolean result = false;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "{call delete_employee(?)}";
            CallableStatement statement = connection.prepareCall(sql);
            statement.setString(1, eno);
            statement.execute();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public int getSelectEmployeeCount(String eno) {
        int result = 0;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "SELECT Count(eno) FROM employee WHERE eno LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + eno + "%");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public int employeeExist(String eno) {
        int result = 0;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "SELECT Count(eno) FROM employee WHERE eno = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, eno);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private ArrayList<Employee> executeResultSet(ResultSet resultSet) throws SQLException, IOException {
        ArrayList<Employee> list = new ArrayList<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String eno = resultSet.getString("eno");
            String ename = resultSet.getString("ename");
            String sex = resultSet.getString("sex");
            Integer age = resultSet.getInt("age");
            String dept = resultSet.getString("dept");
            String appointment = resultSet.getString("appointment");
            LocalDateTime entryTime = (LocalDateTime) resultSet.getObject("entryTime");
            String qualification = resultSet.getString("qualification");
            String phone = resultSet.getString("phone");
            Blob blob = resultSet.getBlob("image");
            String imageString = null;
            if (blob != null) {
                InputStream inputStream = blob.getBinaryStream();
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                BASE64Encoder encoder = new BASE64Encoder();
                imageString = encoder.encode(bytes);
            }
            Employee employee = new Employee(id, eno, ename, sex, age, dept, appointment, entryTime, qualification, imageString, phone);
            list.add(employee);
        }
        return list;
    }
}
