package view;

import controller.PersonsList;
import model.Education;

import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseUI {

    public static final Scanner scanner = new Scanner(System.in);
    private final ArrayList<String> faculties;

    /*
    variable to keep track if the user has entered
    admin mode to access certain commands.
     */
    private static boolean adminGranted = false;

    /**
     * Main method used just to run the program.
     * @param args ignored.
     */
    public static void main(String[] args) {
       new DatabaseUI();
    }

    /**
     * Constructor of this class, also initializes the
     * scanner and faculties data structure.
     */
    public DatabaseUI(){
        this.faculties = new ArrayList<>();
        handleInput();
    }

    private void handleInput(){
        showUserHelpMenu();
        PersonsList personsList = new PersonsList();

        int choice;
        while(scanner.hasNext()) {
            System.out.print("Enter your next choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored){
                System.out.println("Please select a numeric option");
                continue;
            }
            try {
                switch (choice) {
                    case 0:
                        System.out.println("Enter admin password");
                        String password = scanner.nextLine();

                        if (password.equals("admin")) {
                            adminGranted = true;
                            System.out.println("Access granted");
                        } else {
                            System.out.println("Wrong password");
                            //they have to log in again with the correct one.
                            adminGranted = false;
                        }
                        break;
                    case 1:
                        enterStudent(Education.GYMNASIUM, personsList);
                        System.out.println();
                        break;
                    case 2:
                        enterStudent(Education.LYCEUM, personsList);
                        System.out.println();
                        break;
                    case 3:
                        enterStudent(Education.UNIVERSITY, personsList);
                        System.out.println();
                        break;
                    case 9:
                        if  (adminGranted) {
                            System.out.println(personsList.getListOfStudents() + "\n");
                        } else {
                            System.out.println("You do not have permissions for this command unless logged in with admin mode!");
                        }
                        break;
                    case 4:
                        System.out.println("Enter the first name of the student: ");
                        String firstName = scanner.nextLine();

                        System.out.println("Enter the last name of the student: ");
                        String lastName = scanner.nextLine();

                        if (personsList.containsStudent(firstName,lastName,null)){
                            personsList.displayResults(firstName,lastName,null);
                            System.out.println();
                        } else {
                            System.out.println("There is no known student with this full name. Please make your request again\n");
                        }
                        break;
                    case 5:
                        System.out.println("Enter the student number: ");
                        String sNumber1 = scanner.nextLine();

                        if (personsList.containsStudent(null,null,sNumber1)){
                            personsList.displayResults(null,null,sNumber1);
                            System.out.println();
                        } else {
                            System.out.println("There is no known student with this number. Please make your request again\n");
                        }
                        break;
                    case 10:
                        if (adminGranted) {
                            System.out.println("Enter the student number: ");
                            String sNumber2 = scanner.nextLine();

                            if (personsList.containsStudent(null, null, sNumber2)) {
                                personsList.addGrades(sNumber2);
                                System.out.println();
                            } else {
                                System.out.println("No student with this credentials found. Please make your request again\n");
                            }
                        } else {
                            System.out.println("You do not have permissions for this command unless logged in with admin mode!");
                        }
                        break;
                    case 6:
                        System.out.println("Enter the student number: ");
                        String sNumber3 = scanner.nextLine();
                        if  (sNumber3.charAt(0) == 'U') {
                            System.out.println("This is a university student! Please use option 7");
                            break;
                        }
                        if (personsList.containsStudent(null,null, sNumber3)){
                            System.out.println("Class average: " + personsList.getClassAverage(sNumber3) + "\n");
                        } else {
                            System.out.println("No student with this credentials found. Please make your request again\n");
                        }
                        break;
                    case 7:
                        System.out.println("Enter the (University) student number: ");
                        String sNumber4 = scanner.nextLine();

                        if (personsList.containsStudent(null,null, sNumber4)){
                            System.out.println("Class average: " + personsList.getUnivAverage(sNumber4) + "\n");
                        } else {
                            System.out.println("No student with this credentials found. Please make your request again\n");
                        }
                        break;
                    case 11:
                        if (adminGranted){
                            System.out.println("Please enter a new faculty or leave blank to stop ");
                            //assuming the admin will not enter wrong values for the faculties.
                            //assuming all universities in the database has the same faculties.
                            while (true){
                                String faculty = scanner.nextLine();
                                if(faculty.isEmpty()){
                                    break;
                                } else {
                                    System.out.println(faculty.toUpperCase() + " has been successfully added");
                                    faculties.add(faculty.toUpperCase());
                                }
                            }
                        } else {
                            System.out.println("You do not have permissions for this command unless logged in with admin mode!");
                        }
                        break;
                    case 12:
                        if (adminGranted){
                            String facultyToRemove = scanner.nextLine();
                            if  (!faculties.contains(facultyToRemove.toUpperCase())){
                                System.out.println("This faculty is not registered yet. Select option 11 to insert a faculty");
                                break;
                            }
                            faculties.remove(facultyToRemove.toUpperCase());
                            System.out.println("Successfully removed " + facultyToRemove + " from the database");
                        } else {
                            System.out.println("You do not have permission to remove a faculty");
                        }
                        break;
                    case 13:
                        if (adminGranted) {
                            System.out.println("Enter the student number: ");
                            String number = scanner.nextLine();

                            if (!personsList.containsStudent(null, null, number)) {
                                System.out.println("No student with this credentials found. Please make your request again\n");
                            } else {
                                personsList.removePerson(number);
                                System.out.println("Student successfully removed\n");
                            }
                        } else {
                            System.out.println("You do not have the permission to delete a student");
                        }
                        break;
                    case 8:
                        if  (adminGranted){
                            showAdminHelpMenu();
                        } else {
                            showUserHelpMenu();
                        }
                        break;
                    default:
                        if  (adminGranted){
                            System.out.println("Select one of the options 1-8. Use 8 for help menu.");
                        } else {
                            System.out.println("Select one of the options 1-13. Use 8 for help menu.");
                        }
                        break;
                }
            } catch (Exception ignored) {
                System.out.println("There was an error with your request\n");
            }
        }
    }

    /**
     * Method to add a student to the database. It first asks the name and surname,
     * and then checking which education is this student following, and returns a new
     * student number for this student.
     * @param education the level of education of the student.
     * @param personsList the object that holds the database.
     */
    private void enterStudent(Education education, PersonsList personsList){
        boolean addToDB = false;

        System.out.println("Please type the name of the student ");
        String name = scanner.nextLine();

        System.out.println("Please type the surname of the student ");
        String surName = scanner.nextLine();

        String school; //school for students /university for uni students.
        int year; //which year the student is currently studying.
        String faculty = null; //for the university student.

        if (education == Education.UNIVERSITY){
            System.out.println("Please type the name of the university of the student");
            school = scanner.nextLine();

            System.out.println("Please type in the year that the student is currently on");
            while(true) {
                String yearToCheck = scanner.nextLine();
                try {
                    year = Integer.parseInt(yearToCheck);
                    break;
                } catch (NumberFormatException ignored) {
                    System.out.println("Please enter a numeric value of which year the " +
                            "student is currently studying on");
                }
            }

            System.out.println("Please type in the faculty of your study");
            faculty = scanner.nextLine().toUpperCase();

            if  (!faculties.contains(faculty)) {
                System.out.println("This faculty is currently not registered in the database. Please contact your admin");
                System.out.println("The available faculties to register are: " + faculties);
            } else {
                addToDB = true;
            }
        } else {
            System.out.println("Please type the name of the school of the student");
            school = scanner.nextLine();

            System.out.println("Please type in the year that the currently is on (1 for first, 2 for second, 3 for third)");
            while (true) {
                String yearToCheck = scanner.nextLine();
                try {
                    year = Integer.parseInt(yearToCheck);
                    if (year == 1 || year == 2 || year == 3) {
                        addToDB = true;
                        break;
                    }
                    System.out.println("Please type 1 for first, 2 for second, 3 for third year");
                } catch (NumberFormatException ignored) {
                    System.out.println("Please enter a numeric value of which year the " +
                            "student is currently studying on");
                }
            }
        }
        if (addToDB) {
            String studentNumber = personsList.getNewStudentNumber(education);
            personsList.addStudent(name, surName, studentNumber, school, year, faculty);

            System.out.println("Successful! Your student number is " + studentNumber);
        }
    }

    private void showUserHelpMenu(){
        if (adminGranted) {
            System.out.println("Select one of the options below 1-13: \n");
        } else {
            System.out.println("Select one of the options below 1-8: \n");
        }

        System.out.println("0-> Enter admin mode");
        System.out.println("1-> Enter a new Gymnasium student");
        System.out.println("2-> Enter a new Lyceum Student");
        System.out.println("3-> Enter a new University student");
        System.out.println("4-> Get the details of a student by their first and last name");
        System.out.println("5-> Get the details of a student by their student number");
        System.out.println("6-> Get the average of the school (Gymnasium/Lyceum) of the student");
        System.out.println("7-> Get the average of the faculty (University) of the student");
        System.out.println("8-> Show help menu");
    }

    private void showAdminHelpMenu() {
        showUserHelpMenu();
        System.out.println("9-> [Admin command] Get students in the database");
        System.out.println("10-> [Admin command] Set grades of a student");
        System.out.println("11-> [Admin command] Add university faculties");
        System.out.println("12-> [Admin command] Delete a university faculty");
        System.out.println("13-> [Admin command] Delete a student");
    }
}
