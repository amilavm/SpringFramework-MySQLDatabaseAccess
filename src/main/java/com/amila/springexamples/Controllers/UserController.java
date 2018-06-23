package com.amila.springexamples.Controllers;

import com.amila.springexamples.Models.User;
import com.amila.springexamples.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Asus on 23/06/2018.
 */
@RestController
@RequestMapping(value = "/rest")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /*retrieve all users*/
    @RequestMapping(value = "/user")
    public ResponseEntity<List<User>> displayAllUsers(){
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    /*retrieve single user*/
    @RequestMapping(value = "/user/{id}")
    public ResponseEntity<?> displaySingleUser(@PathVariable("id") Integer id){
        User user = userRepository.findByid(id);
        if(user == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /*create user*/
    @RequestMapping(value = "/user" , method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User userAccount){
        User usr = this.userRepository.save(userAccount);
        return new ResponseEntity<User>(usr, HttpStatus.CREATED);
    }

    /*update a user*/
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id, @RequestBody User userAccount){
        User currentUser = userRepository.findByid(id);
        if(currentUser == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        currentUser.setFirstname(userAccount.getFirstname());
        currentUser.setLastname(userAccount.getLastname());
        currentUser.setAddress(userAccount.getAddress());
        currentUser.setEmail(userAccount.getEmail());

        User updt = this.userRepository.save(currentUser);

        return new ResponseEntity<User>(updt, HttpStatus.OK);
    }

    /*delete a user*/
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id){
        User user = userRepository.findByid(id);
        if(user == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        userRepository.delete(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /*delete all users*/
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllUsers(){
        userRepository.deleteAll();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
