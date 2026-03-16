import java.util.Scanner;

/**
 * MenuController handles all console-based user interaction.
 */
public class MenuController {
    private Scanner scanner;
    private TeacherManager teacherManager;
    private RequirementManager requirementManager;
    private TrainingManager trainingManager;
    private StaffAllocator staffAllocator;

    public MenuController(TeacherManager teacherManager,
                          RequirementManager requirementManager,
                          TrainingManager trainingManager,
                          StaffAllocator staffAllocator) {
        this.scanner = new Scanner(System.in);
        this.teacherManager = teacherManager;
        this.requirementManager = requirementManager;
        this.trainingManager = trainingManager;
        this.staffAllocator = staffAllocator;
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    manageTeachers();
                    break;
                case "2":
                    manageRequirements();
                    break;
                case "3":
                    manageAllocation();
                    break;
                case "4":
                    manageTraining();
                    break;
                case "5":
                    running = false;
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("\n========================================");
        System.out.println(" Part-Time Teacher Management System");
        System.out.println("========================================");
        System.out.println(" 1. Manage Teachers");
        System.out.println(" 2. Manage Teaching Requirements");
        System.out.println(" 3. Allocate Staff");
        System.out.println(" 4. Manage Training");
        System.out.println(" 5. Exit");
        System.out.print("Enter choice: ");
    }

    private void manageTeachers() {
        System.out.println("\n--- Teacher Management ---");
        System.out.println("Current loaded teacher count: " + teacherManager.getTeachers().size());
        System.out.println("[Teacher management functions will be added here]");
    }

    private void manageRequirements() {
        System.out.println("\n--- Requirement Management ---");
        System.out.println("Current loaded requirement count: " + requirementManager.getRequirements().size());
        System.out.println("[Requirement management functions will be added here]");
    }

    private void manageAllocation() {
        System.out.println("\n--- Staff Allocation ---");
        System.out.println("Current loaded allocation count: " + staffAllocator.getAllocations().size());
        System.out.println("[Allocation functions will be added here]");
    }

    private void manageTraining() {
        boolean back = false;

        while (!back) {
            printTrainingMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    trainingManager.displayAllTrainings();
                    break;
                case "2":
                    createTraining();
                    break;
                case "3":
                    assignTeacherToTraining();
                    break;
                case "4":
                    updateTeacherSkillsFromTraining();
                    break;
                case "5":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printTrainingMenu() {
        System.out.println("\n--- Training Management ---");
        System.out.println(" 1. List all trainings");
        System.out.println(" 2. Create training");
        System.out.println(" 3. Assign teacher to training");
        System.out.println(" 4. Update teacher skills after training");
        System.out.println(" 5. Back to main menu");
        System.out.print("Enter choice: ");
    }

    private void createTraining() {
        System.out.print("Enter training name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter training date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        System.out.print("Enter skill taught: ");
        String skill = scanner.nextLine().trim();

        Training training = trainingManager.createTraining(name, date, skill);
        System.out.println("Training created successfully: " + training);
    }

    private void assignTeacherToTraining() {
        try {
            System.out.print("Enter training ID: ");
            int trainingId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter teacher ID: ");
            int teacherId = Integer.parseInt(scanner.nextLine().trim());

            boolean success = trainingManager.assignTeacher(trainingId, teacherId);
            if (success) {
                System.out.println("Assignment completed.");
            } else {
                System.out.println("Assignment failed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        }
    }

    private void updateTeacherSkillsFromTraining() {
        try {
            System.out.print("Enter training ID: ");
            int trainingId = Integer.parseInt(scanner.nextLine().trim());

            int updated = trainingManager.updateTeacherSkills(trainingId);
            System.out.println("Updated teacher count: " + updated);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        }
    }
}
