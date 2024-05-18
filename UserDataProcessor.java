import java.io.*;
import java.util.*;
import java.text.*;

public class UserDataProcessor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия Имя Отчество дата_рождения номер_телефона пол), разделенные пробелом:");
        String input = scanner.nextLine();
        scanner.close();

        try {
            processInput(input);
            System.out.println("Данные успешно обработаны и записаны в файл.");
        } catch (InvalidInputException | ParseException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void processInput(String input) throws InvalidInputException, ParseException, IOException {
        String[] data = input.split(" ");
        
        if (data.length != 6) {
            throw new InvalidInputException("Неверное количество данных. Ожидается 6 элементов.");
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String birthDate = data[3];
        String phoneNumber = data[4];
        String gender = data[5];

        // Проверка формата даты рождения
        if (!birthDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new ParseException("Неверный формат даты рождения. Ожидается dd.mm.yyyy.", 0);
        }

        // Проверка формата номера телефона
        if (!phoneNumber.matches("\\d+")) {
            throw new NumberFormatException("Неверный формат номера телефона. Ожидается целое беззнаковое число.");
        }

        // Проверка формата пола
        if (!(gender.equals("f") || gender.equals("m"))) {
            throw new InvalidInputException("Неверный формат пола. Ожидается символ f или m.");
        }

        // Запись данных в файл
        writeToFile(lastName, String.join(" ", lastName, firstName, middleName, birthDate, phoneNumber, gender));
    }

    private static void writeToFile(String lastName, String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(lastName + ".txt", true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            throw new IOException("Ошибка при записи в файл: " + e.getMessage(), e);
        }
    }
}

// Кастомное исключение для обработки ошибок ввода
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
