/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices.service;

import RestServices.Etudiant;
import RestServices.Masters;
import RestServices.Module;
import RestServices.Note;
import RestServices.Semestre;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.MasterMention;
import data.ModuleNote;
import data.ReleveNotes;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 *
 * @author pc
 */
@Stateless
@Path("restservices.etudiant")
public class EtudiantFacadeREST extends AbstractFacade<Etudiant> {

    static String projectPath = "C:\\Users\\pc\\Desktop\\RestServer(Ouahiba)\\RestServer";
    @EJB
    private NoteFacadeREST noteFacadeREST;

    @EJB
    private EtudiantFacadeREST etudiantFacadeREST;

    @EJB
    private ModuleFacadeREST moduleFacadeREST;
    @EJB
    private MastersFacadeREST mastersFacadeREST;
    @EJB
    private SemestreFacadeREST semestreFacadeREST;

    @PersistenceContext(unitName = "RestServerPU")
    private EntityManager em;

    public EtudiantFacadeREST() {
        super(Etudiant.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Etudiant entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Etudiant entity) {
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
    public Etudiant find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Path("/findAll")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Etudiant> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Etudiant> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    /////////////////////                    ///////////////////////////
    /////////////////////   REST SERVICE I   ///////////////////////////
    /////////////////////                    ///////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    //service 1.3 XML
    //service 1.3 XML
    @GET
    @Path("ListEtudiantsSemestresXML/{Semestre}/{Mastre}")
    @Produces("APPLICATION/XML")
    public List<Etudiant> listEtudiantsSemestresXML(@PathParam("Semestre") int idS, @PathParam("Mastre") int idM) {
        List<Etudiant> etudiants = new ArrayList();
        List<Module> modules = moduleFacadeREST.findByIDSemestre((idM - 1) * 4 + idS);
        List<Integer> idsEtuds = new ArrayList();
        for (Module module : modules) {
            List<Integer> id = new ArrayList();
            id = noteFacadeREST.findIdEtudiant(module.getIdModule());
            for (int i : id) {
                if (!idsEtuds.contains(i)) {
                    idsEtuds.addAll(id);
                }
            }
        }
        for (int id : idsEtuds) {
            Etudiant etudiant = new Etudiant();
            etudiant = find(id);
            etudiants.add(etudiant);
        }
        return etudiants;
    }

    //service 1.3 Pdf
    @GET
    @Path("ListEtudiantsSemestersPDF/{Semestre}/{Master}")
    @Produces("Application/pdf")
    public Response listEtudiantsSemestersPDF(@PathParam("Semestre") int idS, @PathParam("Master") int idM) {
        Masters master = mastersFacadeREST.getById(idM);
        List<Etudiant> etudiants = new ArrayList();
        List<Module> modules = moduleFacadeREST.findByIDSemestre((idM - 1) * 4 + idS);
        List<Integer> idsEtuds = new ArrayList();
        for (Module module : modules) {
            List<Integer> id = new ArrayList();

            id = noteFacadeREST.findIdEtudiant(module.getIdModule());
            for (int i : id) {
                if (!idsEtuds.contains(i)) {
                    idsEtuds.addAll(id);
                }
            }
        }
        for (int id : idsEtuds) {
            Etudiant etudiant = new Etudiant();
            etudiant = find(id);
            etudiants.add(etudiant);
        }
//        String path = "C:\\Users\\pc\\Desktop\\RestServer";
        String pdfFilename = projectPath + "\\" + "ListEtudiants.pdf";
        PdfPTable table = getPdfListEtudiants();

        insertListEtudiants(table, etudiants);
        ListEtudiantsPdf(pdfFilename, table, idS, master.getDepartement(), master.getNom());

        File file = new File(pdfFilename);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachement; filename=" + pdfFilename + "").type("application/pdf");
        return response.build();
    }

    // service 1.4 Csv
    @GET
    @Path("listeEtudiantsModuleCsv/{idMaster}/{idSemestre}")
    @Produces("text/csv")
    public Response listeEtudiantsModuleCsv(@PathParam("idMaster") Integer idM, @PathParam("idSemestre") Integer idS) {

//        String path = "C:\\Users\\pc\\Desktop\\RestServer";
        String csvFilename = "notes.csv";

//        DecimalFormat df = new DecimalFormat("0.00");
        try {
            FileOutputStream fos = new FileOutputStream(projectPath + "\\" + csvFilename);

//            FileWriter csvWriter = new FileWriter(path + "\\" + csvFilename);
            StringBuilder csvWriter = new StringBuilder();
//            File file = new File(path + "\\" + csvFilename);
//            file.createNewFile();

            csvWriter.append("CNE");
            csvWriter.append(";");
            csvWriter.append("Nom");
            csvWriter.append(";");
            csvWriter.append("Prenom");
            csvWriter.append(";");
            csvWriter.append("Module 1");

            csvWriter.append(";");
            csvWriter.append("Normale");
            csvWriter.append(";");
            csvWriter.append("Rattrapage");

            csvWriter.append(";");
            csvWriter.append("Module 2");
            csvWriter.append(";");

            csvWriter.append("Normale");
            csvWriter.append(";");
            csvWriter.append("Rattrapage");

            csvWriter.append(";");

            csvWriter.append("Module 3");
            csvWriter.append(";");

            csvWriter.append("Normale");
            csvWriter.append(";");
            csvWriter.append("Rattrapage");

            csvWriter.append(";");
            csvWriter.append("Module 4");
            csvWriter.append(";");

            csvWriter.append("Normale");
            csvWriter.append(";");
            csvWriter.append("Rattrapage");
            csvWriter.append(";");

            csvWriter.append("Module 5");
            csvWriter.append(";");
            csvWriter.append("Normale");
            csvWriter.append(";");
            csvWriter.append("Rattrapage");
            csvWriter.append(";");
            csvWriter.append("Module 6");
            csvWriter.append(";");
            csvWriter.append("Normale");
            csvWriter.append(";");
            csvWriter.append("Rattrapage");
            csvWriter.append("\n");

            List<Module> modules = moduleFacadeREST.findBySemstre(idS);
            PdfPTable table = getPdftable();
            System.out.println("----------------------------| getMoyenneSemestre |----------------------------");
            System.out.println("************* Modules : " + modules);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 5, Font.NORMAL, BaseColor.DARK_GRAY);

            List<Etudiant> etudiants = etudiantFacadeREST.findAll();

            System.out.println("************* Liste  etudiants : " + etudiants);
            System.out.println("************* Liste  modules   : " + modules);

            for (Etudiant e : etudiants) {
                List<Note> notes = new ArrayList();
                csvWriter.append(e.getCne()).append(";");
                csvWriter.append(e.getNom()).append(";");
                csvWriter.append(e.getPrenom()).append(";");
                for (Module m : modules) {
                    Note n;
                    try {
                        n = noteFacadeREST.findByIdEtudiantAndIdModule(e.getIdetudiant(), m.getIdModule());
                    } catch (Exception ex) {
                        System.out.println("not found : idE : " + e.getIdetudiant() + ", idM : " + m.getIdModule() + "");
                        n = new Note(e.getIdetudiant(), m.getIdModule(), 0.0, 0.0);
                    }
                    csvWriter.append(m.getNom()).append(";");
                    csvWriter.append("" + n.getNoteNormale()).append(";").append("" + n.getNoteRattrapage()).append(";");
                }
                csvWriter.append("\n");

            }

            //file path
            fos.write(csvWriter.toString().getBytes());
            fos.close();
            File file = new File(projectPath + "\\" + csvFilename);
            ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachement; filename=" + csvFilename + "").type("text/csv");
            return response.build();

        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
            return null;
        }

    }

    // service 1.4 PDF 
    @GET
    @Path("listeEtudiantsModulePdf/{idMaster}/{idSemestre}")
    @Produces("application/pdf")

    public Response listeEtudiantsModulePdf(@PathParam("idMaster") Integer idM, @PathParam("idSemestre") Integer idS) {
        int i = 0;
        try {

            List<Module> modules = moduleFacadeREST.findBySemstre(idS);
            PdfPTable table = getPdftable();
            System.out.println("----------------------------| getMoyenneSemestre |----------------------------");
            System.out.println("************* Modules : " + modules);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 5, Font.NORMAL, BaseColor.DARK_GRAY);

            List<Etudiant> etudiants = etudiantFacadeREST.findAll();

            System.out.println("************* Liste  etudiants : " + etudiants);
            System.out.println("************* Liste  modules   : " + modules);
            i++;

            for (Etudiant e : etudiants) {
                List<Note> notes = new ArrayList();
                System.out.println("************* Liste  modules   : " + modules);

                for (Module m : modules) {
                    Note n;
                    try {
                        System.out.println(" --->   Pour   : (" + m.getIdModule() + "," + e.getIdetudiant() + ")");
                        n = noteFacadeREST.findByIdEtudiantAndIdModule(e.getIdetudiant(), m.getIdModule());
                        System.out.println(" --->   Trouvé !! ");
                    } catch (Exception ex) {
                        System.out.println(" --->   Trouvé !! ");

                        System.out.println("not found : idE : " + e.getIdetudiant() + ", idM : " + m.getIdModule() + "");
                        n = new Note(e.getIdetudiant(), m.getIdModule(), 0.0, 0.0);
                    }

                    notes.add(n);
                }
                System.out.println(" --->   insertion dans la table !! ");
                System.out.println(" --->   e:" + e.getIdetudiant() + ", notes : \n -->" + notes + " !! ");
                try {
                    insertTable(table, e.getCne(), font, e.getNom(), e.getPrenom(),
                            "" + notes.get(0).getNoteNormale(), "" + notes.get(0).getNoteRattrapage(),
                            "" + notes.get(1).getNoteNormale(), "" + notes.get(1).getNoteRattrapage(),
                            "" + notes.get(2).getNoteNormale(), "" + notes.get(2).getNoteRattrapage(),
                            "" + notes.get(3).getNoteNormale(), "" + notes.get(3).getNoteRattrapage(),
                            "" + notes.get(4).getNoteNormale(), "" + notes.get(4).getNoteRattrapage(),
                            "" + notes.get(5).getNoteNormale(), "" + notes.get(5).getNoteRattrapage());

                } catch (Exception ex) {
                }
            }

            System.out.println(" --->   recuperation du fichier pdf !! ");

            i++;

//            String path = "C:\\Users\\pc\\Desktop\\\\RestServer";
            String pdfFilename = "notes.pdf";
            i++;
            Masters master;
            try {
                master = mastersFacadeREST.getById(idM);
                System.out.println("Master : " + master);

                i++;
            } catch (Exception ecc) {
                System.out.println("Master--->" + ecc.getMessage());
                master = new Masters(1, "M2I", "Lakhouaja", "Informatique");
            }

            System.out.println("Master : " + master);

            Semestre semestre = semestreFacadeREST.find(idS);
            i++;
            System.out.println("Semestre  : " + semestre);

            notesPdf(pdfFilename, table, master, idS);
            File file = new File(projectPath + "\\" + pdfFilename);
            ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachement; filename=" + pdfFilename + "").type("application/pdf");
            return response.build();
        } catch (Exception e) {
            System.out.println("( " + i + ")ERREUR : " + e.getMessage());
        }
        return null;
    }

