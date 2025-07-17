package library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Author {
    int id;
    String name;
    LocalDate birthdayDate;

    public String getName() { return name; }

    public LocalDate getBirthdayDate() { return birthdayDate; }

    public void setName(String authorName) { this.name = authorName;}

    public void setBirthdayDate(LocalDate birthdayDate) { this.birthdayDate = birthdayDate;    }

    public String toString(){
        return  this.name;
    }
}
