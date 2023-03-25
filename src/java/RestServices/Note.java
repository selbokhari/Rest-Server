/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "note")
@XmlRootElement
@NamedQueries({
     @NamedQuery(name ="Note.Modifier", query = "UPDATE Note n SET n.noteNormale=:notenormale , n.noteRattrapage=:noterattrapage WHERE n.notePK.idEtudiant=:idEtudiant AND n.notePK.idModule=:idModule")
    ,@NamedQuery(name = "Note.findAll", query = "SELECT n FROM Note n")
    , @NamedQuery(name = "Note.findIdEtudiant", query = "SELECT n.notePK.idEtudiant FROM Note n WHERE n.notePK.idModule = :idModule")
    , @NamedQuery(name = "Note.findByIdEtudiant", query = "SELECT n FROM Note n WHERE n.notePK.idEtudiant = :idEtudiant")
    , @NamedQuery(name = "Note.findByIdEtudiantAndIdModule", query = "SELECT n FROM Note n WHERE n.notePK.idEtudiant = :idEtudiant and  n.notePK.idModule = :idModule")
    , @NamedQuery(name = "Note.findByIdModule", query = "SELECT n FROM Note n WHERE n.notePK.idModule = :idModule")
    , @NamedQuery(name = "Note.findByNoteNormale", query = "SELECT n FROM Note n WHERE n.noteNormale = :noteNormale")
    , @NamedQuery(name = "Note.findByNoteRattrapage", query = "SELECT n FROM Note n WHERE n.noteRattrapage = :noteRattrapage")})
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotePK notePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "noteNormale")
    private Double noteNormale;
    @Column(name = "noteRattrapage")
    private Double noteRattrapage;

    public Note() {
    }

    public Note(NotePK notePK) {
        this.notePK = notePK;
    }

    public Note(int idEtudiant, int idModule) {
        this.notePK = new NotePK(idEtudiant, idModule);
    }

    public Note(int idEtudiant, int idModule, Double n, Double r) {
        this.noteNormale = n;
        this.noteRattrapage = r;
        this.notePK = new NotePK(idEtudiant, idModule);
    }

    public NotePK getNotePK() {
        return notePK;
    }

    public void setNotePK(NotePK notePK) {
        this.notePK = notePK;
    }

    public Double getNoteNormale() {
        return noteNormale;
    }

    public void setNoteNormale(Double noteNormale) {
        this.noteNormale = noteNormale;
    }

    public Double getNoteRattrapage() {
        return noteRattrapage;
    }

    public void setNoteRattrapage(Double noteRattrapage) {
        this.noteRattrapage = noteRattrapage;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notePK != null ? notePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Note)) {
            return false;
        }
        Note other = (Note) object;
        if ((this.notePK == null && other.notePK != null) || (this.notePK != null && !this.notePK.equals(other.notePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Note{" + "notePK=" + notePK + ", noteNormale=" + noteNormale + ", noteRattrapage=" + noteRattrapage + '}';
    }

   
}
