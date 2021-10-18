package com.example.carddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;

public class Requistion extends AppCompatActivity implements View.OnClickListener, ValueEventListener {
    private String paySys;
    private String valuta;
    private String surname;
    private String name;
    private String secondName;
    private String engNameSur;
    private String birth;
    private String email;
    private String phone;
    private String document;
    private String nationality;
    private long numDoc;
    private String issuance;
    private Date date_fil;
    private String key;

    private static final String PAYSYS = "paysys";
    private static final String VALUTA = "valuta";
    private static final String SURNAME = "surname";
    private static final String NAME = "name";
    private static final String SECOND_NAME = "second_name";
    private static final String ENG_NAME_SUR = "eng_name_sur";
    private static final String BIRTH = "birth";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String DOCUMENT = "document";
    private static final String NATIONALITY = "nationality";
    private static final String DOC_NUM = "doc_num";
    private static final String ISSUANCE = "issuance";
    private static final String DATE_FILL = "date_fil";
    private static final String KEYY = "keyy";

    private static final String CMD = "cmd";
    SQLiteDatabase db;
    DBHelper dbHelper;

    private Spinner paySys_spinner;
    private Spinner valuta_spinner;
    private EditText surname_edit;
    private EditText name_edit;
    private EditText secondName_edit;
    private EditText engNameSur_edit;
    private EditText birth_edit;
    private EditText email_edit;
    private EditText phone_edit;
    private Spinner document_spinner;
    private EditText nationality_edit;
    private EditText numDoc_edit;
    private EditText issuance_edit;
    private Button sendButton;

    public String getPaySys() {
        return paySys;
    }
    public void setPaySys(String paySys) {
        this.paySys = paySys;
    }
    public String getValuta() {
        return valuta;
    }
    public void setValuta(String valuta) {
        this.valuta = valuta;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getEngNameSur() {
        return engNameSur;
    }
    public void setEngNameSur(String engNameSur) {
        this.engNameSur = engNameSur;
    }
    public String getBirth() {
        return birth;
    }
    public void setBirth(String birth) {
        this.birth = birth;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public long getNumDoc() {
        return numDoc;
    }
    public void setNumDoc(long numDoc) {
        this.numDoc = numDoc;
    }
    public String getIssuance() {
        return issuance;
    }
    public void setIssuance(String issuance) {
        this.issuance = issuance;
    }
    public Date getDate_fil() {
        return date_fil;
    }
    public void setDate_fil(Date date_fil) {
        this.date_fil = date_fil;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requistion);

        dbHelper = new DBHelper(this);

        paySys_spinner = findViewById(R.id.req_spinner_paySys);
        ArrayAdapter<?> adapterPaySys =
                ArrayAdapter.createFromResource(this, R.array.paySys,
                        android.R.layout.simple_spinner_item);
        adapterPaySys.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paySys_spinner.setAdapter(adapterPaySys);

        valuta_spinner = findViewById(R.id.req_spinner_valuta);
        ArrayAdapter<?> adapterValuta =
                ArrayAdapter.createFromResource(this, R.array.valuta,
                        android.R.layout.simple_spinner_item);
        adapterValuta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        valuta_spinner.setAdapter(adapterValuta);

        surname_edit = findViewById(R.id.req_edit_surname);
        name_edit = findViewById(R.id.req_edit_name);
        secondName_edit = findViewById(R.id.req_edit_secName);
        engNameSur_edit = findViewById(R.id.req_edit_engNameSur);
        birth_edit = findViewById(R.id.req_edit_birth);
        email_edit = findViewById(R.id.req_edit_email);
        phone_edit = findViewById(R.id.req_edit_phone);

        document_spinner = findViewById(R.id.req_spinner_document);
        ArrayAdapter<?> adapterDocument =
                ArrayAdapter.createFromResource(this, R.array.document,
                        android.R.layout.simple_spinner_item);
        adapterDocument.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        document_spinner.setAdapter(adapterDocument);

        nationality_edit = findViewById(R.id.req_edit_nationality);
        numDoc_edit = findViewById(R.id.req_edit_NumDoc);
        issuance_edit = findViewById(R.id.req_edit_issuance);

        sendButton = findViewById(R.id.req_button_send);
        sendButton.setOnClickListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref_work = database.getReference(CMD);
        ref_work.addValueEventListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.req_button_send:
                /*
                - Проверить обязательные поля на заполненность
                - Записать все в БД
                - отправить на сервер
                - выйти в главное меню
                 */
                if(!surname_edit.getText().toString().equals("")
                && !name_edit.getText().toString().equals("")
                && !engNameSur_edit.getText().toString().equals("")
                && !birth_edit.getText().toString().equals("")
                && !email_edit.getText().toString().equals("")
                && !phone_edit.getText().toString().equals("")
                && !nationality_edit.getText().toString().equals("")
                && !numDoc_edit.getText().toString().equals("")
                && !issuance_edit.getText().toString().equals("")){

                    writeDB(); //записываем в локальную БД
                    toServer();

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intentMain = new Intent(this, MainActivity.class);
                    startActivity(intentMain);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Есть незаполенные поля!",Toast.LENGTH_SHORT);
                    toast.show();
                }



        }
    }
    public void writeDB(){

        paySys = paySys_spinner.getSelectedItem().toString();
        valuta = valuta_spinner.getSelectedItem().toString();
        surname = surname_edit.getText().toString();
        name = name_edit.getText().toString();
        if (!secondName_edit.getText().toString().equals(""))
            secondName = secondName_edit.getText().toString();
        else
            secondName = "-";
        engNameSur = engNameSur_edit.getText().toString();
        birth = birth_edit.getText().toString(); //!!!
        email = email_edit.getText().toString();
        phone = phone_edit.getText().toString();
        document = document_spinner.getSelectedItem().toString();
        nationality = nationality_edit.getText().toString();
        numDoc = Long.parseLong(numDoc_edit.getText().toString());
        issuance = issuance_edit.getText().toString(); //!!!

        date_fil = new Date(System.currentTimeMillis());
        key = generateKey();

        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEYY, key);
        cv.put(PAYSYS, paySys);
        cv.put(VALUTA, valuta);
        cv.put(SURNAME, surname);
        cv.put(NAME, name);
        cv.put(SECOND_NAME, secondName);
        cv.put(ENG_NAME_SUR, engNameSur);
        cv.put(BIRTH, birth);
        cv.put(EMAIL, email);
        cv.put(PHONE, phone);
        cv.put(DOCUMENT, document);
        cv.put(NATIONALITY, nationality);
        cv.put(DOC_NUM, numDoc);
        cv.put(ISSUANCE, issuance);
        cv.put(DATE_FILL, date_fil.toString());

