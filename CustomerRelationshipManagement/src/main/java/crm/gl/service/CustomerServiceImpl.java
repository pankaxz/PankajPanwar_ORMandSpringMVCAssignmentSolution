package crm.gl.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crm.gl.entities.Customer;

@Service
public class CustomerServiceImpl implements ICustomerService {

	private SessionFactory sessionFactory;
	private Session session;
	
	@Autowired
	public CustomerServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException ex) {
			System.out.println("HIBERNATE EXCEPTION");
			session = sessionFactory.openSession();
		}
	}
	
	@Override
	public List<Customer> findAll() {
		Transaction tx = session.beginTransaction(); //TODO: ADD OPTIMISATION
		List<Customer> customerList = session.createQuery("from Customer").list();
		tx.commit();
		return customerList;
	}

	@Override
	@Transactional
	public Customer findById(int customerId) {
		Transaction tx = session.beginTransaction();
		Customer customer = session.get(Customer.class, customerId);
		tx.commit();
		return customer;
	}

	@Override
	@Transactional
	public void saveCustomer(Customer newCustomer) {
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(newCustomer);
		tx.commit();
	}

	@Override
	@Transactional
	public void deleteById(int customerId) {
		Transaction tx = session.beginTransaction();
		Customer customer = session.get(Customer.class, customerId);
		if(customer != null) {
			session.delete(customer);
		}
		tx.commit();
	}
}
