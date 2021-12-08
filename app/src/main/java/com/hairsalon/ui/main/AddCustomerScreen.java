package com.hairsalon.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView addCustomerErrorText;

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
        addCustomerErrorText = findViewById(R.id.addErrorTextBox);

        addCustomerErrorText.setText("");
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

        if(first.equals(""))
        {
            addCustomerErrorText.setText("Please enter a first name");
        }
        else if(last.equals(""))
        {
            addCustomerErrorText.setText("Please enter a last name");
        }
        else if(phone.equals(""))
        {
            addCustomerErrorText.setText("Please enter a phone number");
        }
        else if(parseIntOrNull(phone) == null || phone.length() != 10)
        {
            addCustomerErrorText.setText("Please enter a valid phone number thats 10 digits long");
        }
        else if(address.equals(""))
        {
            addCustomerErrorText.setText("Please enter a address");
        }
        else if(custo == 0 && (!first.equals("") && !last.equals("") && !phone.equals("") && !address.equals("")))
        {
            Customer customer = new Customer(first, last, phone, address);
            mViewModel.insertCustomer(customer);
            addCustomerErrorText.setText("Customer added successfully");
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

    public Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}