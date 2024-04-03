package com.mateoarias.students;

import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;

class StudentUpdates {

  private StudentUpdates() {}

  static Bson updateAllStudentFields(Student student) throws IllegalArgumentException {
    if (student == null) throw new IllegalArgumentException("Student must not be null");

    return Updates.combine(
        Updates.set(StudentCollectionFields.FIRST_NAME, student.firstName()),
        Updates.set(StudentCollectionFields.LAST_NAME, student.lastName()),
        Updates.set(StudentCollectionFields.EMAIL, student.email()),
        Updates.set(StudentCollectionFields.GENDER, student.gender()),
        Updates.set(StudentCollectionFields.PHONE_NUMBER, student.phoneNumber()));
  }
}
