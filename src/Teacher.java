import java.util.ArrayList;
import java.util.List;

/**
 * Represents a part-time teacher in the system.
 *
 * CSV format (teachers.csv) — defined by the group data spec:
 *   id,name,skills,availability
 *   e.g.  1,John Smith,Java;Python;SQL,Mon;Tue;Wed
 *
 * Design notes:
 * - id is an auto-incremented int, assigned by TeacherManager.
 * - Multiple skills and availability slots are stored as semicolon-separated
 *   strings within a single CSV field to avoid conflicts with the comma
 *   delimiter (assumption documented in group data spec).
 * - This class is a pure data model with no I/O responsibility (low coupling).
 */
public class Teacher {

    private int id;
    private String name;
    private List<String> skills;       // e.g. ["Java", "Python", "SQL"]
    private List<String> availability; // e.g. ["Mon", "Wed", "Fri"]

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Full constructor — used when loading from file (id already known).
     */
    public Teacher(int id, String name, List<String> skills, List<String> availability) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher name cannot be null or empty.");
        }
        this.id           = id;
        this.name         = name.trim();
        this.skills       = (skills != null)       ? new ArrayList<>(skills)       : new ArrayList<>();
        this.availability = (availability != null) ? new ArrayList<>(availability) : new ArrayList<>();
    }

    /**
     * Convenience constructor — used when creating a new teacher via UI.
     * The id will be set later by TeacherManager.
     */
    public Teacher(String name) {
        this(0, name, new ArrayList<>(), new ArrayList<>());
    }

    // -------------------------------------------------------------------------
    // Skill management
    // -------------------------------------------------------------------------

    /** Adds a skill if not already present (case-insensitive check). */
    public void addSkill(String skill) {
        if (skill == null || skill.trim().isEmpty()) return;
        String normalised = skill.trim();
        for (String s : skills) {
            if (s.equalsIgnoreCase(normalised)) return;
        }
        skills.add(normalised);
    }

    /** Removes a skill (case-insensitive). Returns true if the skill was found and removed. */
    public boolean removeSkill(String skill) {
        if (skill == null) return false;
        return skills.removeIf(s -> s.equalsIgnoreCase(skill.trim()));
    }

    /** Returns true if this teacher has the specified skill (case-insensitive). */
    public boolean hasSkill(String skill) {
        if (skill == null) return false;
        for (String s : skills) {
            if (s.equalsIgnoreCase(skill.trim())) return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------
    // Availability management
    // -------------------------------------------------------------------------

    /** Adds an availability slot if not already present. */
    public void addAvailability(String slot) {
        if (slot == null || slot.trim().isEmpty()) return;
        String normalised = slot.trim();
        if (!availability.contains(normalised)) {
            availability.add(normalised);
        }
    }

    /** Removes an availability slot. Returns true if it was found and removed. */
    public boolean removeAvailability(String slot) {
        if (slot == null) return false;
        return availability.remove(slot.trim());
    }

    /** Returns true if this teacher is available on the given day (case-insensitive). */
    public boolean isAvailableOn(String day) {
        if (day == null) return false;
        for (String slot : availability) {
            if (slot.equalsIgnoreCase(day.trim())) return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------
    // CSV serialisation — aligned with group data file spec
    //
    // Format:  id,name,skills,availability
    //          1,John Smith,Java;Python;SQL,Mon;Tue;Wed
    //
    // Semicolons separate values within a field; commas separate fields.
    // -------------------------------------------------------------------------

    /**
     * Serialises this Teacher to a single CSV line matching teachers.csv spec.
     */
    public String toCSV() {
        String skillStr = String.join(";", skills);
        String availStr = String.join(";", availability);
        return id + "," + name + "," + skillStr + "," + availStr;
    }

    /**
     * Parses a CSV data line (not the header row) from teachers.csv.
     *
     * Expected format:  id,name,skills,availability
     *
     * @throws IllegalArgumentException if the line is malformed.
     */
    public static Teacher fromCSV(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            throw new IllegalArgumentException("CSV line is null or empty.");
        }
        String[] parts = csvLine.split(",", 4);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Malformed CSV line (expected 4 fields): " + csvLine);
        }
        int id;
        try {
            id = Integer.parseInt(parts[0].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid teacher id: " + parts[0]);
        }

        String name = parts[1].trim();

        List<String> skills = new ArrayList<>();
        if (!parts[2].trim().isEmpty()) {
            for (String s : parts[2].trim().split(";")) {
                if (!s.trim().isEmpty()) skills.add(s.trim());
            }
        }

        List<String> availability = new ArrayList<>();
        if (!parts[3].trim().isEmpty()) {
            for (String a : parts[3].trim().split(";")) {
                if (!a.trim().isEmpty()) availability.add(a.trim());
            }
        }

        return new Teacher(id, name, skills, availability);
    }

    // -------------------------------------------------------------------------
    // Display
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("Teacher{id=%d, name='%s', skills=%s, availability=%s}",
                id, name, skills, availability);
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    public int getId()                    { return id; }
    /** Called by TeacherManager when assigning an auto-incremented id. */
    public void setId(int id)             { this.id = id; }

    public String getName()               { return name; }
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) this.name = name.trim();
    }

    /** Returns a defensive copy of the skills list. */
    public List<String> getSkills()       { return new ArrayList<>(skills); }

    /** Returns a defensive copy of the availability list. */
    public List<String> getAvailability() { return new ArrayList<>(availability); }

    // -------------------------------------------------------------------------
    // CSV helper aliases — used by FileHandler.saveTeachers()
    // -------------------------------------------------------------------------

    /** Semicolon-joined skills string for CSV output. */
    public String skillsToString()       { return String.join(";", skills); }

    /** Semicolon-joined availability string for CSV output. */
    public String availabilityToString() { return String.join(";", availability); }
}
