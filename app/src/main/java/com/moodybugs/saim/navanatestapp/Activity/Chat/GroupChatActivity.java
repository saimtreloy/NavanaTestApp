package com.moodybugs.saim.navanatestapp.Activity.Chat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
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
import android.view.ViewGroup;
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
import com.moodybugs.saim.navanatestapp.Activity.MapsActivity;
import com.moodybugs.saim.navanatestapp.R;
import com.moodybugs.saim.navanatestapp.Utility.SharedPrefDatabase;

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
        scrollAGC.post(new Runnable() {
            public void run() {
                scrollAGC.fullScroll(scrollAGC.FOCUS_DOWN);
            }
        });

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
                    Toast.makeText(getApplicationContext(), new SharedPrefDatabase(getApplicationContext()).RetriveLat() + "\n" +
                            new SharedPrefDatabase(getApplicationContext()).RetriveLon(), Toast.LENGTH_SHORT).show();
                    Map<String, Object> map = new HashMap<>();
                    temp_key = root.push().getKey();
                    root.updateChildren(map);

                    DatabaseReference message_root = root.child(temp_key);
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("name", UserName);
                    map1.put("message", msg);
                    map1.put("lat", new SharedPrefDatabase(getApplicationContext()).RetriveLat());
                    map1.put("lon", new SharedPrefDatabase(getApplicationContext()).RetriveLon());
                    message_root.updateChildren(map1);
                    inputChat.setText("");
                    scrollAGC.post(new Runnable() {
                        public void run() {
                            scrollAGC.fullScroll(scrollAGC.FOCUS_DOWN);
                        }
                    });
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
                scrollAGC.post(new Runnable() {
                    public void run() {
                        scrollAGC.fullScroll(scrollAGC.FOCUS_DOWN);
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                allChatConversation(dataSnapshot);
                scrollAGC.post(new Runnable() {
                    public void run() {
                        scrollAGC.fullScroll(scrollAGC.FOCUS_DOWN);
                    }
                });
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

    String chatMsg, chatUserName, lat, lon, latAndLong;
    @SuppressLint("NewApi")
    private void allChatConversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        int j = 1000;
        while (i.hasNext()){
            lat = (String) ((DataSnapshot)i.next()).getValue();
            lon = (String) ((DataSnapshot)i.next()).getValue();
            latAndLong = lat + "," + lon;
            chatMsg = (String) ((DataSnapshot)i.next()).getValue();
            chatUserName = (String) ((DataSnapshot)i.next()).getValue();


            if (chatMsg.contains("firebasestorage.googleapis.com")){
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutChat);

                LinearLayout child = new LinearLayout(getApplicationContext());
                child.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10,10,150,10);
                child.setLayoutParams(layoutParams);
                linearLayout.addView(child);

                TextView txtUser = new TextView(this);
                txtUser.setBackgroundResource(R.drawable.chat_right);
                txtUser.setPadding(20, 20, 20, 20);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params1.setMargins(10, 10, 10, 10);
                txtUser.setLayoutParams(params1);

                final ImageView img = new ImageView(this);
                img.setBackgroundResource(R.drawable.rounded);
                img.setPadding(20,20,20,20);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,400);
                img.setLayoutParams(params);

                child.addView(txtUser);
                child.addView(img);

                String s = chatUserName.substring(0,1).toUpperCase();
                txtUser.setText(s);

                Glide.with(getApplicationContext()).load(chatMsg).listener(new RequestListener<String, GlideDrawable>() {
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
                        .into(img);
                img.setTag(chatMsg);
                img.setContentDescription(latAndLong);

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ActivityImageFullscreen.class);
                        intent.putExtra("FULLSCREEN_IMAGE", img.getTag().toString());
                        Log.d("IMAGE TAG", img.getTag().toString());
                        startActivity(intent);
                    }
                });
                if (chatUserName.equals(UserName)){
                    child.setGravity(Gravity.END);
                    layoutParams.setMargins(150, 10, 10, 10);
                    child.setLayoutParams(layoutParams);

                    child.removeView(txtUser);
                    txtUser.setBackgroundResource(R.drawable.chat_left);
                    child.addView(txtUser);
                }

                img.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        String f=img.getContentDescription().toString();
                        double lat = Double.parseDouble(f.substring(0, f.indexOf(",")));
                        double lon = Double.parseDouble(f.substring(f.indexOf(",")+1)) ;
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra("Lat", lat);
                        intent.putExtra("Lon", lon);
                        intent.putExtra("Name", "Location When Text You");
                        startActivity(intent);
                        return false;
                    }
                });
            }else {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutChat);

                LinearLayout child = new LinearLayout(getApplicationContext());
                child.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                child.setLayoutParams(layoutParams);
                linearLayout.addView(child);

                final TextView txtMsg = new TextView(this);
                txtMsg.setBackgroundResource(R.drawable.chat_left);
                txtMsg.setPadding(20, 20, 20, 20);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 150, 10);
                txtMsg.setLayoutParams(params);

                TextView txtUser = new TextView(this);
                txtUser.setBackgroundResource(R.drawable.chat_right);
                txtUser.setPadding(20, 20, 20, 20);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params1.setMargins(10, 10, 10, 10);
                txtUser.setLayoutParams(params1);

                child.addView(txtUser);
                child.addView(txtMsg);

                txtMsg.setText(chatMsg);
                txtMsg.setContentDescription(latAndLong);
                String s = chatUserName.substring(0,1).toUpperCase();
                txtUser.setText(s);

                if (chatUserName.toLowerCase().equals(UserName.toLowerCase())) {
                    child.setGravity(Gravity.END);
                    txtMsg.setBackgroundResource(R.drawable.chat_right);
                    params.setMargins(150, 10, 10, 10);
                    txtMsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    child.removeView(txtUser);
                    txtUser.setBackgroundResource(R.drawable.chat_left);
                    child.addView(txtUser);
                }

                txtMsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String f=txtMsg.getContentDescription().toString();
                        double lat = Double.parseDouble(f.substring(0, f.indexOf(",")));
                        double lon = Double.parseDouble(f.substring(f.indexOf(",")+1)) ;
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra("Lat", lat);
                        intent.putExtra("Lon", lon);
                        intent.putExtra("Name", "Location When Text You");
                        startActivity(intent);
                    }
                });
            }
        }
        scrollAGC.post(new Runnable() {
            public void run() {
                scrollAGC.fullScroll(scrollAGC.FOCUS_DOWN);
            }
        });
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
                    map1.put("lat", new SharedPrefDatabase(getApplicationContext()).RetriveLat());
                    map1.put("lon", new SharedPrefDatabase(getApplicationContext()).RetriveLon());
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
