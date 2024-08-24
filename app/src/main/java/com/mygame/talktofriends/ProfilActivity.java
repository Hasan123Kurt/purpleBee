package com.mygame.talktofriends;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {
    private String alinanKullaniciId,aktif_durum,aktifKullaniciID;
    private CircleImageView kullaniciProfilResmi;
    private TextView kullaniciProfilAdi,kullaniciProfilDurumu;
    private Button MesajGondermeTalebi,MesajDegerlendirmeTalebiButtonu;

    //Firebase
    private DatabaseReference Kullaniciyolu,SohbetTalebiYolu,SohbetlerYolu,BildirimYolu;
    private FirebaseAuth mYetki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        alinanKullaniciId=getIntent().getExtras().get("tiklanan_kullanici_Id_goster").toString();


        //tanımlamalar
        kullaniciProfilAdi =findViewById(R.id.kullanici_adi_ziyaret);
        kullaniciProfilDurumu = findViewById(R.id.profil_durumu_ziyaret);
        kullaniciProfilResmi = findViewById(R.id.profil_resmi_ziyaret);
        MesajGondermeTalebi = findViewById(R.id.mesaj_gonderme_talebi_buttonu);
        MesajDegerlendirmeTalebiButtonu = findViewById(R.id.mesaj_degerlendirme_talebi_buttonu);

        aktif_durum="yeni";

        //Firebase
        Kullaniciyolu= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
        SohbetTalebiYolu= FirebaseDatabase.getInstance().getReference().child("Sohbet Talebi");
        SohbetlerYolu= FirebaseDatabase.getInstance().getReference().child("Sohbetler");
        BildirimYolu= FirebaseDatabase.getInstance().getReference().child("Bildirimler");

        mYetki=FirebaseAuth.getInstance();

        aktifKullaniciID=mYetki.getCurrentUser().getUid();

        kullanicibilgisial();


    }

    private void kullanicibilgisial() {
        Kullaniciyolu.child(alinanKullaniciId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.exists())&& (dataSnapshot.hasChild("resim")))
                {
                    //veritabanından veri çekip değişkenlere aktarma
                    String kullaniciResmi = dataSnapshot.child("resim").getValue().toString();
                    String kullaniciAdi = dataSnapshot.child("ad").getValue().toString();
                    String kullaniciDurumu  = dataSnapshot.child("durum").getValue().toString();

                    Picasso.get().load(kullaniciResmi).placeholder(R.drawable.yeni_person).into(kullaniciProfilResmi);

                    // verileri kontrollere aktarma

                    kullaniciProfilAdi.setText(kullaniciAdi);
                    kullaniciProfilDurumu.setText(kullaniciDurumu);

                    //Chat Talebi Gönder metodu
                    chatTalepleriniYonet();
                }
                else
                {
                    //veritabanından veri çekip değişkenlere aktarma


                    String kullaniciAdi = dataSnapshot.child("ad").getValue().toString();
                    String kullaniciDurumu  = dataSnapshot.child("durum").getValue().toString();

                    Picasso.get().load(R.drawable.yeni_person).into(kullaniciProfilResmi);

                    // verileri kontrollere aktarma

                    kullaniciProfilAdi.setText(kullaniciAdi);
                    kullaniciProfilDurumu.setText(kullaniciDurumu);
                    chatTalepleriniYonet();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void chatTalepleriniYonet() {
        //talep varsa button gösterme
        SohbetTalebiYolu.child(aktifKullaniciID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(alinanKullaniciId))
                {
                    String talep_turu = dataSnapshot.child(alinanKullaniciId).child("talep_turu").getValue().toString();
                    if(talep_turu.equals("gonderildi"))
                    {
                        aktif_durum = "talep_gonderildi";
                        MesajGondermeTalebi.setText("cancel message request");


                    }
                    else
                    {
                        aktif_durum = "talep_alindi";
                        MesajGondermeTalebi.setText("accept message request");
                        MesajDegerlendirmeTalebiButtonu.setVisibility(View.VISIBLE);
                        MesajDegerlendirmeTalebiButtonu.setEnabled(true);

                        MesajDegerlendirmeTalebiButtonu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MesajTalebiIptal();
                            }
                        });
                    }
                }
                else
                {
                    SohbetlerYolu.child(aktifKullaniciID)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(alinanKullaniciId))
                                    {
                                        aktif_durum="arkadaşlar";
                                        MesajGondermeTalebi.setText("Remove from your friends list");
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(aktifKullaniciID.equals(alinanKullaniciId))
        {
            //buttonu sakla
            MesajGondermeTalebi.setVisibility(View.INVISIBLE);
        }
        else
        {
            //Mesaj talebi gitsin
            MesajGondermeTalebi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MesajGondermeTalebi.setEnabled(false);
                    if(aktif_durum.equals("yeni"))
                    {
                        SohbetTalebiGonder();
                    }
                    if(aktif_durum.equals("talep_gonderildi"))
                    {
                        MesajTalebiIptal();

                    }
                    if(aktif_durum.equals("talep_alindi"))
                    {
                        MesajTalebiKabul();
                    }
                    if(aktif_durum.equals("arkadaşlar"))
                    {
                        OzelSohbetiSil();
                    }

                }
            });
        }

    }

    private void OzelSohbetiSil() {
        //Talebi gönderenden sil
        SohbetlerYolu.child(aktifKullaniciID).child(alinanKullaniciId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    //talebi alandan sil
                    SohbetlerYolu.child(alinanKullaniciId).child(aktifKullaniciID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                MesajGondermeTalebi.setEnabled(true);
                                aktif_durum="yeni";
                                MesajGondermeTalebi.setText("Send message request");
                                MesajDegerlendirmeTalebiButtonu.setVisibility(View.INVISIBLE);
                                MesajDegerlendirmeTalebiButtonu.setEnabled(false);

                            }

                        }
                    });
                }

            }
        });

    }

    private void MesajTalebiKabul() {
        SohbetlerYolu.child(aktifKullaniciID).child(alinanKullaniciId).child("Sohbetler").setValue("Kaydedildi")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        SohbetlerYolu.child(alinanKullaniciId).child(aktifKullaniciID).child("Sohbetler").setValue("Kaydedildi")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            SohbetTalebiYolu.child(aktifKullaniciID).child(alinanKullaniciId)
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful())
                                                            {
                                                                SohbetTalebiYolu.child(alinanKullaniciId).child(aktifKullaniciID)
                                                                        .removeValue()
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                            MesajGondermeTalebi.setEnabled(true);
                                                                            aktif_durum="arkadaşlar";
                                                                            MesajGondermeTalebi.setText("Remove from your friends list");
                                                                            MesajDegerlendirmeTalebiButtonu.setVisibility(View.INVISIBLE);
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


    private void MesajTalebiIptal() {
        //Talebi gönderenden sil
        SohbetTalebiYolu.child(aktifKullaniciID).child(alinanKullaniciId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    //talebi alandan sil
                    SohbetTalebiYolu.child(alinanKullaniciId).child(aktifKullaniciID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                MesajGondermeTalebi.setEnabled(true);
                                aktif_durum="yeni";
                                MesajGondermeTalebi.setText("Send message request");
                                MesajDegerlendirmeTalebiButtonu.setVisibility(View.INVISIBLE);
                                MesajDegerlendirmeTalebiButtonu.setEnabled(false);

                            }

                        }
                    });
                }

            }
        });

    }

    private void SohbetTalebiGonder() {
        // Veritabanına Veri Gönder
        SohbetTalebiYolu.child(aktifKullaniciID).child(alinanKullaniciId).child("talep_turu").setValue("gonderildi")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            //vERİ TABANINA VERİ GÖNDRERME
                            SohbetTalebiYolu.child(alinanKullaniciId).child(aktifKullaniciID).child("talep_turu")
                                    .setValue("alındı").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        //Bildirim
                                        HashMap<String,String> chatnotification = new HashMap<>();
                                        chatnotification.put("kimden",aktifKullaniciID);
                                        chatnotification.put("tur","talep");

                                        //Bildirim Veritabanı yolu
                                        BildirimYolu.child(alinanKullaniciId).push().setValue(chatnotification).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    MesajGondermeTalebi.setEnabled(true);
                                                    aktif_durum="talep_gonderildi";
                                                    MesajGondermeTalebi.setText("Cancel message request");

                                                    try {
                                                        OneSignal.postNotification(new JSONObject("{'contents': {'en': 'Hello! i want to be friend with you.'}, 'include_player_ids': ['" + alinanKullaniciId +"'], 'sound': deduction.mp3}"), null);

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

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
}
