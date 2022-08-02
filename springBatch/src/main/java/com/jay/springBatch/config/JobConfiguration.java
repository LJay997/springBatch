package com.jay.springBatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableBatchProcessing
public class JobConfiguration {

    // 自动设置JobRepository的JobBuilder的便利工厂
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    // 创建step对象的工厂
    // StepBuilder的便利工厂，它自动设置JobRepository和PlatformTransactionManager
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    @Bean
//    public Job helloWordJob() {
//        return jobBuilderFactory.get("111").start(step1()).build();
//    }

    @Bean
    public Step step1() {

        return stepBuilderFactory.get("strp1").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Hello word");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

}
