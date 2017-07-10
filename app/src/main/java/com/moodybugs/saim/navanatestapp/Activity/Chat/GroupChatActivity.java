package com.moodybugs.saim.navanatestapp.Activity.Chat;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    ImageView btnSend;
    ScrollView scrollAGC;

    DatabaseReference root;

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
        scrollAGC = (ScrollView) findViewById(R.id.scrollAGC);
        scrollAGC.scrollTo(0, scrollAGC.getBottom());

        root = FirebaseDatabase.getInstance().getReference().child(RoomName);

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

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                allChatConversation(dataSnapshot);
                scrollAGC.scrollTo(0, scrollAGC.getBottom());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                allChatConversation(dataSnapshot);
                scrollAGC.scrollTo(0, scrollAGC.getBottom());
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

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutChat);
            TextView txt1 = new TextView(this);
            txt1.setBackgroundResource(R.drawable.chat_left);
            txt1.setPadding(20,20,20,20);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,150,10);
            txt1.setLayoutParams(params);
            linearLayout.addView(txt1);

            SpannableStringBuilder sb = new SpannableStringBuilder(chatUserName);
            StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
            sb.setSpan(bss, 0, chatUserName.length()-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            txt1.setText(sb + "\n" + chatMsg);

            if (chatUserName.toLowerCase().equals(UserName.toLowerCase())){
                txt1.setBackgroundResource(R.drawable.chat_right);
                params.setMargins(150,10,10,10);
                txt1.setGravity(Gravity.END);
                txt1.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }
        }
    }
}
