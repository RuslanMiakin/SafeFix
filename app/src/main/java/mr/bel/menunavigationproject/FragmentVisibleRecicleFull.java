package mr.bel.menunavigationproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentVisibleRecicleFull extends Fragment{
    String date;
    SQLiteOpenHelper SafefixDatabaseHelper;
    SQLiteDatabase db;Cursor cursor;
    ArrayList<String> CategoryArray; ArrayList<Integer> ImageArray;
    ArrayList<String> Money; ArrayList<String> DATA; ArrayList<Integer> idArray;
    ArrayList<Float> MoneyLong;
    Spinner spinner;View view;Bundle bundle;String select;
    DatePickerDialog datePickerDialog;
    Button calend;TextView summ;
    int yearn; int dayn; int mountn;
    CaptionedImagesAdapter adapter;
    RecyclerView recycler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recicler_full, container, false);
        calend = view.findViewById(R.id.button_calen);
        summ = view.findViewById(R.id.text_add_sum);
        spinner = view.findViewById(R.id.spinner);
        bundle=getArguments();

        assert bundle != null;
        select = bundle.getString("cat");
        assert select != null;
        if (select.equals("no")){
            new VisualSort1().execute();
        }else{
         new VisualSortBarChart().execute();
        }

        calend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.button_calen) {
                    VisualCalk();
                }
            }});

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        new VisualSortSpiner().execute("DATA DESC");
                        summ();
                        break;
                    case 2:
                        new VisualSortSpiner().execute("CATEGORY");
                        summ();
                        break;
                    case 3:
                        new VisualSortSpiner().execute("CAST(MONEY AS INTEGER) DESC");
                        summ();
                        break; }}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void summ(){
        float ss = 0;
        for(float s: MoneyLong){
            ss = s +ss;
        }
        summ.setText(""+ss);
    }

    @SuppressLint("StaticFieldLeak")
    private class VisualSortSpiner extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... start) {
            try {
                SafefixDatabaseHelper = new SafeFixDatabaseHelper(view.getContext());
                db = SafefixDatabaseHelper.getReadableDatabase();
                cursor = db.query ("MONEY",
                        new String[] {"_id","IMAGE_RESOURCE_ID","CATEGORY","DATA","MONEY"},
                        null,null,
                        null,null,start[0]);
                CategoryArray = new ArrayList<>();
                ImageArray = new ArrayList<>();
                Money = new ArrayList<>();
                DATA = new ArrayList<>();
                idArray = new ArrayList<>();
                MoneyLong =new ArrayList<>();
                while (cursor.moveToNext()) {
                    String cursorCategory = cursor.getString(cursor.getColumnIndex("CATEGORY"));
                    int cursorImage  = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));
                    int cursorId  = cursor.getInt(cursor.getColumnIndex("_id"));
                    String cursorMoney = cursor.getString(cursor.getColumnIndex("MONEY"));
                    String cursorDATA  = cursor.getString(cursor.getColumnIndex("DATA"));
                    float money = cursor.getFloat(cursor.getColumnIndex("MONEY"));
                    idArray.add(cursorId);
                    CategoryArray.add(cursorCategory);
                    ImageArray.add(cursorImage);
                    Money.add(cursorMoney);
                    DATA.add(cursorDATA);
                    MoneyLong.add(money);
                } cursor.close();
                db.close();

            } catch(SQLiteException e) {
                Toast toast = Toast.makeText(view.getContext(), "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            return true;
        }
        protected void onPostExecute(Boolean result) {
            adapter.setData(Money, ImageArray,CategoryArray,DATA,idArray);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class VisualSortBarChart extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... start) {

            try {
                SafefixDatabaseHelper = new SafeFixDatabaseHelper(view.getContext());
                db = SafefixDatabaseHelper.getReadableDatabase();
                cursor = db.query ("MONEY",
                        new String[] {"_id","IMAGE_RESOURCE_ID","CATEGORY","DATA","MONEY"},
                        "CATEGORY =?",new String[]{select},
                        null,null,null);
                CategoryArray = new ArrayList<>();
                ImageArray = new ArrayList<>();
                Money = new ArrayList<>();
                DATA = new ArrayList<>();
                idArray = new ArrayList<>();
                MoneyLong = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String cursorCategory = cursor.getString(cursor.getColumnIndex("CATEGORY"));
                    int cursorImage  = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));
                    String cursorMoney = cursor.getString(cursor.getColumnIndex("MONEY"));
                    String cursorDATA  = cursor.getString(cursor.getColumnIndex("DATA"));
                    int cursorId  = cursor.getInt(cursor.getColumnIndex("_id"));
                    float money = cursor.getFloat(cursor.getColumnIndex("MONEY"));
                    idArray.add(cursorId);
                    CategoryArray.add(cursorCategory);
                    ImageArray.add(cursorImage);
                    Money.add(cursorMoney);
                    DATA.add(cursorDATA);
                    MoneyLong.add(money);
                } cursor.close();
                db.close();

            } catch(SQLiteException e) {
                Toast toast = Toast.makeText(view.getContext(), "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            return true; }
        protected void onPostExecute(Boolean result) {
            summ();
            visualRecicler();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class VisualSort1 extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... start) {

            try {
                SafefixDatabaseHelper = new SafeFixDatabaseHelper(view.getContext());
                db = SafefixDatabaseHelper.getReadableDatabase();
                cursor = db.query ("MONEY",
                        new String[] {"_id","IMAGE_RESOURCE_ID","CATEGORY","DATA","MONEY"},
                        null,null,
                        null,null,"DATA DESC");
                CategoryArray = new ArrayList<>();
                ImageArray = new ArrayList<>();
                Money = new ArrayList<>();
                DATA = new ArrayList<>();
                idArray = new ArrayList<>();
                MoneyLong = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String cursorCategory = cursor.getString(cursor.getColumnIndex("CATEGORY"));
                    int cursorImage  = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));
                    String cursorMoney = cursor.getString(cursor.getColumnIndex("MONEY"));
                    String cursorDATA  = cursor.getString(cursor.getColumnIndex("DATA"));
                    int cursorId  = cursor.getInt(cursor.getColumnIndex("_id"));
                    float money = cursor.getFloat(cursor.getColumnIndex("MONEY"));
                    idArray.add(cursorId);
                    CategoryArray.add(cursorCategory);
                    ImageArray.add(cursorImage);
                    Money.add(cursorMoney);
                    DATA.add(cursorDATA);
                    MoneyLong.add(money);
                } cursor.close();
                db.close();

            } catch(SQLiteException e) {
                Toast toast = Toast.makeText(view.getContext(), "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            return true; }
        protected void onPostExecute(Boolean result) {
            summ();
            visualRecicler();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class VisualSortCalk extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... start) {
            date = (yearn + "-" + (mountn + 1) + "-" + 0 + dayn);
            try {
                SafefixDatabaseHelper = new SafeFixDatabaseHelper(getContext());
                db = SafefixDatabaseHelper.getReadableDatabase();
                cursor = db.query("MONEY", new String[]{"_id", "IMAGE_RESOURCE_ID", "CATEGORY", "DATA", "MONEY"},
                        "DATA BETWEEN ? AND ?", new String[]{
                                date + " 00:00:00", date + " 23:59:59"}, null, null, "MONEY DESC", null);
                CategoryArray = new ArrayList<>();
                ImageArray = new ArrayList<>();
                Money = new ArrayList<>();
                DATA = new ArrayList<>();
                idArray = new ArrayList<>();
                MoneyLong = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String cursorCategory = cursor.getString(cursor.getColumnIndex("CATEGORY"));
                    int cursorImage = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));
                    String cursorMoney = cursor.getString(cursor.getColumnIndex("MONEY"));
                    String cursorDATA = cursor.getString(cursor.getColumnIndex("DATA"));
                    int cursorId = cursor.getInt(cursor.getColumnIndex("_id"));
                    float money = cursor.getFloat(cursor.getColumnIndex("MONEY"));
                    idArray.add(cursorId);
                    CategoryArray.add(cursorCategory);
                    ImageArray.add(cursorImage);
                    Money.add(cursorMoney);
                    DATA.add(cursorDATA);
                    MoneyLong.add(money);
                }
                cursor.close();
                db.close();
        }catch( SQLiteException e){} return true;
    }
        protected void onPostExecute(Boolean result) {
            adapter.setData(Money, ImageArray,CategoryArray,DATA,idArray);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
        }
    }

        public void VisualCalk(){

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(view.getContext(),AlertDialog.THEME_HOLO_DARK,
                    new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    yearn = year; mountn = month; dayn = day;
                    new VisualSortCalk().execute();
                    }}, year, month, dayOfMonth);
                    datePickerDialog.show();}

        private void visualRecicler(){
            recycler = view.findViewById(R.id.recycler_body);
            adapter = new CaptionedImagesAdapter(view.getContext(),Money, ImageArray,CategoryArray,DATA,idArray);
            recycler.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
            itemTouchHelper.attachToRecyclerView(recycler);}}