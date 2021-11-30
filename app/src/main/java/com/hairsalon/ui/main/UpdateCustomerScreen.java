package com.hairsalon.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.hairsalon.Customer;
import com.hairsalon.MainActivity;
import com.hairsalon.R;

public class UpdateCustomerScreen extends AppCompatActivity
{
    private MainViewModel mViewModel;

    private Button returnButton;
    private Button updateButton;

    private EditText customerFirstName;
    private EditText customerLastName;
    private EditText customerPhoneNumber;
    private EditText customerAddress;
    private Customer updatedCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_customer_screen);


        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        customerFirstName = findViewById(R.id.firstNameTextBox);
        customerLastName = findViewById(R.id.lastNameTextBox);
        customerPhoneNumber = findViewById(R.id.phoneNumTextBox);
        customerAddress = findViewById(R.id.addressTextBox);
        newCustomerInfo();

        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> returnHome());
        updateButton = findViewById(R.id.secondUpdateCustomerButton);
        updateButton.setOnClickListener(v -> updateCustomer());
    }

    public void newCustomerInfo()
    {
        Bundle customerInfo = getIntent().getExtras();
        //Had a null check originally but none of this info will ever be null.
        String first = customerInfo.getString("firstName");
        String last = customerInfo.getString("lastName");
        String phone = customerInfo.getString("phoneNumber");
        String address = customerInfo.getString("customerAddress");
        int id = customerInfo.getInt("customerId");
        //Just made a real quick blank customer so that it can have its values overridden.
        updatedCustomer = new Customer(first,last,phone,address);
        updatedCustomer.setCustomerId(id);

        customerFirstName.setText(updatedCustomer.getFirstName());
        customerLastName.setText(updatedCustomer.getLastName());
        customerPhoneNumber.setText(updatedCustomer.getPhoneNumber());
        customerAddress.setText(updatedCustomer.getAddress());
    }

    public void updateCustomer()
    {
        String first = customerFirstName.getText().toString();
        String last = customerLastName.getText().toString();
        String phone = customerPhoneNumber.getText().toString();
        String address = customerAddress.getText().toString();
        if(!first.equals("") && !last.equals("") && !phone.equals("") && !address.equals(""))
        {
            mViewModel.updateCustomer(first,last,phone,address,updatedCustomer.getCustomerId());
            clearFields();
        }
    }
    public void returnHome()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void clearFields()
    {
        customerFirstName.setText("");
        customerLastName.setText("");
        customerPhoneNumber.setText("");
        customerAddress.setText("");
    }
}