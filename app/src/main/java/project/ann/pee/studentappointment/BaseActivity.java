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
    protected static final int NEW_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setDrawer(Boolean upEnabled) {
        Toolbar toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            if (upEnabled){
                supportActionBar.setDisplayHomeAsUpEnabled(upEnabled);
            }
            else {
                final Intent intent = new Intent(this, ActList.class);

                new DrawerBuilder()
                        .withActivity(this)
                        .withToolbar(toolbar_main)
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
                                        .withIdentifier(1)

                        ).withSelectedItem(-1)

                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.setting_Location)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_my_location)
                                        .withSelectable(false)
                                        .withIdentifier(1)

                        ).withSelectedItem(-1)

                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.about_us)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_apps)
                                        .withSelectable(false)
                                        .withIdentifier(1)

                        ).withSelectedItem(-1)

                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                switch (drawerItem.getIdentifier()) {
                                    case 1:
                                        startActivityForResult(intent, NEW_TASK);
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