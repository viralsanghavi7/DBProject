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
public class dataTypes_assignment {
    
    public String assignment_id;
    public String assignment_name;
    public Integer assignment_difficulty;
    public Integer number_of_retries;
    public Integer random_seed;
    public Integer penalty_points;
    public Integer correct_points;
    Date start_dt= new Date();
    Date end_dt= new Date();
    public Integer score_selection_method;
    public String course_id;
    public String professor_id;
}
