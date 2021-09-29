package Models;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String username, password, address, birthday, sex, description;

    public User(int userId, String username, String password, String address, String brithday, String sex, String description){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.birthday = brithday;
        this.sex = sex;
        this.address = address;
        this.description = description;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getUserName(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getBirthday(){
        return this.birthday;
    }

    public String getSex(){
        return this.sex;
    }

    public String getDes(){
        return this.description;
    }

    public String getAddress(){
        return this.address;
    }
}
