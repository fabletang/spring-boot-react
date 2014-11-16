package edu.iastate.data;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.iastate.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable {
    private static final long serialVersionUID = 3544667395357423671L;

    @Id
    private String id;

    private String semesterCode;

    private String semesterYear;

    //combination of semesterCode and semesterYear for even easier searching
    private String term;

    private String deptCode;

    private String classNumber;

    private String departmentTitle;

    private String classTitle;

    private String classComments;

    private String classPreReqs;

    private Semester semester;

    private List<Section> sections;

    //@Transient
    private CreditType creditType;

    //@Transient
    private Double minCredits;

    //@Transient
    private Double maxCredits;

    private String dvrstyIntl;

    /** Night exam indicator */
    private String nightExam;

    /** Special permission */
    private String spclPerm;
    /** Minor graduate credit */

    private String minorGradCred;

    /** Permission that applies to the first week of class */
    private String permFirstWeek;

    /** Grade type, pass/fail? */
    private String gradeType;

    /** See department indicator */
    private String seeDept;

    /** Special fee max and min */
    private double spclFeeMax;

    private double spclFeeMin;


    @JsonGetter
    public String getSpecialPermissionRequirementsDisplay()
    {
        String specialPermission = StringUtils.upperCase(getSpclPerm());
        if (!StringUtils.equals(specialPermission.trim(), "")) {
            if ( (StringUtils.equals(this.getPermFirstWeek(), "C"))  || (StringUtils.equals(this.getPermFirstWeek(), "B"))) {
                if (Constants.FIRST_WEEK_MESSAGES.containsKey(getPermFirstWeek())) {
                    return Constants.FIRST_WEEK_MESSAGES.get(getPermFirstWeek());
                } else {
                    return Constants.FIRST_WEEK_MESSAGE_DEFAULT;
                }
            } else {
                if (Constants.SPECIAL_PERMISSIONS.containsKey(specialPermission)) {
                    return Constants.SPECIAL_PERMISSIONS.get(specialPermission);
                } else {
                    return Constants.SPECIAL_PERMISSION_DEFAULT;
                }
            }
        }

        return "";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }

    public String getSemesterYear() {
        return semesterYear;
    }

    public void setSemesterYear(String semesterYear) {
        this.semesterYear = semesterYear;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDepartmentTitle() {
        return departmentTitle;
    }

    public void setDepartmentTitle(String departmentTitle) {
        this.departmentTitle = departmentTitle;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getClassComments() {
        return classComments;
    }

    public void setClassComments(String classComments) {
        this.classComments = classComments;
    }

    public String getClassPreReqs() {
        return classPreReqs;
    }

    public void setClassPreReqs(String classPreReqs) {
        this.classPreReqs = classPreReqs;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public Double getMinCredits() {
        return minCredits;
    }

    public void setMinCredits(Double minCredits) {
        this.minCredits = minCredits;
    }

    public Double getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(Double maxCredits) {
        this.maxCredits = maxCredits;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    @JsonGetter
    public String getDvstyIntlDisplay()
    {
        if (!StringUtils.equals(getDvrstyIntl().trim(), ""))
        {

            if (Constants.INTERNATIONAL_DIVERSITY.containsKey(getDvrstyIntl()))
            {
                return Constants.INTERNATIONAL_DIVERSITY.get(getDvrstyIntl().trim());
            }
            return Constants.INTERNATIONAL_DIVERSITY_DEFAULT;
        }
        return "";
    }

    @JsonIgnore
    public String getDvrstyIntl() {
        return dvrstyIntl;
    }

    public void setDvrstyIntl(String dvrstyIntl) {
        this.dvrstyIntl = dvrstyIntl;
    }

    public String getNightExam() {
        return nightExam;
    }

    public void setNightExam(String nightExam) {
        this.nightExam = nightExam;
    }

    public String getSpclPerm() {
        return spclPerm;
    }

    public void setSpclPerm(String spclPerm) {
        this.spclPerm = spclPerm;
    }

    public String getMinorGradCred() {
        return minorGradCred;
    }

    public void setMinorGradCred(String minorGradCred) {
        this.minorGradCred = minorGradCred;
    }

    /*public String getPermFirstWeek() {
        return permFirstWeek;
    }

    public void setPermFirstWeek(String permFirstWeek) {
        this.permFirstWeek = permFirstWeek;
    }
*/
    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    @JsonIgnore
    public String getSeeDept() {
        return seeDept;
    }

    public void setSeeDept(String seeDept) {
        this.seeDept = seeDept;
    }

    @JsonIgnore
    public double getSpclFeeMax() {
        return spclFeeMax;
    }

    public void setSpclFeeMax(double spclFeeMax) {
        this.spclFeeMax = spclFeeMax;
    }

    @JsonIgnore
    public double getSpclFeeMin() {
        return spclFeeMin;
    }

    public void setSpclFeeMin(double spclFeeMin) {
        this.spclFeeMin = spclFeeMin;
    }

    public void findCreditInfo() {
        boolean isVariableCred = false;
        if (CollectionUtils.isEmpty(sections)) {
            return;
        }

        return;

    }

    protected void setCreditDetails(CreditType type, Double min, Double max) {
        setCreditType(type);
        setMaxCredits(max);
        setMinCredits(min);
    }

    public void addSection(Section section)
    {
        if (this.sections == null)
        {
            this.sections = new ArrayList<Section>();
        }

        this.sections.add(section);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @JsonIgnore
    public String getPermFirstWeek() {
        return permFirstWeek;
    }

    public void setPermFirstWeek(String permFirstWeek) {
        this.permFirstWeek = permFirstWeek;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", classTitle='" + classTitle + '\'' +
                ", sections='" + sections + '\'' +
                ", semesterCode='" + semesterCode + '\'' +
                ", semesterYear='" + semesterYear + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", classNumber='" + classNumber + '\'' +
                ", departmentTitle='" + departmentTitle + '\'' +
                ", classComments='" + classComments + '\'' +
                ", classPreReqs='" + classPreReqs + '\'' +
                ", semester=" + semester +
                ", sections=" + sections +
                ", creditType=" + creditType +
                ", minCredits=" + minCredits +
                ", maxCredits=" + maxCredits +
                ", dvrstyIntl='" + dvrstyIntl + '\'' +
                ", nightExam='" + nightExam + '\'' +
                ", spclPerm='" + spclPerm + '\'' +
                ", minorGradCred='" + minorGradCred + '\'' +
                ", permFirstWeek='" + permFirstWeek + '\'' +
                ", gradeType='" + gradeType + '\'' +
                ", seeDept='" + seeDept + '\'' +
                ", spclFeeMax=" + spclFeeMax +
                ", spclFeeMin=" + spclFeeMin +
                '}';
    }
}
