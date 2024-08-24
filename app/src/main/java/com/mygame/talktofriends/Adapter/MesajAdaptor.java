package com.mygame.talktofriends.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygame.talktofriends.ChatActivity;
import com.mygame.talktofriends.MainActivity;
import com.mygame.talktofriends.Model.Mesajlar;
import com.mygame.talktofriends.R;
import com.mygame.talktofriends.ResimGostermeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesajAdaptor extends RecyclerView.Adapter<MesajAdaptor.MesajlarViewHolder> {
    private List<Mesajlar> kullanicimesajlarListesi;
    //firebase
    private FirebaseAuth mYetki;
    private DatabaseReference kullanicilarYolu;



    public MesajAdaptor (List<Mesajlar> kullanicimesajlarListesi)
    {
        this.kullanicimesajlarListesi = kullanicimesajlarListesi;


    }
    //View Holder
    public class MesajlarViewHolder extends RecyclerView.ViewHolder
    {
        //özel mesajdaki kontroller
        public CircleImageView aliciProfilResmi;
        public TextView gondrenMesajMetni,aliciMesajMetni;
        public ImageView mesajgondrenResim,mesajalanResim;

        public MesajlarViewHolder(@NonNull View itemView) {
            super(itemView);
            aliciMesajMetni=itemView.findViewById(R.id.alici_mesaj_metni);
            gondrenMesajMetni=itemView.findViewById(R.id.gonderen_mesaj_metni);
            aliciProfilResmi=itemView.findViewById(R.id.mesaj_profil_resmi);
            mesajgondrenResim=itemView.findViewById(R.id.mesaj_gonderen_image_view);
            mesajalanResim=itemView.findViewById(R.id.mesaj_alan_image_view);
        }
    }

    @NonNull
    @Override
    public MesajlarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ozel_mesajlar_layout,parent,false);
        //firebaseTanımlama
        mYetki=FirebaseAuth.getInstance();
        return new MesajlarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MesajlarViewHolder holder, final int position) {
        String mesajGonderenId = mYetki.getCurrentUser().getUid();

        //model tanımlama
        Mesajlar mesajlar = kullanicimesajlarListesi.get(position);
        final String kimdenKullaniciId = mesajlar.getKimden();
        String kimdenmesajturu = mesajlar.getTur();

        //Veri tabanı yolu
        kullanicilarYolu = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(kimdenKullaniciId);

        //FireBaseden veri çekme
        kullanicilarYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("resim"))
                {
                    // Veritabanındaki resmi aktarma
                    String resmiAlici = dataSnapshot.child("resim").getValue().toString();

                    //kontrolle resmi aktarma
                    Picasso.get().load(resmiAlici).placeholder(R.drawable.ic_person1).into(holder.aliciProfilResmi);
                }

                else
                {


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Görünmez Yapma
        holder.aliciMesajMetni.setVisibility(View.GONE);
        holder.aliciProfilResmi.setVisibility(View.GONE);
        holder.gondrenMesajMetni.setVisibility(View.GONE);
        holder.mesajalanResim.setVisibility(View.GONE);
        holder.mesajgondrenResim.setVisibility(View.GONE);


        if(kimdenmesajturu.equals("metin"))
        {

            if(kimdenKullaniciId.equals(mesajGonderenId))
            {
                holder.aliciProfilResmi.setVisibility(View.INVISIBLE);
                holder.gondrenMesajMetni.setVisibility(View.VISIBLE);
                holder.gondrenMesajMetni.setBackgroundResource(R.drawable.gonderen_mesajlar_layout);
                holder.gondrenMesajMetni.setTextColor(Color.BLACK);
                holder.gondrenMesajMetni.setText(mesajlar.getZaman()+" "+mesajlar.getTarih()+"\n\n"+mesajlar.getMesaj());


            }
            else
            {

                //görünür yapma
                holder.aliciProfilResmi.setVisibility(View.VISIBLE);
                holder.aliciMesajMetni.setVisibility(View.VISIBLE);
                holder.aliciMesajMetni.setBackgroundResource(R.drawable.alici_mesajlari_layout);
                holder.aliciMesajMetni.setTextColor(Color.BLACK);
                holder.aliciMesajMetni.setText(mesajlar.getZaman()+" "+mesajlar.getTarih()+"\n\n"+mesajlar.getMesaj());

            }
        }
        else if (kimdenmesajturu.equals("resim"))
        {
            if(kimdenKullaniciId.equals(mesajGonderenId))
            {
                holder.mesajgondrenResim.setVisibility(View.VISIBLE);
                Picasso.get().load(mesajlar.getMesaj()).into(holder.mesajgondrenResim);
            }
            else
            {
                holder.aliciProfilResmi.setVisibility(View.VISIBLE);

                holder.mesajalanResim.setVisibility(View.VISIBLE);
                Picasso.get().load(mesajlar.getMesaj()).into(holder.mesajalanResim);

            }
        }
        else if(kimdenmesajturu.equals("pdf")||kimdenmesajturu.equals("docx"))
        {
            if(kimdenKullaniciId.equals(mesajGonderenId))
            {
                holder.mesajgondrenResim.setVisibility(View.VISIBLE);
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/talktofriends-923c2.appspot.com/o/Resim%20Dosyalar%C4%B1%2Fdocument.png?alt=media&token=f5cdb221-9959-4ae5-95ee-6a51dfb05041")
                        .into(holder.mesajgondrenResim);



            }
            else
            {
                holder.aliciProfilResmi.setVisibility(View.VISIBLE);
                holder.mesajalanResim.setVisibility(View.VISIBLE);
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/talktofriends-923c2.appspot.com/o/Resim%20Dosyalar%C4%B1%2Fdocument.png?alt=media&token=f5cdb221-9959-4ae5-95ee-6a51dfb05041")
                        .into(holder.mesajalanResim);



            }
        }
        if(kimdenKullaniciId.equals(mesajGonderenId))
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(kullanicimesajlarListesi.get(position).getTur().equals("pdf")||kullanicimesajlarListesi.get(position).getTur().equals("docx"))
                    {
                        CharSequence secenekler [] = new CharSequence[]
                                {
                                        "Benden Sil",
                                        "Bu belgeyi indir ve görüntüle",
                                        "İptal",
                                        "Herkesten sil"

                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Mesaj Silinsin mi?");

                        builder.setItems(secenekler, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0)
                                {
                                    gonderilenmesajiSil(position,holder);
                                    Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);

                                    holder.itemView.getContext().startActivity(intent);

                                }
                                if(i == 1)
                                {

                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(kullanicimesajlarListesi.get(position).getMesaj()));
                                            holder.itemView.getContext().startActivity(intent);
                                }
                                if(i == 2)
                                {
                                    //iptal butonu

                                }
                                if(i == 3)
                                {
                                    mesajiHerkestenSil(position,holder);
                                    Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);

                                    holder.itemView.getContext().startActivity(intent);

                                }
                            }
                        });
                        builder.show();
                    }
                           else if(kullanicimesajlarListesi.get(position).getTur().equals("metin"))
                            {
                                CharSequence secenekler [] = new CharSequence[]
                                        {
                                                "Benden Sil",


                                                "Herkesten sil"

                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                                builder.setTitle("Mesaj Silinsin mi?");

                                builder.setItems(secenekler, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(i == 0)
                                        {
                                            gonderilenmesajiSil(position,holder);
                                            Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);

                                            holder.itemView.getContext().startActivity(intent);

                                        }


                                       else if(i == 1)
                                        {
                                            mesajiHerkestenSil(position,holder);
                                            Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);

                                            holder.itemView.getContext().startActivity(intent);

                                        }

                                    }
                                });
                                builder.show();
                            }
                    else if(kullanicimesajlarListesi.get(position).getTur().equals("resim"))
                    {
                        CharSequence secenekler [] = new CharSequence[]
                                {
                                        "Benden Sil",
                                        "Bu Resmi Görüntüle",
                                        "İPTAL",
                                        "Herkesten sil"

                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Mesaj Silinsin mi?");

                        builder.setItems(secenekler, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0)
                                {
                                    gonderilenmesajiSil(position,holder);
                                    Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);

                                    holder.itemView.getContext().startActivity(intent);

                                }
                                else if(i == 1)
                                {
                                    Intent intent = new Intent(holder.itemView.getContext(), ResimGostermeActivity.class);
                                    intent.putExtra("url",kullanicimesajlarListesi.get(position).getMesaj());
                                    holder.itemView.getContext().startActivity(intent);


                                }


                                else if(i == 3)
                                {
                                    mesajiHerkestenSil(position,holder);
                                    Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);

                                    holder.itemView.getContext().startActivity(intent);

                                }

                            }
                        });
                        builder.show();
                    }
                }
            });
        }
        else
        {
            //Alıcı kısmı
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(kullanicimesajlarListesi.get(position).getTur().equals("pdf")||kullanicimesajlarListesi.get(position).getTur().equals("docx"))
                    {
                        CharSequence secenekler [] = new CharSequence[]
                                {
                                        "Benden Sil",
                                        "Bu belgeyi indir ve görüntüle",
                                        "İptal"


                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Mesaj Silinsin mi?");

                        builder.setItems(secenekler, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0)
                                {
                                    alinanmesajiSil(position,holder);
                                    Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);

                                    holder.itemView.getContext().startActivity(intent);

                                }
                                if(i == 1)
                                {

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(kullanicimesajlarListesi.get(position).getMesaj()));
                                    holder.itemView.getContext().startActivity(intent);
                                }
                                if(i == 2)
                                {

                                }


                            }
                        });
                        builder.show();
                    }
                    else if(kullanicimesajlarListesi.get(position).getTur().equals("metin"))
                    {
                        CharSequence secenekler [] = new CharSequence[]
                                {
                                        "Benden Sil",
                                        "İptal"

                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Mesaj Silinsin mi?");

                        builder.setItems(secenekler, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0)
                                {
                                    alinanmesajiSil(position,holder);
                                    Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);

                                    holder.itemView.getContext().startActivity(intent);

                                }




                            }
                        });
                        builder.show();
                    }
                    else if(kullanicimesajlarListesi.get(position).getTur().equals("resim"))
                    {
                        CharSequence secenekler [] = new CharSequence[]
                                {
                                        "Benden Sil",
                                        "Bu Resmi Görüntüle",
                                        "İptal"



                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Mesaj Silinsin mi?");

                        builder.setItems(secenekler, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0)
                                {
                                    alinanmesajiSil(position,holder);
                                    Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);

                                    holder.itemView.getContext().startActivity(intent);

                                }
                                if(i == 1)
                                {
                                    Intent intent = new Intent(holder.itemView.getContext(), ResimGostermeActivity.class);
                                    intent.putExtra("url",kullanicimesajlarListesi.get(position).getMesaj());
                                    holder.itemView.getContext().startActivity(intent);

                                }



                            }
                        });
                        builder.show();
                    }
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return kullanicimesajlarListesi.size();
    }

    private void gonderilenmesajiSil(final int pozisyon, final MesajlarViewHolder holder )
    {
     final DatabaseReference mesajyolu = FirebaseDatabase.getInstance().getReference();
     mesajyolu.child("Mesajlar")
             .child(kullanicimesajlarListesi.get(pozisyon).getKimden())
             .child(kullanicimesajlarListesi.get(pozisyon).getKime())
             .child(kullanicimesajlarListesi.get(pozisyon).getMesajID())
             .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful())
             {
                 mesajyolu.child("Mesajlar")
                         .child(kullanicimesajlarListesi.get(pozisyon).getKimden())
                         .child(kullanicimesajlarListesi.get(pozisyon).getKime())
                         .child(kullanicimesajlarListesi.get(pozisyon).getMesajID())
                         .removeValue();
                 Toast.makeText(holder.itemView.getContext(), "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();

             }
             else
             {
                 Toast.makeText(holder.itemView.getContext(), "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();

             }

             
         }
     });
    }
    private void alinanmesajiSil(final int pozisyon, final MesajlarViewHolder holder )
    {
        DatabaseReference mesajyolu = FirebaseDatabase.getInstance().getReference();
        mesajyolu.child("Mesajlar")
                .child(kullanicimesajlarListesi.get(pozisyon).getKime())
                .child(kullanicimesajlarListesi.get(pozisyon).getKimden())
                .child(kullanicimesajlarListesi.get(pozisyon).getMesajID())
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(holder.itemView.getContext(), "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(holder.itemView.getContext(), "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
    private void mesajiHerkestenSil(final int pozisyon, final MesajlarViewHolder holder )
    {
        final DatabaseReference mesajyolu = FirebaseDatabase.getInstance().getReference();
        mesajyolu.child("Mesajlar")
                .child(kullanicimesajlarListesi.get(pozisyon).getKime())
                .child(kullanicimesajlarListesi.get(pozisyon).getKimden())
                .child(kullanicimesajlarListesi.get(pozisyon).getMesajID())
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    mesajyolu.child("Mesajlar")
                            .child(kullanicimesajlarListesi.get(pozisyon).getKimden())
                            .child(kullanicimesajlarListesi.get(pozisyon).getKime())
                            .child(kullanicimesajlarListesi.get(pozisyon).getMesajID())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(holder.itemView.getContext(), "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();
                        }
                        }
                    });


                }
                else
                {
                    Toast.makeText(holder.itemView.getContext(), "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

}
