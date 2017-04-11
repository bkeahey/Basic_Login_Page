package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts;
import edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationDbHelper;


import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.CURRENTUSER;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.PASS_KEY;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.mDbHelper;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.setStethoWatch;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_AGE;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_EMAIL;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_MAJOR;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_NAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_PASS;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_USERNAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private Button btn3;

    private TextView test4;
    private TextView username;
    private TextView password;
    List<String> users = new ArrayList<>();
    List<String> passes = new ArrayList<>();

    FragmentTransaction transaction;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn1 = (Button) findViewById(R.id.btn_register_MA);
        btn1.setOnClickListener(new MyLstr());

        btn2 = (Button) findViewById(R.id.btn_forgetpassword_MA);
        btn2.setOnClickListener(new MyLstr2());


        btn3 = (Button) findViewById(R.id.loginMA);
        btn3.setOnClickListener(new MyLstr3());

        test4 = (TextView) findViewById(R.id.textView);
        mDbHelper = new RegistrationDbHelper(this);

        username = (TextView) findViewById(R.id.editText_name_MA1);
        password = (TextView) findViewById(R.id.editText_name_MA2);


        //adds fragment to the FrameLayout
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.ma_fragment1, new MA_Fragment1());
        transaction.commit();

        //adds fragment if the phone is in landscape
        if(findViewById(R.id.ma_fragment2)!=null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.ma_fragment2, new MA_Fragment2());
            transaction.commit();
        }

        //Connects the Listeners to the buttons

    }

    class MyLstr implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, RegistrationPage.class);
            startActivity(intent);
            //Connects the Register button to the registration page
        }
    }

    class MyLstr2 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //checkRecord();
            Intent intent2 = new Intent(MainActivity.this,ForgetPassword.class);
            startActivity(intent2);
            //Connects the Forgot password button to the ForgetPassword page
        }
    }

    class MyLstr3 implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            boolean correct = checkRecord();
            //checkRecords return boolean is stored into correct
            if(correct)
            {
                //If correct is true, Login class is opened
                Intent intent3 = new Intent(MainActivity.this, Login.class);
                intent3.putExtra(CURRENTUSER,"The user  " + username.getText().toString());
                startActivity(intent3);
            }
            else
            {
                //If correct is false , an alert is activated telling the user that their username or password is incorrect
                AlertDialog invalid = new AlertDialog.Builder(MainActivity.this).create();
                invalid.setMessage("Invalid username or password");
                invalid.show();
            }




            //Intent intent3 = new Intent(MainActivity.this, Login.class);
            //startActivity(intent3);


        }
    }
         public boolean checkRecord() {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            // Define a projection that specifies which columns from the database
// you will actually use after this query.
            String[] projection = {
                    RegistrationContracts.StudentEntry._ID,
                    COL_USERNAME,
                    COL_NAME,
                    COL_EMAIL,
                    COL_PASS,
                    COL_AGE,
                    COL_MAJOR

            };

// Filter results WHERE "title" = 'My Title'
            //String selection = RegistrationContracts.StudentEntry.COL_USERNAME + " = ?";
            //String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
            //String sortOrder =
            //    FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

            Cursor cursor = db.query(
                    TABLE_NAME,                     // The table to query
                    projection,                               // The columns to return
                    null,                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );

            String tempStr = "";
            String tempStr2 = "";


            while (cursor.moveToNext()) {




                tempStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME));
                users.add(tempStr);
                //Adds the Username in the database to a temp string and then adds that string to the users array list

                tempStr2 = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASS));
                passes.add(tempStr2);
                //Adds the password in the database to a temp string and then adds that string to the passes array list


                //tempStr += cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
                //tempStr += cursor.getString(cursor.getColumnIndexOrThrow(COL_PASS));
                //tempStr += cursor.getString(cursor.getColumnIndexOrThrow(COL_AGE));
                //tempStr += cursor.getString(cursor.getColumnIndexOrThrow(COL_MAJOR)) +"\n";


            }
            cursor.close();



            for(int i=0; i <users.size(); i++)
            {


                if((username.getText().toString().equals(users.get(i))) && (password.getText().toString().equals(passes.get(i))))
                {
                    //Loops through the users and passes array lists and checks if any of them match the username and password that the user has entered
                    //Returns true if there is a match
                   return true;

                }
            }
             //Returns false if there is no match
             return false;



        }



    }



