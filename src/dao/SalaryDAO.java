package dao;

import model.Salary;
import utils.DatabaseUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SalaryDAO {
    public ArrayList<Salary> findAll(){
        ArrayList<Salary> list = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "SELECT Salary.id,Salary.eno," +
                    "ename,basicSalary, rewardSalary," +
                    " overtimeSalary, subsidySalary," +
                    " reduceSalary,settlementTime FROM Salary INNER JOIN Employee " +
                    "ON employee.eno = Salary.eno " +
                    "ORDER BY Salary.eno,settlementTime ASC";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            list = executeResultSet(resultSet);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public ArrayList<Salary> selectSalary(String eno){
        ArrayList<Salary> list = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "SELECT Salary.id,Salary.eno," +
                    "ename,basicSalary, rewardSalary," +
                    " overtimeSalary, subsidySalary," +
                    " reduceSalary,settlementTime FROM Salary INNER JOIN Employee " +
                    "ON Employee.eno = Salary.eno WHERE Salary.eno = ?" +
                    "ORDER BY Salary.eno,settlementTime ASC";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, eno);
            ResultSet resultSet = statement.executeQuery();
            list = executeResultSet(resultSet);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public ArrayList<Salary> selectSalaryByLimit(String eno, Integer page, Integer count){
        ArrayList<Salary> list = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "SELECT Salary.id,Salary.eno," +
                    "ename,basicSalary, rewardSalary," +
                    " overtimeSalary, subsidySalary," +
                    " reduceSalary,settlementTime FROM Salary INNER JOIN Employee " +
                    "ON Employee.eno = Salary.eno WHERE Salary.eno LIKE ?" +
                    "ORDER BY Salary.eno,settlementTime ASC LIMIT ?,? ";
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

    public int getSelectSalaryCount(String eno) {
        int result = 0;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "SELECT Count(eno) FROM salary WHERE eno LIKE ?";
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

    public boolean addSalary(Salary salary) {
        boolean result = false;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "INSERT INTO Salary(eno, basicSalary, rewardSalary, " +
                    "overtimeSalary, subsidySalary, reduceSalary,settlementTime) " +
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,salary.getEno());
            statement.setBigDecimal(2,salary.getBasicSalary());
            statement.setBigDecimal(3,salary.getRewardSalary());
            statement.setBigDecimal(4,salary.getOvertimeSalary());
            statement.setBigDecimal(5,salary.getSubsidySalary());
            statement.setBigDecimal(6,salary.getReduceSalary());
            statement.setObject(7,salary.getSettlementTime());
            if(statement.executeUpdate()==1){
                result=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean updateSalary(Salary salary) {
        boolean result = false;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String sql = "UPDATE Salary SET basicSalary=?, rewardSalary=?, " +
                    "overtimeSalary=?, subsidySalary=?, " +
                    "reduceSalary=?,settlementTime=? WHERE eno=? AND id=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setBigDecimal(1,salary.getBasicSalary());
            statement.setBigDecimal(2,salary.getRewardSalary());
            statement.setBigDecimal(3,salary.getOvertimeSalary());
            statement.setBigDecimal(4,salary.getSubsidySalary());
            statement.setBigDecimal(5,salary.getReduceSalary());
            statement.setObject(6,salary.getSettlementTime());
            statement.setString(7,salary.getEno());
            statement.setInt(8,salary.getId());
            if(statement.executeUpdate()==1){
                result=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private ArrayList<Salary> executeResultSet(ResultSet resultSet) throws SQLException, IOException {
        ArrayList<Salary> list = new ArrayList<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String eno = resultSet.getString("eno");
            String ename = resultSet.getString("ename");
            BigDecimal basicSalary=resultSet.getBigDecimal("basicSalary");
            BigDecimal rewardSalary=resultSet.getBigDecimal("rewardSalary");
            BigDecimal overtimeSalary=resultSet.getBigDecimal("overtimeSalary");
            BigDecimal subsidySalary=resultSet.getBigDecimal("subsidySalary");
            BigDecimal reduceSalary=resultSet.getBigDecimal("reduceSalary");
            LocalDateTime settlementTime=(LocalDateTime) resultSet.getObject("settlementTime");
            BigDecimal totalSalary=new BigDecimal(0);
            totalSalary=totalSalary.add(basicSalary);
            totalSalary=totalSalary.add(rewardSalary);
            totalSalary=totalSalary.add(overtimeSalary);
            totalSalary=totalSalary.add(subsidySalary);
            totalSalary=totalSalary.subtract(reduceSalary);
            Salary salary=new Salary(id,eno,ename,basicSalary,rewardSalary,overtimeSalary,subsidySalary,reduceSalary,totalSalary,settlementTime);
            list.add(salary);
        }
        return list;
    }
}
