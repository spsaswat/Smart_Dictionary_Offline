package com.example.smartdictionaryoffline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class history extends AppCompatActivity {
    DbHelper dbHelper;
    EditText EditText;
    String word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


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


    public void delete_1(View view) {
        word=EditText.getText().toString();
        int r=dbHelper.delete_hist(word);
        if(r==1)
            Toast.makeText(this, "Word removed!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Word could not be removed!", Toast.LENGTH_SHORT).show();

    }

    public void delete_all(View view) {
        int r=dbHelper.delete_all_hist();
        if(r==0)
            Toast.makeText(this, "History could not be cleared", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "History Cleared!", Toast.LENGTH_SHORT).show();

    }

    public void showMessage(String Title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }

    public void view_all_desc(View view){
        Cursor res=dbHelper.get_all_data_hist_desc();
        if(res.getCount()==0){
            showMessage("Error","Nothing Fnd!");
            return;
        }
        StringBuffer buffer=new StringBuffer();

        while (res.moveToNext()){
            buffer.append(" Word:- "+res.getString(0)+"\n");
            buffer.append(" Meaning:- "+res.getString(1)+"\n\n\n");

        }

        showMessage("History(Descending)",buffer.toString());
    }

    public void view_all_asc(View view) {
        Cursor res=dbHelper.get_all_data_hist_asc();
        if(res.getCount()==0){
            showMessage("Error","Nothing Fnd!");
            return;
        }
        StringBuffer buffer=new StringBuffer();

        while (res.moveToNext()){
            buffer.append(" Word:- "+res.getString(0)+"\n");
            buffer.append(" Meaning:- \n"+res.getString(1)+"\n\n\n");

        }
        showMessage("History(Ascending)",buffer.toString());
    }

    public void view_all(View view) {
        Cursor res=dbHelper.get_all_data_hist();
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

        showMessage("History",buffer.toString());
    }
}
