package com.example.contacts;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contacts.db.DBHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class Message extends AppCompatActivity {

    private EditText edit_message;
    public static final String ACCOUNT_SID = "ACe6f785fa16bd34f4c637fe258b88c863";
    public static final String AUTH_TOKEN = "1cfccda46078316973c9c03c687ca442";
    TextView name, phone;
    private DBHandler dbHandler;
    private String db_id, db_name, db_phone, db_date, db_otp, db_time;

    private Button button_chat_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        db_id = getIntent().getStringExtra("id");

        name = findViewById(R.id.name);
        db_name = getIntent().getStringExtra("name");
        name.setText(getIntent().getStringExtra("name"));

        phone = findViewById(R.id.phone);
        db_phone = getIntent().getStringExtra("phone");
        phone.setText(getIntent().getStringExtra("phone"));

        edit_message = findViewById(R.id.edit_message);
        db_otp = getRandomNumberString();
        edit_message.setText("Hi... Your OTP is "+ db_otp);

        button_chat_send = findViewById(R.id.button_chat_send);

        dbHandler = new DBHandler(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.setThreadPolicy( new StrictMode.ThreadPolicy.Builder().permitAll().build());
        }

        button_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    private void sendSms(){
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.twilio.com/2010-04-01/Accounts/"+ACCOUNT_SID+"/Messages.json";
        String base64EncodedCredentials = "Basic " + Base64.encodeToString((ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP);

        RequestBody body = new FormBody.Builder()
                .add("From", "+14847423795")
                .add("To", "+917415510654")
                .add("Body", "Hi... Your OTP is "+ db_otp)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", base64EncodedCredentials)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject tbldata = new JSONObject(String.valueOf(response.body().string()));
            Log.d("Sfef", String.valueOf(tbldata.getString("date_created")));
            db_date = removeChars(tbldata.getString("date_created"));
            setdataintodb();

//            Log.d(TAG, "sendSms: "+ response.body().string());

        } catch (IOException e) { e.printStackTrace(); }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


    private void setdataintodb() {
        dbHandler.addNewCourse(db_name, db_date, db_otp, db_phone);

        Toast.makeText(this, "Message Sent Successfully! Switching to Main...", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Message.this, MainActivity.class);
                startActivity(i);
            }
        }, 2000);
    }

    public static String removeChars(String str) {
        if(str != null && !str.trim().isEmpty()) {
            return str.substring(0, str.length() - 6);
        }
        return "";
    }
}