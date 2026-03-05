import java.util.Scanner;

/**
 * MenuController — handles all console-based user interaction.
 *
 * Displays a main menu and delegates actions to the appropriate manager.
 * This class should NOT contain business logic — it only handles I/O.
 *
 * TODO (Tianxin Han): implement menu options for each user story
 *
 * Suggested menu structure:
 *   1. Manage Teachers      → TeacherManager
 *   2. Manage Requirements  → RequirementManager
 *   3. Allocate Staff       → StaffAllocator
 *   4. Manage Training      → (Training logic)
 *   5. Exit
 *
 * @author Tianxin Han (Group 23)
 */
public class MenuController {

    private Scanner scanner;
    // TODO: add references to managers as fields
    // private TeacherManager teacherManager;
    // private RequirementManager requirementManager;
    // private StaffAllocator staffAllocator;

    public MenuController() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main loop — display menu and handle user choices until exit.
     */
    public void run() {
        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    // TODO: manage teachers sub-menu
                    System.out.println("[TODO] Teacher management");
                    break;
                case "2":
                    // TODO: manage requirements sub-menu
                    System.out.println("[TODO] Requirement management");
                    break;
                case "3":
                    // TODO: staff allocation
                    System.out.println("[TODO] Staff allocation");
                    break;
                case "4":
                    // TODO: training management
                    System.out.println("[TODO] Training management");
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n========================================");
        System.out.println("  Part-Time Teacher Management System");
        System.out.println("========================================");
        System.out.println("  1. Manage Teachers");
        System.out.println("  2. Manage Teaching Requirements");
        System.out.println("  3. Allocate Staff");
        System.out.println("  4. Manage Training");
        System.out.println("  5. Exit");
        System.out.print("Enter choice: ");
    }
}
