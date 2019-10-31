package com.example.smartdictionaryoffline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emrekose.copyexternaldb.CopyDatabase;

import java.util.ArrayList;

public class DbHelper extends CopyDatabase {

    String Table_Name = "entries";
    String Column_Name = "word";
    Context mcontext;


    public static final String TABLE_NAME_hist = "history";
    public static final String COL_1_hist = "word_h";
    public static final String COL_2_hist = "definition_h";
    public static final String COL_3_hist = "DATE";

    public static final String TABLE_NAME_bm = "bookmarks";
    public static final String COL_1_bm = "word_b";
    public static final String COL_2_bm = "definition_b";
    public static final String COL_3_bm = "DATE";





    public DbHelper(Context context, int version, String databaseName) {
        super(context, version, databaseName);
        mcontext = context;

    }

    public ArrayList<String> GetAllWords(String query){

        ArrayList<String> arrayList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                Table_Name,
                new String[] {Column_Name},
                Column_Name + " LIKE ?",
                new String[] {query + "%"},
                null,null,null);

        int index = cursor.getColumnIndex(Column_Name);

        while(cursor.moveToNext()){
            arrayList.add(cursor.getString(index));

        }

        return arrayList;

    }

    public String GetMean(String word){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String mean = null;

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Table_Name + " where " + Column_Name + "=  '"+word+"'",null);

        while(cursor.moveToNext()){
            mean = cursor.getString(cursor.getColumnIndex("definition"));

        }
        return mean;
    }





    //history functions
    public int insertData_hist(String word,String definition,String date){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_hist,word );
        contentValues.put(COL_2_hist,definition );
        contentValues.put(COL_3_hist,date );
        long result = sqLiteDatabase.insert(TABLE_NAME_hist, null, contentValues);
        if (result == -1)
            return 0;
        else
            return 1;
    }

    public int delete_hist(String word){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        return sqLiteDatabase.delete(TABLE_NAME_hist,"word_h=?",new String[]{word});
    }

    public int delete_all_hist() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME_hist,null,null);
    }

    public Cursor get_all_data_hist_desc(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor res=sqLiteDatabase.query(TABLE_NAME_hist,new String[]{COL_1_hist,COL_2_hist}, null, null, null, null, COL_1_hist+" DESC");
        return res;
    }


    public Cursor get_all_data_hist_asc(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor res=sqLiteDatabase.query(TABLE_NAME_hist,new String[]{COL_1_hist,COL_2_hist}, null, null, null, null, COL_1_hist+" ASC");
        return res;
    }

    public Cursor get_all_data_hist(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor res=sqLiteDatabase.query(TABLE_NAME_hist,new String[]{COL_1_hist,COL_2_hist,COL_3_hist}, null, null, null, null, COL_3_hist);
        return res;
    }






    //bookmarks functions
    public int insertData_bm(String word,String definition,String date){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_bm,word );
        contentValues.put(COL_2_bm,definition );
        contentValues.put(COL_3_bm,date );
        long result = sqLiteDatabase.insert(TABLE_NAME_bm, null, contentValues);
        if (result == -1)
            return 0;
        else
            return 1;
    }

    public int delete_bm(String word){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        return sqLiteDatabase.delete(TABLE_NAME_bm,"word_b=?",new String[]{word});
    }

    public int delete_all_bm() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME_bm,null,null);
    }

    public Cursor get_all_data_bm_desc(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor res=sqLiteDatabase.query(TABLE_NAME_bm,new String[]{COL_1_bm,COL_2_bm}, null, null, null, null, COL_1_bm+" DESC");
        return res;
    }


    public Cursor get_all_data_bm_asc(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor res=sqLiteDatabase.query(TABLE_NAME_bm,new String[]{COL_1_bm,COL_2_bm}, null, null, null, null, COL_1_bm+" ASC");
        return res;
    }

    public Cursor get_all_bm(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor res=sqLiteDatabase.query(TABLE_NAME_bm,new String[]{COL_1_bm,COL_2_bm,COL_3_bm}, null, null, null, null, COL_3_bm);
        return res;
    }




}