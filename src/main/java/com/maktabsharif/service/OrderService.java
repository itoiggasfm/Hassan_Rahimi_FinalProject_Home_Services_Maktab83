package com.maktabsharif.service;

import com.maktabsharif.entity.*;
//import com.maktabsharif.service.*;
import com.maktabsharif.entity.enumeration.OrderStatus;
import com.maktabsharif.repository.OrderRepository;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final SuggestionsService suggestionsService;

    public OrderService(){
        this.orderRepository = new OrderRepository();
        this.userService = new UserService();
        this.suggestionsService = new SuggestionsService();
    }

    private static final Scanner input = new Scanner(System.in);


    public boolean saveOrder(Order order) {

        while(order.getClientSuggestedPrice()<order.getServices().getBasePrice()){
            System.out.println("Our base price for this service is: " + order.getServices().getBasePrice() +
                    "\nYour suggested price is less." +
                    "\nIf you're going to make order of this service you have to suggest more than our base price.");
            System.out.println("Your suggested price: ");
            order.setClientSuggestedPrice(input.nextDouble());
        }

        while (order.getStartDateByClient().before(new Timestamp(new Date().getTime()))){
            System.out.println("Your suggested start date for your order is: " + order.getStartDateByClient() +
                    "\nYour suggested date is passed." +
                    "\nIf you're going to make order of this service you have to suggest a date after now."
            );
            System.out.println("When do you want your work to be got started?" +
                    "\nPlease enter your date in format \"yyyy-mm-dd hh:mm:ss\"."
            );
            System.out.println("Your suggested date to start: ");
            String startDateByClient = input.nextLine();
            startDateByClient = input.nextLine();

            order.setStartDateByClient(Timestamp.valueOf(startDateByClient));
        }
        if(orderRepository.create(order))
            return true;
        else
            return  false;
    }// end of saveOrder

    public void update(Order order, Long orderId){
        orderRepository.update(order, orderId);
    }

    public Order findById(Long id){
        return orderRepository.findById(id);
    }


    public List<Order> findAll(){
        return orderRepository.findAll();
    }


    public void displayAllOrders(){
        List<Order> orders = findAll();

        if(!orders.isEmpty()){
            System.out.printf("\nOrder ID     Ordered service    Description                        Order date               Start date                 Ordered by     Address          Base price of service     Suggested price by client     Order status");
            System.out.printf("\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (Order order: orders
            ) {
                System.out.printf("\n%-13d%-19s%-36s%-25s%-26s%-7s%-8s%-17s%-26.2f%-30.2f%s\n\n", order.getId(), order.getServices().getServiceTitle(), order.getWorkDescription(), order.getOrderDate()/*DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss").format()*/ , order.getStartDateByClient(), order.getUser().getName(), order.getUser().getFamilyName(), order.getAddress(), order.getServices().getBasePrice(), order.getClientSuggestedPrice(), order.getOrderStatus());
            }
        }
        else
            System.out.println("No orders yet");


    }


    public List<Order> ordersWhoseExpertIsSelected(){
        List<Order> orders = findAll();
        List<Order> ordersWhoseExpertIsSelected = new ArrayList<>();

        for(Order order: orders){
            if(order.getSelectedSuggestionId() != null)
                ordersWhoseExpertIsSelected.add(order);
        }
        return ordersWhoseExpertIsSelected;
    }

    public void displayOrdersWhoseExpertIsSelected(Long expertId){
        List<Order> ordersWhoseExpertIsSelected =  ordersWhoseExpertIsSelected();

        if(!ordersWhoseExpertIsSelected.isEmpty()){
            System.out.printf("\nOrder ID     Ordered service    Description                        Order date               Start date by client       Ordered by     Address          Base price of service     Suggested price by client     Order status                           Suggested price by you      Suggested date by you    Start date by you         Do duration");
            System.out.printf("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (Order order: ordersWhoseExpertIsSelected) {
                if(suggestionsService.findById(order.getSelectedSuggestionId()).getUser().getId() == expertId){
                    Suggestions selectedSuggestion = suggestionsService.findById(order.getSelectedSuggestionId());

                    System.out.printf("\n%-13d%-19s%-36s%-25s%-26s%-7s%-8s%-17s%-26.2f%-30.2f%-40s%-27.2f%-26s%-26s%d\n\n",
                            order.getId(),
                            order.getServices().getServiceTitle(),
                            order.getWorkDescription(),
                            order.getOrderDate(),
                            order.getStartDateByClient(),
                            order.getUser().getName(),
                            order.getUser().getFamilyName(),
                            order.getAddress(),
                            order.getServices().getBasePrice(),
                            order.getClientSuggestedPrice(),
                            order.getOrderStatus(),
                            selectedSuggestion.getExpertSuggestedPrice(),
                            selectedSuggestion.getExpertSuggestionDate(),
                            selectedSuggestion.getStartDateByExpert(),
                            selectedSuggestion.getOrderDoDuration()
                    );
                }
            }
        }
        else
            System.out.println("No suggestion selected yet");
    }


    public List<Order> ordersRelatedToExpert(List<Services> expertServices){
        List<Order> orders = findAll();
        List<Order> ordersRelatedToExpert = new ArrayList<>();

        if(!orders.isEmpty()){
            for(Services service: expertServices){
                for (Order order: orders) {
                    if(service.getId() == order.getServices().getId())
                        ordersRelatedToExpert.add(order);
                }
            }

        }
        return ordersRelatedToExpert;
    }


    public void displayOrdersRelatedToExpert(List<Services> expertServices){
        List<Order> ordersRelatedToExpert = ordersRelatedToExpert(expertServices);

        if(!ordersRelatedToExpert.isEmpty()){
            System.out.printf("\nOrder ID     Ordered service    Description                        Order date               Start date                 Ordered by     Address          Base price of service     Suggested price by client     Order status");
            System.out.printf("\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                for (Order order: ordersRelatedToExpert) {
                    if(order.getOrderStatus().equals(OrderStatus.AWAITING_EXPERT_SELECTION) || order.getOrderStatus().equals(OrderStatus.AWAITING_EXPERTS_SUGGESTION))
                        System.out.printf("\n%-13d%-19s%-36s%-25s%-26s%-7s%-8s%-17s%-26.2f%-30.2f%s\n\n",
                                order.getId(),
                                order.getServices().getServiceTitle(),
                                order.getWorkDescription(),
                                order.getOrderDate(),
                                order.getStartDateByClient(),
                                order.getUser().getName(),
                                order.getUser().getFamilyName(),
                                order.getAddress(),
                                order.getServices().getBasePrice(),
                                order.getClientSuggestedPrice(),
                                order.getOrderStatus()
                        );
                }
        }
        else
            System.out.println("No orders yet");

    }


    public Order findOrdersRelatedToExpertById(Long id, List<Services> expertServices){
        List<Order> ordersRelatedToExpert = ordersRelatedToExpert(expertServices);
        Order orderFound = null;

        if(!ordersRelatedToExpert.isEmpty()){
            for(Order order: ordersRelatedToExpert){
                if(order.getId() == id){
                    orderFound = order;
                    break;
                }
            }
        }
            return orderFound;
    }


    public void displayOrdersOfClient(Long clientId) {
        List<Order> orders = findAll();

        if (!orders.isEmpty()) {

            System.out.printf("\nOrder ID     Ordered service    Description                        Order date               Start date                 Ordered by     Address          Base price of service     Suggested price by you     Order status                            Selected expert");
            System.out.printf("\n-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            for (Order order : orders)
                if (clientId == order.getUser().getId())
                        System.out.printf("\n%-13d%-19s%-36s%-25s%-26s%-7s%-8s%-17s%-26.2f%-27.2f%-40s%s %s\n\n",
                                order.getId(),
                                order.getServices().getServiceTitle(),
                                order.getWorkDescription(),
                                order.getOrderDate(),
                                order.getStartDateByClient(),
                                order.getUser().getName(),
                                order.getUser().getFamilyName(),
                                order.getAddress(),
                                order.getServices().getBasePrice(),
                                order.getClientSuggestedPrice(),
                                order.getOrderStatus(),
                                (order.getSelectedSuggestionId() != null)?suggestionsService.findById(order.getSelectedSuggestionId()).getUser().getName():"",
                                (order.getSelectedSuggestionId() != null)?suggestionsService.findById(order.getSelectedSuggestionId()).getUser().getFamilyName():""

                        );
        }// end of   if (!orders.isEmpty())
    }// end of  public void displayOrdersOfClient(Long clientId)




    public Order findOrderOfClientById(Long clientId, Long orderId){
        List<Order> clientOrders = ordersOfClient(clientId);
        Order orderFound = null;

        if(!clientOrders.isEmpty()){
            for (Order order: clientOrders) {
                if(orderId == order.getId()){
                    orderFound = order;
                    break;
                }

            }
        }
        return orderFound;

    }


    public List<Order> ordersOfClient(Long clientId){
        List<Order> orders = findAll();
        List<Order> clientOrders = new ArrayList<>();

        if(!orders.isEmpty()){
            for (Order order: orders) {
                if(clientId == order.getUser().getId())
                    clientOrders.add(order);
            }
        }
        return clientOrders;
    }

}
