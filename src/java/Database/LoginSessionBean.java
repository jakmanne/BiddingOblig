/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Entities.UserInstance;
import Helpers.ValidateUserHelper;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jakob
 */
@Stateful
public class LoginSessionBean {

    private boolean loggedIn;
    private boolean validUser;
    private UserInstance userTemp; 

    public LoginSessionBean() {

    }

    @EJB
    private UserFacade userFacade;

    ValidateUserHelper validateCredentials = new ValidateUserHelper();

    public String tryLogin(String username, String password, String url) {

        validUser = validateCredentials.validation(userFacade.findAll(), username, password);
        
        if(validUser){
            
            userTemp = validateCredentials.getUserTemp(); 
            
        }
        
        if (validUser && url != null && url.equals("validateuser=true")) {
            loggedIn = true;
            return "secured/" + "userpage.xhtml" + "?faces-redirect=true";

        }
        if (validUser && url != null && url.equals("newbid=true")) {
            loggedIn = true;
            return "secured/" + "placebid.xhtml" +"?faces-redirect=true";

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
