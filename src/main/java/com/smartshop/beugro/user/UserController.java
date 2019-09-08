package com.smartshop.beugro.user;

import com.smartshop.beugro.ResponseMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/register")
    public ResponseMessage registerUser(@RequestBody User user) {
        if (userDao.listUserNames().contains(user.getName())) {
            return new ResponseMessage("The name "+user.getName()+" is already taken");
        } else if(user.getPassword().length()<3) {
            return new ResponseMessage("The password has to be at least 3 characters long");
        }else{
            userDao.saveUser(user);
            return new ResponseMessage("user "+user.getName()+" is saved");
        }
    }
    @PostMapping("/login")
    public ResponseMessage getUser(@RequestBody User user){
        if (!userDao.listUserNames().contains(user.getName())) {
            return new ResponseMessage("Username not found.");
        }else if (passwordIsCorrect(user)){
            return new ResponseMessage("OK");
        }else{
            return new ResponseMessage("Password is incorrect.");
        }
    }

    private boolean passwordIsCorrect(User user){
       User userFromList = userDao.getUserByName(user.getName());
       return userFromList.getPassword().equals(user.getPassword());
    }
}
