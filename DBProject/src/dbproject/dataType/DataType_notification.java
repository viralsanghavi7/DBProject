package dbproject.dataType;

import dbproject.dbconnection.dbconnection_dbObject;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Viral
 */
public class DataType_notification {
    
    private String _notification_id;
    private String _notification_text;
    private String _user_id;
    private String _visible;
    private String _course_id;    
    //Connection to the database
    private Statement stmt = null;
    private ResultSet rs = null;
        

    public DataType_notification(String notification_id) {
        
        dbconnection_dbObject db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;
        
        
        String query = "SELECT * "
                        +"FROM notificationTest n "
                        +"WHERE n.notification_id = '" + notification_id + "'";
        
        System.out.println("query to get the data related to the notification "+notification_id+" : "+ query);
        try {
            rs = stmt.executeQuery(query);

            if (!rs.next()) {//the DB is not found the course
                System.out.println("The notification with the ID "+notification_id+" is not recorded in our Database.");
                
            }
            else{
                //get notification_id
                this._notification_id = notification_id;
		
                //get notification_text
                String notificationText = rs.getString("notification_text");
                this._notification_text = notificationText;
		
                //get user_id
                String userID = rs.getString("user_ID");
                this._user_id = userID;
                
                //get visible
                String visible = rs.getString("visible");
                this._visible = visible;
                
                //get course_id
                String courseID = rs.getString("course_id");
                this._course_id = courseID;
                
                this.printContent();
            }

        }
        catch (Exception oops) {
            System.out.println("WARNING - DataType_notification - DataType_notification(String notification_id) - get data from the notification "+notification_id+" : "+ oops); 
        }
    }
    


    //print the content of all the data
    public void printContent(){
        System.out.println("DataType_notification  "+_notification_id);
        System.out.println("text : "+_notification_text);
        System.out.println("visible : "+_visible);
        System.out.println("userID : "+_user_id);
        System.out.println("courseID : "+_course_id);
    }

    public String getCourse_id() {
        return _course_id;
    }

    public String getNotification_id() {
        return _notification_id;
    }

    public String getNotification_text() {
        return _notification_text;
    }

    public String getUser_id() {
        return _user_id;
    }

    public String getVisible() {
        return _visible;
    }
    
    

    public void setCourse_id(String course_id) {
        this._course_id = course_id;
        //TODO : change notificationTest with notification
        String query = "UPDATE notificationTest n "
                    +"SET n.course_id= '"+this._course_id+"' "
                    +"WHERE n.notification_id = '" + _notification_id + "'";

        System.out.println("query to update of the courseID related to the notification "+this._notification_id+" : "+ query);
        System.out.println("WARNING - DataType_notification - setCourse_id(String course_id) - Effectiveness of the function not certified");
        
        try {
            rs = stmt.executeQuery(query);
        }
        catch (Exception oops) {
            System.out.println("WARNING - DataType_notification - setCourse_id(String course_id) - set courseID of the notification "+_notification_id+" : "+ oops); 
        }
    }

    public void setNotification_id(String notification_id) {
        this._notification_id = notification_id;
        //TODO : change notificationTest with notification
        String query = "UPDATE notificationTest n "
                    +"SET n.notification_id= '"+this._notification_id+"' "
                    +"WHERE n.notification_id = '" + _notification_id + "'";

        System.out.println("query to update of the notificationID related to the notification "+this._notification_id+" : "+ query);
        System.out.println("WARNING - DataType_notification - setNotification_id(String notification_id) - Effectiveness of the function not certified");
        
        try {
            rs = stmt.executeQuery(query);
        }
        catch (Exception oops) {
            System.out.println("WARNING - DataType_notification - setNotification_id(String notification_id) - set notificationID of the notification "+_notification_id+" : "+ oops); 
        }
    }

    public void setNotification_text(String notification_text) {
        this._notification_text = notification_text;
        //TODO : change notificationTest with notification
        String query = "UPDATE notificationTest n "
                    +"SET n.notification_text= '"+this._notification_text+"' "
                    +"WHERE n.notification_id = '" + _notification_id + "'";

        System.out.println("query to update of the notificationText related to the notification "+this._notification_id+" : "+ query);
        System.out.println("WARNING - DataType_notification - setNotification_text(String notification_text) - Effectiveness of the function not certified"); 
        
        try {
            rs = stmt.executeQuery(query);
        }
        catch (Exception oops) {
            System.out.println("WARNING - DataType_notification - setNotification_text(String notification_text) - set notificationtext of the notification "+_notification_id+" : "+ oops); 
        }
    }

    public void setUser_id(String user_id) {
        this._user_id = user_id;
        //TODO : change notificationTest with notification
        String query = "UPDATE notificationTest n "
                    +"SET n.user_id= '"+this._user_id+"' "
                    +"WHERE n.notification_id = '" + _notification_id + "'";

        System.out.println("query to update of the userID related to the notification "+this._notification_id+" : "+ query);
        System.out.println("WARNING - DataType_notification - setUser_id(String user_id) - Effectiveness of the function not certified"); 
        
        try {
            rs = stmt.executeQuery(query);
        }
        catch (Exception oops) {
            System.out.println("WARNING - DataType_notification - setUser_id(String user_id) - set notificationtext of the notification "+_notification_id+" : "+ oops); 
        }
    }

    public void setVisible(String visible) {//T for make it visble, F for hiden
        if ((visible.equals("T")) || (visible.equals("F"))){
            this._visible = visible;
            //TODO : change notificationTest with notification
            String query = "UPDATE notificationTest n "
                        +"SET n.visible= '"+this._visible+"' "
                        +"WHERE n.notification_id = '" + _notification_id + "'";

            System.out.println("query to update of the visiblity related to the notification "+this._notification_id+" : "+ query);
            try {
                rs = stmt.executeQuery(query);
            }
            catch (Exception oops) {
                System.out.println("WARNING - DataType_notification - setVisible(String visible) - set visibility of the notification "+_notification_id+" : "+ oops); 
            }
        }
        else {
            System.out.println("WARNING - DataType_notification - setVisible(String visible) - Wrong parameter value for the notification "+_notification_id+" :");
        }
            
    }

    
    
}


// TODO : setvisible