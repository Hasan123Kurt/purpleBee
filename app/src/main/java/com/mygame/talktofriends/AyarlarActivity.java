package com.mygame.talktofriends;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AyarlarActivity extends AppCompatActivity {
    private Button hesapAyarlariniGuncelle;
    private EditText kullaniciAdi,kullaniciDurum;
    private CircleImageView kullaniciProfilResmi;
    //Firebase
    private FirebaseAuth mYetki;
    private String mevcutKullaniciId;
    private DatabaseReference veriyolu;
    private StorageReference KullaniciProfilresmiyolu;
    private StorageTask yuklemegorevi;
    String sorgu ="1";


    int k_adi = 0;
    private String playerId;


    //resim seçme
    private static final int GaleriSecme = 1;
    //yukleniyor
    private ProgressDialog yukleniyorbar;
    //Uri
    Uri resimUri;
    String myUri = "";

    //toolbar
    private Toolbar ayarlarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        //firebase
        mYetki = FirebaseAuth.getInstance();
        veriyolu=FirebaseDatabase.getInstance().getReference();
        KullaniciProfilresmiyolu= FirebaseStorage.getInstance().getReference().child("Profil Resimleri");

        mevcutKullaniciId=mYetki.getCurrentUser().getUid();

        //kontrol Atamaları
        hesapAyarlariniGuncelle=findViewById(R.id.ayarlama_buttonu);
        kullaniciAdi=findViewById(R.id.Kullanici_adi_ayarla);
        kullaniciProfilResmi=findViewById(R.id.profil_resmi_ayarlar);
        kullaniciDurum=findViewById(R.id.Kullanici_durumu_ayarla);
        ayarlarToolbar = findViewById(R.id.ayarlar_toolbar);
        setSupportActionBar(ayarlarToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile Settings");

        yukleniyorbar=new ProgressDialog(this);

        hesapAyarlariniGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AyarlariGuncelle();
            }
        });
        kullaniciAdi.setVisibility(View.INVISIBLE);
        Kullanicibilgisial();
        kullaniciProfilResmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kırpma akticityi aç
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(AyarlarActivity.this);


            }
        });
    }

    private String dosyaUzantisiAl(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
// resim seçme kodu
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
        {
            //resim seçiliyorsa
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            resimUri = result.getUri();
            kullaniciProfilResmi.setImageURI(resimUri);
        }
        else
        {
            Toast.makeText(this, "no picture selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void Kullanicibilgisial() {
        veriyolu.child("Kullanicilar").child(mevcutKullaniciId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if((dataSnapshot.exists())&& (dataSnapshot.hasChild("ad") && (dataSnapshot.hasChild("resim"))))
                        {
                            String kullaniciAdiAl = dataSnapshot.child("ad").getValue().toString();
                            String kullaniciDurumAl = dataSnapshot.child("durum").getValue().toString();
                            String kullaniciResimal = dataSnapshot.child("resim").getValue().toString();

                            kullaniciAdi.setText(kullaniciAdiAl);
                            kullaniciDurum.setText(kullaniciDurumAl);
                            Picasso.get().load(kullaniciResimal).into(kullaniciProfilResmi);

                        }
                        else if((dataSnapshot.exists())&& (dataSnapshot.hasChild("ad")))
                        {
                            String kullaniciAdiAl = dataSnapshot.child("ad").getValue().toString();
                            String kullaniciDurumAl = dataSnapshot.child("durum").getValue().toString();


                            kullaniciAdi.setText(kullaniciAdiAl);
                            kullaniciDurum.setText(kullaniciDurumAl);
                            k_adi = 0;

                        }
                        else
                        {
                            kullaniciAdi.setVisibility(View.VISIBLE);
                            k_adi = 1;
                            Toast.makeText(AyarlarActivity.this, "Please Set Your Profile Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void AyarlariGuncelle() {
        String kullaniciadiayarla = kullaniciAdi.getText().toString();
        String kullanicidurumuayarla = kullaniciDurum.getText().toString();

        if(TextUtils.isEmpty(kullaniciadiayarla))
        {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show();
        }
       else if(TextUtils.isEmpty(kullanicidurumuayarla))
        {
            Toast.makeText(this, "Please enter your state", Toast.LENGTH_LONG).show();
        }
        else
        {
           final String kullaniciAdiniAl = kullaniciAdi.getText().toString();
           String kullaniciDurumunuAl = kullaniciAdi.getText().toString();



               if(TextUtils.isEmpty(kullaniciAdiniAl))
               {
                   Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
               }
               else if(TextUtils.isEmpty(kullaniciDurumunuAl))
               {
                   Toast.makeText(this, "Please enter your state", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   if(k_adi == 0)
                   {
                       resimyukle();
                       Intent intent = new Intent(AyarlarActivity.this,MainActivity.class);
                       startActivity(intent);
                   }
                   else
                   {
                       resimyukle();
                       Intent intent = new Intent(AyarlarActivity.this,IntroActivity.class);
                       finish();
                       startActivity(intent);
                   }

               }




        }

    }

    private void resimyukle() {
        yukleniyorbar.setTitle("Resim Yükleme");
        yukleniyorbar.setMessage("Lütfen Bekleyin");
        yukleniyorbar.setCanceledOnTouchOutside(false);
        yukleniyorbar.show();

        if(resimUri == null)
        {
            DatabaseReference veriyolu = FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
            String gonderiId = veriyolu.push().getKey();
            String kullaniciAdiAl = kullaniciAdi.getText().toString();
            String kullaniciDurumAl = kullaniciDurum.getText().toString();
            HashMap<String,Object> profilharitasi = new HashMap<>();
            profilharitasi.put("uid",gonderiId);
            profilharitasi.put("ad",kullaniciAdiAl);
            profilharitasi.put("durum",kullaniciDurumAl);


            veriyolu.child(mevcutKullaniciId).updateChildren(profilharitasi);
            yukleniyorbar.dismiss();


        }
        else
        {
            final StorageReference resimyolu = KullaniciProfilresmiyolu.child(mevcutKullaniciId + "." + dosyaUzantisiAl(resimUri));
            yuklemegorevi = resimyolu.putFile(resimUri);
            yuklemegorevi.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return resimyolu.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    // görev tamamlandığında
                    if(task.isSuccessful())
                    {
                        //başarılıysa
                        Uri indirmeUri = task.getResult();
                        myUri=indirmeUri.toString();
                        DatabaseReference veriyolu = FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
                        String gonderiId = veriyolu.push().getKey();
                        String kullaniciAdiAl = kullaniciAdi.getText().toString();
                        String kullaniciDurumAl = kullaniciDurum.getText().toString();

                        HashMap<String,Object> profilharitasi = new HashMap<>();

                        profilharitasi.put("uid",gonderiId);
                        profilharitasi.put("ad",kullaniciAdiAl);
                        profilharitasi.put("durum",kullaniciDurumAl);
                        profilharitasi.put("resim",myUri);

                        veriyolu.child(mevcutKullaniciId).updateChildren(profilharitasi);
                        yukleniyorbar.dismiss();

                    }
                    else
                    {
                        //başarısızsa
                        String hata = task.getException().toString();
                        Toast.makeText(AyarlarActivity.this, "Error: "+hata, Toast.LENGTH_SHORT).show();
                        yukleniyorbar.dismiss();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AyarlarActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    yukleniyorbar.dismiss();
                }
            });


        }

        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();

    }

}
