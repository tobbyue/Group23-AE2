import java.util.List;

/**
 * DataStore interface — abstracts all data persistence operations.
 *
 * Design rationale (low coupling):
 *   Other classes (TeacherManager, RequirementManager, etc.) depend on this
 *   interface rather than a concrete file-handling class. If we later migrate
 *   to a database, we only need to provide a new implementation of DataStore;
 *   no other code changes are required.
 *
 * @author Minghao Yue (Group 23)
 */
public interface DataStore {

    // ===== Teacher operations =====
    List<Teacher> loadTeachers();
    void saveTeachers(List<Teacher> teachers);

    // ===== TeachingRequirement operations =====
    List<TeachingRequirement> loadRequirements();
    void saveRequirements(List<TeachingRequirement> requirements);

    // ===== Training operations =====
    List<Training> loadTrainings();
    void saveTrainings(List<Training> trainings);

    // ===== Allocation operations =====
    List<Allocation> loadAllocations();
    void saveAllocations(List<Allocation> allocations);

    // ===== Convenience: load / save everything at once =====
    /**
     * Load all data files into memory.
     * Called once when the program starts.
     */
    default void loadAll(
            List<Teacher> teachers,
            List<TeachingRequirement> requirements,
            List<Training> trainings,
            List<Allocation> allocations) {
        teachers.addAll(loadTeachers());
        requirements.addAll(loadRequirements());
        trainings.addAll(loadTrainings());
        allocations.addAll(loadAllocations());
    }

    /**
     * Persist all in-memory data back to storage.
     * Called once when the program exits.
     */
    default void saveAll(
            List<Teacher> teachers,
            List<TeachingRequirement> requirements,
            List<Training> trainings,
            List<Allocation> allocations) {
        saveTeachers(teachers);
        saveRequirements(requirements);
        saveTrainings(trainings);
        saveAllocations(allocations);
    }
}
