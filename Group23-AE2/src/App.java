import java.util.ArrayList;
import java.util.List;

/**
 * App — main entry point of the Part-Time Teacher Management System.
 *
 * Responsibilities:
 *   1. Initialise the DataStore and load all data from files
 *   2. Create manager objects and pass data to them
 *   3. Hand control to MenuController for user interaction
 *   4. On exit, save all data back to files
 *
 * TODO (Tianxin Han): implement the main method and program lifecycle
 *
 * @author Tianxin Han (Group 23)
 */
public class App {

    public static void main(String[] args) {
        // --- 1. Initialise data store ---
        DataStore dataStore = new FileHandler();

        // --- 2. Load all data into memory ---
        List<Teacher> teachers = new ArrayList<>();
        List<TeachingRequirement> requirements = new ArrayList<>();
        List<Training> trainings = new ArrayList<>();
        List<Allocation> allocations = new ArrayList<>();

        dataStore.loadAll(teachers, requirements, trainings, allocations);
        System.out.println("Data loaded successfully.");
        System.out.println("  Teachers:     " + teachers.size());
        System.out.println("  Requirements: " + requirements.size());
        System.out.println("  Trainings:    " + trainings.size());
        System.out.println("  Allocations:  " + allocations.size());

        // --- 3. Create managers ---
        // TODO: pass data lists to your managers, e.g.:
        // TeacherManager teacherMgr = new TeacherManager(teachers);
        // RequirementManager reqMgr = new RequirementManager(requirements);
        // StaffAllocator allocator = new StaffAllocator(teachers, requirements, allocations);

        // --- 4. Start interactive menu ---
        // TODO: MenuController menu = new MenuController(...);
        // menu.run();

        System.out.println("\n[Placeholder] Menu not yet implemented. Exiting...");

        // --- 5. Save all data before exit ---
        dataStore.saveAll(teachers, requirements, trainings, allocations);
        System.out.println("Data saved successfully. Goodbye!");
    }
}
