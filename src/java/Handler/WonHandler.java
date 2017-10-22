/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Database.ProductFacade;
import Entities.ProductInstance;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jakob
 */
@Named(value = "wonProductHandler")
@ManagedBean
@RequestScoped
public class WonHandler {

    
    @EJB
    ProductFacade productFacade; 
    
    private String productname; 
    private String features;  
    private ProductInstance product; 



    public void setFeatures(String features) {
        this.features = features;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    private String image;

    public WonHandler() {
    }

    public void getProductInstance() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getQueryString();
        long val = Long.valueOf(url);
        product = productFacade.findFinishedById(val);
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getFeatures() {
        return features;
    }

    public ProductInstance getProduct() {
        return product;
    }

    public void setProduct(ProductInstance product) {
        this.product = product;
    }
    
    
    
}
