package com.example.appxone.fiveminuteneurolog;

/**
 * DataBase Management Class
 *
 * @author Nadeem Iqbal
 */

/**
 * @author Nadeem Iqbal
 *
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataBaseManager {


    private static final String TAG = "DBAdapter";

    private final Context context;


    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DataBaseManager(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS \"5minutes\" (\"id\" INTEGER PRIMARY KEY NOT NULL, \"content\" TEXT , \"older_page\" INTEGER , \"page_no\" INTEGER , \"section_id\" INTEGER)";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, AppSetting.DATABASE_NAME, null,
                    AppSetting.DATABASE_VERSION);

            // context.openOrCreateDatabase(AppSettings.DATABASE_NAME, context.MODE_PRIVATE, null);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }
    }

    // ---opens the database---
    public DataBaseManager open() throws SQLException {
        db = DBHelper.getWritableDatabase();

        return this;
    }

    // ---closes the database---
    public void close() {
        DBHelper.close();
    }

    // ---retrieve records---
    public Cursor selectQuery(String query) throws SQLException {
        String myPath = AppSetting.DB_Path + AppSetting.DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
        Cursor mCursor = db.rawQuery(query, null);
        mCursor.moveToFirst();
        db.close();

        return mCursor;
    }

    // //////// For Insert And Update Data ////////
    public void insert_update(String query) throws SQLException {
        String myPath = AppSetting.DB_Path + AppSetting.DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
        db.execSQL(query);
        db.close();
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        if (dbExist) {
            // do nothing - database already exist
        } else {
            CopyFiles();
        }
    }

    private void CopyFiles() {
        try {
            InputStream is = context.getAssets()
                    .open(AppSetting.DATABASE_NAME);
            File outfile = new File(AppSetting.DB_Path,
                    AppSetting.DATABASE_NAME);
            outfile.getParentFile().mkdirs();
            outfile.createNewFile();

            if (is == null)
                throw new RuntimeException("stream is null");
            else {
                FileOutputStream out = new FileOutputStream(outfile);
                byte buf[] = new byte[128];
                do {
                    int numread = is.read(buf);
                    if (numread <= 0)
                        break;
                    out.write(buf, 0, numread);
                } while (true);

                is.close();
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {


        File dbFile = this.context.getDatabasePath(AppSetting.DATABASE_NAME);
        return dbFile.exists();
    }

}
