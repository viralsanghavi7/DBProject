/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.Professor;

import dbproject.dataType.*;
import java.util.*;
import dbproject.WelcomeScreen;
import java.sql.*;
import dbproject.dataType.DataType_user;
import dbproject.dbconnection.dbconnection_dbObject;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Chetan
 */
public class Prof_Edit_HW extends javax.swing.JFrame {

    DataType_user userObj;
    DataType_course courseObj;
    ArrayList<String> Homeworks;
    public String query;
    Statement stmt = null;
    ResultSet rs = null;
    boolean bWarningEnable = false;
    boolean bInitialLoading = true;
    
    /**
     * Creates new form MainScreen
     */
    public Prof_Edit_HW() {
        initComponents();
        Homeworks = new ArrayList<String>();
    }
    
    //Overloaded constructor
    public Prof_Edit_HW(DataType_user inputObj, DataType_course inputCourseObj) {
        initComponents();
        
        dbconnection_dbObject db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;
        
        userObj = inputObj;
        courseObj = inputCourseObj;
        Homeworks = new ArrayList<String>();
        jLabel1.setText(courseObj.course_name + " " + courseObj.course_id);
        jLabel11.setVisible(false);
        GetAllHomeworkList();
        bInitialLoading = false;
        if (Homeworks.size() == 0)
        {
            jButton4.setEnabled(false);
            jButton5.setEnabled(false);
            jLabel11.setVisible(true);
            jLabel11.setText("No homework to edit. Please add homework first.");
        }
    }
    
    
    private void GetAllHomeworkList()
    {
        
        try {
                query = "select assignment_id, assignment_name from assignment where course_id = '" + courseObj.course_id + "'";
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                Homeworks.add(rs.getString("assignment_id"));
                jComboBox3.addItem(rs.getString("assignment_name"));
                }
            } catch (Exception oops) {
                System.out.println("Error in GetAllHomeworkList() : " + oops);
            }
        
