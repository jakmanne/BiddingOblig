/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Entities.UserInstance;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jakob
 */
@Stateless
@DeclareRoles("User")
@RolesAllowed("User")
public class UserFacade extends AbstractFacade<UserInstance> {

    @PersistenceContext(unitName = "BiddingObligPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(UserInstance.class);
    }
    
}
