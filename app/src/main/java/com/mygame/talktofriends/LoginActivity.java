package com.mygame.talktofriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {
    private Button girisButton, telefonGirisButton;
    private EditText KullaniciMail, Kullanicisifre;
    private TextView YenihesapAlma;




    //Firebase
    private FirebaseAuth mYetki;
    ProgressDialog girisDialog;
    private DatabaseReference kullaniciYolu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //kontrol tanımlamalari
        girisButton = findViewById(R.id.giris_button);
        telefonGirisButton= findViewById(R.id.telefonla_giris);
        KullaniciMail=findViewById(R.id.giris_email);
        Kullanicisifre=findViewById(R.id.giris_password);

        YenihesapAlma=findViewById(R.id.yeni_hesapalma);

        //progressdialog
        girisDialog= new ProgressDialog(this);

        //Firebase
        mYetki=FirebaseAuth.getInstance();
        kullaniciYolu= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");


        YenihesapAlma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kayitaktivityIntent = new Intent(LoginActivity.this,KayitActivity.class);
                startActivity(kayitaktivityIntent);
            }
        });
        girisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KullaniciyaGirisizniver();
            }

        });

        telefonGirisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telefonlaOturum = new Intent(LoginActivity.this,TelefonlaAcmaActivity.class);
                startActivity(telefonlaOturum);
            }
        });

    }

    private void KullaniciyaGirisizniver() {
        String email = KullaniciMail.getText().toString();
        String sifre = Kullanicisifre.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter e-mail", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(sifre))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //progress
            girisDialog.setTitle("Logging in");
            girisDialog.setMessage("Please Wait");
            girisDialog.setCanceledOnTouchOutside(true);
            girisDialog.show();

            //giris
            mYetki.signInWithEmailAndPassword(email,sifre)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                String aktifKullaniciId = mYetki.getCurrentUser().getUid();
                                // Benzeriz bir ID bildrim için
                                String cihatToken = FirebaseInstanceId.getInstance().getToken();
                                //Tokken numarasını kullanıcı bilgilerine ekleme
                                kullaniciYolu.child(aktifKullaniciId).child("cihaz_token").setValue(cihatToken)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Intent anasayfa = new Intent(LoginActivity.this,MainActivity.class);
                                                    anasayfa.putExtra("intro","intro");
                                                    anasayfa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(anasayfa);
                                                    finish();
                                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                                    girisDialog.dismiss();

                                                }

                                            }
                                        });


                            }
                            else
                            {
                                String mesaj = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error:  "+ mesaj +"Check Your Information", Toast.LENGTH_LONG).show();
                                girisDialog.dismiss();
                            }
                        }
                    });
        }
    }



    private void KullaniciyiAnaAktivitiyeGonder() {
        Intent AnaAktiviteIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(AnaAktiviteIntent);

    }
}
