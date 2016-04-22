package project.ann.pee.studentappointment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

public class FormContactDefault extends BaseActivity {

    private static final int MenuItem_SaveID = 1;

    private ContactTB contact = null;
    private EditText firstNameEdit;
    private EditText lastNameEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_form);
        setDrawer(true);

        firstNameEdit = (EditText) findViewById(R.id.firstNameEdit);
        lastNameEdit = (EditText) findViewById(R.id.lastNameEdit);
        long id = getIntent().getLongExtra("id", 0);
        if (id == 0) {
            setTitle(R.string.new_contact);
        } else {
            setTitle(R.string.edit_contact);
            contact = ContactTB.load(ContactTB.class, id);
            if (contact != null) {
                firstNameEdit.setText(contact.firstName);
                lastNameEdit.setText(contact.lastName);

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
        if (contact == null)
            return firstNameEdit.getText().length() > 0 ||
                    lastNameEdit.getText().length() > 0;
        else
            return !contact.firstName.equals(firstNameEdit.getText().toString())
                    || !contact.lastName.equals(lastNameEdit.getText().toString());
    }

    private void save() {
        if (firstNameEdit.getText().length() > 0) {
            if (contact == null)
                contact = new ContactTB();
            contact.firstName = firstNameEdit.getText().toString();
            contact.lastName = lastNameEdit.getText().toString();
            contact.saveWithTimestamp();
            setResult(Activity.RESULT_OK, new Intent().putExtra("id", contact.getId()));
            this.finish();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(android.R.string.dialog_alert_title);
            alert.setMessage(R.string.contact_is_required);
            alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();
        }
    }
}
