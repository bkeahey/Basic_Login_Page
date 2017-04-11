package edu.montclair.mobilecomputing.m_alrajab.assignment_1.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.stetho.BuildConfig;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.montclair.mobilecomputing.m_alrajab.assignment_1.model.RegistrationDbHelper;

/**
 * Created by denis on 3/2/2017.
 */

public class Utils {

    public static final String SHARED_PREF_FILENAME="edu.montclair.mobilecomputing.m_alrajab.assignment_1.SHAREDFILE1";
    public static final String SHARED_PREF_FILENAME2="edu.montclair.mobilecomputing.m_alrajab.assignment_1.SHAREDFILE2";
    public static final String KEY_TITLE="Title_";
    public static final String KEY_BODY="Body_";
    public final static String PASS_KEY = "Pass_key";
    public final static String CURRENTUSER ="Current_user";
    public static List<String> notes = new ArrayList<>();
    public static   RegistrationDbHelper mDbHelper;




    public static String[] getListFromSP(Context context, String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_FILENAME,
                Context.MODE_PRIVATE);
        Map<String, ?> map=sharedPreferences.getAll();
        ArrayList<String> lst= new ArrayList<>();
        for(String str:map.keySet()){
            if(str.startsWith(key))
                lst.add((String)map.get(str));
        }
        return lst.toArray(new String[lst.size()]);
    }

    public static void setStethoWatch(Context c){
        if(BuildConfig.DEBUG){
            Stetho.initialize(
                    Stetho.newInitializerBuilder(c)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(c))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(c))
                            .build()
            );

        }

    }
}
