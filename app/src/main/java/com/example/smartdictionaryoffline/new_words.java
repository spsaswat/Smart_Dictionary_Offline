package com.example.smartdictionaryoffline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class new_words extends AppCompatActivity {
    EditText Et1,Et2,Et5,Et6;
    String word="",mean="",word1="",word2="";
    String word_mean="";
    DbHelper dbHelper;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_words);
        Et1=findViewById(R.id.editText2);
        Et2=findViewById(R.id.editText3);
        Et5=findViewById(R.id.editText5);
        Et6=findViewById(R.id.editText6);
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
        if((word.length()!=0)&&(mean.length()!=0)) {
            flag = dbHelper.insert_word(word, mean);
            if (flag == 0)
                Toast.makeText(this, "Word could not be added!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Word added!", Toast.LENGTH_SHORT).show();
        }
        else
            showMessage("Err!","Empty word not accepted");
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
            buffer.append(" Meaning/Note:- \n"+res.getString(1)+"\n\n\n");

        }
        showMessage("word/note_id arranged in alphabetic order",buffer.toString());
    }


    public void speak_nw(View view) {
        word1=Et5.getText().toString();
        word_mean=dbHelper.GetMean_nw(word1);
        if(word1!=""&&word_mean!=null) {
            Intent intent = new Intent(this, read_text.class);
            intent.putExtra("COMMON", word1 + "\n\n" + word_mean);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "ERR! Word/Note can't be read!", Toast.LENGTH_SHORT).show();
        }
    }


    public void delete_1_nw(View view) {
        word2=Et6.getText().toString();
        int r=dbHelper.delete_nw(word2);
        if(r==1)
            Toast.makeText(this, "Word/Note removed!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Word/Note could not be removed!", Toast.LENGTH_SHORT).show();

    }


}
