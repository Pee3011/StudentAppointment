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
    private TextView reportIncomplete;
    private TextView reportComplete;
    private TextView reportCancle;
    private static int countIncomplete;
    private static int countComplete;
    private static int countCancle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        setDrawer(true);
        setTitle(R.string.tasks);

//countIncomplete
        reportIncomplete = (TextView) findViewById(R.id.reportIncomplete);
        countIncomplete = Task.countIncomplete();
        reportIncomplete.setText(Integer.toString(countIncomplete));

//countComplete
        reportComplete = (TextView) findViewById(R.id.reportComplete);
        countComplete = Task.countComplete();
        reportComplete.setText(Integer.toString(countComplete));
 //countCancle
        reportCancle = (TextView) findViewById(R.id.reportCancle);
        countCancle = Task.countCancle();
        reportCancle.setText(Integer.toString(countCancle));





    }



}


