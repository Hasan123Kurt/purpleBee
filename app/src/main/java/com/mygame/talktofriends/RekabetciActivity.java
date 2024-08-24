package com.mygame.talktofriends;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mygame.talktofriends.Model.Kisiler2;

public class RekabetciActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button teste_basla, increase;
    TextView hak_t;
    private DatabaseReference kullaniciyolu;
    int hak_update;
    Handler handler;
    Runnable runnable;
    int sayac=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekabetci);
        recyclerView = findViewById(R.id.skor_tablosu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hak_t = findViewById(R.id.hak);
        teste_basla = findViewById(R.id.teste_basla);
        increase = findViewById(R.id.increase);

        kullaniciyolu = FirebaseDatabase.getInstance().getReference().child("Kullanicilar");

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SQLiteDatabase sqLiteDatabase = RekabetciActivity.this.openOrCreateDatabase("Haklar",MODE_PRIVATE,null);
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Haklar",null);
                    int hakIx = cursor.getColumnIndex("hak");
                    while (cursor.moveToNext())
                    {
                        hak_update=cursor.getInt(hakIx);
                    }
                    cursor.close();
                    hak_update=hak_update+1;
                    hak_t.setText("Your pens "+hak_update);
                    String sql ="UPDATE Haklar SET Hak= ? ";
                    SQLiteStatement sqLiteStatement =sqLiteDatabase.compileStatement(sql);
                    sqLiteStatement.bindDouble(1,hak_update);
                    sqLiteStatement.execute();
                }catch (Exception e){
                    e.printStackTrace();
                }

                MobileAds.initialize(RekabetciActivity.this,"ca-app-pub-2724540026642482~8500905698");
                final InterstitialAd interstitialAd = new InterstitialAd(RekabetciActivity.this);
                interstitialAd.setAdUnitId("ca-app-pub-2724540026642482/5661073643");
                interstitialAd.loadAd(new AdRequest.Builder().build());
                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        interstitialAd.show();
                    }
                });

                handler=new Handler();
                runnable=new Runnable() {
                    @Override
                    public void run() {

                        increase.setEnabled(false);
                        teste_basla.setEnabled(false);
                        handler.postDelayed(runnable,1000);
                        sayac++;
                        if(sayac>=3)
                        {
                            handler.removeCallbacks(runnable);
                            increase.setEnabled(true);
                            teste_basla.setEnabled(true);
                            sayac=0;
                        }


                    }
                };
                handler.post(runnable);

            }
        });

        skortablosunugetir();

        teste_basla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SQLiteDatabase sqLiteDatabase = RekabetciActivity.this.openOrCreateDatabase("Haklar",MODE_PRIVATE,null);
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Haklar",null);
                    int hakIx = cursor.getColumnIndex("hak");
                    while (cursor.moveToNext())
                    {
                        hak_update=cursor.getInt(hakIx);
                    }
                    cursor.close();
                    if(hak_update<=0)
                    {
                        Toast.makeText(RekabetciActivity.this, "You don't have enough pen", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        hak_update=hak_update-1;
                        hak_t.setText("Your pens: "+hak_update);
                        String sql ="UPDATE Haklar SET Hak= ? ";
                        SQLiteStatement sqLiteStatement =sqLiteDatabase.compileStatement(sql);
                        sqLiteStatement.bindDouble(1,hak_update);
                        sqLiteStatement.execute();
                        Intent rekabet = new Intent(RekabetciActivity.this,TestActivity1.class);
                        rekabet.putExtra("button","rekabet");
                        startActivity(rekabet);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        try {
            SQLiteDatabase sqLiteDatabase = RekabetciActivity.this.openOrCreateDatabase("Haklar",MODE_PRIVATE,null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Haklar",null);
            int hakIx = cursor.getColumnIndex("hak");
            while (cursor.moveToNext())
            {
                hak_t.setText("Your pens: "+ cursor.getInt(hakIx));
            }
            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void skortablosunugetir() {
        Query query = kullaniciyolu.orderByChild("score").startAt(0).limitToLast(100);
        FirebaseRecyclerOptions<Kisiler2> options =
                new FirebaseRecyclerOptions.Builder<Kisiler2>().setQuery(query, Kisiler2.class).build();
        FirebaseRecyclerAdapter<Kisiler2, Rekabetciholder> adapter = new FirebaseRecyclerAdapter<Kisiler2, Rekabetciholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  Rekabetciholder rekabetciholder, int i, @NonNull  Kisiler2 kisiler2) {
                String ceviri = String.valueOf(kisiler2.getScore());

                rekabetciholder.kullaniciadi.setText(kisiler2.getAd());
                rekabetciholder.kullnci_skor.setText(ceviri);



            }

            @NonNull
            @Override
            public Rekabetciholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ozel_rekabetci_layout,parent,false);
                Rekabetciholder rekabetciholder = new Rekabetciholder(view);
                return rekabetciholder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.startListening();

    }


    public static class Rekabetciholder extends RecyclerView.ViewHolder {
        TextView kullaniciadi, kullanici_siralama, kullnci_skor;

        public Rekabetciholder(@NonNull View itemView) {
            super(itemView);

            kullaniciadi = itemView.findViewById(R.id.rekaber_isim);
            kullnci_skor = itemView.findViewById(R.id.rekabet_skor);
        }
    }
}