package ke.co.nathanaccess.ui.data;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ke.co.nathanaccess.R;
import ke.co.nathanaccess.ui.data.recycler.DataAdapter;

public class DataDisplay extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Bundle bundle = getIntent().getExtras();
        String dataType = "";

        try{
            dataType = bundle.getString("dataType");
        } catch (NullPointerException e){
            Toast.makeText(this,"Invalid data type",Toast.LENGTH_SHORT).show();
            finish();
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DataAdapter();
        mRecyclerView.setAdapter(mAdapter);

        if(dataType.equals("Validation")){
            mAdapter.;
        } else if(dataType.equals("Cash Movement")){

        } else {

        }
    }
}