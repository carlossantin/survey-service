package com.santin.survey.api.v1.output;

public class AnswerAmountOutput {

    private String value;
    private Long amount;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "{\"AnswerAmountOutput\":{"
                + "\"value\":\"" + value + "\""
                + ", \"amount\":\"" + amount + "\""
                + "}}";
    }
}
