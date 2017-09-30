/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Database.ProductFacade;
import Entities.ProductInstance;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jakob
 */
@ManagedBean
@ViewScoped
public class SearchHandler {

    
    @EJB
    ProductFacade productCreator; 
    
    private List<ProductInstance> allProducts; 
    private List<ProductInstance> temp; 
    
    
    public SearchHandler() {
        
     
    }
    
    public void allproducts(){
        
        allProducts = productCreator.findAllProducts(); 
            for(int i = 0; i<allProducts.size(); i++){
            System.out.println(allProducts.get(i).getProductName());
        }
    }
    
    public void searchCategory(){
        
        temp =  productCreator.findProductsByCategory("Interior"); 
        
        if(temp!=null){
            for(int i = 0; i<temp.size(); i++){
                System.out.println(temp.get(i).getProductName()); 
            }
        }
    }
    
    
      public List<ProductInstance> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<ProductInstance> allProducts) {
        this.allProducts = allProducts;
    }
    
}
