package org.example.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingSerice {
    private User user;
    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();
//    it is used in the conversion of data between different representations
//    example: Object Relational Mapping (ORM):which is the process of mapping objects in an object-oriented programming language (such as Java) to tables in a relational database, and vice versa.
//Serialization and Deserialization: Object mappers can serialize Java objects into formats like JSON or XML, and deserialize data from these formats back into Java objects.
    private static final String USERS_PATH="app/src/main/java/org/example/Services/UserBookingSerice.java";

    public UserBookingSerice(User user) throws IOException{
        this.user = user;
        File users = new File(USERS_PATH);
        userList = objectMapper.readValue(users, new TypeReference<List<User>>(){});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

}

