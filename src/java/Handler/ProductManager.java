
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import Entities.ProductInstance;
import javax.annotation.PreDestroy;
import javax.ws.rs.client.ClientBuilder;

@Named
@SessionScoped
public class ProductManager implements Serializable {

    private static final long serialVersionUID = 1;
    private static final Logger logger = Logger.getLogger(ProductManager.class.getName());
    private List<ProductInstance> products;
    private Client client;
    private final String baseUri = "http://localhost:8080/BiddingOblig/webapi";
    private WebTarget target;
    private ProductInstance product; 

    
        /**
     * Default constructor creates the JAX-RS client
     */
    public ProductManager() {
        client = ClientBuilder.newClient();
    }
    
    @PreDestroy
    private void clean() {
        client.close();
    }
    
    
    
        /**
     * @return the event
     */
    public ProductInstance getProduct() {
        return product;
    }

    /**
     * @param event the event to set
     */
    public void setProduct(ProductInstance product) {
        this.product = product;
    }
    
    /**
     * Get all the events
     *
     * @return all the events
     */
    public List<ProductInstance> getProducts() {
        List<ProductInstance> returnedProducts = null;
        
        System.out.println("hei!");
        
        try {
            System.out.print("Er inne i try");
            returnedProducts = client.target(baseUri)
                    .path("/product/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ProductInstance>>() {
                    });
            if (returnedProducts == null) {
                logger.log(Level.SEVERE, "Returned events null.");
                System.out.println("Det skjedde ein feil med å hente produkt");
            } else {
                logger.log(Level.INFO, "Products have been returned.");
                System.out.println("Produkt henta");
            }
        } catch (WebApplicationException ex) {
            System.out.println("Webapplicationexception" + ex.getMessage());
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (ResponseProcessingException ex) {
            logger.log(Level.SEVERE, "ReponseProcessingException thrown.");
            logger.log(Level.SEVERE, "Error is {0}", ex.getMessage());
        } catch (ProcessingException ex) {
            logger.log(Level.SEVERE, "ProcessingException thrown.");
            logger.log(Level.SEVERE, "Error is {0}", ex.getMessage());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error retrieving all events.");
            logger.log(Level.SEVERE, "base URI is {0}", baseUri);
            logger.log(Level.SEVERE, "path is {0}", "all");
            logger.log(Level.SEVERE, "Exception is {0}", ex.getMessage());
        }
        return returnedProducts;
    }

    /**
     * Setter for all the events
     *
     * @param events the events to set
     */
    public void setProducts(List<ProductInstance> products) {
        this.products = products;
    }



}