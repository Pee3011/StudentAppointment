package project.ann.pee.studentappointment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.model.ContainerDrawerItem;

import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.widget.AdapterView.OnItemSelectedListener;


public class FormActivity extends BaseActivity implements OnClickListener {

    private static final int MenuItem_SaveID = 1;
    private static final int Refresh=2;


    private Task task = null;
    private Spinner titleEdit;
    private EditText contentEdit;
    private ContactTB contactTB;

    private EditText time_StarEdit;
    private EditText time_EndEdit;
    private EditText date_startEdit;
    private EditText date_endEdit;
    private Spinner location;
    private Spinner statusEdit;
    private Spinner spn;
    private EditText tx;


    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    private EditText fromTime;
    private EditText toTime;

    private int mHour;
    private int mMinute;

    private ArrayList<ContactTB> contactTBs;
    private ArrayList<Act> acts;
    private ArrayList<LocationsTB> locats;
    private ArrayList<Status> statuses;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private TimePickerDialog fromTimePickerDialog;
    private TimePickerDialog toTimePickerDialog;

    private SimpleDateFormat dateFormatter;
    private Spinner spinner;
    private String[] str;
    private String choosed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        setDrawer(true);

        Spinner spn = (Spinner) findViewById(R.id.status);

        str=getResources().getStringArray(R.array.statusar);
        ArrayAdapter<String> objAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, str);
        objAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(objAdapter);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                choosed = str[i];
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                choosed = str[0];
            }

        });


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();

        setView();


        ImageButton add_title = (ImageButton) findViewById(R.id.add_title);
              add_title.setOnClickListener(new OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      startActivity(new Intent(FormActivity.this, FormActDefault.class));

                  }
              });

        ImageButton add_contact = (ImageButton) findViewById(R.id.add_contact);
               add_contact.setOnClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       startActivity(new Intent(FormActivity.this, FormContactDefault.class));

                   }
               });

        ImageButton add_location = (ImageButton) findViewById(R.id.add_location);

        add_location.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormActivity.this, FormLocationDefault.class));

            }
        });


        tx=(EditText)findViewById(R.id.textchoose);
        spinner = (Spinner) findViewById(R.id.spinner);
        titleEdit = (Spinner)findViewById(R.id.titleEdit);
        location = (Spinner)findViewById(R.id.locationEdit);
        contentEdit = (EditText) findViewById(R.id.contentEdit);
        time_StarEdit = (EditText) findViewById(R.id.timeStart);
        time_EndEdit = (EditText) findViewById(R.id.timeEnd);
        date_startEdit = (EditText) findViewById(R.id.dateStart);
        date_endEdit = (EditText) findViewById(R.id.dateEnd);
        //statusEdit=(Spinner)findViewById(R.id.status);


        long id = getIntent().getLongExtra("id", 0);
        if (id == 0) {
            setTitle(R.string.new_task);
        } else {
            setTitle(R.string.edit_task);
            task = Task.load(Task.class, id);
            contactTB = ContactTB.load(ContactTB.class,id);
            if (task != null) {
                contentEdit.setText(task.content);
                // contactEdit.setText(task.contact);
                time_StarEdit.setText(task.timeStart);
                time_EndEdit.setText(task.timeEnd);
                date_startEdit.setText(task.dateStart);
                date_endEdit.setText(task.dateEnd);
                //location.setText(task.location);
                int spinnerPosition = task.contact.getId().intValue()-1;
                spinner.setSelection(spinnerPosition);



                int spinnerPosition1 = task.title.getId().intValue()-1;
                titleEdit.setSelection(spinnerPosition1);

                int spinnerPosition2 = task.location.getId().intValue()-1;
                location.setSelection(spinnerPosition2);





            } else {
                finish();
            }
        }
    }


