package crm.gl.service;

import java.util.List;

import org.springframework.stereotype.Service;

import crm.gl.entities.Customer;

@Service
public interface ICustomerService {
	
	List<Customer> findAll();
	
	Customer findById(int customerId);
	
	void saveCustomer(Customer newCustomer);
	
	void deleteById(int customerId);
	
}
