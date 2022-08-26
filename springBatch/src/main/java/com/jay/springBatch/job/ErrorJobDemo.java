package com.jay.springBatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;

@Configuration
public class ErrorJobDemo {

    @Resource
    private JobBuilderFactory jobBuilderFactory;

    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job MyErrorJob() {
        return jobBuilderFactory.get("MyErrorJob")
                .start(errorStep()).build();

    }

    @Bean
    public Step errorStep() {
        return stepBuilderFactory.get("errorStep")
                .chunk(10)
                .listener()
                .build();
    }

    private Tasklet errorTasklet() {

        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                Map<String, Object> stepExecutionContext = chunkContext.getStepContext().getStepExecutionContext();
                if (null != stepExecutionContext.get("errorSeason1")) {
                    System.out.println("fish");
                    return RepeatStatus.FINISHED;
                } else {
//                    chunk上下文 获取 Step上下文 获取 执行上下文
                    chunkContext.getStepContext().getStepExecution().getExecutionContext().put("errorSeason1", true);
                    System.out.println("Error");
                    throw new RuntimeException("error");
                }

            }
        };
    }
}
