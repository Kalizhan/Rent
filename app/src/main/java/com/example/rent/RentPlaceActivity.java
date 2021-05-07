package com.example.rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RentPlaceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner1, spinner2;
    EditText editText, date_input, time_input1, time_input2;
    Button button;
    DatabaseReference databaseReference;
    ArrayList<Rent> rentArrayList;
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_place);

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        editText = findViewById(R.id.et_which_group);
        date_input = findViewById(R.id.date_input);
        time_input1 = findViewById(R.id.time_input1);
        time_input2 = findViewById(R.id.time_input2);
        time_input1 = findViewById(R.id.time_input1);
        button = findViewById(R.id.btn);
        rentArrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents");

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.spinner_list1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spinner_list2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        date_input.setInputType(InputType.TYPE_NULL);
        time_input1.setInputType(InputType.TYPE_NULL);

        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(date_input);
            }
        });

        time_input1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(time_input1);
            }
        });

        time_input2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(time_input2);
            }
        });
    }

    private void showTimeDialog(EditText time_in) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                time_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(RentPlaceActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private void showDateDialog(EditText date_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(RentPlaceActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private Boolean validatePlace() {
        String place = spinner1.getSelectedItem().toString();

        if (place.isEmpty()) {
            Toast.makeText(this, "Орынды таңдаңыз!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateDay() {
        String day = date_input.getText().toString();

        if (day.isEmpty()) {
            date_input.setError("Күн бос болмауы керек");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateTimeBefore() {
        String time1 = time_input1.getText().toString();

        if (time1.isEmpty()) {
            time_input1.setError("Басталатын уақытты енгізіңіз!");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateTimeAfter() {
        String time2 = time_input2.getText().toString();

        if (time2.isEmpty()) {
            time_input2.setError("Аяқталатын уақытты енгізіңіз!");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateGroup() {
        String group = editText.getText().toString();

        if (group.isEmpty()) {
            editText.setError("Қай группа енгізді?");
            return false;
        } else {
            return true;
        }
    }


    public void check(View view) {
        if (!validatePlace() | !validateDay() | !validateTimeBefore() | !validateTimeAfter() | !validateGroup()) {
            return;
        } else {

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Енгізілуде...");
            progressDialog.setMessage("Күте тұрыңыз");
            progressDialog.show();

            String userId = databaseReference.push().getKey();

            String place = spinner1.getSelectedItem().toString();
            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
            String day = date_input.getText().toString();
            String timeBefore = time_input1.getText().toString();
            String timeAfter = time_input2.getText().toString();
            String reason = spinner2.getSelectedItem().toString();
            String group = editText.getText().toString();

            Rent rent1 = new Rent(place, currentDateTimeString, day, timeBefore, timeAfter, reason, group, userId);
            Intent intent;
//             && (timeBefore==rent.getTimebefore() || timeAfter==rent.getTimeafter())
            switch (place) {
                case "Футбол алаңы":
                    Query query = databaseReference.child("Football").child(day).orderByChild("timebefore").equalTo(timeBefore);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Toast.makeText(RentPlaceActivity.this, "Бұл уақыт аралығы бос емес", Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                    //for (DataSnapshot snap : snapshot.getChildren()){

                                    //for (int i=1; i<=snap.getChildrenCount(); i++){
//                                        if (timeBefore == rent2.getTimebefore()){
//                                            Toast.makeText(RentPlaceActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }else{
//                                            Toast.makeText(RentPlaceActivity.this, "Go", Toast.LENGTH_SHORT).show();
//                                        }
//                                        else {
//                                            databaseReference.child("Football").child(day).child(userId).setValue(rent1);
//                                            Intent intent = new Intent(RentPlaceActivity.this, CommonActivity.class);
//                                            intent.putExtra("dbName", "Football");
//                                            Toast.makeText(RentPlaceActivity.this, "Енгізілді", Toast.LENGTH_SHORT).show();
//                                            startActivity(intent);
//                                        }
                                    //}
//                                }
                                }
//                                else if (timeAfter >= ) {
//                                    Rent rent2 = snap.getValue(Rent.class);
//                                    if (timeBefore == rent2.getTimebefore()) {
//                                        Toast.makeText(RentPlaceActivity.this, "Бұл уақыт аралығы бос емес", Toast.LENGTH_SHORT).show();
//                                        progressDialog.cancel();
//                                    }
//                                }
                                else {
                                    checkSnapsot(snapshot, timeBefore, timeAfter);
//                                    databaseReference.child("Football").child(day).child(userId).setValue(rent1);
//                                    Intent intent = new Intent(RentPlaceActivity.this, CommonActivity.class);
//                                    intent.putExtra("dbName", "Football");
//                                    Toast.makeText(RentPlaceActivity.this, "Енгізілді", Toast.LENGTH_SHORT).show();
//                                    startActivity(intent);
                                }


//                                Log.i("bb", "b " + snap.getChildrenCount());
//                                for(int i=1; i<=snap.getChildrenCount(); i++){
//                                    if (timeBefore==rent2.getTimebefore() && timeAfter==rent2.getTimeafter()){
//                                        Toast.makeText(RentPlaceActivity.this, "Бұл уақыт бос емес", Toast.LENGTH_SHORT).show();
//                                    }else {
//                                        j += i;
//
//                                    }
//                                }
//                                if (j>1){
//
//                                }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(RentPlaceActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    databaseReference.child("Football").child(day).child(userId).setValue(rent1);
//                    intent = new Intent(RentPlaceActivity.this, CommonActivity.class);
//                    intent.putExtra("dbName", "Football");
//                    startActivity(intent);
                    break;
                case "Спортзал":
                    databaseReference.child("SportRoom").child(day).child(userId).setValue(rent1);
                    intent = new Intent(RentPlaceActivity.this, CommonActivity.class);
                    intent.putExtra("dbName", "SportRoom");
                    startActivity(intent);
                    Toast.makeText(this, "Енгізілді", Toast.LENGTH_SHORT).show();
                    break;
                case "Тапшан(Алдыңғы)":
                    databaseReference.child("Tapchan").child(day).child(userId).setValue(rent1);
                    intent = new Intent(RentPlaceActivity.this, CommonActivity.class);
                    intent.putExtra("dbName", "Tapchan");
                    startActivity(intent);
                    Toast.makeText(this, "Енгізілді", Toast.LENGTH_SHORT).show();
                    break;
                case "Тапшан(Артқы)":
                    databaseReference.child("Tapchan").child(day).child(userId).setValue(rent1);
                    intent = new Intent(RentPlaceActivity.this, CommonActivity.class);
                    intent.putExtra("dbName", "Tapchan");
                    startActivity(intent);
                    Toast.makeText(this, "Енгізілді", Toast.LENGTH_SHORT).show();
                    break;
                case "Тапшан(Мангал)":
                    databaseReference.child("Tapchan").child(day).child(userId).setValue(rent1);
                    intent = new Intent(RentPlaceActivity.this, CommonActivity.class);
                    intent.putExtra("dbName", "Tapchan");
                    startActivity(intent);
                    Toast.makeText(this, "Енгізілді", Toast.LENGTH_SHORT).show();
                    break;
                case "Акт залы":
                    databaseReference.child("ActRoom").child(day).child(userId).setValue(rent1);
                    intent = new Intent(RentPlaceActivity.this, CommonActivity.class);
                    intent.putExtra("dbName", "ActRoom");
                    startActivity(intent);
                    Toast.makeText(this, "Енгізілді", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    private void checkSnapsot(DataSnapshot snapshot, String timeBefore, String timeAfter) {
        for (DataSnapshot snap : snapshot.getChildren()){
            Rent rent2 = snap.getValue(Rent.class);
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                Date date1 = simpleDateFormat.parse(timeBefore);
                Date date2 = simpleDateFormat.parse(timeAfter);
                Date date3 = simpleDateFormat.parse(rent2.getTimebefore());
                Date date4 = simpleDateFormat.parse(rent2.getTimeafter());

                if (date1.getTime() > date3.getTime() && date1.getTime() < date4.getTime()){
                    Toast.makeText(getApplicationContext(), "Data is exists 111", Toast.LENGTH_SHORT).show();
                }else if (date2.getTime() > date3.getTime() && date2.getTime() < date4.getTime()){
                    Toast.makeText(getApplicationContext(), "Data is exists 222", Toast.LENGTH_SHORT).show();
                }else if (date1.getTime() > date3.getTime() && date2.getTime() < date4.getTime()){
                    Toast.makeText(getApplicationContext(), "Data is exists 333", Toast.LENGTH_SHORT).show();
                }

//                TimeUnit time2 = TimeUnit.MINUTES;
//                long minute = time2.convert(diff, TimeUnit.MILLISECONDS);
//                long hour = minute / 60;
//                long minu = minute % 60;
                //Log.i("gogo", "123 " + String.format("%d:%02d", hour, minu));
            } catch (ParseException e) {
                e.printStackTrace();
            }



        }

    }
}