package com.example.ac_twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendTweet extends AppCompatActivity implements View.OnClickListener {
    private EditText edtSendTweet;
    private Button btnSendTweet;

    private ListView viewTweetsListView;
    private Button btnViewTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        edtSendTweet = findViewById(R.id.edtSendTweet);
        btnSendTweet = findViewById(R.id.btnSendTweet);

        viewTweetsListView = findViewById(R.id.viewTweetsListView);
        btnViewTweets = findViewById(R.id.btnViewTweets);

        btnSendTweet.setOnClickListener(this);
        btnViewTweets.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSendTweet:
                if (edtSendTweet.getText().toString().equals("")){
                    FancyToast.makeText(SendTweet.this, "Message is required", Toast.LENGTH_LONG,
                            FancyToast.INFO,false).show();
                } else {

                    ParseObject parseObject = new ParseObject("MyTweet");
                    parseObject.put("tweet", edtSendTweet.getText().toString());
                    parseObject.put("user", ParseUser.getCurrentUser().getUsername());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                FancyToast.makeText(SendTweet.this, ParseUser.getCurrentUser().getUsername() + "'s tweet" + "(" +
                                        edtSendTweet.getText().toString() + ")" + " is saved!!!", Toast.LENGTH_LONG,
                                        FancyToast.SUCCESS, false).show();
                            } else {
                                FancyToast.makeText(SendTweet.this, e.getMessage(), Toast.LENGTH_LONG,
                                        FancyToast.ERROR, false).show();
                            }
                            progressDialog.cancel();


                        }
                    });



                }
                break;
            case R.id.btnViewTweets:
                final ArrayList<HashMap<String, String>> tweetList = new ArrayList<>();
                final SimpleAdapter adapter = new SimpleAdapter(SendTweet.this, tweetList,android.R.layout.simple_list_item_2 ,
                        new String[]{"tweetUserName", "tweetValue"},new int[]{android.R.id.text1, android.R.id.text2} );
                try {
                    ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("MyTweet");
                    parseQuery.whereContainedIn("user", ParseUser.getCurrentUser().getList("fanOf"));
                    parseQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects.size() > 0 && e == null){
                                for (ParseObject tweetObject : objects) {
                                    HashMap<String, String> userTweet = new HashMap<>();
                                    userTweet.put("tweetUserName", tweetObject.getString("user"));
                                    userTweet.put("tweetValue", tweetObject.getString("tweet"));
                                    tweetList.add(userTweet);
                                }
                                viewTweetsListView.setAdapter(adapter);
                            }
                        }
                    });

                } catch (Exception e){
                    e.printStackTrace();
            }

                break;

        }
    }
}