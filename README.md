# 员工信息管理App（Employee-Manage-Service）

## 项目说明

这是一个Android项目，采用前后端分离，实现员工信息管理的基本功能，比如：

* 用户登录
* 账号信息管理
* 员工信息管理
* 员工工资信息管理
* ...

项目已经完结

## 使用的框架

* Android
* FastJSON
* 传统Servlet Web（当时还没学会Spring（笑））
* ...

## 关键代码

### 图片的处理
* 在传输照片时，采用Base64加密使得Bitmap对象转化为String对象，在服务器侧需要解码为InputStream
~~~Java
public class ImageUtils {
    public static InputStream decodeImageString(String imageString) throws IOException {
        byte[] bytes = new BASE64Decoder().decodeBuffer(imageString);
        return new ByteArrayInputStream(bytes);
    }
}
~~~

### Servlet的简化
* 因为用的是Servlet，按照传统的来，估计是一个url对应一个Servlet，估计得写死，所以这里用反射，实现类似Spring的@RequerMapper()的一个效果。
  这样的话，url=当中的'*'就必须是该Servlet函数的名称。
  比如url="/EmployeeServlet/findAll"，那么那个函数也必须叫findAll
~~~Java
@WebServlet(name = "EmployeeServlet", value = "/EmployeeServlet/*")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);//获取url当中的方法名称
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    //执行的方法...
}
~~~

### SQL工具
* 这里没使用Mybatis（当时还不会），所以自己搓了一个Utils，帮助执行SQL，用到Java反射。
  其实就是根据SQL查询后的结果集的MetaData，根据这个MetaData可以得到查询表头，在根据这个查询表头用反射填充属性字段就行了。
  （后续还添加了执行存储过程的方法）
~~~Java
public class JdbcUtils {
    private static final String url = "jdbc:mysql://localhost:3306/android_class_design?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String user = "root";
    private static final String password = "root";
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
~~~