package org.example.utility;
public class NumericGrToLetterGrade {
    public static String convertToLetterGrade(double numericGrade) {
        if (numericGrade >= 90) {
            return "A+";
        } else if (numericGrade >= 85) {
            return "A";
        } else if (numericGrade >= 80) {
            return "A-";
        } else if (numericGrade >= 75) {
            return "B+";
        } else if (numericGrade >= 70) {
            return "B";
        } else if (numericGrade >= 65) {
            return "B-";
        } else if (numericGrade >= 60) {
            return "C+";
        } else if (numericGrade >= 50) {
            return "C";
        } else if (numericGrade >= 40) {
            return "D";
        } else {
            return "F";
        }
    }
}