    // service 1.5 XML
    @GET
    @Path("releveNotesXml/{idEtudiant}/{idSemstre}")
    @Produces(MediaType.APPLICATION_XML)
    public ReleveNotes releveNotesXml(@PathParam("idEtudiant") Integer idE, @PathParam("idSemstre") Integer idS) {
        double normale = 0.0, rattrapage = 0.0, somme = 0.0;
        List<Module> modules = moduleFacadeREST.findBySemstre(idS);
        Semestre semestre = semestreFacadeREST.find(idS);
        Etudiant etudiant = etudiantFacadeREST.findById(idE);
        double moyenne = 0.0;
        Masters master = mastersFacadeREST.getById(semestre.getIdMaster());

        System.out.println("----------------------------| getMoyenneSemestre |----------------------------");
        System.out.println("************* Modules : " + modules);
        System.out.println("************* Semetre : " + semestre);
        System.out.println("************* Master : " + master);
        System.out.println("************* Etudiant : " + etudiant);
        int invalideCount = 0;
        List<ModuleNote> moduleNotes = new ArrayList();
        try {
            for (Module m : modules) {
                Note n = noteFacadeREST.findByIdEtudiantAndIdModule(idE, m.getIdModule());
                moduleNotes.add(new ModuleNote(m.getNom(), n.getNoteNormale(), n.getNoteRattrapage()));
                try {
                    normale = n.getNoteNormale();
                } catch (Exception e) {
                    System.out.println("Note du normale est null !!");
                    throw new Exception("L'estudiant n'a pas encore validé tous les modules");

                }
                try {
                    rattrapage = n.getNoteRattrapage();
                } catch (Exception e) {
                    System.out.println("Note du rattrapage est null !!");
                }
                System.out.println("*****************Note : " + n + "==> N : " + n.getNoteNormale() + ", R : " + n.getNoteRattrapage());

                if (normale < 7 || (rattrapage < 7 && normale < 10)) {
                    throw new Exception("L'estudiant n'a pas encore validé tous les modules");

                } else if (normale < 10 && rattrapage < 10) {
                    invalideCount++;
                }

                somme += normale > rattrapage ? normale : rattrapage;

            }

//    Masters master;
//    Etudiant etudiant;
//    List<ModuleNote> moduleNote;
//    Double moyenne  ;
            moyenne = invalideCount == 0 ? somme / modules.size() : 0.0;

        } catch (Exception e) {
            System.out.println("\n**** ERREUR dans le service releveNotes : " + e.getMessage());
            return new ReleveNotes(master.getDepartement(), master.getNom(), etudiant, moduleNotes, moyenne);
        }

        return new ReleveNotes(master.getDepartement(), master.getNom(), etudiant, moduleNotes, moyenne);
    }

