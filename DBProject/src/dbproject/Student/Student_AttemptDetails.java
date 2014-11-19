/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.Student;

import dbproject.dataType.*;
import dbproject.Professor.*;
import dbproject.Professor.Prof_Edit_HW;
import dbproject.Professor.Prof_Notification;
import dbproject.Professor.Prof_Report;
import dbproject.Professor.Prof_View_HW;
import dbproject.WelcomeScreen;
import dbproject.dbconnection.dbconnection_dbObject;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;


/**
 *
 * @author Chetan
 */
public class Student_AttemptDetails extends javax.swing.JFrame {

    private DataType_attempt attempt;
    private DataType_courseAction _courseAction;
    private DataType_attemptQuestion[] attemptQuestionArray;//contains all the questions of the attempt - sorted in the postion in the original attempt
    private DataType_attempt_answers[][] attemptAnswersArray;//Contains all the answers offeres in the attempt - same order than attemptQuestionArray
    
    //Connection to the database
    private Statement stmt = null;
    private ResultSet rs = null;
    
    /**
     * Creates new form MainScreen
     */
    public Student_AttemptDetails() {
        initComponents();
        AddHomeworkAsRadioButtons();
    }
    
    //Overloaded constrctor
    public Student_AttemptDetails(DataType_attempt inputObj, DataType_courseAction courseObj) {
        initComponents();
        attempt = inputObj;
        _courseAction = courseObj; 
        
        //connection to the db
        dbconnection_dbObject db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;
        
        
        //Variables
        int numberOfQuestions = 0;
        int numberOfAnswersPerQuestion = 4;
        
        
        //Number of questions in this assignments and creation of the arrays attempQuestionArray and atempsAnswersArray
        /*
        String query = "Select Count(CQ.question_id) "
                +"from chosen_question CQ "
                +"where CQ.assignment_id ='"+attempt.assignment_id+"' ";
        */
        String query = "Select count(*) as number_of_questions  from attempt_question a "
                +" where a.student_id ='"+inputObj.student_id+"' "
                +" and a.assignment_id='"+inputObj.assignment_id+"' "
                +" and a.atmpt_dt = to_timestamp('"+inputObj.atmpt_dt+"', 'yyyy-mm-dd HH24:MI:SS.FF') ";
               
        System.out.println("query get number of questions in the assignment "+attempt.assignment_id+" : "+ query);
        
        try {
            rs = stmt.executeQuery(query);
            rs.next();
            numberOfQuestions = rs.getInt("number_of_questions");
            attemptQuestionArray = new DataType_attemptQuestion[numberOfQuestions];
            attemptAnswersArray = new DataType_attempt_answers[numberOfQuestions][numberOfAnswersPerQuestion];
            
        }
        catch (Exception oops) {
            System.out.println("WARNING - Student_AttemptDetails -Student_AttemptDetails(DataType_attempt inputObj, String courseID) -get number of questions in the assignment "+attempt.assignment_id+" :  " + oops); 
        } 
        
        //Fill the array attempQuestionArray - Find all the attempted question
        
        /*
        query = "Select question_id "
                +"from chosen_question CQ "
                +"where CQ.assignment_id ='"+attempt.assignment_id+"' ";
        */       
        
        query = "Select a.atmpt_ques_id from attempt_question a "
                +" where a.student_id ='"+inputObj.student_id+"' "
                +" and a.assignment_id='"+inputObj.assignment_id+"' "
                +" and a.atmpt_dt = to_timestamp('"+inputObj.atmpt_dt+"', 'yyyy-mm-dd HH24:MI:SS.FF') ";
        
        System.out.println("query get questionID of all the questionin the assignment "+attempt.assignment_id+" : "+ query);
        
        try {
            Statement outerStmtForQuestion = db.conn.createStatement();
            rs = outerStmtForQuestion.executeQuery(query);//get tje list of the questionID in this assignment
            
            while (rs.next()) {//Treat each question
                String questionID = rs.getString("atmpt_ques_id");
                
                /*
                String QuestionDetailsquery = "Select * "
                +" from attempt_question a "
                +" where a.student_id ='"+attempt.student_id+"' "
                +" and a.assignment_id='"+attempt.assignment_id+"' "
                +" and a.atmpt_dt = to_timestamp('"+attempt.atmpt_dt+"', 'yyyy-mm-dd HH24:MI:SS.FF') "
                +" and a.atmpt_ques_id='"+questionID+"' ";
                
                ResultSet QuestionDetailRS = stmt.executeQuery(QuestionDetailsquery);
                DataType_attemptQuestion provAttemptQuestion = new DataType_attemptQuestion();
                QuestionDetailRS.next();
                provAttemptQuestion.student_id = attempt.student_id;
                provAttemptQuestion.assignment_id = attempt.assignment_id;
                provAttemptQuestion.atmpt_dt = attempt.atmpt_dt;
                provAttemptQuestion.atmpt_ques_id = questionID;
                provAttemptQuestion.atmpt_ques_seq_no= QuestionDetailRS.getInt("atmpt_ques_seq_no");
                provAttemptQuestion.atmpt_ques_text = QuestionDetailRS.getString("atmpt_ques_text");
                */
                DataType_attemptQuestion provAttemptQuestion = new DataType_attemptQuestion(attempt.atmpt_dt, attempt.student_id, attempt.assignment_id, questionID);
                attemptQuestionArray[provAttemptQuestion.atmpt_ques_seq_no] = provAttemptQuestion;
                
                //get all the answers offered for this questionFill the array attempQuestionArray - Find all the attempted answers
                query = "Select atmpt_answer_id "
                        +"from attempt_answers AA "
                        +"where AA.student_id ='"+provAttemptQuestion.student_id+"' "
                        +"and AA.assignment_id='"+provAttemptQuestion.assignment_id+"' "
                        +"and AA.atmpt_dt = to_timestamp('"+provAttemptQuestion.atmpt_dt+"', 'yyyy-mm-dd HH24:MI:SS.FF') "
                        +"and AA.atmpt_ques_id='"+provAttemptQuestion.atmpt_ques_id+"' ";

                System.out.println("query get answerID of all the answers in the question "+provAttemptQuestion.atmpt_ques_id+" : "+ query);

                try {
                    Statement InnerStmtForQuestion = db.conn.createStatement();
                    ResultSet rs2 = InnerStmtForQuestion.executeQuery(query);//get tje list of the questionID in this assignment

                    while (rs2.next()) {//Treat each answers
                        String answerID = rs2.getString("atmpt_answer_id");
                        DataType_attempt_answers provAttemptAnswer = new DataType_attempt_answers(provAttemptQuestion.atmpt_dt, provAttemptQuestion.student_id, provAttemptQuestion.assignment_id, provAttemptQuestion.atmpt_ques_id, answerID);
                        attemptAnswersArray[provAttemptQuestion.atmpt_ques_seq_no][provAttemptAnswer.atmpt_answer_seq_no] = provAttemptAnswer;
                        
                    }
                }
                catch (Exception oops) {
                    System.out.println("WARNING - Student_AttemptDetails -Student_AttemptDetails(DataType_attempt inputObj, String courseID) -get questionID of all the questionin the assignment "+attempt.assignment_id+" :  " + oops); 
                }
            }
            
            //Constitution of the text to display
            String textToDisplay = "";
            int numberOfGoodAnswers = 0;
            int selectedanswerIndex = -1;
            int indexOfCorrectAnswer  = -1;
            for (int i = 0; i < numberOfQuestions; i++) {//treat of each question
                 //the index of the answer that we have to display to text at the end. -1 if the selected answer is the good answer
                indexOfCorrectAnswer = -1;
                textToDisplay += "Question "+ (i+1) +" : ";
                textToDisplay += attemptQuestionArray[i].atmpt_ques_text+ "\n";
                for (int j = 0; j < numberOfAnswersPerQuestion; j++) {//treat of each answer
                    DataType_attempt_answers answer= attemptAnswersArray[i][j];
                    if (answer.atmpt_answer_selected.equals("T")) {//the answer has been selected
                        textToDisplay += "X - "+ answer.atmpt_answer_text+"\n";
                        selectedanswerIndex = j;
                    }
                    else if (answer.atmpt_answer_selected.equals("F")){
                        textToDisplay += "O - "+ answer.atmpt_answer_text+"\n";
                    }
                    
                    if (answer.answer_state.equals("C"))
                        indexOfCorrectAnswer = j;
                }
                textToDisplay += "\n";
                textToDisplay += "Result : ";
                if(indexOfCorrectAnswer == selectedanswerIndex){//the selected answer is good
                    textToDisplay += "Correct\n"+attemptQuestionArray[i].atmpt_ques_explaination + "\n";
                    numberOfGoodAnswers++;
                }
                else if (selectedanswerIndex == -1)
                {
                    textToDisplay += "Unattempted\n\n";
                }
                else{
                    textToDisplay += "Wrong.\n"+attemptAnswersArray[i][selectedanswerIndex].answer_explanation + "\n";
                }
                
                textToDisplay += "---------------------------------------------------------------\n";
            }
            System.out.println(textToDisplay);
            
            //Display data on the top of the screen
            jLabel1.setText(_courseAction.getCourseID());//Display the courseID in the top left corner
            jLabel2.setText("Assignment \"" + attempt.assignment_name + "\" on attempt date:\"" + attempt.due_dt + "\"");
            jLabel3.setText("Score : " + attempt.atmpt_score);
            jLabel5.setText(numberOfGoodAnswers+" good, "+(numberOfQuestions - numberOfGoodAnswers)+" bad");
            
            //Display the content
            jTextArea1.setText(textToDisplay);
                
            jTextArea1.setEditable(false);
           
            
        }
        catch (Exception oops) {
            System.out.println("WARNING - Student_AttemptDetails -Student_AttemptDetails(DataType_attempt inputObj, String courseID) -get questionID of all the questionin the assignment "+attempt.assignment_id+" :  " + oops); 
        } 
    }
    
    private void AddHomeworkAsRadioButtons()
    {
        
        //JButton newButton = new JButton
        buttonGroup1.add(new JButton("Homework 1 | 5 Attempts remaining"));
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
        jPanel1 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setText("Assignment Name");

        jLabel3.setText("Attempt Score");

        jLabel5.setText("X correct Y wrong");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(54, 54, 54)
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        Student_ViewPastSubmissions obj = new Student_ViewPastSubmissions(_courseAction);
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /*
    Home
    */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Student_Home obj = new Student_Home(_courseAction.userObj);
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Student_AttemptDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Student_AttemptDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Student_AttemptDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student_AttemptDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Student_AttemptDetails().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
