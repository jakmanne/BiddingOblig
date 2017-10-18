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
    public String getAuction() {
        
        
        products123 = productFinder123.findAllProducts(); 
        System.out.println(products123.get(0).getProductName());
        
        //ProductInstance[] companies = new ProductInstance[products123.size()];
        
       //ProductInstance haha = new ProductInstance();
       
        //companies = products123.toArray(companies); 

     

        //companies[1] = haha2;
        return "hei";
     
          //return temp.toArray(new ProductInstance[temp.size()]); 
        }
 
}
