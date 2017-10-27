/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Entities.ProductInstance;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;


@Stateless
@DeclareRoles("User")
@RolesAllowed("User")
public class ProductCalculations {
   
    public ProductCalculations(){   
    }
    
    //Calculates the average rating for the user. 
    public double calculateNewRating(int[] ratings){
        
         double total = 0; 
         int count = 0;         
         
         for(int i =1; i<ratings.length; i++){
             total = total + ratings[i]*i; 
             count = count + ratings[i]; 
         }
         
         total = (double) total/count;
         return total; 
    }
    
    //Calculates if the bid can be placed. 
    public boolean calculateBid(ProductInstance product, int newBid){
        
        if(product.getCurrentBid() == null){
            
            return true; 
            
        }
        
        if(product.getCurrentBid().getAmount()<newBid){
            
            return true;
        }
        
        return false; 
        
    }
    

    }
    

