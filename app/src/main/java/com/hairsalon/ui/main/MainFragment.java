package com.hairsalon.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import com.hairsalon.Customer;
import com.hairsalon.R;

import java.util.List;


public class MainFragment extends Fragment implements CustomerListAdapter.OnNoteListener
{

    private MainViewModel mViewModel;
    private CustomerListAdapter adapter;

    private EditText customerFirstName;
    private EditText customerLastName;
    private EditText customerPhoneNumber;
    private EditText customerAddress;

    private Customer existingCustomer;

    public static MainFragment newInstance()
    {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        customerFirstName = getView().findViewById(R.id.firstNameTextBox);
        customerLastName = getView().findViewById(R.id.lastNameTextBox);
        customerPhoneNumber = getView().findViewById(R.id.phoneNumTextBox);
        customerAddress = getView().findViewById(R.id.addressTextBox);

        listenerSetup();
        observerSetup();
        recyclerSetup();
    }

    private void clearFields()
    {
        customerFirstName.setText("");
        customerLastName.setText("");
        customerPhoneNumber.setText("");
        customerAddress.setText("");
    }

    private void listenerSetup()
    {
        Button addButton = getView().findViewById(R.id.secondAddCustomerButton);
        Button deleteButton = getView().findViewById(R.id.deleteButton);
        Button cancel = getView().findViewById(R.id.cancelButton);
        Button updateButton = getView().findViewById(R.id.updateCustButton);


        addButton.setOnClickListener(view -> {
            String first = customerFirstName.getText().toString();
            String last = customerLastName.getText().toString();
            String phone = customerPhoneNumber.getText().toString();
            String address = customerAddress.getText().toString();
            //Makes sure that the user has no-one selected to add in
            if(first.equals("") && last.equals("") && phone.equals("") && address.equals("")) {
                openSecondScreen();
            }
            //Removed all this code because the addButton doesn't care if a customer is selected it will
            //just open the screen with blank information anyways.
            /*String first = customerFirstName.getText().toString();
            String last = customerLastName.getText().toString();
            String phone = customerPhoneNumber.getText().toString();
            String address = customerAddress.getText().toString();

            int custo = 0;
            custo = mViewModel.existingCustomer(first);
            if(custo == 0 && (!first.equals("") && !last.equals("") && !phone.equals("") && !address.equals("")))
            {
                Customer customer = new Customer(first, last, phone, address);
                //mViewModel.insertCustomer(customer);
                clearFields();
            }*/
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clearFields();
                //mViewModel.findCustomer(customerFirstName.getText().toString());
            }
        });

        deleteButton.setOnClickListener(view -> {
            mViewModel.deleteCustomer(customerFirstName.getText().toString());
            clearFields();
        });

        updateButton.setOnClickListener(view -> {
            String first = customerFirstName.getText().toString();
            String last = customerLastName.getText().toString();
            String phone = customerPhoneNumber.getText().toString();
            String address = customerAddress.getText().toString();
            //Ensures that the person actually clicks on a person to update.
            if(!first.equals("") && !last.equals("") && !phone.equals("") && !address.equals(""))
            {
                Intent intent = new Intent(getContext(), UpdateCustomerScreen.class);
                //These pass values into the update screen.
                intent.putExtra("firstName",first);
                intent.putExtra("lastName",last);
                intent.putExtra("phoneNumber",phone);
                intent.putExtra("customerAddress",address);
                intent.putExtra("customerId",existingCustomer.getCustomerId());
                startActivity(intent);
                //mViewModel.updateCustomer(first,last,phone,address,existingCustomer.getCustomerId());
                //clearFields();
            }
        });
    }

    private void observerSetup()
    {
        mViewModel.getAllCustomers().observe(getViewLifecycleOwner(), new Observer<List<Customer>>()
        {
            @Override
            public void onChanged(@Nullable List<Customer> customers)
            {
                adapter.setCustomerList(customers);
            }
        });

        mViewModel.getSearchResults().observe(getViewLifecycleOwner(), new Observer<List<Customer>>()
        {
            @Override
            public void onChanged(@Nullable final List<Customer> customers)
            {
                if(customers.size() > 0)
                {
                    customerFirstName.setText(customers.get(0).getFirstName());
                    customerLastName.setText(customers.get(0).getLastName());
                    customerPhoneNumber.setText(customers.get(0).getPhoneNumber());
                    customerAddress.setText(customers.get(0).getAddress());
                }
            }
        });
    }

    private void recyclerSetup()
    {
        RecyclerView recyclerView;

        adapter = new CustomerListAdapter(R.layout.customer_list_item,this);
        recyclerView = getView().findViewById(R.id.customer_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(int position)
    {
        existingCustomer = adapter.getCustomer(position);
        customerFirstName.setText(existingCustomer.getFirstName());
        customerLastName.setText(existingCustomer.getLastName());
        customerPhoneNumber.setText(existingCustomer.getPhoneNumber());
        customerAddress.setText(existingCustomer.getAddress());
    }

    public void openSecondScreen()
    {
        Intent intent = new Intent(getContext(), AddCustomerScreen.class);
        startActivity(intent);
    }
}