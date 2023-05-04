package model;


/**
 * Class to represent a person.
 */
public abstract class Person implements IWrite {

    private final String name;
    private final String surname;
    private final String studentNumber;

    public Person(String name, String surname, String studentNumber) {
        this.name = name;
        this.surname = surname;
        this.studentNumber = studentNumber;
    }

    /**
     * This method is called to write to the output
     * details about this student.
     */
    @Override
    public abstract void write();
    
    /**
     * This method calculates the average grade of the student.
     * @return the average grade of the student
     */
    public abstract double getAverageGrade();

    /**
     * This method asks for the grades of the student to add them to the
     * database.
     */
    public abstract void setGrade();

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getStudentNumber() {
        return studentNumber;
    }
}
