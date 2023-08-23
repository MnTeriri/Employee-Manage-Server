package model;

public class User {
    private Integer id;
    private String uid;
    private String password;
    private Integer flag;
    private String eno;
    private String uname;
    private String imageString;

    public User() {
    }

    public User(Integer id, String uid, String password, Integer flag, String eno, String uname, String imageString) {
        this.id = id;
        this.uid = uid;
        this.password = password;
        this.flag = flag;
        this.eno = eno;
        this.uname = uname;
        this.imageString = imageString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getEno() {
        return eno;
    }

    public void setEno(String eno) {
        this.eno = eno;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", password='" + password + '\'' +
                ", flag=" + flag +
                ", eno='" + eno + '\'' +
                ", uname='" + uname + '\'' +
                ", imageString='" + imageString + '\'' +
                '}';
    }
}
