package il.cshaifasweng.OCSFMediatorExample.entities.appUsers;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
@Table(name = "principal")
public class Principal extends User implements Serializable {


    public Principal(String userID, String passWord, String userName, String firstName, String lastName) {
        super(userID, passWord, userName, firstName, lastName);
    }

    public Principal() {

    }
}