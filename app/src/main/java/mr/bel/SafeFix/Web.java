package mr.bel.SafeFix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;

public class Web extends AppCompatActivity implements View.OnClickListener{
    StringBuilder builder,builder2;
    WebView webview;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webview = findViewById(R.id.web);
        button = findViewById(R.id.button15);
        button.setOnClickListener(this);
        try {
            SafeFixDatabaseHelper SafefixDatabaseHelper = new SafeFixDatabaseHelper(this);
            SQLiteDatabase db = SafefixDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("MONEY",
                    new String[]{"CATEGORY", "DATA", "MONEY"},
                    null, null,
                    null, null, null);
            builder = new StringBuilder();
            builder2 = new StringBuilder();
            builder.append("<html><body><h1>Данные</h1><table>");
            while (cursor.moveToNext()) {
                builder.append("<tr><td>");
                String Data = cursor.getString(cursor.getColumnIndex("DATA"));
                builder.append(Data).append("  ");
                builder2.append(Data).append("  ").append("\n");
                builder.append("</td><td>");
                String Categ = cursor.getString(cursor.getColumnIndex("CATEGORY"));
                builder.append(Categ).append("  ");
                builder.append("</td><td>");
                builder2.append(Categ).append("  ").append("\n");
                String Money = cursor.getString(cursor.getColumnIndex("MONEY"));
                builder.append(Money).append(" ");
                builder.append("</td></tr>");
                builder2.append(Money).append(" ").append("\n");
            }
            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        webview.loadData(builder.toString(), "text/html", "UTF-8");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, (Serializable) builder2);
        startActivity(intent);
    }
}