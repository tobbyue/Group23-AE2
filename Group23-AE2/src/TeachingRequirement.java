/**
 * Represents a teaching requirement submitted by a course director.
 *
 * Fields based on requirements.csv:
 *   id, courseName, semester, requiredSkill, dayOfWeek, status
 *
 * TODO (Yanyu Ge): add business methods as needed
 *
 * @author Yanyu Ge (Group 23)
 */
public class TeachingRequirement {

    /** Status constants */
    public static final String STATUS_OPEN     = "OPEN";
    public static final String STATUS_ASSIGNED = "ASSIGNED";

    private int id;
    private String courseName;
    private String semester;       // e.g. "2026-S1"
    private String requiredSkill;  // the skill needed for this course
    private String dayOfWeek;      // e.g. "Mon"
    private String status;         // OPEN or ASSIGNED

    // ---------- Constructors ----------

    public TeachingRequirement(int id, String courseName, String semester,
                               String requiredSkill, String dayOfWeek, String status) {
        this.id = id;
        this.courseName = courseName;
        this.semester = semester;
        this.requiredSkill = requiredSkill;
        this.dayOfWeek = dayOfWeek;
        this.status = status;
    }

    // ---------- Getters & Setters ----------

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getRequiredSkill() { return requiredSkill; }
    public void setRequiredSkill(String requiredSkill) { this.requiredSkill = requiredSkill; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isOpen() { return STATUS_OPEN.equals(status); }

    @Override
    public String toString() {
        return "TeachingRequirement{id=" + id + ", course='" + courseName
                + "', semester='" + semester + "', skill='" + requiredSkill
                + "', day='" + dayOfWeek + "', status='" + status + "'}";
    }
}
