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
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;

public class LocationList extends BaseActivity {

    private static final int NEW_TASK = 1;
    private static final int SHOW_TASK = 2;

    private ArrayList<LocationsTB> tasks;


    private ListView listView;
    private TextView emptyLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list);
        setDrawer(false);
        setTitle(R.string.locations);

        listView = (ListView) findViewById(R.id.listView);
        emptyLabel = (TextView) findViewById(R.id.emptyLabel);
        FloatingActionButton newFab = (FloatingActionButton) findViewById(R.id.newFab);
        newFab.setImageDrawable(buildDrawable(MaterialDesignIconic.Icon.gmi_plus));
        newFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationList.this, FormLocationDefault.class);
                startActivityForResult(intent, NEW_TASK);
            }
        });


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

    private void setView() {
        tasks = new ArrayList<>(LocationsTB.getAll());
        if (tasks.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyLabel.setVisibility(View.VISIBLE);
        } else {
            emptyLabel.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new LocationAdapter(this, tasks));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(LocationList.this, ShowLocation.class);
                    intent.putExtra("id", tasks.get(position).getId());
                    startActivityForResult(intent, SHOW_TASK);
                }
            });
        }
    }
}

class LocationAdapter extends ArrayAdapter<LocationsTB> {

    public LocationAdapter(Context context, ArrayList<LocationsTB> acts) {
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
