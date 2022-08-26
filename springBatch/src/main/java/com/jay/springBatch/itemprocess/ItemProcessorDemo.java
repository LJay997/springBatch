package com.jay.springBatch.itemprocess;

import com.jay.springBatch.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ItemProcessorDemo {

    @Resource
    private JobBuilderFactory jobBuilderFactory;

    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Resource
    private JdbcPagingItemReader<Customer> dbJdbcReader;

    @Resource
    private FlatFileItemWriter<Customer> fileItemWriter;

    @Resource
    private FirstNameUpperProcessor firstNameUpperProcessor;

    @Resource
    @Qualifier("idFilterProcessor")
    private ItemProcessor<Customer, Customer> idFilterProcessor;

    @Bean
    public Job itemProcessorDemoJob() {
        return jobBuilderFactory.get("itemProcessorDemoJob")
                .start(itemProcessorDemoStep()).build();
    }

    @Bean
    public Step itemProcessorDemoStep() {
        return stepBuilderFactory.get("itemProcessorDemoStep")
                .<Customer, Customer>chunk(5)
                .reader(dbJdbcReader)
                .processor(compositeItemProcessor())
                .writer(fileItemWriter).build();
    }

    @Bean
    public CompositeItemProcessor<Customer, Customer> compositeItemProcessor() {
        // 委托职责
        List<ItemProcessor<Customer, Customer>> delegates = new ArrayList<>();
        delegates.add(firstNameUpperProcessor);
        delegates.add(idFilterProcessor);

        CompositeItemProcessor<Customer, Customer> compositeItemProcessors = new CompositeItemProcessor<>();
        compositeItemProcessors.setDelegates(delegates);

        return compositeItemProcessors;
    }

}
