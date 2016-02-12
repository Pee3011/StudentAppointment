package project.ann.pee.studentappointment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

public class FormActivity extends BaseActivity {

    private static final int MenuItem_SaveID = 1;

    private Task task = null;
    private EditText titleEdit;
    private EditText contentEdit;
    private EditText contactEdit;
    private EditText time_StarEdit;
    private EditText time_EndEdit;
    private EditText date_startEdit;
    private EditText date_endEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        setDrawer(true);

        FloatingActionButton add_title = (FloatingActionButton) findViewById(R.id.add_title);
        add_title.setImageDrawable(buildDrawable(MaterialDesignIconic.Icon.gmi_plus));
        add_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormActivity.this, FormActDefault.class));

            }
        });
        titleEdit = (EditText) findViewById(R.id.titleEdit);
        contentEdit = (EditText) findViewById(R.id.contentEdit);
        contactEdit = (EditText) findViewById(R.id.contactEdit);
        time_StarEdit = (EditText) findViewById(R.id.timeStart);
        time_EndEdit = (EditText)findViewById(R.id.timeEnd);
        date_startEdit = (EditText) findViewById(R.id.dateStart);
        date_endEdit = (EditText) findViewById(R.id.dateEnd);

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
            return titleEdit.getText().length() > 0 || contentEdit.getText().length() > 0 || contactEdit.getText().length()>0
                    || time_StarEdit.getText().length()>0 || time_EndEdit.getText().length()>0
                    || date_startEdit.getText().length()>0
                    || date_endEdit.getText().length()>0;
        else
            return !task.title.equals(titleEdit.getText().toString()) || !task.content.equals(contentEdit.getText().toString())||
                    !task.contact.equals(contactEdit.getText().toString())|| !task.timeStart.equals(time_StarEdit.getText().toString())
                    || !task.timeEnd.equals(time_EndEdit.getText().toString())
                    || !task.dateStart.equals(date_startEdit.getText().toString())
                    || !task.dateEnd.equals(date_endEdit.getText().toString());
    }

    private void save() {
        if (titleEdit.getText().length() > 0) {
            if (task == null)
                task = new Task();
            task.title = titleEdit.getText().toString();
            task.content = contentEdit.getText().toString();
            task.contact = contactEdit.getText().toString();
            task.timeStart= time_StarEdit.getText().toString();
            task.timeEnd = time_EndEdit.getText().toString();
            task.dateStart = date_startEdit.getText().toString();
            task.dateEnd = date_endEdit.getText().toString();
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
}
