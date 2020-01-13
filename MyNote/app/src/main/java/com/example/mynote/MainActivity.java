package com.example.mynote;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public Button button;
    ListView listView;
    SearchView mysearchview;
    ArrayList<String> arrayList;
    ArrayAdapter<String> Adopter,adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar calendar= Calendar.getInstance();
        final String currentdate= DateFormat.getDateInstance().format(calendar.getTime());
        button = (Button)findViewById(R.id.btn);
        mysearchview=(SearchView)findViewById(R.id.searchview);
        arrayList = new ArrayList<>();
        Adopter = new ArrayAdapter<String>(MainActivity.this, R.layout.list2,R.id.note, arrayList);
        final ListView listView=(ListView)findViewById(R.id.list);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        final DatabaseReference Ref = database.getReference(id);
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    Note info=item.getValue(Note.class);
                    arrayList.add(info.getTitle().toString() +"\n"+info.getContent().toString()+"\n"+currentdate);
                }
                listView.setAdapter(Adopter);
                        /*mysearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                Adopter.getFilter().filter(s);
                                return false;
                            }
                        });*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this, SideActivity.class);
                startActivity(intent);
            }
        });
        //listView.setOnClickListener(new View.OnClickListener() {
        //   @Override
        // public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SideActivity.class);
        //intent.putExtra("Title1", );
        //intent.putExtra("Content1",arrayList);
        //   startActivity(intent);

        //}
        //});
        //adapter =new ArrayAdapter<>(this,R.layout.list2,arrayList);
        mysearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Adopter.getFilter().filter(s);
                return false;
            }
        });
    }



}


