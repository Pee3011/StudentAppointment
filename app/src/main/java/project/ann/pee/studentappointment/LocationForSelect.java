package project.ann.pee.studentappointment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;

public class LocationForSelect extends BaseActivity {

    private static final int NEW_TASK = 1;
    private static final int SHOW_TASK = 2;

    private ArrayList<LocationsTB> tasks;


    private ListView listView;
    private TextView emptyLabel;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list);
        setDrawer(false);
        setTitle(R.string.locations);


        listView = (ListView) findViewById(R.id.listView);
        emptyLabel = (TextView) findViewById(R.id.emptyLabel);
        scrollView = (ScrollView)findViewById(R.id.scrollView);




        setView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            switch (requestCode) {
                case NEW_TASK:
                    if (extras != null && extras.getLong("id", 0) > 0)
                        setView();
                    break;
                case SHOW_TASK:
                    if (extras != null && extras.getBoolean("refreshNeeded", false))
                        setView();
                    break;
            }
        }
    }

    public void setView() {
        tasks = new ArrayList<>(LocationsTB.getAll());
        if (tasks.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyLabel.setVisibility(View.VISIBLE);
        } else {
            emptyLabel.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new LoAdapter(this, tasks));
           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   String selected = ((EditText)view.findViewById(R.id.titleEdit)).getText().toString();

                  Toast toast=Toast.makeText(getApplicationContext(),selected,Toast.LENGTH_LONG);


            }

            });
        }
    }
}

class LoAdapter extends ArrayAdapter<LocationsTB> {

    public LoAdapter(Context context, ArrayList<LocationsTB> acts) {
        super(context, 0, acts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocationsTB locationsTB = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(locationsTB.locationName);

        return convertView;
    }
}
