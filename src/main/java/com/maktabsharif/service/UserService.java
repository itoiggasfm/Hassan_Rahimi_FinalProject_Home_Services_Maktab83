package com.maktabsharif.service;

import com.maktabsharif.entity.User;
import com.maktabsharif.validation.Validators;
import com.maktabsharif.entity.enumeration.ExpertStatus;
import com.maktabsharif.entity.enumeration.UserRole;
import com.maktabsharif.repository.UserRepository;

import java.io.File;
import java.util.Scanner;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;
    private final Validators validators;
    private final Scanner input;

    public UserService(){
        this.userRepository = new UserRepository();
        this.validators = new Validators();
        this.input = new Scanner(System.in);
    }



    public User beforeSignUp(User user) {
        String username = user.getUsername();
        while (!validators.validateNationalCOde(username) || findByUsername(username)){
            System.out.println("National Code: ");
            username = input.next();
            if (validators.validateNationalCOde(username) && !findByUsername(username))
                break;
        }
        user.setUsername(username);
        user.setPassword(username);

        String email = user.getEmail();
        while (!validators.validateEmail(email) || findByEmail(email)){
            System.out.println("Email : ");
            email = input.next();
            if (validators.validateEmail(email) && !findByEmail(email))
                break;
        }
        user.setEmail(email);


        return user;
    }


    public boolean signUp(User user) {
        if(userRepository.create(beforeSignUp(user)))
            return true;
        else
            return  false;
    }

    public void addProfilePhoto(User user, String imagePath){
        boolean imageSize = false;
        boolean imageExtension = false;
        File file = new File(imagePath);
        if(!file.exists() || !file.isFile())
            return;
        double fileSize = file.length();
        if(fileSize > fileSize/1024)
            System.out.println("Image file must be at most 300 KB in size.");
        else
            imageSize = true;

        if(validators.validateImageExtension(imagePath))
            System.out.println("Image file must be of jpg file extension.");
        else
            imageExtension = true;

        if(imageSize && imageExtension){
            user.setProfilePhotoPath(imagePath);
            update(user, user.getId());
        }

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

    public boolean findPasswordById(Long id, String password){
        return userRepository.findPasswordById(id, password);
    }


    public String oldAndNewPasswordsMatchAndMeet(String oldPassword, String newPassword, String newPasswordConfirmation){
        while(!validators.validatePasswordPolicy(validators.validatePasswordMatch(newPassword, newPasswordConfirmation)) ||
                validators.validateNewAndOldPasswordEquality((validators.validatePasswordMatch(newPassword, oldPassword)), oldPassword)){
            System.out.println("New Password: ");
            newPassword = input.next();
            System.out.println("Enter new password again: ");
            newPasswordConfirmation = input.next();
        }
        return newPassword;
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
        }else{
        System.out.println("Username or password is incorrect\n");
        return null;
}


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
