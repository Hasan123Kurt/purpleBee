package com.mygame.talktofriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class KayitActivity extends AppCompatActivity {
    private Button KayitolusturmaButton;
    private EditText KullaniciEmail, Kullanicisifre;
    private TextView ZatenHesabimVar;
    //firebase
    private DatabaseReference kokReference;
    private FirebaseAuth mYetki;

    private ProgressDialog yukleniyorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        //firebase tanımlaması
        mYetki=FirebaseAuth.getInstance();
        kokReference = FirebaseDatabase.getInstance().getReference();

        //kayitoluşturma buttonu
        KayitolusturmaButton=findViewById(R.id.kayit_button);

        KullaniciEmail=findViewById(R.id.kayit_email);
        Kullanicisifre=findViewById(R.id.kayit_sifre1);
        ZatenHesabimVar=findViewById(R.id.var_olanhesap);

        yukleniyorDialog=new ProgressDialog(this);

        ZatenHesabimVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginActivityIntent = new Intent(KayitActivity.this,LoginActivity.class);
                startActivity(loginActivityIntent);
            }
        });
        KayitolusturmaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YeniHesapolustur();
            }

            private void YeniHesapolustur() {
                String email = KullaniciEmail.getText().toString();
                String sifre = Kullanicisifre.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(KayitActivity.this, "Please enter the e-mail", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(sifre))
                {
                    Toast.makeText(KayitActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    yukleniyorDialog.setTitle("Creating a new account");
                    yukleniyorDialog.setMessage("Please Wait");
                    yukleniyorDialog.setCanceledOnTouchOutside(true);
                    yukleniyorDialog.show();

                    mYetki.createUserWithEmailAndPassword(email,sifre)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        //Benzersiz bildirim ID
                                        String cihazToken = FirebaseInstanceId.getInstance().getToken();

                                        String mevcutKullaniciid=mYetki.getCurrentUser().getUid();
                                        kokReference.child("Kullanicilar").child(mevcutKullaniciid).setValue("");

                                        kokReference.child("Kullanicilar").child(mevcutKullaniciid).child("cihaz_token")
                                                .setValue(cihazToken);


                                        Intent anasayfa= new Intent(KayitActivity.this,AyarlarActivity.class);
                                        anasayfa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                        finish();
// sql ---------------------------------------------------------------
                                        try {
                                            SQLiteDatabase sqLiteDatabase = KayitActivity.this.openOrCreateDatabase("Haklar",MODE_PRIVATE,null);
                                            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Haklar (hak INT(2))");
                                            sqLiteDatabase.execSQL("INSERT INTO Haklar (hak) VALUES (2)");


                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(KayitActivity.this, "New Account Successfully Created", Toast.LENGTH_SHORT).show();
                                        startActivity(anasayfa);
                                        yukleniyorDialog.dismiss();
                                    }
                                    else {
                                        String mesaj = task.getException().toString();
                                        Toast.makeText(KayitActivity.this, "Error:      " + mesaj + "Check Your Information", Toast.LENGTH_SHORT).show();
                                        yukleniyorDialog.dismiss();
                                    }
                                }
                            });
                }
            }
        });
    }
}
