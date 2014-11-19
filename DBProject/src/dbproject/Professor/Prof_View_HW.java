/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.Professor;

import dbproject.dataType.*;
import dbproject.WelcomeScreen;
import dbproject.dbconnection.dbconnection_dbObject;
import java.awt.Color;
import java.util.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chetan
 */
public class Prof_View_HW extends javax.swing.JFrame {

    DataType_user userObj;
    DataType_course courseObj;
    String query_past;
    String query_current;
    Statement stmt;
    ResultSet rs;
    ArrayList<DataType_assignment> current_assignment = new ArrayList<DataType_assignment>();
    ArrayList<DataType_assignment> past_assignment = new ArrayList<DataType_assignment>();
    ArrayList<String> allQuestions = new ArrayList<String>();
    String query;
    
    
    /**
     * Creates new form MainScreen
     */
    public Prof_View_HW() {
        initComponents();
    }
    
    //Overloaded constructor
    public Prof_View_HW(DataType_user inputObj, DataType_course course) {
        initComponents();
        userObj = inputObj;
        courseObj=course;
        dbconnection_dbObject db = dbconnection_dbObject.getDBConnection();
        stmt = db.stmt;    
        jLabel1.setText(course.course_name + "" + course.course_id);
        jLabel22.setVisible(false);
        populateHomeworksComboboxes();
        if(jComboBox1.getItemCount() == 0 ){
            jButton4.setEnabled(false);
        }
        if(jComboBox2.getItemCount() == 0 ){
            jButton2.setEnabled(false);
        }
        
        if (current_assignment.size() == 0 && past_assignment.size() == 0)
        {
            jLabel22.setVisible(true);
            jLabel22.setText("No assignments found");
        }
            
    }
    
    /*
    This method will populate the combo boxes on the screen.
    */
    private void populateHomeworksComboboxes()
    {
        
        query_past = "SELECT assignment_id ,assignment_name,assignment_difficulty_level,"
                         + " number_of_retries, random_seed , penalty_points,correct_points, start_dt , "
                         + "end_dt , score_selection_method , course_id ,professor_id,number_of_questions from assignment "
                + "where course_id ='" + courseObj.course_id +"' and end_dt < SYSDATE"; 
        
        //System.out.println(query_past);
        query_current = "SELECT assignment_id ,assignment_name,assignment_difficulty_level,"
                         + " number_of_retries, random_seed , penalty_points,correct_points, start_dt , "
                         + "end_dt , score_selection_method , course_id ,professor_id,number_of_questions from assignment "
                + "where course_id ='" + courseObj.course_id +"' and end_dt >= SYSDATE"; 
        //System.out.println(query_current);
        try {
            rs = stmt.executeQuery(query_past);
            
            while (rs.next()) {
            DataType_assignment assignment_object = new DataType_assignment();    
              
            assignment_object.assignment_id = rs.getString("assignment_id");
            assignment_object.assignment_name = rs.getString("assignment_name");
            assignment_object.assignment_difficulty = rs.getInt("assignment_difficulty_level");
            assignment_object.number_of_retries = rs.getInt("number_of_retries");
            assignment_object.random_seed = rs.getInt("random_seed");
            assignment_object.penalty_points = rs.getInt("penalty_points");
            assignment_object.correct_points = rs.getInt("correct_points");
            assignment_object.correct_points = rs.getInt("correct_points");
            assignment_object.start_dt = rs.getTimestamp("start_dt");
            assignment_object.end_dt = rs.getTimestamp("end_dt");
            assignment_object.score_selection_method = rs.getInt("score_selection_method");
            assignment_object.course_id = rs.getString("course_id");
            assignment_object.professor_id = rs.getString("professor_id");
            assignment_object.number_of_questions = rs.getInt("number_of_questions");
            
               
                past_assignment.add(assignment_object);
                jComboBox2.addItem(rs.getString("assignment_name"));
            }
            
            rs = stmt.executeQuery(query_current);
            
            while (rs.next()) {
                
                DataType_assignment assignment_object = new DataType_assignment();  
                
             assignment_object.assignment_id = rs.getString("assignment_id");
            assignment_object.assignment_name = rs.getString("assignment_name");
            assignment_object.assignment_difficulty = rs.getInt("assignment_difficulty_level");
            assignment_object.number_of_retries = rs.getInt("number_of_retries");
            assignment_object.random_seed = rs.getInt("random_seed");
            assignment_object.penalty_points = rs.getInt("penalty_points");
            assignment_object.correct_points = rs.getInt("correct_points");
            assignment_object.start_dt = rs.getTimestamp("start_dt");
            assignment_object.end_dt = rs.getTimestamp("end_dt");
            assignment_object.score_selection_method = rs.getInt("score_selection_method");
            assignment_object.course_id = rs.getString("course_id");
            assignment_object.professor_id = rs.getString("professor_id");
            assignment_object.number_of_questions = rs.getInt("number_of_questions");
                current_assignment.add(assignment_object);
                jComboBox1.addItem(rs.getString("assignment_name"));
            }
            
        } catch (Exception oops) {
            System.out.println("ProfAss_Remove_Question.java:load_assignments() " + oops);

        }
    
        
        //currentHWKeys = new Hashtable();
        //pastDueHWKeys = new Hashtable();
        
        //Step1: Connect to database to get the list of past due homeworks and current homeworks
        
        //Step2: Create combo box item and bind it's corresponding id in the hashtable.
        
        
        //for each entry we got from database for homeworks, create comboboxItem and add it in currentHWkeys or
        //futureHWKeys hashtable
        
        //step3: Add combo box item to respective combo box item. 
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
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

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

        jButton3.setText("Home");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Current Homeworks");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Past Due Homeworks");

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Questions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setText("Start Date:");