    // service 1.5 PDF
    @GET
    @Path("releveNotesPdf/{idEtudiant}/{idSemstre}")
    @Produces("application/pdf")
    public Response releveNotesPdf(@PathParam("idEtudiant") Integer idE, @PathParam("idSemstre") Integer idS) {
        double normale = 0.0, rattrapage = 0.0, somme = 0.0;
        List<Module> modules = moduleFacadeREST.findBySemstre(idS);
        Semestre semestre = semestreFacadeREST.find(idS);
        Etudiant etudiant = etudiantFacadeREST.findById(idE);
        double moyenne = 0.0;
        Masters master = mastersFacadeREST.getById(semestre.getIdMaster());

        System.out.println("----------------------------| getMoyenneSemestre |----------------------------");
        System.out.println("************* Modules  : " + modules);
        System.out.println("************* Semetre  : " + semestre);
        System.out.println("************* Master   : " + master);
        System.out.println("************* Etudiant : " + etudiant);
        int invalideCount = 0;
        List<ModuleNote> moduleNotes = new ArrayList();
        try {
            for (Module m : modules) {
                Note n;
                try {
                    n = noteFacadeREST.findByIdEtudiantAndIdModule(idE, m.getIdModule());
                } catch (Exception e) {
                    n = new Note();
                }
                moduleNotes.add(new ModuleNote(m.getNom(), n.getNoteNormale(), n.getNoteRattrapage()));
                try {
                    normale = n.getNoteNormale();
                } catch (Exception e) {
                    System.out.println("Note du normale est null !!");
//                    throw new Exception("L'estudiant n'a pas encore validé tous les modules");

                }
                try {
                    rattrapage = n.getNoteRattrapage();
                } catch (Exception e) {
                    System.out.println("Note du rattrapage est null !!");
                }
                System.out.println("*****************Note : " + n + "==> N : " + n.getNoteNormale() + ", R : " + n.getNoteRattrapage());

                if (normale < 7 || (rattrapage < 7 && normale < 10)) {
//                    throw new Exception("L'estudiant n'a pas encore validé tous les modules");
                    invalideCount++;
                } else if (normale < 10 && rattrapage < 10) {
                    invalideCount++;
                }

                somme += normale > rattrapage ? normale : rattrapage;

            }

//    Masters master;
//    Etudiant etudiant;
//    List<ModuleNote> moduleNote;
//    Double moyenne  ;
            moyenne = invalideCount == 0 ? somme / modules.size() : 0.0;

        } catch (Exception e) {
            System.out.println("\n**** ERREUR dans le service releveNotes : " + e.getMessage());
        }

//        String path = "C:\\Users\\pc\\Desktop\\RestServer";
        String pdfFilename = projectPath + "\\" + "ReleveDeNotes.pdf";
        PdfPTable table = getPdfTableNR();

        insertTableRN(table, moduleNotes);
        releveDeNotePdf(pdfFilename, moyenne, table,
                etudiant,
                idS, master.getDepartement(), master.getNom());

        File file = new File(pdfFilename);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachement; filename=" + pdfFilename + "").type("application/pdf");
        return response.build();

    }

