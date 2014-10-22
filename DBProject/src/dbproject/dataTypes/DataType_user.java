/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.dataTypes;

import java.util.Date;

/**
 *
 * @author Viral
 */
public class DataType_user {
    
    public String user_id;
    public String user_name;
    public String user_password;
    public String user_type;
    
    //For TA
    public String course_id;
    public Date valid_till;
    
    //For Professor
    public String designation;
    
    //For Student
    public String student_type;
}