        jLabel5.setText("End Date:");

        jLabel6.setText("_______________");

        jLabel7.setText("_______________");

        jLabel8.setText("Number of Attempts:");

        jLabel9.setText("Difficulty Rane:");

        jLabel10.setText("_______________");

        jLabel11.setText("_______________");

        jLabel12.setText("Score Selection Method:");

        jLabel13.setText("_______________");

        jLabel14.setText("Number of questions:");

        jLabel15.setText("_______________");

        jLabel16.setText("Correct Answer Points:");

        jLabel17.setText("_______________");

        jLabel18.setText("Incorrect Answer Points:");

        jLabel19.setText("_______________");

        jLabel20.setText("Random Seed:");

        jLabel21.setText("_________");

        jLabel22.setText("Warning");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel18))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel16))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel7))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel6)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(jLabel9))
                                            .addComponent(jLabel8))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel19))
                                .addGap(13, 13, 13))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2)))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    Back
    */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Prof_CourseActions obj = new Prof_CourseActions(userObj,courseObj);
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /*
    Home Button
    */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ProfHome obj = new ProfHome(userObj);
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    //Search button for current hoemwork combobox
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       // String selectedSubject = jComboBox1.getSelectedItem().toString();
        
        //Get the corresponding AssignmentId from currentHWKeys hashtable
      //  String AssignmentId = currentHWKeys.get(selectedSubject).toString();
        
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();    
        int rows = model.getRowCount(); 
        for(int i = rows - 1; i >=0; i--)   
        {
            model.removeRow(i);  
        }    
        
         jButton2.setBackground(null);
          jButton4.setBackground(Color.YELLOW);
          PopulateValuesOnUI("current");
    }
        public void PopulateValuesOnUI(String type){
            int combo_selection = 0;
            ArrayList<DataType_assignment> selectedAssignments;
            if(type .equals("current")){
                selectedAssignments = current_assignment;
                combo_selection = jComboBox1.getSelectedIndex();
            }
            else 
            {
                selectedAssignments = past_assignment;
                    combo_selection = jComboBox2.getSelectedIndex();
            }
        jLabel6.setText(selectedAssignments.get(combo_selection).start_dt.toString());
        jLabel7.setText(selectedAssignments.get(combo_selection).end_dt.toString());
        jLabel13.setText(selectedAssignments.get(combo_selection).score_selection_method.toString());
        jLabel15.setText(selectedAssignments.get(combo_selection).number_of_questions.toString());
        if(selectedAssignments.get(combo_selection).number_of_retries == 0){
        jLabel10.setText("Unlimited");
        }else{
        jLabel10.setText(selectedAssignments.get(combo_selection).number_of_retries.toString());
        }
        jLabel11.setText(selectedAssignments.get(combo_selection).assignment_difficulty.toString());
        jLabel17.setText(selectedAssignments.get(combo_selection).correct_points.toString());
        jLabel19.setText(selectedAssignments.get(combo_selection).penalty_points.toString());
        jLabel21.setText(selectedAssignments.get(combo_selection).random_seed.toString());
        
        
        //System.out.println(selectedAssignments.get(combo_selection).start_dt);
        
         allQuestions.clear();
        //System.out.println( combo_selection);
        
        String assignmentId = selectedAssignments.get(combo_selection).assignment_id;
        query = "SELECT q.ques_text,q.question_id from question q, chosen_question c where q.question_id = c.question_id"
                + " and c.assignment_id = '" + assignmentId + "'";
                
                
        
        Object[] data = new Object[1];
        
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();    
        model.getDataVector().removeAllElements();
        //System.out.println(query);
        try {
            rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                
                
                data[0] = rs.getString("ques_text");
                allQuestions.add(rs.getString("question_id"));
                model.addRow(data);
                
            }
            
            
        } catch (Exception oops) {
            System.out.println("Prof_View_HW.java:jButton4ActionPerformed " + oops);

         }
        
       
        
    }//GEN-LAST:event_jButton4ActionPerformed

    //Search button for past due hoemwork combobox
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    //    String selectedSubject = jComboBox1.getSelectedItem().toString();
        
        //Get the corresponding AssignmentId from pastDueHWKeys hashtable
      //  String AssignmentId = pastDueHWKeys.get(selectedSubject).toString();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();    
        int rows = model.getRowCount(); 
        for(int i = rows - 1; i >=0; i--)   
        {
            model.removeRow(i);  
        }    
        
        jButton4.setBackground(null);
        jButton2.setBackground(Color.YELLOW);
        PopulateValuesOnUI("past");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();    
        int rows = model.getRowCount(); 
        for(int i = rows - 1; i >=0; i--)   
        {
            model.removeRow(i);  
        }    
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();    
        int rows = model.getRowCount(); 
        for(int i = rows - 1; i >=0; i--)   
        {
            model.removeRow(i);  
        }    
    }//GEN-LAST:event_jComboBox2ActionPerformed

    /*
    This method is responsible for displaying the deailed data for perticular assignment on UI.
    */
  
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
            java.util.logging.Logger.getLogger(Prof_View_HW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Prof_View_HW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Prof_View_HW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Prof_View_HW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Prof_View_HW().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
