/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jakob
 */
@Entity
public class UserInstance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String Username;
    private String phone; 
    private String name; 
    private String email;    
       
    private String Password; 
    private int[] rating; 
    
    @OneToMany(mappedBy="user")
    ArrayList<BidInstance> bids;
    
    @OneToMany(mappedBy="seller")
    ArrayList<ProductInstance> products; 

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
   
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    public int[] getRating() {
        return rating;
    }

    public void setRating(int[] rating) {
        this.rating = rating;
    }

    public ArrayList<BidInstance> getBids() {
        return bids;
    }

    public void setBids(ArrayList<BidInstance> bids) {
        this.bids = bids;
    }

    public ArrayList<ProductInstance> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductInstance> products) {
        this.products = products;
    }
        
    
 
    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
      public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
     public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Username != null ? Username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserInstance)) {
            return false;
        }
        UserInstance other = (UserInstance) object;
        if ((this.Username == null && other.Username != null) || (this.Username != null && !this.Username.equals(other.Username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.User[ id=" + Username + " ]";
    }
    
}
