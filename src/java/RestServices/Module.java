/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "module")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m")
    , @NamedQuery(name = "Module.findByIdModule", query = "SELECT m FROM Module m WHERE m.idModule = :idModule")
    , @NamedQuery(name = "Module.findByNom", query = "SELECT m FROM Module m WHERE m.nom = :nom")
    , @NamedQuery(name = "Module.findByIdSmester", query = "SELECT m FROM Module m WHERE m.idSmester = :idSmester")
    , @NamedQuery(name = "Module.findByIdSmesters", query = "SELECT m FROM Module m WHERE m.idSmester = :idSmester1 OR m.idSmester = :idSmester2 ")})
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_module")
    private Integer idModule;
    @Size(max = 50)
    @Column(name = "nom")
    private String nom;
    @Column(name = "id_smester")
    private Integer idSmester;

    public Module() {
    }

    public Module(Integer idModule) {
        this.idModule = idModule;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getIdSmester() {
        return idSmester;
    }

    public void setIdSmester(Integer idSmester) {
        this.idSmester = idSmester;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModule != null ? idModule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.idModule == null && other.idModule != null) || (this.idModule != null && !this.idModule.equals(other.idModule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RestServices.Module[ idModule=" + idModule + " ]";
    }
    
}
