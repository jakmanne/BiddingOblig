/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Entities.UserInstance;
import Helpers.ValidateUserHelper;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jakob
 */
@Stateful
@PermitAll
public class LoginSessionBean {

    private boolean loggedIn;
    private boolean validUser;
    private UserInstance userTemp; 
    private String urlParam; 
    private String productId; 

    public LoginSessionBean() {

    }

    @EJB
    private UserFacade userFacade;

    //Helper class for validation. 
    ValidateUserHelper validateCredentials = new ValidateUserHelper();

    //Method that tries to login the user. Checks on the url parameter where the user wants to go and sends redirect. 
    public String tryLogin(String username, String password, String url) {

        validUser = validateCredentials.validation(userFacade.findAll(), username, password);
        
        if(validUser){
            
            userTemp = validateCredentials.getUserTemp(); 
            String[] split = url.split("=");
            urlParam  =  split[0];
            productId = split[1]; 
            
        }
        
        if (validUser && url != null && urlParam.equals("validateuser")) {
            loggedIn = true;
            return "secured/" + "userpage.xhtml" + "?faces-redirect=true";

        }
        
        if(validUser && url != null && urlParam.equals("newproduct")){
             loggedIn = true;
            return "secured/" + "newproduct.xhtml" + "?faces-redirect=true";
        }
        if (validUser && url != null && urlParam.equals("newbid")) {
            loggedIn = true;
            return "secured/" + "placebid.xhtml" +"?faces-redirect=true&id=" + productId;

        } else {
            loggedIn = false;
            FacesMessage msg = new FacesMessage("Wrong password or something wrong with your URL ;)");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "/login.xhtml?" + url;
        }
    }

    public UserInstance getUserTemp() {
        return userTemp;
    }

    public void setUserTemp(UserInstance userTemp) {
        this.userTemp = userTemp;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.loggedIn = isLoggedIn;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }
}
