package project.ann.pee.studentappointment;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Activity")
public class Activity extends Model {

    @Column(name = "name")
    public String name;
    
}