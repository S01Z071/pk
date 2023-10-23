/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.mod.SifrantSodiscUvoz;
import si.vsrs.cif.mod.datajpa.repository.SifrantSodiscUvozRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
public class SifrantSodiscUvozRepoManager {

    @Autowired
    SifrantSodiscUvozRepository repository;

    public List<SifrantSodiscUvoz> getSifrantSodisc() {
        return repository.findAll();
    }
}
