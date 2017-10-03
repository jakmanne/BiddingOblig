/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.RequestScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Jakob
 * Entity class for productInstance.
 */
@Entity
@RequestScoped
public class ProductInstance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    
    private String productName;
    private String picture; 
    private String features; 
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date databaseTimestamp; 
    
    @OneToOne
    @JoinColumn(name = "bid_fk")
    private BidInstance currentBid;
    
    @ManyToOne
    @JoinColumn(name = "user_seller_pk")
    private UserInstance seller;
    
    @ManyToOne
    @JoinColumn(name = "user_buyer_pk")
    private UserInstance buyer;

    
    
    
    private boolean isactive; 
    private boolean ispurchased; 
    private String category; 

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UserInstance getSeller() {
        return seller;
    }

    public void setSeller(UserInstance seller) {
        this.seller = seller;
    }
    

    public BidInstance getCurrentBid() {
        return currentBid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
      public UserInstance getBuyer() {
        return buyer;
    }

    public void setBuyer(UserInstance buyer) {
        this.buyer = buyer;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public UserInstance getUser() {
        return seller;
    }

    public void setUser(UserInstance user) {
        this.seller = user;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public boolean isIspurchased() {
        return ispurchased;
    }

    public void setIspurchased(boolean ispurchased) {
        this.ispurchased = ispurchased;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
 
    public Long getId() {
        return productId;
    }

    public void setId(Long id) {
        this.productId = id;
    }
    
    public void setCurrentBid(BidInstance currentBid) {
        this.currentBid = currentBid;
    }
    
    public Date getDatabaseTimestamp() {
        return databaseTimestamp;
    }

    public void setDatabaseTimestamp(Date databaseTimestamp) {
        this.databaseTimestamp = databaseTimestamp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductInstance)) {
            return false;
        }
        ProductInstance other = (ProductInstance) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ProductHandler[ id=" + productId + " ]";
    }

 
    
}
