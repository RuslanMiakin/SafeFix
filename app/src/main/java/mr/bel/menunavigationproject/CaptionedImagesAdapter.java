package mr.bel.menunavigationproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>{

    SQLiteDatabase db;
    private ArrayList<String> captions;
    private ArrayList<Integer> imageIds;
    private ArrayList<String> category;
    private ArrayList<String> time;
    private ArrayList<Integer> idArray;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    public CaptionedImagesAdapter(Context context,ArrayList<String> captions, ArrayList<Integer> imageIds,ArrayList<String> category, ArrayList<String> time,ArrayList<Integer> idArray){
        this.captions = captions;
        this.imageIds = imageIds;
        this.category = category;
        this.time = time;
        this.idArray = idArray;
        this.context=context;
    }


    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position){

        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.info_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds.get(position));
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions.get(position));
        TextView textView = cardView.findViewById(R.id.info_text);
        textView.setText(captions.get(position));
        TextView texttime = cardView.findViewById(R.id.info_time);
        texttime.setText(time.get(position));
        TextView textcategory = cardView.findViewById(R.id.info_category);
        textcategory.setText(category.get(position));
        TextView idText = cardView.findViewById(R.id.textView_id);
        idText.setText(""+idArray.get(position));

    }


    public void deleteItem(int position) {

        captions.remove(position);
        notifyItemRemoved(position);
        String del = String.valueOf(idArray.get(position));
        SafeFixDatabaseHelper safeFixDatabaseHelper = new SafeFixDatabaseHelper(context);
        db = safeFixDatabaseHelper.getWritableDatabase();
        db.delete("MONEY", "_id = ?",new String[] {del});
        db.close();

    }

    @Override
    public int getItemCount(){
        return captions.size();
    }

    public void setData(ArrayList<String> captions, ArrayList<Integer> imageIds,ArrayList<String> category, ArrayList<String> time,ArrayList<Integer> idArray){
        this.captions = captions;
        this.imageIds = imageIds;
        this.category = category;
        this.time = time;
        this.idArray = idArray;
    }
}