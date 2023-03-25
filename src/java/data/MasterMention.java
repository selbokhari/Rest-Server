/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.text.DecimalFormat;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@XmlRootElement(name = "MentionMaster")
public class MasterMention {

    private String mention;
    private double moyenne;

    public MasterMention() {
    }

    public MasterMention(String mention, double moyenne) {
        DecimalFormat dc = new DecimalFormat("##.##");

        moyenne = Double.parseDouble(dc.format(moyenne).replace(",", "."));

        this.mention = mention;
        this.moyenne = moyenne;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.mention);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.moyenne) ^ (Double.doubleToLongBits(this.moyenne) >>> 32));
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
        final MasterMention other = (MasterMention) obj;
        if (Double.doubleToLongBits(this.moyenne) != Double.doubleToLongBits(other.moyenne)) {
            return false;
        }
        if (!Objects.equals(this.mention, other.mention)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MasterMention{" + "mention=" + mention + ", moyenne=" + moyenne + '}';
    }

}
