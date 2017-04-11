package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothClass;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Dialog;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts;
import edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationDbHelper;



import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.mDbHelper;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.setStethoWatch;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_USERNAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_NAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_EMAIL;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_AGE;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_MAJOR;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_PASS;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.TABLE_NAME;

public class RegistrationPage extends AppCompatActivity {
    private Button btn1;
    private TextView name;
    private TextView username;
    private TextView pass;
    private TextView pass2;
    private TextView emailCheck;
    private TextView major;
    private static  TextView DOB;
    private TextView tv;
    private List<String> users = new ArrayList<>();

    FragmentTransaction transaction;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        setStethoWatch(this);
        btn1=(Button)findViewById(R.id.btn_Reg);
        btn1.setOnClickListener(new MyLstnr());
        username=(EditText)findViewById(R.id.Reg_UserName);
        name=(EditText)findViewById(R.id.Reg_SName);
        pass=(EditText)findViewById(R.id.Reg_pass1);
        pass2=(EditText)findViewById(R.id.Reg_pass2);
        emailCheck=(EditText)findViewById(R.id.Reg_Email);
        DOB=(EditText)findViewById(R.id.DOB);
        major=(EditText)findViewById(R.id.Reg_SMajor);
        DOB.setOnClickListener(new DOBLstnr());


        mDbHelper = new RegistrationDbHelper(this);
        //Connects the Listener to the button


        //adds fragment to FrameLayout
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.reg_fragment1, new Reg_Fragment1());
        transaction.commit();

        //adds fragment if the phone is in landscape
        if(findViewById(R.id.reg_fragment2)!=null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.reg_fragment2, new Reg_Fragment2());
            transaction.commit();
        }


    }
    class MyLstnr implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            boolean alreadyExists = checkRecord();
            //Returns true if the username is already registered in the database
            //Returns false if the username is not already taken

            int check = pass.length();
            if(alreadyExists)
            {
                AlertDialog exists = new AlertDialog.Builder(RegistrationPage.this).create();
                exists.setMessage("This Username is already taken");
                exists.show();
                //Checks if the Username is already taken or not

            }
            else if (check < 6 && !pass.getText().toString().contains("[0-9]+") && !pass.getText().toString().contains("[a-zA-Z]+"))
            {
                AlertDialog passWord = new AlertDialog.Builder(RegistrationPage.this).create();
                passWord.setMessage("The password needs to be at least 6 characters and contain numbers and characters");
                passWord.show();
                //Checks if the password length is at least 6 characters

            }
            else if (!pass.getText().toString().contentEquals(pass2.getText().toString())) {
                AlertDialog passWord = new AlertDialog.Builder(RegistrationPage.this).create();
                passWord.setMessage("The passwords don't match");
                passWord.show();
                //Checks if the confirm password is the same as the password entered
            }
            else if (!emailCheck.getText().toString().contains("montclair.edu")) {
                AlertDialog emailWord = new AlertDialog.Builder(RegistrationPage.this).create();
                emailWord.setMessage("The email does not contain montclair.edu");
                emailWord.show();

                //Checks if the email contains montclair.edu
            }
            //checks if username field is empty
            else if(username.getText().toString().isEmpty()){
                AlertDialog error = new AlertDialog.Builder(RegistrationPage.this).create();
                error.setMessage("The username field is empty");
                error.show();
            }
            //checks if name field is empty
            else if(name.getText().toString().isEmpty()){
                AlertDialog error = new AlertDialog.Builder(RegistrationPage.this).create();
                error.setMessage("The name field is empty");
                error.show();
            }
            //checks if date of birth field is empty
            else if(DOB.getText().toString().isEmpty()){
                AlertDialog error = new AlertDialog.Builder(RegistrationPage.this).create();
                error.setMessage("The date of birth field is empty");
                error.show();
            }
                //checks if major field is empty
            else if(major.getText().toString().isEmpty()){
                AlertDialog error = new AlertDialog.Builder(RegistrationPage.this).create();
                error.setMessage("The major field is empty");
                error.show();
            }
            //checks if email field is empty
            else if(emailCheck.getText().toString().isEmpty()){
                AlertDialog error = new AlertDialog.Builder(RegistrationPage.this).create();
                error.setMessage("The email field is empty");
                error.show();
            }
            //checks if password field is empty
            else if(pass.getText().toString().isEmpty()){
                AlertDialog error = new AlertDialog.Builder(RegistrationPage.this).create();
                error.setMessage("The password field is empty");
                error.show();
            }
                //checks if confirm password field is empty
            else if(pass2.getText().toString().isEmpty()){
                AlertDialog error = new AlertDialog.Builder(RegistrationPage.this).create();
                error.setMessage("The confirm password field is empty");
                error.show();
            }
            else
            {
                addRecord();
                //Adds a record if none of the above errors occur
            }

        }




    }
    class DOBLstnr implements View.OnClickListener{
        @Override
        public void onClick(View v) {


            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
            //Creates new Dialog Fragment

        }
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            month = month +1;
            DOB.setText(String.valueOf(month) + "/" + String.valueOf(day)+ "/"  +String.valueOf(year));
        }
    }

        public void addRecord()
        {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME,username.getText().toString());
        values.put(COL_NAME, name.getText().toString());
        values.put(COL_EMAIL,emailCheck.getText().toString());
        values.put(COL_PASS,pass.getText().toString());
        values.put(COL_AGE, DOB.getText().toString());
        values.put(COL_MAJOR, major.getText().toString());


// Insert the new row, returning the primary key value of the new row
    long newRowId = db.insert(TABLE_NAME, null, values);
            Intent intent2 = new Intent(RegistrationPage.this,MainActivity.class);
            startActivity(intent2);

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



        while (cursor.moveToNext()) {



            tempStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME));
            users.add(tempStr);
            //The username is stored in a temp string and then is added to the users array list





        }
        cursor.close();

        for(int i=0; i <users.size(); i++)
        {


            if((username.getText().toString().equals(users.get(i))))
            {
                //Loops through the users array list and see if there is a match between the username trying to be registered and if there is a username that already exists with the same name
                return true;
                //Returns true if there is a match

            }
        }
        return false;
        //Returns false if there is no match


    }
    }








