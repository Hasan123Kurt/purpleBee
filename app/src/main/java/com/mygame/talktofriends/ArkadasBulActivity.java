package com.mygame.talktofriends;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mygame.talktofriends.Model.Kisiler;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

//import android.widget.Toolbar;


public class ArkadasBulActivity extends AppCompatActivity {
   private Toolbar mToolbar;
   private RecyclerView ArkadasbulRecycelerlistesi;

   //firebase
    private DatabaseReference Kullaniciyolu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadas_bul);
        ArkadasbulRecycelerlistesi=findViewById(R.id.recyler_listesi);
        ArkadasbulRecycelerlistesi.setLayoutManager(new LinearLayoutManager(this));


        //toolbar
        mToolbar=findViewById(R.id.arkadas_bul_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Global");

        //firebase tanımlama
        Kullaniciyolu= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");

    }

    @Override
    protected void onStart() {
        super.onStart();

        //başlagığında
        //sorgu seçenekler
        FirebaseRecyclerOptions<Kisiler> secenekler =
                new FirebaseRecyclerOptions.Builder<Kisiler>()
                .setQuery(Kullaniciyolu,Kisiler.class)
                .build();
        FirebaseRecyclerAdapter<Kisiler,ArkadasbulviewHolder> adapter = new FirebaseRecyclerAdapter<Kisiler, ArkadasbulviewHolder>(secenekler) {
            @Override
            protected void onBindViewHolder(@NonNull ArkadasbulviewHolder arkadasbulviewHolder, final int i, @NonNull Kisiler kisiler) {
                arkadasbulviewHolder.kullaniciAdi.setText(kisiler.getAd());
                arkadasbulviewHolder.kullanicidurum.setText(kisiler.getDurum());
                Picasso.get().load(kisiler.getResim()).placeholder(R.drawable.yeni_person).into(arkadasbulviewHolder.profilresmi);
                //tıklanıldığında
                arkadasbulviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tiklanan_kullanici_Id_goster = getRef(i).getKey();
                        Intent profilAktivite = new Intent(ArkadasBulActivity.this,ProfilActivity.class);
                        profilAktivite.putExtra("tiklanan_kullanici_Id_goster",tiklanan_kullanici_Id_goster);
                        startActivity(profilAktivite);
                    }
                });

            }

            @NonNull
            @Override
            public ArkadasbulviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kullanici_gosterme_layout,parent,false);
                ArkadasbulviewHolder viewholder = new ArkadasbulviewHolder(view);
                return viewholder;
            }
        };
        ArkadasbulRecycelerlistesi.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.startListening();
    }
    public static class ArkadasbulviewHolder extends RecyclerView.ViewHolder
    {
        TextView kullaniciAdi,kullanicidurum;
        CircleImageView profilresmi;

        public ArkadasbulviewHolder(@NonNull View itemView) {
            super(itemView);
            //tanımlamalar
            kullaniciAdi=itemView.findViewById(R.id.kullanici_profil_adi);
            kullanicidurum=itemView.findViewById(R.id.kullanici_durumu);
            profilresmi=itemView.findViewById(R.id.kullanicilar_profil_resmi);
        }
    }
}
