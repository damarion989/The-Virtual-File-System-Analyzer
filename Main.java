import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static int countFilesRecursive(FileSystemItem item) {
        if (item instanceof FileItem) {
            return 1;
        }

        Folder folder = (Folder) item;
        int count = 0;

        for (FileSystemItem child : folder.getItems()) {
            count += countFilesRecursive(child);
        }

        return count;
    }

    public static int calculateTotalSizeRecursive(FileSystemItem item) {
        if (item instanceof FileItem) {
            return item.getSizeInKB();
        }

        Folder folder = (Folder) item;
        int total = 0;

        for (FileSystemItem child : folder.getItems()) {
            total += calculateTotalSizeRecursive(child);
        }

        return total;
    }

    public static FileItem findLargestFileRecursive(FileSystemItem item) {
        if (item instanceof FileItem) {
            return (FileItem) item;
        }

        Folder folder = (Folder) item;
        FileItem largest = null;

        for (FileSystemItem child : folder.getItems()) {
            FileItem currentFile = findLargestFileRecursive(child);

            if (currentFile != null &&
                (largest == null || currentFile.getSizeInKB() > largest.getSizeInKB())) {
                largest = currentFile;
            }
        }

        return largest;
    }

    public static int countFilesIterative(Folder rootFolder) {
        Stack<FileSystemItem> stack = new Stack<>();
        stack.push(rootFolder);

        int fileCount = 0;

        while (!stack.isEmpty()) {
            FileSystemItem current = stack.pop();

            if (current instanceof FileItem) {
                fileCount++;
            } else {
                Folder folder = (Folder) current;

                for (FileSystemItem child : folder.getItems()) {
                    stack.push(child);
                }
            }
        }

        return fileCount;
    }

    public static void printHierarchy(FileSystemItem item, String indent) {
        if (item instanceof FileItem) {
            System.out.println(indent + item.getName() + " (" + item.getSizeInKB() + " KB)");
        } else {
            Folder folder = (Folder) item;
            System.out.println(indent + folder.getName() + "/");

            for (FileSystemItem child : folder.getItems()) {
                printHierarchy(child, indent + "  ");
            }
        }
    }

    public static Folder findFolder(Folder current, String targetName) {
        if (current.getName().equalsIgnoreCase(targetName)) {
            return current;
        }

        for (FileSystemItem child : current.getItems()) {
            if (child instanceof Folder) {
                Folder found = findFolder((Folder) child, targetName);

                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Starting file system
        Folder root = new Folder("Root");
        Folder documents = new Folder("Documents");
        Folder pictures = new Folder("Pictures");
        Folder vacations = new Folder("Vacations");

        documents.addItem(new FileItem("Homework.docx", 500));
        documents.addItem(new FileItem("Notes.txt", 50));

        vacations.addItem(new FileItem("Beach.jpg", 2500));
        vacations.addItem(new FileItem("TripVideo.mp4", 8000));

        pictures.addItem(vacations);

        root.addItem(documents);
        root.addItem(pictures);
        root.addItem(new FileItem("Music.mp3", 4000));

        boolean running = true;

        while (running) {
            System.out.println("\n--- File System Menu ---");
            System.out.println("1. Display File System");
            System.out.println("2. Add File");
            System.out.println("3. Add Subfolder");
            System.out.println("4. Run Recursive Audit");
            System.out.println("5. Run Iterative Audit");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    printHierarchy(root, "");
                    break;

                case "2":
                    System.out.print("File name: ");
                    String fileName = scanner.nextLine();

                    System.out.print("File size in KB: ");
                    int fileSize = Integer.parseInt(scanner.nextLine());

                    if (fileSize < 0) {
                        System.out.println("File size cannot be negative.");
                        break;
                    }

                    System.out.print("Add file to which folder? ");
                    String folderName = scanner.nextLine();

                    Folder targetFolder = findFolder(root, folderName);

                    if (targetFolder == null) {
                        System.out.println("Folder not found.");
                    } else {
                        targetFolder.addItem(new FileItem(fileName, fileSize));
                        System.out.println("File added.");
                    }
                    break;

                case "3":
                    System.out.print("New folder name: ");
                    String newFolderName = scanner.nextLine();

                    System.out.print("Add it inside which folder? ");
                    String parentName = scanner.nextLine();

                    Folder parentFolder = findFolder(root, parentName);

                    if (parentFolder == null) {
                        System.out.println("Folder not found.");
                    } else {
                        parentFolder.addItem(new Folder(newFolderName));
                        System.out.println("Folder added.");
                    }
                    break;

                case "4":
                    int fileCount = countFilesRecursive(root);
                    int totalSize = calculateTotalSizeRecursive(root);
                    FileItem largest = findLargestFileRecursive(root);

                    System.out.println("Total files: " + fileCount);
                    System.out.println("Total size: " + totalSize + " KB");

                    if (largest != null) {
                        System.out.println("Largest file: " + largest.getName()
                                + " (" + largest.getSizeInKB() + " KB)");
                    } else {
                        System.out.println("No files found.");
                    }
                    break;

                case "5":
                    int recursiveCount = countFilesRecursive(root);
                    int iterativeCount = countFilesIterative(root);

                    System.out.println("Recursive count: " + recursiveCount);
                    System.out.println("Iterative count: " + iterativeCount);

                    if (recursiveCount == iterativeCount) {
                        System.out.println("Both counts match.");
                    } else {
                        System.out.println("Counts do not match.");
                    }
                    break;

                case "6":
                    running = false;
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
