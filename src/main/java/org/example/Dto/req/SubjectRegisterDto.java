package org.example.Dto.req;

import org.example.model.Teacher;

public class SubjectRegisterDto {
    private String subjectCode;
    private String name;

    private  int teacherId;
    private  int credithour;

    public int getCredithour() {
        return credithour;
    }

    public void setCredithour(int credithour) {
        this.credithour = credithour;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }



    // Getters and Setters
    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
