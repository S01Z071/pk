/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.mod.JobLog;
import si.vsrs.cif.mod.datajpa.repository.JobLogRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
public class JobLogManager {

    @Autowired
    JobLogRepository repository;
    @Autowired
    NastavitveHelper nastavitveHelper;

    public JobLog findById(long id) {
        return repository.findById(id);
    }

    public void addJobLog(JobLog jobLog) {
        repository.save(jobLog);
    }
    
    public void updateJobLog(JobLog jobLog){
        repository.save(jobLog);
    }

    public List<JobLog> findAll(int pageNum) {
        Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran(), Sort.Direction.DESC, "id");
        return repository.findAll(p).getContent();
    }

    public int getCount() {
        return (int) repository.count();
    }
}
