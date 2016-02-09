package project.ann.pee.studentappointment;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by PeerapatNy on 05-Feb-16.
 */
public class StudentAppointmentApplication extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
