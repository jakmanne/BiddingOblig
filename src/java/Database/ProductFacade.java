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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jakob
 */
@Stateless
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
     
    
    public void timeChecker(){
        
      Date currentDate = new Date(); 
      UserInstance user; 
        
      TypedQuery<ProductInstance> query =
      em.createQuery("SELECT c FROM ProductInstance c WHERE c.isactive = true AND c.ispurchased = false AND c.timestamp < :date", ProductInstance.class).setParameter("date", currentDate);
      List<ProductInstance> results = query.getResultList();
      
      for(int i = 0; i<results.size(); i++){
          
          results.get(i).setIspurchased(true);
          results.get(i).setIsactive(false); 
          user = results.get(i).getCurrentBid().getUser();
     
          //legg til bruker som har kjøpt produktet. 
          em.persist(results.get(i));
          em.flush();
          
      }
                   
    }
    
    
}
