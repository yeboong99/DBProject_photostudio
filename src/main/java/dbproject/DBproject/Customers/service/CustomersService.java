package dbproject.DBproject.Customers.service;

import dbproject.DBproject.Customers.domain.Customers;
import dbproject.DBproject.Customers.repository.CustomersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomersService {

    private final CustomersRepository customersRepository;

    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<Customers> getAllCustomers() {
        return customersRepository.findAll();
    }

    public List<Customers> findByNameContainingIgnoreCase(String name) {
        return customersRepository.findByNameContainingIgnoreCase(name);
    }

    public void saveCustomers(Customers customers) {
        customersRepository.save(customers);
    }

    public Customers findById(Long id) {
        return customersRepository.findById(id).orElse(null);
    }

    public void deleteCustomer(Long id) {
        customersRepository.deleteById(id);
    }
    public void updateCustomer(Long id, Customers updatedCustomer) {
        Customers customer = customersRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setName(updatedCustomer.getName());
            customer.setPhone(updatedCustomer.getPhone());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setCount(updatedCustomer.getCount());
            customer.setLastVisit(updatedCustomer.getLastVisit());
            customer.setNote(updatedCustomer.getNote());
            customersRepository.save(customer);
        }
    }
}
