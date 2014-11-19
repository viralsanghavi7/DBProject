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
        

    //creation of a notification for user userID, related to a course courseID and that contains the text notificationText
    public DataType_notification(String userID, String courseID, String notificationText) {
        //generate a notificationID that is not already inside the DB
        int sizeMaxNotificationID = 20;
        int sizeMaxNotificationText = 100;
        
        dbconnection_dbObject db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;
        
        
        String query;
        StringBuffer buffer = new StringBuffer();
        boolean existanceNotificationIDInDB = false;
        String notificationID = null;
                
        while (existanceNotificationIDInDB == false){
            for (int i = 0; i < sizeMaxNotificationID; i++) {
                int value = (int) ((Math.random() * 10) % 10);
                buffer.append(value);
            }
            notificationID = buffer.toString();
            System.out.println(notificationID);

            //check if this notificationID is not already used in the db
            query = "SELECT * "
                    +"FROM notification n "
                    +"WHERE n.notification_id = '" + notificationID + "'";
            System.out.println("query to check if notification "+notificationID+" not in DB : "+ query);
        
            try {
                rs = stmt.executeQuery(query);

                if (!rs.next()) {//this notification id is not used in the DB
                    existanceNotificationIDInDB = true;

                }
            }
            catch (Exception oops) {
                System.out.println("WARNING - DataType_notification - DataType_notificationString userID, String courseID, String notificationText) - check if notificationID "+notificationID+" already exist: "+ oops); 
            }
        }
        
        //check if the text is not too long
        if (notificationText.length() > sizeMaxNotificationText){
            notificationText.substring(0,sizeMaxNotificationText);//only the sizeMaxNotificationText first characters of notificationText remain
        }
        
        //creation of the notification in the database
        _user_id = userID;
        _notification_text = notificationText;
        _visible = "T";
        _course_id = courseID;
        query = "INSERT INTO notification(notification_id, notification_text, user_id, visible, course_id) "
                +"VALUES ('"+notificationID+"', '"+notificationText+"', '"+userID+"', 'T', '"+courseID+"')";
        System.out.println("query to insert the notification "+notificationID+" : "+ query);
        
        try {
            rs = stmt.executeQuery(query);
/*
            if (!rs.next()) {//this notification id is not used in the DB
                existanceNotificationIDInDB = true;

            }
*/
        }
        catch (Exception oops) {
            System.out.println("WARNING - DataType_notification - DataType_notificationString userID, String courseID, String notificationText) - query to insert the notification "+notificationID+" :  "+ oops); 
        } 
    }
    
    
    public DataType_notification(String notification_id) {
        
        dbconnection_dbObject db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;
        
        
        String query = "SELECT * "
                        +"FROM notification n "
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
        String query = "UPDATE notification n "
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
        String query = "UPDATE notification n "
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
        String query = "UPDATE notification n "
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
        String query = "UPDATE notification n "
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
            String query = "UPDATE notification n "
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