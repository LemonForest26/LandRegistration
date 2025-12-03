package group27.landRegistration.utility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager<T> {
    private final String filePath;
    public FileManager(String filePath) {
        this.filePath = filePath;
    }
    public void saveList(List<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<T> loadList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>(); // if file doesn't exist
        }
    }

    public void appendItem(T item) {
        List<T> list = loadList();
        list.add(item);
        saveList(list);
    }

    public void updateItem(int index, T newItem) {
        List<T> list = loadList();
        if (index >= 0 && index < list.size()) {
            list.set(index, newItem);
            saveList(list);
        }
    }

    public T find(ItemCondition<T> condition) {
        for (T item : loadList()) {
            if (condition.matches(item)) return item;
        }
        return null;
    }

    public void deleteItem(int index) {
        List<T> list = loadList();
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            saveList(list);
        }
    }

    public interface ItemCondition<T> {
        boolean matches(T item);
    }
}
