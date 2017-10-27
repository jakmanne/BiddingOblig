/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Database.ProductCalculations;
import Database.ProductFacade;
import Database.UserFacade;
import Entities.ProductInstance;
import Entities.UserInstance;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
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
public class SearchHandler implements Serializable {

    
    @EJB
    ProductFacade productCreator; 
    
    @EJB
    UserFacade userDatabase;
    
    @EJB
    ProductCalculations calc;

    
    
    private List<ProductInstance> allProducts; 
    private List<ProductInstance> temp; 
    private ProductInstance person; 
    private UserInstance searchForPerson;
    private UserInstance user; 
    private String category = "undefined";
    private String name ="Search by name";
    private double myuserRating;
    private int userRating; 
    private int[] ratingCollection;

    

 
    
    public SearchHandler() {
             
    }
    
    public void searchProductActive(){
        
       
    }
    
    //Gets the list of the users baught products. 
    public void searchBaughtProducts() {
                
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        LoginHandler sessionobject = (LoginHandler) httpSession.getAttribute("loginHandler");
        user = sessionobject.getSessionuser();
        allProducts = productCreator.findboughtproducts(user);
        
        UserInstance usertemp = this.userDatabase.find(user.getUsername());   
        myuserRating =  calc.calculateNewRating(usertemp.getRating());

    }
     
    //Updates the database if a user has posted a new rating. 
    public String postRating(){
        
        ratingCollection = searchForPerson.getRating();  
        ratingCollection[userRating] += 1;
        this.userDatabase.edit(searchForPerson);    
        return "/secured/userpage?faces-redirect=true";
    }
        
       
    public double getMyuserRating() {
        return myuserRating;
    }

    public void setMyuserRating(double myuserRating) {
        this.myuserRating = myuserRating;
    }
     
    public void searchByName() {

        productCreator.timeChecker();
        allProducts = productCreator.findProductsByName(name);
    }

    public void searchDropdown() {

        productCreator.timeChecker();
        allProducts = productCreator.findProductsByCategory(category);

    }

    public void allproducts() {

        productCreator.timeChecker();
        allProducts = productCreator.findAllProducts();

    }
       
    
      public List<ProductInstance> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<ProductInstance> allProducts) {
        this.allProducts = allProducts;
    }
    
     public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
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
    
    
    public UserInstance getSearchForPerson() {
        return searchForPerson;
    }

    public void setSearchForPerson(UserInstance searchForPerson) {
        this.searchForPerson = searchForPerson;
    }
    
    public UserInstance getUser() {
        return user;
    }

    public void setUser(UserInstance user) {
        this.user = user;
    }

    
    
}
