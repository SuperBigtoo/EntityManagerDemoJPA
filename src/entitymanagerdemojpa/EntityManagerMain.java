/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitymanagerdemojpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Customer;
import model.Address;

/**
 *
 * @author Jason
 */
public class EntityManagerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Customer> customerList = new ArrayList<>();
        List<Address> addressList = new ArrayList<>();
        //John
        customerList.add(new Customer(1L, "John", "Henry", "jh@mail.com"));
        addressList.add(new Address(1L, "123/4 Viphavadee Rd.", "Bankok", "10900", "TH"));
        //Marry
        customerList.add(new Customer(2L, "Marry", "Jane", "mj@mail.com"));
        addressList.add(new Address(2L, "123/5 Viphavadee Rd.", "Bankok", "10900", "TH"));
        //Peter
        customerList.add(new Customer(3L, "Peter", "Parker", "pp@mail.com"));
        addressList.add(new Address(3L, "543/21 Fake Rd.", "Nonthaburi", "20900", "TH"));
        //Bruce
        customerList.add(new Customer(4L, "Bruce", "Wayn", "bw@mail.com"));
        addressList.add(new Address(4L, "678/90 Unreal Rd.", "Pathumthani", "30500", "TH"));
        
        //createData(customerList, addressList);
        printAllCustomer();
        printCustomerByCity("Bankok");
    }
    
    public static void createData(List<Customer> customerList, List<Address> addressList) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoJPAPU");
        
        for (int i = 0; i < customerList.size(); i++) {
            customerList.get(i).setAddress(addressList.get(i));
            addressList.get(i).setCustomerFk(customerList.get(i));
            
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            try {
                em.persist(customerList.get(i));
                em.persist(addressList.get(i));
                em.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
        }
    }
    
    public static void printAllCustomer() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoJPAPU");
        EntityManager em = emf.createEntityManager();
        List<Customer> customerList = (List<Customer>)em.createNamedQuery("Customer.findAll").getResultList();
        em.close();
        
        for (Customer customer : customerList) {
           System.out.println("-------------------");
           System.out.println("First Name: " + customer.getFirstname());
           System.out.println("Last Name: " + customer.getLastname());
           System.out.println("Email: " + customer.getEmail());
           System.out.println("Street: " + customer.getAddress().getStreet());
           System.out.println("City: " + customer.getAddress().getCity());
           System.out.println("Country: " + customer.getAddress().getCountry());
           System.out.println("Zip Code: " + customer.getAddress().getZipcode());
           System.out.println("-------------------");
        }
    }
    
    public static void printCustomerByCity(String city) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoJPAPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Address.findByCity");
        query.setParameter("city", city);
        List<Address> addressList = (List<Address>) query.getResultList();
        em.close();
        
        for (Address customrtInCity : addressList) {
           System.out.println("-------------------");
           System.out.println("First Name: " + customrtInCity.getCustomerFk().getFirstname());
           System.out.println("Last Name: " + customrtInCity.getCustomerFk().getLastname());
           System.out.println("Email: " + customrtInCity.getCustomerFk().getEmail());
           System.out.println("Street: " + customrtInCity.getCustomerFk().getAddress().getStreet());
           System.out.println("City: " + customrtInCity.getCustomerFk().getAddress().getCity());
           System.out.println("Country: " + customrtInCity.getCustomerFk().getAddress().getCountry());
           System.out.println("Zip Code: " + customrtInCity.getCustomerFk().getAddress().getZipcode());
           System.out.println("-------------------");
        }
    }
}
