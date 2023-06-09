package il.cshaifasweng.OCSFMediatorExample.entities.appUsers;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User implements  Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userID;
    private String passWord;
    private String userName;
    private String firstName;
    private String lastName;
    private Boolean isConnected;
    public User() {

    }
    public User(String userID, String passWord, String userName, String firstName, String lastName) {
        this.userID = userID;
        this.passWord = passWord;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isConnected=false;
    }

    public User(User user) {
        this.id=user.id;
        this.userID = user.userID;
        this.passWord = user.passWord;
        this.userName = user.userName;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.isConnected=false;
    }

    public int getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }
}
