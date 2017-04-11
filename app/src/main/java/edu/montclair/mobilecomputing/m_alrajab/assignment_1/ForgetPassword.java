package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts;
import edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationDbHelper;


import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.PASS_KEY;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.mDbHelper;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_AGE;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_EMAIL;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_MAJOR;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_NAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_PASS;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_USERNAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.TABLE_NAME;

public class ForgetPassword extends AppCompatActivity {
private Button btn1;
    private TextView name;
    private TextView email;
    private String tempPass;
    List<String> emailsList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    List<String> passesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        btn1 = (Button)findViewById(R.id.ForgetButton);
        btn1.setOnClickListener(new Mylistr());

        name = (TextView)findViewById(R.id.ForgetuserName);
        email = (TextView)findViewById(R.id.ForgetEmail);
        mDbHelper = new RegistrationDbHelper(this);

        //Creates forgot password page
    }
    class Mylistr implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            boolean check = checkRecord();
            //The boolean result for checkRecord is stored in check
            if(check)
            {
                Intent intent1 = new Intent(ForgetPassword.this, ReceiveForgottenPassword.class );
                intent1.putExtra(PASS_KEY,"The password for the user " + name.getText().toString() + " is  " + tempPass);
                startActivity(intent1);
                //If check comes back true then the tempPass is stored and is moved to the ReceiveForgottenPassword class


            }
            else
            {
                AlertDialog invalid = new AlertDialog.Builder(ForgetPassword.this).create();
                invalid.setMessage("The Username or Email does not exist");
                invalid.show();
                //If check comes back false then a error dialog appears saying the Username or email does not exist
            }


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
        String tempStr1= "";
        String tempStr2= "";


        while (cursor.moveToNext()) {



            tempStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME));
            nameList.add(tempStr);
            //The username in the database is stored in the temp string and is then added to the nameList string array

            tempStr1 = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
            emailsList.add(tempStr1);
            //The email in the database is stored in the temp string and is then added to the emailList string array

            tempStr2 = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASS));
            passesList.add(tempStr2);
            //The password in the database is stored in the temp string and is then added to the passesList string array


        }
        cursor.close();



        for(int i=0; i <nameList.size(); i++)
        {


            if((name.getText().toString().equals(nameList.get(i))) && (email.getText().toString().equals(emailsList.get(i))))
            {
                //Loops through the nameList and emailsList array and checks if there is a match to what the user has entered in the username and email fields
               tempPass = passesList.get(i);
                //tempPass receives the password the user is seeking for only if the username and emails match
                return true;



            }


        }

        return false;
        //Returns false if there is no match found for the username and email





    }
}
