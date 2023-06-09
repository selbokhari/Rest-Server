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
 * @author Ouahiba
 */
@Entity
@Table(name = "compteadmin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name ="Compteadmin.ModifierMP", query = "UPDATE  Compteadmin c SET c.motdePasse=:motdePasse WHERE c.nom=:nom")
    , @NamedQuery(name = "Compteadmin.login", query = "SELECT c FROM Compteadmin c WHERE c.nom = :nom AND c.motdePasse=:motdePasse")
    ,@NamedQuery(name = "Compteadmin.findAll", query = "SELECT c FROM Compteadmin c")
    , @NamedQuery(name = "Compteadmin.findById", query = "SELECT c FROM Compteadmin c WHERE c.id = :id")
    , @NamedQuery(name = "Compteadmin.findByNom", query = "SELECT c FROM Compteadmin c WHERE c.nom = :nom")
    , @NamedQuery(name = "Compteadmin.findByMotdePasse", query = "SELECT c FROM Compteadmin c WHERE c.motdePasse = :motdePasse")})
public class Compteadmin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "nom")
    private String nom;
    @Size(max = 50)
    @Column(name = "mot_de_Passe")
    private String motdePasse;

    public Compteadmin() {
    }

    public Compteadmin(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotdePasse() {
        return motdePasse;
    }

    public void setMotdePasse(String motdePasse) {
        this.motdePasse = motdePasse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compteadmin)) {
            return false;
        }
        Compteadmin other = (Compteadmin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RestServices.Compteadmin[ id=" + id + " ]";
    }
    
}
