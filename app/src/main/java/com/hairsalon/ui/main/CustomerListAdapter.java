package com.hairsalon.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hairsalon.R;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import com.hairsalon.Customer;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder>
{
    private final int customerItemLayout;
    private List<Customer> customerList;
    private OnNoteListener mOnNoteListener;

    public CustomerListAdapter(int layoutId, OnNoteListener onNoteListener)
    {
        customerItemLayout = layoutId;
        this.mOnNoteListener = onNoteListener;
    }

    public void setCustomerList(List<Customer> customers)
    {
        customerList = customers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return customerList == null ? 0 : customerList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(customerItemLayout,parent,false);
        return new ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition)
    {
        TextView item = holder.item;
        String itemText = customerList.get(listPosition).getFirstName() + " " + customerList.get(listPosition).getLastName();
        item.setText(itemText);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView item;
        OnNoteListener onNoteListener;
        ViewHolder(View itemView, OnNoteListener onNoteListener)
        {
            super(itemView);
            item = itemView.findViewById(R.id.customer_row);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener
    {
        void onNoteClick(int position);
    }

    public Customer getCustomer(int customerId)
    {
        return customerList.get(customerId);
    }
}

