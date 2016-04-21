package project.ann.pee.studentappointment;

import android.renderscript.Sampler;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "Tasks")
public class Task extends Model {



    @Column(name = "title",onUpdate = Column.ForeignKeyAction.CASCADE,onDelete = Column.ForeignKeyAction.CASCADE)
    public Act title;

    @Column(name = "content")
    public String content;

    @Column(name = "contact",onUpdate = Column.ForeignKeyAction.CASCADE,onDelete = Column.ForeignKeyAction.CASCADE)
    public ContactTB contact;

    @Column(name = "location",onUpdate = Column.ForeignKeyAction.CASCADE,onDelete = Column.ForeignKeyAction.CASCADE)
    public LocationsTB location;

    public List<Act> acts(){
        return getMany(Act.class,"activityName");
    }


    public List<ContactTB> contactTBs(){

        return getMany(ContactTB.class, "firstName");
    }


     public List<LocationsTB> locationsTBs(){
    return getMany(LocationsTB.class,"locationName");
}



    @Column(name ="timeStart")
    public String timeStart;

    @Column(name = "status" )
    public int status;

    @Column(name = "statusChar")
    public String statusChar;


    @Column(name="timeEnd")
    public String timeEnd;

    @Column(name ="dateStart")
    public String dateStart;

    @Column(name="dateEnd")
    public String dateEnd;

    @Column(name = "dueAt", index = true)
    public Date dueAt = null;

    @Column(name = "createdAt", index = true)
    public Date createdAt = null;

    @Column(name = "updatedAt", index = true)
    public Date updatedAt = null;

    public static List<Task> getAll() {
        return new Select().from(Task.class).orderBy("updatedAt DESC").execute();
    }

    public void saveWithTimestamp() {
        Date now = new Date();
        updatedAt = now;
        if (createdAt == null)
            createdAt = now;
        save();
    }

    public static int count() {
        return new Select()
                .from(Task.class)
                .where("status = ?",6)
                .count();
    }

}
