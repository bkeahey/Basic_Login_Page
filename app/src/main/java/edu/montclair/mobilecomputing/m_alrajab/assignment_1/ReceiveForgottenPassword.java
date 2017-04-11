package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts;
import edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationDbHelper;



import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.PASS_KEY;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils.Utils.setStethoWatch;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_AGE;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_EMAIL;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_MAJOR;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_NAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_PASS;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.StudentEntry.COL_USERNAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationContracts.TABLE_NAME;

public class ReceiveForgottenPassword extends AppCompatActivity {
    private TextView tv;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStethoWatch(this);
        tv = (TextView) findViewById(R.id.tv4);
        setContentView(R.layout.activity_receive_forgotten_password);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new myLstr());

        String incomingMsg = getIntent().getStringExtra(PASS_KEY);

        AlertDialog retrieve = new AlertDialog.Builder(ReceiveForgottenPassword.this).create();
        retrieve.setMessage(incomingMsg);
        retrieve.show();
        //The ReceiveForgottenPassword page starts open with an alertdialog telling the user the password that they were looking for


    }

    class myLstr implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ReceiveForgottenPassword.this,MainActivity.class);
            startActivity(intent);
            //The Return to login button which brings the user back to the login page


        }
    }
}

