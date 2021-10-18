package com.example.carddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.Date;
import java.util.ArrayList;

public class ListRequistion extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListView listView;

    private ArrayList<Requistion> arrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_requistion);

        //TODO: подгрузить из БД заявки
        listView = findViewById(R.id.listreq_listview);
        arrList = new ArrayList<>();
        arrList = readDB();
        //Если список пуст
        if (arrList.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Внимание!");
            builder.setMessage("Отсутсвуют заявления в процессе!");
            builder.setPositiveButton("Главное меню", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
        }

        ListAdapter adapter = new ListAdapter(this, arrList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int i = (int) id;
        Intent intent = new Intent(this, Status.class);
        intent.putExtra("keyy", arrList.get(i).getKey());
        intent.putExtra("date_fil", arrList.get(i).getDate_fil().toString());
        intent.putExtra("surname", arrList.get(i).getSurname());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    public ArrayList<Requistion> readDB(){
        ArrayList<Requistion> arrList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query("zayavka", null, null, null, null, null, null );
        if(c.moveToFirst()){
            do{
                Requistion r = new Requistion();
                r.setKey(c.getString(c.getColumnIndex("keyy")));
                r.setDate_fil(Date.valueOf(c.getString(c.getColumnIndex("date_fil"))));
                r.setSurname(c.getString(c.getColumnIndex("surname")));
                arrList.add(r);
            }while(c.moveToNext());
        }
        c.close();

        return  arrList;
    }
}
