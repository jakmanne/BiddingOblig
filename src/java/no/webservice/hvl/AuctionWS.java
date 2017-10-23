/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.webservice.hvl;

import Database.BidFacade;
import Database.ProductCalculations;
import Database.ProductFacade;
import Database.UserFacade;
import Entities.BidInstance;
import Entities.ProductInstance;
import Entities.UserInstance;
import Helpers.ValidateUserHelper;
import com.oracle.webservices.api.message.MessageContext;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import java.util.List; 
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import main.Publisher;

/**
 *
 * @author Jakob
 */
@RequestScoped
@WebService(serviceName = "AuctionWS")
@Stateless()
public class AuctionWS {


    @EJB
    ProductFacade productFinder123;
    
    @EJB
    UserFacade userFacade; 
    
    @EJB
    ProductFacade productFacade; 
    
    @EJB
    BidFacade bidFacade; 
    
    private boolean validUser;
    private UserInstance usertemp; 
    private ProductInstance producttemp;
    
    private Publisher topic = new Publisher();
  
    
    
    @EJB
    ProductCalculations calculations; 
    
   
    
    
    ValidateUserHelper validateCredentials = new ValidateUserHelper();
    
    private List<ProductInstance> products123; 

    @WebMethod(operationName = "getAuction")
    public ProductInstance[] getAuction() {
        
        //returns only active products that one can bid for. 
        //unable to just return the list because we get an infinite loop while converting the objects to XML. 
        //therefore we had to convert the list to an array.  
        products123 = productFinder123.findAllProducts(); 
        
        ProductInstance[] productArray = new ProductInstance[products123.size()];
        
        for(int i = 0; i<productArray.length; i++){
            
            ProductInstance temp = new ProductInstance(); 
            temp.setProductName(products123.get(i).getProductName());
            temp.setIsactive(true);
            temp.setFeatures(products123.get(i).getFeatures());
            temp.setSellerName(products123.get(i).getSeller().getUsername());
            if(products123.get(i).getCurrentBid() != null){
               temp.setHighestbid(products123.get(i).getCurrentBid().getAmount());
            }else{
                temp.setHighestbid(0);
            }
            productArray[i] = temp; 
        }
        
        return productArray; 
    }
 
    @WebMethod(operationName = "placeBid")
    public String placeBid(@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "productname") String productname, @WebParam(name = "amount") int amount) throws Exception {

        topic.activateTopic(productFacade.getFinishedAuction());
        
        validUser = validateCredentials.validation(userFacade.findAll(), username, password);

        BidInstance newbid = new BidInstance();

        usertemp = validateCredentials.validationUser(userFacade.findAll(), username, password);
             
        if (validUser) {

            if (productFacade.findProductsByName(productname) != null) {
                producttemp = productFacade.findProductsByName(productname).get(0);
            } else {
                return "Product does not exist";
            }

            if (calculations.calculateBid(producttemp, amount)) {
                newbid.setUser(usertemp);
                newbid.setAmount(amount);
                newbid.setProduct(producttemp);
                producttemp.setCurrentBid(newbid);
                productFacade.edit(producttemp);
                
                return "Bid has been successfully placed";
            } else {
                  return "Bid is too low";
            }
        }
        return "Not a valid user";

    }
    
    
}



