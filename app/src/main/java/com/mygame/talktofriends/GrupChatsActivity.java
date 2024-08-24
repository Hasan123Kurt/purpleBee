package com.mygame.talktofriends;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygame.talktofriends.Adapter.MesajAdaptor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SimpleTimeZone;


public class GrupChatsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageButton Mesajgondermebuttonu;
    private EditText kullaniciGirdisi;
    private ScrollView mScrollView;
    private TextView metinmesajlarinigoster;
    private DatabaseReference kullaniciYolu,grupadiYolu,grupmesajanahtariyolu;




    //Firebase
    private FirebaseAuth mYetki;
    private String mevcutgrupadi,aktifkullaniciId,aktifkullaniciADi,aktifTarih,aktifzaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grup_chats);

        //intenti al
        mevcutgrupadi=getIntent().getExtras().get("grupAdi").toString();

        //Firebase tanımlama
        mYetki=FirebaseAuth.getInstance();
        aktifkullaniciId=mYetki.getCurrentUser().getUid();
        kullaniciYolu= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
        grupadiYolu= FirebaseDatabase.getInstance().getReference().child("Gruplar").child(mevcutgrupadi);



        // tanımlamalar

        mToolbar = findViewById(R.id.grup_chat_bar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mevcutgrupadi);

        Mesajgondermebuttonu=findViewById(R.id.msaj_gonderme_buttonu);
        kullaniciGirdisi=findViewById(R.id.grup_mesaj_girdisi);
        metinmesajlarinigoster = findViewById(R.id.grup_chat_metni_gosterme);
        mScrollView=findViewById(R.id.grup_scrollview);


        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/opensans.ttf");
        metinmesajlarinigoster.setTypeface(typeface);


        kullanicibilgisial();
//mesajııı göndermeeeee
        Mesajgondermebuttonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MesajVeritabaninaKaydet();
                kullaniciGirdisi.setText("");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        grupadiYolu.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    mesajlariGoster(dataSnapshot);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    mesajlariGoster(dataSnapshot);
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void mesajlariGoster(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();
        while (iterator.hasNext())
        {
            String sohbetAdi = (String)((DataSnapshot)iterator.next()).getValue();
            String sohbetmesaji = (String)((DataSnapshot)iterator.next()).getValue();
            String sohbettarihi = (String)((DataSnapshot)iterator.next()).getValue();
            String sohbetZamani = (String)((DataSnapshot)iterator.next()).getValue();
            metinmesajlarinigoster.append(sohbetZamani +"      "+ sohbettarihi +"\n" + sohbetAdi+": "+sohbetmesaji + "\n" +"\n\n\n" );



           // Toast.makeText(this, sohbetmesaji, Toast.LENGTH_SHORT).show();

        }
    }

    private void MesajVeritabaninaKaydet() {
        String mesaj= kullaniciGirdisi.getText().toString();
        String mesajAnahtari= grupadiYolu.push().getKey();


        if(TextUtils.isEmpty(mesaj))
        {
            Toast.makeText(this, "Enter message!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Calendar Tarihicintakvim=Calendar.getInstance();
            SimpleDateFormat aktifTarihiformat = new SimpleDateFormat("MMM dd, yyyy");
            aktifTarih=aktifTarihiformat.format(Tarihicintakvim.getTime());

            Calendar zamanicinTak = Calendar.getInstance();
            SimpleDateFormat aktifzamanformati = new SimpleDateFormat("hh:mm:ss a");
            aktifzaman = aktifzamanformati.format(zamanicinTak.getTime());

            HashMap<String,Object>grupMesajAnahtari = new HashMap<>();
            grupadiYolu.updateChildren(grupMesajAnahtari);
            grupmesajanahtariyolu = grupadiYolu.child(mesajAnahtari);

            HashMap<String,Object> mesajbilgisimap = new HashMap<>();
            mesajbilgisimap.put("ad",aktifkullaniciADi);
            mesajbilgisimap.put("mesaj",mesaj);
            mesajbilgisimap.put("tarih",aktifTarih);
            mesajbilgisimap.put("zaman",aktifzaman);

            grupmesajanahtariyolu.updateChildren(mesajbilgisimap);

        }
    }

    private void kullanicibilgisial() {
        kullaniciYolu.child(aktifkullaniciId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    aktifkullaniciADi=dataSnapshot.child("ad").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
