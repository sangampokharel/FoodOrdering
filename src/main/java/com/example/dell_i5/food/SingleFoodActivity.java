package com.example.dell_i5.food;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleFoodActivity extends AppCompatActivity {

    private String food_key=null;
    private DatabaseReference databaseReference,UserData;
    private DatabaseReference mref;
    private FirebaseAuth auth;
    private ImageView singleImage;
    private TextView Singlename,Singledesc,Singleprice;
    private FirebaseUser current_user;
    private Button orderItem;
    private String food_name,food_price,food_desc,food_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        food_key=getIntent().getExtras().getString("foodId");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Item");
        auth=FirebaseAuth.getInstance();
        current_user=auth.getCurrentUser();
        UserData=FirebaseDatabase.getInstance().getReference().child("users").child(current_user.getUid());
        mref=FirebaseDatabase.getInstance().getReference().child("orders");
        singleImage=(ImageView) findViewById(R.id.singleFoodImage);
        Singlename=(TextView) findViewById(R.id.singleFoodName);
        Singledesc=(TextView) findViewById(R.id.singleFoodDesc);
        Singleprice=(TextView) findViewById(R.id.singleFoodPrice);
        orderItem=(Button) findViewById(R.id.orderBtn);

        databaseReference.child(food_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 food_name=(String) dataSnapshot.child("fname").getValue();
                 food_price=(String) dataSnapshot.child("price").getValue();
                 food_desc=(String) dataSnapshot.child("desc").getValue();
                 food_image=(String) dataSnapshot.child("image").getValue();

                Singledesc.setText(food_desc);
                Singlename.setText(food_name);
                Singleprice.setText(food_price);
                Picasso.with(SingleFoodActivity.this).load(food_image).into(singleImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        orderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference newOrder=mref.push();
                UserData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Toast.makeText(SingleFoodActivity.this, "Ordered Item Sucessfully", Toast.LENGTH_SHORT).show();
                            newOrder.child("itemname").setValue(food_name);
                            newOrder.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(SingleFoodActivity.this,MenuItemActivity.class));
                                }
                            });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
