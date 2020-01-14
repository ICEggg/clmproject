package org.clm.javaproject.lambada;

public class StudentTest {
    public static void main(String[] args) {
        IStudent iStudent = new IStudent() {
            @Override
            public void insert(Student student) {
                System.out.println(student.getName());
            }
        };

        Student student = new Student("aaa",10,100);
        iStudent.insert(student);



    }
}
