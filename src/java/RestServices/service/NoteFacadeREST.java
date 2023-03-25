/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices.service;

import RestServices.Etudiant;
import RestServices.Module;
import RestServices.Note;
import RestServices.NotePK;
import java.util.List;
import javax.ejb.EJB;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author pc
 */
@Stateless
@Path("restservices.note")
public class NoteFacadeREST extends AbstractFacade<Note> {

    @PersistenceContext(unitName = "RestServerPU")
    private EntityManager em;
    @EJB
    private ModuleFacadeREST moduleFacadeREST;
    @EJB
    private EtudiantFacadeREST etudiantFacadeREST;

    private NotePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;idEtudiant=idEtudiantValue;idModule=idModuleValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        RestServices.NotePK key = new RestServices.NotePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> idEtudiant = map.get("idEtudiant");
        if (idEtudiant != null && !idEtudiant.isEmpty()) {
            key.setIdEtudiant(new java.lang.Integer(idEtudiant.get(0)));
        }
        java.util.List<String> idModule = map.get("idModule");
        if (idModule != null && !idModule.isEmpty()) {
            key.setIdModule(new java.lang.Integer(idModule.get(0)));
        }
        return key;
    }

    public NoteFacadeREST() {
        super(Note.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Note entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Note entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        RestServices.NotePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Note find(@PathParam("id") PathSegment id) {
        RestServices.NotePK key = getPrimaryKey(id);
        return super.find(key);
    }

    public Note findByNotePK(NotePK notePK) {
//        RestServices.NotePK key = getPrimaryKey(id);
        return super.find(notePK);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Note> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Note> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    public Note findByIdEtudiantAndIdModule(int idE, int idM) throws Exception {
        try {
            Query q = getEntityManager().createNamedQuery("Note.findByIdEtudiantAndIdModule").setParameter("idEtudiant", idE).setParameter("idModule", idM);
            return (Note) q.getSingleResult();

        } catch (Exception e) {
            return new Note(idE, idM, 0.0, 0.0);
        }

    }

    public List<Note> findByIdModule(int idM) throws Exception {

        Query q = getEntityManager().createNamedQuery("Note.findByIdModule").setParameter("idModule", idM);
        List<Note> notes = q.getResultList();
        return notes;
    }

    public List<Note> findByIDModulee(int id_module) {
        return getEntityManager().createNamedQuery("Note.findByIdModule").setParameter("idModule", id_module).getResultList();
    }

    public List<Integer> findIdEtudiant(int id_module) {
        return getEntityManager().createNamedQuery("Note.findIdEtudiant").setParameter("idModule", id_module).getResultList();
    }

    @GET
    @Path("ModifierNote/{cne}/{nomModule}/{noteNormale}/{noteRattrapage}")
    @Produces(MediaType.TEXT_PLAIN)
    public String modifierNote(@PathParam("cne") String cne, @PathParam("nomModule") String nom, @PathParam("noteNormale") double noteNormale, @PathParam("noteRattrapage") double noteRattrapage) {
        try {
            Module module = moduleFacadeREST.findByNomModule(nom);
            Etudiant etudiant = etudiantFacadeREST.findCne(cne);
            getEntityManager().createNamedQuery("Note.Modifier").setParameter("notenormale", noteNormale).setParameter("noterattrapage", noteRattrapage).setParameter("idEtudiant", etudiant.getIdetudiant()).setParameter("idModule", module.getIdModule()).executeUpdate();
        
            return "true";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
