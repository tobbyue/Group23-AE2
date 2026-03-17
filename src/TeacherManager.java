import java.util.ArrayList;
import java.util.List;

/**
 * Manages the in-memory collection of {@link Teacher} objects.
 *
 * Responsibilities:
 *   - CRUD operations: add, get, update, remove, search.
 *   - Auto-increment teacher IDs.
 *   - Act as the single source of truth for teacher data during a session.
 *
 * I/O responsibility is deliberately kept OUT of this class.
 * Loading and saving is handled by FileHandler (owned by Minghao Yue),
 * which calls {@link #setTeachers(List)} on startup and
 * {@link #getAllTeachers()} on shutdown.
 *
 * Design principle: low coupling — TeacherManager knows nothing about
 * FileHandler, UI, or any other manager class.
 */
public class TeacherManager {

    private List<Teacher> teachers;
    private int nextId;   // auto-increment counter

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public TeacherManager() {
        this.teachers = new ArrayList<>();
        this.nextId   = 1;
    }

    // -------------------------------------------------------------------------
    // Initialisation — called by FileHandler after loading teachers.csv
    // -------------------------------------------------------------------------

    /**
     * Replaces the in-memory list with data loaded from file.
     * Also resets the id counter so new teachers receive ids that continue
     * from the highest existing id.
     *
     * @param loaded list returned by FileHandler.loadTeachers()
     */
    public void setTeachers(List<Teacher> loaded) {
        this.teachers = (loaded != null) ? new ArrayList<>(loaded) : new ArrayList<>();
        // Find the current maximum id so auto-increment continues correctly
        int maxId = 0;
        for (Teacher t : this.teachers) {
            if (t.getId() > maxId) maxId = t.getId();
        }
        this.nextId = maxId + 1;
    }

    // -------------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------------

    /**
     * Adds a new teacher, assigning the next available auto-increment id.
     *
     * @return the assigned id, or -1 if teacher is null.
     */
    public int addTeacher(Teacher teacher) {
        if (teacher == null) return -1;
        teacher.setId(nextId++);
        teachers.add(teacher);
        return teacher.getId();
    }

    // -------------------------------------------------------------------------
    // READ
    // -------------------------------------------------------------------------

    /**
     * Returns the teacher with the given id, or null if not found.
     */
    public Teacher getTeacherById(int id) {
        for (Teacher t : teachers) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    /**
     * Returns a copy of the full teacher list.
     * Used by FileHandler.saveTeachers() at shutdown.
     */
    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teachers);
    }

    /**
     * Searches teachers by name (case-insensitive, partial match).
     */
    public List<Teacher> searchByName(String keyword) {
        List<Teacher> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) return result;
        String kw = keyword.trim().toLowerCase();
        for (Teacher t : teachers) {
            if (t.getName().toLowerCase().contains(kw)) result.add(t);
        }
        return result;
    }

    /**
     * Returns all teachers who have the specified skill (case-insensitive).
     * Used by StaffAllocator when matching teachers to teaching requirements.
     */
    public List<Teacher> searchBySkill(String skill) {
        List<Teacher> result = new ArrayList<>();
        if (skill == null || skill.trim().isEmpty()) return result;
        for (Teacher t : teachers) {
            if (t.hasSkill(skill)) result.add(t);
        }
        return result;
    }

    /**
     * Returns all teachers available on the given day (case-insensitive).
     * Used by StaffAllocator for availability-based filtering.
     */
    public List<Teacher> searchByAvailability(String day) {
        List<Teacher> result = new ArrayList<>();
        if (day == null || day.trim().isEmpty()) return result;
        for (Teacher t : teachers) {
            if (t.isAvailableOn(day)) result.add(t);
        }
        return result;
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    /**
     * Updates the name of an existing teacher.
     *
     * @return false if no teacher with that id exists.
     */
    public boolean updateTeacherName(int id, String newName) {
        Teacher t = getTeacherById(id);
        if (t == null) return false;
        t.setName(newName);
        return true;
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------

    /**
     * Removes the teacher with the given id.
     *
     * @return false if no teacher with that id was found.
     */
    public boolean removeTeacher(int id) {
        return teachers.removeIf(t -> t.getId() == id);
    }

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    /** Returns the number of teachers currently in memory. */
    public int count() {
        return teachers.size();
    }

    /** Prints a summary of all teachers to stdout — useful for console UI. */
    public void printAll() {
        if (teachers.isEmpty()) {
            System.out.println("  (no teachers registered)");
            return;
        }
        System.out.printf("  %-5s %-20s %-30s %-20s%n", "ID", "Name", "Skills", "Availability");
        System.out.println("  " + "-".repeat(78));
        for (Teacher t : teachers) {
            System.out.printf("  %-5d %-20s %-30s %-20s%n",
                    t.getId(),
                    t.getName(),
                    String.join(";", t.getSkills()),
                    String.join(";", t.getAvailability()));
        }
    }
}
