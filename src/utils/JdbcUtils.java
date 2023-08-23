package utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class JdbcUtils {
    private static final String url = "jdbc:mysql://localhost:3306/android_class_design?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String user = "root";
    private static final String password = "lsy12345";
    private static Connection connection = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static synchronized Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public static int add(String sql, Object... var) {
        return modify(sql, var);
    }

    public static int delete(String sql, Object... var) {
        return modify(sql, var);
    }

    public static int update(String sql, Object... var) {
        return modify(sql, var);
    }

    public static <T> ArrayList<T> search(String sql, Class<T> cls, Object... var) {
        ArrayList<T> list = new ArrayList<>();
        try {
            PreparedStatement prep = getConnection().prepareStatement(sql);
            ArrayList<Field> fields = new ArrayList<>();
            for (int i = 0; i < var.length; i++) {
                prep.setObject(i + 1, var[i]);
            }
            ResultSet resultSet = prep.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            //根据sql执行结果，获取cls当中属性字段field
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //System.out.println("ColumnLabel:"+metaData.getColumnLabel(i)+ " ColumnName:"+metaData.getColumnName(i));
                try {
                    //System.out.println(cls.getDeclaredField(metaData.getColumnLabel(i).toLowerCase()));
                    fields.add(cls.getDeclaredField(metaData.getColumnLabel(i)));
                } catch (NoSuchFieldException ignored) {}
            }
            while (resultSet.next()) {
                T instance = null;
                //基本类型处理
                if (cls.isAssignableFrom(Integer.class)
                        || cls.isAssignableFrom(Long.class)
                        || cls.isAssignableFrom(Double.class)
                        || cls.isAssignableFrom(String.class)) {
                    instance = (T) resultSet.getObject(1);
                } else if (cls.isAssignableFrom(Boolean.class)) {
                    long v = resultSet.getLong(1);
                    Boolean val = v > 0;
                    instance = (T) val;
                } else {
                    instance = cls.newInstance();//创建cls对应的实例
                    for (Field field : fields) {
                        field.setAccessible(true);//属性字段为private，需设置可见性为true才能调用field的set方法
                        field.set(instance, resultSet.getObject(field.getName()));//设置实例属性字段的值
                    }
                }
                list.add(instance);
            }
            prep.close();
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public static <T> T searchOne(String sql, Class<T> cls, Object... var) {
        return search(sql,cls,var).get(0);
    }
    private static int modify(String sql, Object... var) {
        int modify=0;
        try {
            PreparedStatement prep = getConnection().prepareStatement(sql);
            for (int i = 0; i < var.length; i++) {
                prep.setObject(i + 1, var[i]);
            }
            modify= prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modify;
    }

}