    // service 1.6 PDF
    @GET
    @Path("attestationReussite/{idEtudiant}")
    @Produces("application/pdf")
    public Response attestationReussite(@PathParam("idEtudiant") Integer idE) throws Exception {

//        String path = "C:\\Users\\pc\\Desktop\\RestServer";
        String pdfFileName = "attestationReussite.pdf";

        MasterMention mm = moyenneMasterMention(idE);
        Masters m = req(idE);
        Etudiant e = etudiantFacadeREST.findById(idE);
        System.out.println("Masters" + m);
        creerAttestationReussitePDF(projectPath, pdfFileName, e.getNom(), e.getPrenom(), m.getNom(), mm.getMention(), e.getNumApogee());

        File file = new File(projectPath + "\\" + pdfFileName);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachement; filename=" + pdfFileName + "").type("application/pdf");
        return response.build();

    }

    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    /////////////////////                    ///////////////////////////
    /////////////////////   REST SERVICE II  ///////////////////////////
    /////////////////////                    ///////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
// servcice 01
    @GET
    @Path("moyenneSemestre/{idEtudiant}/{idSemstre}")
    @Produces(MediaType.TEXT_PLAIN)
    public double getMoyenneSemestre(@PathParam("idEtudiant") Integer idE, @PathParam("idSemstre") Integer idS) {
        double normale = 0.0, rattrapage = 0.0, somme = 0.0;
        List<Module> modules = moduleFacadeREST.findBySemstre(idS);
        System.out.println("----------------------------| getMoyenneSemestre |----------------------------");
        System.out.println("************* Modules : " + modules);
        int invalideCount = 0;
        try {
            for (Module m : modules) {
                Note n = noteFacadeREST.findByIdEtudiantAndIdModule(idE, m.getIdModule());

                try {
                    normale = n.getNoteNormale();
                } catch (Exception e) {
                    System.out.println("Note du normale est null !!");
                    return 0.0;
                }
                try {
                    rattrapage = n.getNoteRattrapage();
                } catch (Exception e) {
                    System.out.println("Note du rattrapage est null !!");
                }
                System.out.println("*****************Note : " + n + "==> N : " + n.getNoteNormale() + ", R : " + n.getNoteRattrapage());

                if (normale < 7 || (rattrapage < 7 && normale < 10)) {
                    return 0.0;
                } else if (normale < 10 && rattrapage < 10) {
                    invalideCount++;
                }

                somme += normale > rattrapage ? normale : rattrapage;

            }

            return invalideCount == 0 ? somme / modules.size() : 0.0;
        } catch (Exception e) {

            System.out.println("\n**** ERREUR dans le service getMoyenneSemestre : " + e.getMessage());
            return 0.0;
        }

    }

    //Service 02
    @GET
    @Path("moyenneAnnée/{idEtudiant}/{annee}")
    @Produces(MediaType.TEXT_PLAIN)
    public double moyenneAnnée(@PathParam("idEtudiant") Integer idE, @PathParam("annee") int an) {
        System.out.println("----------------------------| moyenneAnnée |----------------------------");
        int invalideCount = 0;
        double somme = 0.0;
        List<Module> modules;
        int s1, s2;
        if (an == 1) {
            s1 = 1;
            s2 = 2;
        } else {
            s1 = 3;
            s2 = 4;
        }
        modules = moduleFacadeREST.findBySemstres(s1, s2);
        System.out.println("************* Les semestres : s1=" + s1 + ", s2=" + s2);
        System.out.println("************* Nombre de  modules : " + modules.size());
        System.out.println("************* Modules : \n" + modules);

        try {
            for (Module m : modules) {
                Note n = noteFacadeREST.findByIdEtudiantAndIdModule(idE, m.getIdModule());
                System.out.println("*****************Note : " + n + "==> N : " + n.getNoteNormale() + ", R : " + n.getNoteRattrapage());
                double normale = 0.0, rattrapage = 0.0;

                try {
                    normale = n.getNoteNormale();
                } catch (Exception e) {
                    System.out.println("Note du normale est null !!");
                }
                try {
                    rattrapage = n.getNoteRattrapage();
                } catch (Exception e) {
                    System.out.println("Note du rattrapage est null !!");
                }
                // pour s'assurer que l'étudiant a passer au rattrapage et eviter le ca au ( normale = 17.0 > 7 mais rattrapage = 0.0 < 7 alors ==> le module est bien validé )
                if (normale < 7 || (normale < 10 && rattrapage < 7) && invalideCount > 1) {
                    return 0.0;
                } else if (normale < 10 && rattrapage < 10) {
                    invalideCount++;
                }
                //  ICi on doit vérifier les absences apres avoir modifier la BD  !!
                somme += normale > rattrapage ? normale : rattrapage;

            }
            return invalideCount > 1 ? 0.0 : (somme / modules.size());
        } catch (Exception e) {
            System.out.println("*** validationSemestre ERREUR : " + e.getLocalizedMessage());
        }
        return 0.0;

    }

    //service 03
    @GET
    @Path("moyenneMasterMention/{idEtudiant}")
    @Produces({MediaType.APPLICATION_XML})
    public MasterMention moyenneMasterMention(@PathParam("idEtudiant") Integer idE) throws Exception {
        System.out.println("----------------------------| moyenneMasterMention |----------------------------");

        try {
            double m = (moyenneAnnée(idE, 1) + moyenneAnnée(idE, 2)) / 2;
            MasterMention mm;
            mm = new MasterMention(m >= 18.0 ? "Excellent" : (m >= 16.0 ? "Très bien" : (m >= 14.0 ? "Bien" : (m >= 12.0 ? "Assez bien" : (m >= 10.0 ? "Passable" : "Invalide")))), m);
            return mm;
        } catch (Exception e) {
            throw new Exception("moyenneMaster service : L'étudiant n'a pas encore validé tous les semestres  !!");
        }
    }

    //service 04
    @GET
    @Path("validationSemestre/{idEtudiant}/{idSemstre}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean validationSemestre(@PathParam("idEtudiant") Integer idE, @PathParam("idSemstre") Integer idS) {
        System.out.println("----------------------------| validationSemestre |----------------------------");
        return (getMoyenneSemestre(idE, idS) > 10.0);
    }

