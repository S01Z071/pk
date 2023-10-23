/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.common;

/**
 *
 * @author andrej
 */
public abstract class Sifrant extends BaseModel {

    private String id = "";
    private String opis = "";

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sifrant() {
    }

    public Sifrant(String id, String opis) {
        this.id = id;
        this.opis = opis;
    }

    @Override
    public String getPrintSimple() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(" - ").append(opis);
        return builder.toString();
    }

    @Override
    public String getPrintInLine() {
        return getPrintSimple();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sifrant other = (Sifrant) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Sifrant{" + "id=" + id + ", opis=" + opis + '}';
    }
    
}
