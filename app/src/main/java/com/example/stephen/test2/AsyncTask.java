package com.example.stephen.test2;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stephen on 02/04/2016.
 */
public class AsyncTask extends android.os.AsyncTask {

    String id, Lat, Long;
    public static final String TAG = AsyncTask.class.getSimpleName();
    @Override
    protected Object doInBackground(Object[] params) {
        final Firebase ref = new Firebase("https://test1-polly.firebaseio.com/Skills");
        Query sref = ref.child("" + SearchActivity.Skill);

        sref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {

                    //List<String> bop = (ArrayList<String>) snapshot.getValue();
                    Map<String, Object> bop = (HashMap<String, Object>) snapshot.getValue();

                    //List<Object> values = (List<Object>) bop.values();
                    //System.out.println("Skills id: " + bop.get("id"));
                    id = (String) bop.get("id");
                    System.out.println("get id: " + id);

                    //System.out.println("Skills: " + bop.containsValue(SearchActivity.list));
                    //System.out.println("Skills: " + bop.get("Skills"));
                    Log.d(TAG, "BBABABABABBAABBABBABABAB");
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        final Firebase Iref = new Firebase("https://test1-polly.firebaseio.com/users");
        Query LLref = Iref.child("" + id);

        LLref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {

                    Map<String, Object> hop = (HashMap<String, Object>) snapshot.getValue();
                    Lat = (String) hop.get("lat");
                    Long = (String) hop.get("long");
                    System.out.println("lat:" + Lat + ", long:" + Long);

                    Log.d(TAG, "kebab");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


        return null;
    }

}
