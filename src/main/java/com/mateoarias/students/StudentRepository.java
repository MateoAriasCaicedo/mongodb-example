package com.mateoarias.students;

import com.mateoarias.db.DBCollections;
import com.mateoarias.db.DBNames;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import java.util.Optional;
import org.bson.Document;
import org.bson.types.ObjectId;

class StudentRepository {

  private final MongoClient mongoClient;

  StudentRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  void createStudent(Student student) {
    if (student == null) throw new IllegalArgumentException("Student cannot be null");

    var studentDocument = StudentDocumentMapper.studentAsMongoDocument(student);

    mongoClient
        .getDatabase(DBNames.UNIVERSITY_DB)
        .getCollection(DBCollections.STUDENT_COLLECTION)
        .insertOne(studentDocument);
  }

  boolean removeStudent(ObjectId studentID) throws IllegalArgumentException {
    var equalIdFilter = StudentFilters.equalIDFilter(studentID);

    var deletedCount =
        mongoClient
            .getDatabase(DBNames.UNIVERSITY_DB)
            .getCollection(DBCollections.STUDENT_COLLECTION)
            .deleteOne(equalIdFilter)
            .getDeletedCount();

    return deletedCount == 1; // As the id is unique (UUID), the deletion must be <= 1
  }

  Optional<Student> findStudent(ObjectId studentID) {

    FindIterable<Document> matchedStudent =
        mongoClient
            .getDatabase(DBNames.UNIVERSITY_DB)
            .getCollection(DBCollections.STUDENT_COLLECTION)
            .find(StudentFilters.equalIDFilter(studentID));

    try (var studentIterable = matchedStudent.iterator()) {

      if (studentIterable.hasNext()) {
        // As the ID is unique, we only expect one document in the iterable
        return Optional.of(StudentDocumentMapper.mongoDocumentAsStudent(studentIterable.next()));
      }

      return Optional.empty();
    }
  }

  boolean updateStudent(ObjectId studentID, Student student) throws IllegalArgumentException {
    if (findStudent(studentID).isEmpty()) return false;

    mongoClient
        .getDatabase(DBNames.UNIVERSITY_DB)
        .getCollection(DBCollections.STUDENT_COLLECTION)
        .updateOne(
            StudentFilters.equalIDFilter(studentID),
            StudentUpdates.updateAllStudentFields(student));

    return true;
  }
}
