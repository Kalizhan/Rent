package com.example.rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class CommonActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RentListAdapter rentListAdapter;
    ArrayList<Rent> rentArrayList;
    Button btn1, btn2, btn3;
    DatabaseReference databaseReference;
    ArrayList<String> lastDays = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        recyclerView = findViewById(R.id.recylcer_rent);

        Intent intentName = getIntent();
        String fbName = intentName.getStringExtra("dbName");
        switch (fbName){
            case "Football":
                setTitle("Футбол алаңы");
                break;
            case "ActRoom":
                setTitle("Акт залы");
                break;
            case "SportRoom":
                setTitle("Спортзал");
                break;
            case "Tapchan":
                setTitle("Тапшан");
                break;
        }

        rentArrayList = new ArrayList<Rent>();
        rentListAdapter = new RentListAdapter(rentArrayList);
        recyclerView.setAdapter(rentListAdapter);
        linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        
//
//        databaseReference.child("Rents").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.i("football","databaseReference");
//
//                if (snapshot.exists()){
//                    Toast.makeText(CommonActivity.this, "Data", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(CommonActivity.this, "No", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.i("football","error");
//                Toast.makeText(CommonActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Rents").child(fbName);

        getLastThreeDays();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastDays.size() >= 3){
                    getData(lastDays.get(2));
                }else{
                    Toast.makeText(CommonActivity.this, "Бұл бет бос", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastDays.size() >= 2){
                    getData(lastDays.get(1));
                }else{
                    Toast.makeText(CommonActivity.this, "Бұл бет бос", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastDays.size() >= 1){
                    getData(lastDays.get(0));
                }else{
                    Toast.makeText(CommonActivity.this, "Бұл бет бос", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getLastThreeDays(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lastDays.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    lastDays.add(snapshot1.getKey());
                }
                Collections.reverse(lastDays);
                if (lastDays.size() == 1){
                    btn1.setText("Бос");
                    btn2.setText("Бос");
                    btn3.setText(lastDays.get(0));
                }else if (lastDays.size() == 2){
                    btn1.setText("Бос");
                    btn2.setText(lastDays.get(1));
                    btn3.setText(lastDays.get(0));
                }else if(lastDays.size() >= 3){
                    btn1.setText(lastDays.get(2));
                    btn2.setText(lastDays.get(1));
                    btn3.setText(lastDays.get(0));
                }else if(lastDays.isEmpty()){
                    btn1.setText("Бос");
                    btn2.setText("Бос");
                    btn3.setText("Бос");
                }

                try {
                    getData(lastDays.get(0));
                }catch (Exception e){
                    Toast.makeText(CommonActivity.this, "Бұл бет бос", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void getData(String date){
        databaseReference.child(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rentArrayList.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Rent rent = snap.getValue(Rent.class);
                    rentArrayList.add(rent);
                }
                rentListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}