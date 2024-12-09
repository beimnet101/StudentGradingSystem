package org.example.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectDto {
    private int subjectId;
    private String subjectCode;
    private String name;

    public String getCurrentAssignedteacherName() {
        return CurrentAssignedteacherName;
    }

    public void setCurrentAssignedteacherName(String currentAssignedteacherName) {
        CurrentAssignedteacherName = currentAssignedteacherName;
    }

    private String CurrentAssignedteacherName; // Add teacherName field

    // Getters and Setters
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

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
