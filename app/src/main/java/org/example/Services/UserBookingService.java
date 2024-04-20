package org.example.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Train;
import org.example.models.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;


    private ObjectMapper objectMapper = new ObjectMapper();
//    it is used in the conversion of data between different representations
//    example: Object Relational Mapping (ORM):which is the process of mapping objects in an object-oriented programming language (such as Java) to tables in a relational database, and vice versa.
//Serialization and Deserialization: Object mappers can serialize Java objects into formats like JSON or XML, and deserialize data from these formats back into Java objects.
    private static final String USERS_PATH="app/src/main/java/org/example/localDB/users.json";

    public UserBookingService(User user) throws IOException{
        this.user = user;

        loadUserListFromFile();
    }

    public UserBookingService() throws IOException{
        loadUserListFromFile();
    }

    private void loadUserListFromFile() throws IOException {
        userList = objectMapper.readValue(new File(USERS_PATH), new TypeReference<List<User>>() {});
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

    public void fetchBookings(){

    }
    public Boolean cancelBooking(String ticketId){
        // todo: Complete this function
        return Boolean.FALSE;
    }

    public List<Train> getTrains(String source,String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source,destination);
        }catch (IOException e){
            return null;
        }
    }

    public List<List<Integer>> fetchSeats(Train trainSelectedForBooking) {
        return trainSelectedForBooking.getSeats();
    }


    public Boolean bookTrainSeat(Train train, int row, int seat) {
        try{
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    return true; // Booking successful
                } else {
                    return false; // Seat is already booked
                }
            } else {
                return false; // Invalid row or seat index
            }
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

}

