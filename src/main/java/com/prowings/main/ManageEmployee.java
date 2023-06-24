package com.prowings.main;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.prowings.entity.Employee;

public class ManageEmployee {
	private static SessionFactory factory;

	public static void main(String[] args) {

		factory = new Configuration().configure().buildSessionFactory();

		ManageEmployee ME = new ManageEmployee();

		/* Add few employee records in the database */
		Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
		Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
		Integer empID3 = ME.addEmployee("John", "Paul", 10000);

		/* List down all the employees */
		ME.listEmployees();

		/* Update an employee's record */
		ME.updateEmployee(empID1, 5000);

		/* Delete an employee from the database */
		ME.deleteEmployee(empID2);

		/* List down the new list of employees */
		ME.listEmployees();

		// Close the session factory when done
		factory.close();
	}

	/* Method to CREATE an employee in the database */
	public Integer addEmployee(String fname, String lname, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;

		tx = session.beginTransaction();
		Employee employee = new Employee();
		employee.setFirstName(fname);
		employee.setLastName(lname);
		employee.setSalary(salary);
		employeeID = (Integer) session.save(employee);
		tx.commit();
		session.close();

		return employeeID;
	}

	/* Method to READ all the employees */
	/* Method to READ all the employees */
	public void listEmployees() {
	    Session session = factory.openSession();
	    Transaction tx = null;

	        tx = session.beginTransaction();
	        List<Employee> employees = session.createQuery("FROM Employee", Employee.class).list();
	        for (Employee employee : employees) {
	            System.out.println("First Name: " + employee.getFirstName());
	            System.out.println("Last Name: " + employee.getLastName());
	            System.out.println("Salary: " + employee.getSalary());
	            System.out.println();
	        }
	        tx.commit();
	        session.close();
	}

	/* Method to UPDATE salary for an employee */
	public void updateEmployee(Integer employeeID, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;

		tx = session.beginTransaction();
		Employee employee = session.get(Employee.class, employeeID);
		if (employee != null) {
			employee.setSalary(salary);
			session.update(employee);
		}
		tx.commit();
		session.close();

	}

	/* Method to DELETE an employee from the records */
	public void deleteEmployee(Integer employeeID) {
		Session session = factory.openSession();
		Transaction tx = null;

		tx = session.beginTransaction();
		Employee employee = session.get(Employee.class, employeeID);
		if (employee != null) {
			session.delete(employee);
		}
		tx.commit();
		session.close();
	}
}
