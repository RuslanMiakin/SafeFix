package mr.bel.SafeFix;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SafeFixDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "safefix";
    private static final int DB_VERSION = 1;

    SafeFixDatabaseHelper (Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE MONEY (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "CATEGORY TEXT, "
                + "IMAGE_RESOURCE_ID INTEGER, " +
                "DATA DATETIME DEFAULT CURRENT_TIMESTAMP, " + "MONEY TEXT);");}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }}
