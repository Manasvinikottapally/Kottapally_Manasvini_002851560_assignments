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
        List<String> professorNames = Arrays.asList("Dr. Smith", "Prof. Johnson", "Dr. Lee", "Prof. Brown", "Dr. Taylor",
                                                     "Prof. White", "Dr. Green", "Prof. Black", "Dr. Gray", "Prof. Blue");

        // Create Professors and assign them to a list
        List<FacultyProfile> professors = new ArrayList<>();
        for (String name : professorNames) {
            Person profPerson = personDirectory.newPerson(name);
            FacultyProfile profProfile = facultyDirectory.newFacultyProfile(profPerson);
            professors.add(profProfile);
        }

        // Define Courses (1 Core, 5+ Electives)
        System.out.println("Managing Course Catalog:");
        Course coreCourse = courseCatalog.newCourse("Application Engineering", "INFO 5100", 4); // Core course
        List<Course> electiveCourses = Arrays.asList(
            courseCatalog.newCourse("Web Development", "INFO 5200", 3),
            courseCatalog.newCourse("Data Science", "INFO 5300", 4),
            courseCatalog.newCourse("Database Management", "INFO 5400", 3),
            courseCatalog.newCourse("Cloud Computing", "INFO 5500", 4),
            courseCatalog.newCourse("Machine Learning", "INFO 5600", 3),
            courseCatalog.newCourse("Cyber Security", "INFO 5700", 3),  // Additional elective
            courseCatalog.newCourse("Artificial Intelligence", "INFO 5800", 4) // Additional elective
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

        // Create Course Schedule for Fall2020 and Add Course Offers for each course
        CourseSchedule fall2020Schedule = department.newCourseSchedule("Fall2020");
        List<CourseOffer> courseOffers = new ArrayList<>();
        courseOffers.add(fall2020Schedule.newCourseOffer("INFO 5100")); // Core course offer
        for (Course elective : electiveCourses) {
            courseOffers.add(fall2020Schedule.newCourseOffer(elective.getCOurseNumber()));
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
        printCourseSchedule(fall2020Schedule);

        // Register 10 Students, each registering for at least two courses
        StudentDirectory studentDirectory = department.getStudentDirectory();
        for (int i = 1; i <= 10; i++) {
            Person person = personDirectory.newPerson("S" + i);
            StudentProfile student = studentDirectory.newStudentProfile(person);
            CourseLoad courseLoad = student.newCourseLoad("Fall2020");

            // Register each student for at least two courses
            courseLoad.newSeatAssignment(courseOffers.get(i % courseOffers.size())); // First course
            courseLoad.newSeatAssignment(courseOffers.get((i + 1) % courseOffers.size())); // Second course
            for (SeatAssignment seat : courseLoad.getSeatAssignments()) {
            seat.setGrade(4.0f - (i % 3) * 0.7f); // Example grading (A, A-, B+ for variety)
           }
            System.out.println("Student S" + i + " registered for:");
            for (SeatAssignment seat : courseLoad.getSeatAssignments()) {
                FacultyProfile professor = seat.getCourseOffer().getAssignedFaculty();
                String professorName = (professor != null) ? professor.getPerson().getPersonId() : "TBA";
                System.out.println(" - Course: " + seat.getAssociatedCourse().getName() + ", Professor: " + professorName);
            }
        }

        // Calculate and Display Revenue
        int totalRevenue = department.calculateRevenuesBySemester("Fall2020");
        System.out.println("\nTotal Revenue for Fall 2020: $" + totalRevenue);

        // Generate and Print Student Report for Fall 2020
        printStudentReport(studentDirectory, degree);
    }

    // Method to browse and display all courses in the catalog
    public static void browseCourses(CourseCatalog courseCatalog) {
        System.out.println("\nBrowsing Course Catalog:");
        for (Course course : courseCatalog.getCourseList()) {
            System.out.println(" - Course Name: " + course.getName() + ", ID: " + course.getCOurseNumber() + ", Credits: " + course.getCredits());
        }
    }

    // Method to add a predefined course to the catalog
    public static void addPredefinedCourse(CourseCatalog courseCatalog) {
        Course newCourse = courseCatalog.newCourse("Software Engineering", "INFO 5900", 3);
        System.out.println("Predefined Course added successfully: " + newCourse.getName() + " (" + newCourse.getCOurseNumber() + "), Credits: " + newCourse.getCredits());

        // Display updated course catalog
        browseCourses(courseCatalog);
    }

    // Method to print the course schedule for the semester
    public static void printCourseSchedule(CourseSchedule courseSchedule) {
        System.out.println("\nCourse Offerings for Fall2020:");
        for (CourseOffer offer : courseSchedule.getSchedule()) {
            FacultyProfile assignedProfessor = offer.getAssignedFaculty();
            String professorName = (assignedProfessor != null) ? assignedProfessor.getPerson().getPersonId() : "TBA";

            System.out.println(" - Course: " + offer.getCourseNumber() +
                               ", Professor: " + professorName +
                               ", Seats Available: " + offer.getSeatList().size());
        }
    }

    // Method to print the student report for the semester
    public static void printStudentReport(StudentDirectory studentDirectory, Degree degree) {
        System.out.println("\nStudent Report for Fall 2020:");
        for (StudentProfile student : studentDirectory.getStudentList()) {
            System.out.println("Student ID: " + student.getPerson().getPersonId());
            System.out.println("Courses, Professors, Grades, and Fees:");

            float totalCredits = 0;
            float totalWeightedScore = 0;
            int totalTuition = 0;

            for (SeatAssignment seat : student.getTranscript().getCourseList()) {
                Course course = seat.getAssociatedCourse();
                String courseName = course.getName();
                int credits = course.getCredits();
                float grade = seat.getGrade();
                

                int courseFee = course.getCoursePrice();

                FacultyProfile professor = seat.getCourseOffer().getAssignedFaculty();
                String professorName = (professor != null) ? professor.getPerson().getPersonId() : "TBA";

                System.out.println("Course: " + courseName +
                               ", Credits: " + credits +
                               ", Grade: " + grade +
                               ", Professor: " + professorName +
                               ", Fee: $" + courseFee);

                totalWeightedScore += grade * credits;
                totalCredits += credits;
                totalTuition += courseFee;
            }

            // Calculate GPA
            float gpa = totalCredits > 0 ? totalWeightedScore / totalCredits : 0;
            System.out.println("GPA: " + gpa);
            System.out.println("Total Tuition Fees: $" + totalTuition);
            
            // Graduation Eligibility Check
             boolean readyToGraduate = degree.isStudentReadyToGraduate(student);
             System.out.println("Ready to Graduate: " + (readyToGraduate ? "Yes" : "No"));
             System.out.println("-------------");

            
        }
    }

}

  

