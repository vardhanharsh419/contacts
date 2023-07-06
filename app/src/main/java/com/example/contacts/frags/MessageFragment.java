package com.example.contacts.frags;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.contacts.R;
import com.example.contacts.adapters.ContactAdapter;
import com.example.contacts.adapters.SMSAdapter;
import com.example.contacts.db.CourseModal;
import com.example.contacts.db.DBHandler;

import java.util.ArrayList;


public class MessageFragment extends Fragment implements SMSAdapter.ItemClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String adminid;

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> phone = new ArrayList<>();
    private ArrayList<CourseModal> courseModalArrayList;

    private SMSAdapter adapter;
    private DBHandler dbHandler;

    public MessageFragment() {

    }

    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        dbHandler = new DBHandler(getContext());

        courseModalArrayList = new ArrayList<>();
        courseModalArrayList = dbHandler.readCourses();


        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SMSAdapter(getContext(), courseModalArrayList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }



    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onResume(){
        super.onResume();


    }
}