import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    private final String FILE_NAME = "employee_test.dat";

    @BeforeEach
    void setUp() {
        Employee.clearRegistry(); //чистим статик перед каждым тестом
    }

    @AfterEach
    void tearDown() {
        new File(FILE_NAME).delete(); //удаляем файл после теста
    }

    @Test
    void testPasswordIsNotSerialized() throws Exception {
        Employee emp = new Employee(1, "Alice", "secret123", "Dev");

        Employee.saveToFile(FILE_NAME, emp);
        Employee deserialized = Employee.loadFromFile(FILE_NAME);

        assertNull(deserialized.getPassword(), "Пароль не должен был сохраниться"); //пароль дб null
    }

    @Test
    void testNoDuplicateIds() throws Exception {
        Employee original = new Employee(1, "Alice", "pass", "Dev"); //создаем сотрудника

        Employee.saveToFile(FILE_NAME, original);
        Employee result = Employee.loadFromFile(FILE_NAME); //загружаем обратно

        assertSame(original, result, "Должен вернуться существующий объект из реестра"); //ссылка на объект совпадает
    }
}