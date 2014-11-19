/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.dataType;
import java.util.Date;

/**
 *
 * @author Viral
 */
public class DataType_assignment {
    
    public String assignment_id;
    public String assignment_name;
    public Integer assignment_difficulty;
    public Integer number_of_retries;
    public Integer random_seed;
    public Integer penalty_points;
    public Integer correct_points;
    public Date start_dt= new Date();
    public Date end_dt= new Date();
    public Integer score_selection_method;
    public String course_id;
    public String professor_id;
    public Integer number_of_questions;
    private String student_id;
    private int number;
    
    public DataType_assignment()
    {}

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
    

    public int getNumber_of_questions() {
        return number_of_questions;
    }

    public String getAssignment_id() {
        return assignment_id;
    }

    public String getAssignment_name() {
        return assignment_name;
    }

    public Integer getAssignment_difficulty() {
        return assignment_difficulty;
    }

    public Integer getNumber_of_retries() {
        return number_of_retries;
    }

    public Integer getRandom_seed() {
        return random_seed;
    }

    public Integer getPenalty_points() {
        return penalty_points;
    }

    public Integer getCorrect_points() {
        return correct_points;
    }

    public Date getStart_dt() {
        return start_dt;
    }

    public Date getEnd_dt() {
        return end_dt;
    }

    public Integer getScore_selection_method() {
        return score_selection_method;
    }

    public String getCourse_id() {
        return course_id;
    }

    public String getProfessor_id() {
        return professor_id;
    }
    

    public DataType_assignment(int num,Integer random, String assignment_id, String assignment_name, Integer assignment_difficulty, Integer number_of_retries, Integer penalty_points, Integer correct_points,Date start_dt, Date end_dt,  Integer score_selection_method, String course_id, String professor_id) {
        this.assignment_id = assignment_id;
        this.assignment_name = assignment_name;
        this.assignment_difficulty = assignment_difficulty;
        this.number_of_retries = number_of_retries;
        this.penalty_points = penalty_points;
        this.correct_points = correct_points;
        this.score_selection_method = score_selection_method;
        this.course_id = course_id;
        this.professor_id = professor_id;
        this.start_dt = start_dt;
        this.end_dt = end_dt;
        this.random_seed = random;
        this.number_of_questions = num;
    }
    
}
