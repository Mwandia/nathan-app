package ke.co.nathanaccess.ui.data.recycler;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ke.co.nathanaccess.R;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    public static final String TAG = "RecyclerViewAdapter";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        LinearLayout mRecyclerLayout;

        public ViewHolder(View itemView){
            super(itemView);
            mRecyclerLayout = itemView.findViewById(R.id.recycler_view);
            if(itemView.getId() == R.id.cash_card){
                tv1 = itemView.findViewById(R.id.balanceTextView);
                tv2 = itemView.findViewById(R.id.balanceTextView);
                tv3 = itemView.findViewById(R.id.balanceTextView);
                tv4 = itemView.findViewById(R.id.balanceTextView);
                tv5 = itemView.findViewById(R.id.balanceTextView);
            } else if(itemView.getId() == R.id.vehicle_card){
                tv1 = itemView.findViewById(R.id.balanceTextView);
                tv2 = itemView.findViewById(R.id.balanceTextView);
                tv3 = itemView.findViewById(R.id.balanceTextView);
                tv4 = itemView.findViewById(R.id.balanceTextView);
                tv5 = itemView.findViewById(R.id.balanceTextView);
            } else {
                tv1 = itemView.findViewById(R.id.balanceTextView);
                tv2 = itemView.findViewById(R.id.balanceTextView);
            }

        }
    }
}
