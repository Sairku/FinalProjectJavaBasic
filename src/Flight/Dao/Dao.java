package Flight.Dao;

import Flight.service.Flight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dao implements IDao<Flight> {

    private List<Flight> flights;

    public Dao() {
        this.flights = new ArrayList<>();
        loadDataFromFile(); // Загружаем данные из файла при инициализации
    }

    @Override
    public Flight get(int id) {
        // Поиск полета по ID
        for (Flight flight : flights) {
            if (flight.getId() == id) {
                return flight;
            }
        }
        return null; // Возвращаем null, если полет не найден
    }

    @Override
    public List<Flight> getAll() {
        // Возвращаем список всех полетов
        return flights;
    }

    @Override
    public void save(Flight flight) {
        // Проверяем, существует ли уже полет с таким же ID
        if (get(flight.getId()) != null) {
            System.out.println("Flight with ID " + flight.getId() + " already exists.");
            return;
        }
        // Добавляем полет в список
        flights.add(flight);
        // Записываем в файл
        saveDataToFile();
    }

    @Override
    public void update(Flight flight) {
        // Обновление полета в списке
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getId() == flight.getId()) {
                flights.set(i, flight);
                saveDataToFile(); // Сохраняем изменения в файл после обновления
                return;
            }
        }
        System.out.println("Flight with ID " + flight.getId() + " not found.");
    }

    @Override
    public Flight delete(int id) {
        // Удаление полета из списка
        Flight toRemove = null;
        for (Flight flight : flights) {
            if (flight.getId() == id) {
                toRemove = flight;
                break;
            }
        }
        if (toRemove != null) {
            flights.remove(toRemove);
            // Обновляем данные в файле
            saveDataToFile();
            return toRemove;
        }
        System.out.println("Flight with ID " + id + " not found.");
        return null;
    }

    // Метод для записи данных в файл
    private void saveDataToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("flights.dat");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(flights);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Метод для загрузки данных из файла (опционально)
    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        File file = new File("flights.dat");
        if (!file.exists()) {
            return; // Если файла нет, загружать нечего
        }
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            flights = (List<Flight>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
    }
}
