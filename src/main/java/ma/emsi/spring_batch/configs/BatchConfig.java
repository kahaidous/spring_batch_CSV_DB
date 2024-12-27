package ma.emsi.spring_batch.configs;


import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.emsi.spring_batch.JobCompletionNotificationListener;
import ma.emsi.spring_batch.entities.Customer;
import ma.emsi.spring_batch.repos.CustomerRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final EntityManagerFactory entityManagerFactory;

    //Reader
    @Bean
    public FlatFileItemReader<Customer> itemReader(){
        FlatFileItemReaderBuilder<Customer> flatFileItemReader
                = new FlatFileItemReaderBuilder<>();
        flatFileItemReader.name("Read-CSV");
        flatFileItemReader.linesToSkip(1);
        flatFileItemReader
                .resource(new ClassPathResource("customers-100.csv"));
        flatFileItemReader
                .delimited().delimiter(";")
                .names("id","customerId","firstName","lastName","company","city","country","phone","email",
                        "subscriptionDate","website");
        flatFileItemReader.targetType(Customer.class);
        return flatFileItemReader.build();
    }
    //Processor
    @Bean
    public ItemProcessor<Customer,Customer> customerProcessor() {
        return new CustomerProcessor();
    }

    //Writer
    @Bean
    public JpaItemWriter<Customer> writer() {
        JpaItemWriter<Customer> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager transactionManager) {
        return new StepBuilder("ETL_Customer_Step", jobRepository)
                .<Customer, Customer>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(customerProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("Customer_Job1", jobRepository)
                .start(step(jobRepository, transactionManager))
                .build();
    }
}
