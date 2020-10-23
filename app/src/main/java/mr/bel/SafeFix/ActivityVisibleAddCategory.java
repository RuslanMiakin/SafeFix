package mr.bel.SafeFix;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ActivityVisibleAddCategory extends AppCompatActivity {

    ArrayList<String> CategoryArray; ArrayList<Integer> ImageArray;
    public static final String EXTRA_MONEY = "MoneyIntent";
    EditText editText; EditText editText1;
    String cat; String mon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        CategoryArray = new ArrayList<>(); ImageArray = new ArrayList<>();
        CategoryArray.add(getString(R.string.add_1)); ImageArray.add(R.drawable.airport);
        CategoryArray.add(getString(R.string.add_2)); ImageArray.add(R.drawable.apple);
        CategoryArray.add(getString(R.string.add_3)); ImageArray.add(R.drawable.bar);
        CategoryArray.add(getString(R.string.add_4)); ImageArray.add(R.drawable.bubbles);
        CategoryArray.add(getString(R.string.add_5)); ImageArray.add(R.drawable.cake);
        CategoryArray.add(getString(R.string.add_6)); ImageArray.add(R.drawable.camera);
        CategoryArray.add(getString(R.string.add_7)); ImageArray.add(R.drawable.case1);
        CategoryArray.add(getString(R.string.add_8)); ImageArray.add(R.drawable.children);
        CategoryArray.add(getString(R.string.add_9)); ImageArray.add(R.drawable.diamond);
        CategoryArray.add(getString(R.string.add_10)); ImageArray.add(R.drawable.dress);
        CategoryArray.add(getString(R.string.add_11)); ImageArray.add(R.drawable.fairytale);
        CategoryArray.add(getString(R.string.add_12)); ImageArray.add(R.drawable.gift);
        CategoryArray.add(getString(R.string.add_13)); ImageArray.add(R.drawable.intertaiment);
        CategoryArray.add(getString(R.string.add_14)); ImageArray.add(R.drawable.locker);
        CategoryArray.add(getString(R.string.add_15)); ImageArray.add(R.drawable.muscle);
        CategoryArray.add(getString(R.string.add_16)); ImageArray.add(R.drawable.musical);
        CategoryArray.add(getString(R.string.add_17)); ImageArray.add(R.drawable.notebook);
        CategoryArray.add(getString(R.string.add_18)); ImageArray.add(R.drawable.paint);
        CategoryArray.add(getString(R.string.add_19)); ImageArray.add(R.drawable.pizza);
        CategoryArray.add(getString(R.string.add_20)); ImageArray.add(R.drawable.potted);
        CategoryArray.add(getString(R.string.add_21)); ImageArray.add(R.drawable.shopaholic);
        CategoryArray.add(getString(R.string.add_22)); ImageArray.add(R.drawable.shoppingbasket);
        CategoryArray.add(getString(R.string.add_23)); ImageArray.add(R.drawable.tooth);
        CategoryArray.add(getString(R.string.add_24)); ImageArray.add(R.drawable.truck);

        String MoneyIntent = (String) Objects.requireNonNull(getIntent().getExtras()).get(EXTRA_MONEY);

        editText = findViewById(R.id.editTextMoney);
        editText.setText(MoneyIntent);
        editText1 =findViewById(R.id.editTextCategory);

        RecyclerView recycler = findViewById(R.id.recycler_addcat);
        CapImAdapterImageAdd adapter = new CapImAdapterImageAdd(CategoryArray,ImageArray);
        recycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);

        adapter.setListener(new CapImAdapterImageAdd.Listener() {
            @Override
            public void onClick(int position) {
                int image = ImageArray.get(position);
                if(editText1.getText().length()<9 && editText.getText().length() < 10){
                cat = editText1.getText().toString();
                mon = editText.getText().toString();
                if (!cat.equals("") && !mon.equals("0")){
                try {
                    SQLiteOpenHelper SafefixDatabaseHelper = new SafeFixDatabaseHelper(getApplicationContext());
                    SQLiteDatabase db = SafefixDatabaseHelper.getReadableDatabase();
                    insertDB(db,cat, image, mon);
                    db.close();
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.category_ok, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } catch(SQLiteException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }}else{
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.add_info, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.size_error, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }}
        });
    }

    private static void insertDB(SQLiteDatabase db, String Category, int resourceId, String Money) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date());
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("CATEGORY", Category);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        drinkValues.put("DATA", strDate);
        drinkValues.put("MONEY", Money);
        db.insert("MONEY", null, drinkValues);
    }
}