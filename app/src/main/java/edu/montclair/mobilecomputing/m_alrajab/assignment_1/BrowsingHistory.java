package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.SHARED_PREF_FILENAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.SHARED_PREF_FILENAME2;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.notes;

public class BrowsingHistory extends AppCompatActivity  {
    private TextView textView;
    private Button button;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String note;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsing_history);



        textView=(TextView)findViewById(R.id.tv1);

        sharedPreferences = getSharedPreferences(SHARED_PREF_FILENAME2, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name","");
        textView.append(name);
        for(int i =0; i <notes.size(); i++) {
            note = sharedPreferences.getString(notes.get(i), "");
            textView.append(note + '\n');
        }





    }
    class MyLisn implements View.OnClickListener{
        @Override
        public void onClick(final View view) {




        }
    }




}
