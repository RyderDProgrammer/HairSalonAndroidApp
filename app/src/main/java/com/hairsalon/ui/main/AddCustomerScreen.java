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

public class AddCustomerScreen extends AppCompatActivity
{
    private MainViewModel mViewModel;

    private Button returnButton;
    private Button addButton;

    private EditText customerFirstName;
    private EditText customerLastName;
    private EditText customerPhoneNumber;
    private EditText customerAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer_screen);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        customerFirstName = findViewById(R.id.firstNameTextBox);
        customerLastName = findViewById(R.id.lastNameTextBox);
        customerPhoneNumber = findViewById(R.id.phoneNumTextBox);
        customerAddress = findViewById(R.id.addressTextBox);

        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> returnHome());
        addButton = findViewById(R.id.secondAddCustomerButton);
        addButton.setOnClickListener(v -> addCustomer());
    }

    public void returnHome()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addCustomer()
    {
        String first = customerFirstName.getText().toString();
        String last = customerLastName.getText().toString();
        String phone = customerPhoneNumber.getText().toString();
        String address = customerAddress.getText().toString();

        int custo = 0;
        custo = mViewModel.existingCustomer(first);

        if(custo == 0 && (!first.equals("") && !last.equals("") && !phone.equals("") && !address.equals("")))
        {
            Customer customer = new Customer(first, last, phone, address);
            mViewModel.insertCustomer(customer);
            clearFields();
        }
    }

    private void clearFields()
    {
        customerFirstName.setText("");
        customerLastName.setText("");
        customerPhoneNumber.setText("");
        customerAddress.setText("");
    }
}