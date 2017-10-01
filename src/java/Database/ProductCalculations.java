/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Entities.ProductInstance;
import javax.ejb.Stateless;

/**
 *
 * @author Jakob
 */
@Stateless
public class ProductCalculations {
    
    
    public ProductCalculations(){
        
        
    }
    
    
    public double calculateRating(int[] ratings){
        
        double rating = 0; 
        
        if(ratings != null){
            
            for(int i =0; i<ratings.length; i++){
                
                rating = rating + ratings[i];
            }
            
            rating = rating/ratings.length; 
            
        }
        return rating; 
    }
    
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
