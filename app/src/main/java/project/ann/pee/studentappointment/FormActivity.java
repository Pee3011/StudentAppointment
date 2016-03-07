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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class FormActivity extends BaseActivity implements OnClickListener {

    private static final int MenuItem_SaveID = 1;

    private Task task = null;
    private EditText titleEdit;
    private EditText contentEdit;
    private EditText contactEdit;
    private EditText time_StarEdit;
    private EditText time_EndEdit;
    private EditText date_startEdit;
    private EditText date_endEdit;
    private EditText location;

    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    private EditText fromTime;
    private EditText toTime;

    private int mHour;
    private int mMinute;

    private ArrayList<ContactTB> contactTBs;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private TimePickerDialog fromTimePickerDialog;
    private TimePickerDialog toTimePickerDialog;

    private SimpleDateFormat dateFormatter;




    private Spinner spinner;
    private int string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        setDrawer(true);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();

        setView();

        FloatingActionButton add_title = (FloatingActionButton) findViewById(R.id.add_title);
        add_title.setImageDrawable(buildDrawable(MaterialDesignIconic.Icon.gmi_plus));
        add_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormActivity.this, FormActDefault.class));

            }
        });

        FloatingActionButton add_contact = (FloatingActionButton) findViewById(R.id.add_contact);
        add_contact.setImageDrawable(buildDrawable(MaterialDesignIconic.Icon.gmi_plus));
        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormActivity.this, FormContactDefault.class));

            }
        });

        FloatingActionButton add_location = (FloatingActionButton) findViewById(R.id.add_location);
        add_location.setImageDrawable(buildDrawable(MaterialDesignIconic.Icon.gmi_plus));
        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormActivity.this, FormLocationDefault.class));

            }
        });





        titleEdit = (EditText) findViewById(R.id.titleEdit);
        contentEdit = (EditText) findViewById(R.id.contentEdit);
        contactEdit = (EditText) findViewById(R.id.contactEdit);
        time_StarEdit = (EditText) findViewById(R.id.timeStart);
        time_EndEdit = (EditText) findViewById(R.id.timeEnd);
        date_startEdit = (EditText) findViewById(R.id.dateStart);
        date_endEdit = (EditText) findViewById(R.id.dateEnd);
        location=(EditText)findViewById(R.id.location);

        long id = getIntent().getLongExtra("id", 0);
        if (id == 0) {
            setTitle(R.string.new_task);
        } else {
            setTitle(R.string.edit_task);
            task = Task.load(Task.class, id);
            if (task != null) {
                titleEdit.setText(task.title);
                contentEdit.setText(task.content);
                contactEdit.setText(task.contact);
                time_StarEdit.setText(task.timeStart);
                time_EndEdit.setText(task.timeEnd);
                date_startEdit.setText(task.dateStart);
                date_endEdit.setText(task.dateEnd);
                location.setText(task.location);
            } else {
                finish();
            }
        }
    }
  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isEdited() {
        if (task == null)
            return titleEdit.getText().length() > 0 || contentEdit.getText().length() > 0 || contactEdit.getText().length() > 0
                    || time_StarEdit.getText().length() > 0 || time_EndEdit.getText().length() > 0
                    || date_startEdit.getText().length() > 0
                    || date_endEdit.getText().length() > 0|| location.getText().length()>0 ;
        else
            return !task.title.equals(titleEdit.getText().toString()) || !task.content.equals(contentEdit.getText().toString()) ||
                    !task.contact.equals(contactEdit.getText().toString()) || !task.timeStart.equals(time_StarEdit.getText().toString())
                    || !task.timeEnd.equals(time_EndEdit.getText().toString())
                    || !task.dateStart.equals(date_startEdit.getText().toString())
                    || !task.dateEnd.equals(date_endEdit.getText().toString())
                    || !task.location.equals(location.getText().toString());
    }

    private void findViewsById(){
        fromDateEtxt = (EditText) findViewById(R.id.dateStart);
       fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        fromTime=(EditText)findViewById(R.id.timeStart);
        fromTime.setInputType(InputType.TYPE_NULL);
        fromTime.requestFocus();


        toDateEtxt = (EditText) findViewById(R.id.dateEnd);
        toDateEtxt.setInputType(InputType.TYPE_NULL);

        toTime=(EditText)findViewById(R.id.timeEnd);
        toTime.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField(){
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        fromTime.setOnClickListener(this);
        toTime.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        mHour = newCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute=newCalendar.get(Calendar.MINUTE);
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromTimePickerDialog  =new TimePickerDialog(this
                , new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                fromTime.setText(hourOfDay + ":" + minute);
            }
        },mHour,mMinute,false);

        toTimePickerDialog  =new TimePickerDialog(this
                , new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                toTime.setText(hourOfDay + ":" + minute);
            }
        },mHour,mMinute,false);





        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));

            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));




    }




    private void save() {
        if (titleEdit.getText().length() > 0) {
            if (task == null)
                task = new Task();
            task.title = titleEdit.getText().toString();
            task.content = contentEdit.getText().toString();
            task.contact = contactEdit.getText().toString();
            task.timeStart = time_StarEdit.getText().toString();
            task.timeEnd = time_EndEdit.getText().toString();
            task.dateStart = date_startEdit.getText().toString();
            task.dateEnd = date_endEdit.getText().toString();
            task.location=location.getText().toString();
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
        if (v == fromDateEtxt){
            fromDatePickerDialog.show();

        }
        if (v== fromTime){
            fromTimePickerDialog.show();
        }

        else if (v == toDateEtxt) {
            toDatePickerDialog.show();
        }
             if (v == toTime){
                 toTimePickerDialog.show();
             }


        }


    private void setView() {
        contactTBs = new ArrayList<>(ContactTB.getAll());

        final Spinner sp=(Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp.setAdapter(new ContactAdapter(this, contactTBs));



    }

    class ContactAdapter extends ArrayAdapter<ContactTB> {

        public ContactAdapter(Context context, ArrayList<ContactTB> acts) {

            super(context, 0, acts);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ContactTB contactTB = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }
           TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(contactTB.firstName);
            return convertView;
        }
    }


    }