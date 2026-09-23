import java.util.ArrayList;

public class Folder implements FileSystemItem {
    private String name;
    private ArrayList<FileSystemItem> items;

    public Folder(String name) {
        this.name = name;
        items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getSizeInKB() {
        return 0;
    }

    public void addItem(FileSystemItem item) {
        items.add(item);
    }

    public ArrayList<FileSystemItem> getItems() {
        return items;
    }
}
