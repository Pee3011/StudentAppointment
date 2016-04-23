package project.ann.pee.studentappointment;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Table(name = "StatusTBs")
public class StatusTB extends Model {

    @Column(name = "statusName")
    public String statusName;


    @Column(name = "createdAt", index = true)
    public Date createdAt = null;

    @Column(name = "updatedAt", index = true)
    public Date updatedAt = null;

    public static List<StatusTB> getAll() {
        return new Select().from(StatusTB.class).execute();
    }

    public void saveWithTimestamp() {
        Date now = new Date();
        updatedAt = now;
        if (createdAt == null)
            createdAt = now;
        save();
    }

}


