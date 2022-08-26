package com.jay.springBatch.itemwriter;

import com.jay.springBatch.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class ItemWriterDbDemo {
    @Resource
    private JobBuilderFactory jobBuilderFactory;

    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Resource
    private FlatFileItemReader<Customer> flatFileReader;

    @Resource
    private JdbcBatchItemWriter<Customer> itemWriterDb;

    @Bean
    public Job itemWriterDbDemoJob1() {
        return jobBuilderFactory.get("itemWriterDbDemoJob1")
                .start(itemWriterDbDemoStep1()).build();
    }

    @Bean
    public Step itemWriterDbDemoStep1() {
        return stepBuilderFactory.get("itemWriterDbDemoStep1")
                .<Customer, Customer>chunk(10)
                .reader(flatFileReader)
                .writer(itemWriterDb).build();
    }
}
