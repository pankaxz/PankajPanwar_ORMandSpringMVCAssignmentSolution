package crm.gl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import crm.gl.entities.Customer;
import crm.gl.service.ICustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private ICustomerService customerService;
	
	@RequestMapping("/list")
	public String listCustomers(Model model) {
		List<Customer> customers = customerService.findAll();
		model.addAttribute("Customers", customers);
		return "list-customers";
	}
	
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		Customer newCustomer = new Customer();
		// set Customers as a model attribute to pre-populate the form
		model.addAttribute("Customer", newCustomer);
		// send over to form 
		return "form-customers";
	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int customerID, Model model) {
		//Get the customer from the service
		Customer newCustomer = customerService.findById(customerID);
		
		//set Customer as a model attribute to pro-populate the form
		model.addAttribute("Customer", newCustomer);
		
		// send over to the form
		return "form-customers";
	}
	
	@PostMapping("/save")
	public String saveCustomerData(@RequestParam("id") int customerID, 
			@RequestParam("firstname") String firstName, 
			@RequestParam("lastname") String lastName,
			@RequestParam("email") String email) {
		System.out.println(customerID);
		Customer newCustomer;
		if(customerID != 0) {
			newCustomer = customerService.findById(customerID);
			newCustomer.setFirstName(firstName);
			newCustomer.setLastName(lastName);
			newCustomer.setEmail(email);
		}else {
			newCustomer = new Customer(customerID, firstName, lastName, email);
		}
		// save the entry
		customerService.saveCustomer(newCustomer);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/customers/list";
	}
	
	@RequestMapping("/delete")
	public String deleteCustomerData(@RequestParam("customerId") int customerID) {
		customerService.deleteById(customerID);
		return "redirect:/customers/list";
	}
}
