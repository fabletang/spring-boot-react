package edu.iastate.data;



import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;
import edu.iastate.Constants;
import edu.iastate.Utils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;


import java.io.Serializable;
import java.util.Date;
import java.text.NumberFormat;
import java.util.*;


public class Section implements Serializable {
    private static final long serialVersionUID = 3544958753020524083L;
    private int id;

    private String sectionID;

    /** section reference number (unique) */
    private String referenceNumber;


    /** comments for section */
    private String sectionComment;

    /** code for linking with other sections of same class */
    private String linkCode;

    private List<SectionTime> sectionTimes = new ArrayList<SectionTime>();

    private int openseats;

    private String creditTypeVal;

    private Double creditLow;

    private Double creditHigh;

    private Date startDateVal;

    private Date stopDateVal;

    private String specialFeeType;

    private double specialFeeAmt;

    private String syllabus;

    private String courseUrl;

    private String specialPermission;

    private String permissionFirstWeek;


    /** Meeting dates if session dates weren't available */
    private String meetDate;

    /** Workshop fee indicator */
    private String workshopFee;


    /** Off-campus location */
    private String offCampusLocation;


    /** Delivery type, face-to-face, online, ect. */
    private String deliveryType;


    /** Part semester comment, First 8 weeks, First half, ect. */
    private String partialSemesterComment;

    /** Secondary delivery type, face-to-face, online, etc. */
    private String secondaryDeliveryType;


    /** Computer requirement URL */
    private String computerRequirement;

    @JsonIgnore
    private List<Interval> meetingTimes;

