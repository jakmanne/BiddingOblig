/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Database.BidFacade;
import Database.ProductCalculations;
import Database.ProductFacade;
import Entities.BidInstance;
import Entities.ProductInstance;
import Entities.UserInstance;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jakob
 */
@ManagedBean
@ViewScoped
public class BidHandler implements Serializable{

  
    
    private ProductInstance product; 
    private double userRating; 
    private int currentBid; 
    private UserInstance user; 
    
   


    
    
    public BidHandler() {
        
        product = new ProductInstance(); 
        
    }
    @EJB
    ProductFacade productDatabase; 
    
    @EJB
    BidFacade bidDatabase; 
    
    @EJB
    ProductCalculations calculations; 
    
    
    public void findProductById() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getQueryString();

        String[] split = url.split("=");
        String productId = split[1];
        System.out.println(productId);
        Long id = Long.parseLong(productId);

        product = productDatabase.findProductById(id);
        userRating = calculations.calculateRating(product.getSeller().getRating()); 


    }
    
    public void placeBid(){
        
        BidInstance newBid = new BidInstance(); 
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        LoginHandler sessionobject = (LoginHandler) httpSession.getAttribute("loginHandler"); 
        user  = sessionobject.getSessionuser(); 
        
        if(calculations.calculateBid(product, currentBid)){
            
            newBid.setProduct(product);
            newBid.setAmount(currentBid);
            newBid.setUser(user);
           // bidDatabase.create(newBid);
                        
            FacesMessage msg = new FacesMessage("Bid succesfully placed");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
           
            
        }else{
            
            FacesMessage msg = new FacesMessage("Your bid is too low");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }
        
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }
    

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
       public ProductInstance getProduct() {
        return product;
    }

    public void setProduct(ProductInstance product) {
        this.product = product;
    }
 
    
    
    
}