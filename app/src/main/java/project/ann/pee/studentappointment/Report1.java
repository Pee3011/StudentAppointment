package project.ann.pee.studentappointment;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.query.Select;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Report1 extends BaseActivity{
    private Task  task ;
    private TextView reportView;
    private static int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        setDrawer(true);
        setTitle(R.string.tasks);


        reportView = (TextView) findViewById(R.id.reportView);

        count = Task.count();
        reportView.setText(Integer.toString(count));
    }



}


