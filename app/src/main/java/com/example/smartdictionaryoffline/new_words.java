package com.example.smartdictionaryoffline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class new_words extends AppCompatActivity {
    EditText Et1,Et2;
    String word,mean;
    DbHelper dbHelper;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_words);
        Et1=findViewById(R.id.editText2);
        Et2=findViewById(R.id.editText3);
        dbHelper = new DbHelper(this,1,"garbage.db");
        try{
            dbHelper.openDatabase();

        }catch (Exception e){}
        try {
            dbHelper.createDatabase();
        }
        catch (Exception e){}
    }


    public void showMessage(String Title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }


    public void addword(View view) {
        flag=0;
        word=Et1.getText().toString();
        mean=Et2.getText().toString();
        flag=dbHelper.insert_word(word,mean);
        if(flag==0)
            Toast.makeText(this, "Word could not be added!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Word added!", Toast.LENGTH_SHORT).show();
    }

    public void view_all_asc(View view) {
        Cursor res=dbHelper.get_all_data_newword_asc();
        if(res.getCount()==0){
            showMessage("Error","Nothing Fnd!");
            return;
        }
        StringBuffer buffer=new StringBuffer();

        while (res.moveToNext()){
            buffer.append(" Word:- "+res.getString(0)+"\n");
            buffer.append(" Meaning:- \n"+res.getString(1)+"\n\n\n");

        }
        showMessage("New words(Ascending)",buffer.toString());
    }



}
