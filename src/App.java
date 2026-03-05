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
 * Design note: App uses the DataStore interface (not FileHandler directly),
 * so switching to a database only requires changing one line here.
 *
 * TODO (Tianxin Han): integrate all managers into MenuController
 *
 * @author Tianxin Han (Group 23)
 */
public class App {

    public static void main(String[] args) {
        // --- 1. Initialise data store ---
        // To switch to a database in future, only change this line:
        //   DataStore dataStore = new DatabaseHandler();
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
        TeacherManager teacherMgr = new TeacherManager(teachers);
        RequirementManager reqMgr = new RequirementManager(requirements);
        TrainingManager trainingMgr = new TrainingManager(trainings, teachers);
        StaffAllocator allocator = new StaffAllocator(teachers, requirements, allocations);

        // --- 4. Start interactive menu ---
        // TODO (Tianxin): pass all managers to MenuController
        // MenuController menu = new MenuController(teacherMgr, reqMgr, trainingMgr, allocator);
        // menu.run();

        System.out.println("\n[Placeholder] Menu not yet implemented. Exiting...");

        // --- 5. Save all data before exit ---
        dataStore.saveAll(teachers, requirements, trainings, allocations);
        System.out.println("Data saved successfully. Goodbye!");
    }
}
