package study.vv.myapplication;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {
    private EditText word;
    private TextView text;
    String address="https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180416T153327Z.fda17494174b11d8.26a0d159a35c9c36ead0bbe856016c63b0e2b86c&%20&lang=en-ru&text=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        word=(EditText)findViewById(R.id.editText);
        text=(TextView)findViewById(R.id.textview);
    }

    public void get(View view) {
        text.setText("");
        String text=word.getText().toString();
        new MyAsyncTask().execute(text);
    }

    private class MyAsyncTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject json=new JSONObject(s);
                JSONArray array=json.getJSONArray("text");
                text.setText((String)array.get(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String res="";
            try {
                URL url=new URL(address+strings[0]);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                Scanner sc=new Scanner(connection.getInputStream());
                while(sc.hasNextLine()){
                    res=res+sc.nextLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return res;
        }


    }
}


