package com.hairsalon;

import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import android.app.Application;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

import javax.xml.transform.Result;

public class CustomerRepository
{
    private final MutableLiveData<List<Customer>> searchResults = new MutableLiveData<>();
    private final LiveData<List<Customer>> allCustomers;
    private final CustomerDao customerDao;

    public CustomerRepository(Application application)
    {
        CustomerRoomDatabase db;
        db = CustomerRoomDatabase.getDatabase(application);
        customerDao = db.customerDao();
        allCustomers = customerDao.getAllCustomers();
    }

    private void asyncFinished(List<Customer> results)
    {
        searchResults.setValue(results);
    }

    private static class QueryAsyncTask extends AsyncTask<String,Void,List<Customer>>
    {
        private final CustomerDao asyncTaskDao;
        private CustomerRepository delegate = null;

        QueryAsyncTask(CustomerDao dao)
        {
            asyncTaskDao = dao;
        }

        //Could create a dummy list and just add whatever customer is found into that list.
        @Override
        protected List<Customer> doInBackground(final String... params)
        {
            return asyncTaskDao.findCustomer(params[0]);
        }

        @Override
        protected void onPostExecute(List<Customer> result)
        {
            delegate.asyncFinished(result);
        }
    }
    public void findCustomer(String name)
    {
        QueryAsyncTask task = new QueryAsyncTask(customerDao);
        task.delegate = this;
        task.execute(name);
    }

    //Created this so if a duplicate customer does get attempted to be added it won't actually query
    //and overwrite the data being entered with the found customer.
    private static class ExistingAsyncTask extends AsyncTask<String,Void,List<Customer>>
    {
        private final CustomerDao existingTaskDao;
        ExistingAsyncTask(CustomerDao dao)
        {
            existingTaskDao = dao;
        }

        @Override
        protected List<Customer> doInBackground(final String... params)
        {
            return  existingTaskDao.findCustomer(params[0]);
        }
    }
    public int existingCustomer(String name)
    {
        int customerCount = 0;
        try
        {
            ExistingAsyncTask task = new ExistingAsyncTask(customerDao);
            task.execute(name);
            customerCount = task.get().size();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return customerCount;
    }

    //Use the customerID instead of all information.
    private static class UpdateAsyncTask extends AsyncTask<Customer,Void,Void>
    {
        private final CustomerDao updateTaskDao;
        UpdateAsyncTask(CustomerDao dao){ updateTaskDao = dao; }
        @Override
        protected Void doInBackground(final Customer... param)
        {
            updateTaskDao.updateCustomer(param[0].getFirstName(),param[0].getLastName(),param[0].getPhoneNumber(),param[0].getAddress(),param[0].getCustomerId());
            return null;
        }
    }
    public void updateCustomer(String first,String last,String phone,String address)
    {
        Customer updatedCustomer = new Customer(first,last,phone,address);
        UpdateAsyncTask task = new UpdateAsyncTask(customerDao);
        task.execute(updatedCustomer);
    }

    private static class InsertAsyncTask extends AsyncTask<Customer, Void, Void>
    {
        private final CustomerDao asyncTaskDao;
        InsertAsyncTask(CustomerDao dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Customer... params)
        {
            asyncTaskDao.insertCustomer(params[0]);
            return null;
        }
    }
    public void insertCustomer(Customer newCustomer)
    {
        InsertAsyncTask task = new InsertAsyncTask(customerDao);
        task.execute(newCustomer);
    }

    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void>
    {
        private final CustomerDao asyncTaskDao;
        DeleteAsyncTask(CustomerDao dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deleteCustomer(params[0]);
            return null;
        }
    }
    public void deleteCustomer(String name)
    {
        DeleteAsyncTask task = new DeleteAsyncTask(customerDao);
        task.execute(name);
    }

    public LiveData<List<Customer>> getAllCustomers()
    {
        return allCustomers;
    }
    public MutableLiveData<List<Customer>> getSearchResults()
    {
        return searchResults;
    }
}
