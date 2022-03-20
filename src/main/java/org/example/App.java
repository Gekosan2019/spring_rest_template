package org.example;


import org.example.Entity.User;
import org.example.configuration.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication =  applicationContext.getBean("communication", Communication.class);
        List<User> userList = communication.getAllUsers();
        communication.saveUser(new User( "James", "Brown", (byte) 23));
        communication.saveUser(new User(3L, "Thomas", "Shelby", (byte) 23));
        communication.deleteUser(3L);
    }
}
