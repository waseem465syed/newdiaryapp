package com.td.diaryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.td.diaryapp.IndividualClasses.Contact;
import com.td.diaryapp.R;

import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter extends ArrayAdapter<Contact> {

    public PhoneAdapter(@NonNull Context context) {
        super(context, 0, new ArrayList<Contact>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        ViewHolder holder;

        if (convertView == null)
        {
            //attach row_data
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView  = li.inflate(R.layout.row_data, null);

            //create a new holder
            holder = new ViewHolder();

            //set the fields
            holder.txtBig = convertView.findViewById(R.id.txtBig);
            holder.txtSmall = convertView.findViewById(R.id.txtSmall);

            //store]
            convertView.setTag(holder);
        }
        else {

            //use existing holder
            holder = (ViewHolder) convertView.getTag();
        }

        Contact c = getItem(position);

        holder.txtBig.setText(c.getName());

        String phones = "";

        if (c.getPhoneNumbers() != null && !c.getPhoneNumbers().isEmpty())
        {

            for (int i = 0; i < c.getPhoneNumbers().size(); i++)
            {
                phones += c.getPhoneNumbers().get(i);

                if ((i + 1) < c.getPhoneNumbers().size()){
                    phones += ", ";
                }
            }
        }

        holder.txtSmall.setText(phones);

        return  convertView;
    }

    private class ViewHolder {
        public TextView txtBig;
        public TextView txtSmall;
    }
}
