/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.Student;

import java.text.SimpleDateFormat;
import dbproject.dataType.*;
import dbproject.Professor.Prof_Edit_HW;
import dbproject.Professor.Prof_Notification;
import dbproject.Professor.Prof_Report;
import dbproject.Professor.Prof_View_HW;
import dbproject.WelcomeScreen;
import dbproject.dbconnection.dbconnection_dbObject;
import javax.swing.ButtonModel;
import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.GroupLayout.*;
import java.util.*;
import java.sql.PreparedStatement;
/**
 *
 * @author Chetan
 */
public class Student_StartAttempt extends JFrame implements ActionListener {

    private ResultSet rs = null;
    private int number;
    private DataType_question[] list_question;
    private DataType_answer[][] answers;
    private ButtonGroup[] buttonGroup;
    private DataType_assignment assignment;
    private dbconnection_dbObject db;
    private Statement stmt;
    private DataType_courseAction courseObj;
    /**
     * Creates new form MainScreen
     */
    public Student_StartAttempt(DataType_assignment assignment, DataType_courseAction InputcourseObj) {
        courseObj = InputcourseObj;
        initComponents();
        this.assignment = assignment;
        db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;
        number = assignment.getNumber_of_questions();
        int seed = assignment.getRandom_seed();
        ArrayList<String> list = new ArrayList<String>();
        
        list_question = new DataType_question[number];
        answers = new DataType_answer[number][4];
        try{
            
            String id = assignment.getAssignment_id();
            String query = "select question_id from chosen_question where assignment_id='"+id+"'";
            rs = stmt.executeQuery(query);        
            while(rs.next()){
                list.add(rs.getString(1));
            }
             
            //Choosing questions, question_id is stored in array
            String[] questions = new String[number];
            for(int i=0;i<number;i++){
                Random random = new Random();
                int j = random.nextInt(list.size())+1;
                 
                questions[i] = list.get(j-1);
                list.remove(j-1);
            }
             
            //retrieving questions from db
//            ArrayList<DataType_question> list_question = new ArrayList<DataType_question>();
            for(int i=0;i<number;i++){
                String query1 = "select * from question where question_id='"+questions[i]+"'";
                rs = stmt.executeQuery(query1);
                rs.next();
                String question_id = rs.getString(1);
                String ques_text = rs.getString(2);
                
                String ques_hint = rs.getString(4);
                String isDynamic = rs.getString(6);
                String ques_long = rs.getString(7);
                DataType_question question = new DataType_question(question_id,ques_text,ques_hint,isDynamic,ques_long);
                list_question[i] = question;
            }
            
            String[] question_text = new String[number];   
//            String[][] answers = new String[number][4];
           
            
            for(int j=0;j<number;j++){
                DataType_question question = list_question[j];
                if(question.getQuestion_isdynamic().equals("F")){
                    
                    question_text[j]=""+j+1+". "+question.getQuestion_text();
                    //answer
                    ArrayList<DataType_answer> answer_list = new ArrayList<DataType_answer>();
                    String query4 = "select * from answer where question_id='"+question.getQuestion_id()+"'";
                    rs = stmt.executeQuery(query4);
                    
                    while(rs.next()){
                        String answer_id = rs.getString(1);
                        String question_id = rs.getString(2);
                        String answer_state = rs.getString(3);
                        String answer_value = rs.getString(4);
                        String answer_short_expl = rs.getString(5);
                        DataType_answer answer = new DataType_answer(answer_id,question_id,answer_state,answer_value,answer_short_expl);
                        answer_list.add(answer);
                    }
                    System.out.println("`````````````````````"+question.getQuestion_id());
                    
                    int c = 0;
                    int w = 0;
                    DataType_answer[] answer_show = new DataType_answer[4];
                    DataType_answer c1 = null;
                    while(true){
                        Random random = new Random();
                        int n = random.nextInt(answer_list.size());
                        System.out.println(""+n+"~"+answer_list.get(n).getAnswer_state());
                        if(answer_list.get(n).getAnswer_state().equals("W")&&w<3){
                            answer_show[w] = answer_list.get(n);
                            answer_list.remove(n);
                            w++;
                        }
                        else if(answer_list.get(n).getAnswer_state().equals("W")&&w==3)
                            answer_list.remove(n);                      
                        else if(answer_list.get(n).getAnswer_state().equals("C")&&c<1){
                            c1 = answer_list.get(n);
                            answer_list.remove(n);
                            c++;
                        }
                        else if(answer_list.get(n).getAnswer_state().equals("C")&&c==1)
                            answer_list.remove(n);  
                        if(w==3&&c==1)
                            break;
                    }
                   
                    System.out.println("`````````````````````aaa");
                    answer_show[3] = c1;
                    int m = new Random().nextInt(4)+1;
                    answer_show[3] = answer_show[m-1];
                    answer_show[m-1] = c1;
                    for(int a=0;a<4;a++){
                        answers[j][a] = answer_show[a];
                        System.out.println(answer_show[a].getAnswer_value());}
                    
                }
                else {
                    question_text[j]=""+j+1+". "+question.getQuestion_text();
                    ArrayList<String> comb = new ArrayList<String>();
                    String query2 = "select combination_id from question_parameters where question_id='"+question.getQuestion_id()+"' group by combination_id";
                    rs = stmt.executeQuery(query2);
                    while(rs.next()){
                        comb.add(rs.getString(1));
                    }
                    Random r = new Random();
                    int x = r.nextInt(comb.size());
                    String comb_id = comb.get(x);
                    String query3 = "select variable_name,variable_value from question_parameters where combination_id='"+comb_id+"' and question_id='"+question.getQuestion_id()+"'";
                    rs = stmt.executeQuery(query3);
                    while(rs.next()){
                        String parameter_name = rs.getString(1);
                        String parameter_value = rs.getString(2);
                        //question_text[j]+=","+rs.getString(1)+":"+rs.getString(2);
                        question_text[j] = question_text[j].replaceFirst(parameter_name, parameter_name + ":" +parameter_value);
                    }
                    list_question[j].setQuestion_text(question_text[j]);
                    //answer
                    ArrayList<DataType_answer> answer_list = new ArrayList<DataType_answer>();
                    String query5 = "select a.*, d.answer_value as dynamic_values from answer a, dynamic_answer d "
                            + " where a.answer_id = d.answer_id "
                            + " and d.combination_id = '"+comb_id + "' "
                            + " and a.question_id='"+question.getQuestion_id()+"' ";
                    rs = stmt.executeQuery(query5);
                    
                    while(rs.next()){
                        String answer_id = rs.getString("answer_id");
                        String question_id = rs.getString("question_id");
                        String answer_state = rs.getString("answer_state");
                        String answer_value = rs.getString("dynamic_values");
                        String answer_short_expl = rs.getString("answer_short_expl");
                        DataType_answer answer = new DataType_answer(answer_id,question_id,answer_state,answer_value,answer_short_expl);
                        answer_list.add(answer);
                    }
                    int c = 0;
                    int w = 0;
                    DataType_answer[] answer_show = new DataType_answer[4];
                    DataType_answer c1 = null;
                    while(true){
                        Random random = new Random();
                        int n = random.nextInt(answer_list.size());
                        System.out.println(""+n+"~"+answer_list.get(n).getAnswer_state());
                        if(answer_list.get(n).getAnswer_state().equals("W")&&w<3){
                            answer_show[w] = answer_list.get(n);
                            answer_list.remove(n);
                            w++;
                        }
                        else if(answer_list.get(n).getAnswer_state().equals("W")&&w==3)
                            answer_list.remove(n);                      
                        else if(answer_list.get(n).getAnswer_state().equals("C")&&c<1){
                            c1 = answer_list.get(n);
                            answer_list.remove(n);
                            c++;
                        }
                        else if(answer_list.get(n).getAnswer_state().equals("C")&&c==1)
                            answer_list.remove(n);  
                        if(w==3&&c==1)
                            break;
                    }
                    answer_show[3] = c1;
                    int m = new Random().nextInt(4)+1;
                    answer_show[3] = answer_show[m-1];
                    answer_show[m-1] = c1;
                    /*
                    for(int q=0;q<4;q++){
                        String s = "select answer_value from dynamic_answer where answer_id='"+answer_show[q].getAnswer_id()+"' and combination_id='"+comb_id+"'";
                        rs = stmt.executeQuery(s);
                        rs.next();
                        answer_show[q].setAnswer_value(rs.getString(1));
                    }
                    */
                    for(int a=0;a<4;a++)
                        answers[j][a] = answer_show[a];
                }
            }
        }catch(Exception e){System.out.println("potential query errors");}
        
        buttonGroup = new ButtonGroup[number];
        JLabel[] text = new JLabel[number];
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(layout);
        SequentialGroup group = layout.createSequentialGroup();          
        ParallelGroup group1 = layout.createParallelGroup(Alignment.LEADING);
        
        System.out.println(number);
        for(int t=0;t<number;t++){
            buttonGroup[t] = new ButtonGroup();
            int l=t+1;
            text[t] = new JLabel(""+l+". "+list_question[t].getQuestion_text()+"");
            group.addComponent(text[t]);
            group1.addComponent(text[t]);
            JRadioButton[] radio = new JRadioButton[4];
            for(int k=0;k<4;k++){
                radio[k] = new JRadioButton(answers[t][k].getAnswer_value());           
                buttonGroup[t].add(radio[k]);
                group.addComponent(radio[k]);
                group1.addComponent(radio[k]);
            }
        }   
        layout.setVerticalGroup(group);
        layout.setHorizontalGroup(group1);
        jPanel3.setBorder(BorderFactory.createTitledBorder(assignment.getAssignment_name()));
       
    }
    
