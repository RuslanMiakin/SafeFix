package mr.bel.menunavigationproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class FragmentVisible1 extends Fragment implements View.OnClickListener {

View view;
Button button0,button00,button1,button2,button3,button4,button5,
        button6,button7,button8,button9,button_ok,button_delite;
TextView textView454;
String getedit;
BarChart chart;
SQLiteOpenHelper SafefixDatabaseHelper;
SQLiteDatabase db;
ArrayList<BarEntry> entries;
ArrayList<String> labels;
BarData data;
BarDataSet dataset;
ArrayList<String> MoneyRecic;
ArrayList<Integer> ImageRecic;
ArrayList<Integer> idArray;
ArrayList<String> CategoryRecic;
ArrayList<String> DATERecic;
ArrayList<Float> ListMoney;
String[] CategoryAlert;
TextView summaRash;
int IntFav = 0;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_visible1, container, false);

            entries = new ArrayList<>();
            labels = new ArrayList<>();
            MoneyRecic = new ArrayList<>();
            ImageRecic = new ArrayList<>();
            CategoryRecic = new ArrayList<>();
            DATERecic = new ArrayList<>();
            ListMoney =new ArrayList<>();
            idArray = new ArrayList<>();
            new VisualTask().execute();
            button1 = view.findViewById(R.id.button4);
            button1.setOnClickListener(this);
            button2 =  view.findViewById(R.id.button5);
            button2.setOnClickListener(this);
            button3 =  view.findViewById(R.id.button6);
            button3.setOnClickListener(this);
            button4 = view.findViewById(R.id.button7);
            button4.setOnClickListener(this);
            button5= view.findViewById(R.id.button8);
            button5.setOnClickListener(this);
            button6 =  view.findViewById(R.id.button9);
            button6.setOnClickListener(this);
            button7 =  view.findViewById(R.id.button10);
            button7.setOnClickListener(this);
            button8 =  view.findViewById(R.id.button11);
            button8.setOnClickListener(this);
            button9 =  view.findViewById(R.id.button12);
            button9.setOnClickListener(this);
            button0 =  view.findViewById(R.id.button13);
            button0.setOnClickListener(this);
            button00 =  view.findViewById(R.id.button14);
            button00.setOnClickListener(this);
            button_ok =  view.findViewById(R.id.button3);
            button_ok.setOnClickListener(this);
            button_delite=  view.findViewById(R.id.button2);
            button_delite.setOnClickListener(this);
            textView454 = view.findViewById(R.id.textView3);
            textView454.append("0");
            getedit = textView454.getText().toString();

            return view; }

        // Метод записи данных в SQLITE    Фоновый поток
    private static void insertDrink(SQLiteDatabase db, String Category, int resourceId, String Money) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date());
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("CATEGORY", Category);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        drinkValues.put("DATA", strDate);
        drinkValues.put("MONEY", Money);
        db.insert("MONEY", null, drinkValues);
    }

    @SuppressLint("StaticFieldLeak")
    private class VisualTask extends AsyncTask<Boolean, Void, Boolean>{

    @SuppressLint("SetTextI18n")
    protected Boolean doInBackground(Boolean... start) {
        summaRash = view.findViewById(R.id.textView);
        entries.clear();labels.clear();
        ListMoney.clear();CategoryRecic.clear();
        DATERecic.clear();MoneyRecic.clear();
        ImageRecic.clear();idArray.clear();

        try {
            SafefixDatabaseHelper = new SafeFixDatabaseHelper(view.getContext());
            db = SafefixDatabaseHelper.getReadableDatabase();
            Cursor cursorGrafic = db.query ("MONEY",
                    new String[] {"CATEGORY", "SUM(MONEY) AS MONEY"},
                    null,null,
                    "CATEGORY", null,null);
            CategoryAlert = new String[cursorGrafic.getCount()];
            while (cursorGrafic.moveToNext()) {
                float Money; String Category;
                Money= cursorGrafic.getFloat(cursorGrafic.getColumnIndex("MONEY"));
                Category = cursorGrafic.getString(cursorGrafic.getColumnIndex("CATEGORY"));
                ListMoney.add(Money);
                labels.add(Category);
            } cursorGrafic.close();

            Cursor cursorRecicle = db.query ("MONEY",
                    new String[] {"_id","CATEGORY","IMAGE_RESOURCE_ID","MONEY","DATA"},
                    null,null,
                    null, null,"DATA DESC");

            while (cursorRecicle.moveToNext()) {
                String Money; int ImageRes1; String Category;  String Data; int id;
                Money = cursorRecicle.getString(cursorRecicle.getColumnIndex("MONEY"));
                ImageRes1 = cursorRecicle.getInt(cursorRecicle.getColumnIndex("IMAGE_RESOURCE_ID"));
                Category = cursorRecicle.getString(cursorRecicle.getColumnIndex("CATEGORY"));
                Data = cursorRecicle.getString(cursorRecicle.getColumnIndex("DATA"));
                id = cursorRecicle.getInt(cursorRecicle.getColumnIndex("_id"));
                if(CategoryRecic.size()<10){
                    CategoryRecic.add(Category);
                    DATERecic.add(Data);
                    MoneyRecic.add(Money);
                    ImageRecic.add(ImageRes1);
                    idArray.add(id);
                }
            } cursorRecicle.close();

            Cursor cursorSum = db.query("MONEY",new String[]{"SUM(MONEY) AS MONEY"},
                    null,null,null,null,null);

            if (cursorSum.moveToLast()){
                long sum = cursorSum.getLong(cursorSum.getColumnIndex("MONEY"));
                summaRash.setText(""+sum);

            }cursorSum.close();

            db.close();

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(view.getContext(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        int j = 0;
        for (Float i:ListMoney){
            entries.add(new BarEntry(i, j));
            j++;}
        for(int i=0; i<labels.size();i++){
            CategoryAlert[i] = labels.get(i);}
        return true;
    }

    protected void onPostExecute(Boolean result) {

        dataset = new BarDataSet(entries, "");
        chart = view.findViewById(R.id.chart);
        data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription("");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.animateY(500);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {

                String categoryGraf = CategoryAlert[entry.getXIndex()];
                Bundle bundl = new Bundle();
                bundl.putString("cat", categoryGraf);
                Fragment fragment = new FragmentVisibleRecicleFull();
                fragment.setArguments(bundl);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();}

            @Override
            public void onNothingSelected() {}});

        RecyclerView pizzaRecycler = view.findViewById(R.id.pizza_recycler);
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(view.getContext(),MoneyRecic, ImageRecic,CategoryRecic,DATERecic,idArray);
        pizzaRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        pizzaRecycler.setLayoutManager(layoutManager);
    }
}

    //Диалоговое окно
    public void openDialog() {
            final String MoneyIntent;
            MoneyIntent = textView454.getText().toString();
        textView454.setText("0");
        getedit = "0";
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_HOLO_DARK);
        builder.setTitle(R.string.category_inter);
        builder.setPositiveButton(R.string.dialog_exit,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });

        builder.setNeutralButton(R.string.category_dialog_add,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent intent = new Intent(view.getContext(),ActivityVisibleAddCategory.class);
                        intent.putExtra(ActivityVisibleAddCategory.EXTRA_MONEY,MoneyIntent);
                        startActivity(intent);
                    }
                });

        builder.setItems(CategoryAlert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if(Exists(CategoryAlert[which])){
                        db = SafefixDatabaseHelper.getReadableDatabase();
                        Cursor cFav = db.query ("MONEY",
                                new String[] {"IMAGE_RESOURCE_ID","CATEGORY"},
                                "CATEGORY =?",new String[]{CategoryAlert[which]},
                                "CATEGORY", null,null);

                        if (cFav.moveToFirst()) {
                            IntFav = cFav.getInt(cFav.getColumnIndex("IMAGE_RESOURCE_ID"));
                        } cFav.close();}
                    insertDrink(db, CategoryAlert[which],IntFav, MoneyIntent);
                db.close();
            } catch(SQLiteException e) {
                Toast toast = Toast.makeText(view.getContext(), "Database Error", Toast.LENGTH_SHORT);
                toast.show();
            }
           new VisualTask().execute();
            }});

        AlertDialog dialog = builder.create();
        dialog.show();
        Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        nbutton.setTextColor(Color.WHITE);
        Button pbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.WHITE);
        }

    //Проверка наличия строки
    public boolean Exists(String searchItem) {
        SafefixDatabaseHelper = new SafeFixDatabaseHelper(view.getContext());
        db = SafefixDatabaseHelper.getReadableDatabase();
        String[] columns = {"CATEGORY"};
        String selection = "CATEGORY" + " =?";
        String[] selectionArgs = {searchItem};
        String limit = "1";
        Cursor cursor = db.query("MONEY", columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;}

    //Удаление символов в строке
    public String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));}

    //Обработка нажатий на клавиатуре
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button2:
                textView454.setText(getedit);
                textView454.setText(removeLastChar(getedit));
                if (textView454.getText().length()<=0){
                    textView454.setText("0");}
                getedit = textView454.getText().toString();
                break;
            case R.id.button3:
                if (getedit.equals("0")){
                    Toast toast = Toast.makeText(view.getContext(), R.string.summ_add, Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                openDialog();
                    getedit = "0";
                }
                break;
            case R.id.button4:
                if (getedit.equals("0")){
                    textView454.setText("");}
                if(textView454.getText().length()<10){
                    textView454.append(button1.getText());
                    getedit = textView454.getText().toString();}
                break;
            case R.id.button5:
                if (getedit.equals("0")){
                    textView454.setText("");}
                if(textView454.getText().length()<10){
                textView454.append(button2.getText());
                getedit = textView454.getText().toString();}
                break;
            case R.id.button6:
                if (getedit.equals("0")){
                    textView454.setText("");}
                if(textView454.getText().length()<10){
                    textView454.append(button3.getText());
                    getedit = textView454.getText().toString();}
                break;
            case R.id.button7:
                if (getedit.equals("0")){
                    textView454.setText("");}
                if(textView454.getText().length()<10){
                    textView454.append(button4.getText());
                    getedit = textView454.getText().toString();}
                break;
            case R.id.button8:
                if (getedit.equals("0")){
                    textView454.setText("");}
                if(textView454.getText().length()<10){
                    textView454.append(button5.getText());
                    getedit = textView454.getText().toString();}
                break;
            case R.id.button9:
                if (getedit.equals("0")){
                    textView454.setText("");}
                if(textView454.getText().length()<10){
                    textView454.append(button6.getText());
                    getedit = textView454.getText().toString();}
                break;
            case R.id.button10:
                if (getedit.equals("0")){
                    textView454.setText("");}
                if(textView454.getText().length()<10){
                    textView454.append(button7.getText());
                    getedit = textView454.getText().toString();}
                break;
            case R.id.button11:
                if (getedit.equals("0")){
                    textView454.setText("");}
                if(textView454.getText().length()<10){
                    textView454.append(button8.getText());
                    getedit = textView454.getText().toString();}
                break;
            case R.id.button12:
                if (getedit.equals("0")){
                    textView454.setText("");}
                if(textView454.getText().length()<10){
                    textView454.append(button9.getText());
                    getedit = textView454.getText().toString();}
                break;
            case R.id.button13:
                if (!getedit.equals("0")){
                    textView454.append(button0.getText());
                    getedit = textView454.getText().toString();}
                break;
            case R.id.button14:
                String sub = ",";
                if (!getedit.contains(sub) && textView454.getText().length() > 0 ){
                    textView454.append(button00.getText());
                    getedit = textView454.getText().toString();}
                break;}}}
