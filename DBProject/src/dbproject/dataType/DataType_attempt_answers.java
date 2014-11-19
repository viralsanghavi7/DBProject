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
public class DataType_attempt_answers {
    public String student_id;
    public String assignment_id;
    public Date atmpt_dt;
    public String atmpt_ques_id;
    public String atmpt_answer_id;
    public Integer atmpt_answer_seq_no;
    public String atmpt_answer_selected;
    public String atmpt_answer_context;
    public String atmpt_answer_text;
    public String answer_state;
    public String answer_explanation;
    
    //Connection to the database
    private Statement stmt = null;
    private ResultSet rs = null;
    
    public DataType_attempt_answers()
    {}
    
    //Constructor
    public DataType_attempt_answers(Date attemptDate, String studentID, String assignmentID, String attemptQuestionID, String attemptAnswerID){
        dbconnection_dbObject db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;
        
        //Get data from DB
        /*
        String query = "Select a.*, b.answer_short_expl, b. "
                +" from attempt_answers a, answer b "
                +" where a.atmpt_answer_id = b.answer_id "
                +" and a.student_id ='"+studentID+"' "
                +" and a.assignment_id='"+assignmentID+"' "
                +" and a.atmpt_dt = to_timestamp('"+attemptDate+"', 'yyyy-mm-dd HH24:MI:SS.FF') "
                +" and a.atmpt_ques_id='"+attemptQuestionID+"' "
                +" and a.atmpt_answer_id ='"+attemptAnswerID+"' ";
        System.out.println("query get data from DB for attemptAnswer : "+ query);
        */
        String query = "Select aa.*, q.ques_isdynamic, a.answer_value, a.answer_short_expl "
                +" from attempt_answers aa, answer a, attempt_question aq, question q "
                +" where aa.atmpt_answer_id = a.answer_id and aq.atmpt_ques_id = q.question_id "
                +" and aa.atmpt_dt = aq.atmpt_dt"
                +" and aa.student_id = aq.student_id"
                +" and aa.assignment_id = aq.assignment_id"
                +" and aa.atmpt_ques_id = aq.atmpt_ques_id"
                +" and aa.student_id ='"+studentID+"' "
                +" and aa.assignment_id='"+assignmentID+"' "
                +" and aa.atmpt_dt = to_timestamp('"+attemptDate+"', 'yyyy-mm-dd HH24:MI:SS.FF') "
                +" and aa.atmpt_ques_id='"+attemptQuestionID+"' "
                +" and aa.atmpt_answer_id ='"+attemptAnswerID+"' ";
        try {
            rs = stmt.executeQuery(query);

            if (rs.next()) {//attempt found
                this.student_id = studentID;
                this.assignment_id = assignmentID;
                this.atmpt_dt = attemptDate;
                this.atmpt_ques_id = attemptQuestionID;
                this.atmpt_answer_id = attemptAnswerID;
                this.atmpt_answer_seq_no= rs.getInt("atmpt_answer_seq_no");
                this.atmpt_answer_selected = rs.getString("atmpt_answer_selected");
                this.atmpt_answer_context = rs.getString("atmpt_answer_context");
                this.answer_state = rs.getString("atmpt_answer_state");
                this.answer_explanation = rs.getString("answer_short_expl");
                if ((rs.getString("ques_isdynamic")).equals("F"))
                    this.atmpt_answer_text = rs.getString("answer_value");
                else
                    this.atmpt_answer_text = this.atmpt_answer_context;
            }
            else{
                System.out.println("Attempt not found");
            }
        }
        catch (Exception oops) {
            System.out.println("WARNING - DataType_attempt_answer - DataType_attempt_answers(Date attemptDate, String studentID, String assignmentID, String attemptQuestionID, String attemptAnswerID) -get data from DB for attemptAnswer : " + oops); 
        }
    
    }
    
    
    
    
    public static void main(String args[]) {
       Date date = new Date();
       new DataType_attempt_answers(date,"paulID", "assignID", "attQID", "attAnswerID");
    }
    
}
