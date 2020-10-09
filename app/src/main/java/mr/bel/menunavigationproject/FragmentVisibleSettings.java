package mr.bel.menunavigationproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentVisibleSettings extends Fragment implements View.OnClickListener{
    Button button, button2;
    View view;
    CheckBox checkBox1,checkBox2,checkBox3;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        checkBox1 = view.findViewById(R.id.checkBox);
        checkBox2 = view.findViewById(R.id.checkBox2);
        checkBox3 = view.findViewById(R.id.checkBox3);
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        TextView textView = view.findViewById(R.id.textView4);
        textView.setText("SafeFix"+ " "+ versionName + " " + versionCode);
        button = view.findViewById(R.id.button_delete);
        button2 = view.findViewById(R.id.button_delete2);
        button2.setOnClickListener(this);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_delete:
                if (checkBox1.isChecked() && checkBox3.isChecked()) {
                    try {
                        SQLiteOpenHelper SafefixDatabaseHelper = new SafeFixDatabaseHelper(view.getContext());
                        SQLiteDatabase db = SafefixDatabaseHelper.getReadableDatabase();
                        db.delete("MONEY", null, null);
                        db.close();
                    } catch (SQLiteException e) {
                        Toast toast = Toast.makeText(view.getContext(), "Database Error", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                    Toast toast = Toast.makeText(view.getContext(), R.string.delite_ok, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(view.getContext(), R.string.or_send, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            case R.id.button_delete2:
                Intent intent = new Intent(view.getContext(),Web.class);
                startActivity(intent);
        }}}