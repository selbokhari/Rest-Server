/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices.service;

import RestServices.Masters;
import RestServices.Module;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
@Path("restservices.masters")
public class MastersFacadeREST extends AbstractFacade<Masters> {

    @PersistenceContext(unitName = "RestServerPU")
    private EntityManager em;

    public MastersFacadeREST() {
        super(Masters.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Masters entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Masters entity) {
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
    public Masters find(@PathParam("id") Integer id) {
        return super.find(id);
    }

//    @GET
//    @Override
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<Masters> findAll() {
//        return super.findAll();
//    }
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Masters> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    public Masters getById(int idM) {
        Query q = getEntityManager().createNamedQuery("Masters.findByIdMaster").setParameter("idMaster", idM);
        return (Masters) q.getSingleResult();
    }

    @GET
    @Override
    @Path("/listeMasters")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Masters> findAll() {
//        return (List<Masters>) getEntityManager().createNamedQuery("Masters.findAll").getResultList() ;
        return super.findAll();
    }

    public Masters findByMaster(String nom) {
        return (Masters) getEntityManager().createNamedQuery("Masters.findByNom").setParameter("nom", nom).getSingleResult();
    }

}
