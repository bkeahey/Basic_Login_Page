package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.CURRENTUSER;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.PASS_KEY;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.SHARED_PREF_FILENAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.SHARED_PREF_FILENAME2;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.notes;

public class Login extends AppCompatActivity   implements TitlesFragment.OnFragmentInteractionListener{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;
    FragmentTransaction transaction;
    String message;




    @BindView(R.id.btn_addanote_ma) Button btn;
    @BindView(R.id.textview_ma1) TextView tv;
    @BindView(R.id.browser)Button browse;



    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences=getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_APPEND);
        editor=sharedPreferences.edit();

        sharedPreferences2 = getSharedPreferences(SHARED_PREF_FILENAME2, Context.MODE_APPEND);
        editor2=sharedPreferences2.edit();
        Stetho.initializeWithDefaults(this);
        ButterKnife.bind(this);
        String incomingMsg = getIntent().getStringExtra(CURRENTUSER);
        message = incomingMsg;



        btn.setOnClickListener(new Lstnr());
        browse.setOnClickListener(new listr());

        transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,new TitlesFragment());
        transaction.commit();

        if(findViewById(R.id.fragment_container_details)!=null){
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container_details,new DetailsFragment());
            transaction.commit();
        }


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());


        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        editor2.putString("Name",incomingMsg + " logged on at " + mydate + "\n\n");
        editor2.apply();


    }


    class Lstnr implements View.OnClickListener {
        @Override
        public void onClick(final View view) {

            View viewGrp = getLayoutInflater().inflate(R.layout.costum_dialog_layout,
                    (ViewGroup) findViewById(R.id.activity_main), false);

            final EditText noteTitle = (EditText) viewGrp.findViewById(R.id.dialog_title_et);
            final EditText noteBody = (EditText) viewGrp.findViewById(R.id.dialog_body_et);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Login.this)
                    .setTitle("Take a note").setView(viewGrp)

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tv.append(noteTitle.getText()+ "  ");

                            //This saves the note
                            try{
                                FileOutputStream outputStream = openFileOutput(noteTitle.getText().toString().replace(" ",""), MODE_APPEND);
                                outputStream.write(noteBody.getText().toString().getBytes());
                                outputStream.close();
                                Snackbar.make(view,"File Saved", Snackbar.LENGTH_INDEFINITE).show();
                            }catch(Exception e){

                                Log.e("ERROR",e.getMessage() );
                            }
                            //This loads the note
                            String tempStr = "";
                            String lstOfFile_Str = "";
                            ArrayList<String> lstOfNotes = new ArrayList<>();

                            File filesDir = getFilesDir();
                            long x = filesDir.getFreeSpace()/1_000_000;//this in bytes converted to MB
                            File[] filesList = filesDir.listFiles();

                            for(File fl:filesList) {
                                lstOfFile_Str += fl.getName() + "\n";
                                lstOfNotes.add(fl.getName());


                            }
                            try{
                                FileInputStream inputStream = openFileInput(noteTitle.getText().toString().replace(" ",""));
                                int c;
                                while((c=inputStream.read())!=-1){
                                    tempStr+=Character.toString((char)c);

                                }


                                inputStream.close();

                            }catch(Exception e){

                                Log.e("ERROR",e.getMessage() );
                            }
                            tv.append(tempStr +"\n");

                            tv.append(" Free space available is : " + x +" MB" +"\n");
                            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                            editor2.putString(noteTitle.getText().toString(),message +  " added a note titled " + noteTitle.getText().toString() +" at " + mydate+ '\n');
                            notes.add(noteTitle.getText().toString());
                            editor2.apply();



                        }

                    });
            alertBuilder.show();
        }
    }
    class listr implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Login.this,BrowsingHistory.class);

            startActivity(intent);

        }
    }

    @Override
    public void onFragmentInteraction(String uri) {
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,new TitlesFragment());
        transaction.commit();
        if(findViewById(R.id.fragment_container_details)!=null){
            transaction=getSupportFragmentManager().beginTransaction();
            DetailsFragment df=new DetailsFragment();
            Bundle b=new Bundle();  b.putString("KEY",uri);
            df.setArguments(b);
            transaction.add(R.id.fragment_container_details,df);
            transaction.commit();
        }else{
            Intent i=new Intent(Login.this,MainActivity.class);
            i.putExtra("MSG",uri);
            startActivity(i);
        }
    }
}
