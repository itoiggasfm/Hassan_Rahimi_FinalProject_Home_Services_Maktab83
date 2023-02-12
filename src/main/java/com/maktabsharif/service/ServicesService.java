package com.maktabsharif.service;

import com.maktabsharif.entity.Services;
import com.maktabsharif.repository.ServicesRepository;
import com.maktabsharif.repository.UserRepository;

import java.util.List;
import java.util.Scanner;

public class ServicesService {

    private static final Scanner input = new Scanner(System.in);
    private final ServicesRepository servicesRepository;
    public ServicesService(){
        this.servicesRepository = new ServicesRepository();
    }


    public void addService(Services services){
        servicesRepository.create(services);
    }


    public Services findById(Long id){
        return servicesRepository.findById(id);
    }

    public List<Services> findAll(){
        return servicesRepository.findAll();
    }

    public void availableServicesById(){
        List<Services> servicesList = findAll();

        for(Services service: servicesList){
            if(service.getParentId() == null){
                System.out.println(service.getId() + ". " + service.getServiceTitle());
                for(Services subService: servicesList){
                    if(subService.getParentId() == service.getId())
                        System.out.printf("%-3d%-10s%-10.2f%s\n", subService.getId(), subService.getServiceTitle(), subService.getBasePrice(), subService.getDescription());
                }
            }
        }
    }// end of availableServicesById()

    public void availableServicesByCategory(){
        List<Services> servicesList = findAll();
        int i=0;
        for(Services service: servicesList){
            if(service.getParentId() == null){
                System.out.println((++i) + ". " + service.getServiceTitle());
            }
        }

    }//end of availableServicesByCategory()


    public void availableServicesBySubcategory(Integer choice){
        List<Services> servicesList = findAll();
        int counter = 0;
        int row = 0;
        for(Services service: servicesList){
            if(service.getParentId() == null)
                ++counter;
            if(counter == choice){
                for(Services subService: servicesList){
                    if(subService.getParentId() == service.getId())
                        System.out.printf("%d.%s\t\t%.2f\t\t%s\n", ++row, subService.getServiceTitle(), subService.getBasePrice(), subService.getDescription());
                }
            }
        }

    }//end of availableServicesBySubcategory()

    public void  availableServices(){
        List<Services> servicesList = findAll();
        int i=0;
        for(Services service: servicesList){
            int j=0;
            if(service.getParentId() == null){
                System.out.println((++i) + ". " + service.getServiceTitle());
                for(Services subService: servicesList){
                    if(subService.getParentId() == service.getId())
                        System.out.println("\t" + i + "." + (++j) + ". " + subService.getServiceTitle() + "\t" + subService.getBasePrice() + "\t" + subService.getDescription());
                }
            }
        }
    }//end of availableServices()

    public Services findByServiceTitle(String serviceTitle){
        Services service = servicesRepository.findByServiceTitle(serviceTitle);
        if(service != null){
            if(service.getParentId() == null){
                System.out.printf("%s category of service already exists.%n", serviceTitle);
                return service;
            }
            else{
                System.out.printf("%s already exists as a subcategory of %s.", serviceTitle, servicesRepository.findById(service.getParentId()).getServiceTitle());
                return service;
            }
        }
        else
            return null;

    }

    public void update(Services services, Long id){
        servicesRepository.update(services, id);
    }



}//end of class ServicesService
