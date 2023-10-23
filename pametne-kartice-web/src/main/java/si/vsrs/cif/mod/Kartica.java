/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author uporabnik
 */
@Entity
@Table(name = "kartica")
public class Kartica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_KARTICA")
    @SequenceGenerator(name = "SEQ_KARTICA", sequenceName = "SEQ_KARTICA")
    private Long id;
    @Size(min = 0, max = 255)
    @Column(name = "SERIJSKA_ST_KARTICE")
    private String serijskaStevilkaKartice;
    @DateTimeFormat(pattern = "DD.MM.YYYY hh:mm:ss")
    @Column(name = "DATUM_INIT")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datumInit;
    @Size(min = 0, max = 255)
    @Column(name = "PKCS11")
    private String pkcs11;
    @Size(min = 0, max = 255)
    @Column(name = "ADMIN_PASS")
    private String adminPass;
    @Size(min = 0, max = 255)
    @Column(name = "USER_PASS")
    private String userPass;
    @Size(min = 0, max = 255)
    @Column(name = "PIN3")
    private String pin3;
    @Size(min = 0, max = 255)
    @Column(name = "RFID1")
    private String rfid1;
    @Size(min = 0, max = 255)
    @Column(name = "RFID2")
    private String rfid2;
    @Size(min = 0, max = 255)
    @Column(name = "BARCODE")
    private String barcode;
    @DateTimeFormat(pattern = "DD.MM.YYYY hh:mm:ss")
    @Column(name = "DATUM_VRNITVE")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datumVrnitve;
    
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "IMETNIK_ID")
    private Imetnik imetnik;
    
    @OneToMany(mappedBy = "kartica", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Certifikat> certifikat;

   
    
    public List<Certifikat> getCertifikat() {
        return certifikat;
    }

    public void setCertifikat(List<Certifikat> certifikat) {
        this.certifikat = certifikat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerijskaStevilkaKartice() {
        return serijskaStevilkaKartice;
    }

    public void setSerijskaStevilkaKartice(String serijskaStevilkaKartice) {
        this.serijskaStevilkaKartice = serijskaStevilkaKartice;
    }

    public Date getDatumInit() {
        return datumInit;
    }

    public void setDatumInit(Date datumInit) {
        this.datumInit = datumInit;
    }

    public String getPkcs11() {
        return pkcs11;
    }

    public void setPkcs11(String pkcs11) {
        this.pkcs11 = pkcs11;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getPin3() {
        return pin3;
    }

    public void setPin3(String pin3) {
        this.pin3 = pin3;
    }

    public String getRfid1() {
        return rfid1;
    }

    public void setRfid1(String rfid1) {
        this.rfid1 = rfid1;
    }

    public String getRfid2() {
        return rfid2;
    }

    public void setRfid2(String rfid2) {
        this.rfid2 = rfid2;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Imetnik getImetnik() {
        return imetnik;
    }

    public void setImetnik(Imetnik imetnik) {
        this.imetnik = imetnik;
    }

    public Date getDatumVrnitve() {
        return datumVrnitve;
    }

    public void setDatumVrnitve(Date datumVrnitve) {
        this.datumVrnitve = datumVrnitve;
    }
    
    
    
    
}
