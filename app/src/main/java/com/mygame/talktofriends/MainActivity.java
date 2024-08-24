package com.mygame.talktofriends;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager myViewPager;
    public TabLayout myTablaLayout;
    private SekmeErisimAdapter mySekmeErisimAdapter;
    private int i=0;
   // private static int SPLASH_TIME_OUT = 4000;


    private AdView mAdView;



    //firebase

    private FirebaseAuth mYetki;
    private DatabaseReference kullanicilarReference,grupreferans,mesajyolu,databaseReference;
    private FirebaseDatabase database;

    private InterstitialAd mInterstitialAd;
    private String aktifKullaniciId,IdMesajAlici,IdmesajGonderen;
    private String alici="0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent iin = getIntent();
        Bundle bundle = iin.getExtras();
        if(bundle!=null)
        {
            alici = (String)bundle.get("intro");
        }
        //intro
        if(alici.equals("intro"))
        {
            Intent intent2 = new Intent(getApplicationContext(),IntroActivity.class);
            startActivity(intent2);

        }


        //animasyon
        RelativeLayout relativeLayout = findViewById(R.id.arka_plan);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();





//admob


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mYetki=FirebaseAuth.getInstance();



        // OneSignal Initialization

        kullanicilarReference=FirebaseDatabase.getInstance().getReference();
        databaseReference=FirebaseDatabase.getInstance().getReference();

        // OneSignal Initialization




        mToolbar=findViewById(R.id.anasayfa_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Purple Bee");


        myViewPager = findViewById(R.id.ana_sekmeler_pager);
        mySekmeErisimAdapter = new SekmeErisimAdapter (getSupportFragmentManager());
        myViewPager.setAdapter(mySekmeErisimAdapter);
        myTablaLayout=findViewById(R.id.anasekmeler);
        myTablaLayout.setupWithViewPager(myViewPager);


        //firebase
        grupreferans=FirebaseDatabase.getInstance().getReference("Gruplar");

    }

    @Override
    protected void onStart() {
        super.onStart();


        try {
            FirebaseUser mevcutKullanici=mYetki.getCurrentUser();
            if(mevcutKullanici==null)
            {
                KullaniciyiLoginActivityeGonder();
            }
            else
            {
                kullaniciDurumuGuncelle("çevrimiçi");
                kullanicininVarliginiDogrula();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser mevcutKullanici=mYetki.getCurrentUser();

        if(mevcutKullanici != null)
        {
            kullaniciDurumuGuncelle("çevrimdışı");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser mevcutKullanici=mYetki.getCurrentUser();

        if(mevcutKullanici != null)
        {
            kullaniciDurumuGuncelle("çevrimdışı");
        }
    }



    private void kullanicininVarliginiDogrula() {
        String mevcutkullaniciId = mYetki.getCurrentUser().getUid();
        kullanicilarReference.child("Kullanicilar").child(mevcutkullaniciId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.child("ad").exists()))
                {
                    OneSignal.startInit(MainActivity.this)
                            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                            .unsubscribeWhenNotificationsAreDisabled(true)
                            .init();



                    OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
                        @Override
                        public void idsAvailable(final String userId, String registrationId) {
                            UUID uuid = UUID.randomUUID();
                            final String uuidString = uuid.toString();
                            DatabaseReference sorguDatabaseReference = FirebaseDatabase.getInstance().getReference("PlayerIds");

                            sorguDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ArrayList<String> playerIDsFromserver = new ArrayList<>();
                                    for(DataSnapshot ds: dataSnapshot.getChildren())
                                    {

                                        HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();
                                        String currentPlayerId = hashMap.get("playerID");
                                        playerIDsFromserver.add(currentPlayerId);


                                    }
                                    if(!playerIDsFromserver.contains(userId))
                                    {
                                        try {
                                            databaseReference.child("PlayerIds").child(uuidString).child("playerID").setValue(userId);
                                            String mevcutId = mYetki.getCurrentUser().getUid();
                                            kullanicilarReference.child("Kullanicilar").child(mevcutId).child("BildirimID").setValue(userId);
                                            Toast.makeText(MainActivity.this, userId, Toast.LENGTH_SHORT).show();

                                        }catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }

                                    }



                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });

                }
                else
                {
                    Intent ayarlar = new Intent(MainActivity.this,AyarlarActivity.class);
                    //Geri tuşunu engelleme
                    ayarlar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(ayarlar);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void KullaniciyiLoginActivityeGonder() {
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.secenekler_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.ana_arkadasBul)
         {
             Intent arkadaBul = new Intent(MainActivity.this,ArkadasBulActivity.class);
             startActivity(arkadaBul);


         }
         if(item.getItemId()==R.id.ana_serach)
         {
             Intent arama = new Intent(MainActivity.this,SearchActivity.class);
             startActivity(arama);

         }
         if(item.getItemId()==R.id.rekabetci)
         {
             Intent rekabet = new Intent(MainActivity.this,RekabetciActivity.class);
             startActivity(rekabet);

         }
        if(item.getItemId()==R.id.quiz)
        {
            Intent quiz = new Intent(MainActivity.this,QuizActivity.class);
            startActivity(quiz);


        }
        if(item.getItemId()==R.id.ayarlar)
        {
            Intent ayarla = new Intent(MainActivity.this,AyarlarActivity.class);
            startActivity(ayarla);

        }
        if(item.getItemId()==R.id.cikisYap)
        {
            kullaniciDurumuGuncelle("Offline");
        mYetki.signOut();
        Intent giris = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(giris);
        }

        if(item.getItemId()==R.id.ana_grup_olustur)
        {
            //admob geçiş
            MobileAds.initialize(this,"ca-app-pub-2724540026642482~8500905698");
            final InterstitialAd interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId("ca-app-pub-2724540026642482/5661073643");
            interstitialAd.loadAd(new AdRequest.Builder().build());
            interstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    interstitialAd.show();
                }
            });
            yeniGrupTalebi();
        }
        return true;

    }
                 //Grup Oluşturma
    private void yeniGrupTalebi() {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialog);
        builder.setTitle("Enter group name:");

        final EditText grupalani = new EditText(MainActivity.this);
        grupalani.setHint("Sample: Music Group");
        grupalani.setMaxLines(2);
        builder.setView(grupalani);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String grupadi = grupalani.getText().toString();
                if(TextUtils.isEmpty(grupadi))
                {
                    Toast.makeText(MainActivity.this, "Enter group name", Toast.LENGTH_LONG).show();
                }
                else if(grupalani.length()>50)
                {
                    Toast.makeText(MainActivity.this, "too long!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    YeniGrupolustur(grupadi);
                }

            }
        });
        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        builder.show();

    }

    private void YeniGrupolustur(final String grupadi) {

        grupreferans.child(grupadi).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, grupadi+" group created.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void kullaniciDurumuGuncelle(String durum)
    {
        String kaydedilenAktifZaman,kaydedilenAktifTarih;

        Calendar calendar = Calendar.getInstance();
        //Tarih formatı
        SimpleDateFormat aktifTarih = new SimpleDateFormat("MMM dd, yyyy");
        kaydedilenAktifTarih = aktifTarih.format(calendar.getTime());

        // saat formatı
        SimpleDateFormat aktifZaman = new SimpleDateFormat("hh:mm a");
        kaydedilenAktifZaman = aktifZaman.format(calendar.getTime());

        HashMap<String,Object> cevrimiciDurumu = new HashMap<>();
        cevrimiciDurumu.put("zaman",kaydedilenAktifZaman);
        cevrimiciDurumu.put("tarih",kaydedilenAktifTarih);
        cevrimiciDurumu.put("durum",durum);
        aktifKullaniciId=mYetki.getCurrentUser().getUid();

        kullanicilarReference.child("Kullanicilar").child(aktifKullaniciId).child("kullaniciDurumu").updateChildren(cevrimiciDurumu);
    }
}