    //service 05  validationAnnee
    @GET
    @Path("validationAnnee/{idEtudiant}/{annee}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean validationAnnée(@PathParam("idEtudiant") int idE, @PathParam("annee") int an) {
        System.out.println("----------------------------| validationAnnée |----------------------------");

        return moyenneAnnée(idE, an) >= 10.0;

    }

    ////////////////////////////////////////////////////////////////////         Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 5, Font.NORMAL, BaseColor.DARK_GRAY);
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    /////////////////////                    ///////////////////////////
    /////////////////////  REST SERVICE III  ///////////////////////////
    /////////////////////                    ///////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    //
    @GET
    @Path("SupprimerEtudiant/{CNE}")
    public String removeEtud(@PathParam("CNE") String CNE) {
        try {
            super.remove(findCne(CNE));

            return "true";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Etudiant findCne(String cne) {
        return (Etudiant) getEntityManager().createNamedQuery("Etudiant.findByCne").setParameter("cne", cne.trim()).getSingleResult();
    }

    @GET
    @Path("AjouterEtudiant/{Nom}/{Prenom}/{numApogee}/{Cin}/{Cne}/{Email}/{Telephone}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean Ajouter(@PathParam("Nom") String nom, @PathParam("Prenom") String prenom, @PathParam("numApogee") String numApogee, @PathParam("Cin") String cin, @PathParam("Cne") String cne, @PathParam("Email") String email, @PathParam("Telephone") String telephone) {
        try {
            Etudiant etudiant = new Etudiant(nom, prenom, numApogee, cin, cne, email, telephone);
            super.create(etudiant);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    ///////////////////////////////////     
    ///////////////////////////////////     
    ////////                   ////////     
    ////////  Les fonctions    ////////     
    ////////                   ////////     
    ///////////////////////////////////     
    ///////////////////////////////////     
    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);

        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }

    // Semstre 03 Master M2I
    public void insertCellForNoteTop(PdfPTable table, String text, int align, int colspan, int rowspan, Font font, boolean disableBorderSide) {
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        if (disableBorderSide) {
            cell.disableBorderSide(PdfPCell.TOP);
        }
        //in case there is no text and you wan to create an empty row

        //add the call to the table
        table.addCell(cell);
    }

    public void insertCellForNoteButtom(PdfPTable table, String text, int align, int colspan, int rowspan, Font font, boolean disableBorderSide) {
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setPaddingTop(15);
        if (disableBorderSide) {
            cell.disableBorderSide(PdfPCell.BOTTOM);
        }
        //in case there is no text and you wan to create an empty row

        //add the call to the table
        table.addCell(cell);
    }

    public PdfPTable getPdftable() {
        Font NR = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD, BaseColor.BLACK);
        Font cneFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD, BaseColor.BLUE);
        Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 5, Font.NORMAL, BaseColor.DARK_GRAY);

        float[] columnWidths = {1.7f, 1.6f, 1.6f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.setWidthPercentage(97f);

        //insert column headings
        insertCellForNoteButtom(table, "CNE", Element.ALIGN_CENTER, 1, 1, NR, true);
        insertCellForNoteButtom(table, "Nom", Element.ALIGN_CENTER, 1, 1, NR, true);
        insertCellForNoteButtom(table, "Prenom", Element.ALIGN_CENTER, 1, 1, NR, true);

        insertCell(table, "M1", Element.ALIGN_CENTER, 2, NR);
        insertCell(table, "M2", Element.ALIGN_CENTER, 2, NR);

        insertCell(table, "M3", Element.ALIGN_CENTER, 2, NR);
        insertCell(table, "M4", Element.ALIGN_CENTER, 2, NR);

        insertCell(table, "M5", Element.ALIGN_CENTER, 2, NR);
        insertCell(table, "M6", Element.ALIGN_CENTER, 2, NR);

        insertCellForNoteTop(table, " ", Element.ALIGN_RIGHT, 1, 1, NR, true);
        insertCellForNoteTop(table, " ", Element.ALIGN_LEFT, 1, 1, NR, true);
        insertCellForNoteTop(table, " ", Element.ALIGN_LEFT, 1, 1, NR, true);
//            insertCell(table, "", Element.ALIGN_MIDDLE, 3, NR);
        insertCell(table, "N", Element.ALIGN_CENTER, 1, NR);
        insertCell(table, "R", Element.ALIGN_CENTER, 1, NR);

        insertCell(table, "N", Element.ALIGN_CENTER, 1, NR);
        insertCell(table, "R", Element.ALIGN_CENTER, 1, NR);

        insertCell(table, "N", Element.ALIGN_CENTER, 1, NR);
        insertCell(table, "R", Element.ALIGN_CENTER, 1, NR);

        insertCell(table, "N", Element.ALIGN_CENTER, 1, NR);
        insertCell(table, "R", Element.ALIGN_CENTER, 1, NR);

        insertCell(table, "N", Element.ALIGN_CENTER, 1, NR);
        insertCell(table, "R", Element.ALIGN_CENTER, 1, NR);

        insertCell(table, "N", Element.ALIGN_CENTER, 1, NR);
        insertCell(table, "R", Element.ALIGN_CENTER, 1, NR);

        table.setHeaderRows(1);
        return table;
    }

    public PdfPTable insertTable(PdfPTable table, String cne, Font font, String nom, String prenom, String n1, String r1, String n2, String r2, String n3, String r3, String n4, String r4, String n5, String r5, String n6, String r6) {

        Font cneFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD, BaseColor.BLUE);

