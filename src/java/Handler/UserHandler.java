/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Database.ProductCalculations;
import Database.UserFacade;
import Entities.UserInstance;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jakob
 */
@Named(value = "userHandler")
@RequestScoped
public class UserHandler {

    @EJB
    private UserFacade userFacade;
    
    @EJB
    ProductCalculations prod; 

    private UserInstance user; 

    private String username; 
    private String repeatPassword; 

    /**
     * Creates a new instance of UserHandler
     */
    public UserHandler() {
        
        this.user = new UserInstance(); 
    }
    
      public UserInstance getUser() {
       return user;
    }

    // Returns the total number of messages
    public int getNumberOfUsers(){
       return userFacade.findAll().size();
    }
    
    public List<UserInstance> getListOfEverybody(){
        List<UserInstance> temp = userFacade.findAll();  
        return  temp;    
    } 

    // updates the database with a new user. 
    public String postUser() {

        if (!user.getPassword().equals(repeatPassword)) {

            FacesMessage msg = new FacesMessage("Password does not match");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "newuser";

        }else{
            
             int[] temp = new int[6];
             user.setRating(temp);
             this.userFacade.create(user);
             return "index?faces-redirect=true";
        }
    }
    
    
        public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    

  
    
}
