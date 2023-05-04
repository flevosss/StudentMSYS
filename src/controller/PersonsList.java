package controller;

import model.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * A class to hold all the details of the database.
 * It has different methods to query the students, and their results.
 */
public class PersonsList {

    private final HashMap<String, Person> studentDatabase; //holds all the students with their student numbers.
    private final HashMap<Education, Set<Person>> educationSetMap; //holds the students at the specified education.

    public PersonsList() {
        this.studentDatabase = new HashMap<>();
        this.educationSetMap = new HashMap<>();

        educationSetMap.put(Education.GYMNASIUM, new HashSet<>());
        educationSetMap.put(Education.LYCEUM, new HashSet<>());
        educationSetMap.put(Education.UNIVERSITY, new HashSet<>());
    }

    /**
     * Adds a student to the database.
     * @param name of the student.
     * @param surname of the student.
     * @param studentNumber of the student taken by the getNewStudentNumber method.
     * @param school of the student.
     * @param year of the student.
     * @param faculty of the student (only for university students).
     */
    public void addStudent(String name, String surname, String studentNumber, String school, int year, String faculty) {
        switch (studentNumber.charAt(0)){
            //checks the first digit of the student number to determine their level of education.
            case 'G':
                PupilGymnasium pupilGymnasium = new PupilGymnasium(name, surname, studentNumber, school, year);

                studentDatabase.put(studentNumber,pupilGymnasium);
                educationSetMap.get(Education.GYMNASIUM).add(pupilGymnasium);
                break;
            case 'L':
                PupilLyceum pupilLyceum = new PupilLyceum(name, surname, studentNumber, school, year);

                studentDatabase.put(studentNumber,pupilLyceum);
                educationSetMap.get(Education.LYCEUM).add(pupilLyceum);
                break;
            case 'U':
                PupilUniversity pupilUniversity = new PupilUniversity(name, surname, studentNumber, school, faculty, year);

                studentDatabase.put(studentNumber, pupilUniversity);
                educationSetMap.get(Education.UNIVERSITY).add(pupilUniversity);
                break;
            default:
                System.out.println("Something went !really! wrong.");
                break;
        }
    }

    /**
     * Displays the results of the student.
     * The results can be obtained either by name/surname either by student number.
     * @param name the name of the student to be searched along with the surname.
     * @param surname the surname of the student to be searched along with the name.
     * @param studentNumber the student number of the student.
     */
    public void displayResults(String name, String surname, String studentNumber) {
        //checking the persons in the database and checking their values.
        for (Person value: studentDatabase.values()) {
            if ((value.getName().equals(name) && value.getSurname().equals(surname))
                    || value.getStudentNumber().equals(studentNumber)) {
                value.write();
            }
        }
    }

    /**
     * Sets the grades of the students.
     * @param studentNumber of the student.
     */
    public void addGrades(String studentNumber) {
        studentDatabase.get(studentNumber).setGrade();
    }

    /**
     * Returns the average of the class of the student.
     * It only checks for the same year as the student, so it does not matter
     * if two students study at a different school.
     * @param studentNumber the student number of the student to return the average of the class.
     * @return the average of that class (different schools but same class, counts as well).
     */
    public double getClassAverage(String studentNumber) {
        double total = 0;
        double people = 0;
        int year;

        //No need to check of university student because we do so
        //in getUnivAverage() function.
        if (studentDatabase.get(studentNumber) instanceof PupilGymnasium) {
            year = ((PupilGymnasium) studentDatabase.get(studentNumber)).getYear();
        } else {
            year = ((PupilLyceum) studentDatabase.get(studentNumber)).getYear();
        }

        for (String sNumber : studentDatabase.keySet()) {
            if (studentDatabase.get(sNumber) instanceof PupilGymnasium
                && ((PupilGymnasium) studentDatabase.get(sNumber)).getYear() == year) {
                total +=studentDatabase.get(sNumber).getAverageGrade();
                people++;
            } else if (studentDatabase.get(sNumber) instanceof PupilLyceum
                && ((PupilLyceum) studentDatabase.get(sNumber)).getYear() == year) {
                total +=studentDatabase.get(sNumber).getAverageGrade();
                people++;
            } else {
                continue;
            }
        }
        return total/people;
    }

