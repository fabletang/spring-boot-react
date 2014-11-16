package edu.iastate;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;


public final class Constants {
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    public static Map<String, String> WORKSHOP_FEES = new ImmutableMap.Builder<String, String>()
            .put("A", "Study Abroad admin fee applies")
            .put("B", "Tuition Category: Des Moines MBA")
            .put("C", "Tuition Category: Camp fee")
            .put("D", "Tuition Category: Engineer Dual Masters deg")
            .put("E", "Tuition Category: Des Moines M Ed")
            .put("G", "Tuition Category: Gulf Coast Research Lab")
            .put("I", "Tuition Category: Intl Education Courses")
            .put("L", "Tuition Category: Iowa Lakeside Lab")
            .put("K", "Tuition Category: Seed Science")
            .put("N", "Tuition Category: National Tech Univ")
            .put("P", "Tuition Category: Des Moines MPA")
            .put("R", "Tuition Category: Regents Summer Study Abroad")
            .put("S", "Tuition Category: Saturday MBA")
            .put("1", "Tuition Category: GPIDEA Consortium")
            .put("2", "Tuition Category: GPIDEA Consortium")
            .put("3", "Tuition Category: GPIDEA Consortium")
            .put("Y", "Tuition Category: Workshop fee applies")
            .put("4", "Tuition Category: Nuc E Consortium")
            .put("5", "Tuition Category: Nuc E Consortium")
            .put("6", "Tuition Category: Nuc E Consortium").build();

    public static final Map<String, String> DELIVERY_TYPES = new ImmutableMap.Builder<String, String>()
            .put("FF", "on-site instruction")
            .put("WWW", "WWW")
            .put("FF/WWW", "on-site instruction & WWW")
            .put("ICN/FF", "ICN & on-site instruction")
            .put("WWW/FF", "WWW & on-site instruction")
            .put("VT", "videotape")
            .put("IS", "independent study")
            .put("ICN", "ICN")
            .put("CDROM", "CD-ROM")
            .put("DVD", "DVD")
            .put("VS", "Videostream").build();

    public static final Map<String, String> SPECIAL_FEE_TYPES = new ImmutableMap.Builder<String, String>()
            .put("B", "Materials, Field Trips &amp; Travel Fee")
            .put("A", "Applied Music Fee")
            .put("E", "Equipment Use Fee")
            .put("F", "Field Trips &amp; Travel Fee")
            .put("J", "Professional Support and Field Trips &amp; Travel Fee")
            .put("M", "Materials Fee")
            .put("N", "Materials and Equipment Use Fee")
            .put("P", "Professional Support Fee")
            .put("Q", "Equipment Use and Field Trips &amp; Travel Fee")
            .put("R", "Materials and Professional Support Fee")
            .put("S", "Professional Support and Equipment Use Fee")
            .put("V", "Videotape Fee")
            .put("D", "Delivery Fee").build();

    public static final Map<String, String> FIRST_WEEK_MESSAGES = new ImmutableMap.Builder<String, String>()
            .put("I", "Instructor permission required 1st week.")
            .put("D", "Department permission required 1st week.")
            .put("C", "College permission required 1st week.")
            .put("B", "College permission required to add during 1st week; go to 1200 Gerdin.")
            .put("X", "Distance Ed students only. To register, call the Registrar''s Office at 515-294-1889.").build();

    public static final String FIRST_WEEK_MESSAGE_DEFAULT = "Special permission required for this course during 1st week.";

    public static final Map<String, String> SPECIAL_PERMISSIONS =  new ImmutableMap.Builder<String, String>()
            .put("I", "Instructor permission required.")
            .put("D", "Department permission required.")
            .put("C", "College permission required.")
            .put("R", "Permission of Coordinator required.")
            .put("H", "Permission of Department Chair required.")
            .put("E", "Permission of Adviser &amp; Career Services required; college will register student")
            .put("V", "Permission of Instructor required; process approved add slip in 2270A Vet Med")
            .put("W", "Permission of Department Chair required; process approved add slip in 2270A Vet Med")
            .put("L", "Permission of Lakeside Lab office required - HTTP://WWW.CONTINUETOLEARN.UIOWA.EDU/LAKESIDELAB")
            .put("B", "College permission required to add - Go to 1200 Gerdin")
            .put("A", "Permission of Adviser required.")
            .put("X", "Distance Ed students only. To register, call the Registrar's Office at 515-294-1889")
            .put("S", "").build();
    public static final String SPECIAL_PERMISSION_DEFAULT = "Special Permission Required for this course.";

    public static final Map<String, String> INTERNATIONAL_DIVERSITY = new ImmutableMap.Builder<String, String>()
            .put("I", "Meets International Perspectives Reqmt")
            .put("D", "Meets U.S. Diversity Reqmt").build();

    public static final String INTERNATIONAL_DIVERSITY_DEFAULT = "Meets International Perspectives/U.S Diversity Reqmt";

    public static final Map<String, String> DAYS = new ImmutableMap.Builder<String, String>()
            .put("M", "Monday")
            .put("T", "Tuesday")
            .put("W", "Wednesday")
            .put("R", "Thursday")
            .put("F", "Friday")
            .put("S", "Saturday").build();
}
