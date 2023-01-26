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




//    private static final ProductService productService = new ProductService();
//    private static final OrderService orderService = new OrderService();
    private static final Scanner input = new Scanner(System.in);
    private static User signedInUser = null;
    private static List<Order> cart = new ArrayList<>();

    private static List<Order> allOrdersList = new ArrayList<>();
    private static List<User> allPeopleList = new ArrayList<>();
    private static List<Services> productsList = new ArrayList<>();


    public static void home(){
        boolean valid = true;
        while (valid == true){
            System.out.println("----- Home page -----");
            System.out.println("1. Sign in");
            System.out.println("2. Sign up");
//            System.out.println("3. Services");
            System.out.println("=======================");

            String choice = input.next();
            switch (choice){
                case "1":{
                    signIn();
//                    if(signedInUser != null){
//                        if(signedInUser.getUserRole().equals("CLIENT"))
//                            clientAffairs(signedInUser);
//                        if(signedInUser.getUserRole().equals("EXPERT") && signedInUser.getExpertStatus().equals("Approved"))
//                            expertAffairs(signedInUser);
//                        if(signedInUser.getUserRole().equals("ADMIN"))
//                            adminAffairs(signedInUser);
//                    }
                }//case 1
                break;

                case "2": {
                    signUp();
//                    if (signedInUser != null) {
//                        if (signedInUser.getUserRole().equals("EXPERT") && signedInUser.getExpertStatus().equals("Approved"))
//                            expertAffairs(signedInUser);
//                    }
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
        while (!validators.validateNationalCOde(nationalCode) || userService.findByID(nationalCode)){
            System.out.println("National Code: ");
            nationalCode = input.next();
            if (validators.validateNationalCOde(nationalCode) && !userService.findByID(nationalCode))
                break;
        }
        user.setId(nationalCode);
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

        user.getWallet().setCredit(0d);

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


        if(userService.signUp(user))
            signedInUser = user;
    }//end of signUp


    public static void changePassword(){
        System.out.println("Enter old password: ");
        String oldPassword = input.next();
        while(!signedInUser.getPassword().equals(oldPassword)){
            System.out.println("Incorrect password");
            System.out.println("Enter old password: ");
            oldPassword = input.next();
        }

        System.out.println("New Password: ");
        String newPassword = input.next();
        System.out.println("Enter new password again: ");
        String newPasswordConfirmation = input.next();
        while (!newPassword.equals(newPasswordConfirmation)){
            System.out.println("Passwords don't match\n");
            System.out.println("New Password: ");
            newPassword = input.next();
            System.out.println("Enter new password again: ");
            newPasswordConfirmation = input.next();
        }
        signedInUser.setPassword(newPassword);
        userService.update(signedInUser, signedInUser.getId());

    }//end of changePassword


//    public static void adminAffairs(User signedInUser){
//
//        boolean valid = true;
//        while (valid == true){
//            System.out.printf("%n--------------------%nWelcome dear %s%n--------------------%n",signedInUser.getName());
//            System.out.println("1. Sign out");
//            System.out.println("2. Change password");
//            System.out.println("3. List of available services");
//            System.out.println("4. Add a service");
//            System.out.println("5. Approve registered expeerts");
//            System.out.println("6. Add or remove expert from services");
//            System.out.printf("%n--------------------%n");
//
//            System.out.println("Choose an item:\n");
//            String choice = input.next();
//            switch (choice){
//                case "1":{
//                    valid =false;
//                }//end of case 1
//                break;
//
//                case "2":{
//                    String newPassword = changePassword();
//
//                    if(customer.chnagePasword(signedInPerson.getId(), newPassword))
//                        signedInPerson.setPassword(newPassword);
//                }//case 2
//                break;
//
//                case "3":{
//                    addToCart();
//                }
//                break;
//
//                case "4":{
//                    cart();
//                }
//                break;
//
//                case "5":{
//                    cart();
//                }
//                break;
//
//                case "6":{
//                    cart();
//                }
//                break;
//
//                default:
//                    System.out.println("Invalid choice");
//                    break;
//            }
//        }
//    }//end of adminaffairs
//
//
//    public static void expertAffairs(User signedInUser){
//
//        boolean valid = true;
//        while (valid == true){
//            System.out.printf("%n--------------------%nWelcome dear %s%n--------------------%n",signedInUser.getName());
//            System.out.println("1. Sign out");
//            System.out.println("2. Change password");
//            System.out.println("3. List of available services");
//            System.out.println("4. Add a service");
//            System.out.println("5. Approve registered expeerts");
//            System.out.println("6. Add or remove expert from services");
//            System.out.printf("%n--------------------%n");
//
//            System.out.println("Choose an item:\n");
//            String choice = input.next();
//            switch (choice){
//                case "1":{
//                    valid =false;
//                }//end of case 1
//                break;
//
//                case "2":{
//                    String newPassword = changePassword();
//
//                    if(customer.chnagePasword(signedInPerson.getId(), newPassword))
//                        signedInPerson.setPassword(newPassword);
//                }//case 2
//                break;
//
//                case "3":{
//                    addToCart();
//                }
//                break;
//
//                case "4":{
//                    cart();
//                }
//                break;
//
//                case "5":{
//                    cart();
//                }
//                break;
//
//                case "6":{
//                    cart();
//                }
//                break;
//
//                default:
//                    System.out.println("Invalid choice");
//                    break;
//            }
//        }
//    }//end of expertAffairs
//
//
//    public static void clientAffairs(User signedInUser){
//
//        boolean valid = true;
//        while (valid == true){
//            System.out.printf("%n--------------------%nWelcome dear %s%n--------------------%n",signedInUser.getName());
//            System.out.println("1. Sign out");
//            System.out.println("2. Change password");
//            System.out.println("3. List of available services");
//            System.out.println("4. Add a service");
//            System.out.println("5. Approve registered expeerts");
//            System.out.println("6. Add or remove expert from services");
//            System.out.printf("%n--------------------%n");
//
//            System.out.println("Choose an item:\n");
//            String choice = input.next();
//            switch (choice){
//                case "1":{
//                    valid =false;
//                }//end of case 1
//                break;
//
//                case "2":{
//                    String newPassword = changePassword();
//
//                    if(customer.chnagePasword(signedInPerson.getId(), newPassword))
//                        signedInPerson.setPassword(newPassword);
//                }//case 2
//                break;
//
//                case "3":{
//                    addToCart();
//                }
//                break;
//
//                case "4":{
//                    cart();
//                }
//                break;
//
//                case "5":{
//                    cart();
//                }
//                break;
//
//                case "6":{
//                    cart();
//                }
//                break;
//
//                default:
//                    System.out.println("Invalid choice");
//                    break;
//            }
//        }
//    }//end of clientAffairs

}
