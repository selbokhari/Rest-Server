/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices.service;

import RestServices.Compteadmin;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author pc
 */
@Stateless
@Path("restservices.compteadmin")
public class CompteadminFacadeREST extends AbstractFacade<Compteadmin> {

    @PersistenceContext(unitName = "RestServerPU")
    private EntityManager em;

    public CompteadminFacadeREST() {
        super(Compteadmin.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Compteadmin entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Compteadmin entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Compteadmin find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Compteadmin> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Compteadmin> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    // services 
    @GET
    @Path("login/{nom}/{motPasse}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean loginAdmin(@PathParam("nom") String u, @PathParam("motPasse") String m) {
        boolean result = false;
        try {
            Compteadmin cptA = (Compteadmin) getEntityManager().createNamedQuery("Compteadmin.login").setParameter("nom", u).setParameter("motdePasse", m).getSingleResult();
            if (u.equals(cptA.getNom())) {
                result = true;
            }

        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @GET
    @Path("ChangermotPasse/{Utilisateur}/{MotPasse}")
    @Produces(MediaType.TEXT_PLAIN)
    public String ChangerMP(@PathParam("Utilisateur") String u, @PathParam("MotPasse") String m) {
        try {

            getEntityManager().createQuery("UPDATE  Compteadmin SET motdePasse= '"+m+"' WHERE c.nom="+u).executeUpdate();
            return "true";
        } catch (Exception e) {
            System.out.println("ERREUR : "+e.getMessage());
            return "false";
        }
    }

}
