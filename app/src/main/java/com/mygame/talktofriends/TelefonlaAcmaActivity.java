package com.mygame.talktofriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class TelefonlaAcmaActivity extends AppCompatActivity {
    private Button dogrulamaKodugondermebuttonu,dogrulamabuttonu;
    private EditText telefonnumarasiGirdisi,dogrulamakodugirdisi;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String mDogrulamaID;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    //Firebase
    FirebaseAuth mYetki;
    //yükleniyor penceresi
    private ProgressDialog yuklemebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefonla_acma);
        //Tanımlamalar
        dogrulamaKodugondermebuttonu=findViewById(R.id.dogrulama_kodu_gonder_buttonu);
        dogrulamabuttonu=findViewById(R.id.dogrulama_buttonu);

        //Progress dialog tanımlama
        yuklemebar=new ProgressDialog(this);
        //Firebase tanımlama
        mYetki=FirebaseAuth.getInstance();

        telefonnumarasiGirdisi=findViewById(R.id.telefon_numarasi_girdi);
        dogrulamakodugirdisi=findViewById(R.id.dogrulama_kodunu_yazın);
        dogrulamaKodugondermebuttonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String telefonNumarasi= telefonnumarasiGirdisi.getText().toString();
                if(TextUtils.isEmpty(telefonNumarasi))
                {
                    Toast.makeText(TelefonlaAcmaActivity.this, "Enter the telephone number!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Yükleniyor penceresi
                    yuklemebar.setTitle("Phone Verification");
                    yuklemebar.setMessage("Please Wait");
                    yuklemebar.setCanceledOnTouchOutside(false);
                    yuklemebar.show();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            telefonNumarasi,        // Phone number to verify
                            80,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            TelefonlaAcmaActivity.this,               // Activity (for callback binding)
                            callbacks);
                }

            }
        });
        dogrulamabuttonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogrulamaKodugondermebuttonu.setVisibility(View.INVISIBLE);
                telefonnumarasiGirdisi.setVisibility(View.INVISIBLE);
                String dogrulamakodu = dogrulamakodugirdisi.getText().toString();
                if(TextUtils.isEmpty(dogrulamakodu))
                {
                    Toast.makeText(TelefonlaAcmaActivity.this, "Enter verification code!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Yükleniyor penceresi
                    yuklemebar.setTitle("Code Verification");
                    yuklemebar.setMessage("Please Wait");
                    yuklemebar.setCanceledOnTouchOutside(false);
                    yuklemebar.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mDogrulamaID, dogrulamakodu);
                    telefonlagirisyap(credential);
                }

            }
        });
        callbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                telefonlagirisyap(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {
                //Yukleme penceresi
                yuklemebar.dismiss();


                Toast.makeText(TelefonlaAcmaActivity.this, "Invalid telephone number\n" +
                        "Be sure to enter with Country Code!", Toast.LENGTH_LONG).show();
                //Görüntü ayarları
                dogrulamaKodugondermebuttonu.setVisibility(View.VISIBLE);
                dogrulamabuttonu.setVisibility(View.INVISIBLE);
                telefonnumarasiGirdisi.setVisibility(View.VISIBLE);
                dogrulamakodugirdisi.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {


                // Save verification ID and resending token so we can use them later
                mDogrulamaID= verificationId;
                mResendToken = token;

                //yukleme penceresi
                yuklemebar.dismiss();
                
                Toast.makeText(TelefonlaAcmaActivity.this, "Code Sent. Please wait a little", Toast.LENGTH_LONG).show();
                //Görünüm ayarları
                dogrulamaKodugondermebuttonu.setVisibility(View.INVISIBLE);
                dogrulamabuttonu.setVisibility(View.VISIBLE);
                telefonnumarasiGirdisi.setVisibility(View.INVISIBLE);
                dogrulamakodugirdisi.setVisibility(View.VISIBLE);


                // ...
            }
        };
    }

    private void telefonlagirisyap (PhoneAuthCredential credential) {
        mYetki.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            yuklemebar.dismiss();
                            Toast.makeText(TelefonlaAcmaActivity.this, "Congratulations Logged in", Toast.LENGTH_SHORT).show();
                            // sql ------------------------------
                            try {
                                SQLiteDatabase sqLiteDatabase = TelefonlaAcmaActivity.this.openOrCreateDatabase("Haklar",MODE_PRIVATE,null);
                                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Haklar (hak INT(2))");
                                sqLiteDatabase.execSQL("INSERT INTO Haklar (hak) VALUES (2)");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            KullaniciyiAnasayfayaGonder();

                        }
                        else
                            {
                                String hatamesaji = task.getException().toString();
                                Toast.makeText(TelefonlaAcmaActivity.this, "Error:" + hatamesaji , Toast.LENGTH_LONG).show();


                            }
                    }
                });
    }

    private void KullaniciyiAnasayfayaGonder() {
        Intent anasayfa = new Intent(TelefonlaAcmaActivity.this,MainActivity.class);
        anasayfa.putExtra("intro","intro");
        anasayfa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(anasayfa);
        finish();
    }

}
