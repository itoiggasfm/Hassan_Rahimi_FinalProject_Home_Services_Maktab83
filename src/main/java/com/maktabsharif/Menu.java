package com.maktabsharif;

import com.maktabsharif.entity.*;
import com.maktabsharif.entity.enumeration.ExpertStatus;
import com.maktabsharif.entity.enumeration.UserRole;
import com.maktabsharif.service.UserService;
import com.maktabsharif.validation.Validators;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final User user = new User();
    private static final Services services = new Services();
    private static final Order order = new Order();

    private static final Validators validators = new Validators();

    private static final UserService userService = new UserService();





//    private static final OrderService orderService = new OrderService();
    private static final Scanner input = new Scanner(System.in);
    private static User signedInUser = null;
    private static List<Order> cart = new ArrayList<>();

    private static List<Order> OrdersList = new ArrayList<>();
    private static List<User> usersList = new ArrayList<>();
    private static List<Services> servicessList = new ArrayList<>();


    public static void home(){
        boolean valid = true;
        while (valid == true){
            System.out.println("----- Home page -----");
            System.out.println("1. Sign in");
            System.out.println("2. Sign up");
//            System.out.println("3. Services");
            System.out.println("---------------------------");

            String choice = input.next();
            switch (choice){
                case "1":{
                    signIn();
                    if(signedInUser != null){
//                        if(signedInUser.getUserRole().equals(UserRole.CLIENT))
//                            clientAffairs(signedInUser);
//                        if(signedInUser.getUserRole().equals(UserRole.EXPERT))
//                            expertAffairs(signedInUser);
                        if(signedInUser.getUsername().equals("admin"))
                            adminAffairs(signedInUser);
                    }
                }//case 1
                break;

                case "2": {
                    signUp();
//                    if (signedInUser != null)
//                        clientAffairs(signedInUser);
                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }//end of switch (choice)
        }//end of while (valid = true)
    }//end of home


    public static void signIn(){

        System.out.println("Username: (National code) ");
        String username = input.next().toLowerCase();
        System.out.println("Password: (National code) ");
        String password = input.next().toLowerCase();
        signedInUser = userService.singIn(username, password);
    }

    public static void signUp(){
        System.out.println("Name: ");
        user.setName(input.next().toLowerCase());

        System.out.println("Family name: ");
        user.setFamilyName(input.next().toLowerCase());

        System.out.println("National Code: ");
        String nationalCode = input.next();
        while (!validators.validateNationalCOde(nationalCode) || userService.findByUsername(nationalCode)){
            System.out.println("National Code: ");
            nationalCode = input.next();
            if (validators.validateNationalCOde(nationalCode) && !userService.findByUsername(nationalCode))
                break;
        }
//        user.setId(nationalCode);
        user.setUsername(nationalCode);
        user.setPassword(nationalCode);

        System.out.println("Email : ");
        String email = input.next().toLowerCase();
        while (!validators.validateEmail(email) || userService.findByEmail(email)){
            System.out.println("Email : ");
            email = input.next();
            if (validators.validateEmail(email) && !userService.findByEmail(email))
                break;
        }
        user.setEmail(email);

        user.setRegisterDate(ZonedDateTime.now().toEpochSecond());

        Wallet userWallet = new Wallet();
        userWallet.setBalance(0d);
        user.setWallet(userWallet);

        System.out.println("Are you registering as an expert? (Y/N)");
        String wantToBeExpert = input.next();
        while (!(wantToBeExpert.equals("y") || wantToBeExpert.equals("n"))){
            System.out.println("Invalid answer.");
            System.out.println("Are you registering as an expert? (Y/N)");
            wantToBeExpert = input.next();
        }
        if(wantToBeExpert.equals("y")){
            user.setUserRole(UserRole.EXPERT);
            user.setExpertStatus(ExpertStatus.NEW);
            user.setExpertPoint(0);
        }
        else
            user.setUserRole(UserRole.CLIENT);


        if(userService.signUp(user) && user.getUserRole().equals(UserRole.CLIENT))
            signedInUser = user;
    }//end of signUp


    public static void changePassword(){
        String newPassword = null;
        System.out.println("Enter old password: ");
        String oldPassword = input.next();
        if(signedInUser.getPassword().equals(oldPassword)){
            System.out.println("New Password: ");
            newPassword = input.next();
            System.out.println("Enter new password again: ");
            String newPasswordConfirmation = input.next();
            while(!validators.validatePasswordPolicy(validators.validatePasswordMatch(newPassword, newPasswordConfirmation)) || validators.validateNewAndOldPasswordEquality((validators.validatePasswordMatch(newPassword, oldPassword)), oldPassword)){
                System.out.println("New Password: ");
                newPassword = input.next();
                System.out.println("Enter new password again: ");
                newPasswordConfirmation = input.next();
            }
            User passwordChangingUser = signedInUser;
            passwordChangingUser.setPassword(newPassword);
            userService.update(passwordChangingUser, signedInUser.getId());
            if(userService.isPasswordChanged(signedInUser.getId(), newPassword))
                signedInUser.setPassword(newPassword);
        }
        else
            System.out.println("Incorrect password.\n");



    }//end of changePassword


    public static void adminAffairs(User signedInUser){

        boolean valid = true;
        while (valid == true){
            System.out.println("\n-------------------- Welcome dear " + signedInUser.getName() + "--------------------"
                        + "\n1. Sign out"
                        + "\n2. Change password"
                        + "\n3. List of available services"
                        + "\n4. Add a service"
                        + "\n5. Edit a service"
                        + "\n6. Approve registered experts"
                        + "\n7. Add or remove expert from services"
                        + "\n-------------------------------------\n");

            System.out.println("Choose an item:");
            String choice = input.next();
            switch (choice){
                case "1":{
                    valid =false;
                }//end of case 1
                break;

                case "2":{
                 changePassword();
                }//case 2
                break;

                case "3":{

                }
                break;

                case "4":{

                }
                break;

                case "5":{

                }
                break;

                case "6":{

                }
                break;

                case "7":{

                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }//end of adminAffairs


    public static void expertAffairs(User signedInUser){

        boolean valid = true;
        while (valid == true){
            System.out.println("\n-------------------- Welcome dear --------------------" + signedInUser.getName()
                    + "\n1. Sign out"
                    + "\n2. Change password"
                    + "\n3. List of available services"
                    + "\n4. Make an order"
                    + "\n-------------------------------------\n");

            System.out.println("Choose an item:\n");
            String choice = input.next();
            switch (choice){
                case "1":{
                    valid =false;
                }//end of case 1
                break;

                case "2":{
                    changePassword();
                }//case 2
                break;

                case "3":{

                }
                break;

                case "4":{

                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }//end of expertAffairs


    public static void clientAffairs(User signedInUser){

        boolean valid = true;
        while (valid == true){
            System.out.println("\n-------------------- Welcome dear --------------------" + signedInUser.getName()
                    + "\n1. Sign out"
                    + "\n2. Change password"
                    + "\n3. List of available orders"
                    + "\n4. Select an order"
                    + "\n-------------------------------------\n");

            System.out.println("Choose an item:\n");
            String choice = input.next();
            switch (choice){
                case "1":{
                    valid =false;
                }//end of case 1
                break;

                case "2":{
                    changePassword();
                }//case 2
                break;

                case "3":{

                }
                break;

                case "4":{

                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }//end of clientAffairs

}
