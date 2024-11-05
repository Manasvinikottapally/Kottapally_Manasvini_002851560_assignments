/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example;

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
        
        CourseSchedule courseschedule = department.newCourseSchedule("Fall2020");

        CourseOffer courseoffer = courseschedule.newCourseOffer("info 5100");
        if (courseoffer==null)return;
        courseoffer.generatSeats(10);
        PersonDirectory pd = department.getPersonDirectory();
        Person person = pd.newPerson("0112303");
        StudentDirectory sd = department.getStudentDirectory();
        StudentProfile student = sd.newStudentProfile(person);
        CourseLoad courseload = student.newCourseLoad("Fall2020"); 
//        
        courseload.newSeatAssignment(courseoffer); //register student in class
        
        int total = department.calculateRevenuesBySemester("Fall2020");
        System.out.print("Total: " + total);

    }*/
    public static void main(String[] args) {
    // Initialize the Department and Course Catalog
    Department department = new Department("Information Systems");
    CourseCatalog courseCatalog = department.getCourseCatalog();
    
    // Set up Core and Elective Courses
    Course coreCourse1 = courseCatalog.newCourse("Application Engineering", "INFO 5100", 4); // Core
    Course electiveCourse1 = courseCatalog.newCourse("Web Development", "INFO 5200", 3); // Elective
    Course electiveCourse2 = courseCatalog.newCourse("Data Science", "INFO 5300", 4); // Elective
    
    // Add courses to degree requirements
    Degree degree = department.getDegree(); // The Degree instance from Department
    degree.addCoreCourse(coreCourse1);
    degree.addElectiveCourse(electiveCourse1);
    degree.addElectiveCourse(electiveCourse2);

    // Initialize Course Schedule for Fall2020
    CourseSchedule fall2020Schedule = department.newCourseSchedule("Fall2020");
    
    // Create Course Offers and generate seats
    CourseOffer appEngOffer = fall2020Schedule.newCourseOffer("INFO 5100");
    appEngOffer.generatSeats(10);
    CourseOffer webDevOffer = fall2020Schedule.newCourseOffer("INFO 5200");
    webDevOffer.generatSeats(10);
    CourseOffer dataSciOffer = fall2020Schedule.newCourseOffer("INFO 5300");
    dataSciOffer.generatSeats(10);

    // Initialize Person Directory and create Faculty
    FacultyDirectory facultyDirectory = department.getFacultyDirectory();
    PersonDirectory personDirectory = department.getPersonDirectory();
    
    // Create and Assign Professors
    FacultyProfile prof1 = facultyDirectory.newFacultyProfile(personDirectory.newPerson("P1"));
    FacultyProfile prof2 = facultyDirectory.newFacultyProfile(personDirectory.newPerson("P2"));
    prof1.AssignAsTeacher(appEngOffer);
    prof2.AssignAsTeacher(webDevOffer);
    
    // Initialize Student Directory and Register Students in Courses
    StudentDirectory studentDirectory = department.getStudentDirectory();
    
    for (int i = 1; i <= 10; i++) {
        Person person = personDirectory.newPerson("S" + i);
        StudentProfile student = studentDirectory.newStudentProfile(person);
        CourseLoad courseLoad = student.newCourseLoad("Fall2020");

        // Register students in core and elective courses
        courseLoad.newSeatAssignment(appEngOffer);
        courseLoad.newSeatAssignment(webDevOffer);
        courseLoad.newSeatAssignment(dataSciOffer);
        
        // Assign grades to each registered course
        for (SeatAssignment seat : courseLoad.getSeatAssignments()) {
            seat.setGrade(4.0f - (i % 3) * 0.7f); // Example grading (A, A-, B+ for variety)
           }
    }

    // Calculate and Display Revenue
    int totalRevenue = department.calculateRevenuesBySemester("Fall2020");
    System.out.println("Total Revenue for Fall 2020: $" + totalRevenue);

    // Generate and Print Student Report for Fall 2020
    System.out.println("Student Report for Fall 2020:");
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
            
            FacultyProfile professor = seat.getCourseOffer().getFacultyProfile();
            String professorName = professor != null ? professor.getPerson().getPersonId() : "TBA";

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
