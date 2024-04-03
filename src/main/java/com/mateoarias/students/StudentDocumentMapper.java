package com.mateoarias.students;

import org.bson.Document;

class StudentDocumentMapper {

  private StudentDocumentMapper() {}

  static Document studentAsMongoDocument(Student student) throws IllegalArgumentException {
    if (student == null) throw new IllegalArgumentException("Student must not be null");

    var document = new Document();

    document.append(StudentCollectionFields.FIRST_NAME, student.firstName());
    document.append(StudentCollectionFields.LAST_NAME, student.lastName());
    document.append(StudentCollectionFields.EMAIL, student.email());
    document.append(StudentCollectionFields.GENDER, student.gender());
    document.append(StudentCollectionFields.PHONE_NUMBER, student.phoneNumber());

    return document;
  }

  static Student mongoDocumentAsStudent(Document document) {
    if (document == null) throw new IllegalArgumentException("Document must not be null");

    return new Student(
        document.get(StudentCollectionFields.FIRST_NAME, String.class),
        document.get(StudentCollectionFields.LAST_NAME, String.class),
        document.get(StudentCollectionFields.EMAIL, String.class),
        document.get(StudentCollectionFields.GENDER, String.class),
        document.get(StudentCollectionFields.PHONE_NUMBER, String.class));
  }
}
