package ke.co.nathanaccess;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import ke.co.nathanaccess.ui.search.SearchActivity;

public class SessionManager {
    // Session length set to a week (number of milliseconds in a week)
    private static final long SESSION_LENGTH = 604800000;

    // Time session expires
    private long expiryTime;

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context mContext;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    private static final String EMAIL = "email";

    // Email address (make variable public to access from outside)
    private static final String EXPIRY = "expiryTime";

    // Constructor
    public SessionManager(Context context){
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String email){
        // Store email login and set to logged in
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(EMAIL, email);

        // Store sessions expiry time
        expiryTime = SESSION_LENGTH + System.currentTimeMillis();
        editor.putLong(EXPIRY, expiryTime);

        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, SearchActivity.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        } else if (System.currentTimeMillis() > expiryTime){
            // Set stored variables to defaults
            editor.putBoolean(IS_LOGIN, false);
            editor.putString(EMAIL, "");
        } else {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, SearchActivity.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        }

    }



    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user email id
        user.put(EMAIL, pref.getString(EMAIL, null));

        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(mContext, SearchActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }

    /**
     * Check if a user already logged on
     */
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}