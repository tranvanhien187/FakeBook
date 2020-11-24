package com.example.fakebook.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fakebook.MainActivity;
import com.example.fakebook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends AppCompatActivity {
    private Toolbar newPostToolbar;
    private ImageView image;
    private EditText edtText;
    private Button btnPost;
    private Uri mainImageURI=null;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String cur_user_id=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        newPostToolbar=(Toolbar)findViewById(R.id.new_post_toolbar);
        firebaseAuth=FirebaseAuth.getInstance();
        Toast.makeText(this,firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            Log.d("debug","null");
        }
        cur_user_id=firebaseAuth.getCurrentUser().getUid();
        setSupportActionBar(newPostToolbar);
        getSupportActionBar().setTitle("Post Status");
        image=findViewById(R.id.image_post);
        edtText=findViewById(R.id.edt_text);
        btnPost=findViewById(R.id.btn_post);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewPostActivity.this, "Choose image", Toast.LENGTH_SHORT).show();
                CropImage();

            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTmp=firebaseAuth.getCurrentUser().getEmail();
                final String email=emailTmp.substring(0,emailTmp.length()-"@gmail.com".length());
                Toast.makeText(NewPostActivity.this, "email : "+email, Toast.LENGTH_SHORT).show();
                final String text=edtText.getText().toString();
                if(!TextUtils.isEmpty(text)&&mainImageURI!=null){
                    final String randomName= FieldValue.serverTimestamp().toString();
                    Log.d("name",randomName);
                    StorageReference filePath=storageReference.child("post_images").child(randomName+".jpg");
                    filePath.putFile(mainImageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d("link","downloadURL : "+uri.toString());
                                    Toast.makeText(NewPostActivity.this, "uploaded", Toast.LENGTH_SHORT).show();
                                    Map<String,Object> postMap=new HashMap<>();
                                    postMap.put("userId",email);
                                    postMap.put("imageUrl",uri.toString());
                                    postMap.put("text",text);
                                    postMap.put("timestamp",FieldValue.serverTimestamp());
                                    firebaseFirestore.collection("Users").document(email)
                                            .collection("Posts").add(postMap)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(NewPostActivity.this, "added", Toast.LENGTH_SHORT).show();
                                                    Intent mainIntent=new Intent(NewPostActivity.this,MainActivity.class);
                                                    startActivity(mainIntent);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(NewPostActivity.this, "error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });

                        }
                    });

                }
            }
        });
    }
    void CropImage(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(512,512)
                .start(NewPostActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                mainImageURI=result.getUri();
                image.setImageURI(mainImageURI);
            }
            else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error=result.getError();

            }
        }
    }
}