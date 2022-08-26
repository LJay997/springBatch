package com.jay.springBatch.itemprocess;

import com.jay.springBatch.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 过滤掉id被2整除的数据
 */
@Component
@Qualifier("idFilterProcessor")
public class IdFilterProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) throws Exception {
        if (item.getId() % 2 == 0) {
            return item;
        } else {
            return null;
        }
    }
}
