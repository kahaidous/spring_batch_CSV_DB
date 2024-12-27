package ma.emsi.spring_batch.configs;

import ma.emsi.spring_batch.entities.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer,Customer> {
    @Override
    public Customer process(Customer customer) throws Exception {
        customer.setLastName(customer.getLastName().toUpperCase());
        //
        return customer;
    }
}
