package com.example.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    RecyclerView recyclerView;
//    LinearLayoutManager linearLayoutManager;
//    RentListAdapter rentListAdapter;
//    ArrayList<Rent> rentArrayList;
    FloatingActionButton fab;
    Button btn1, btn2, btn3, btn4;
    Intent intent;
//    DatabaseReference databaseReference;
//    ArrayList<String> lastDays = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        recyclerView = findViewById(R.id.recylcer_rent);
        fab = findViewById(R.id.floatbtn);
        btn1 = findViewById(R.id.football);
        btn2 = findViewById(R.id.act_room);
        btn3 = findViewById(R.id.sportroom);
        btn4 = findViewById(R.id.tapchan);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, CommonActivity.class);
                intent.putExtra("dbName", "Football");
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, CommonActivity.class);
                intent.putExtra("dbName", "ActRoom");
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, CommonActivity.class);
                intent.putExtra("dbName", "SportRoom");
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, CommonActivity.class);
                intent.putExtra("dbName", "Tapchan");
                startActivity(intent);
            }
        });


//        rentArrayList = new ArrayList<Rent>();
//        rentListAdapter = new RentListAdapter(rentArrayList, this);
//        recyclerView.setAdapter(rentListAdapter);
//        linearLayoutManager =new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents");

//        getLastThreeDays();

        fab.setOnClickListener(this);

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (lastDays.size() >= 3){
//                    getData(lastDays.get(2));
//                }else{
//                    Toast.makeText(MainActivity.this, "Бұл бет бос", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (lastDays.size() >= 2){
//                    getData(lastDays.get(1));
//                }else{
//                    Toast.makeText(MainActivity.this, "Бұл бет бос", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (lastDays.size() >= 1){
//                    getData(lastDays.get(0));
//                }else{
//                    Toast.makeText(MainActivity.this, "Бұл бет бос", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

//    private void getLastThreeDays(){
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                lastDays.clear();
//                for (DataSnapshot snapshot1 : snapshot.getChildren()){
//                    lastDays.add(snapshot1.getKey());
//                }
//                Collections.reverse(lastDays);
//                if (lastDays.size() == 1){
//                    btn1.setText("Бос");
//                    btn2.setText("Бос");
//                    btn3.setText(lastDays.get(0));
//                    getData(lastDays.get(0));
//                }else if (lastDays.size() == 2){
//                    btn1.setText("Бос");
//                    btn2.setText(lastDays.get(1));
//                    btn3.setText(lastDays.get(0));
//                    getData(lastDays.get(0));
//                }else if(lastDays.size() >= 3){
//                    btn1.setText(lastDays.get(2));
//                    btn2.setText(lastDays.get(1));
//                    btn3.setText(lastDays.get(0));
//                    getData(lastDays.get(0));
//                }else if(lastDays.isEmpty()){
//                    btn1.setText("Бос");
//                    btn2.setText("Бос");
//                    btn3.setText("Бос");
//                    getData(lastDays.get(0));
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
//    private void getData(String date){
//        databaseReference.child(date).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                rentArrayList.clear();
//                for (DataSnapshot snap : snapshot.getChildren()){
//                    Rent rent = snap.getValue(Rent.class);
//                    rentArrayList.add(rent);
//                }
//                rentListAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, RentPlaceActivity.class);
        startActivity(intent);
    }
}