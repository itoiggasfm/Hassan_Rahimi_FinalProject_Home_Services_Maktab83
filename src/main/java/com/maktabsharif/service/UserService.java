package com.maktabsharif.service;

import com.maktabsharif.entity.BaseEntity;
import com.maktabsharif.entity.User;
import com.maktabsharif.repository.UserRepository;

public class UserService {

    private UserRepository<BaseEntity> userRepository;

    public UserService(){
        this.userRepository = new UserRepository<BaseEntity>("User", User.class);
    }



    public boolean signUp(User user) {
        if(userRepository.create(user))
            return true;
        else
            return  false;
    }

    public boolean findByID(String nationalCode) {
        if (userRepository.findById(nationalCode) != null){
            System.out.println("National code already exists.");
            return true;
        }
        else {
            return false;
        }
    }

    public boolean findByEmail(String email) {
        if (userRepository.findByEmail(email) != null){
            System.out.println("Email already exists.");
            return true;
        }
        else {
            return false;
        }
    }


    public User singIn(String username, String password) {
        User user = userRepository.findByUsername(username);
        if( user != null && user.getPassword().equalsIgnoreCase(password)){
            System.out.println("Login successful.\n");
            return user;
        }
        System.out.println("Username or password is incorrect\n");
        return null;
    }


    public void update(User user, String id) {
        userRepository.update(user, id);
    }

}
