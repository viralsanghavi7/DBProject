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
public class DataType_course {
    
    public String course_id;
    public String  course_name;
    public String course_level;
    Date course_start_dt = new Date();
    Date course_end_dt= new Date();
    public Integer no_of_students_enrolled;
    public Integer max_students_allowed;
}
