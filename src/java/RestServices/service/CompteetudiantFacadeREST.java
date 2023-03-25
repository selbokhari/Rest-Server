/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices.service;

import RestServices.Compteadmin;
import RestServices.Compteetudiant;
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
@Path("restservices.compteetudiant")
public class CompteetudiantFacadeREST extends AbstractFacade<Compteetudiant> {

    @PersistenceContext(unitName = "RestServerPU")
    private EntityManager em;

    public CompteetudiantFacadeREST() {
        super(Compteetudiant.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Compteetudiant entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Compteetudiant entity) {
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
    public Compteetudiant find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Compteetudiant> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Compteetudiant> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    // service 
    @GET
    @Path("ChangermotPasse/{Utilisateur}/{MotPasse}")
    @Produces(MediaType.TEXT_PLAIN)
    public String ChangerMP(@PathParam("Utilisateur") String u, @PathParam("MotPasse") String m) {
         try {

            getEntityManager().createQuery("UPDATE  Compteetudiant SET motdePasse='"+m+"' WHERE nom='"+u+"'").executeUpdate();
            return "true";
        } catch (Exception e) {
            System.out.println("ERREUR : "+e.getMessage());
            return "false";
        }
    }

    @GET
    @Path("Connexion/{Login}/{motPasse}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean loginEtudiant(@PathParam("Login") String u, @PathParam("motPasse") String m) {
        boolean result = false;
        try {                                                                          
            Compteetudiant cptE = (Compteetudiant) getEntityManager().createNamedQuery("Compteetudiant.login").setParameter("nom", u).setParameter("motdePasse", m).getSingleResult();
            if (u.equals(cptE.getNom())) {
                result = true;
            }

        } catch (Exception e) {
            result = false;
        }
        return result;
    }

}
