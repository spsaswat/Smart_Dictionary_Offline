package com.example.smartdictionaryoffline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    int flag;
    String word="",tmp="",meaning="";

    ArrayList<String> arrayList;

    DbHelper dbHelper;


    TextView textView,textView_4;


    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(this,1,"garbage.db");
        try{
            dbHelper.openDatabase();

        }catch (Exception e){}
        try {
            dbHelper.createDatabase();
        }
        catch (Exception e){}


        autoCompleteTextView = findViewById(R.id.srchv);

        arrayList = new ArrayList<>();

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 1){
                    arrayList.addAll(dbHelper.GetAllWords(s.toString()));

                    autoCompleteTextView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_list_item_1,arrayList));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        textView = findViewById(R.id.txtmean);
        textView_4= findViewById(R.id.textView4);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                word = (String) parent.getItemAtPosition(position);


            }
        });


    }

    public void meaning(View view) {
        tmp=word;
        if(word.length()!=0)
        {
            flag=0;
            meaning=dbHelper.GetMean(word);
            textView_4.setText("                 Add to Bookmarks");
            flag=dbHelper.insertData_hist(word,meaning,formattedDate);
        }
        else
            meaning="Kindly Enter Something or Check For Spelling then click again on search";

        textView.setText(meaning);
    }

    public void Bookmarks(View view) {
        flag=0;
        flag=dbHelper.insertData_bm(word,meaning,formattedDate);
        if(flag==0)
            Toast.makeText(MainActivity.this, "Bookmark could not be added!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "Successfully Bookmarked!", Toast.LENGTH_SHORT).show();
    }

    public void bookmark(View view) {
        Intent intent=new Intent(this,Bookmarks.class);
        startActivity(intent);
    }

    public void history(View view) {
        Intent intent=new Intent(this,history.class);
        startActivity(intent);
    }
}
