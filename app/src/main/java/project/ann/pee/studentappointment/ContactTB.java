package project.ann.pee.studentappointment;

import android.location.Location;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;


@Table(name = "Contact")
public class ContactTB extends Model {

    @Column(name="firstName")
    public String firstName;

    @Column(name="lastName")
    public String lastName;

    @Column(name = "dueAt", index = true)
    public Date dueAt = null;

    @Column(name = "createdAt", index = true)
    public Date createdAt = null;

    @Column(name = "updatedAt", index = true)
    public Date updatedAt = null;

    public static List<ContactTB> getAll() {
        return new Select().from(ContactTB.class).orderBy("updatedAt DESC").execute();
    }

    public void saveWithTimestamp() {
        Date now = new Date();
        updatedAt = now;
        if (createdAt == null)
            createdAt = now;
        save();
    }

}
