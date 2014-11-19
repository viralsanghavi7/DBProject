/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.dataType;

/**
 *
 * @author Darren
 */
public class DataType_answer {
    private String answer_id;
    private String question_id;
    private String answer_state;
    private String answer_value;
    private String answer_short_expl;

    public void setAnswer_value(String answer_value) {
        this.answer_value = answer_value;
    }

    public DataType_answer(String answer_id, String question_id, String answer_state, String answer_value, String answer_short_expl) {
        this.answer_id = answer_id;
        this.question_id = question_id;
        this.answer_state = answer_state;
        this.answer_value = answer_value;
        this.answer_short_expl = answer_short_expl;
    }

    public String getAnswer_id() {
        return answer_id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public String getAnswer_state() {
        return answer_state;
    }

    public String getAnswer_value() {
        return answer_value;
    }

    public String getAnswer_short_expl() {
        return answer_short_expl;
    }
    
}