    public void actionPerformed(ActionEvent e) {
//        System.out.println(buttonGroup1.getElements().nextElement().get);
    }

       
    public void setCourseName(String cName){
        this.jLabel1.setText(cName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JRadioButton button = new JRadioButton("aaaaaa");
        button.addActionListener(this);
        //        buttonGroup1.add(button);
        jPanel1.add(button);
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton10.setText("Log out");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Subject Name");

        jButton2.setText("Home");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 429, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel3);

        jButton3.setText("Submit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    Log out
    */
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        WelcomeScreen obj = new WelcomeScreen();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton10ActionPerformed

    /*
    Back button
    */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Student_AttemptHW obj = new Student_AttemptHW(new DataType_courseAction(courseObj.getCourseID()));
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /*
    Click of submit button
    */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int num=0;
        String student_id = assignment.getStudent_id();
        String assignment_id = assignment.getAssignment_id();
        int earn = assignment.getCorrect_points();
        int loss = assignment.getPenalty_points();
        //Date attmept_date = new Date();
        /*
        try{
             Date attempt_date1 = new Date();
             SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd HH24:MI:SS.FF");
             attmept_date = DATE_FORMAT.format(attempt_date1);
        }catch(Exception e)
        {
            System.out.println("e = " + e);
        }
        */
        //Date sysdate = new Date();
        Date attempt_date = new Date();
        try
        {
            String query = "SELECT SYSDATE FROM DUAL";
                System.out.println("query to get date from server : "+query);

                
            rs = stmt.executeQuery(query);
            rs.next();
            attempt_date = rs.getTimestamp("sysdate");
        }
        catch(Exception e)
        {
            System.out.println("e = " + e);
        }
        
        //String attempt_date1 = attmept_date.toString();
        
        
        for(int i=0;i<number;i++){
            
            Enumeration<AbstractButton> allRadioButton=buttonGroup[i].getElements();
            int j=0;
            while(allRadioButton.hasMoreElements())  
            {  
               JRadioButton temp=(JRadioButton)allRadioButton.nextElement();  
               if(temp.isSelected()){
                   if(answers[i][j].getAnswer_state().equals("C")){
                       num+=earn;                                 
                   }        
                   else num-=loss;
                   
                   break;
               }  
               j++;
            }           
        }
        if(num<0)num=0;
        
        String s1 = "insert into attempt values(to_timestamp('"+attempt_date+"', 'yyyy-mm-dd HH24:MI:SS.FF'), "
                + "'"+num+"','"+student_id+"','"+assignment_id+"')";     
        System.out.println("s1 = " + s1);
        try{
            stmt.executeUpdate(s1);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        for(int v=0;v<number;v++){
            String s2 = "insert into attempt_question values('"+student_id+"','"+assignment_id+"',"
                    + "to_timestamp('"+attempt_date+"', 'yyyy-mm-dd HH24:MI:SS.FF'),'"+v+"','"+list_question[v].getQuestion_id()+"','"+list_question[v].getQuestion_text()+"')";           
            System.out.println("s2 = " + s2);
            System.out.println(list_question[v].getQuestion_id());
            try{
                stmt.executeUpdate(s2);
            }catch(Exception o){o.printStackTrace();}
            Enumeration<AbstractButton> allRadioButton=buttonGroup[v].getElements();
            int j=0;
            while(allRadioButton.hasMoreElements())  
            {  
               JRadioButton temp=(JRadioButton)allRadioButton.nextElement();  
               if(temp.isSelected()){
                   if(answers[v][j].getAnswer_state().equals("C")){
                       num+=earn;                                 
                   }        
                   else num-=loss;
                   
                   break;
               }  
               j++;
            }
            
            for(int k=0;k<4;k++){
                String selected;               
                if(k==j)
                    selected = "T";                  
                else 
                    selected = "F";
                String s3 = "insert into attempt_answers values('"+student_id+"','"+assignment_id+"',"
                        + " to_timestamp('"+attempt_date+"', 'yyyy-mm-dd HH24:MI:SS.FF'),'"+list_question[v].getQuestion_id()+"','"+answers[v][k].getAnswer_id()+"','"+k+"','"+selected+"','"+answers[v][k].getAnswer_value()+"','"+answers[v][k].getAnswer_state()+"')";
                System.out.println("s3 = " + s3);
                try{
                    stmt.executeUpdate(s3);
                }catch(Exception o){
                    o.printStackTrace();
                }
            }
        }
        
        try{
            String q1 = "select score_selection_method from assignment where assignment_id='"+assignment_id+"'";
            rs = stmt.executeQuery(q1);
            rs.next();
            int m = rs.getInt(1);
            String method = null;
            switch(m){
                
                case 1: method = "last(atmpt_score)";break;
                case 2: method = "max(atmpt_score)";break;
                case 3: method = "avg(atmpt_score)";break;
                                  
            }
            String q4 = "select "+method+" from attempt where student_id='"+student_id+"' and assignment_id='"+assignment_id+"'";
            rs = stmt.executeQuery(q4);
            rs.next();
            int score = rs.getInt(1);
            String q = "select score from statistics_student where student_id='"+student_id+"' and course_id='"+courseObj.getCourseID()+"' and assignment_id='"+assignment.getAssignment_id()+"'";
            rs = stmt.executeQuery(q);
            if(!rs.next()){
                String q3 = "insert into statistics_student values('"+student_id+"','"+courseObj.getCourseID()+"','"+assignment_id+"','"+score+"')";
                stmt.executeUpdate(q3);
            }else{
                String q5 = "update statistics_student set score='"+score+"' where student_id='"+student_id+"' and course_id='"+courseObj.getCourseID()+"' and assignment_id='"+assignment.getAssignment_id()+"'";
                stmt.executeUpdate(q5);
            }
            
            String q8 = "select max(score),avg(score) from statistics_student where course_id='"+courseObj.getCourseID()+"' and assignment_id='"+assignment_id+"' group by course_id,assignment_id";
            rs = stmt.executeQuery(q8);
            rs.next();
            int max = rs.getInt(1);
            int avg = rs.getInt(2);
            String q6 = "select * from statistics_course where course_id='"+courseObj.getCourseID()+"' and assignment_id='"+assignment_id+"'";
            rs = stmt.executeQuery(q6);
            String q7;
            if(!rs.next())
                q7 = "insert into statistics_course values('"+courseObj.getCourseID()+"','"+assignment_id+"','"+max+"','"+avg+"')";
            else 
                q7 = "update statistics_course set maxScore='"+max+"',avgScore='"+avg+"' where course_id='"+courseObj.getCourseID()+"' and assignment_id='"+assignment_id+"'";
            stmt.executeUpdate(q7);
        }catch(Exception e){e.printStackTrace();}
        
        DataType_attempt newobj = new DataType_attempt(attempt_date, student_id, assignment_id);
        Student_AttemptDetails obj = new Student_AttemptDetails(newobj, courseObj);
        obj.setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /*
    Home button
    */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Student_Home obj = new Student_Home();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Student_StartAttempt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Student_StartAttempt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Student_StartAttempt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Student_StartAttempt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Student_StartAttempt().setVisible(true);
//                
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
