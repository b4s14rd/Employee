import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Map<Integer, Employee> employeesRegistry = new HashMap<>();

    private int id;
    private String name;

    private transient String password;
    private String position;

    public Employee(int id, String name, String password, String position) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.position = position;
        employeesRegistry.put(id, this);
    }

    protected Object readResolve() throws ObjectStreamException { //выполняется после того как объект считан из файла
        if (employeesRegistry.containsKey(this.id)) { //если в бд есть такой id
            return employeesRegistry.get(this.id); //ссылка на старого
        }
        employeesRegistry.put(this.id, this); //если такого id нет - добавляем
        return this;
    }

    public int getId() { return id; } //геттеры для тестов
    public String getPassword() { return password; }
    public static void clearRegistry() { employeesRegistry.clear(); } //от implements Serializable вызывается автоматически
}