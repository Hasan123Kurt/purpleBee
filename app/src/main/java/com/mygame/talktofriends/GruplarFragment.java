package com.mygame.talktofriends;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class GruplarFragment extends Fragment {
    private View grupCerceve;
    private ListView list_View;
    private ArrayAdapter<String>arrayAdapter;
    private ArrayList<String>grup_listeleri = new ArrayList<>();


    //Firebase
    private DatabaseReference grupyolu;

    public GruplarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        grupCerceve= inflater.inflate(R.layout.fragment_gruplar, container, false);

        //Firabase tanımlama
        grupyolu= FirebaseDatabase.getInstance().getReference().child("Gruplar");

        //Tanımlamalar
        list_View=grupCerceve.findViewById(R.id.list_view);
        arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,grup_listeleri);
        list_View.setAdapter(arrayAdapter);
        //grupları alma kodları

        GruplariAlGoster();

        list_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mevcutGrupadi=adapterView.getItemAtPosition(i).toString();
                Intent grupChatActivity = new Intent(getContext(),GrupChatsActivity.class);
                grupChatActivity.putExtra("grupAdi",mevcutGrupadi);
                startActivity(grupChatActivity);
            }
        });
        return grupCerceve;

    }

    private void GruplariAlGoster() {
        grupyolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String>set=new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext())
                {
                    set.add(((DataSnapshot)iterator.next()).getKey());

                }
                grup_listeleri.clear();
                grup_listeleri.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
