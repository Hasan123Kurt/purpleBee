package com.mygame.talktofriends;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygame.talktofriends.Model.Kisiler;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {
    private View OzelsohbetlerView;
    private RecyclerView sohbetlerListesi;
    //Firebase
    private DatabaseReference sohbetyolu,kullaniciYolu;
    private FirebaseAuth mYetki;
    private String aktifKullaniciId;



    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        OzelsohbetlerView= inflater.inflate(R.layout.fragment_chats, container, false);
        //Firebase
        mYetki=FirebaseAuth.getInstance();
        aktifKullaniciId=mYetki.getCurrentUser().getUid();
        sohbetyolu= FirebaseDatabase.getInstance().getReference().child("Sohbetler").child(aktifKullaniciId);
        kullaniciYolu= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");


        sohbetlerListesi=OzelsohbetlerView.findViewById(R.id.sohbetler_listesi);
        sohbetlerListesi.setLayoutManager(new LinearLayoutManager(getContext()));
        return OzelsohbetlerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Kisiler> secenekler = new FirebaseRecyclerOptions.Builder<Kisiler>()
                .setQuery(sohbetyolu,Kisiler.class)
                .build();
        FirebaseRecyclerAdapter<Kisiler,SohbetlerViewHolder> adapter = new FirebaseRecyclerAdapter<Kisiler, SohbetlerViewHolder>(secenekler) {
            @Override
            protected void onBindViewHolder(@NonNull final SohbetlerViewHolder sohbetlerViewHolder, int i, @NonNull Kisiler kisiler) {
                final String kullaniciIdleri = getRef(i).getKey();
                final String[] resimal = {"Varsayılan Resim"};

                //Veritabanından veri çağırma
                kullaniciYolu.child(kullaniciIdleri).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                      if(dataSnapshot.exists())
                      {
                          if(dataSnapshot.hasChild("resim"))
                          {
                              resimal[0] = dataSnapshot.child("resim").getValue().toString();
                              //Veri tabanından gelen veriyi kontrole aktarma
                              Picasso.get().load(resimal[0]).into(sohbetlerViewHolder.profilresmi);
                          }
                          // Veri tabanından kullanıcı durumuna yönelik verileri çekme

                          final String adAll= dataSnapshot.child("ad").getValue().toString();
                          final String durumAl= dataSnapshot.child("durum").getValue().toString();

                          sohbetlerViewHolder.kullaniciAdi.setText(adAll);


                          if(dataSnapshot.child("kullaniciDurumu").hasChild("durum"))
                          {
                              String durum = dataSnapshot.child("kullaniciDurumu").child("durum").getValue().toString();
                              String tarih = dataSnapshot.child("kullaniciDurumu").child("tarih").getValue().toString();
                              String zaman = dataSnapshot.child("kullaniciDurumu").child("zaman").getValue().toString();


                              if(durum.equals("çevrimiçi"))
                              {
                                  sohbetlerViewHolder.kullaniciDurumu.setText("Online");
                              }
                              else if(durum.equals("çevrimdışı"))
                              {
                                  sohbetlerViewHolder.kullaniciDurumu.setText("Last Seen:  "+tarih + " "+zaman);
                              }

                          }

                          //her satıra tıklandığında ne yapsın
                          sohbetlerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                  //chat aktivitisine git
                                  Intent chatAktivite = new Intent(getContext(),ChatActivity.class);
                                  chatAktivite.putExtra("kullanici_id_ziyaret",kullaniciIdleri);
                                  chatAktivite.putExtra("kullanici_adi_ziyaret",adAll);
                                  chatAktivite.putExtra("resim_ziyaret", resimal[0]);
                                  startActivity(chatAktivite);
                              }
                          });
                      }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public SohbetlerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kullanici_gosterme_layout,parent,false);
                return new SohbetlerViewHolder(view);
            }
        };
        sohbetlerListesi.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.startListening();
    }
    public static class SohbetlerViewHolder extends RecyclerView.ViewHolder {

        //Kontroller
        CircleImageView profilresmi;
        TextView kullaniciAdi,kullaniciDurumu;

        public SohbetlerViewHolder(@NonNull View itemView) {
            super(itemView);
            kullaniciAdi=itemView.findViewById(R.id.kullanici_profil_adi);
            kullaniciDurumu=itemView.findViewById(R.id.kullanici_durumu);
            profilresmi=itemView.findViewById(R.id.kullanicilar_profil_resmi);


        }
    }
}
