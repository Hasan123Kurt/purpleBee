package com.mygame.talktofriends;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mygame.talktofriends.Model.Kisiler;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView ArkadasbulRecycelerlistesi;
    private Button searchbtn;
    private EditText aranacakkelime;
    String arama;
    private int anahtar=0;

    //firebase
    private DatabaseReference Kullaniciyolu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchbtn=findViewById(R.id.search_button);
        aranacakkelime=findViewById(R.id.search_girdi);


        ArkadasbulRecycelerlistesi=findViewById(R.id.search_recycler);
        ArkadasbulRecycelerlistesi.setLayoutManager(new LinearLayoutManager(this));
        //toolbar
        mToolbar=findViewById(R.id.tool_search);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Search Friend");


        Kullaniciyolu= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arama = aranacakkelime.getText().toString();
                if(TextUtils.isEmpty(arama))
                {
                    Toast.makeText(SearchActivity.this, "Please enter the name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    aramayapma();
                }

            }
        });
    }

    private void aramayapma() {
        Query query = Kullaniciyolu.orderByChild("ad").equalTo(arama);


        FirebaseRecyclerOptions<Kisiler> secenekler =
                new FirebaseRecyclerOptions.Builder<Kisiler>()
                        .setQuery(query,Kisiler.class)
                        .build();
        FirebaseRecyclerAdapter<Kisiler, SearchActivity.ArkadasbulviewHolder> adapter = new FirebaseRecyclerAdapter<Kisiler, SearchActivity.ArkadasbulviewHolder>(secenekler) {
            @Override
            protected void onBindViewHolder(@NonNull SearchActivity.ArkadasbulviewHolder arkadasbulviewHolder,final int i, @NonNull Kisiler kisiler) {


                    arkadasbulviewHolder.kullaniciAdi.setText(kisiler.getAd());
                    arkadasbulviewHolder.kullanicidurum.setText(kisiler.getDurum());
                    Picasso.get().load(kisiler.getResim()).placeholder(R.drawable.yeni_person).into(arkadasbulviewHolder.profilresmi);
                    //tıklanıldığında
                    arkadasbulviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String tiklanan_kullanici_Id_goster = getRef(i).getKey();
                            Intent profilAktivite = new Intent(SearchActivity.this,ProfilActivity.class);
                            profilAktivite.putExtra("tiklanan_kullanici_Id_goster",tiklanan_kullanici_Id_goster);
                            startActivity(profilAktivite);

                        }
                    });

            }

            @NonNull
            @Override
            public SearchActivity.ArkadasbulviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kullanici_gosterme_layout,parent,false);
                SearchActivity.ArkadasbulviewHolder viewholder = new SearchActivity.ArkadasbulviewHolder(view);
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
