/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.vsrs.cif.mod.StatusCertifikat;

/**
 *
 * @author uporabnik
 */
    public interface StatusCertifikatRepository extends JpaRepository<StatusCertifikat, Long> {

    StatusCertifikat findBySifra(String sifra);

}
