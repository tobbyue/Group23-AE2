import java.util.ArrayList;
import java.util.List;

/**
 * RequirementManager — handles CRUD operations for TeachingRequirement objects.
 *
 * Design notes:
 *   - Holds a reference to the shared requirements list (managed by App).
 *   - Does NOT call DataStore directly; App is responsible for loading/saving.
 *   - ID generation is auto-incremented based on the current max ID.
 *
 * User Story:
 *   "As a Class Director, I want to submit teaching requirements for courses
 *    so that suitable part-time teachers can be found and assigned."
 *
 * @author Yanyu Ge (Group 23)
 */
public class RequirementManager {

    private List<TeachingRequirement> requirements;

    public RequirementManager(List<TeachingRequirement> requirements) {
        this.requirements = requirements;
    }

    // ---------- ID generation ----------

    /**
     * Generate the next available ID by finding the current maximum and adding 1.
     * Returns 1 if the list is empty.
     */
    private int generateNextId() {
        int maxId = 0;
        for (TeachingRequirement r : requirements) {
            if (r.getId() > maxId) {
                maxId = r.getId();
            }
        }
        return maxId + 1;
    }

    // ---------- Create ----------

    /**
     * Add a new teaching requirement.
     * The ID is generated automatically.
     *
     * @return the newly created TeachingRequirement
     */
    public TeachingRequirement addRequirement(String courseName, String semester,
                                              String requiredSkill, String dayOfWeek) {
        int id = generateNextId();
        TeachingRequirement req = new TeachingRequirement(
                id, courseName, semester, requiredSkill, dayOfWeek);
        requirements.add(req);
        return req;
    }

    // ---------- Read ----------

    /** Return all requirements. */
    public List<TeachingRequirement> listAll() {
        return requirements;
    }

    /** Find a single requirement by ID, or null if not found. */
    public TeachingRequirement findById(int id) {
        for (TeachingRequirement r : requirements) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    /** Return all requirements that need a teacher with the given skill. */
    public List<TeachingRequirement> findBySkill(String skill) {
        List<TeachingRequirement> result = new ArrayList<>();
        for (TeachingRequirement r : requirements) {
            if (r.getRequiredSkill().equalsIgnoreCase(skill)) {
                result.add(r);
            }
        }
        return result;
    }

    /** Return all requirements with the given status (OPEN / ASSIGNED). */
    public List<TeachingRequirement> findByStatus(String status) {
        List<TeachingRequirement> result = new ArrayList<>();
        for (TeachingRequirement r : requirements) {
            if (r.getStatus().equalsIgnoreCase(status)) {
                result.add(r);
            }
        }
        return result;
    }

    /** Return all requirements scheduled on a given day. */
    public List<TeachingRequirement> findByDay(String day) {
        List<TeachingRequirement> result = new ArrayList<>();
        for (TeachingRequirement r : requirements) {
            if (r.getDayOfWeek().equalsIgnoreCase(day)) {
                result.add(r);
            }
        }
        return result;
    }

    // ---------- Update ----------

    /**
     * Update an existing requirement's fields.
     *
     * @return true if the requirement was found and updated, false otherwise
     */
    public boolean updateRequirement(int id, String courseName, String semester,
                                     String requiredSkill, String dayOfWeek) {
        TeachingRequirement req = findById(id);
        if (req == null) {
            return false;
        }
        req.setCourseName(courseName);
        req.setSemester(semester);
        req.setRequiredSkill(requiredSkill);
        req.setDayOfWeek(dayOfWeek);
        return true;
    }

    // ---------- Delete ----------

    /**
     * Remove a requirement by ID.
     *
     * @return true if the requirement was found and removed, false otherwise
     */
    public boolean removeRequirement(int id) {
        TeachingRequirement req = findById(id);
        if (req == null) {
            return false;
        }
        requirements.remove(req);
        return true;
    }

    // ---------- Getter ----------

    public List<TeachingRequirement> getRequirements() {
        return requirements;
    }
}
