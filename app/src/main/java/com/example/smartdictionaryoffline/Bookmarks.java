package com.example.smartdictionaryoffline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Bookmarks extends AppCompatActivity {
    DbHelper dbHelper;
    android.widget.EditText EditText;
    String word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        dbHelper = new DbHelper(this,1,"garbage.db");
        try{
            dbHelper.openDatabase();

        }catch (Exception e){}
        try {
            dbHelper.createDatabase();
        }
        catch (Exception e){}


        EditText=findViewById(R.id.editText);

    }



    public void delete_1_bm(View view) {
        word=EditText.getText().toString();
        int r=dbHelper.delete_bm(word);
        if(r==1)
            Toast.makeText(this, "Word removed!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Word could not be removed!", Toast.LENGTH_SHORT).show();

    }

    public void delete_all_bm(View view) {
        int r=dbHelper.delete_all_bm();
        if(r==0)
            Toast.makeText(this, "Bookmarks could not be cleared", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Bookmarks Cleared!", Toast.LENGTH_SHORT).show();

    }

    public void showMessage(String Title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }

    public void view_all_desc_bm(View view){
        Cursor res=dbHelper.get_all_data_bm_desc();
        if(res.getCount()==0){
            showMessage("Error","Nothing Fnd!");
            return;
        }
        StringBuffer buffer=new StringBuffer();

        while (res.moveToNext()){
            buffer.append(" Word:- "+res.getString(0)+"\n");
            buffer.append(" Meaning:- \n"+res.getString(1)+"\n\n\n");

        }

        showMessage("Bookmarks(Descending)",buffer.toString());
    }

    public void view_all_asc_bm(View view) {
        Cursor res=dbHelper.get_all_data_bm_asc();
        if(res.getCount()==0){
            showMessage("Error","Nothing Fnd!");
            return;
        }
        StringBuffer buffer=new StringBuffer();

        while (res.moveToNext()){
            buffer.append(" Word:- "+res.getString(0)+"\n");
            buffer.append(" Meaning:- \n"+res.getString(1)+"\n\n\n");

        }

        showMessage("Bookmarks(Ascending)",buffer.toString());
    }

    public void view_all_bm(View view) {
        Cursor res=dbHelper.get_all_bm();
        if(res.getCount()==0){
            showMessage("Error","Nothing Fnd!");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        for (res.moveToLast(); !res.isBeforeFirst(); res.moveToPrevious()) {
            buffer.append(" DATE:- "+res.getString(2)+"\n");
            buffer.append(" Word:- "+res.getString(0)+"\n");
            buffer.append(" Meaning:- \n"+res.getString(1)+"\n\n\n");
        }

        showMessage("Bookmarks",buffer.toString());
    }


}
