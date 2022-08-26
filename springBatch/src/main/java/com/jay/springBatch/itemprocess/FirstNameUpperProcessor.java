package com.jay.springBatch.itemprocess;

import com.jay.springBatch.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FirstNameUpperProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) {
        Customer processCustomer = new Customer();
        // Object source, Object target
        BeanUtils.copyProperties(item, processCustomer);
        processCustomer.setFirstName(processCustomer.getFirstName().toUpperCase());
        return processCustomer;
    }
}
