package com.example.contacts.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.R;
import com.example.contacts.db.CourseModal;
import com.example.contacts.frags.ContactsFragment;
import com.example.contacts.frags.MessageFragment;

import java.util.ArrayList;

public class SMSAdapter extends RecyclerView.Adapter<SMSAdapter.ViewHolder> {

    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> phone = new ArrayList<String>();

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList<CourseModal> courseModalArrayList;

    Context context;
    // data is passed into the constructor
    public SMSAdapter(Context context, ArrayList<CourseModal> courseModalArrayList) {
        this.mInflater = LayoutInflater.from(context);
        this.courseModalArrayList = courseModalArrayList;

        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_sms, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SMSAdapter.ViewHolder holder, final int position) {
        CourseModal modal = courseModalArrayList.get(position);
        holder.text1.setText(modal.getCourseName());
        holder.text2.setText(modal.getCourseDuration());
        holder.otp.setText(modal.getCourseDescription());
        holder.time.setText(modal.getCourseTracks());

        Log.d("sassas", modal.getCourseTracks().toString());

    }

    @Override
    public int getItemCount() {
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text1, text2, otp, time;
        CardView hospitalcard;

        ViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.name);
            text2 = itemView.findViewById(R.id.phone);
            otp = itemView.findViewById(R.id.otp);
            time = itemView.findViewById(R.id.time);

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

    public void setClickListener(MessageFragment itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
