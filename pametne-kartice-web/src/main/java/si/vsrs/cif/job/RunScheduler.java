/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.job;

import java.util.Date;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import si.vsrs.cif.managers.JobLogManager;
import si.vsrs.cif.mod.JobLog;

/**
 *
 * @author uporabnik
 */
@Component
public class RunScheduler {

    // @Autowired
    private JobLauncher jobLauncher;
    //@Autowired
    private Job job;
    @Autowired
    JobLogManager jobLogManager;

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void run() {
        try {    
            JobLog jobLog = new JobLog();
            jobLogManager.addJobLog(jobLog);

            String dateParam = new Date().toString();
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("date", dateParam);
            jobParametersBuilder.addLong("id", jobLog.getId());

            JobParameters param = jobParametersBuilder.toJobParameters();

            JobExecution execution = jobLauncher.run(job, param);

            jobLog.setStartDate(execution.getStartTime());
            jobLog.setEndDate(execution.getEndTime());
            jobLog.setJobId(execution.getJobId());
            jobLog.setStatus(execution.getStatus().toString());
            jobLog.setNumberOfUpdates(Long.parseLong(Integer.toString(execution.getStepExecutions().iterator().next().getWriteCount())));
            jobLogManager.updateJobLog(jobLog);      
            //System.out.println("AFTER");           
            //System.out.println("Exit Status : " + execution.getStatus());
            // System.out.println("INFOOO:"+execution.getStepExecutions().iterator().next().getWriteCount());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
