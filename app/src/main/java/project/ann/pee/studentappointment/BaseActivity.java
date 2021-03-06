package project.ann.pee.studentappointment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    protected static final int NEW_TASK = 1;
    protected static final int NEW = 2;
    protected static final int NEW2 = 3;
    protected static final int HOME = 4;
    protected static final int NEW4 = 5;
    protected static final int NEW6 = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setDrawer(Boolean upEnabled) {
        Toolbar toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            if (upEnabled) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                final Intent intent = new Intent(this, ActList.class);
                final Intent intent1 = new Intent(this, LocationList.class);
                final Intent intent2 = new Intent(this, ContactList.class);
                final Intent intent3 = new Intent(this, ListActivity.class);
                final Intent intent4 = new Intent(this, AboutUs.class);
                final Intent intent5 = new Intent(this, Report1.class);
                new DrawerBuilder()
                        .withActivity(this)
                        .withToolbar(toolbar_main)
                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.setting_home)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_home)
                                        .withSelectable(false)
                                        .withIdentifier(4)

                        ).withSelectedItem(-1)

                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.setting_Activity)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_local_activity)
                                        .withSelectable(false)
                                        .withIdentifier(1)

                        ).withSelectedItem(-1)

                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.setting_Contact)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_nature_people)
                                        .withSelectable(false)
                                        .withIdentifier(3)

                        ).withSelectedItem(-1)

                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.setting_Location)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_my_location)
                                        .withSelectable(false)
                                        .withIdentifier(2)

                        ).withSelectedItem(-1)

                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName("Report appointment status")
                                        .withIcon(MaterialDesignIconic.Icon.gmi_reader)
                                        .withSelectable(false)
                                        .withIdentifier(6)
                        )

                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.about_us)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_apps)
                                        .withSelectable(false)
                                        .withIdentifier(5)

                        ).withSelectedItem(-1)


                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                switch (drawerItem.getIdentifier()) {
                                    case 1:
                                        startActivityForResult(intent, NEW_TASK);
                                        break;
                                    case 2:
                                        startActivityForResult(intent1, NEW);
                                        break;
                                    case 3:
                                        startActivityForResult(intent2, NEW2);
                                        break;
                                    case 4:
                                        startActivityForResult(intent3, HOME);
                                        break;
                                    case 5:
                                        startActivityForResult(intent4, NEW4);
                                        break;
                                    case 6:
                                        startActivityForResult(intent5, NEW6);
                                        break;
                                }
                                return false;
                            }
                        })
                        .build();
            }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    protected Drawable buildDrawable(IIcon icon) {
        return new IconicsDrawable(this).icon(icon).color(Color.WHITE).sizeDp(70).paddingDp(4);
    }

    protected void addMenuItem(Menu menu, int id, int labelId, Drawable icon) {
        MenuItem menuItem = menu.add(Menu.NONE, id, Menu.NONE, labelId);
        menuItem.setIcon(icon);
        menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }


}