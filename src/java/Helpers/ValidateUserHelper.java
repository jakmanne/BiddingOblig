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
    

  public ValidateUserHelper(){
      
  }
    
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
                        break; 
                    }
                }
            }
        }
         return usernameCorrect && passwordCorrect; 
    }
} 
