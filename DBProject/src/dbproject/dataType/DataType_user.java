
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

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getCourse_id() {
        return course_id;
    }

    public Date getValid_till() {
        return valid_till;
    }

    public String getDesignation() {
        return designation;
    }

    public String getStudent_type() {
        return student_type;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


