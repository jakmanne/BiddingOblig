/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Entities.UserInstance;
import java.util.List;

/**
 *
 * @author Jakob
 */
public class ValidateUserHelper {
    
 private UserInstance userTemp;  

  public ValidateUserHelper(){
      
  }
    //Validating password and username. We know this is not efficient and it should have been hashed/made more secure. 
    public boolean validation(List<UserInstance> temp, String username, String password){
      
         boolean usernameCorrect = false; 
         boolean passwordCorrect = false;
        
        if(username!= null && !username.isEmpty() && password !=null && !password.isEmpty() ){
            
            if(!temp.isEmpty()){
                
                for(UserInstance users: temp){
                    if(users.getUsername().equals(username)){
                        usernameCorrect = true;
                        break; 
                    }
                }
                
                for(UserInstance users: temp){
                    if(users.getPassword().equals(password)){
                        passwordCorrect = true;
                        userTemp = users; 
                        break; 
                    }
                }
            }
        }
        
         return usernameCorrect && passwordCorrect; 
    }
    
      public UserInstance validationUser(List<UserInstance> temp, String username, String password){
      
         boolean usernameCorrect = false; 
         boolean passwordCorrect = false;
        
        if(username!= null && !username.isEmpty() && password !=null && !password.isEmpty() ){
            
            if(!temp.isEmpty()){
                
                for(UserInstance users: temp){
                    if(users.getUsername().equals(username)){
                        usernameCorrect = true;
                        break; 
                    }
                }
                
                for(UserInstance users: temp){
                    if(users.getPassword().equals(password)){
                        passwordCorrect = true;
                        return userTemp; 
                       
                    }
                }
            }
        }
        
        return null; 
    }

    public UserInstance getUserTemp() {
        return userTemp;
    }

    public void setUserTemp(UserInstance userTemp) {
        this.userTemp = userTemp;
    }
} 
