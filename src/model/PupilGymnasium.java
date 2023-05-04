package model;

import controller.PersonsList;
import view.DatabaseUI;

/**
 * This class represents a Gymnasium student.
 */
public class PupilGymnasium extends Person {

    private final String school;
    private final int year;
    private final int[] semesterGrades;

    public PupilGymnasium(String name, String surname, String studentNumber, String school , int year) {
        super(name, surname, studentNumber);
        this.school = school;
        this.year = year;
        this.semesterGrades = new int[3];
    }

    @Override
    public void write() {
        System.out.println("Name: " + super.getName());
        System.out.println("Surname: " + super.getSurname());
        System.out.println("Student number: " + super.getStudentNumber());
        System.out.println("School: " + school);
        System.out.println("Year: " + year);
        System.out.println("First semester grade: " + semesterGrades[0]);
        System.out.println("Second semester grade: " + semesterGrades[1]);
        System.out.println("Third semester grade: " + semesterGrades[2]);
        System.out.println("Class average: " + new PersonsList().getClassAverage(super.getStudentNumber()));
    }

    @Override
    public void setGrade() {
        System.out.println("What was your first semester grade: ");
        semesterGrades[0] = DatabaseUI.scanner.nextInt();

        System.out.println("What was your second semester grade: ");
        semesterGrades[1] = DatabaseUI.scanner.nextInt();

        System.out.println("What was your third semester grade: ");
        semesterGrades[2] = DatabaseUI.scanner.nextInt();

        System.out.println("Grades successfully updated");
    }

    @Override
    public double getAverageGrade() {
        return (double) (semesterGrades[0] + semesterGrades[1] + semesterGrades[2])/3;
    }

    public int getYear(){
        return this.year;
    }
}
