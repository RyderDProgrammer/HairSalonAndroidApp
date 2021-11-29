package com.hairsalon;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers")
public class Customer
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "customerId")
    private int customerId;

    @ColumnInfo(name = "firstName")
    private String firstName;
    @ColumnInfo(name = "lastName")
    private String lastName;
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;
    @ColumnInfo(name = "Address")
    private String address;

    public Customer(String firstName,String lastName,String phoneNumber,String address)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getCustomerId() { return this.customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) {this.lastName = lastName; }

    public String getPhoneNumber() { return this.phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }
}
