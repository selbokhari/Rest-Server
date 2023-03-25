/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "masters")
@XmlRootElement(name = "Master")
@NamedQueries({
    @NamedQuery(name = "Masters.findAll", query = "SELECT m FROM Masters m")
    , @NamedQuery(name = "Masters.findByIdMaster", query = "SELECT m FROM Masters m WHERE m.idMaster = :idMaster")
    , @NamedQuery(name = "Masters.findByNom", query = "SELECT m FROM Masters m WHERE m.nom = :nom")
    , @NamedQuery(name = "Masters.findByCordinateur", query = "SELECT m FROM Masters m WHERE m.cordinateur = :cordinateur")
    , @NamedQuery(name = "Masters.findByDepartement", query = "SELECT m FROM Masters m WHERE m.departement = :departement")})
public class Masters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_master")
    private Integer idMaster;
    @Size(max = 25)
    @Column(name = "nom")

    private String nom;
    @Size(max = 50)
    @Column(name = "cordinateur")

    private String cordinateur;
    @Size(max = 50)
    @Column(name = "departement")

    private String departement;

    public Masters() {
    }

    public Masters(Integer idMaster, String nom, String cordinateur, String departement) {
        this.idMaster = idMaster;
        this.nom = nom;
        this.cordinateur = cordinateur;
        this.departement = departement;
    }

    public Integer getIdMaster() {
        return idMaster;
    }

    public void setIdMaster(Integer idMaster) {
        this.idMaster = idMaster;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCordinateur() {
        return cordinateur;
    }

    public void setCordinateur(String cordinateur) {
        this.cordinateur = cordinateur;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMaster != null ? idMaster.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Masters)) {
            return false;
        }
        Masters other = (Masters) object;
        if ((this.idMaster == null && other.idMaster != null) || (this.idMaster != null && !this.idMaster.equals(other.idMaster))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Masters{" + "idMaster=" + idMaster + ", nom=" + nom + ", cordinateur=" + cordinateur + ", departement=" + departement + '}';
    }

}
