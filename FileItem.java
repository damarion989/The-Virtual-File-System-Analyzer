public class FileItem implements FileSystemItem {
    private String name;
    private int sizeInKB;

    public FileItem(String name, int sizeInKB) {
        this.name = name;
        this.sizeInKB = sizeInKB;
    }

    public String getName() {
        return name;
    }

    public int getSizeInKB() {
        return sizeInKB;
    }
}
