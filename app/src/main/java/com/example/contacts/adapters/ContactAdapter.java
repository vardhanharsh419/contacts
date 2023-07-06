package com.example.contacts.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.contacts.MainActivity;
import com.example.contacts.Message;
import com.example.contacts.R;
import com.example.contacts.frags.ContactsFragment;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> phone = new ArrayList<String>();

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    Context context;
    // data is passed into the constructor
    public ContactAdapter(Context context, ArrayList<String> id, ArrayList<String> name, ArrayList<String> phone) {
        this.mInflater = LayoutInflater.from(context);
        this.name = name;
        this.id = id;
        this.phone = phone;

        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_contacts, parent, false);
        return new ContactAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, final int position) {
        holder.text1.setText(name.get(position));
        holder.text2.setText(phone.get(position));

        String idclicked = id.get(position);
        String nameclicked = name.get(position);
        String phoneclicked = phone.get(position);



        holder.sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, Message.class);
                i.putExtra("id", idclicked);
                i.putExtra("name", nameclicked);
                i.putExtra("phone", phoneclicked);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text1, text2;
        CardView hospitalcard;

        Button sendmsg;

        ViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.name);
            text2 = itemView.findViewById(R.id.phone);
            hospitalcard = itemView.findViewById(R.id.contact_card);
            sendmsg = itemView.findViewById(R.id.sendmsg);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int id) {
        return name.get(id);
    }

    public void setClickListener(ContactsFragment itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
