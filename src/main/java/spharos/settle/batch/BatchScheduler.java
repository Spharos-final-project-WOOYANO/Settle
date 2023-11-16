package spharos.settle.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private SettleJobConfig settleJob;

  //  @Scheduled(cron = "0 0 2 * * ?")
    @Scheduled(fixedRate = 200000) // 10초마다 실행
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(settleJob.createJob(), jobParameters);

            // Job 실행 결과 출력
            System.out.println("Job Execution Status: " + jobExecution.getStatus());
            System.out.println("Job ID: " + jobExecution.getJobId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


