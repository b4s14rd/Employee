import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @BeforeEach
    void setUp() {
        Employee.clearRegistry(); //чистим статик перед каждым тестом
    }

    @Test
    void testPasswordIsNotSerialized() throws Exception {
        Employee emp = new Employee(1, "Alice", "secret123", "Dev");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos); //последовательность
        oos.writeObject(emp);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray())); //поток всех байтов
        Employee deserialized = (Employee) ois.readObject(); //восстанавливаем объект

        assertNull(deserialized.getPassword(), "Пароль не должен был сохраниться"); //пароль дб null
    }

    @Test
    void testNoDuplicateIds() throws Exception {
        Employee original = new Employee(1, "Alice", "pass", "Dev"); //создаем сотрудника

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //загрузка того же id
        new ObjectOutputStream(baos).writeObject(original); //превращаем original в набор байтов и сохраняем

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray())); //берем те же байты которые только что сохранили
        Employee result = (Employee) ois.readObject(); //восстанавливаем объект

        assertSame(original, result, "Должен вернуться существующий объект из реестра"); //ссылка на объект совпадает
    }
}