    public Boolean conflicts(Section s)
    {
        if (s.getId() == this.getId())
        {
            return false;
        }

        if (!s.getSessionInterval().overlaps(this.getSessionInterval()))
        {
            return false;
        }

        for (Interval mt : s.getMeetingTimes())
        {
            for (Interval mt2: this.getMeetingTimes())
            {
                if (mt.overlap(mt2) != null)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Interval> getMeetingTimes()
    {
        if (meetingTimes == null)
        {
            List<Interval> tmpMeetingTimes = new ArrayList<Interval>();
            for (SectionTime st: sectionTimes)
            {
                tmpMeetingTimes.addAll(st.getMeetTimeIntervals());
            }
            meetingTimes = tmpMeetingTimes;
        }
        return meetingTimes;
    }

    @JsonGetter
    private String getWorkshopFeeDisplay()
    {
        String workshopFee = Utils.upper(getWorkshopFee());

        if (Constants.WORKSHOP_FEES.containsKey(workshopFee))
        {
            return Constants.WORKSHOP_FEES.get(workshopFee);
        }
        return "";
    }

    @JsonGetter
    public String getSecondaryDeliveryTypeDisplay()
    {
        if (!StringUtils.equals(getSecondaryDeliveryType().trim(), "")) {
            if (Constants.DELIVERY_TYPES.containsKey(getSecondaryDeliveryType().trim()))
            {
                return "Secondary Delivery is " + Constants.DELIVERY_TYPES.get(getSecondaryDeliveryType().trim());
            }
            else
            {
                return "Secondary Delivery is " + getSecondaryDeliveryType();
            }
        }

        return "";
    }

    @JsonGetter
    public String getDeliveryTypeDisplay()
    {
        if (!StringUtils.equals(getDeliveryType().trim(), "")) {
            if (Constants.DELIVERY_TYPES.containsKey(getDeliveryType().trim()))
            {
                return "Course Delivery is " + Constants.DELIVERY_TYPES.get(getDeliveryType().trim());
            }
            else
            {
                return "Course Delivery is " + getDeliveryType();
            }
        }

        return "";
    }

    @JsonGetter
    public String getSpecialFeeTypeDisplay(Course course)
    {
        if (  (this.getSpecialFeeAmt() != 0) ||
                ( (!StringUtils.equals(this.getSpecialFeeType(), ""))
                    && (course.getSpclFeeMax() != 0)
                    && (!(StringUtils.equals(this.getSectionID().substring(0,1), "X")) )
                )  )
        {
            double courseSpecialFeeMin = course.getSpclFeeMin();
            double courseSpecialFeeMax = course.getSpclFeeMax();


            String specialFeeType = this.getSpecialFeeType().toUpperCase().trim();


            String label = "Special Fee";
            if (Constants.SPECIAL_FEE_TYPES.containsKey(specialFeeType))
            {
                label = Constants.SPECIAL_FEE_TYPES.get(specialFeeType);
            }

            NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
            if ( (getSpecialFeeAmt() == 0) &&
                 (!(StringUtils.equals(this.getSectionID().substring(0,1), "X")) ) &&
                    (!(StringUtils.equals(this.getSpecialFeeType(), " ")) ) )
            {
                return label + ": " + n.format(course.getSpclFeeMin()) + " - " + n.format(course.getSpclFeeMax());
            }
            else
            {
                return label + ": " + n.format(getSpecialFeeAmt());
            }
        }
        return "";
    }


    @JsonGetter
    public String getMeetsDisplay()
    {
        if (StringUtils.equals(this.getMeetDate().trim(), "DZ"))
        {
            return "";
        }
        else if ( (StringUtils.equals(this.getPartialSemesterComment().trim(), ""))
                || (StringUtils.equals(this.getMeetDate().trim(), "")))
        {
            return this.getStartDateVal().toString() + " - " + this.getStopDateVal().toString();
        }
        else if ( (!(StringUtils.equals(this.getPartialSemesterComment().trim(), ""))) &&
                (!(StringUtils.equals(this.getMeetDate().trim(), "")))  )
        {
            return this.getMeetDate();
        }
        return "";
    }

    //@CachePut(value="sectionTime")
    public void addSectionTime(SectionTime st)
    {
        if (sectionTimes == null)
        {
            sectionTimes = new ArrayList<SectionTime>();
        }
        sectionTimes.add(st);
    }

    @JsonGetter
    public String getOffCampusDisplay()
    {
        if ( (getSectionID().length() > 0) && (StringUtils.equals(getSectionID().substring(0,1),"X")  )  )
        {
            return "Section " + getSectionID() + " is off campus";
        }
        return "";
    }

    @JsonGetter
    public String getSpecialPermissionRequirementsDisplay()
    {
        String specialPermission = StringUtils.upperCase(getSpecialPermission());
        if (!StringUtils.equals(specialPermission.trim(), "")) {
            if (!(StringUtils.equals(this.getPermissionFirstWeek(), ""))) {

                if (Constants.FIRST_WEEK_MESSAGES.containsKey(specialPermission)) {
                    return Constants.FIRST_WEEK_MESSAGES.get(specialPermission);
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


    @JsonGetter
    public String getBookStoreUrl(Course course)
    {
        String bookUrl = "http://www.isubookstore.com/SelectCourses.aspx?src=2&type=2&stoid=130&trm=" + course.getSemester().getSemesterName() + "%20";
        bookUrl += course.getSemester().getShortYear() + "&cid=" + this.getReferenceNumber();
        return bookUrl;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionComment() {
        return sectionComment;
    }

    public void setSectionComment(String sectionComment) {
        this.sectionComment = sectionComment;
    }

    public String getLinkCode() {
        return linkCode;
    }

    public void setLinkCode(String linkCode) {
        this.linkCode = linkCode;
    }

    public int getOpenseats() {
        return openseats;
    }

    public void setOpenseats(int openseats) {
        this.openseats = openseats;
    }

    public String getCreditTypeVal() {
        return creditTypeVal;
    }

    public void setCreditTypeVal(String creditType) {
        this.creditTypeVal = creditType;
    }
    public CreditType getCreditType() {
        return CreditType.fromCode(getCreditTypeVal());
    }

    public void setCreditType(CreditType creditType) {
        this.creditTypeVal = creditType.getCode();
    }

    public Double getCreditLow() {
        return creditLow;
    }

    public Double getCreditHigh() {
        return creditHigh;
    }

    public Collection<SectionTime> getSectionTimes() {
        return sectionTimes;
    }
/*
    @JsonGetter
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    public Collection<SectionTime> getSectionTimesEager() {
        return sectionTimes;
    }
*/

    public void setSectionTimes(List<SectionTime> sectionTimes) {
        this.sectionTimes = sectionTimes;
    }

    public LocalDate getStartDate() {
        return LocalDate.fromDateFields(getStartDateVal());
    }

    public Date getStartDateVal() {
        return startDateVal;
    }

    public void setStartDateVal(Date startDateVal) {
        this.startDateVal = startDateVal;
    }

    public LocalDate getStopDate() {
        return LocalDate.fromDateFields(getStopDateVal());
    }

    public Date getStopDateVal() {
        return stopDateVal;
    }

    public void setStopDateVal(Date stopDateVal) {
        this.stopDateVal = stopDateVal;
    }

    @JsonIgnore
    public Interval getSessionInterval() {
        return new Interval(new DateTime(this.startDateVal), new DateTime(this.getStopDateVal()));
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setCreditLow(Double creditLow) {
        this.creditLow = creditLow;
    }

    public void setCreditHigh(Double creditHigh) {
        this.creditHigh = creditHigh;
    }

    @JsonIgnore
    public String getSpecialFeeType() {
        return specialFeeType;
    }

    public void setSpecialFeeType(String specialFeeType) {
        this.specialFeeType = specialFeeType;
    }

    @JsonIgnore
    public double getSpecialFeeAmt() {
        return specialFeeAmt;
    }

    public void setSpecialFeeAmt(double specialFeeAmt) {
        this.specialFeeAmt = specialFeeAmt;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getSpecialPermission() {
        return specialPermission;
    }

    public void setSpecialPermission(String specialPermission) {
        this.specialPermission = specialPermission;
    }

    public String getPermissionFirstWeek() {
        return permissionFirstWeek;
    }

    public void setPermissionFirstWeek(String permissionFirstWeek) {
        this.permissionFirstWeek = permissionFirstWeek;
    }

    public String getMeetDate() {
        return meetDate;
    }

    public void setMeetDate(String meetDate) {
        this.meetDate = meetDate;
    }

    @JsonIgnore
    public String getWorkshopFee() {
        return workshopFee;
    }

    public void setWorkshopFee(String workshopFee) {
        this.workshopFee = workshopFee;
    }

    public String getOffCampusLocation() {
        return offCampusLocation;
    }

    public void setOffCampusLocation(String offCampusLocation) {
        this.offCampusLocation = offCampusLocation;
    }

    @JsonIgnore
    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getPartialSemesterComment() {
        return partialSemesterComment;
    }

    public void setPartialSemesterComment(String partialSemesterComment) {
        this.partialSemesterComment = partialSemesterComment;
    }

    public String getSecondaryDeliveryType() {
        return secondaryDeliveryType;
    }

    public void setSecondaryDeliveryType(String secondaryDeliveryType) {
        this.secondaryDeliveryType = secondaryDeliveryType;
    }

    public String getComputerRequirement() {
        return computerRequirement;
    }

    public void setComputerRequirement(String computerRequirement) {
        this.computerRequirement = computerRequirement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", sectionID='" + sectionID + '\'' +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", sectionComment='" + sectionComment + '\'' +
                ", linkCode='" + linkCode + '\'' +
                ", sectionTimes=" + sectionTimes +
                ", openseats=" + openseats +
                ", creditTypeVal='" + creditTypeVal + '\'' +
                ", creditLow=" + creditLow +
                ", creditHigh=" + creditHigh +
                ", startDateVal=" + startDateVal +
                ", stopDateVal=" + stopDateVal +
                ", specialFeeType='" + specialFeeType + '\'' +
                ", specialFeeAmt=" + specialFeeAmt +
                ", syllabus='" + syllabus + '\'' +
                ", courseUrl='" + courseUrl + '\'' +
                ", specialPermission='" + specialPermission + '\'' +
                ", permissionFirstWeek='" + permissionFirstWeek + '\'' +
                ", meetDate='" + meetDate + '\'' +
                ", workshopFee='" + workshopFee + '\'' +
                ", offCampusLocation='" + offCampusLocation + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", partialSemesterComment='" + partialSemesterComment + '\'' +
                ", secondaryDeliveryType='" + secondaryDeliveryType + '\'' +
                ", computerRequirement='" + computerRequirement + '\'' +
                ", meetingTimes=" + meetingTimes +
                '}';
    }
}
