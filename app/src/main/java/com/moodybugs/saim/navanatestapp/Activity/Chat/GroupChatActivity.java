package com.moodybugs.saim.navanatestapp.Activity.Chat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moodybugs.saim.navanatestapp.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GroupChatActivity extends AppCompatActivity {

    public static Toolbar toolbar;

    String UserName, RoomName;
    String temp_key;

    TextView txtConversation;
    EditText inputChat;
    ImageView btnSend, imgUploadImage;
    ScrollView scrollAGC;

    DatabaseReference root;
    StorageReference mStorage;

    public final int REQUEST_CODE_GALLERY = 10001;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        UserName = getIntent().getExtras().getString("USER_NAME");
        RoomName = getIntent().getExtras().getString("ROOM_NAME");

        init();
    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Room : " + RoomName);

        txtConversation = (TextView) findViewById(R.id.txtConversation);
        inputChat = (EditText) findViewById(R.id.inputChat);
        btnSend = (ImageView) findViewById(R.id.btnSend);
        imgUploadImage = (ImageView) findViewById(R.id.imgUploadImage);
        scrollAGC = (ScrollView) findViewById(R.id.scrollAGC);
        scrollAGC.fullScroll(ScrollView.FOCUS_DOWN);

        progressDialog = new ProgressDialog(this);

        root = FirebaseDatabase.getInstance().getReference().child(RoomName);
        mStorage = FirebaseStorage.getInstance().getReference();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = inputChat.getText().toString();
                if (msg.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Chat message empty!", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, Object> map = new HashMap<>();
                    temp_key = root.push().getKey();
                    root.updateChildren(map);

                    DatabaseReference message_root = root.child(temp_key);
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("name", UserName);
                    map1.put("message", msg);
                    message_root.updateChildren(map1);
                    inputChat.setText("");
                    scrollAGC.scrollTo(0, scrollAGC.getBottom());
                }
            }
        });

        imgUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);

            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                allChatConversation(dataSnapshot);
                scrollAGC.fullScroll(ScrollView.FOCUS_DOWN);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                allChatConversation(dataSnapshot);
                scrollAGC.fullScroll(ScrollView.FOCUS_DOWN);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    String chatMsg, chatUserName;
    @SuppressLint("NewApi")
    private void allChatConversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        int j = 1000;
        while (i.hasNext()){
            chatMsg = (String) ((DataSnapshot)i.next()).getValue();
            chatUserName = (String) ((DataSnapshot)i.next()).getValue();

            if (chatMsg.contains("firebasestorage.googleapis.com")){
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutChat);
                final ImageView txt1 = new ImageView(this);
                txt1.setBackgroundResource(R.drawable.rounded);
                txt1.setPadding(20,20,20,20);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,400);
                params.setMargins(10,10,10,10);
                txt1.setLayoutParams(params);
                linearLayout.addView(txt1);
                Glide.with(getApplicationContext())
                        .load(chatMsg)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                progressDialog.dismiss();
                                return false;
                            }
                        })
                        .into(txt1);
                txt1.setTag(chatMsg);

                txt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ActivityImageFullscreen.class);
                        intent.putExtra("FULLSCREEN_IMAGE", txt1.getTag().toString());
                        Log.d("IMAGE TAG", txt1.getTag().toString());
                        startActivity(intent);
                    }
                });
                if (chatUserName.equals(UserName)){
                    params.gravity = Gravity.END;
                }
            }else {

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutChat);
                TextView txt1 = new TextView(this);
                txt1.setBackgroundResource(R.drawable.chat_left);
                txt1.setPadding(20, 20, 20, 20);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 150, 10);
                txt1.setLayoutParams(params);
                linearLayout.addView(txt1);

                SpannableStringBuilder sb = new SpannableStringBuilder(chatUserName);
                StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
                sb.setSpan(bss, 0, chatUserName.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                txt1.setText(sb + "\n" + chatMsg);

                if (chatUserName.toLowerCase().equals(UserName.toLowerCase())) {
                    txt1.setBackgroundResource(R.drawable.chat_right);
                    params.setMargins(150, 10, 10, 10);
                    txt1.setGravity(Gravity.END);
                    txt1.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                }
            }
        }
        scrollAGC.fullScroll(ScrollView.FOCUS_DOWN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){

            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please wait image uploading and featching.");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            Uri uri = data.getData();
            StorageReference filePath = mStorage.child(RoomName).child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUri = taskSnapshot.getDownloadUrl().toString();

                    Map<String, Object> map = new HashMap<>();
                    temp_key = root.push().getKey();
                    root.updateChildren(map);
                    DatabaseReference message_root = root.child(temp_key);
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("name", UserName);
                    map1.put("message", downloadUri);
                    message_root.updateChildren(map1);

                    Log.d("DOWNLOAD URI", downloadUri);
                    Toast.makeText(getApplicationContext(), "Image uploaded successfully.", Toast.LENGTH_SHORT ).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Image uploaded Failed.", Toast.LENGTH_SHORT ).show();
                }
            });
        }


    }
}
