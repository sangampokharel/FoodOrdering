package com.example.dell_i5.food;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFoodActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private EditText fName,fdesc,fprice;
    private   static final int  GallerReq=1;
    private Button menuBtn;
    Uri uri=null;
    StorageReference storageReference=null;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        imageButton=(ImageButton) findViewById(R.id.foodImageBtn);
        fName=(EditText) findViewById(R.id.foodName);
        fdesc=(EditText) findViewById(R.id.fooddesc);
        fprice=(EditText) findViewById(R.id.foodPrice);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Item").push();
        storageReference= FirebaseStorage.getInstance().getReference("Items/");

    }

    public void ImageBtnClicked(View view) {
        Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GallerReq);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GallerReq && resultCode==RESULT_OK){
            uri=data.getData();
            Log.v("uri", String.valueOf(uri));
            imageButton.setImageURI(uri);
        }
    }

    public void addMenuCicked(View view) {
       final  String name=fName.getText().toString().trim();
       final String desc=fdesc.getText().toString().trim();
        final String price=fprice.getText().toString().trim();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(price)){
            StorageReference filepath=storageReference.child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadUri=taskSnapshot.getDownloadUrl();
                    Toast.makeText(AddFoodActivity.this, "file uploaded sucessfully", Toast.LENGTH_SHORT).show();
                    databaseReference.child("fname").setValue(name);
                    databaseReference.child("desc").setValue(desc);
                    databaseReference.child("price").setValue(price);
                    databaseReference.child("image").setValue(downloadUri.toString());

                }
            });
        }
    }
}