        insertCell(table, cne, Element.ALIGN_LEFT, 1, cneFont);
        insertCell(table, nom, Element.ALIGN_LEFT, 1, font);
        insertCell(table, prenom, Element.ALIGN_LEFT, 1, font);
        insertCell(table, n1, Element.ALIGN_LEFT, 1, font);
        insertCell(table, r1, Element.ALIGN_LEFT, 1, font);
        insertCell(table, n2, Element.ALIGN_LEFT, 1, font);
        insertCell(table, r2, Element.ALIGN_LEFT, 1, font);
        insertCell(table, n3, Element.ALIGN_LEFT, 1, font);
        insertCell(table, r3, Element.ALIGN_LEFT, 1, font);
        insertCell(table, n4, Element.ALIGN_LEFT, 1, font);
        insertCell(table, r4, Element.ALIGN_LEFT, 1, font);
        insertCell(table, n5, Element.ALIGN_LEFT, 1, font);
        insertCell(table, r5, Element.ALIGN_LEFT, 1, font);
        insertCell(table, n6, Element.ALIGN_LEFT, 1, font);
        insertCell(table, r6, Element.ALIGN_LEFT, 1, font);

        return table;
    }

    public void notesPdf(String pdfFilename, PdfPTable table, Masters master, int semestre) {

        Document doc = new Document();
        PdfWriter docWriter = null;

//        DecimalFormat df = new DecimalFormat("0.00");
        try {

            //special font sizes
            Font Bold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            Font Bold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);
            Font NR = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD, BaseColor.BLACK);
            Font cneFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD, BaseColor.BLUE);
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.DARK_GRAY);

            //file path
            String path = projectPath + "\\" + pdfFilename;
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));

            //document header attributes
            doc.addAuthor("betterThanZero");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("MySampleCode.com");
            doc.addTitle("Report with Column Headings");
            doc.setPageSize(PageSize.LETTER);

            //open document
            doc.open();

            //create a paragraph
            Paragraph paragraph0 = new Paragraph("Departement : " + master.getDepartement() + "", Bold12);
//            paragraph0.add(paragraph1) ;//
            paragraph0.setAlignment(Paragraph.ALIGN_CENTER);
            paragraph0.add(new Paragraph("\n\n", Bold10));

            Paragraph paragraph2 = new Paragraph(String.format("%-10s  :   %-10s %n", "Master", master.getNom()), Bold10);
//            Paragraph paragraph4 = new Paragraph("    Semestre  : " + semestre + "\n", Bold10);
            Paragraph paragraph4 = new Paragraph(String.format("%-9s  :   %-10s %n", "Semestre", semestre), Bold10);

//            Paragraph paragraph2 = new Paragraph("    Master : " + master.getNom() + "\n", Bold10);
//             paragraph2.add( new Paragraph(" " + master.getNom()+"" ,bf12));
//            paragraph2.add(paragraph3);
//            Paragraph paragraph4 = new Paragraph("    Semestre : " + semestre.getIdSemestre() + "\n", Bold10);
            paragraph4.add(new Paragraph("\n\n", Bold10));
//            paragraph4.add( new Paragraph(" " + semestre.getIdSemestre(), bf12) );

//            insertCell(table, "California Total...", Element.ALIGN_RIGHT, 3, bfBold12);
//            insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bfBold12);
            //add the PDF table to the paragraph 
            // add the paragraph to the document
            doc.add(paragraph0);
            doc.add(paragraph2);
            doc.add(paragraph4);

            doc.add(table);
//            doc.add(paragraph4);
        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
//        return doc;
    }

// etudiant
    public Etudiant findById(int id) {

        Query q = getEntityManager().createNamedQuery("Etudiant.findByIdetudiant").setParameter("idetudiant", id);
//         notes = q.getResultList();
        return (Etudiant) q.getSingleResult();
    }

    // methode du services 1.5 PDF
    public PdfPTable insertTableRN(PdfPTable table, List<ModuleNote> list) {

        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.DARK_GRAY);

        for (ModuleNote mn : list) {
            insertCell(table, mn.getModule(), Element.ALIGN_LEFT, 1, font);
            if (mn.getNoteNormale() >= mn.getNoteRattarapage() && mn.getNoteNormale() >= 10) {
                insertCell(table, "" + mn.getNoteNormale(), Element.ALIGN_LEFT, 1, font);
                insertCell(table, "V", Element.ALIGN_CENTER, 1, font);
            } else if (mn.getNoteNormale() < mn.getNoteRattarapage() && mn.getNoteRattarapage() >= 10) {
                insertCell(table, "" + mn.getNoteRattarapage(), Element.ALIGN_LEFT, 1, font);
                insertCell(table, "VAR", Element.ALIGN_CENTER, 1, font);

            } else {
                insertCell(table, mn.getNoteNormale() > mn.getNoteRattarapage() ? "" + mn.getNoteNormale() : "" + mn.getNoteRattarapage(), Element.ALIGN_LEFT, 1, font);
                insertCell(table, "NV", Element.ALIGN_CENTER, 1, font);
            }
        }

        return table;
    }

    public PdfPTable getPdfTableNR() {
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);

        float[] columnWidths = {2.0f, 2.0f, 2.0f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.setWidthPercentage(75f);

        //insert column headings
//        insertCellForNoteButtom(table, "CNE", Element.ALIGN_CENTER, 1, 1, NR, true);
//        insertCellForNoteButtom(table, "Nom", Element.ALIGN_CENTER, 1, 1, NR, true);
//        insertCellForNoteButtom(table, "Prenom", Element.ALIGN_CENTER, 1, 1, NR, true);
        insertCell(table, "Module", Element.ALIGN_CENTER, 1, headerFont);
        insertCell(table, "Note", Element.ALIGN_CENTER, 1, headerFont);
        insertCell(table, "Validation", Element.ALIGN_CENTER, 1, headerFont);
        table.setHeaderRows(1);
        return table;
    }

    public void releveDeNotePdf(String pdfFilename, Double moyenne, PdfPTable table, Etudiant etudiant, int semestre, String departement, String master) {

        Document doc = new Document();
        PdfWriter docWriter = null;

//        DecimalFormat df = new DecimalFormat("0.00");
        try {

            //special font sizes
            Font Bold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            Font Bold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);
            Font NR = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD, BaseColor.BLACK);
            Font cneFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD, BaseColor.BLUE);
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.DARK_GRAY);

            //file path
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(pdfFilename));

            //document header attributes
            doc.addAuthor("betterThanZero");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("Ouahiba And Sohaib");
            doc.addTitle("Report with Column Headings");
            doc.setPageSize(PageSize.LETTER);

            //open document
            doc.open();
            //create a paragraph
            Paragraph paragraph1 = new Paragraph("Relevé de notes", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.BLACK));
            paragraph1.add(new Paragraph("\n", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK)));

            paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph paragraph2 = new Paragraph("Departement : " + departement + "", Bold12);
