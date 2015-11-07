package model;

import javax.persistence.*;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */

@Entity
@Table(name = "options")
public class Options {
    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer optionId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Questions question;

    @Column(name = "option")
    private String option;
    @Column(name = "truth")
    private Boolean truth;


    public Options() {
        //default
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass() || !(obj instanceof Questions)) {
            return false;
        }
        Options option = (Options) obj;
        return (this.optionId == option.getOptionId())
                && (this.option != null && this.option.equals(option.getOption()));
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + optionId;
        result = 31 * result + (option != null ? option.hashCode() : 0);
        return result;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Boolean getTruth() {
        return truth;
    }

    public void setTruth(Boolean truth) {
        this.truth = truth;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }
}
