package project.ann.pee.studentappointment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

public class FormStatus extends BaseActivity {
    private Status statuss = null;

    private static final int MenuItem_SaveID = 1;
    private static final int MenuItem_DeleteID = 2;
    private Status status= null;
    private EditText statusEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_form);
        setDrawer(true);

        statusEdit = (EditText) findViewById(R.id.statusEdit);

        long id = getIntent().getLongExtra("id", 0);
        if (id == 0) {
            setTitle(R.string.new_status);
        } else {
            setTitle(R.string.edit_status);
            status = Status.load(Status.class, id);
            if (status != null) {
                statusEdit.setText(status.Name);

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
        if (status == null)
            return statusEdit.getText().length() > 0 ;
        else
            return !status.Name.equals(statusEdit.getText().toString()) ;
    }

    private void save() {
        if (statusEdit.getText().length() > 0) {
            if (status == null)
                status = new Status();
            status.Name = statusEdit.getText().toString();

            status.saveWithTimestamp();
            setResult(Activity.RESULT_OK, new Intent().putExtra("id", status.getId()));
            this.finish();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(android.R.string.dialog_alert_title);
            alert.setMessage(R.string.location_is_required);
            alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();
        }
    }

}
