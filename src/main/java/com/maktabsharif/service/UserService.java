package com.maktabsharif.service;

import com.maktabsharif.entity.User;
import com.maktabsharif.entity.enumeration.ExpertStatus;
import com.maktabsharif.entity.enumeration.UserRole;
import com.maktabsharif.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(){
        this.userRepository = new UserRepository();
    }



    public boolean signUp(User user) {
        if(userRepository.create(user))
            return true;
        else
            return  false;
    }

    public boolean findByUsername(String username) {
        if (userRepository.findByUsername(username) != null){
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


    public User findById(Long id){
        return userRepository.findById(id);
    }


    public User singIn(String username, String password) {
        User user = userRepository.findByUsername(username);
        if( user != null && user.getPassword().equals(password) && (user.getUserRole().equals(UserRole.CLIENT) || (user.getUserRole().equals(UserRole.EXPERT) && user.getExpertStatus().equals(ExpertStatus.APPROVED)) || user.getUserRole().equals(UserRole.ADMIN))){
            System.out.println("Login successful.\n");
            return user;
        }
        System.out.println("Username or password is incorrect\n");
        return null;
    }


    public void update(User user, Long id) {
        userRepository.update(user, id);
    }

    public boolean isPasswordChanged(Long id, String password) {
        User user = userRepository.findById(id);
        if(user.getPassword().equals(password)){
            System.out.println("Password successfully changed.\n");
            return true;
        }
        else {
            System.out.println("Password was not changed.\n");
            return false;
        }
    }

    public List<User> findAllUsers(){
         return userRepository.findAll();
    }

    public void findApprovedExperts(){
        List<User> users = findAllUsers();
        System.out.println("Expert ID   Expert name");
        System.out.println("-----------------------");
        for(User user: users){
            if(user.getUserRole().equals(UserRole.EXPERT) && user.getExpertStatus().equals(ExpertStatus.APPROVED)){
                System.out.printf("%-12d%s\t%s\n", user.getId(), user.getName(), user.getFamilyName());
            }
        }
    }

    public void findNewExperts(){
        List<User> users = findAllUsers();
        System.out.println("Expert ID   Expert name");
        System.out.println("-----------------------");
        for(User user: users){
            if(user.getUserRole().equals(UserRole.EXPERT) && user.getExpertStatus().equals(ExpertStatus.NEW)){
                System.out.printf("%-12d%s\t%s\n", user.getId(), user.getName(), user.getFamilyName());
            }
        }
    }

}
