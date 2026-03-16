import java.util.Arrays;
import java.util.List;

/**
 * Represents a teaching requirement (course that needs a teacher).
 *
 * Fields based on requirements.csv:
 *   id, courseName, semester, requiredSkill, dayOfWeek, status
 *
 * Status values: OPEN (not yet assigned), ASSIGNED (teacher allocated)
 *
 * @author Yanyu Ge (Group 23)
 */
public class TeachingRequirement {

    /** Allowed status values. */
    public static final String STATUS_OPEN     = "OPEN";
    public static final String STATUS_ASSIGNED = "ASSIGNED";

    private static final List<String> VALID_DAYS =
            Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri");

    private int    id;
    private String courseName;
    private String semester;       // e.g. "2026-S1"
    private String requiredSkill;  // e.g. "Java"
    private String dayOfWeek;      // e.g. "Mon"
    private String status;         // OPEN or ASSIGNED

    // ---------- Constructors ----------

    public TeachingRequirement(int id, String courseName, String semester,
                               String requiredSkill, String dayOfWeek, String status) {
        this.id            = id;
        this.courseName    = courseName;
        this.semester      = semester;
        this.requiredSkill = requiredSkill;
        setDayOfWeek(dayOfWeek);
        setStatus(status);
    }

    /** Convenience constructor — defaults status to OPEN. */
    public TeachingRequirement(int id, String courseName, String semester,
                               String requiredSkill, String dayOfWeek) {
        this(id, courseName, semester, requiredSkill, dayOfWeek, STATUS_OPEN);
    }

    // ---------- Getters & Setters ----------

    public int    getId()            { return id; }
    public void   setId(int id)      { this.id = id; }

    public String getCourseName()    { return courseName; }
    public void   setCourseName(String courseName) { this.courseName = courseName; }

    public String getSemester()      { return semester; }
    public void   setSemester(String semester) { this.semester = semester; }

    public String getRequiredSkill() { return requiredSkill; }
    public void   setRequiredSkill(String requiredSkill) { this.requiredSkill = requiredSkill; }

    public String getDayOfWeek()     { return dayOfWeek; }
    public void   setDayOfWeek(String dayOfWeek) {
        if (!VALID_DAYS.contains(dayOfWeek)) {
            System.out.println("Warning: unexpected day '" + dayOfWeek
                    + "'. Expected one of " + VALID_DAYS);
        }
        this.dayOfWeek = dayOfWeek;
    }

    public String getStatus()        { return status; }
    public void   setStatus(String status) {
        if (!STATUS_OPEN.equals(status) && !STATUS_ASSIGNED.equals(status)) {
            System.out.println("Warning: unexpected status '" + status
                    + "'. Expected OPEN or ASSIGNED.");
        }
        this.status = status;
    }

    // ---------- Convenience ----------

    /** Return true if this requirement has not yet been assigned. */
    public boolean isOpen() {
        return STATUS_OPEN.equals(status);
    }

    /** Mark this requirement as assigned. */
    public void markAssigned() {
        this.status = STATUS_ASSIGNED;
    }

    /** Mark this requirement as open (e.g. after un-assigning). */
    public void markOpen() {
        this.status = STATUS_OPEN;
    }

    @Override
    public String toString() {
        return "TeachingRequirement{id=" + id
                + ", course='" + courseName + "'"
                + ", semester='" + semester + "'"
                + ", skill='" + requiredSkill + "'"
                + ", day='" + dayOfWeek + "'"
                + ", status='" + status + "'}";
    }
}
