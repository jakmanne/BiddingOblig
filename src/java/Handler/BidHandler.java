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
    private  BidInstance newBid; 
    private double userRating; 
    private int currentBid; 
    private UserInstance user; 
    private Long endTime; 
    
    public BidHandler() {
        
        product = new ProductInstance(); 
        newBid = new BidInstance(); 
        
    }
    @EJB
    ProductFacade productDatabase; 
    
    @EJB
    BidFacade bidDatabase; 
    
    @EJB
    ProductCalculations calculations; 
    
    //Method for the user to find a product by ID. This method is loaded every time the placebid.xhtml site is loaded. 
    //It gets the URL parameter and quires the database for the ID and calculates the time and displays it. It also gets the rating for the user. 
    public void findProductById() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getQueryString();
        
        if(url == null){
            
            FacesMessage msg = new FacesMessage("Dont refresh the page, database cant handle request. please go to index and try again");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg); 
            
        }else{
            
        String[] split = url.split("=");
        String productId = split[1];
        Long id = Long.parseLong(productId);

        product = productDatabase.findProductById(id);
        userRating = calculations.calculateNewRating(product.getSeller().getRating()); 
        endTime = product.getTimestamp().getTime();
        
        }  

    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    
    //This method is invoked when the user places a bid. It checks if the bid is valid and updates the database with the new bid. 
    public void placeBid(){
        
       
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        LoginHandler sessionobject = (LoginHandler) httpSession.getAttribute("loginHandler"); 
        user  = sessionobject.getSessionuser(); 
        
        if(calculations.calculateBid(product, currentBid)){
            
            newBid.setProduct(product);
            newBid.setAmount(currentBid);
            newBid.setUser(user);
            product.setCurrentBid(newBid);  
            productDatabase.edit(product);
            
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
