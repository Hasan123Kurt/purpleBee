package com.mygame.talktofriends;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
public class TaleplerFragment extends Fragment {
    private View TaleplerFragmentView;
    private RecyclerView taleplerlistem;
    private TextView yoketmetext;

    //firebase
    private DatabaseReference SohbetTalepleriYolu,KullanicilarYolu,Sohbetleryolu;
    private FirebaseAuth mYetki;
    private String aktifKullaniciId;



    public TaleplerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TaleplerFragmentView= inflater.inflate(R.layout.fragment_talepler, container, false);

        //firebase
        mYetki=FirebaseAuth.getInstance();
        aktifKullaniciId=mYetki.getCurrentUser().getUid();

        SohbetTalepleriYolu= FirebaseDatabase.getInstance().getReference().child("Sohbet Talebi");
        KullanicilarYolu= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
        Sohbetleryolu=FirebaseDatabase.getInstance().getReference().child("Sohbetler");
        yoketmetext=TaleplerFragmentView.findViewById(R.id.yok_etme_text);
        taleplerlistem=TaleplerFragmentView.findViewById(R.id.chat_talepleri_listesi);
        taleplerlistem.setLayoutManager(new LinearLayoutManager(getContext()));
        return TaleplerFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Kisiler> secenekler =new FirebaseRecyclerOptions.Builder<Kisiler>()
                .setQuery(SohbetTalepleriYolu.child(aktifKullaniciId),Kisiler.class).build();

        FirebaseRecyclerAdapter<Kisiler,TaleplerViewHolder> adapter = new FirebaseRecyclerAdapter<Kisiler, TaleplerViewHolder>(secenekler) {
            @Override
            protected void onBindViewHolder(@NonNull final TaleplerViewHolder holder, int i, @NonNull Kisiler kisiler) {


                //taleplerin hepsini alma
                final String kullanici_id_listesi = getRef(i).getKey();
                DatabaseReference talepturual = getRef(i).child("talep_turu").getRef();
                talepturual.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            yoketmetext.setVisibility(View.INVISIBLE);
                            String tur = dataSnapshot.getValue().toString();
                            if(tur.equals("alındı"))
                            {
                                KullanicilarYolu.child(kullanici_id_listesi).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.hasChild("resim"))
                                        {
                                            //Veri tabanı verileri alıp değişkenlere aktarma

                                            final String talepProfilresmi = dataSnapshot.child("resim").getValue().toString();
                                            // çekilen veriler ilgili kontrollere aktarma

                                            Picasso.get().load(talepProfilresmi).into(holder.profilResmi);
                                        }

                                            //Veri tabanı verileri alıp değişkenlere aktarma
                                            final String talepKullaniciAdi = dataSnapshot.child("ad").getValue().toString();
                                            final String talepKullaniciDurum = dataSnapshot.child("durum").getValue().toString();

                                            // çekilen veriler ilgili kontrollere aktarma
                                        holder.kullaniciAdi.setTextColor(Color.YELLOW);
                                            holder.kullaniciAdi.setText(talepKullaniciAdi);
                                            holder.kullaniciDurumu.setTextColor(Color.YELLOW);
                                            holder.kullaniciDurumu.setText("Hey I want to be friends with you. Click to reply");




                                        //her satıra tıklandığında
                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                CharSequence secenekler[] = new CharSequence[]
                                                        {
                                                                "Admit",
                                                                "Reject"
                                                        };
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle(talepKullaniciAdi + " Chat Request");
                                                builder.setItems(secenekler, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        if(i==0)
                                                        {
                                                            Sohbetleryolu.child(aktifKullaniciId).child(kullanici_id_listesi).child("Sohbetler")
                                                                    .setValue("Kaydedildi").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful())
                                                                {
                                                                    Sohbetleryolu.child(kullanici_id_listesi).child(aktifKullaniciId)
                                                                            .child("Sohbetler").setValue("Kaydedildi")
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if(task.isSuccessful())
                                                                                    {
                                                                                        SohbetTalepleriYolu.child(aktifKullaniciId).child(kullanici_id_listesi)
                                                                                                .removeValue()
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if(task.isSuccessful())
                                                                                                        {
                                                                                                            SohbetTalepleriYolu.child(kullanici_id_listesi).child(aktifKullaniciId)
                                                                                                                    .removeValue()
                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                            Toast.makeText(getContext(), "Request Accepted", Toast.LENGTH_LONG).show();
                                                                                                                            
    
                                                                                                                        }
                                                                                                                    });
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                                }
                                                            });

                                                        }
                                                        if(i==1)
                                                        {
                                                            SohbetTalepleriYolu.child(aktifKullaniciId).child(kullanici_id_listesi)
                                                                    .removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful())
                                                                            {
                                                                                SohbetTalepleriYolu.child(kullanici_id_listesi).child(aktifKullaniciId)
                                                                                        .removeValue()
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                Toast.makeText(getContext(), "Request denied", Toast.LENGTH_LONG).show();


                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    });


                                                        }
                                                    }
                                                });
                                                builder.show();

                                            }
                                        });


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
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
            public TaleplerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kullanici_gosterme_layout,parent,false);
                TaleplerViewHolder holder = new TaleplerViewHolder(view);
                return holder;
            }
        };
        taleplerlistem.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.startListening();


    }
    public static class TaleplerViewHolder extends RecyclerView.ViewHolder {
        //Kontroller
        TextView kullaniciAdi, kullaniciDurumu;
        CircleImageView profilResmi;
        Button KabulButtonu, IptalButtonu;


        public TaleplerViewHolder(@NonNull View itemView) {
            super(itemView);
            //Kontrol tanımlamaları
            kullaniciAdi = itemView.findViewById(R.id.kullanici_profil_adi);
            kullaniciDurumu = itemView.findViewById(R.id.kullanici_durumu);
            profilResmi = itemView.findViewById(R.id.kullanicilar_profil_resmi);
            KabulButtonu = itemView.findViewById(R.id.talep_kabul_buttonu);
            IptalButtonu = itemView.findViewById(R.id.talep_iptal_buttonu);


        }
    }
}
