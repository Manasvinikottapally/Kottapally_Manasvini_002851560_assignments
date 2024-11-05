/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.CourseSchedule;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kal bugrara
 */
public class CourseOffer {

    Course course;
    ArrayList<Seat> seatlist;
    FacultyAssignment facultyassignment;

    public FacultyAssignment getFacultyassignment() {
        return facultyassignment;
    }
    
    

    public CourseOffer(Course c) {
        course = c;
        seatlist = new ArrayList();
    }
    
    public void AssignAsTeacher(FacultyProfile professor) {
    this.facultyassignment = new FacultyAssignment(professor, this); // Store the faculty assignment in CourseOffer
}

// Add a getter for FacultyProfile in CourseOffer to retrieve the professor
    public FacultyProfile getFacultyProfile() {
        return (this.facultyassignment != null) ? this.facultyassignment.getFacultyProfile() : null;
    }
     
//    

    public String getCourseNumber() {
        return course.getCOurseNumber();
    }

    public void generatSeats(int n) {

        for (int i = 0; i < n; i++) {

            seatlist.add(new Seat(this, i));

        }

    }

    public Seat getEmptySeat() {

        for (Seat s : seatlist) {

            if (!s.isOccupied()) {
                return s;
            }
        }
        return null;
    }


    public SeatAssignment assignEmptySeat(CourseLoad cl) {

        Seat seat = getEmptySeat();
        if (seat == null) {
            return null;
        }
        SeatAssignment sa = seat.newSeatAssignment(cl); //seat is already linked to course offer
        cl.registerStudent(sa); //coures offer seat is now linked to student
        return sa;
    }

    public int getTotalCourseRevenues() {

        int sum = 0;

        for (Seat s : seatlist) {
            if (s.isOccupied() == true) {
                sum = sum + course.getCoursePrice();
            }

        }
        return sum;
    }
    public Course getSubjectCourse(){
        return course;
    }
    public int getCreditHours(){
        return course.getCredits();
    }
    
    public List<Seat> getSeatList() {
        return seatlist;
    }

    public FacultyProfile getAssignedFaculty() {
    return facultyassignment != null ? facultyassignment.getFacultyProfile() : null;
}

    

    
}
