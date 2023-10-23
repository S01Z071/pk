/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import si.vsrs.cif.mod.JobLog;

/**
 *
 * @author uporabnik
 */
public interface JobLogRepository extends JpaRepository<JobLog, Long> {

    JobLog findById(long id);    
  
}
