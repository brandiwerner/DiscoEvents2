package com.brandi.discoevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.brandi.discoevents.R.id.EventData_List;

public class TagEventList extends AppCompatActivity {

    globals g = globals.getInstance();

    private static final String TAG = "Main Activity Data";

    // This will hold our collection of com.brandi.discoevents.EventData Objects that will be printed to the screen
    final ArrayList<EventData> events = new ArrayList<EventData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_event_list);

        // Get a reference to our Events
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        // Attach a listener to read the data at our posts reference
        ref.child("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(TagEventList.this, "The onDataChange ", Toast.LENGTH_LONG).show();

                // get all of the children at this level.
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                // Iterate over the collection called children
                // Each time you get to a child put it in the collection variable
                for (DataSnapshot child : children) {
                    // Returned as a java object
                    EventData value = child.getValue(EventData.class);

                    // Checking for the tags and if the tag is true and the value's same tag is true then add it to the events array
                    if((g.isCS() == value.isCsTag())&&(g.isCS()==true)){
                        events.add(value);
                    }else if((g.isCE()==value.isCeTag())&&(g.isCE()==true)){
                        events.add(value);
                    }else if((g.isDEPT()==value.isDeptTag())&&(g.isDEPT()==true)){
                        events.add(value);
                    }else if((g.isEE()==value.isEeTag())&&(g.isEE()==true)){
                        events.add(value);
                    }
                }
                showListNow();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Display a toast Error message
                Log.d(TAG, " Error ");
                Toast.makeText(TagEventList.this, "The read failed: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }

        });

        // Make array adapter to show results
        ListView listview = (ListView) findViewById(EventData_List);
        ListAdapter eventAdapter = new CustomAdapter(this, events);
        listview.setAdapter(eventAdapter);

    }

    private void showListNow() {
        // Make array adapter to show results
        ListView listview = (ListView) findViewById(EventData_List);
        ListAdapter eventAdapter = new CustomAdapter(this, events);
        listview.setAdapter(eventAdapter);
    }

}