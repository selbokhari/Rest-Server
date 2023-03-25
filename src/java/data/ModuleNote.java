/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@XmlRootElement(name="Module")

public class ModuleNote {

    String Module;
    Double NoteNormale;
    Double NoteRattarapage;

    public ModuleNote() {
    }
    
    public ModuleNote(String Module, Double NoteNormale, Double NoteRattarapage) {
        this.Module = Module;
        this.NoteNormale = NoteNormale;
        this.NoteRattarapage = NoteRattarapage;
    }

    public String getModule() {
        return Module;
    }

    public void setModule(String Module) {
        this.Module = Module;
    }

    public Double getNoteNormale() {
        return NoteNormale;
    }

    public void setNoteNormale(Double NoteNormale) {
        this.NoteNormale = NoteNormale;
    }

    public Double getNoteRattarapage() {
        return NoteRattarapage;
    }

    public void setNoteRattarapage(Double NoteRattarapage) {
        this.NoteRattarapage = NoteRattarapage;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.Module);
        hash = 43 * hash + Objects.hashCode(this.NoteNormale);
        hash = 43 * hash + Objects.hashCode(this.NoteRattarapage);
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
        final ModuleNote other = (ModuleNote) obj;
        if (!Objects.equals(this.Module, other.Module)) {
            return false;
        }
        if (!Objects.equals(this.NoteNormale, other.NoteNormale)) {
            return false;
        }
        if (!Objects.equals(this.NoteRattarapage, other.NoteRattarapage)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModuleNote{" + "Module=" + Module + ", NoteNormale=" + NoteNormale + ", NoteRattarapage=" + NoteRattarapage + '}';
    }
    
    
   


}
