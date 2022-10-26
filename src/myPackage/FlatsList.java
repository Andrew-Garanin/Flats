package myPackage;

import myExeptions.MyExeption;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FlatsList {
    private static final String flatsPath = "flats.txt";
    private static final String searchFilePath = "data.txt";
    private final ArrayList<Flat> flatsList;

    public FlatsList() throws MyExeption, IOException {
        flatsList = readFileWithFlats();
    }

    public static ArrayList<Flat> readFileWithFlats() throws IOException, MyExeption {
        ArrayList<Flat> flats = new ArrayList<Flat>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(flatsPath));
        String tempStr;
        double metrics;
        int countOfRooms;
        String[] address;
        int floorOfFlat;
        String typeOfHouse;
        int countOfFloors;
        long price;
        while ((tempStr = bufferedReader.readLine()) != null) {
            if (tempStr.isEmpty())
                continue;
            metrics = Double.parseDouble(tempStr.substring(1, tempStr.indexOf('м')));
            tempStr = bufferedReader.readLine();
            countOfRooms = Integer.parseInt(tempStr.substring(1, 2));
            tempStr = bufferedReader.readLine();
            tempStr = tempStr.substring(1);
            address = tempStr.split("[, ]+");
            tempStr = bufferedReader.readLine();
            floorOfFlat = Integer.parseInt(tempStr.substring(1, 2));
            countOfFloors = Integer.parseInt(tempStr.substring(3, tempStr.indexOf(' ')));
            tempStr = bufferedReader.readLine();
            typeOfHouse = tempStr.substring(1);
            tempStr = bufferedReader.readLine();
            price = Long.parseLong(tempStr.substring(1));
            flats.add(new Flat(metrics, countOfRooms, address, floorOfFlat, typeOfHouse, countOfFloors, price));
        }
        bufferedReader.close();
        if (flats.isEmpty())
            throw new MyExeption("Файл "+ flatsPath + " пуст");
        return flats;
    }

    public void addFlat() throws MyExeption {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите адрес квартиры(формат: р-н, улица, дом)-> ");
        String tempStr = sc.nextLine().trim();
        String[] address;
        if (tempStr.matches("(.*), (.*), (.*)"))
            address = tempStr.split("[, ]+", 0);
        else throw new MyExeption("Вы ввели неверные данные или нарушили формат");

        System.out.print("Введите метраж квартиры в квадратных метрах-> ");
        double metrics = sc.nextDouble();
        System.out.print("Введите колличество комнат в квартире-> ");
        int countOfRooms = sc.nextInt();
        System.out.print("Введите этаж квартиры-> ");
        int floor = sc.nextInt();
        System.out.print("Введите колличество этажей в доме-> ");
        int countOfFloors = sc.nextInt();
        if (floor > countOfFloors)
            throw new MyExeption("Этаж квартиры не может быть больше колличества этажей в доме");
        System.out.print("Введите номер типа дома(1.Кирпичный 2.Панельный)-> ");
        int type = sc.nextInt();
        String typeOfHouse;
        if (type == 1)
            typeOfHouse = "Кирпичный";
        else if (type == 2)
            typeOfHouse = "Панельный";
        else throw new MyExeption("Вы ввели неверный номер типа дома");
        System.out.print("Введите цену квартиры-> ");
        long price = sc.nextLong();
        flatsList.add(new Flat(metrics, countOfRooms, address, floor, typeOfHouse, countOfFloors, price));
        System.out.println("Квартира добавлена" +
                "\n***************************");
    }

    public void removeFlat() throws MyExeption {
        if (flatsList.size() == 0) {
            System.out.println("Список квартир пуст");
            return;
        }
        Scanner sc = new Scanner(System.in);
        flatsList.stream().forEach((p) ->
                System.out.println("Квартира №" + (flatsList.indexOf(p) + 1) + "\n" + p));
        System.out.print("Введите номер квартиры, которую хотите удалить-> ");
        int delIndex = sc.nextInt() - 1;
        if (delIndex < 0 || delIndex > flatsList.size())
            throw new MyExeption("Вы ввели неверный номер");
        flatsList.remove(delIndex);
        System.out.println("Квартира удалена" +
                "\n***************************");
    }

    public void sortFlats() {
        if (flatsList.size() == 0) {
            System.out.println("Список квартир пуст");
            return;
        }
        ArrayList<Flat> sortedFlats = flatsList;
        System.out.println("Отсортированный список квартир:");
        sortedFlats.stream().sorted().forEach((p) -> System.out.println(p));
        System.out.println("***************************");
    }

    public void flatsFromRange() throws MyExeption {
        if (flatsList.size() == 0) {
            System.out.println("Список квартир пуст");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите диапозон цены(формат: от-до)->");
        String range = sc.next().trim();
        long from = Long.parseLong(range.substring(0, range.indexOf('-')));
        long to = Long.parseLong(range.substring(range.indexOf('-') + 1));
        if (from < 0 || to < 0 || from > to)
            throw new MyExeption("Ошибка при вводе цены");
        ArrayList<Flat> flatsFromRange = flatsList;
        long count = flatsFromRange.stream().filter(o -> o.getPrice() >= from).filter(o -> o.getPrice() <= to).count();
        if (count != 0) {
            System.out.println("Результы поиска по данному диапозону цен");
            flatsFromRange.stream().filter(o -> o.getPrice() >= from).filter(o -> o.getPrice() <= to).forEach((p) -> System.out.println(p));
            System.out.println("***************************");
        } else System.out.println("По данному диапозону цен ничего не найдено" +
                "\n***************************");
    }

    public void searchFlats(RequestsList requestsList) {
        File file = new File(searchFilePath);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(file.getAbsoluteFile()); //PrintWriter обеспечит возможности записи в файл
            for (int i = 0; i < requestsList.getRequestListSize(); i++) {
                int count = 0;//Считает, сколько квартир найдено по заявке
                Request request = requestsList.getRequestByIndex(i);
                System.out.println("Поиск по завке:");
                printWriter.println("Поиск по завке:");
                System.out.println(request);
                printWriter.println(request);
                System.out.println("Дал следущие результаты->");
                printWriter.println("Дал следущие результаты->");
                for (Flat flat : flatsList) {
                    if (flat.getCountOfRooms() == request.getCountOfRooms() && flat.getArea().equals(request.getArea()) && flat.getTypeOfHouse().equals(request.getTypeOfHouse())) {
                        System.out.println(flat);
                        printWriter.println(flat);
                        count++;
                    }
                }
                if (count != 0) {
                    System.out.println("====================");
                    printWriter.println("====================");
                } else {
                    System.out.println("Поиск не дал результатов"+
                            "\n***************************");
                    printWriter.println("Поиск не дал результатов"+
                            "\n***************************");
                }
            }
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
