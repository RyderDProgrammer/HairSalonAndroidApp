package com.hairsalon;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CustomerDao
{
    @Insert
    void insertCustomer(Customer customer);

    @Query("SELECT * FROM customers WHERE firstName = :firstName")
    List<Customer> findCustomer(String firstName);

    @Query("DELETE FROM customers WHERE firstName = :firstName")
    void deleteCustomer(String firstName);

    @Query("SELECT * FROM customers")
    LiveData<List<Customer>> getAllCustomers();

    /*
    @Query("SELECT EXISTS(SELECT 1 FROM customers WHERE firstName = :first)")
    String existingCustomer(String first);*/
    /*
    @Query("SELECT * FROM customers WHERE customerId = :id")
    Customer singleCustomer(int id);*/

    @Query("UPDATE customers SET firstName = :first, lastName = :last, phoneNumber = :phone, address = :address WHERE customerId = :id")
    void updateCustomer(String first,String last,String phone,String address,int id);

    /*
    @Query("SELECT customerId FROM customers WHERE firstName = :first AND lastName = :last AND phoneNumber = :phone AND address = :address")
    int getCustomerID(String first,String last,String phone,String address);*/
}
