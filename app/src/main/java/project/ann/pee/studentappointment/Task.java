package project.ann.pee.studentappointment;

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

    @Column(name = "title")
    public String title;

    @Column(name = "content")
    public String content;

    @Column(name = "contact")
    public String contact;

    public List<ContactTB>contactTBs(){
        return getMany(ContactTB.class,"Task");
    }


    @Column(name = "location")
    public String location;

    @Column(name ="timeStart")
    public String timeStart;

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



}
