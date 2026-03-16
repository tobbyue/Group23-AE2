import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a part-time teacher in the system.
 *
 * Fields based on teachers.csv:
 *   id, name, skills (semicolon-separated), availability (semicolon-separated)
 *
 * TODO (Lu Liu): add business methods as needed (e.g. hasSkill, isAvailableOn)
 *
 * @author Lu Liu (Group 23)
 */
public class Teacher {

    private int id;
    private String name;
    private List<String> skills;        // e.g. ["Java", "Python", "SQL"]
    private List<String> availability;  // e.g. ["Mon", "Tue", "Wed"]

    // ---------- Constructors ----------

    public Teacher(int id, String name, List<String> skills, List<String> availability) {
        this.id = id;
        this.name = name;
        this.skills = new ArrayList<>(skills);
        this.availability = new ArrayList<>(availability);
    }

    // ---------- Getters & Setters ----------

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = new ArrayList<>(skills); }

    public List<String> getAvailability() { return availability; }
    public void setAvailability(List<String> availability) {
        this.availability = new ArrayList<>(availability);
    }

    // ---------- Convenience ----------

    /** Check whether this teacher possesses a given skill (case-insensitive). */
    public boolean hasSkill(String skill) {
        for (String s : skills) {
            if (s.equalsIgnoreCase(skill)) return true;
        }
        return false;
    }

    /** Check whether this teacher is available on a given day (case-insensitive). */
    public boolean isAvailableOn(String day) {
        for (String d : availability) {
            if (d.equalsIgnoreCase(day)) return true;
        }
        return false;
    }

    // ---- CSV helpers (used by FileHandler) ----

    /** Semicolon-joined skills string for CSV output. */
    public String skillsToString() {
        return String.join(";", skills);
    }

    /** Semicolon-joined availability string for CSV output. */
    public String availabilityToString() {
        return String.join(";", availability);
    }

    @Override
    public String toString() {
        return "Teacher{id=" + id + ", name='" + name + "', skills=" + skills
                + ", availability=" + availability + "}";
    }
}
