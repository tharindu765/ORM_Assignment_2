package org.example;

import org.example.config.FactoryConfiguration;
import org.example.entity.Address;
import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        /*===============================================
         * Insert Operation - Insert Student
         ================================================*/
        Student student1 = new Student(1, "kirito", null);
        Student student2 = new Student(2, "sinhabahu", null);

        session.save(student1);
        session.save(student2);

        System.out.println("Students Inserted!");

        /*===============================================
         * Insert Operation - Insert Address
         ================================================*/
        Address address1 = new Address(101, student1, "serupita", "kalutara");
        Address address2 = new Address(102, student2, "nigeria", "colombo");

        session.save(address1);
        session.save(address2);

        System.out.println("Addresses Inserted!");

        /*===============================================
         * Search Operation - Search Student by ID
         ================================================*/
        Query<Student> searchStudentByIdQuery = session.createQuery("FROM Student WHERE id = :id", Student.class);
        searchStudentByIdQuery.setParameter("id", 1);
        List<Student> studentList = searchStudentByIdQuery.getResultList();

        for (Student student : studentList) {
            System.out.println("Found Student: " + student.getId() + " - " + student.getName());
        }

        /*===============================================
         * Search Operation - Search Address by ID
         ================================================*/
        Query<Address> searchAddressByIdQuery = session.createQuery("FROM Address WHERE Aid = :Aid", Address.class);
        searchAddressByIdQuery.setParameter("Aid", 101);
        List<Address> addressList = searchAddressByIdQuery.getResultList();

        for (Address address : addressList) {
            System.out.println("Found Address: " + address.getAid() + " - " + address.getAddress1());
        }

        /*===============================================
         * Update Operation - Update Student
         ================================================*/
        Query updateStudentQuery = session.createQuery("UPDATE Student SET name = :name WHERE id = :id");
        updateStudentQuery.setParameter("name", "tharindu");
        updateStudentQuery.setParameter("id", 1);
        int studentUpdateResult = updateStudentQuery.executeUpdate();

        if (studentUpdateResult > 0) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student not found!");
        }

        /*===============================================
         * Update Operation - Update Address
         ================================================*/
        Query updateAddressQuery = session.createQuery("UPDATE Address SET address1 = :address1 WHERE Aid = :Aid");
        updateAddressQuery.setParameter("address1", "bolossagama");
        updateAddressQuery.setParameter("Aid", 101);
        int addressUpdateResult = updateAddressQuery.executeUpdate();

        if (addressUpdateResult > 0) {
            System.out.println("Address updated successfully!");
        } else {
            System.out.println("Address not found!");
        }

        /*===============================================
         * Delete Operation - Delete Address
         ================================================*/
        Query deleteAddressQuery = session.createQuery("DELETE FROM Address WHERE Aid = :Aid");
        deleteAddressQuery.setParameter("Aid", 102);
        int addressDeleteResult = deleteAddressQuery.executeUpdate();

        if (addressDeleteResult > 0) {
            System.out.println("Address deleted successfully!");
        } else {
            System.out.println("Address not found!");
        }

        /*===============================================
         * Delete Operation - Delete Student
         ================================================*/
        Query deleteStudentQuery = session.createQuery("DELETE FROM Student WHERE id = :id");
        deleteStudentQuery.setParameter("id", 2);
        int studentDeleteResult = deleteStudentQuery.executeUpdate();

        if (studentDeleteResult > 0) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }

        /*===============================================
         * Join Operation - Fetch Student with Addresses
         ================================================*/
        Query<Object[]> joinQuery = session.createQuery(
                "SELECT s.id, s.name, a.Aid, a.address1 FROM Student s JOIN s.address a"
        );

        List<Object[]> joinResultList = joinQuery.getResultList();

        for (Object[] result : joinResultList) {
            System.out.println("Student ID: " + result[0] + ", Name: " + result[1] + ", Address ID: " + result[2] + ", Address: " + result[3]);
        }

        // Commit the transaction and close the session
        transaction.commit();
        session.close();
    }
}
