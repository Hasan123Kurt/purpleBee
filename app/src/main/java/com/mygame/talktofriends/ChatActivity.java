package com.mygame.talktofriends;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mygame.talktofriends.Adapter.MesajAdaptor;
import com.mygame.talktofriends.Model.Mesajlar;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private String IdMesajAlici,AdmesajAlici,ResimMesajAlici,IdmesajGonderen;
    private TextView kullaniciAdi,kullaniciSongorulme;
    private CircleImageView kullaniciResmi;
    private ImageView SohbeteGondermeoku;
    private EditText GirilenMesajMetni;

    //toolbar
    private Toolbar SohbetToolbar;

    MainActivity mainActivity = new MainActivity();

    //Firebase
    private FirebaseAuth mYetki;
    private DatabaseReference mesajYolu,kullaniciyolu,mesajalicibildirimId,mesajgondereninadi,anayol;
    private final List<Mesajlar> mesajlarList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MesajAdaptor mesajAdaptor;
    private RecyclerView kullaniciMesajlariListesi;

    private String kaydedilenAktifZaman,kaydedilenAktifTarih;
    private String kontrolcu="",myUrl="";
    private StorageTask yuklemegorevi;
    private Uri dosyaUri;
    private FirebaseDatabase database;
    private int i=0;

    private String alici;
    private String gondereninadi;


    //progress
    private ProgressDialog yuklemebar;
    private ImageButton mesajGondermeButtonu,dosyaGondermeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        try {
            FirebaseUser mevcutKullanici=mYetki.getCurrentUser();
            if(mevcutKullanici==null)
            {

            }
            else
            {
                mainActivity.kullaniciDurumuGuncelle("çevrimiçi");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //chat fragmenten gelen intenti al
        IdMesajAlici=getIntent().getExtras().get("kullanici_id_ziyaret").toString();
        AdmesajAlici=getIntent().getExtras().get("kullanici_adi_ziyaret").toString();
        ResimMesajAlici=getIntent().getExtras().get("resim_ziyaret").toString();


        //Tanımlamalar
        kullaniciAdi=findViewById(R.id.kullanici_adi_gosterme_chat_activity);
        kullaniciSongorulme=findViewById(R.id.kullanici_son_gorulme_chat_activity);
        kullaniciResmi=findViewById(R.id.kullanicilar_profil_resmi_chat_activity);
        SohbeteGondermeoku=findViewById(R.id.sohbetana_sayfaya_gonderme);
        mesajGondermeButtonu=findViewById(R.id.mesaj_gonder_btn);
        dosyaGondermeBtn=findViewById(R.id.dosya_gonder_btn);
        GirilenMesajMetni=findViewById(R.id.girilen_mesaj_metni);

        mesajAdaptor = new MesajAdaptor(mesajlarList);
        kullaniciMesajlariListesi=findViewById(R.id.kullanicilarin_ozel_mesajlarinin_listesi);
        linearLayoutManager=new LinearLayoutManager(this);
        kullaniciMesajlariListesi.setLayoutManager(linearLayoutManager);
        kullaniciMesajlariListesi.setAdapter(mesajAdaptor);

        yuklemebar = new ProgressDialog(this);

        //takvim
        Calendar calendar = Calendar.getInstance();
        //Tarih formatı
        SimpleDateFormat aktifTarih = new SimpleDateFormat("MMM dd, yyyy");
        kaydedilenAktifTarih = aktifTarih.format(calendar.getTime());

        // saat formatı
        SimpleDateFormat aktifZaman = new SimpleDateFormat("hh:mm a");
        kaydedilenAktifZaman = aktifZaman.format(calendar.getTime());

        //firebase
        mYetki=FirebaseAuth.getInstance();
        IdmesajGonderen=mYetki.getCurrentUser().getUid();
        mesajYolu = FirebaseDatabase.getInstance().getReference();
        kullaniciyolu = FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
       mesajalicibildirimId = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(IdMesajAlici).child("BildirimID");
       mesajgondereninadi = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(IdmesajGonderen).child("ad");



        SohbeteGondermeoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



        //kontrolleri  intentle gelenleri aktarma

        kullaniciAdi.setText(AdmesajAlici);
        Picasso.get().load(ResimMesajAlici).placeholder(R.drawable.yeni_person).into(kullaniciResmi);

        //mesaj gönderme buttonu tıklandığında
        mesajGondermeButtonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    MesajGonder();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });

        //dosya gönderme butonu tıkladığında
        dosyaGondermeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence secenekler [] = new CharSequence[]
                        {
                                "Image","PDF","WORD"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                builder.setTitle("Select file");
                builder.setItems(secenekler, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0)
                        {

                            kontrolcu="resim";
                            //Telefon dosyalarına gitme
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent,"Choose image"),438);
                        }
                        if(i==1)
                        {
                            kontrolcu="pdf";
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/pdf");
                            startActivityForResult(Intent.createChooser(intent,"Choose pdf"),438);

                        }
                        if(i==2)
                        {
                            kontrolcu="docx";
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/*");
                            startActivityForResult(Intent.createChooser(intent,"Choose other files"),438);

                        }

                    }
                });
                builder.show();
            }
        });

        mesajYolu.child("Mesajlar").child(IdmesajGonderen).child(IdMesajAlici)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        //veri tabanından veriyi alıp modele aktarma
                        Mesajlar mesajlar = dataSnapshot.getValue(Mesajlar.class);

                        //modeli listeye ekleme
                        mesajlarList.add(mesajlar);
                        mesajAdaptor.notifyDataSetChanged(); //anlık güncelleme



                        //ScrollView ayarlama
                        kullaniciMesajlariListesi.smoothScrollToPosition(kullaniciMesajlariListesi.getAdapter().getItemCount());

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


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

        SonGorulmayiGoster();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==438 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
//           progress
            yuklemebar.setTitle("Sending Files");
            yuklemebar.setMessage("Please Wait");
            yuklemebar.setCanceledOnTouchOutside(false);
            yuklemebar.show();

            dosyaUri=data.getData();
            if(!kontrolcu.equals("resim"))
            {
                StorageReference depolamayolu = FirebaseStorage.getInstance().getReference().child("Dokuman Dosyalari");
                final String mesajGonderenYolu = "Mesajlar/"+IdmesajGonderen+"/"+IdMesajAlici;
                final String mesajAlanYolu = "Mesajlar/"+IdMesajAlici+"/"+IdmesajGonderen;
                final DatabaseReference kullaniciMesajAnahtarYolu = mesajYolu.child("Mesajlar").child(IdmesajGonderen).child(IdMesajAlici).push();
                final String mesajEklemeId = kullaniciMesajAnahtarYolu.getKey();
                final StorageReference dosyayolu = depolamayolu.child(mesajEklemeId + "." + kontrolcu);


                yuklemegorevi=dosyayolu.putFile(dosyaUri);
                yuklemegorevi.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if(!task.isSuccessful())
                        {

                            throw task.getException();
                        }
                        return dosyayolu.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task <Uri> task) {
                        if(task.isSuccessful())
                        {
                            Uri indirmeUrl = task.getResult();
                            myUrl = indirmeUrl.toString();

                            Map mesajMetniGovdesi = new HashMap();
                            mesajMetniGovdesi.put("mesaj",myUrl);
                            mesajMetniGovdesi.put("ad",dosyaUri.getLastPathSegment());
                            mesajMetniGovdesi.put("tur",kontrolcu);
                            mesajMetniGovdesi.put("kimden",IdmesajGonderen);
                            mesajMetniGovdesi.put("kime",IdMesajAlici);
                            mesajMetniGovdesi.put("mesajID",mesajEklemeId);
                            mesajMetniGovdesi.put("zaman",kaydedilenAktifZaman);
                            mesajMetniGovdesi.put("tarih",kaydedilenAktifTarih);

                            Map mesajGovdesiDetaylari = new HashMap();
                            mesajGovdesiDetaylari.put(mesajGonderenYolu + "/" + mesajEklemeId, mesajMetniGovdesi);
                            mesajGovdesiDetaylari.put(mesajAlanYolu + "/" + mesajEklemeId, mesajMetniGovdesi);
                            mesajYolu.updateChildren(mesajGovdesiDetaylari).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful())
                                    {
                                        yuklemebar.dismiss();
                                    }
                                    else
                                    {
                                        yuklemebar.dismiss();
                                        Toast.makeText(ChatActivity.this, "Error please try again", Toast.LENGTH_SHORT).show();
                                    }
                                    GirilenMesajMetni.setText("");

                                }
                            });
                        }
                    }
                });

            }
            else if(kontrolcu.equals("resim"))
            {
                StorageReference depolamayolu = FirebaseStorage.getInstance().getReference().child("Resim Dosyaları");

                final String mesajGonderenYolu = "Mesajlar/"+IdmesajGonderen+"/"+IdMesajAlici;
                final String mesajAlanYolu = "Mesajlar/"+IdMesajAlici+"/"+IdmesajGonderen;
                final DatabaseReference kullaniciMesajAnahtarYolu = mesajYolu.child("Mesajlar").child(IdmesajGonderen).child(IdMesajAlici).push();
                final String mesajEklemeId = kullaniciMesajAnahtarYolu.getKey();
                final StorageReference dosyayolu = depolamayolu.child(mesajEklemeId + "." + "jpg");
                yuklemegorevi=dosyayolu.putFile(dosyaUri);
                yuklemegorevi.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if(!task.isSuccessful())
                        {

                            throw task.getException();
                        }
                        return dosyayolu.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task <Uri> task) {
                        if(task.isSuccessful())
                        {
                            Uri indirmeUrl = task.getResult();
                            myUrl = indirmeUrl.toString();

                            Map mesajMetniGovdesi = new HashMap();
                            mesajMetniGovdesi.put("mesaj",myUrl);
                            mesajMetniGovdesi.put("ad",dosyaUri.getLastPathSegment());
                            mesajMetniGovdesi.put("tur","resim");
                            mesajMetniGovdesi.put("kimden",IdmesajGonderen);
                            mesajMetniGovdesi.put("kime",IdMesajAlici);
                            mesajMetniGovdesi.put("mesajID",mesajEklemeId);
                            mesajMetniGovdesi.put("zaman",kaydedilenAktifZaman);
                            mesajMetniGovdesi.put("tarih",kaydedilenAktifTarih);

                            Map mesajGovdesiDetaylari = new HashMap();
                            mesajGovdesiDetaylari.put(mesajGonderenYolu + "/" + mesajEklemeId, mesajMetniGovdesi);
                            mesajGovdesiDetaylari.put(mesajAlanYolu + "/" + mesajEklemeId, mesajMetniGovdesi);
                            mesajYolu.updateChildren(mesajGovdesiDetaylari).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful())
                                    {
                                        yuklemebar.dismiss();
                                        Toast.makeText(ChatActivity.this, "successfully uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                        yuklemebar.dismiss();
                                        Toast.makeText(ChatActivity.this, "Error try again", Toast.LENGTH_SHORT).show();
                                    }
                                    GirilenMesajMetni.setText("");

                                }
                            });
                        }
                    }
                });
            }
            else
            {
                yuklemebar.dismiss();
                Toast.makeText(this, "item not selected!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void SonGorulmayiGoster()
    {

        kullaniciyolu.child(IdMesajAlici).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("kullaniciDurumu").hasChild("durum"))
                {
                    String durum = dataSnapshot.child("kullaniciDurumu").child("durum").getValue().toString();
                    String tarih = dataSnapshot.child("kullaniciDurumu").child("tarih").getValue().toString();
                    String zaman = dataSnapshot.child("kullaniciDurumu").child("zaman").getValue().toString();

                    if(durum.equals("çevrimiçi"))
                    {
                        kullaniciSongorulme.setText("Online");
                    }
                    else if(durum.equals("çevrimdışı"))
                    {
                       kullaniciSongorulme.setText("Last Seen:  "+tarih + " "+zaman);

                    }

                }


                else
                {
                    kullaniciSongorulme.setText("Offline");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        //Veri Tabanından veri çekme

    }


    private void MesajGonder() {
        //mesajı kontrolden alma
        final String mesajMetni = GirilenMesajMetni.getText().toString();
        if(TextUtils.isEmpty(mesajMetni))
        {
            Toast.makeText(this, "Enter message!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String mesajGonderenYolu = "Mesajlar/"+IdmesajGonderen+"/"+IdMesajAlici;
            String mesajAlanYolu = "Mesajlar/"+IdMesajAlici+"/"+IdmesajGonderen;

            DatabaseReference kullaniciMesajAnahtarYolu = mesajYolu.child("Mesajlar").child(IdmesajGonderen).child(IdMesajAlici).push();
            String mesajEklemeId = kullaniciMesajAnahtarYolu.getKey();
            Map mesajMetniGovdesi = new HashMap();
            mesajMetniGovdesi.put("mesaj",mesajMetni);
            mesajMetniGovdesi.put("tur","metin");
            mesajMetniGovdesi.put("kimden",IdmesajGonderen);
            mesajMetniGovdesi.put("kime",IdMesajAlici);
            mesajMetniGovdesi.put("mesajID",mesajEklemeId);
            mesajMetniGovdesi.put("zaman",kaydedilenAktifZaman);
            mesajMetniGovdesi.put("tarih",kaydedilenAktifTarih);



            Map mesajGovdesiDetaylari = new HashMap();
            mesajGovdesiDetaylari.put(mesajGonderenYolu + "/" + mesajEklemeId, mesajMetniGovdesi);
            mesajGovdesiDetaylari.put(mesajAlanYolu + "/" + mesajEklemeId, mesajMetniGovdesi);

            mesajYolu.updateChildren(mesajGovdesiDetaylari).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful())
                    {
                        DatabaseReference veritabani = FirebaseDatabase.getInstance().getReference();
                        veritabani.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                gondereninadi = dataSnapshot.child("Kullanicilar").child(IdmesajGonderen).child("ad").getValue().toString();
                                if(dataSnapshot.child("Kullanicilar").child(IdMesajAlici).hasChild("BildirimID"))
                                {
                                    alici = dataSnapshot.child("Kullanicilar").child(IdMesajAlici).child("BildirimID").getValue().toString();
                                }
                             //   alici = dataSnapshot.child("Kullanicilar").child(IdMesajAlici).child("BildirimID").getValue().toString();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e("databaseError","databaseError");

                            }
                        });

                        DatabaseReference newDatabaseReference = FirebaseDatabase.getInstance().getReference("PlayerIds");
                        newDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds : dataSnapshot.getChildren())
                                {

                                    HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();

                                   final String playerId = hashMap.get("playerID");
                                            if(dataSnapshot.exists())
                                            {

                                                if(playerId.equals(alici))
                                                {

                                                   try {
                                                        OneSignal.postNotification(new JSONObject("{'contents': {'en':'"+gondereninadi+": "+mesajMetni+"'}, 'include_player_ids': ['" + playerId +"'], 'sound': deduction.mp3}"), null);
                                                        break;
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(ChatActivity.this, "Failed please try again", Toast.LENGTH_SHORT).show();
                    }
                    GirilenMesajMetni.setText("");

                }
            });

        }




    }
}
