/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Jakob
 */
@Named(value = "productHandler")
@RequestScoped
public class ProductHandler implements Serializable  {

    /**
     * Creates a new instance of ProductHandler
     */
    public ProductHandler() {
    }
    
}