        db.insert("zayavka",null, cv);

    }
    public void toServer(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference(KEYY);
        myRef.setValue(key);
        myRef = database.getReference(PAYSYS);
        myRef.setValue(paySys);
        myRef = database.getReference(VALUTA);
        myRef.setValue(valuta);
        myRef = database.getReference(SURNAME);
        myRef.setValue(surname);
        myRef = database.getReference(NAME);
        myRef.setValue(name);
        myRef = database.getReference(SECOND_NAME);
        myRef.setValue(secondName);
        myRef = database.getReference(ENG_NAME_SUR);
        myRef.setValue(engNameSur);
        myRef = database.getReference(BIRTH);
        myRef.setValue(birth);
        myRef = database.getReference(EMAIL);
        myRef.setValue(email);
        myRef = database.getReference(PHONE);
        myRef.setValue(phone);
        myRef = database.getReference(DOCUMENT);
        myRef.setValue(document);
        myRef = database.getReference(NATIONALITY);
        myRef.setValue(nationality);
        myRef = database.getReference(DOC_NUM);
        myRef.setValue(numDoc);
        myRef = database.getReference(ISSUANCE);
        myRef.setValue(issuance);
        myRef = database.getReference(DATE_FILL);
        myRef.setValue(date_fil.toString());

        DatabaseReference  myRef_req = database.getReference(CMD);
        myRef_req.setValue("throw_req"); // команда для отправки
        //myRef.push().setValue("Hello, Thdfgdere!");

    }
    public String generateKey(){
        String key = surname+date_fil+Math.random();
        return key;
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Object object;
        if (dataSnapshot.getRef().getKey().equals(CMD)){
            if (dataSnapshot.getValue().equals("got")){
                Toast toast = Toast.makeText(getApplicationContext(),"Заявка доставлена",Toast.LENGTH_SHORT);
                toast.show();

                //зачистим ввод
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference  myRef_req = database.getReference(CMD);
                myRef_req.setValue("");
            }
            //зачистим ввод
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
