package com.maktabsharif.service;

import com.maktabsharif.entity.Order;
import com.maktabsharif.entity.Suggestions;
import com.maktabsharif.repository.OrderRepository;
import com.maktabsharif.repository.SuggestionsRepository;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class SuggestionsService {

    private final SuggestionsRepository suggestionsRepository;

    public SuggestionsService(){
        this.suggestionsRepository = new SuggestionsRepository();
    }

    private static final Scanner input = new Scanner(System.in);

    public boolean saveSuggestion(Suggestions suggestion){

        while(suggestion.getExpertSuggestedPrice()<suggestion.getOrder().getClientSuggestedPrice()){
            System.out.println("Client's suggested price for this service is: " + suggestion.getOrder().getClientSuggestedPrice() +
                    "\nYour suggested price has to be more than client's suggested price.");
            System.out.println("Your suggested price: ");
            suggestion.setExpertSuggestedPrice(input.nextDouble());
        }

        while (suggestion.getStartDateByExpert().before(suggestion.getOrder().getStartDateByClient())){
            System.out.println("Your suggested start date for your order is: " + suggestion.getStartDateByExpert() +
                    "\nYour suggested date has to be after the date suggested by client: " + suggestion.getOrder().getStartDateByClient()
            );
            System.out.println("When do you want to start the work?" +
                    "\nPlease enter your date in format \"yyyy-mm-dd hh:mm:ss\"."
            );
            System.out.println("Your suggested date to start: ");
            String startDateByClient = input.nextLine();
            startDateByClient = input.nextLine();

           suggestion.setStartDateByExpert(Timestamp.valueOf(startDateByClient));
        }

       if(suggestionsRepository.create(suggestion))
           return true;
       else
           return false;
    }


    public Suggestions findById(Long id){
        return suggestionsRepository.findById(id);
    }

    public  void update(Suggestions suggestions, Long id){
        suggestionsRepository.update(suggestions, id);
    }


    public List<Suggestions> findAll(){
        return suggestionsRepository.findAll();
    }


    public List<Suggestions> specificOrderSuggestions(Long id){
        List<Suggestions> suggestions = findAll();
        List<Suggestions> orderSuggestions = new ArrayList<>();

        if(!suggestions.isEmpty()){
            for(Suggestions suggestion: suggestions){
                if(suggestion.getOrder().getId() == id)
                    orderSuggestions.add(suggestion);
            }

        }
        return orderSuggestions;
    }


    public Suggestions findSpecificOrderSuggestionsById(Long id){
        List<Suggestions> orderSuggestions = specificOrderSuggestions(id);
        Suggestions suggestionFound = null;

        if(!orderSuggestions.isEmpty()){
            for(Suggestions suggestion: orderSuggestions){
                if(suggestion.getOrder().getId() == id)
                    suggestionFound = suggestion;
            }
        }
        return suggestionFound;

    }

    public void displayOrderSuggestions(Long id){
        List<Suggestions> orderSuggestions = specificOrderSuggestions(id);

        if(!orderSuggestions.isEmpty()){
           List<Suggestions> sortedOrderSuggestions = orderSuggestions.stream()
                   .sorted(Comparator.comparingDouble(Suggestions::getExpertSuggestedPrice))
                    .collect(Collectors.toList());

            System.out.printf("\nSelected     Suggestion ID     Suggested by    Suggestion date            Suggested price     Suggestion                    Suggested date to start the work     Suggested time to do the work(days)");
            System.out.printf("\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for(Suggestions suggestion: sortedOrderSuggestions) {
                System.out.printf("\n%-13s%-18d%-7s %-8s%-27s%-20.2f%-30s%-37s%-10d\n\n", suggestion.getSelecetd()?"\u2714":"", suggestion.getId(), suggestion.getUser().getName(), suggestion.getUser().getFamilyName(), suggestion.getExpertSuggestionDate(), suggestion.getExpertSuggestedPrice(), suggestion.getExpertSuggestion(), suggestion.getStartDateByExpert(), suggestion.getOrderDoDuration());
            }
        }
        else
            System.out.println("No suggestion yet");


    }

}
