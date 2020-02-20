package ke.co.nathanaccess.ui.data;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ke.co.nathanaccess.R;

public class DataDisplay extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String dataType = "";

        try{
            dataType = bundle.getString("dataType");
        } catch (NullPointerException e){
            Toast.makeText(this,"Invalid data type",Toast.LENGTH_SHORT).show();
            finish();
        }

        if(dataType.equals("Validation")){
            setContentView(R.layout.activity_data);
        } else if(dataType.equals("Cash Movement")){
            setContentView(R.layout.activity_data);
        } else {
            setContentView(R.layout.activity_data);
        }
    }
}
