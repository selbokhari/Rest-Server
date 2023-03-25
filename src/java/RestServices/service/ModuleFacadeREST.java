/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices.service;

import RestServices.Module;
import RestServices.Semestre;
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
@Path("restservices.module")
public class ModuleFacadeREST extends AbstractFacade<Module> {

    @PersistenceContext(unitName = "RestServerPU")
    private EntityManager em;

    public ModuleFacadeREST() {
        super(Module.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Module entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Module entity) {
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
    public Module find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Module> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Module> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
     public List<Module> findBySemstre(int idS) { // find Modules

        Query q = getEntityManager().createNamedQuery("Module.findByIdSmester").setParameter("idSmester", idS);
        List<Module> l = q.getResultList();
//        List<T> list = getEntityManager().createQuery(query).getResultList();
        return l;
    }
     public List<Module> findBySemstres(int idS1,int idS2) { // find Modules
         
        Query q = getEntityManager().createNamedQuery("Module.findByIdSmesters").setParameter("idSmester1", idS1).setParameter("idSmester2", idS2);
        List<Module> l = q.getResultList();
//        List<T> list = getEntityManager().createQuery(query).getResultList();
        return l;
    }
    public Module findByNomModule (String nom){
        return (Module)getEntityManager().createNamedQuery("Module.findByNom").setParameter("nom", nom).getSingleResult();
    }
    public List<Module> findByIDSemestre (int id_semetre){
        return getEntityManager().createNamedQuery("Module.findByIdSmester").setParameter("idSmester", id_semetre).getResultList();
    }
}
