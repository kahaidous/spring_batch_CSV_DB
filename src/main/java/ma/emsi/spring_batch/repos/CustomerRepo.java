package ma.emsi.spring_batch.repos;

import ma.emsi.spring_batch.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
