package com.example.james.seniorproject;

import java.util.Date;

public class Assignment {
    String title;
    String contentDescription;
    Date creationDate;
    // should be null if and only if user doesn't select a due date
    Date dueDate;

    public Assignment(String title, String contentDescription, Date creationDate, Date dueDate) {
        this.title = title;
        this.contentDescription = contentDescription;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
    }
}
