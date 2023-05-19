package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class Generating_Users {
    public static void generateUsers(Session session)
    {
        Student us=new Student("33333","123","asfa","hdfhfh","jfjtj");
        session.save(us);
        session.flush();
        Student us2=new Student("33333","123","asfa","hdfhfh","jfjtj");
        session.save(us2);
        session.flush();
    }
}
