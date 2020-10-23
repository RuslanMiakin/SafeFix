package mr.bel.SafeFix;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CapImAdapterImageAdd extends RecyclerView.Adapter<CapImAdapterImageAdd.ViewHolder>{

    private ArrayList<String> captions;
    private ArrayList<Integer> imageIds;
    private Listener listener;
    public  interface Listener {
         void onClick(int position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    public CapImAdapterImageAdd(ArrayList<String> captions, ArrayList<Integer> imageIds){
        this.captions = captions;
        this.imageIds = imageIds;
    }

    public void setListener(CapImAdapterImageAdd.Listener listener){
        this.listener = listener;
    }

    @Override
    public CapImAdapterImageAdd.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv2 = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_cap_im2, parent, false);
        return new CapImAdapterImageAdd.ViewHolder(cv2);
    }

    public void onBindViewHolder(CapImAdapterImageAdd.ViewHolder holder, @SuppressLint("RecyclerView") final int position){
        CardView cardView = holder.cardView;
        ImageView imageView =cardView.findViewById(R.id.info_image_add);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds.get(position));
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions.get(position));
        TextView textView = cardView.findViewById(R.id.textView_add);
        textView.setText(captions.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }
    @Override
    public int getItemCount(){
        return captions.size();
    }}
