package com.mygame.talktofriends;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
public class KisilerFragment extends Fragment {
    private View KisilerView;
    private RecyclerView kisilerListem;

    //Firebase
    private DatabaseReference SohbetlerYolu,KullanicilarYolu;
    private FirebaseAuth mYetki;


    private String aktifKullaniciId;

    public KisilerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        KisilerView= inflater.inflate(R.layout.fragment_kisiler, container, false);
        kisilerListem=KisilerView.findViewById(R.id.kisiler_listesi);
        kisilerListem.setLayoutManager(new LinearLayoutManager(getContext()));

        //Firebase
        mYetki=FirebaseAuth.getInstance();
        aktifKullaniciId=mYetki.getCurrentUser().getUid();
        SohbetlerYolu= FirebaseDatabase.getInstance().getReference().child("Sohbetler").child(aktifKullaniciId);
        KullanicilarYolu = FirebaseDatabase.getInstance().getReference().child("Kullanicilar");

        return KisilerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions secenekler = new FirebaseRecyclerOptions.Builder<Kisiler>()
                .setQuery(SohbetlerYolu,Kisiler.class)
                .build();
        FirebaseRecyclerAdapter<Kisiler,KisilerViewHolder>adapter = new FirebaseRecyclerAdapter<Kisiler, KisilerViewHolder>(secenekler) {
            @Override
            protected void onBindViewHolder(@NonNull final KisilerViewHolder kisilerViewHolder, int i, @NonNull Kisiler kisiler) {
                String tiklanansatirkullaniciId = getRef(i).getKey();
                KullanicilarYolu.child(tiklanansatirkullaniciId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        if(dataSnapshot.exists())
                        {
                            String durumAna = dataSnapshot.child("durum").getValue().toString();
                            kisilerViewHolder.kullaniciDurumu.setText(durumAna);

                            if(dataSnapshot.child("kullaniciDurumu").hasChild("durum"))
                            {
                                String durum = dataSnapshot.child("kullaniciDurumu").child("durum").getValue().toString();
                                String tarih = dataSnapshot.child("kullaniciDurumu").child("tarih").getValue().toString();
                                String zaman = dataSnapshot.child("kullaniciDurumu").child("zaman").getValue().toString();



                                if(durum.equals("çevrimiçi"))
                                {
                                    kisilerViewHolder.cevrimiciIconu.setVisibility(View.VISIBLE);
                                }
                                else
                                {

                                    kisilerViewHolder.cevrimiciIconu.setVisibility(View.INVISIBLE);
                                }


                            }

                            else
                            {
                                kisilerViewHolder.cevrimiciIconu.setVisibility(View.INVISIBLE);
                                kisilerViewHolder.kullaniciDurumu.setText("Offline");
                            }



                            if(dataSnapshot.hasChild("resim"))
                            {
                                String profilresmi = dataSnapshot.child("resim").getValue().toString();
                                String  kullaniciAdi = dataSnapshot.child("ad").getValue().toString();
                             //   String kullaniciDurumu = dataSnapshot.child("durum").getValue().toString();

                                kisilerViewHolder.kullaniciAdi.setText(kullaniciAdi);
                              //  kisilerViewHolder.kullaniciDurumu.setText(kullaniciDurumu);
                                Picasso.get().load(profilresmi).placeholder(R.drawable.yeni_person).into(kisilerViewHolder.profilresmi);
                            }
                            else
                            {
                                //resim olmadan işlemlere devam etme
                                String  kullaniciAdi = dataSnapshot.child("ad").getValue().toString();
                                String kullaniciDurumu = dataSnapshot.child("durum").getValue().toString();

                                kisilerViewHolder.kullaniciAdi.setText(kullaniciAdi);
                                kisilerViewHolder.kullaniciDurumu.setText(kullaniciDurumu);


                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public KisilerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kullanici_gosterme_layout,parent,false);
                KisilerViewHolder viewHolder = new KisilerViewHolder(view);
                return viewHolder;

            }
        };
        kisilerListem.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.startListening();
    }
    public static class KisilerViewHolder extends RecyclerView.ViewHolder {
        TextView kullaniciAdi,kullaniciDurumu;
        CircleImageView profilresmi;
        ImageView cevrimiciIconu;

        public KisilerViewHolder(@NonNull View itemView) {
            super(itemView);
            kullaniciAdi=itemView.findViewById(R.id.kullanici_profil_adi);
            kullaniciDurumu=itemView.findViewById(R.id.kullanici_durumu);
            profilresmi=itemView.findViewById(R.id.kullanicilar_profil_resmi);
            cevrimiciIconu=itemView.findViewById(R.id.kullanici_cemrimici_olma_durumu);


        }
    }
}
