/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Database.ProductFacade;
import Entities.ProductInstance;
import Entities.UserInstance;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Jakob
 */
@ManagedBean
@RequestScoped
public class ProductHandler implements Serializable  {

    private ProductInstance product; 
    private String dateString; 

    private UserInstance user; 
    
    @EJB
    ProductFacade productCreator; 

    public ProductHandler() {
        
       this.product = new ProductInstance(); 
    }
    
   public void createProduct() throws ParseException{
       
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        LoginHandler sessionobject = (LoginHandler) httpSession.getAttribute("loginHandler"); 
        user  = sessionobject.getSessionuser(); 
        
        if(user!=null){
            
            product.setSeller(user);
            product.setIsactive(true);
            product.setIspurchased(false);
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, Integer.parseInt(dateString));
            Date newDate = now.getTime();
            product.setTimestamp(newDate);
            productCreator.create(product);       
            FacesMessage msg = new FacesMessage("Product succesfully created");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg); 
        }else{
            
            FacesMessage msg = new FacesMessage("ERROR! Contact system administrator");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);                             
        }            
   }
   
      public UserInstance getUser() {
        return user;
    }

    public void setUser(UserInstance user) {
        this.user = user;
    }
     
  
    public ProductInstance getProduct() {
        return product;
    }

    public void setProduct(ProductInstance product) {
        this.product = product;
    }
    
    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
    
    

    
    
}