//            paragraph0.add(paragraph1) ;//
            paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph2.add(new Paragraph("\n", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK)));

//            Paragraph paragraph3 = new Paragraph("    Master    : " + master + "\n", Bold10);
            Paragraph paragraph3 = new Paragraph(String.format("%-20s  :   %-20s %n", "Master", master), Bold10);
//            Paragraph paragraph4 = new Paragraph("    Semestre  : " + semestre + "\n", Bold10);
            Paragraph paragraph4 = new Paragraph(String.format("%-19s  :   %-20s %n", "Semestre", semestre), Bold10);
            paragraph4.add(new Paragraph("\n", Bold10));

            Paragraph paragraph5 = new Paragraph(String.format("%-25s  :   %-20s %n", "Nom", etudiant.getNom()), Bold10);

            Paragraph paragraph6 = new Paragraph(String.format("%-23s  :   %-20s %n", "Prenom", etudiant.getPrenom()), Bold10);
            Paragraph paragraph7 = new Paragraph(String.format("%-27s  :   %-20s %n", "Cin", etudiant.getCin()), Bold10);
            Paragraph paragraph8 = new Paragraph(String.format("%-26.8s  :   %-20s %n", "Cne", etudiant.getCne()), Bold10);
            Paragraph paragraph9 = new Paragraph(String.format("%-21s  :   %-20s %n", "N° Apogée", etudiant.getNumApogee()), Bold10);

//            doc.add(new Phrase(String.format("%-30s     :  %-20s %n", "Nom", etudiant.getNom()), Bold10)); //"Elbokhadri    : sohaib", Bold10));
//            doc.add(new Phrase(String.format("%-28s     :  %-20s %n", "Prenom", etudiant.getPrenom()), Bold10)); //"Elbokhadri    : sohaib", Bold10));
//            doc.add(new Phrase(String.format("%-32s     :  %-20s %n", "Cin", etudiant.getCin()), Bold10)); //"Elbokhadri    : sohaib", Bold10));
//            doc.add(new Phrase(String.format("%-31s     :  %-20s %n", "Cne", etudiant.getCne()), Bold10)); //"Elbokhadri    : sohaib", Bold10));
//            doc.add(new Phrase(String.format("%-26s     :  %-20s %n", "N° Apogée", etudiant.getNumApogee()))); //"Elbokhadri    : sohaib", Bold10));
            paragraph9.add(new Paragraph("\n", Bold10));

            paragraph3.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph4.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph5.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph6.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph7.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph8.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph9.setAlignment(Paragraph.ALIGN_LEFT);

            doc.add(paragraph1);
            doc.add(paragraph2);
            doc.add(paragraph3);
            doc.add(paragraph4);
            doc.add(paragraph5);
            doc.add(paragraph6);
            doc.add(paragraph7);
            doc.add(paragraph8);
            doc.add(paragraph9);

            doc.add(table);
            doc.add(new Paragraph("\n\n", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("Moyenne générale  : " + new DecimalFormat("##.##").format(moyenne), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK)));

        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
    }

    public PdfPTable insertListEtudiants(PdfPTable table, List<Etudiant> list) {

        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.DARK_GRAY);

        for (Etudiant e : list) {
            insertCell(table, e.getNumApogee(), Element.ALIGN_LEFT, 1, font);
            insertCell(table, e.getCne(), Element.ALIGN_CENTER, 1, font);
            insertCell(table, e.getNom(), Element.ALIGN_CENTER, 1, font);
            insertCell(table, e.getPrenom(), Element.ALIGN_CENTER, 1, font);

        }

        return table;
    }

    public PdfPTable getPdfListEtudiants() {
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);

        float[] columnWidths = {1f, 1f, 1f, 1f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.setWidthPercentage(70f);

        //insert column headings
        insertCell(table, "Apogée", Element.ALIGN_CENTER, 1, headerFont);
        insertCell(table, "CNE", Element.ALIGN_CENTER, 1, headerFont);
        insertCell(table, "Nom", Element.ALIGN_CENTER, 1, headerFont);
        insertCell(table, "Prénom", Element.ALIGN_CENTER, 1, headerFont);

        table.setHeaderRows(1);
        return table;
    }

    public void ListEtudiantsPdf(String pdfFilename, PdfPTable table, int semestre, String departement, String master) {

        Document doc = new Document();
        PdfWriter docWriter = null;

//        DecimalFormat df = new DecimalFormat("0.00");
        try {

            //special font sizes
            Font Bold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            Font Bold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);
            Font NR = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD, BaseColor.BLACK);
            Font cneFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD, BaseColor.BLUE);
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.DARK_GRAY);

            //file path
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(pdfFilename));

            //document header attributes
            doc.addAuthor("betterThanZero");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("Ouahiba And Sohaib");
            doc.addTitle("Report with Column Headings");
            doc.setPageSize(PageSize.LETTER);

            //open document
            doc.open();
            //create a paragraph
            Paragraph paragraph1 = new Paragraph("Liste des étudiants", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.BLACK));
            paragraph1.add(new Paragraph("\n", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK)));

            paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph paragraph2 = new Paragraph("Département : " + departement + "", Bold12);
//            paragraph0.add(paragraph1) ;//

//            Paragraph paragraph3 = new Paragraph("    Master    : " + master + "\n", Bold10);
            Paragraph paragraph3 = new Paragraph(String.format("%-20s  :   %-20s %n", "Master", master), Bold10);
