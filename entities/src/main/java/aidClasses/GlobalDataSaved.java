package aidClasses;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Principal;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class GlobalDataSaved {
    public static User connectedUser;

    public static ObservableList<Subject> subjects = FXCollections.observableArrayList();
    public static boolean AddFlag = true;
}
