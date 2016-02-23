package project.ann.pee.studentappointment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.w3c.dom.Text;

public class ShowLocation extends BaseActivity {

    private LocationTB     locationTB = null;
    private TextView locationView;

    private static final int MenuItem_EditID = 1;
    private static final int MenuItem_DeleteID = 2;
    private static final int EDIT_TASK = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_show);
        setDrawer(true);
        setTitle(R.string.location);

        locationView = (TextView) findViewById(R.id.locationView);


        long id = getIntent().getLongExtra("id", 0);
        setView(id);
    }

    private void setView(long id) {
        if (id > 0)
            locationTB = LocationTB.load(LocationTB.class, id);
        if (locationTB != null) {
            locationView.setText(locationTB.locationName);


        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        addMenuItem(menu, MenuItem_EditID, R.string.edit, buildDrawable(MaterialDesignIconic.Icon.gmi_edit));
        addMenuItem(menu, MenuItem_DeleteID, R.string.delete, buildDrawable(MaterialDesignIconic.Icon.gmi_delete));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MenuItem_EditID:
                Intent intent = new Intent(this, FormActDefault.class);
                intent.putExtra("id", locationTB.getId());
                startActivityForResult(intent, EDIT_TASK);
                break;
            case MenuItem_DeleteID:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(android.R.string.dialog_alert_title);
                alert.setMessage(R.string.are_you_sure);
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        locationTB.delete();
                        setResult(Activity.RESULT_OK, new Intent().putExtra("refreshNeeded", true));
                        finish();
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            switch (requestCode) {
                case EDIT_TASK:
                    if (extras != null && extras.getLong("id", 0) > 0) {
                        setView(locationTB.getId());
                        setResult(Activity.RESULT_OK, new Intent().putExtra("refreshNeeded", true));
                    }
                    break;
            }
        }
    }
}
