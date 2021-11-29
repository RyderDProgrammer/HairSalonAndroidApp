package com.hairsalon.ui.main;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hairsalon.Customer;
import com.hairsalon.CustomerRepository;
import java.util.List;
//import androidx.lifecycle.ViewModel;

public class MainViewModel extends AndroidViewModel
{
    private CustomerRepository repository;
    private LiveData<List<Customer>> allCustomers;
    private MutableLiveData<List<Customer>> searchResults;

    public MainViewModel(Application application)
    {
        super(application);
        repository = new CustomerRepository(application);
        allCustomers = repository.getAllCustomers();
        searchResults = repository.getSearchResults();
    }

    MutableLiveData<List<Customer>> getSearchResults()
    {
        return searchResults;
    }

    LiveData<List<Customer>> getAllCustomers()
    {
        return allCustomers;
    }

    public void insertCustomer(Customer customer)
    {
        repository.insertCustomer(customer);
    }
    public void findCustomer(String firstName)
    {
        repository.findCustomer(firstName);
    }
    public void deleteCustomer(String firstName)
    {
        repository.deleteCustomer(firstName);
    }
    public int existingCustomer(String firstName) { return repository.existingCustomer(firstName);}
    public void updateCustomer(String first,String last,String phone,String address) { repository.updateCustomer(first, last, phone, address);}
}