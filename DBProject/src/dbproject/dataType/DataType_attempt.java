/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.dataType;
import java.util.*;
/**
 *
 * @author Viral
 */
public class DataType_attempt {

Date atmpt_dt;
public Integer atmpt_score;    
public String student_id;
public String assignment_id;
public Date due_dt;

    public Date getAtmpt_dt() {
        return atmpt_dt;
    }

    public Integer getAtmpt_score() {
        return atmpt_score;
    }

    public String getStudent_id() {
        return student_id;
    }

    public String getAssignment_id() {
        return assignment_id;
    }

    public Date getDue_dt() {
        return due_dt;
    }

    public DataType_attempt(Date atmpt_dt, Integer atmpt_score, String student_id, String assignment_id, Date due_dt) {
        this.atmpt_dt = atmpt_dt;
        this.atmpt_score = atmpt_score;
        this.student_id = student_id;
        this.assignment_id = assignment_id;
        this.due_dt = due_dt;
    }


    
}