    /**
     * This method calculates the average of a certain faculty for a university student.
     * @param sNumber the student number of the student to get the faculty.
     * @return the average of that faculty.
     */
    public double getUnivAverage(String sNumber) {
        double total = 0;
        double people = 0;

        PupilUniversity student = (PupilUniversity) studentDatabase.get(sNumber);
        String studentsFaculty = student.getFaculty();

        for (String key : studentDatabase.keySet()) {
            if (studentDatabase.get(key) instanceof PupilUniversity &&
                    ((PupilUniversity) studentDatabase.get(key)).getFaculty().equals(studentsFaculty)) {
                total+=studentDatabase.get(key).getAverageGrade();
                people++;
            }
        }
        return total/people;
    }

    /**
     * This method checks if a student is registered in the database.
     * It can work with student number, or with name and surname.
     * @param name the name of the student to be searched.
     * @param surname the surname of the student to be searched.
     * @param studentNumber the student number of the student to be searched.
     * @return true if the database contains the student, false otherwise.
     */
    public boolean containsStudent(String name, String surname, String studentNumber) {
        if  (studentDatabase.containsKey(studentNumber)) return true;
        for (Person person : studentDatabase.values()) {
            if (person.getName().equals(name) && person.getSurname().equals(surname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns a list of students (with their names),  of all the education levels.
     * @return the list of students.
     */
    public String getListOfStudents() {
        StringBuilder result = new StringBuilder();

        for (var entry : educationSetMap.entrySet()) {
            switch (entry.getKey()) {
                case GYMNASIUM:
                    if (entry.getValue().size() == 0) {
                        result.append("Gymnasium students: No students ");
                        continue;
                    }

                    result.append("Gymnasium students: ");
                    for (Person person : entry.getValue()) {
                        result.append(person.getName()).append(" ");
                    }
                    break;
                case LYCEUM:
                    if (entry.getValue().size() == 0) {
                        result.append("\nLyceum students: No students ");
                        continue;
                    }

                    result.append("\nLyceum students: ");
                    for (Person person : entry.getValue()) {
                        result.append(person.getName()).append(" ");
                    }
                    break;
                case UNIVERSITY:
                    if (entry.getValue().size() == 0) {
                        result.append("\nUniversity students: No students ");
                        continue;
                    }

                    result.append("\nUniversity students: ");
                    for (Person person : entry.getValue()) {
                        result.append(person.getName()).append(" ");
                    }
                    break;
            }
        }
        return result.toString();
    }

    /**
     * This method removes a student from the database.
     * @param studentNumber the student number of the student.
     */
    public void removePerson(String studentNumber) {
        studentDatabase.remove(studentNumber);
        switch (studentNumber.charAt(0)){
            case 'G':
                educationSetMap.get(Education.GYMNASIUM).remove(studentDatabase.get(studentNumber));
                break;
            case 'L':
                educationSetMap.get(Education.LYCEUM).remove(studentDatabase.get(studentNumber));
                break;
            case 'U':
                educationSetMap.get(Education.UNIVERSITY).remove(studentDatabase.get(studentNumber));
                break;
        }
    }

    /**
     * This method is called whenever a new student wants to register in the database.
     * It checks the level of education and appends a letter according to the education
     * at the beginning of a random number.
     * @param education the level of education of the current student. It can be Gymnasium, Lyceum or University.
     * @return a new student number for the student.
     */
    public String getNewStudentNumber(Education education) {
        StringBuilder result = new StringBuilder();

        switch (education){
            case GYMNASIUM:
                result.append("G");
                break;
            case LYCEUM:
                result.append("L");
                break;
            case UNIVERSITY:
                result.append("U");
                break;
        }
        result.append(new Random().nextInt(5000));

        //check if this number is already in the database
        if (studentDatabase.containsKey(result.toString())){
            getNewStudentNumber(education);
        }
        return result.toString();
    }
}