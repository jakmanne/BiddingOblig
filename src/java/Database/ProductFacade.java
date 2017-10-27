/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Entities.ProductInstance;
import Entities.UserInstance;
import java.util.Date;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Jakob
 */
@Stateless
@Named
@Path("http://localhost:8080/BiddingOblig/product")
@PermitAll
public class ProductFacade extends AbstractFacade<ProductInstance> {

    @PersistenceContext(unitName = "BiddingObligPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(ProductInstance.class);
    }
    
    /**
     * 
     * 
     * Here are several methods to get information from the Database. They are similar and in the end we have a timechecker method
     * that is run several times in order to update the information that lays in the database. We have used typedquerys. 
     */
    
    public List<ProductInstance> findProductsByCategory(String category) {
   
      TypedQuery<ProductInstance> query =
      em.createQuery("SELECT c FROM ProductInstance AS c WHERE c.category = :category AND c.isactive = true AND c.ispurchased = false ORDER BY c.databaseTimestamp", ProductInstance.class).setParameter("category", category);
      List<ProductInstance> results = query.getResultList();
      return results; 
      

    }
 
   
     public List<ProductInstance> findProductsByName(String name) {
   
      TypedQuery<ProductInstance> query =
      em.createQuery("SELECT c FROM ProductInstance AS c WHERE c.productName = :name AND c.isactive = true AND c.ispurchased = false ORDER BY c.databaseTimestamp", ProductInstance.class).setParameter("name", name);
      List<ProductInstance> results = query.getResultList();
      return results; 
      
    }
    
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/product/all")
    public List<ProductInstance> findAllProducts(){
      Date currentDate = new Date();
      TypedQuery<ProductInstance> query =
      em.createQuery("SELECT c FROM ProductInstance c WHERE c.isactive = true AND c.ispurchased = false AND c.timestamp > :date ORDER BY c.databaseTimestamp",ProductInstance.class).setParameter("date",currentDate);
      List<ProductInstance> results = query.getResultList();
      return results; 
        
    }
    
     public ProductInstance findProductById(Long productId){
        
      TypedQuery<ProductInstance> query =
      em.createQuery("SELECT c FROM ProductInstance c WHERE c.isactive = true AND c.ispurchased = false AND c.productId = :Id", ProductInstance.class).setParameter("Id", productId);
      ProductInstance product = query.getSingleResult();
      return product; 
    }
     
     public ProductInstance findFinishedById(Long productId){
      
      TypedQuery<ProductInstance> query =
      em.createQuery("SELECT c FROM ProductInstance c WHERE c.isactive = false AND c.ispurchased = true AND c.productId = :Id", ProductInstance.class).setParameter("Id", productId);
      ProductInstance product = query.getSingleResult();
      return product; 
     }
   
    public List<ProductInstance> findboughtproducts(UserInstance username) {

        TypedQuery<ProductInstance> query =
        em.createQuery("SELECT c FROM ProductInstance c WHERE c.isactive = false AND c.ispurchased = true AND c.buyer = :name", ProductInstance.class).setParameter("name", username);
        List<ProductInstance> results = query.getResultList();
        System.out.println(results.size());
        return results;

    }
    
    public List<ProductInstance> getFinishedAuction(){
         
        TypedQuery<ProductInstance> query =
        em.createQuery("SELECT c FROM ProductInstance c WHERE c.isactive = false AND c.ispurchased = true ORDER BY c.databaseTimestamp", ProductInstance.class);
        List<ProductInstance> result = query.getResultList(); 
        return result;
    }
     
 
    
      public void timeChecker(){
        
      Date currentDate = new Date(); 
      UserInstance user; 
       
      TypedQuery<ProductInstance> query =
      em.createQuery("SELECT c FROM ProductInstance c WHERE c.isactive = true AND c.ispurchased = false AND c.timestamp < :date", ProductInstance.class).setParameter("date", currentDate);
      List<ProductInstance> results = query.getResultList();
                     
      for(int i = 0; i<results.size(); i++){
                 
          if(results.get(i).getCurrentBid()!=null){
              
                 results.get(i).setIspurchased(true);
                 results.get(i).setIsactive(false); 
                 user = results.get(i).getCurrentBid().getUser();
                 List <ProductInstance> temp = user.getBoughtproducts(); 
                 temp.add(results.get(i));
                 results.get(i).setBuyer(user);
                 getEntityManager().merge(user);
          
          }else{
                 results.get(i).setIspurchased(false);
                 results.get(i).setIsactive(false); 
                 getEntityManager().merge(results.get(i));

          }   
          
      }
    }
    
    
}
