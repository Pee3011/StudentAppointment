package project.ann.pee.studentappointment;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;


@Table(name = "Act")
public class Act extends Model {

    @Column(name = "activityName")
    public String activityName;


    @Column(name = "dueAt", index = true)
    public Date dueAt = null;

    @Column(name = "createdAt", index = true)
    public Date createdAt = null;

    @Column(name = "updatedAt", index = true)
    public Date updatedAt = null;

    public static List<Act> getAll() {
        return new Select().from(Act.class).execute();
    }

    public void saveWithTimestamp() {
        Date now = new Date();
        updatedAt = now;
        if (createdAt == null)
            createdAt = now;
        save();
    }

}

