package com.santin.survey.dto;

public class SessionAnswerCompilationResultDto {

    private String value;
    private Long amount;

    public SessionAnswerCompilationResultDto() {
    }

    public SessionAnswerCompilationResultDto(String value, Long amount) {
        this.value = value;
        this.amount = amount;
    }

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
}
