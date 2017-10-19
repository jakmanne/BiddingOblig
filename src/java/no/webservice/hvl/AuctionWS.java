/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.webservice.hvl;

import Database.ProductFacade;
import Entities.ProductInstance;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import java.util.List; 
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

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
 
}
