import com.alibaba.fastjson2.JSON;
import com.mysql.cj.xdevapi.PreparableStatement;
import dao.EmployeeDAO;
import dao.SalaryDAO;
import dao.UserDAO;
import model.Employee;
import model.User;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import utils.DatabaseUtils;
import utils.JdbcUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws Exception {
//        InputStream inputStream=new FileInputStream("F:/Code/IDEA/Android Class Design Server/src/3.png");
//        Connection connection= DatabaseUtils.getConnection();
//        String sql="UPDATE User SET image =? WHERE uid=?";
//        PreparedStatement statement=connection.prepareStatement(sql);
//        statement.setBlob(1,inputStream);
//        statement.setString(2,"123456790");
//        statement.execute();
        //        byte[] data = new byte[inputStream.available()];
//        inputStream.read(data);
//        // 加密
//        BASE64Encoder encoder = new BASE64Encoder();
//        String str=encoder.encode(data);
//        //String str=Base64.getEncoder().encodeToString(data);
//        System.out.println(str);
//
//        byte[] bytes = new BASE64Decoder().decodeBuffer(str);
//        FileOutputStream outputStream=new FileOutputStream("F:/Code/IDEA/Android Class Design Server/src/2.jpg");
//        outputStream.write(bytes);
//        outputStream.flush();
//        EmployeeDAO dao=new EmployeeDAO();
//        Integer age=11;
//        Employee employee=new Employee("1212","asdas","男",age,"软件部门","asd",null,"本科","","123");
//        dao.addEmployee(employee);
//        String format = LocalDateTime.now().format(
//                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        );
//        System.out.println(format);
//        LocalDate time = LocalDate.parse("2022-2-2", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        LocalDateTime localDateTime=time.atStartOfDay();
//        System.out.println(time);
//        System.out.println(localDateTime);
//        EmployeeDAO dao=new EmployeeDAO();
//        System.out.println(dao.employeeExist("123456789"));
        SalaryDAO dao=new SalaryDAO();
        System.out.println(dao.findAll());
    }
}
