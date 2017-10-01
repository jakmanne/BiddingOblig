/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Database.ProductFacade;
import Entities.ProductInstance;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Jakob
 */
@ManagedBean
@ViewScoped
public class SearchHandler implements Serializable {

    
    @EJB
    ProductFacade productCreator; 
    
    private List<ProductInstance> allProducts; 
    private List<ProductInstance> temp; 
    private ProductInstance person; 
    private String category = "undefined";
    private String name ="Search by name";

 
    
    public SearchHandler() {
             
    }
    
    public void searchProductActive(){
        
       
    }
    
    public void searchByName(){
        allProducts = productCreator.findProductsByName(name);
    }
    
  
    public void searchDropdown(){
    allProducts =  productCreator.findProductsByCategory(category); 
    
    }
    
    public void allproducts(){
        allProducts = productCreator.findAllProducts(); 
        
    }
       
    
      public List<ProductInstance> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<ProductInstance> allProducts) {
        this.allProducts = allProducts;
    }
    
       public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
      public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
