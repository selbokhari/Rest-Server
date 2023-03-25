/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import RestServices.Etudiant;
import RestServices.Masters;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@XmlRootElement(name = "ReleveDeNotes")
public class ReleveNotes {

    String Departement;
    String Master;
    Etudiant Etudiant;
    List<ModuleNote> ModuleNote;
    Double moyenne;

    public ReleveNotes() {
    }

    public ReleveNotes(String Departement, String Master, Etudiant Etudiant, List<ModuleNote> ModuleNote, Double moyenne) {
        this.Departement = Departement;
        this.Master = Master;
        this.Etudiant = Etudiant;
        this.ModuleNote = ModuleNote;
        this.moyenne = moyenne;
    }

    public String getDepartement() {
        return Departement;
    }

    public void setDepartement(String Departement) {
        this.Departement = Departement;
    }

    public String getMaster() {
        return Master;
    }

    public void setMaster(String Master) {
        this.Master = Master;
    }

    public Etudiant getEtudiant() {
        return Etudiant;
    }

    public void setEtudiant(Etudiant Etudiant) {
        this.Etudiant = Etudiant;
    }

    public List<ModuleNote> getModuleNote() {
        return ModuleNote;
    }

    public void setModuleNote(List<ModuleNote> ModuleNote) {
        this.ModuleNote = ModuleNote;
    }

    public Double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Double moyenne) {
        this.moyenne = moyenne;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.Departement);
        hash = 13 * hash + Objects.hashCode(this.Master);
        hash = 13 * hash + Objects.hashCode(this.Etudiant);
        hash = 13 * hash + Objects.hashCode(this.ModuleNote);
        hash = 13 * hash + Objects.hashCode(this.moyenne);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReleveNotes other = (ReleveNotes) obj;
        if (!Objects.equals(this.Departement, other.Departement)) {
            return false;
        }
        if (!Objects.equals(this.Master, other.Master)) {
            return false;
        }
        if (!Objects.equals(this.Etudiant, other.Etudiant)) {
            return false;
        }
        if (!Objects.equals(this.ModuleNote, other.ModuleNote)) {
            return false;
        }
        if (!Objects.equals(this.moyenne, other.moyenne)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReleveNotes{" + "Departement=" + Departement + ", Master=" + Master + ", Etudiant=" + Etudiant + ", ModuleNote=" + ModuleNote + ", moyenne=" + moyenne + '}';
    }

}
