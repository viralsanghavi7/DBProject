/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject.dataType;

/**
 *
 * @author Viral
 */
public class DataType_question {

    
    public String question_id;
    public String question_text;
    public Integer question_difficulty_level;
    public String question_hint;
    public String topic_id;
    public String question_isdynamic;
    public Integer question_no_of_parameters;
    public String question_long_explanation;
    public String question_short_explanation;

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public DataType_question(String question_id, String question_text, String question_hint, String question_isdynamic, String question_long_explanation) {
        this.question_id = question_id;
        this.question_text = question_text;
//        this.question_difficulty_level = question_difficulty_level;
        this.question_hint = question_hint;
//        this.topic_id = topic_id;
        this.question_isdynamic = question_isdynamic;
//        this.question_no_of_parameters = question_no_of_parameters;
        this.question_long_explanation = question_long_explanation;
//        this.question_short_explanation = question_short_explanation;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public Integer getQuestion_difficulty_level() {
        return question_difficulty_level;
    }

    public String getQuestion_hint() {
        return question_hint;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public String getQuestion_isdynamic() {
        return question_isdynamic;
    }

    public Integer getQuestion_no_of_parameters() {
        return question_no_of_parameters;
    }

    public String getQuestion_long_explanation() {
        return question_long_explanation;
    }

    public String getQuestion_short_explanation() {
        return question_short_explanation;
    }
    
            
}
