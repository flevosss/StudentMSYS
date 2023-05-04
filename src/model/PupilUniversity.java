package model;

import view.DatabaseUI;

import java.util.ArrayList;

/**
 * This class represent a University student.
 */
public class PupilUniversity extends Person {

    private final String university;
    private final String faculty;
    private final int year; //in which year this student is currently on
    private final ArrayList<Integer> grades; // all the grades of the students.

    public PupilUniversity(String name, String surname, String studentNumber, String university, String faculty, int year) {
        super(name, surname, studentNumber);
        this.university = university;
        this.faculty = faculty;
        this.year = year;
        grades = new ArrayList<>();
    }

    @Override
    public void write() {
        System.out.println("Name: " + super.getName());
        System.out.println("Surname: " + super.getSurname());
        System.out.println("Student number: " + super.getStudentNumber());
        System.out.println("University: " + university);
        System.out.println("Faculty: " + faculty);
        System.out.println("Current Year: " + year);

        for (int i = 1; i <= year ; i++) {
            System.out.println("Your average grade for  " + i + " year " + "is: " + grades.get(i-1));
        }
        System.out.println("Your GPA is : " + getAverageGrade());
    }


    @Override
    public void setGrade() {
        for (int i = 1; i <= year; i++) {
            System.out.println("What is your average grade of " + i + " year: ");
            grades.add(DatabaseUI.scanner.nextInt());
        }
        System.out.println("Grades successfully updated");
    }

    @Override
    public double getAverageGrade() {
        int result = 0;

        for (int grade: grades){
            result+=grade;
        }
        return (double) result/grades.size();
    }

    /**
     * Returns the faculty of the student.
     * @return faculty.
     */
    public String getFaculty() {
        return faculty;
    }
}