public void clickChoose(View view){
    tx.setText(choosed);
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        addMenuItem(menu, Refresh, R.string.refresh, buildDrawable(MaterialDesignIconic.Icon.gmi_refresh));
        addMenuItem(menu, MenuItem_SaveID, R.string.save, buildDrawable(MaterialDesignIconic.Icon.gmi_save));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isEdited()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle(android.R.string.dialog_alert_title);
                    alert.setMessage(R.string.unsaved_exit_alert);
                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.show();
                    return true;
                }
                break;
            case MenuItem_SaveID:
                save();
                break;
            case Refresh:
                refresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        Intent i= getIntent();
               finish();
        startActivity(i);
    }

    private boolean isEdited() {
        if (task == null)
            return contentEdit.getText().length() > 0 || time_StarEdit.getText().length() > 0 || time_EndEdit.getText().length() > 0
                    || date_startEdit.getText().length() > 0
                    || date_endEdit.getText().length() > 0;
        else
            return !task.content.equals(contentEdit.getText().toString()) || !task.timeStart.equals(time_StarEdit.getText().toString())
                    || !task.timeEnd.equals(time_EndEdit.getText().toString())
                    || !task.dateStart.equals(date_startEdit.getText().toString())
                    || !task.dateEnd.equals(date_endEdit.getText().toString());
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.dateStart);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        fromTime = (EditText) findViewById(R.id.timeStart);
        fromTime.setInputType(InputType.TYPE_NULL);
        fromTime.requestFocus();


        toDateEtxt = (EditText) findViewById(R.id.dateEnd);
        toDateEtxt.setInputType(InputType.TYPE_NULL);

        toTime = (EditText) findViewById(R.id.timeEnd);
        toTime.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        fromTime.setOnClickListener(this);
        toTime.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        mHour = newCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = newCalendar.get(Calendar.MINUTE);
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromTimePickerDialog = new TimePickerDialog(this
                , new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                fromTime.setText(hourOfDay + ":" + minute);
            }
        }, mHour, mMinute, false);

        toTimePickerDialog = new TimePickerDialog(this
                , new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                toTime.setText(hourOfDay + ":" + minute);
            }
        }, mHour, mMinute, false);


        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    private void save() {
        if (date_startEdit.getText().length() > 0) {
            if (task == null)
                task = new Task();


            task.statusChar = tx.getText().toString();
            task.status = tx.getText().length();
            task.title = (Act)titleEdit.getSelectedItem();
            task.content = contentEdit.getText().toString();
            task.contact =  (ContactTB) spinner.getSelectedItem();
            task.timeStart = time_StarEdit.getText().toString();
            task.timeEnd = time_EndEdit.getText().toString();
            task.dateStart = date_startEdit.getText().toString();
            task.dateEnd = date_endEdit.getText().toString();
            task.location = (LocationsTB)location.getSelectedItem();
            task.saveWithTimestamp();
            setResult(Activity.RESULT_OK, new Intent().putExtra("id", task.getId()));
            this.finish();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(android.R.string.dialog_alert_title);
            alert.setMessage(R.string.title_is_required);
            alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();
        }


    }


    @Override
    public void onClick(View v) {
        if (v == fromDateEtxt) {
            fromDatePickerDialog.show();

        }
        if (v == fromTime) {
            fromTimePickerDialog.show();
        } else if (v == toDateEtxt) {
            toDatePickerDialog.show();
        }
        if (v == toTime) {
            toTimePickerDialog.show();
        }

    }

    private void setView() {
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(new ContactTBAdapter(this, contactTBs));
        contactTBs = new ArrayList<>(ContactTB.getAll());


        Spinner sp1 = (Spinner) findViewById(R.id.titleEdit);
        sp1.setOnItemSelectedListener(new ActivityAdapter(this, acts));
        acts = new ArrayList<>(Act.getAll());

        Spinner sp2 = (Spinner) findViewById(R.id.locationEdit);
        sp2.setOnItemSelectedListener(new LocationAdapter(this, locats));
        locats=new ArrayList<>(LocationsTB.getAll());

      //  Spinner sp3=(Spinner)findViewById(R.id.status);
      //  sp3.setOnItemSelectedListener(new StatusAdapter(this, statuses));
      //  statuses = new ArrayList<>(Status.getAll());

        sp.setAdapter(new ContactTBAdapter(this, contactTBs));
        ArrayAdapter<ContactTB> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contactTBs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp1.setAdapter(new ActivityAdapter(this, acts));
        ArrayAdapter<Act> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, acts);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);


        sp2.setAdapter(new LocationAdapter(this, locats));
        ArrayAdapter<LocationsTB> adapter2= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locats);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);

        //sp3.setAdapter(new StatusAdapter(this, statuses));
       // ArrayAdapter<Status> adapter3= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
       // adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
    }

    private class LocationAdapter extends ArrayAdapter<LocationsTB> implements OnItemSelectedListener {
        public LocationAdapter(Context context, ArrayList<LocationsTB> locats) {
            super(context, R.layout.support_simple_spinner_dropdown_item, locats);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            LocationsTB locationsTB = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }
            TextView sp1 = (TextView) convertView.findViewById(android.R.id.text1);
            sp1.setText(locationsTB.locationName);

            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LocationsTB locationsTB = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
            }
            TextView sp1 = (TextView) convertView.findViewById(android.R.id.text1);
            sp1.setText(locationsTB.locationName);

            return convertView;
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    private class ActivityAdapter extends ArrayAdapter<Act> implements OnItemSelectedListener {
        public ActivityAdapter(Context context, ArrayList<Act> acts) {
            super(context, R.layout.support_simple_spinner_dropdown_item, acts);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            Act act = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }
            TextView sp1 = (TextView) convertView.findViewById(android.R.id.text1);
            sp1.setText(act.activityName);

            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Act act = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
            }
            TextView sp1 = (TextView) convertView.findViewById(android.R.id.text1);
            sp1.setText(act.activityName);

            return convertView;
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class ContactTBAdapter extends ArrayAdapter<ContactTB> implements OnItemSelectedListener {
        public ContactTBAdapter(Context context, ArrayList<ContactTB> contactTBs) {
            super(context, R.layout.support_simple_spinner_dropdown_item, contactTBs);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            ContactTB contactTB = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }
            TextView sp = (TextView) convertView.findViewById(android.R.id.text1);
            sp.setText(contactTB.firstName);

            //TextView sp1 = (TextView) convertView.findViewById(android.R.id.text2);
            //sp1.setText(contactTB.lastName);

            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ContactTB contactTB = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
            }
            TextView sp = (TextView) convertView.findViewById(android.R.id.text1);
            sp.setText(contactTB.firstName);

            return convertView;
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    class StatusAdapter extends ArrayAdapter<Status> implements OnItemSelectedListener {
        public StatusAdapter(Context context, ArrayList<Status> statuses) {
            super(context, R.layout.support_simple_spinner_dropdown_item, statuses);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            Status status = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_expandable_list_item_2, parent, false);
            }
            TextView sp = (TextView) convertView.findViewById(android.R.id.text1);
            sp.setText(status.Name);

            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Status status = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
            }
            TextView sp = (TextView) convertView.findViewById(android.R.id.text1);
            sp.setText(status.Name);

            return convertView;
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}


