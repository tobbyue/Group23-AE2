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
        boolean back = false;

        while (!back) {
            printRequirementsMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    listAllRequirements();
                    break;
                case "2":
                    addRequirement();
                    break;
                case "3":
                    updateRequirement();
                    break;
                case "4":
                    removeRequirement();
                    break;
                case "5":
                    filterRequirementsByStatus();
                    break;
                case "6":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printRequirementsMenu() {
        System.out.println("\n--- Requirement Management ---");
        System.out.println(" 1. List all requirements");
        System.out.println(" 2. Add requirement");
        System.out.println(" 3. Update requirement");
        System.out.println(" 4. Remove requirement");
        System.out.println(" 5. Filter by status");
        System.out.println(" 6. Back to main menu");
        System.out.print("Enter choice: ");
    }

    private void listAllRequirements() {
        java.util.List<TeachingRequirement> list = requirementManager.getRequirements();
        if (list.isEmpty()) {
            System.out.println("No requirements found.");
            return;
        }
        System.out.println("ID | Course              | Semester  | Skill  | Day | Status");
        System.out.println("--------------------------------------------------------------");
        for (TeachingRequirement r : list) {
            System.out.printf("%-3d| %-20s| %-10s| %-7s| %-4s| %s%n",
                    r.getId(), r.getCourseName(), r.getSemester(),
                    r.getRequiredSkill(), r.getDayOfWeek(), r.getStatus());
        }
    }

    private void addRequirement() {
        System.out.print("Enter course name: ");
        String course = scanner.nextLine().trim();

        System.out.print("Enter semester (e.g. 2026-S1): ");
        String semester = scanner.nextLine().trim();

        System.out.print("Enter required skill: ");
        String skill = scanner.nextLine().trim();

        System.out.print("Enter day of week (Mon/Tue/Wed/Thu/Fri): ");
        String day = scanner.nextLine().trim();

        try {
            TeachingRequirement req = requirementManager.addRequirement(course, semester, skill, day);
            System.out.println("Requirement added: " + req);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateRequirement() {
        try {
            System.out.print("Enter requirement ID to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            TeachingRequirement existing = requirementManager.findById(id);
            if (existing == null) {
                System.out.println("Requirement not found.");
                return;
            }

            System.out.print("Enter new course name [" + existing.getCourseName() + "]: ");
            String course = scanner.nextLine().trim();
            if (course.isEmpty()) course = existing.getCourseName();

            System.out.print("Enter new semester [" + existing.getSemester() + "]: ");
            String semester = scanner.nextLine().trim();
            if (semester.isEmpty()) semester = existing.getSemester();

            System.out.print("Enter new required skill [" + existing.getRequiredSkill() + "]: ");
            String skill = scanner.nextLine().trim();
            if (skill.isEmpty()) skill = existing.getRequiredSkill();

            System.out.print("Enter new day of week [" + existing.getDayOfWeek() + "]: ");
            String day = scanner.nextLine().trim();
            if (day.isEmpty()) day = existing.getDayOfWeek();

            requirementManager.updateRequirement(id, course, semester, skill, day);
            System.out.println("Requirement updated.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number for the ID.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void removeRequirement() {
        try {
            System.out.print("Enter requirement ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            boolean removed = requirementManager.removeRequirement(id);
            System.out.println(removed ? "Requirement removed." : "Requirement not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        }
    }

    private void filterRequirementsByStatus() {
        System.out.print("Enter status to filter (OPEN / ASSIGNED): ");
        String status = scanner.nextLine().trim().toUpperCase();

        java.util.List<TeachingRequirement> list = requirementManager.findByStatus(status);
        if (list.isEmpty()) {
            System.out.println("No requirements with status: " + status);
            return;
        }
        System.out.println("ID | Course              | Skill  | Day | Status");
        System.out.println("---------------------------------------------------");
        for (TeachingRequirement r : list) {
            System.out.printf("%-3d| %-20s| %-7s| %-4s| %s%n",
                    r.getId(), r.getCourseName(), r.getRequiredSkill(),
                    r.getDayOfWeek(), r.getStatus());
        }
    }

    private void manageAllocation() {
        boolean back = false;

        while (!back) {
            printAllocationMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    staffAllocator.listAllocations();
                    break;
                case "2":
                    manualAllocate();
                    break;
                case "3":
                    autoMatchRequirement();
                    break;
                case "4":
                    removeAllocation();
                    break;
                case "5":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printAllocationMenu() {
        System.out.println("\n--- Staff Allocation ---");
        System.out.println(" 1. List all allocations");
        System.out.println(" 2. Manual allocate (teacher to requirement)");
        System.out.println(" 3. Auto match (find best teacher for requirement)");
        System.out.println(" 4. Remove allocation");
        System.out.println(" 5. Back to main menu");
        System.out.print("Enter choice: ");
    }

    private void manualAllocate() {
        try {
            System.out.print("Enter teacher ID: ");
            int teacherId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter requirement ID: ");
            int requirementId = Integer.parseInt(scanner.nextLine().trim());

            staffAllocator.allocate(teacherId, requirementId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        }
    }

    private void autoMatchRequirement() {
        try {
            System.out.print("Enter requirement ID to auto-match: ");
            int requirementId = Integer.parseInt(scanner.nextLine().trim());

            TeachingRequirement req = null;
            for (TeachingRequirement r : requirementManager.getRequirements()) {
                if (r.getId() == requirementId) {
                    req = r;
                    break;
                }
            }

            if (req == null) {
                System.out.println("Requirement not found.");
                return;
            }
            staffAllocator.autoMatch(req);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        }
    }

    private void removeAllocation() {
        try {
            System.out.print("Enter allocation ID to remove: ");
            int allocationId = Integer.parseInt(scanner.nextLine().trim());

            staffAllocator.removeAllocation(allocationId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        }
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