        if (Homeworks.size() > 0)
        {
          DataType_assignment assgDetails =  getAssignmentDetails(Homeworks.get(0));
          PopulateAllHWDetails(assgDetails);
        }
    }
    
    
    private DataType_assignment getAssignmentDetails(String assignmentID)
    {
        DataType_assignment assgDetails = new DataType_assignment();
        
        try {
                query = "select * from assignment where assignment_id = '" + assignmentID + "'";
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                assgDetails.assignment_id = assignmentID;
                assgDetails.assignment_difficulty = rs.getInt("assignment_difficulty_level");
                assgDetails.assignment_name = rs.getString("assignment_name");
                assgDetails.correct_points = rs.getInt("correct_points");
                assgDetails.course_id = rs.getString("course_id");
                assgDetails.end_dt = rs.getTimestamp("end_dt");
                assgDetails.start_dt = rs.getTimestamp("start_dt");
                assgDetails.number_of_retries = rs.getInt("number_of_retries");
                assgDetails.penalty_points = rs.getInt("penalty_points");
                assgDetails.professor_id = rs.getString("professor_id");
                assgDetails.random_seed = rs.getInt("random_seed");
                assgDetails.score_selection_method = rs.getInt("score_selection_method");
                assgDetails.number_of_questions = rs.getInt("number_of_questions");
                }
            } catch (Exception oops) {
                System.out.println("Error in GetAllHomeworkList() : " + oops);
            }
        
        return assgDetails;
    }
    
    /*
    display all homework details on UI and enable/disable fields based on hw start date
    */
    private void PopulateAllHWDetails(DataType_assignment assignment)
    {
        try
        {
            java.util.Date currentDate = new java.util.Date();
            if (currentDate.compareTo(assignment.start_dt) > 2)
            {
                bWarningEnable = true;
                jLabel11.setVisible(true);
                jLabel11.setText("Warning: Can not edit/delete homework. Start date already passed");
            }
            else
            {
                bWarningEnable = false;
                jLabel11.setVisible(false);
            }
            
            //start date
            jXDatePicker2.setDate(assignment.start_dt);
            if (bWarningEnable)
                jXDatePicker2.setEnabled(false);
            else
                jXDatePicker2.setEnabled(true);
            
            //end date
            jXDatePicker3.setDate(assignment.end_dt);
            if (bWarningEnable)
                jXDatePicker3.setEnabled(false);
            else
                jXDatePicker3.setEnabled(true);
            
            //number of attempts
            jComboBox4.setSelectedIndex(assignment.number_of_retries);
            if (bWarningEnable)
                 jComboBox4.setEnabled(false);
            else
                jComboBox4.setEnabled(true);
            
            //randomization seed
            jTextField1.setText(Integer.toString(assignment.random_seed));
            if (bWarningEnable)
                 jTextField1.setEnabled(false);
            else
                jTextField1.setEnabled(true);
            
            //Difficulty range
            jComboBox1.setSelectedIndex(assignment.assignment_difficulty - 1);
            if (bWarningEnable)
                 jComboBox1.setEnabled(false);
            else
                jComboBox1.setEnabled(true);
            
            //score selection theme
            jComboBox2.setSelectedIndex(assignment.score_selection_method - 1);
            if (bWarningEnable)
                 jComboBox2.setEnabled(false);
            else
                jComboBox2.setEnabled(true);
            
            //number of questions
            jTextField2.setText(Integer.toString(assignment.number_of_questions));
            if (bWarningEnable)
                 jTextField2.setEnabled(false);
            else
                jTextField2.setEnabled(true);
            
            //correct answer points
            jTextField3.setText(Integer.toString(assignment.correct_points));
            if (bWarningEnable)
                 jTextField3.setEnabled(false);
            else
                jTextField3.setEnabled(true);
            
            //Incorrect answer points
            jTextField4.setText(Integer.toString(assignment.penalty_points));
            if (bWarningEnable)
                 jTextField4.setEnabled(false);
            else
                jTextField4.setEnabled(true);
            
            
            if (bWarningEnable)
            {
                jButton4.setEnabled(false);
                jButton5.setEnabled(false);
            }
            else
            {
                jButton4.setEnabled(true);
                jButton5.setEnabled(true);
            }
            
        }
        catch(Exception oops) {
            System.out.println("Error in GetAllHomeworkList() : " + oops);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker3 = new org.jdesktop.swingx.JXDatePicker();
        jComboBox4 = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton10.setText("Log out");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Subject Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton4.setText("Delete Homework");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setText("Start Date");

        jLabel3.setText("End Date");

        jLabel4.setText("Number of Attempts");

        jLabel5.setText("Difficulty Range");

        jLabel6.setText("Score Selection Scheme");

        jLabel7.setText("Number of Questions");

        jLabel8.setText("Correct Answer Points");

        jLabel9.setText("Incorrect Answer Points");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "latest attempt", "maximum score", "average score" }));

        jLabel10.setText("Select Homework to edit");

        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Unlimited", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jButton5.setText("Update Details");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(255, 51, 51));
        jLabel11.setText("warning");

        jLabel12.setText("Randomization seed");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel12))
                                .addGap(80, 80, 80)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jXDatePicker3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(30, 30, 30)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
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
    Code for 'delete homework' button
    */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //Step1: chcek if the hoemwork can be deleted
        if (bWarningEnable)
        {
            JOptionPane.showMessageDialog(this, "Can not delete homework");
            return;
        }
        
        //Step2: Update the perticular homework record in database.
        String selectedHW = Homeworks.get(jComboBox3.getSelectedIndex());
        
        try
        {
        //delete chosen_question entries first
        query = "delete from chosen_question where assignment_id = '" + selectedHW + "'";
        
        rs = stmt.executeQuery(query);
                
        //delete from assignment table
        query = "delete from assignment where assignment_id = '" + selectedHW + "'";
        
        rs = stmt.executeQuery(query);
        
        } catch (Exception oops) {
            System.out.println("Prof_Edit_HW.java:DeleteHomework() " + oops);
        }
        //Step3: Navigate professor to course Actions page.
        JOptionPane.showMessageDialog(this, "Homework deleted");
        Prof_CourseActions obj = new Prof_CourseActions(userObj, courseObj);
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

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
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Prof_CourseActions obj = new Prof_CourseActions(userObj, courseObj);
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /*
    Home button
    */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ProfHome obj = new ProfHome(userObj);
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    //Code for 'update details' button
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //Step1: chcek if the hoemwork can be updated
        if (bWarningEnable)
        {
            JOptionPane.showMessageDialog(this, "Can not update homework details");
            return;
        }
        
        //Step2: Update the perticular homework record in database.
        String selectedHW = Homeworks.get(jComboBox3.getSelectedIndex());
        
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yy");
        
        //start date
        java.util.Date start_date_swing_format = jXDatePicker2.getDate();        
        String start_date = DATE_FORMAT.format(start_date_swing_format);
        
        //end date
        java.util.Date end_date_swing_format =  jXDatePicker3.getDate();
        String end_date = DATE_FORMAT.format(end_date_swing_format);
        
        int no_of_retries = jComboBox4.getSelectedIndex();
        int difficulty_range = (jComboBox1.getSelectedIndex())+1;
        int randomization_seed = Integer.parseInt(jTextField1.getText());
        int score_selection = (jComboBox2.getSelectedIndex())+1;
        int number_of_questions = Integer.parseInt(jTextField2.getText());
        int correct_answer_point = Integer.parseInt(jTextField3.getText());
        int penalty_points = Integer.parseInt(jTextField4.getText());
        
        try
        {
        
        query = "update assignment set " +
                " assignment_difficulty_level = " + difficulty_range + ", " +
                " number_of_retries = " + no_of_retries + ", " +
                " random_seed = " + randomization_seed + ", " +
                " penalty_points = " + penalty_points + ", " +
                " correct_points = " + correct_answer_point + ", " +
                " start_dt = '" + start_date + "', " +
                " end_dt = '" + end_date + "', " +
                " score_selection_method = " + score_selection + ", " +
                " course_id = '" + courseObj.course_id + "', " +
                " professor_id = '" + userObj.user_id + "', " +
                " number_of_questions = " + number_of_questions + " " +
                " where assignment_id = '" + selectedHW + "' ";
        
        rs = stmt.executeQuery(query);
        
        } catch (Exception oops) {
            System.out.println("Prof_Edit_HW.java:UpdateHomework() " + oops);

        }
        
        //Step3: Navigate professor to course Actions page.
        JOptionPane.showMessageDialog(this, "Homework details updated");
        Prof_CourseActions obj = new Prof_CourseActions(userObj, courseObj);
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    //When different homework is selected from dropdown
    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        if(!bInitialLoading)
        {
            String selectedHW = Homeworks.get(jComboBox3.getSelectedIndex());
        
            DataType_assignment assgDetails =  getAssignmentDetails(selectedHW);
            PopulateAllHWDetails(assgDetails);
        }        
    }//GEN-LAST:event_jComboBox3ActionPerformed

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
            java.util.logging.Logger.getLogger(Prof_Edit_HW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Prof_Edit_HW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Prof_Edit_HW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Prof_Edit_HW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Prof_Edit_HW().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker3;
    // End of variables declaration//GEN-END:variables
}
