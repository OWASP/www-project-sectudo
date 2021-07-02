package com.example.synradar_sectudo.insecure;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.synradar_sectudo.IndBlogActivity;
import com.example.synradar_sectudo.MainNewActivity;
import com.example.synradar_sectudo.R;
import com.example.synradar_sectudo.helper.Constants;
import com.example.synradar_sectudo.helper.GeneralUtils;
import com.example.synradar_sectudo.learningmodule.CongratsActivity;
import com.example.synradar_sectudo.learningmodule.LearningTabActivity;
import com.example.synradar_sectudo.learningmodule.RetryActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsLogin2Activity extends AppCompatActivity {
    TextView textview18;
    Button login;
    Button menu;
    String topic;
    EditText uname;
    EditText pass;
    TextView topicName;
    TextView task;

    public static final String SHARED_PREFS = "sharedPrefs";
    String tokenstr;


    class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strs) {

            String uname = strs[0];
            String pass = strs[1];
            String responsestr = new String();

            JSONObject reqjson = new JSONObject();
            URL url;
            try {

                reqjson.put("username", uname);
                reqjson.put("password", pass);

                url = new URL(Constants.floginURL); //change url

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                OutputStream out = con.getOutputStream();
                out.write(reqjson.toString().getBytes());

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String tempstr;

                while ((tempstr = in.readLine()) != null) {

                    responsestr = tempstr;
                }


                in.close();


            } catch (Exception e) {

                e.printStackTrace();
            }

            return responsestr;
        }
    }

    //SharedPreferences sharedprefrences;
    @SuppressLint("WrongViewCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_login2);
        topic = getIntent().getStringExtra("topic");


        ImageView tpn;
        tpn = (ImageView) findViewById(R.id.imageViewtp);
        tpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InsLogin2Activity.this, MainNewActivity.class).putExtra("irs","back"));
            }});
        ImageView bck;
        bck = (ImageView) findViewById(R.id.imageViewbk);
        bck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(InsLogin2Activity.this, LearningTabActivity.class).putExtra("topic",topic));
            }
        });
        uname = findViewById(R.id.u14);
        pass = findViewById(R.id.p14);


        login = (Button) findViewById(R.id.button71);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                try {
                    String username = uname.getText().toString();
                    String password = pass.getText().toString();



                    InsLogin2Activity.MyTask task = new InsLogin2Activity.MyTask();
                    String resStr = task.execute(username, password).get();

                    JSONObject resObj = new JSONObject(resStr);

                    String opstr = (String) resObj.get("Status");



                    if (opstr.equals("True")) {
                        startActivity(new Intent(InsLogin2Activity.this, CongratsActivity.class).putExtra("topic", topic));
                        }
                    else {
                        startActivity(new Intent(InsLogin2Activity.this, RetryActivity.class).putExtra("topic", topic));
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }




            }
        });

        ImageView imgbtn;
        imgbtn = (ImageView) findViewById(R.id.imageView4);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webviewIntent = new Intent(InsLogin2Activity.this, IndBlogActivity.class);
                // String test = getIntent().getStringExtra("ch");
                switch (topic){
                    case "ia" :
                    case "csua" :
                        webviewIntent.putExtra("URL", Constants.login_blog);
                        break;
                    case "ism" :
                        webviewIntent.putExtra("URL", Constants.home_blog);
                        break;
                    case "ids" :
                        webviewIntent.putExtra("URL", Constants.kyc_blog);
                        break;
                    case "xss" :
                        webviewIntent.putExtra("URL", Constants.view_blog);
                        break;
                    case "sqli" :
                        webviewIntent.putExtra("URL", Constants.show_blog);
                        break;
                    case "ida" :
                        webviewIntent.putExtra("URL", Constants.login_blog);
                        break;
                    case "dl" :
                        webviewIntent.putExtra("URL", Constants.home_blog);
                        break;
                    case "icp" :
                        webviewIntent.putExtra("URL", Constants.login_blog);
                        break;
                    case "sm" :
                        webviewIntent.putExtra("URL", Constants.home_blog);
                        break;
                }

                startActivity(webviewIntent);
            }
        });

        topicName = (TextView) findViewById(R.id.textView354);
        switch (topic){
            case "ia":
                topicName.setText(("Task for Insecure Authentication - Response Manipulation"));
                break;
            case "ism":
                topicName.setText(("Task for Insecure Session Management"));
                break;
        }

    }
    @Override
    public void onBackPressed() { }

}