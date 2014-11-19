/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.dataType;
import dbproject.dbconnection.dbconnection_dbObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
/**
 *
 * @author Viral
 */
public class DataType_attempt {

    public Date atmpt_dt;
    public Integer atmpt_score;    
    public String student_id;
    public String assignment_id;
    public Date due_dt;
    public String assignment_name;

	//Connection to the database
    private Statement stmt = null;
    private ResultSet rs = null;

    public String getAssignment_name() {
        return assignment_name;
    }


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

    public DataType_attempt(String name,Date atmpt_dt, Integer atmpt_score, String student_id, String assignment_id, Date due_dt) {
        this.assignment_name = name; 
        this.atmpt_dt = atmpt_dt;
        this.atmpt_score = atmpt_score;
        this.student_id = student_id;
        this.assignment_id = assignment_id;
        this.due_dt = due_dt;
    }
    
    //Constructor that get data from DB
    public DataType_attempt(Date attemptDate, String studentID, String assignmentID){
        dbconnection_dbObject db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;
        
        //Get data from DB
        String query = "Select * "
                +"from attempt a "
                +"where a.student_id ='"+studentID+"' "
                +"and a.assignment_id='"+assignmentID+"' "
                +"and a.atmpt_dt = to_timestamp('"+attemptDate+"', 'yyyy-mm-dd HH24:MI:SS.FF') ";
               
        System.out.println("query get data from DB for attempt : "+ query);
        
        try {
            rs = stmt.executeQuery(query);

            if (rs.next()) {//attempt found
                this.student_id = studentID;
                this.assignment_id = assignmentID;
                this.atmpt_dt = attemptDate;
                this.atmpt_score = rs.getInt("atmpt_score");

            }
            else{
                System.out.println("Attempt not found");
            }
        }
        catch (Exception oops) {
            System.out.println("WARNING - DataType_attempt_answer - DataType_attempt(Date attemptDate, String studentID, String assignmentID) -get data from DB for attempt : " + oops); 
        }
    
    }
}
