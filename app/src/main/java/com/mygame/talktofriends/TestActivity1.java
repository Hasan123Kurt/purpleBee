package com.mygame.talktofriends;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class TestActivity1 extends AppCompatActivity {
    Button answer1,answer2,answer3,answer4,next;
    TextView skor,soru1,soru_gosterimi;
    private Questions mQuestions = new Questions();
    private Questions2 mQuestions2 = new Questions2();
    private Question3 mQuestion3 = new Question3();
    private Questions4 mQuestions4 = new Questions4();
    private Questions5 mQuestions5 = new Questions5();
    private Questions6 mQuestions6 = new Questions6();
    private Questions7 mQuestions7 = new Questions7();
    private Questions8 mQuestions8 = new Questions8();
    private Questions9 mQuestions9 = new Questions9();
    private QuestionsRekabet mQuestionsRekabet = new QuestionsRekabet();
    private String mAnswer;
    int soru_goster=1;
    DatabaseReference kullaniciyolu;
    FirebaseAuth mYetki;
    String sYetki;


    private int mScore =0;
    int i=0;
    private String alici;
    Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        kullaniciyolu= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
        mYetki=FirebaseAuth.getInstance();
        sYetki=mYetki.getCurrentUser().getUid();
        r=new Random();
        answer1=findViewById(R.id.answer1);
        answer2=findViewById(R.id.answer2);
        answer3=findViewById(R.id.answer3);
        answer4=findViewById(R.id.answer4);
        skor=findViewById(R.id.skor);
        soru1=findViewById(R.id.soru1);
        next=findViewById(R.id.arka_plan);
        soru_gosterimi=findViewById(R.id.soru_gosterimi);

        answer1.setBackgroundColor(Color.WHITE);
        answer2.setBackgroundColor(Color.WHITE);
        answer3.setBackgroundColor(Color.WHITE);
        answer4.setBackgroundColor(Color.WHITE);

    Intent iin =getIntent();
    Bundle bundle =iin.getExtras();
    if(bundle!=null)
    {
        alici = (String)bundle.get("button");
    }

        if(alici.equals("test1"))
        {
            updateQuestion(i);
        }
        else if(alici.equals("test2"))
        {
            updateQuestion2(i);

        }
        else if(alici.equals("test3"))
        {
            updateQuestion3(i);
        }
        else if(alici.equals("test4"))
        {
            updateQuestion4(i);
        }
        else if(alici.equals("test5"))
        {
            updateQuestion5(i);
        }
        else if(alici.equals("test6"))
        {
            updateQuestion6(i);
        }
        else if(alici.equals("test7"))
        {
            updateQuestion7(i);
        }
        else if(alici.equals("test8"))
        {
            updateQuestion8(i);
        }
        else if(alici.equals("test9"))
        {
            updateQuestion9(i);
        }
        else if(alici.equals("rekabet"))
        {
            updateQuestionRekabet(i);
        }
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer1.getText() == mAnswer)
                {
                    mScore=mScore+5;
                    skor.setText("Score: "+mScore);
                    answer1.setBackgroundColor(Color.GREEN);

                }
                else {
                    answer1.setBackgroundColor(Color.RED);
                }
                answer1.setEnabled(false);
                answer2.setEnabled(false);
                answer3.setEnabled(false);
                answer4.setEnabled(false);
            }
        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer2.getText() == mAnswer)
                {
                    mScore=mScore+5;
                    skor.setText("Scor: "+mScore);
                    answer2.setBackgroundColor(Color.GREEN);
                }
                else {
                    answer2.setBackgroundColor(Color.RED);

                }
                answer1.setEnabled(false);
                answer2.setEnabled(false);
                answer3.setEnabled(false);
                answer4.setEnabled(false);
            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer3.getText() == mAnswer)
                {
                    mScore=mScore+5;
                    skor.setText("Scor: "+mScore);
                    answer3.setBackgroundColor(Color.GREEN);

                }
                else {
                    answer3.setBackgroundColor(Color.RED);

                }
                answer1.setEnabled(false);
                answer2.setEnabled(false);
                answer3.setEnabled(false);
                answer4.setEnabled(false);

            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer4.getText() == mAnswer)
                {
                    mScore=mScore+5;
                    skor.setText("Scor: "+mScore);
                    answer4.setBackgroundColor(Color.GREEN);
                }
                else {
                    answer4.setBackgroundColor(Color.RED);

                }
                answer1.setEnabled(false);
                answer2.setEnabled(false);
                answer3.setEnabled(false);
                answer4.setEnabled(false);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soru_goster++;
                soru_gosterimi.setText(soru_goster+"/20");
                answer1.setBackgroundColor(Color.WHITE);
                answer2.setBackgroundColor(Color.WHITE);
                answer3.setBackgroundColor(Color.WHITE);
                answer4.setBackgroundColor(Color.WHITE);
                i++;
                if(i<20)
                {
                    answer1.setEnabled(true);
                    answer2.setEnabled(true);
                    answer3.setEnabled(true);
                    answer4.setEnabled(true);

                    if(alici.equals("test1"))
                    {
                        updateQuestion(i);
                    }
                    else if(alici.equals("test2"))
                    {
                        updateQuestion2(i);

                    }
                    else if(alici.equals("test3"))
                    {
                        updateQuestion3(i);
                    }
                    else if(alici.equals("test4"))
                    {
                        updateQuestion4(i);
                    }
                    else if(alici.equals("test5"))
                    {
                        updateQuestion5(i);
                    }
                    else if(alici.equals("test6"))
                    {
                        updateQuestion6(i);
                    }
                    else if(alici.equals("test7"))
                    {
                        updateQuestion7(i);
                    }
                    else if(alici.equals("test8"))
                    {
                        updateQuestion8(i);
                    }
                    else if(alici.equals("test9"))
                    {
                        updateQuestion9(i);
                    }
                    else if(alici.equals("rekabet"))
                    {
                        updateQuestionRekabet(i);
                    }

                }
                else
                {
                    if(alici.equals("rekabet"))
                    {
                        AlertDialog.Builder alert2 = new AlertDialog.Builder(TestActivity1.this);
                        alert2.setTitle("Competitive Test done!");
                        alert2.setMessage("Your score "+mScore + "Do you want to save it?");
                        alert2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String mtr = String.valueOf(mScore);
                                kullaniciyolu.child(sYetki).child("score").setValue(mScore).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(TestActivity1.this, "successfully saved", Toast.LENGTH_SHORT).show();
                                        }
                                        
                                    }
                                });
                                
                                finish();

                            }
                        });
                        alert2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            finish();
                            }
                        });
                        AlertDialog dialog = alert2.create();
                        alert2.show();
                    }
                    else
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(TestActivity1.this);
                        if(alici.equals("test1"))
                        {
                            alert.setTitle("Test 1 done!");
                        }
                        else if(alici.equals("test2"))
                        {
                            alert.setTitle("Test 2 done!");
                        }
                        else if(alici.equals("test3"))
                        {
                            alert.setTitle("Test 3 done!");
                        }
                        else if(alici.equals("test4"))
                        {
                            alert.setTitle("Test 4 done!");
                        }
                        else if(alici.equals("test5"))
                        {
                            alert.setTitle("Test 5 done!");
                        }
                        else if(alici.equals("test6"))
                        {
                            alert.setTitle("Test 6 done!");
                        }
                        else if(alici.equals("test7"))
                        {
                            alert.setTitle("Test 7 done!");
                        }
                        else if(alici.equals("test8"))
                        {
                            alert.setTitle("Test 8 done!");
                        }
                        else if(alici.equals("test9"))
                        {
                            alert.setTitle("Test 9 done!");
                        }

                        alert.setMessage("Your score: "+mScore+"      Do you want to start over?");
                        alert.setPositiveButton("Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent =getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });
                        alert.setNegativeButton("turn back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(TestActivity1.this,QuizActivity.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog dialog = alert.create();
                        alert.show();
                        dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.GREEN));

                    }


                }
            }
        });

    }


    private void updateQuestion(int num){
        soru1.setText(mQuestions.getquestions(num));
        answer1.setText(mQuestions.getChoice(num));
        answer2.setText(mQuestions.getChoice1(num));
        answer3.setText(mQuestions.getChoice2(num));
        answer4.setText(mQuestions.getChoice3(num));

        mAnswer=mQuestions.getcorrectAnswer(num);
    }
    private void updateQuestion2(int num){
        soru1.setText(mQuestions2.getquestions(num));
        answer1.setText(mQuestions2.getChoice(num));
        answer2.setText(mQuestions2.getChoice1(num));
        answer3.setText(mQuestions2.getChoice2(num));
        answer4.setText(mQuestions2.getChoice3(num));

        mAnswer=mQuestions2.getcorrectAnswer(num);
    }
    private void updateQuestion3(int num){
        soru1.setText(mQuestion3.getquestions(num));
        answer1.setText(mQuestion3.getChoice(num));
        answer2.setText(mQuestion3.getChoice1(num));
        answer3.setText(mQuestion3.getChoice2(num));
        answer4.setText(mQuestion3.getChoice3(num));

        mAnswer=mQuestion3.getcorrectAnswer(num);
    }
    private void updateQuestion4(int num){
        soru1.setText(mQuestions4.getquestions(num));
        answer1.setText(mQuestions4.getChoice(num));
        answer2.setText(mQuestions4.getChoice1(num));
        answer3.setText(mQuestions4.getChoice2(num));
        answer4.setText(mQuestions4.getChoice3(num));

        mAnswer=mQuestions4.getcorrectAnswer(num);
    }
    private void updateQuestion5(int num){
        soru1.setText(mQuestions5.getquestions(num));
        answer1.setText(mQuestions5.getChoice(num));
        answer2.setText(mQuestions5.getChoice1(num));
        answer3.setText(mQuestions5.getChoice2(num));
        answer4.setText(mQuestions5.getChoice3(num));

        mAnswer=mQuestions5.getcorrectAnswer(num);
    }
    private void updateQuestion6(int num){
        soru1.setText(mQuestions6.getquestions(num));
        answer1.setText(mQuestions6.getChoice(num));
        answer2.setText(mQuestions6.getChoice1(num));
        answer3.setText(mQuestions6.getChoice2(num));
        answer4.setText(mQuestions6.getChoice3(num));

        mAnswer=mQuestions6.getcorrectAnswer(num);
    }
    private void updateQuestion7(int num){
        soru1.setText(mQuestions7.getquestions(num));
        answer1.setText(mQuestions7.getChoice(num));
        answer2.setText(mQuestions7.getChoice1(num));
        answer3.setText(mQuestions7.getChoice2(num));
        answer4.setText(mQuestions7.getChoice3(num));

        mAnswer=mQuestions7.getcorrectAnswer(num);
    }
    private void updateQuestion8(int num){
        soru1.setText(mQuestions8.getquestions(num));
        answer1.setText(mQuestions8.getChoice(num));
        answer2.setText(mQuestions8.getChoice1(num));
        answer3.setText(mQuestions8.getChoice2(num));
        answer4.setText(mQuestions8.getChoice3(num));

        mAnswer=mQuestions8.getcorrectAnswer(num);
    }
    private void updateQuestion9(int num){
        soru1.setText(mQuestions9.getquestions(num));
        answer1.setText(mQuestions9.getChoice(num));
        answer2.setText(mQuestions9.getChoice1(num));
        answer3.setText(mQuestions9.getChoice2(num));
        answer4.setText(mQuestions9.getChoice3(num));

        mAnswer=mQuestions9.getcorrectAnswer(num);
    }
    private void updateQuestionRekabet(int num){
        soru1.setText(mQuestionsRekabet.getquestions(num));
        answer1.setText(mQuestionsRekabet.getChoice(num));
        answer2.setText(mQuestionsRekabet.getChoice1(num));
        answer3.setText(mQuestionsRekabet.getChoice2(num));
        answer4.setText(mQuestionsRekabet.getChoice3(num));

        mAnswer=mQuestionsRekabet.getcorrectAnswer(num);
    }

}