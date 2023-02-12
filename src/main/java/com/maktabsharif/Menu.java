package com.maktabsharif;

import com.maktabsharif.entity.*;
import com.maktabsharif.entity.enumeration.ExpertStatus;
import com.maktabsharif.entity.enumeration.OrderStatus;
import com.maktabsharif.entity.enumeration.UserRole;
import com.maktabsharif.service.OrderService;
import com.maktabsharif.service.ServicesService;
import com.maktabsharif.service.SuggestionsService;
import com.maktabsharif.service.UserService;
import com.maktabsharif.validation.Validators;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final User user = new User();
    private static final Services services = new Services();
    private static final Order order = new Order();
    private static final Suggestions suggestion = new Suggestions();

    private static final Validators validators = new Validators();

    private static final UserService userService = new UserService();
    private static final ServicesService servicesService = new ServicesService();
    private static final OrderService orderService = new OrderService();
    private static final SuggestionsService suggestionsService = new SuggestionsService();

    private static final Scanner input = new Scanner(System.in);
    private static User signedInUser = null;



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
                        if(signedInUser.getUserRole().equals(UserRole.CLIENT))
                            clientAffairs(signedInUser);
                        if(signedInUser.getUserRole().equals(UserRole.EXPERT))
                            expertAffairs(signedInUser);
                        if(signedInUser.getUsername().equals("admin"))
                            adminAffairs(signedInUser);
                    }
                }//case 1
                break;

                case "2": {
                    signUp();
                    if (signedInUser != null)
                        clientAffairs(signedInUser);
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
        if(continueConfirmation().equals("y")){
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
        User passwordChangingUser = signedInUser;

        String newPassword = null;
        System.out.println("Enter old password: ");
        String oldPassword = input.next();
        if(userService.findPasswordById(signedInUser.getId(), oldPassword)){
            System.out.println("New Password: ");
            newPassword = input.next();
            System.out.println("Enter new password again: ");
            String newPasswordConfirmation = input.next();
            passwordChangingUser.setPassword(userService.oldAndNewPasswordsMatchAndMeet(oldPassword, newPassword, newPasswordConfirmation));
            userService.update(passwordChangingUser, signedInUser.getId());
            if(userService.isPasswordChanged(signedInUser.getId(), newPassword))
                signedInUser.setPassword(newPassword);
        }
        else
            System.out.println("Incorrect password.\n");
    }//end of changePassword

    public static void availableServices(){
        servicesService.availableServices();
    }

    public static void availableServicesByCategory(){
        servicesService.availableServicesByCategory();
    }

    public static void availableServicesBySubcategory(Integer choice){
        servicesService.availableServicesBySubcategory(choice);
    }

    public static boolean existsService(String serviceTitle){
        if(servicesService.findByServiceTitle(serviceTitle) == null)
            return false;
        else
            return true;
    }


    public static void addService(){
        System.out.println("Available services:\n");
        availableServices();

        System.out.println("Are you going to add a service? (Y/N) ");
        if(continueConfirmation().equals("y")){
            System.out.println("Service title: ");
            String newServiceTitle = input.nextLine().toLowerCase();
            newServiceTitle = input.nextLine().toLowerCase();

            String continueConfirmation = "y";
           // checking if the service exists.
           while(existsService(newServiceTitle)){
               System.out.println("Do you want to continue? (Y/N) ");
               if(continueConfirmation().equals("y")){
                   System.out.println("Service title: ");
                   newServiceTitle = input.nextLine().toLowerCase();
               }//if(wantToContinue.equals("y"))
               else{
                   System.out.println("No services added.\n");
                   continueConfirmation = "n";
                   break;
               }

           }// while(existsService(newServiceTitle))

            if(continueConfirmation.equals("y")){
                System.out.println("Is this service a subcategory of other service? (Y/N) ");
                    if(continueConfirmation().equals("y")){
                        System.out.println("1. New category" +
                                         "\n2. Available categories");
                        String choice = input.next();
                        switch (choice){
                            case "1":{
                                System.out.println("Category: ");
                                String parentServiceTitle = input.nextLine().toLowerCase();
                                parentServiceTitle = input.nextLine().toLowerCase();

                                services.setServiceTitle(parentServiceTitle);
                                services.setBasePrice(null);
                                services.setParentId(null);
                                services.setDescription(null);
                                servicesService.addService(services);

                                services.setServiceTitle(newServiceTitle);
                                services.setParentId(servicesService.findByServiceTitle(parentServiceTitle).getId());
                            }
                            break;

                            case "2":{
                                System.out.println("Subcategory of which service is it?");
                                String parentServiceTitle = input.nextLine().toLowerCase();
                                parentServiceTitle = input.nextLine().toLowerCase();

                                // checking if the service exists.
                                while(!existsService(parentServiceTitle) || (existsService(parentServiceTitle) && servicesService.findByServiceTitle(parentServiceTitle).getParentId() != null)){
                                        System.out.println("Subcategory of which service is it?");
                                        parentServiceTitle = input.nextLine().toLowerCase();
                                        parentServiceTitle = input.nextLine().toLowerCase();
                                }//while(!existsService(parentServiceTitle)) checking if the service exists.

                                services.setServiceTitle(newServiceTitle);
                                services.setParentId(servicesService.findByServiceTitle(parentServiceTitle).getId());
                            }
                            break;

                            default:
                                System.out.println("Invalid choice");
                                break;
                        }// determining category

                        System.out.println("\nBase Price? ");
                        services.setBasePrice(input.nextDouble());

                        System.out.println("\nAdd description for this service:");
                        services.setDescription(input.nextLine());
                        services.setDescription(input.nextLine());

                        servicesService.addService(services);

                    }// if(wantToContinue.equals("y")) it's subcategory and want to continue to add parent service
                else{
                        services.setServiceTitle(newServiceTitle);
                        services.setBasePrice(null);
                        services.setParentId(null);
                        services.setDescription(null);
                        servicesService.addService(services);
                    }
            } // willing to add patent service

        }//if(wantToAddService.equals("y"))

    }//public static void addService()


    public static User findSpecificApprovedExpert(){
        userService.findApprovedExperts();
        System.out.println("Select expert by his/her ID: ");
        return userService.findById(input.nextLong());
    }


    public static Services findSpecificService(){
        servicesService.availableServicesById();
        System.out.println("Select service by ID: ");
        return servicesService.findById(input.nextLong());
    }


    public static void addExpertToServices(){
        Services specificService = findSpecificService();

        User specificExpert = findSpecificApprovedExpert();
        List<Services> specificExpertServices = specificExpert.getServices();
        int existsBefore = 0;
        for (Services service:
             specificExpertServices) {
            if(service.getId() == specificService.getId())
                ++existsBefore;
            break;
        }
        if(existsBefore == 0) {
            specificExpertServices.add(specificService);
            specificExpert.setServices(specificExpertServices);
            userService.update(specificExpert, specificExpert.getId());
        }
        else
            System.out.println("This service was assigned to the user before.");

    }


    public static void removeExpertFromServices(){
        User specificExpert = findSpecificApprovedExpert();
        List<Services> specificExpertServices = specificExpert.getServices();

        System.out.println("Services assigned to this user:");
        System.out.println("ID\tTitle");
        for(Services service: specificExpertServices){
            System.out.println(service.getId() + "\t" + service.getServiceTitle());
        }

        System.out.println("Select service by ID:");
        Long removedServicesId = input.nextLong();
        specificExpertServices.removeIf(t -> t.getId() == removedServicesId);
        specificExpert.setServices(specificExpertServices);

        userService.update(specificExpert, specificExpert.getId());
    }


    public static void editService(){
        Services underEditService = findSpecificService();
        System.out.println("Are you going to edit service title? (Y/N) ");
        if(continueConfirmation().equals("y")){
            System.out.println("New Service title: ");
            underEditService.setServiceTitle(input.nextLine());
            underEditService.setServiceTitle(input.nextLine());
        }

        System.out.println("Are you going to edit service base price? (Y/N) ");
        if(continueConfirmation().equals("y")){
            System.out.println("New base price: ");
            underEditService.setBasePrice(input.nextDouble());
        }

        System.out.println("Are you going to edit service description? (Y/N) ");
        if(continueConfirmation().equals("y")){
            System.out.println("New description: ");
            underEditService.setDescription(input.nextLine());
            underEditService.setDescription(input.nextLine());
        }

        servicesService.update(underEditService, underEditService.getId());

    }


    public static void approveNewRegisteredExperts(){
        userService.findNewExperts();
        System.out.println("Select expert by his/her ID: ");
        Long newExpertId = input.nextLong();
        User newExpert = userService.findById(newExpertId);
        newExpert.setExpertStatus(ExpertStatus.APPROVED);

        userService.update(newExpert, newExpertId);
    }


    public static void adminAffairs(User signedInUser){

        boolean valid = true;
        while (valid == true){
            System.out.println("\n-------------------- Welcome dear " + signedInUser.getName() + " " + signedInUser.getFamilyName() + " --------------------"
                        + "\n1. Sign out"
                        + "\n2. Add profile photo"
                        + "\n3. Change password"
                        + "\n4. List of available services"
                        + "\n5. Add a service"
                        + "\n6. Edit a service"
                        + "\n7. Approve new registered experts"
                        + "\n8. Add expert to services"
                        + "\n9. Remove expert from services"
                        + "\n10. List of orders"
                        + "\n-------------------------------------");

            System.out.println("Choose an item:");
            String choice = input.next();
            switch (choice){
                case "1":{
                    signedInUser = null;
                    valid =false;
                }//end of case 1
                break;
                case "2":{
                    System.out.println("Add photo: ");
                    String imagePath = input.next();
                    userService.addProfilePhoto(signedInUser, imagePath);
                }//end of case 1
                break;

                case "3":{
                 changePassword();
                }//case 2
                break;

                case "4":{
                    availableServices();
                }
                break;

                case "5":{
                    addService();
                }
                break;

                case "6":{
                    editService();
                }
                break;

                case "7":{
                    approveNewRegisteredExperts();
                }
                break;

                case "8":{
                    addExpertToServices();
                }
                break;

                case "9":{
                    removeExpertFromServices();
                }
                break;

                case "10":{
                    orderService.displayAllOrders();                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }//end of adminAffairs


    public static void makeSuggestions(){
        System.out.println("Are you willing to suggest on orders? (Y/N)");
        while(continueConfirmation().equals("y")){
            System.out.println("Select order by ID: ");
            Long underSuggestionOrderId = input.nextLong();
            Order underSuggestionOrder = orderService.findOrdersRelatedToExpertById(underSuggestionOrderId, signedInUser.getServices());

            if(underSuggestionOrder != null){
                System.out.println("Your suggestion: ");
                String expertSuggestion = input.nextLine();
                expertSuggestion = input.nextLine();
                suggestion.setExpertSuggestion(expertSuggestion);

                suggestion.setExpertSuggestionDate(new Timestamp(new Date().getTime()));

                System.out.println("Your suggested price: ");
                Double expertSuggestedPrice = input.nextDouble();
                suggestion.setExpertSuggestedPrice(expertSuggestedPrice);

                System.out.println("When do you suggest to start the work?" +
                        "\nPlease enter your date in format \"yyyy-mm-dd hh:mm:ss\"."
                );
                String startDateByClient = input.nextLine();
                startDateByClient = input.nextLine();
                suggestion.setStartDateByExpert(Timestamp.valueOf(startDateByClient));

                System.out.println("How many days will this work take to be done?");
                suggestion.setOrderDoDuration(input.nextLong());

                suggestion.setOrder(underSuggestionOrder);

                suggestion.setUser(signedInUser);
                suggestion.setSelecetd(false);

                if(suggestionsService.saveSuggestion(suggestion)){
                    underSuggestionOrder.setOrderStatus(OrderStatus.AWAITING_EXPERT_SELECTION);
                    orderService.update(underSuggestionOrder, underSuggestionOrderId);
                }

            }
            else
                System.out.println("Order with this ID is not for you.");

            System.out.println("Do you want to suggest on any other order? (Y/N)");
        }// you're willing to make suggestions
    }// end of makeSuggestions()


    public static void expertAffairs(User signedInUser){

        boolean valid = true;
        while (valid == true){
            System.out.println("\n-------------------- Welcome dear " + signedInUser.getName() + " " + signedInUser.getFamilyName() + " --------------------"
                    + "\n1. Sign out"
                    + "\n2. Add profile photo"
                    + "\n3. Change password"
                    + "\n4. List of available orders"
                    + "\n5. My tasks(orders I've selected)"
                    + "\n-------------------------------------");

            System.out.println("Choose an item:");
            String choice = input.next();
            switch (choice){
                case "1":{
                    signedInUser = null;
                    valid =false;
                }//end of case 1
                break;

                case "2":{
                    System.out.println("Add photo: ");
                    String imagePath = input.next();
                    userService.addProfilePhoto(signedInUser, imagePath);
                }//end of case 1
                break;

                case "3":{
                    changePassword();
                }//case 2
                break;

                case "4":{
                    orderService.displayOrdersRelatedToExpert(signedInUser.getServices());
                    makeSuggestions();
                }
                break;

                case "5":{
                    orderService.displayOrdersWhoseExpertIsSelected(signedInUser.getId());
                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }//end of expertAffairs


    public static void makeOrder(){

        System.out.println(new Timestamp(new Date().getTime()));

        availableServicesByCategory();

        System.out.println("\nAre you willing to see subcategories? (Y/N)");
        if (continueConfirmation().equals("y")){
            System.out.println("Enter the service number.");
            availableServicesBySubcategory(input.nextInt());
        }

        System.out.println("Are you willing to make an order? (Y/N)");
        if(continueConfirmation().equals("y")){

            order.setUser(signedInUser);

            System.out.println("Enter the service name.");
            String orderServiceTitle = input.nextLine();
            orderServiceTitle = input.nextLine();
            while (!existsService(orderServiceTitle)){
                System.out.println("Enter the service name correctly.");
                orderServiceTitle = input.nextLine();
                orderServiceTitle = input.nextLine();
            }
            order.setServices(servicesService.findByServiceTitle(orderServiceTitle));

            System.out.println("\nAdd a description about your work because of which you ordered this service.");
            String description = input.nextLine();
            description = input.nextLine();
            order.setWorkDescription(description);

            System.out.println("Your suggested price:");
            order.setClientSuggestedPrice(input.nextDouble());

//            order.setOrderDate(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")));
            order.setOrderDate(new Timestamp(new Date().getTime()));

            System.out.println("When do want your work to be get started?" +
                    "\nPlease enter your date in format \"yyyy-mm-dd hh:mm:ss\"."
            );
            String startDateByClient = input.nextLine();
             startDateByClient = input.nextLine();
             order.setStartDateByClient(Timestamp.valueOf(startDateByClient));

            System.out.println("Address: ");
            String address = input.nextLine();
            address = input.nextLine();
            order.setAddress(address);

            order.setOrderStatus(OrderStatus.AWAITING_EXPERTS_SUGGESTION);

            orderService.saveOrder(order);

        }// end of if(continueConfirmation().equals("y")) you wanted to make an order

    }// end of makeOrder


    public static void myOrdersMenu(){
        boolean valid = true;
        while (valid == true){
            System.out.println("\n-------------------- Welcome dear " + signedInUser.getName() + " " + signedInUser.getFamilyName() + " --------------------"
                    + "\n1. See suggestions and select expert for your order"
                    + "\n2. Change order status to 'started'"
                    + "\n3. Change order status to 'done'"
                    + "\n4. Change order status to 'paid'"
                    + "\n5. Exit"
                    + "\n-------------------------------------");

            System.out.println("Choose an item:");
            String choice = input.next();
            switch (choice){
                case "1":{
                    seeSuggestionsAndSelectExpert();
                }//end of case 1
                break;

                case "2":{
                    changeOrderStatusToStarted();
                }//case 2
                break;

                case "3":{
                    changeOrderStatusToDone();
                }
                break;

                case "4":{
                    changeOrderStatusToPaid();
                }
                break;

                case "5":{
                    valid = false;
                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }


    public static void seeSuggestionsAndSelectExpert(){

        System.out.println("Select order by ID: ");
        Long orderId = input.nextLong();
        Order underExpertSelectionOrder = orderService.findOrderOfClientById(signedInUser.getId(), orderId);

        if(underExpertSelectionOrder != null){
            suggestionsService.displayOrderSuggestions(orderId);
        }
        else
            System.out.println("Order with this ID is not for you.");

        System.out.println("Do you want to select ang suggestion? (Y/N)");
        if(continueConfirmation().equals("y")){
            System.out.println("Select suggestion by ID: ");
            Long selectedSuggestionId = input.nextLong();
            Suggestions selectedSuggestion = suggestionsService.findSpecificOrderSuggestionsById(orderId);
            if (selectedSuggestion != null){
                underExpertSelectionOrder.setOrderStatus(OrderStatus.AWAITING_EXPERT_TO_COME_TO_YOUR_PLACE);
                underExpertSelectionOrder.setSelectedSuggestionId(selectedSuggestionId);
                orderService.update(underExpertSelectionOrder, orderId);

                selectedSuggestion.setSelecetd(true);
                suggestionsService.update(selectedSuggestion, selectedSuggestionId);
            }
            else
                System.out.println("Suggestion with this ID has not been registered for this order.");

        }
    }//end of seeSuggestionsAndSelectExpert()


    public static void changeOrderStatusToStarted() {

        System.out.println("Select order by ID: ");
        Long orderId = input.nextLong();
        Order underChangeStatusToStarted = orderService.findOrderOfClientById(signedInUser.getId(), orderId);

        if (underChangeStatusToStarted != null) {
            underChangeStatusToStarted.setOrderStatus(OrderStatus.STARTED);
            orderService.update(underChangeStatusToStarted, orderId);
        } else
            System.out.println("Order with this ID is not for you.");
    }


    public static void changeOrderStatusToDone() {

        System.out.println("Select order by ID: ");
        Long orderId = input.nextLong();
        Order underChangeStatusToDone = orderService.findOrderOfClientById(signedInUser.getId(), orderId);

        if (underChangeStatusToDone != null) {
            underChangeStatusToDone.setOrderStatus(OrderStatus.DONE);
            orderService.update(underChangeStatusToDone, orderId);
        } else
            System.out.println("Order with this ID is not for you.");
    }


    public static void changeOrderStatusToPaid() {

        System.out.println("Select order by ID: ");
        Long orderId = input.nextLong();
        Order underChangeStatusToDone = orderService.findOrderOfClientById(signedInUser.getId(), orderId);

        if (underChangeStatusToDone != null) {
            //todo payment procedure. Make transaction and save it wallet.
            underChangeStatusToDone.setOrderStatus(OrderStatus.PAID);
            orderService.update(underChangeStatusToDone, orderId);
        } else
            System.out.println("Order with this ID is not for you.");
    }



    public static void clientAffairs(User signedInUser){

        boolean valid = true;
        while (valid == true){
            System.out.println("\n-------------------- Welcome dear " + signedInUser.getName() + " " + signedInUser.getFamilyName() + " --------------------"
                    + "\n1. Sign out"
                    + "\n2. Add profile photo"
                    + "\n3. Change password"
                    + "\n4. Make an order"
                    + "\n5. My orders"
                    + "\n-------------------------------------");

            System.out.println("Choose an item:");
            String choice = input.next();
            switch (choice){
                case "1":{
                    signedInUser = null;
                    valid =false;
                }//end of case 1
                break;

                case "2":{
                    System.out.println("Add photo: ");
                    String imagePath = input.next();
                    userService.addProfilePhoto(signedInUser, imagePath);
                }//end of case 1
                break;

                case "3":{
                    changePassword();
                }//case 2
                break;

                case "4":{
                      makeOrder();
                }
                break;

                case "5":{
                      orderService.displayOrdersOfClient(signedInUser.getId());
                      myOrdersMenu();
                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }//end of clientAffairs


    public static String continueConfirmation() {
        String continueConfirmation = input.next().toLowerCase();
        while (!(continueConfirmation.equals("y") || continueConfirmation.equals("n"))) {
            System.out.println("Invalid answer.");
            System.out.println("Are you going to continue? (Y/N) ");
            continueConfirmation = input.next();
        }
        return continueConfirmation;
    }// end of continueConfirmation()

}
