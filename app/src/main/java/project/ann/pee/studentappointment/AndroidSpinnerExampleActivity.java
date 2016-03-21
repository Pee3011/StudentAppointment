package project.ann.pee.studentappointment;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.activeandroid.query.Select;

public class AndroidSpinnerExampleActivity extends Activity implements OnItemSelectedListener{
    private Act act;
    private Spinner spinner;
    private static final  int show_task = 1;
    private ArrayList<Act> acts;
     private ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropdown2);

         // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);


        // Spinner Drop down elements
       List<Act> acts = new ArrayList<>(Act.getAll());
        spinner.setAdapter(new SpinnerAdapter() {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                getItem(position);
                TextView tv=(TextView)convertView.findViewById(android.R.id.text1);
                tv.setText(act.activityName);
                return null;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }


            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });


        // Creating adapter for spinner
        ArrayAdapter<Act> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, acts);

        // Drop down layout style - list view with radio button
       dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
       spinner.setAdapter(dataAdapter);

    }
    private void setView() {
        acts = new ArrayList<Act>(Act.getAll());

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();


        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {

    }


}
