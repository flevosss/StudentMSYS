package model;

import controller.PersonsList;
import view.DatabaseUI;

/**
 * This class represents a Lyceum student.
 */
public class PupilLyceum extends Person {

    private final String school;
    private final int year;
    private final int[] quarterGrades;

    public PupilLyceum (String name, String surname, String studentNumber, String school, int year) {
        super(name, surname, studentNumber);
        this.school = school;
        this.year = year;
        quarterGrades = new int[2];
    }

    @Override
    public void write() {
        System.out.println("Name " + super.getName());
        System.out.println("Surname: " + super.getSurname());
        System.out.println("Student number: " + super.getStudentNumber());
        System.out.println("School: " + school);
        System.out.println("Year: " + year);
        System.out.println("First term grade: " + quarterGrades[0]);
        System.out.println("Second term grade: " + quarterGrades[1]);
        System.out.println("Class average: " + new PersonsList().getClassAverage(super.getStudentNumber()));
    }

    @Override
    public void setGrade() {
        System.out.println("What is your first term grade? ");
        quarterGrades[0] = DatabaseUI.scanner.nextInt();

        System.out.println("What is your second term grade? ");
        quarterGrades[1] = DatabaseUI.scanner.nextInt();

        System.out.println("Grades successfully updated");
    }

    @Override
    public double getAverageGrade() {
        return (double) (quarterGrades[0] + quarterGrades[1])/2;
    }

    public int getYear(){
        return this.year;
    }
}
