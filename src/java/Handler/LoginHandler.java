/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Database.LoginSessionBean;
import Database.UserFacade;
import Helpers.ValidateUserHelper;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;



@ManagedBean
@SessionScoped
public class LoginHandler implements Serializable{
    
    private String url; 
    private String username;
    private String password;
    private boolean loggedIn = false; 


    public LoginHandler() {
        
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getQueryString();  
    }
        
    @EJB
    private LoginSessionBean executeLogin; 
    
    public String doLogin(){
        
       String returnUrl = executeLogin.tryLogin(username, password, url);  
       loggedIn = executeLogin.isLoggedIn(); 
       return returnUrl; 
    }
    
    public String doLogout(){
        
        this.setLoggedIn(false);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
}
