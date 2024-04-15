package org.example.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserBookingSerice {
    private User user;
    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();
//    it is used in the conversion of data between different representations
//    example: Object Relational Mapping (ORM):which is the process of mapping objects in an object-oriented programming language (such as Java) to tables in a relational database, and vice versa.
//Serialization and Deserialization: Object mappers can serialize Java objects into formats like JSON or XML, and deserialize data from these formats back into Java objects.
    private static final String USERS_PATH="../localDB/users.json";

    public UserBookingSerice(User user1) throws IOException{
        this.user = user1;
        File users = new File(USERS_PATH);
        userList = objectMapper.readValue(users, new TypeReference<List<User>>(){});
    }

}

