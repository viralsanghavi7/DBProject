package dbproject.dataType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dbproject.dbconnection.dbconnection_dbObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


/**
 *
 * @author Viral
 */
public class DataType_attemptQuestion {
    public String student_id;
    public String assignment_id;
    public Date atmpt_dt = new Date();
    public Integer atmpt_ques_seq_no;
    public String atmpt_ques_id;
    public String atmpt_ques_text;
    public String atmpt_ques_explaination;
    
//Connection to the database
    private Statement stmt = null;
    private ResultSet rs = null;
    
    public DataType_attemptQuestion()
    {}
    
    
    //Constructor
    public DataType_attemptQuestion(Date attemptDate, String studentID, String assignmentID, String attemptQuestionID){
        dbconnection_dbObject db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;
        
        //Get data from DB
        /*
        String query = "Select * "
                +" from attempt_question a "
                +" where a.student_id ='"+studentID+"' "
                +" and a.assignment_id='"+assignmentID+"' "
                +" and a.atmpt_dt = to_timestamp('"+attemptDate+"', 'yyyy-mm-dd HH24:MI:SS.FF') "
                +" and a.atmpt_ques_id='"+attemptQuestionID+"' ";
                */
        
        String query = "Select aq.*, q.ques_text, q.ques_isdynamic, q.ques_long_explanation  "
                +" from attempt_question aq, question q "
                + "where aq.atmpt_ques_id = q.question_id "
                +" and aq.student_id ='"+studentID+"' "
                +" and aq.assignment_id='"+assignmentID+"' "
                +" and aq.atmpt_dt = to_timestamp('"+attemptDate+"', 'yyyy-mm-dd HH24:MI:SS.FF') "
                +" and aq.atmpt_ques_id='"+attemptQuestionID+"' ";
        
        System.out.println("query get data from DB for attemptQuestion : "+ query);
        
        try {
            rs = stmt.executeQuery(query);

            if (rs.next()) {//attempt found
                this.student_id = studentID;
                this.assignment_id = assignmentID;
                this.atmpt_dt = attemptDate;
                this.atmpt_ques_id = attemptQuestionID;
                this.atmpt_ques_seq_no= rs.getInt("atmpt_ques_seq_no");
                this.atmpt_ques_text = rs.getString("atmpt_ques_text");
                this.atmpt_ques_explaination = rs.getString("ques_long_explanation");
            }
            else{
                System.out.println("AttemptQuestion not found");
            }
        }
        catch (Exception oops) {
            System.out.println("WARNING - DataType_attempt_answer - DataType_attemptQuestion(Date attemptDate, String studentID, String assignmentID, String attemptQuestionID) -get data from DB for attemptQuestion : " + oops); 
        }
    
    }



}
