package ma.emsi.spring_batch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @GetMapping("/runjob")
    public BatchStatus load()
            throws Exception {
        //ClassPathResource imgFile = new ClassPathResource("customers-100.csv");
        //String pathToResource = imgFile.getFile().getAbsolutePath();

        JobParameters params = new JobParametersBuilder()
                //.addString("filePath", pathToResource)
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, params);
        return jobExecution.getStatus();
    }

}
