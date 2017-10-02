/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Database.ProductCalculations;
import Database.UserFacade;
import Entities.UserInstance;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    // Saves the message and then returns the string "theend"
    public String postUser(){
       int[] temp = new int[6];
       user.setRating(temp);
       this.userFacade.create(user);
       return "newuser";
    }
    
    
        public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

  
    
}
