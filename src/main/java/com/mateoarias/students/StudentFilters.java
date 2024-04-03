package com.mateoarias.students;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

class StudentFilters {

  private StudentFilters() {}

  static Bson equalIDFilter(ObjectId studentID) throws IllegalArgumentException {
    if (studentID == null) throw new IllegalArgumentException("id cannot be null");
    return Filters.eq(StudentCollectionFields.ID, studentID);
  }
}
