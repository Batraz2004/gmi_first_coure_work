// package app.services;

// import app.data.Models.Order;
// import java.io.File;
// import java.io.IOException;

// public class OrderService {
//     // public OrderService(Order[]orders){

//     // }
//     /**
//      * Читает массив заказов из JSON-файла и выводит их на экран.
//      * 
//      * @param filePath путь к JSON-файлу (должен содержать массив объектов Order)
//      */
//     public void displayOrdersFromJson(String filePath) {
//         // objectMapper objectMapper = new ObjectMapper();
//         // try {
//         // // Десериализация JSON-файла в массив Order[]
//         // Order[] orders = objectMapper.readValue(new File(filePath), Order[].class);
//         // // Вывод каждого заказа на экран
//         // for (Order order : orders) {
//         // System.out.println(order);
//         // }
//         // } catch (IOException e) {
//         // System.err.println("Ошибка при чтении файла: " + e.getMessage());
//         // e.printStackTrace();
//         // }
//     }

//     // public boolean deleteOrderById(long id) throws IOException {
//     // boolean removed = orders.removeIf(o -> o.getId() == id);
//     // if (removed) {
//     // saveOrdersToFile();
//     // System.out.println("Заказ с ID " + id + " удалён");
//     // } else {
//     // System.err.println("Заказ с ID " + id + " не найден");
//     // }
//     // return removed;
//     // }

// }