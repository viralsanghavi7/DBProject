/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.dataType;

import dbproject.dbconnection.dbconnection_dbObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Viral
 */
public class DataType_courseAction {
    //Attributes
    private Statement stmt = null;
    private ResultSet rs = null;
    public DataType_user userObj;
    private String _courseID;
    private String _courseName;
    private String _courseLevel;
    private Date _courseStartDt;
    private Date _courseEndDt;
    private int _noOfSudentEnrolled;
    private int _maxStudentAllowed;
    
    
    
    //Creator - give in parameter the id of the course
    public DataType_courseAction(String pCourseID){
        //creation of the db connection
        dbconnection_dbObject db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;
        
        //Get all the data related to the course that the course_id is pCourseID
        String query = "SELECT * from course c where c.course_id = '"+pCourseID+"'";
        System.out.println(query);
        
        try {
            rs = stmt.executeQuery(query);
        
            if (!rs.next()) {//the DB is not found the course
                System.out.println("The course with the ID "+pCourseID+" is not recorded in our Database.");
                
            }
            else{
                //get course_id
                String courseID = rs.getString("course_id");
                this._courseID = courseID;
		
                //get course_name
                String courseName = rs.getString("course_name");
                this._courseName = courseName;
		
                //get course_level
                String courseLevel = rs.getString("course_level");
                this._courseLevel = courseLevel;
                
                //get course_start_dt
                Date courseStartDt = rs.getDate("course_start_dt");
                this._courseStartDt = courseStartDt;
                
                //get course_start_dt
                Date courseEndDt = rs.getDate("course_end_dt");
                this._courseEndDt = courseEndDt;
                
                //get no_of_students_enrolled
                int noOfStudentsEnrolled = rs.getInt("no_of_students_enrolled");
                this._noOfSudentEnrolled = noOfStudentsEnrolled;
                
                //get course_strt_dt
                int maxStudentsAllowed = rs.getInt("max_students_allowed");
                this._maxStudentAllowed = maxStudentsAllowed;
                
                this.printContent();
            }

        }
        catch (Exception oops) {
            System.out.println("WARNING - DataType_courseAction - DataType_courseAction(String pCourseID) - get all data of the course "+pCourseID+" : "+ oops); 
        }
    }
    
    //print the content of all the data
    public void printContent(){
        System.out.println("DataType_courseAction of "+_courseID);
        System.out.println("id : "+_courseID);
        System.out.println("name : "+_courseName);
        System.out.println("level : "+_courseLevel);
        System.out.println("startDate : "+_courseStartDt);
        System.out.println("endDate : "+_courseEndDt);
        System.out.println("noOfStudentEnrolled : "+_noOfSudentEnrolled);
        System.out.println("maxStudentAllowed : "+_maxStudentAllowed);
        
    }
    
    
    
   // public DataType_course courseObj;

    public int getMaxStudentAllowed() {
        return _maxStudentAllowed;
    }

    public int getNoOfSudentEnrolled() {
        return _noOfSudentEnrolled;
    }

    public String getCourseID() {
        return _courseID;
    }

    public String getCourseName() {
        return _courseName;
    }

    public Date getCourseEndDt() {
        return _courseEndDt;
    }

    public Date getCourseStartDt() {
        return _courseStartDt;
    }

    public String getCourseLevel() {
        return _courseLevel;
    }

    public DataType_user getUserObj() {
        return userObj;
    }
    
    public DataType_course getCourseObj() {
        DataType_course obj = new DataType_course();
        obj.course_id = this._courseID;
        obj.course_end_dt = this._courseEndDt;
        obj.course_level = this._courseLevel;
        obj.course_name = this._courseName;
        obj.course_start_dt = this._courseStartDt;
        obj.max_students_allowed = this._maxStudentAllowed;
        obj.no_of_students_enrolled = this._noOfSudentEnrolled;
        
        return obj;
    }
    
    
}
