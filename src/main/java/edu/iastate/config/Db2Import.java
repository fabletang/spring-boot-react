package edu.iastate.config;

import edu.iastate.Constants;
import edu.iastate.data.Course;
import edu.iastate.data.Section;
import edu.iastate.data.SectionTime;
import edu.iastate.data.Semester;
import edu.iastate.repository.CourseRepository;
import edu.iastate.repository.SemesterRepository;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class Db2Import {

    @Autowired
    DatabaseConfiguration config;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    //@PostConstruct
    public void initialImport()
    {
        importData();
    }

    public void importData() {
        Connection conn = null;
        //Connection mysqlConn = null;
        try {
            DataSource tmpDb2DataSource = config.db2Data();
//            DataSource tmpDb2DataSource = config.dataSource();
            //DataSource localSource = config.dataSource();

            conn = tmpDb2DataSource.getConnection();
            //mysqlConn = localSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            courseRepository.deleteAll();

            String semesterSQL = "Select Distinct DATETBL.SEM_CD, " +
                    "DATETBL.SEM_CCYY, " +
                    "DATETBL.PRLM_SCHED_FLAG, " +
                    "LAST_CHNG_TMS " +
                    "FROM RGSTN.CTRL_DATE AS DATETBL, " +
                    "RGSTN.CRSE_PLAN AS PLAN " +
                    "WHERE DATETBL.SEM_CD = PLAN.SEM_CD " +
                    "AND DATETBL.SEM_CCYY = PLAN.SEM_CCYY " +
                    "ORDER BY SEM_CCYY";
            PreparedStatement semesters = conn.prepareStatement(semesterSQL);

            Map<Map<String, String>, Integer> semesterIdMap = new HashMap<Map<String, String>, Integer>();
            Map<Integer, Semester> semesterMap = new HashMap<Integer, Semester>();

            Integer i = 1;
            ResultSet semesterResult = semesters.executeQuery();

            while (semesterResult.next())
            {
                String code = semesterResult.getString("SEM_CD");
                String prelim = semesterResult.getString("PRLM_SCHED_FLAG");
                String yr = semesterResult.getString("SEM_CCYY");
                Timestamp lastChange = semesterResult.getTimestamp("LAST_CHNG_TMS");

                Map<String, String> idComposite = new HashMap<String, String>();
                idComposite.put("year", yr);
                idComposite.put("semCd", code);

                semesterIdMap.put(idComposite, i);

                Semester semester = new Semester();
                semester.setId(i);
                semester.setCode(code);
                semester.setYear(yr);
                semester.setPreliminary(prelim);
                semester.setLastChanged(lastChange);
                if ((LocalDate.now().getMonthOfYear() >= 2) && (LocalDate.now().getMonthOfYear() <= 8) && StringUtils.equals(code, "F"))
                {
                    semester.setCurrent("Y");
                }
                else if ((LocalDate.now().getMonthOfYear() >= 9) && (LocalDate.now().getMonthOfYear() <= 12) && StringUtils.equals(code, "S"))
                {
                    semester.setCurrent("Y");
                }
                else
                {
                    semester.setCurrent("N");
                }

                semesterMap.put(i, semester);

                i++;
            }

            boolean currentSet = false;
            Semester summerSemester = null;
            for (Semester semester: semesterMap.values())
            {
                if (StringUtils.equals("Y", semester.getCurrent()))
                {
                    currentSet = true;
                }
                if (StringUtils.equals(semester.getCode(), "1"))
                {
                    summerSemester = semester;
                }
            }

            if (!currentSet && summerSemester != null )
            {
                summerSemester.setCurrent("Y");
            }

            PreparedStatement courses = conn.prepareStatement("SELECT DISTINCT SEM_CD, \n" +
                    "       SEM_CCYY, \n" +
                    "       OFFER_DEPT_ABRVN,\n" +
                    "       CRSE,\n" +
                    "       DEPT_TITLE,\n" +
                    "       RTRIM(SUBSTR(CRSE_CMNT, 1, 3000)) as CRSE_CMNT,\n" +
                    "       CRSE_TITLE,\n" +
                    "       PRE_REQ,\n" +
                    "       DVRST_INTNL_CD,\n" +
                    "\t   NIGHT_EXAM, \n" +
                    "\t   CRSE_SPCL_PRMSN,\n" +
                    "\t   MINOR_GRAD_CR,\n" +
                    "\t   PRMSN_1ST_WEEK,\n" +
                    "\t   GRADE_TYPE, \n" +
                    "\t   SEE_DEPT_IND, \n" +
                    "\t   SPCL_FEE_MAX, \n" +
                    "\t   SPCL_FEE_MIN \n" +
                    "FROM RGSTN.CRSE_PLAN");
            ResultSet courseResult = courses.executeQuery();


            Integer courseId = 0;
            Map<Map<String, String>, Integer> courseIdMap = new HashMap<Map<String, String>, Integer>();
            Map<Integer, Course> courseMap = new HashMap<Integer, Course>();
            while (courseResult.next())
            {
                courseId++;

                String semCd = courseResult.getString("SEM_CD");
                Integer semYY = courseResult.getInt("SEM_CCYY");
                String deptCode = courseResult.getString("OFFER_DEPT_ABRVN");
                String classNumber = courseResult.getString("CRSE");
                String departmentTitle = courseResult.getString("DEPT_TITLE");
                String classComments = courseResult.getString("CRSE_CMNT");
                String classTitle = courseResult.getString("CRSE_TITLE");
                String preReq = courseResult.getString("PRE_REQ");
                String dvrstyIntl = courseResult.getString("DVRST_INTNL_CD");
                String nightExam = courseResult.getString("NIGHT_EXAM");
                String courseSpecialPermissions = courseResult.getString("CRSE_SPCL_PRMSN");
                String minorGradCredit = courseResult.getString("MINOR_GRAD_CR");
                String gradeType = courseResult.getString("GRADE_TYPE");
                String seeDeptInd = courseResult.getString("SEE_DEPT_IND");

                String firstWeek = courseResult.getString("PRMSN_1ST_WEEK");

                Double specialFeeMin = courseResult.getDouble("SPCL_FEE_MIN");
                Double specialFeeMax = courseResult.getDouble("SPCL_FEE_MAX");



                Map<String, String> semesterIdComposite = new HashMap<String, String>();
                semesterIdComposite.put("semCd", semCd);
                semesterIdComposite.put("year", semYY.toString());

                Semester semester = semesterMap.get(semesterIdMap.get(semesterIdComposite));


                Map<String, String> idComposite = new HashMap<String, String>();
                idComposite.put("semCd", semCd);
                idComposite.put("year", semYY.toString());
                idComposite.put("course", classNumber);
                idComposite.put("deptCode", deptCode);

                Course course = new Course();
                //course.setId(courseId);
                course.setDeptCode(deptCode);
                course.setDepartmentTitle(departmentTitle);
                course.setClassTitle(classTitle);
                course.setClassNumber(classNumber);
                course.setSemesterCode(semCd);
                course.setSemesterYear(semYY.toString());
                course.setTerm(semCd + semYY.toString());
                course.setClassComments(classComments);
                course.setClassPreReqs(preReq);
                course.setDvrstyIntl(dvrstyIntl);
                course.setNightExam(nightExam);
                course.setSpclPerm(courseSpecialPermissions);
                course.setGradeType(gradeType);
                course.setSeeDept(seeDeptInd);
                course.setSpclFeeMin(specialFeeMin);
                course.setSpclFeeMax(specialFeeMax);
                course.setMinorGradCred(minorGradCredit);
                course.setSemester(semester);
                course.setPermFirstWeek(firstWeek);


                courseMap.put(courseId, course);

                //semester.addCourse(course);

                courseIdMap.put(idComposite, courseId);
            }


            PreparedStatement sections = conn.prepareStatement("SELECT DISTINCT SEM_CD, \n" +
                    "       SEM_CCYY, \n" +
                    "       OFFER_DEPT_ABRVN,\n" +
                    "       CRSE,\n" +
                    "       SECT,\n" +
                    "       SECT_REF_NUM,\n" +
                    "       RTRIM(SUBSTR(SECT_CMNT, 1, 3000)) as SECT_CMNT,\n" +
                    "       LINK_CD,\n" +
                    "       OPEN_SEAT_NUM,\n" +
                    "       CREDIT_TYPE,\n" +
                    "       CREDIT_LOW,\n" +
                    "       CREDIT_HIGH,\n" +
                    "       SESS_START_DATEDB,\n" +
                    "       SESS_STOP_DATEDB,\n" +
                    "       SPECIAL_FEE,\n" +
                    "       SPCL_FEE_AMT,\n" +
                    "       SYLBS_URL,\n" +
                    "       CRSE_URL,\n" +
                    "       SECT_SPCL_PRMSN,\n" +
                    "       MEET_DATE,\n" +
                    "       WKSHP_FEE,\n" +
                    "       OC_LOC,\n" +
                    "       DLVRY_TYPE,\n" +
                    "       PART_SEM_CMNT,\n" +
                    "       SCNDRY_DLVRY_TYPE,\n" +
                    "       CMPTR_REQMT_URL\n" +
                    "FROM RGSTN.CRSE_PLAN");

            ResultSet sectionResult = sections.executeQuery();

            Integer sectionId = 0;
            Integer sectionCourseId = 0;
            Map<Map<String, String>, Integer> sectionIdMap = new HashMap<Map<String, String>, Integer>();
            Map<Integer, Section> sectionMap = new HashMap<Integer, Section>();

            //DateTimeFormat.forPattern("1/1/70 0:00")



            while (sectionResult.next())
            {
                sectionId++;

                String semCd = sectionResult.getString("SEM_CD");
                Integer semYY = sectionResult.getInt("SEM_CCYY");
                String deptCode = sectionResult.getString("OFFER_DEPT_ABRVN");
                String course = sectionResult.getString("CRSE");
                String sect = sectionResult.getString("SECT");
                String linkCode = sectionResult.getString("LINK_CD");
                Integer openSeats = sectionResult.getInt("OPEN_SEAT_NUM");
                String creditType = sectionResult.getString("CREDIT_TYPE");
                Double creditLow = sectionResult.getDouble("CREDIT_LOW");
                Double creditHigh = sectionResult.getDouble("CREDIT_HIGH");
                Date startDate = sectionResult.getDate("SESS_START_DATEDB");
                Date stopDate = sectionResult.getDate("SESS_STOP_DATEDB");
                String specialFeeFlag = sectionResult.getString("SPECIAL_FEE");
                Integer specialFee = sectionResult.getInt("SPCL_FEE_AMT");
                String syllabusUrl = sectionResult.getString("SYLBS_URL");
                String courseUrl = sectionResult.getString("CRSE_URL");

                String sectCmnt = sectionResult.getString("SECT_CMNT");
                String sectSpecialPermissions = sectionResult.getString("SECT_SPCL_PRMSN");
                String meetDate = sectionResult.getString("MEET_DATE");
                String workshopFee = sectionResult.getString("WKSHP_FEE");
                String ocLocation = sectionResult.getString("OC_LOC");
                String deliveryType = sectionResult.getString("DLVRY_TYPE");
                String partialSemesterComment = sectionResult.getString("PART_SEM_CMNT");
                String secondaryDeliveryType = sectionResult.getString("SCNDRY_DLVRY_TYPE");
                String computerRequirementUrl = sectionResult.getString("CMPTR_REQMT_URL");
                String referenceNumber = sectionResult.getString("SECT_REF_NUM");


                Map<String, String> courseIdComposite = new HashMap<String, String>();
                courseIdComposite.put("semCd", semCd);
                courseIdComposite.put("year", semYY.toString());
                courseIdComposite.put("course", course);
                courseIdComposite.put("deptCode", deptCode);

                Map<String, String> sectionIdComposite = new HashMap<String, String>();
                sectionIdComposite.put("semCd", semCd);
                sectionIdComposite.put("year", semYY.toString());
                sectionIdComposite.put("course", course);
                sectionIdComposite.put("deptCode", deptCode);
                sectionIdComposite.put("sect", sect);

                sectionIdMap.put(sectionIdComposite, sectionId);

                sectionCourseId = courseIdMap.get(courseIdComposite);

                Section section = new Section();
                section.setId(sectionId);
                section.setSectionID(sect);
                section.setOpenseats(openSeats);
                section.setCreditTypeVal(creditType);
                section.setCreditLow(creditLow);
                section.setCreditHigh(creditHigh);
                section.setSpecialFeeAmt(specialFee);
                section.setSpecialFeeType(specialFeeFlag);
                section.setSectionComment(sectCmnt);
                section.setLinkCode(linkCode);
                section.setSyllabus(syllabusUrl);
                section.setCourseUrl(courseUrl);
                section.setSpecialPermission(sectSpecialPermissions);
                section.setMeetDate(meetDate);
                section.setWorkshopFee(workshopFee);
                section.setOffCampusLocation(ocLocation);
                section.setDeliveryType(deliveryType);
                section.setPartialSemesterComment(partialSemesterComment);
                section.setSecondaryDeliveryType(secondaryDeliveryType);
                section.setStartDateVal(startDate);
                section.setStopDateVal(stopDate);
                section.setComputerRequirement(computerRequirementUrl);
                section.setReferenceNumber(referenceNumber);


                Course tmpCourse = courseMap.get(sectionCourseId);
                tmpCourse.addSection(section);

                //section.setCourse(tmpCourse);
                sectionMap.put(sectionId, section);
            }

            PreparedStatement sectionTimes = conn.prepareStatement("SELECT DISTINCT SEM_CD, \n" +
                    "       SEM_CCYY, \n" +
                    "       OFFER_DEPT_ABRVN,\n" +
                    "       CRSE,\n" +
                    "       SECT,\n" +
                    "       SECT_SEQ_NUM,\n" +
                    "       TM_INSTRN_TYPE,\n" +
                    "       TM_START_TMSDB,\n" +
                    "       TM_STOP_TMSDB,\n" +
                    "       TM_DA_WEEK,\n" +
                    "       INSTR_NAME,\n" +
                    "       ROOM_BLDG_NAME_1 as ROOM_BLDG_NAME,\n" +
                    "       ROOM_NUM_1 as ROOM_NUM\n" +
                    "FROM RGSTN.CRSE_PLAN");
            ResultSet sectionTimeResult = sectionTimes.executeQuery();



            List<SectionTime> sectTimeList = new ArrayList<SectionTime>();
            Integer sectionTimeSectionId = 0;
            Integer sectionTimeId = 0;
            while (sectionTimeResult.next())
            {
                sectionTimeId++;

                String semCd = sectionTimeResult.getString("SEM_CD");
                Integer semYY = sectionTimeResult.getInt("SEM_CCYY");
                String deptCode = sectionTimeResult.getString("OFFER_DEPT_ABRVN");
                String course = sectionTimeResult.getString("CRSE");
                String sect = sectionTimeResult.getString("SECT");
                String seqNumStr = sectionTimeResult.getString("SECT_SEQ_NUM");
                Integer seqNumber = sectionTimeResult.getInt("SECT_SEQ_NUM");
                String daysOfWeek = sectionTimeResult.getString("TM_DA_WEEK");

                Time startTime = sectionTimeResult.getTime("TM_START_TMSDB");
                Time stopTime = sectionTimeResult.getTime("TM_STOP_TMSDB");


                String instructionType =sectionTimeResult.getString("TM_INSTRN_TYPE");
                String instructorName = sectionTimeResult.getString("INSTR_NAME");
                String buildingName = sectionTimeResult.getString("ROOM_BLDG_NAME");
                String buildingNumber = sectionTimeResult.getString("ROOM_NUM");


                Map<String, String> sectionIdComposite = new HashMap<String, String>();
                sectionIdComposite.put("semCd", semCd);
                sectionIdComposite.put("year", semYY.toString());
                sectionIdComposite.put("course", course);
                sectionIdComposite.put("deptCode", deptCode);
                sectionIdComposite.put("sect", sect);

                sectionTimeSectionId = sectionIdMap.get(sectionIdComposite);

                SectionTime st = new SectionTime();
                st.setId(sectionTimeId);
                st.setSectionSequenceNumber(seqNumber);

                for (Map.Entry<String, String> map : Constants.DAYS.entrySet())
                {
                    if (StringUtils.contains(daysOfWeek, map.getKey()))
                    {
                        //st.
                        String key = map.getKey();
                        String value = map.getValue();
                        try {
                            Method m = st.getClass().getMethod("set" + value, Integer.class);
                            try {
                                m.invoke(st, 1);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }

                st.setMeetDays(daysOfWeek);

                st.setStartTimeVal(startTime);
                st.setStopTimeVal(stopTime);

                st.setInstructionType(instructionType);
                st.setInstrName(instructorName);
                st.setBuildingName(buildingName);
                st.setRoomNum(buildingNumber);


                Section tmpSection = sectionMap.get(sectionTimeSectionId);
                tmpSection.addSectionTime(st);

                //st.setSection(tmpSection);
                sectTimeList.add(st);
            }

            String tmpType;
            for (Semester tmpSemester : semesterMap.values()) {
                semesterRepository.save(tmpSemester);
            }

            for (Course tmpCourse : courseMap.values()) {
                courseRepository.save(tmpCourse);
            }

            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
