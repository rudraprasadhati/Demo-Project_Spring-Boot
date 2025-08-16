package com.rph.ecom_proj.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection="User-Data")
public class UserData {
    @Indexed(unique = true) //Makes sure that there are no duplicate values.
    private String username;
    private String password;
    private List<String> role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    //When we create and object of this class and try to print it , it gives the output as something like the class name and a set of characters appended to it.
    //So to get the output in the form of that class's data member types , we use the toString() method.
    @Override
    public String toString() {
        return "UserData{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
