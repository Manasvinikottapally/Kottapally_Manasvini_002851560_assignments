/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Degree.Degree;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.Faculty.FacultyDirectory;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;
import static java.lang.Math.round;

/**
 *
 * @author kal bugrara
 */
public class Info5001UniversityExample {

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        // TODO code application logic here
        Department department = new Department("Information Systems");
        CourseCatalog coursecatalog = department.getCourseCatalog();
        
        Course course = coursecatalog.newCourse("app eng", "info 5100", 4);
        
        CourseSchedule courseschedule = department.newCourseSchedule("Fall2024");

        CourseOffer courseoffer = courseschedule.newCourseOffer("info 5100");
        if (courseoffer==null)return;
        courseoffer.generatSeats(10);
        PersonDirectory pd = department.getPersonDirectory();
        Person person = pd.newPerson("0112303");
        StudentDirectory sd = department.getStudentDirectory();
        StudentProfile student = sd.newStudentProfile(person);
        CourseLoad courseload = student.newCourseLoad("Fall2024"); 
//        
        courseload.newSeatAssignment(courseoffer); //register student in class
        
        int total = department.calculateRevenuesBySemester("Fall2024");
        System.out.print("Total: " + total);

    }*/
    
    public static void main(String[] args) {
        // Initialize the Department and Course Catalog
        Department department = new Department("Information Systems");
        CourseCatalog courseCatalog = department.getCourseCatalog();
        PersonDirectory personDirectory = department.getPersonDirectory();
        FacultyDirectory facultyDirectory = department.getFacultyDirectory();

        // Define Professor Names
        List<String> professorNames = Arrays.asList("Dr. Smith", "Prof. Johnson", "Dr. Lee", "Prof.Naveen", "Dr.vishal",
                                                     "Prof. White", "Dr. Green", "Prof. Black", "Dr.khal", "Prof. Blue");

        // Create Professors and assign them to a list
        List<FacultyProfile> professors = new ArrayList<>();
        for (String name : professorNames) {
            Person profPerson = personDirectory.newPerson(name);
            FacultyProfile profProfile = facultyDirectory.newFacultyProfile(profPerson);
            professors.add(profProfile);
        }

        // Define Courses (1 Core, 5+ Electives)
        System.out.println("MANAGING COURSE CATALOG:");
        Course coreCourse = courseCatalog.newCourse("Application Engineering", "INFO 5100", 4); // Core course
        List<Course> electiveCourses = Arrays.asList(
            courseCatalog.newCourse("Web Development", "INFO 5200", 3),
            courseCatalog.newCourse("Data Science", "INFO 5300", 4),
            courseCatalog.newCourse("Database Management", "DAMG 5400", 3),// Additional elective
            courseCatalog.newCourse("Cloud Computing", "INFO 5500", 4),
            courseCatalog.newCourse("Machine Learning", "DAMG 5600", 3),// Additional elective
            courseCatalog.newCourse("Cyber Security", "CSYE 5700", 3),  // Additional elective
            courseCatalog.newCourse("Artificial Intelligence", "CSYE 5800", 4) // Additional elective
        );

        // Set up Degree Requirements
        Degree degree = department.getDegree();
        degree.addCoreCourse(coreCourse); // Set core course
        for (Course elective : electiveCourses) {
            degree.addElectiveCourse(elective); // Add each elective course
        }

        // Browse and Display Course Catalog
        browseCourses(courseCatalog);

        // Add a predefined new course to the catalog without user input
        addPredefinedCourse(courseCatalog);

        // Create Course Schedule for Fall2024 and Add Course Offers for each course
        CourseSchedule fall2024Schedule = department.newCourseSchedule("Fall2024");
        List<CourseOffer> courseOffers = new ArrayList<>();
        courseOffers.add(fall2024Schedule.newCourseOffer("INFO 5100")); // Core course offer
        for (Course elective : electiveCourses) {
            courseOffers.add(fall2024Schedule.newCourseOffer(elective.getCOurseNumber()));
        }

        // Generate seats for each course offer and assign professors
        int professorIndex = 0;
        for (CourseOffer offer : courseOffers) {
            offer.generatSeats(10); // Each class has 10 seats
            FacultyProfile professor = professors.get(professorIndex % professors.size());
            offer.AssignAsTeacher(professor); // Assign a single professor
            
            System.out.println("Assigned Professor " + professor.getPerson().getPersonId() + " to " + offer.getCourseNumber());
            professorIndex++;
        }

        // Print Full Course Schedule
        printCourseSchedule(fall2024Schedule);

        // Register 10 Students, each registering for at least two courses
        StudentDirectory studentDirectory = department.getStudentDirectory();
        for (int i = 1; i <= 10; i++) {
            Person person = personDirectory.newPerson("Student_" + i);
            StudentProfile student = studentDirectory.newStudentProfile(person);
            CourseLoad courseLoad = student.newCourseLoad("Fall2024");

            // Register each student for at least two courses
            courseLoad.newSeatAssignment(courseOffers.get(i % courseOffers.size())); // First course
            courseLoad.newSeatAssignment(courseOffers.get((i + 1) % courseOffers.size())); // Second course
            System.out.println("\nLIST OF STUDENTS WHO REGISTERED ");
            for (SeatAssignment seat : courseLoad.getSeatAssignments()) {
            seat.setGrade(4.0f - (i % 3) * 0.7f); // Example grading (A, A-, B+ for variety)
           }
            System.out.println("StudentID= Student_" + i + " registered for:");
            for (SeatAssignment seat : courseLoad.getSeatAssignments()) {
                FacultyProfile professor = seat.getCourseOffer().getAssignedFaculty();
                String professorName = (professor != null) ? professor.getPerson().getPersonId() : "TBA";
                System.out.println(" - Course: " + seat.getAssociatedCourse().getName() + ", Professor: " + professorName);
            }
        }

        // Calculate and Display Revenue
        int totalRevenue = department.calculateRevenuesBySemester("Fall2024");
        System.out.println("\nTotal Revenue for Fall 2024: $" + totalRevenue);

        // Generate and Print Student Report for Fall 2024
        printStudentReport(studentDirectory, degree);
    }

    // Method to browse and display all courses in the catalog
    public static void browseCourses(CourseCatalog courseCatalog) {
        System.out.println("\nBROWSING COURSE CATALOG:");
        for (Course course : courseCatalog.getCourseList()) {
            System.out.println(" - Course Name: " + course.getName() + ", ID: " + course.getCOurseNumber() + ", Credits: " + course.getCredits());
            
        }
        System.out.println("  ");
    }

    // Method to add a predefined course to the catalog
    public static void addPredefinedCourse(CourseCatalog courseCatalog) {
        
        System.out.println("  ");
        
        System.out.println("ADDITIONAL COURSES ADDED");
        
        Course newCourse = courseCatalog.newCourse("Software Engineering", "INFO 5900", 3);
        System.out.println("Predefined Course added successfully: " + newCourse.getName() + " (" + newCourse.getCOurseNumber() + "), Credits: " + newCourse.getCredits());
        
        Course newCourse1 = courseCatalog.newCourse("Network Analysis", "TELE 7245", 4);
        System.out.println("Predefined Course added successfully: " + newCourse1.getName() + " (" + newCourse1.getCOurseNumber() + "), Credits: " + newCourse.getCredits());

        // Display updated course catalog
        browseCourses(courseCatalog);
    }

    // Method to print the course schedule for the semester
    public static void printCourseSchedule(CourseSchedule courseSchedule) {
        System.out.println("\nCOURSE OFFERINGS FOR Fall2024:");
        for (CourseOffer offer : courseSchedule.getSchedule()) {
            FacultyProfile assignedProfessor = offer.getAssignedFaculty();
            String professorName = (assignedProfessor != null) ? assignedProfessor.getPerson().getPersonId() : "TBA";

            System.out.println(" - Course: " + offer.getCourseNumber() +
                               ", Professor: " + professorName +
                               ", Seats Available: " + offer.getSeatList().size());
        }
        System.out.println("  ");
    }

    // Method to print the student report for the semester
    public static void printStudentReport(StudentDirectory studentDirectory, Degree degree) {
        System.out.println("\nStudent Report for Fall 2024:");
        for (StudentProfile student : studentDirectory.getStudentList()) {
            System.out.println("Student ID: " + student.getPerson().getPersonId());
            //System.out.println("Courses, Professors, Grades, and Fees:");

            float totalCredits = 0;
            float totalWeightedScore = 0;
            int totalTuition = 0;

            for (SeatAssignment seat : student.getTranscript().getCourseList()) {
                Course course = seat.getAssociatedCourse();
                String courseName = course.getName();
                int credits = course.getCredits();
                float grade = seat.getGrade();
                String letterGrade = seat.getLetterGrade();
                

                int courseFee = course.getCoursePrice();

                FacultyProfile professor = seat.getCourseOffer().getAssignedFaculty();
                String professorName = (professor != null) ? professor.getPerson().getPersonId() : "TBA";

                System.out.println("Course: " + courseName); 
                System.out.println ("Credits: " + credits);
                System.out.println("Grade: " + letterGrade);
                System.out.println("Professor: " + professorName);
                System.out.println("Fee: $" + courseFee);
                System.out.println("....................");

                totalWeightedScore += grade * credits;
                totalCredits += credits;
                totalTuition += courseFee;
                
            }

            // Calculate GPA
            float gpa = totalCredits > 0 ? totalWeightedScore / totalCredits : 0;
            System.out.println("GPA: " + round(gpa));
            System.out.println("Total Tuition Fees: $" + totalTuition);
            System.out.println("*********************************************");

            
        }
    }

}

  

