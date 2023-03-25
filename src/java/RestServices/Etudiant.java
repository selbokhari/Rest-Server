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
@Table(name = "etudiant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etudiant.findAll", query = "SELECT e FROM Etudiant e")
    , @NamedQuery(name = "Etudiant.findByIdetudiant", query = "SELECT e FROM Etudiant e WHERE e.idetudiant = :idetudiant")
    , @NamedQuery(name = "Etudiant.findByNom", query = "SELECT e FROM Etudiant e WHERE e.nom = :nom")
    , @NamedQuery(name = "Etudiant.findByPrenom", query = "SELECT e FROM Etudiant e WHERE e.prenom = :prenom")
    , @NamedQuery(name = "Etudiant.findByNumApogee", query = "SELECT e FROM Etudiant e WHERE e.numApogee = :numApogee")
    , @NamedQuery(name = "Etudiant.findByCin", query = "SELECT e FROM Etudiant e WHERE e.cin = :cin")
    , @NamedQuery(name = "Etudiant.findByCne", query = "SELECT e FROM Etudiant e WHERE e.cne = :cne")
    , @NamedQuery(name = "Etudiant.findByEmail", query = "SELECT e FROM Etudiant e WHERE e.email = :email")
    , @NamedQuery(name = "Etudiant.findByT\u00e9lephone", query = "SELECT e FROM Etudiant e WHERE e.t\u00e9lephone = :t\u00e9lephone")})
public class Etudiant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id_etudiant")
    private Integer idetudiant;
    @Size(max = 50)
    @Column(name = "nom")
    private String nom;
    @Size(max = 50)
    @Column(name = "prenom")
    private String prenom;
    @Size(max = 50)
    @Column(name = "numApogee")
    private String numApogee;
    @Size(max = 50)
    @Column(name = "cin")
    private String cin;
    @Size(max = 25)
    @Column(name = "cne")
    private String cne;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 60)
    @Column(name = "email")
    private String email;
    @Size(max = 10)
    @Column(name = "T\u00e9lephone")
    private String télephone;

    public Etudiant() {
    }

    public Etudiant(Integer idetudiant, String nom, String prenom, String numApogee, String cin, String cne, String email, String télephone) {
        this.idetudiant = idetudiant;
        this.nom = nom;
        this.prenom = prenom;
        this.numApogee = numApogee;
        this.cin = cin;
        this.cne = cne;
        this.email = email;
        this.télephone = télephone;
    }
    public Etudiant( String nom, String prenom, String numApogee, String cin, String cne, String email, String télephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.numApogee = numApogee;
        this.cin = cin;
        this.cne = cne;
        this.email = email;
        this.télephone = télephone;
    }

    public Etudiant(Integer idetudiant) {
        this.idetudiant = idetudiant;
    }

    public Integer getIdetudiant() {
        return idetudiant;
    }

    public void setIdetudiant(Integer idetudiant) {
        this.idetudiant = idetudiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumApogee() {
        return numApogee;
    }

    public void setNumApogee(String numApogee) {
        this.numApogee = numApogee;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTélephone() {
        return télephone;
    }

    public void setTélephone(String télephone) {
        this.télephone = télephone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idetudiant != null ? idetudiant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etudiant)) {
            return false;
        }
        Etudiant other = (Etudiant) object;
        if ((this.idetudiant == null && other.idetudiant != null) || (this.idetudiant != null && !this.idetudiant.equals(other.idetudiant))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Etudiant{" + "idetudiant=" + idetudiant + ", nom=" + nom + ", prenom=" + prenom + ", numApogee=" + numApogee + ", cin=" + cin + ", cne=" + cne + ", email=" + email + ", t\u00e9lephone=" + télephone + '}';
    }

    
    
    
}