//            Paragraph paragraph4 = new Paragraph("    Semestre  : " + semestre + "\n", Bold10);
            Paragraph paragraph4 = new Paragraph(String.format("%-19s  :   %-20s %n", "Semestre", semestre), Bold10);
            paragraph4.add(new Paragraph("\n", Bold10));
            doc.add(paragraph1);
            doc.add(paragraph2);
            doc.add(paragraph3);
            doc.add(paragraph4);

            doc.add(table);

        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
    }
    // service 1.6 PDF

    public void creerAttestationReussitePDF(String path, String pdfFileName, String nom, String prenom, String nomMaster, String mention, String apogee) throws Exception {

        Document doc = new Document();

//        DecimalFormat df = new DecimalFormat("0.00");
        //special font sizes
        Font f15BoldBlack = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK);
        Font f14BoldDarckGray = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.DARK_GRAY);
        Font f14BoldBlack = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
        Font f18BoldBlack = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);

        //file path
        PdfWriter docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path + "\\" + pdfFileName));

        doc.addAuthor("betterThanZero");
        doc.addCreationDate();
        doc.addProducer();
        doc.addCreator("Ouahiba And Sohaib");
        doc.addTitle("Report with Column Headings");
        doc.setPageSize(PageSize.LETTER);

        //open document
        doc.open();
        //create a paragraph
        String universite = "Université Mohammed Premier Oujda";
        String titre = "Attestation de réussite au diplome".toUpperCase();
        String doyen = "Le doyen de l'université mohammed premier".toUpperCase() + " atteste que";
//        String etudiant0 = "Le doyen de l'université mohammed premier".toUpperCase() + " atteste que";
//        String etudiant1 = "le Diplome de Master Spécialisé Ingénierie Informatique";
        String etudiant1 = "le Diplome de " + nomMaster + "";
        String etudiant2 = "a été décerné à";
        String etudiant3 = "Monsieur " + nom.toUpperCase() + " " + prenom.toUpperCase();
//        String etudiant4 = "au titre de l'année universitaire 2020/2021 avec la mention Assez Bien";
        String etudiant4 = "au titre de l'année universitaire 2020/2021 avec la mention ";

        Phrase ph6 = new Phrase("" + mention, f14BoldBlack);

        PdfPTable t = new PdfPTable(new float[]{10.0f});

        Paragraph p1 = new Paragraph(universite, f15BoldBlack);
        p1.setAlignment(Element.ALIGN_CENTER);
        doc.add(p1);

        doc.add(new Paragraph("\n", f14BoldDarckGray));
        // creation d'une cellule
        PdfPCell cell = new PdfPCell(new Phrase(titre, f18BoldBlack));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(10.0f);

        if (titre.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        t.addCell(cell);
//        insertCell(t, titre, f18BoldBlack);// diplome
        doc.add(t);
        doc.add(new Paragraph("\n", f14BoldDarckGray));
        doc.add(new Paragraph("\n", f14BoldDarckGray));
        doc.add(new Paragraph("\n", f14BoldDarckGray));

        Paragraph p2 = new Paragraph(doyen, f14BoldDarckGray);
        p2.setAlignment(Element.ALIGN_CENTER);
        doc.add(p2);

        Paragraph p3 = new Paragraph(etudiant1, f14BoldBlack);
        p3.setAlignment(Element.ALIGN_CENTER);
        doc.add(p3);

        Paragraph p4 = new Paragraph(etudiant2, f14BoldDarckGray);
        p4.setAlignment(Element.ALIGN_CENTER);
        doc.add(p4);

        Paragraph p5 = new Paragraph(etudiant3, f14BoldBlack);
        p5.setAlignment(Element.ALIGN_CENTER);
        doc.add(p5);

        Paragraph p6 = new Paragraph(etudiant4, f14BoldDarckGray);
        p6.setAlignment(Element.ALIGN_CENTER);
        p6.add(ph6);
        doc.add(p6);

        doc.add(new Paragraph("\n\n\n\n\n\n", f14BoldDarckGray));

        Phrase ph1 = new Phrase("Fait le : ", f15BoldBlack);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(" dd/MM/yyyy");
        LocalDateTime date = LocalDateTime.now();

        Phrase ph2 = new Phrase("   " + dtf.format(date), f14BoldDarckGray);

        Paragraph p7 = new Paragraph();

        p7.add(ph1);
        p7.add(ph2);
        p7.setAlignment(Element.ALIGN_RIGHT);

        doc.add(p7);

        doc.add(new Paragraph("\n\n\n\n", f14BoldDarckGray));

        Phrase ph3 = new Phrase("  N° appogée d'étudiant  :  ", f15BoldBlack);
        Phrase ph4 = new Phrase("  " + apogee + " ", f14BoldDarckGray);

        Paragraph p8 = new Paragraph();

        p8.add(ph3);
        p8.add(ph4);
        p8.setAlignment(Element.ALIGN_LEFT);

        doc.add(p8);

        doc.close();

    }
    // requete req pour service 1.6 PDF

    public Masters req(int idE) {

        Query q = getEntityManager().createQuery("select distinct  ms  FROM Masters ms, Semestre s,Note n,Module m,Etudiant e\n"
                + "WHERE \n"
                + "n.notePK.idEtudiant=" + idE + " AND\n"
                + "m.idModule=n.notePK.idModule       AND\n"
                + "s.idSemestre=m.idSmester AND\n"
                + "ms.idMaster = s.idMaster ");
//         notes = q.getResultList();
        return (Masters) q.getSingleResult();
    }

    ////////// Ouahiba
    @GET
    @Path("findByApogee/{Apogee}")
    @Produces("APPLICATION/XML")
    public Etudiant findApogee(@PathParam("Apogee") String Apogee) {
        return (Etudiant) getEntityManager().createNamedQuery("Etudiant.findByNumApogee").setParameter("numApogee", Apogee).getSingleResult();
    }
    

}
