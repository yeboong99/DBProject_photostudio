package dbproject.DBproject.Customers.repository;

import dbproject.DBproject.Customers.domain.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomersRepository extends JpaRepository<Customers, Long> {
    List<Customers> findByNameContainingIgnoreCase(String name);
}
