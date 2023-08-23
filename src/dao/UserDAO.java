package dao;

import model.User;
import sun.misc.BASE64Encoder;
import utils.DatabaseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    public ArrayList<User> findAll(){
        ArrayList<User> list=new ArrayList<>();
        try {
            Connection connection=DatabaseUtils.getConnection();
            String sql="SELECT * FROM User";
            PreparedStatement statement=connection.prepareStatement(sql);
            ResultSet resultSet=statement.executeQuery();
            list=executeResultSet(resultSet);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public User searchUserByUid(String uid){
        User user=new User();
        try {
            Connection connection=DatabaseUtils.getConnection();
            String sql="SELECT * FROM User WHERE uid=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,uid);
            ResultSet resultSet=statement.executeQuery();
            ArrayList<User> list=executeResultSet(resultSet);
            if(list.size()!=0){
                user=list.get(0);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public boolean updateUserPassword(String uid,String password){
        boolean result=false;
        try {
            String sql="UPDATE User SET password=MD5(?) WHERE uid=?";
            Connection connection=DatabaseUtils.getConnection();
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,password);
            statement.setString(2,uid);
            int a=statement.executeUpdate();
            if(a==1){
                result=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public User loginUser(String uid,String password){
        User user=new User();
        try {
            Connection connection=DatabaseUtils.getConnection();
            String sql="SELECT * FROM User WHERE uid=? AND password=MD5(?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,uid);
            statement.setString(2,password);
            ResultSet resultSet=statement.executeQuery();
            ArrayList<User> list=executeResultSet(resultSet);
            if(list.size()!=0){
                user=list.get(0);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private ArrayList<User> executeResultSet(ResultSet resultSet) throws SQLException, IOException {
        ArrayList<User> list=new ArrayList<>();
        while (resultSet.next()){
            Integer id=resultSet.getInt("id");
            String uid=resultSet.getString("uid");
            String password=resultSet.getString("password");
            Integer flag=resultSet.getInt("flag");
            String eno=resultSet.getString("eno");
            String uname=resultSet.getString("uname");
            Blob blob = resultSet.getBlob("image");
            String imageString=null;
            if(blob!=null){
                InputStream inputStream=blob.getBinaryStream();
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                BASE64Encoder encoder = new BASE64Encoder();
                imageString=encoder.encode(bytes);
            }
            User user=new User(id,uid,password,flag,eno,uname,imageString);
            list.add(user);
        }
        return list;
    }
}
