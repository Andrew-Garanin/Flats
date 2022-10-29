package myPackage;

import myExeptions.MyExeption;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FlatsList {


    private final ArrayList<Flat> flatsList;

    public FlatsList(String flatsPath) throws MyExeption, IOException {
        flatsList = readFileWithFlats(flatsPath);
    }

    public static ArrayList<Flat> readFileWithFlats(String flatsPath) throws IOException, MyExeption {
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
            metrics = extractMetrics(tempStr);
            countOfRooms = extractCountOfRooms(bufferedReader.readLine());
            address = extractAddress(bufferedReader.readLine().substring(1));

            tempStr = bufferedReader.readLine();
            floorOfFlat = extractFloorOfFlat(tempStr);
            countOfFloors = extractCountOfFloors(tempStr);

            typeOfHouse = extractTypeOfHouse(bufferedReader.readLine());
            price = extractPrice(bufferedReader.readLine());
            flats.add(new Flat(metrics, countOfRooms, address, floorOfFlat, typeOfHouse, countOfFloors, price));
        }
        bufferedReader.close();
        if (flats.isEmpty())
            throw new MyExeption("Файл "+ flatsPath + " пуст");
        return flats;
    }

    private static double extractMetrics(String metricsStr){
        return Double.parseDouble(metricsStr.substring(1, metricsStr.indexOf('м')));
    }

    private static int extractCountOfRooms(String countOfRoomsStr){
        return Integer.parseInt(countOfRoomsStr.substring(1, 2));
    }

    private static String[] extractAddress(String addressStr){
        return addressStr.split("[, ]+");
    }

    private static int extractFloorOfFlat(String floorOfFlatStr){
        return Integer.parseInt(floorOfFlatStr.substring(1, 2));
    }

    private static int extractCountOfFloors(String countOfFloorsStr){
        return Integer.parseInt(countOfFloorsStr.substring(3, countOfFloorsStr.indexOf(' ')));
    }

    private static String extractTypeOfHouse(String typeOfHouseStr){
        return typeOfHouseStr.substring(1);
    }

    private static long extractPrice(String priceStr){
        return Long.parseLong(priceStr.substring(1));
    }

    public void addFlat() throws MyExeption {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите адрес квартиры(формат: р-н, улица, дом)-> ");
        String tempStr = sc.nextLine().trim();
        String[] address;
        if (isAddressCorrect(tempStr))
            address = extractAddress(tempStr);
        else throw new MyExeption("Вы ввели неверные данные или нарушили формат");

        System.out.print("Введите метраж квартиры в квадратных метрах-> ");
        double metrics = sc.nextDouble();
        System.out.print("Введите колличество комнат в квартире-> ");
        int countOfRooms = sc.nextInt();
        System.out.print("Введите этаж квартиры-> ");
        int floor = sc.nextInt();
        System.out.print("Введите колличество этажей в доме-> ");
        int countOfFloors = sc.nextInt();
        if (!isFloorCorrect(floor, countOfFloors))
            throw new MyExeption("Этаж квартиры не может быть больше колличества этажей в доме");
        System.out.print("Введите номер типа дома(1.Кирпичный 2.Панельный)-> ");
        int type = sc.nextInt();
        String typeOfHouse;

        typeOfHouse = getTypeOfHouseName(type);
        System.out.print("Введите цену квартиры-> ");
        long price = sc.nextLong();
        flatsList.add(new Flat(metrics, countOfRooms, address, floor, typeOfHouse, countOfFloors, price));
        System.out.println("Квартира добавлена" + "\n***************************");
    }

    private static boolean isAddressCorrect(String addressStr){
        return addressStr.matches("(.*), (.*), (.*)");
    }

    private static boolean isFloorCorrect(int floor, int countOfFloors){
        return floor <= countOfFloors;
    }

    private static String getTypeOfHouseName(int type) throws MyExeption {
        switch (type){
            case 1:
                return "Кирпичный";
            case 2:
                return "Панельный";
            default:
                throw new MyExeption("Вы ввели неверный номер типа дома");
        }
    }

    public void removeFlat() throws MyExeption {
        if (flatsList.isEmpty()) {
            System.out.println("Список квартир пуст");
            return;
        }
        Scanner sc = new Scanner(System.in);
        flatsList.forEach((p) ->
                System.out.println("Квартира №" + (flatsList.indexOf(p) + 1) + "\n" + p));
        System.out.print("Введите номер квартиры, которую хотите удалить-> ");
        int delIndex = sc.nextInt() - 1;
        if (isFlatNumberExist(delIndex))
            throw new MyExeption("Вы ввели неверный номер");
        flatsList.remove(delIndex);
        System.out.println("Квартира удалена" + "\n***************************");
    }

    private boolean isFlatNumberExist(int flatNum){
        return flatNum < 0 || flatNum > flatsList.size();
    }

    public void sortFlats() {
        if (flatsList.isEmpty()) {
            System.out.println("Список квартир пуст");
            return;
        }
        System.out.println("Отсортированный список квартир:");
        flatsList.stream().sorted().forEach(System.out::println);
        System.out.println("***************************");
    }

    public void flatsFromRange() throws MyExeption {
        if (flatsList.isEmpty()) {
            System.out.println("Список квартир пуст");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите диапозон цены(формат: от-до)->");
        String range = sc.next().trim();
        long from =extractMinPrice(range);
        long to =extractMaxPrice(range);
        if (!isRangeCorrect(from, to))
            throw new MyExeption("Ошибка при вводе цены");
        long count = countFlatsFromRange(flatsList, from, to);
        if (count != 0) {
            System.out.println("Результы поиска по данному диапозону цен");
            flatsList.stream().filter(o -> o.getPrice() >= from).filter(o -> o.getPrice() <= to).forEach(System.out::println);
            System.out.println("***************************");
        } else System.out.println("По данному диапозону цен ничего не найдено" + "\n***************************");
    }

    private long extractMinPrice(String rangeStr){
        return Long.parseLong(rangeStr.substring(0, rangeStr.indexOf('-')));
    }

    private long extractMaxPrice(String rangeStr){
        return Long.parseLong(rangeStr.substring(rangeStr.indexOf('-') + 1));
    }

    private boolean isRangeCorrect(long from, long to){
        return from >= 0 || to >= 0 || from <= to;
    }

    private long countFlatsFromRange(ArrayList<Flat> flatsList, long from, long to){
        return flatsList.stream().filter(flat -> flat.getPrice() >= from).filter(flat -> flat.getPrice() <= to).count();
    }

    public void searchFlats(RequestsList requestsList, String searchFilePath) {
        File file = new File(searchFilePath);

        try {
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
                    if (isFlatCorrespondsToRequest(flat, request)) {
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

    private boolean isFlatCorrespondsToRequest(Flat flat, Request request){
        return flat.getCountOfRooms() == request.getCountOfRooms() &&
               flat.getArea().equals(request.getArea()) &&
               flat.getTypeOfHouse().equals(request.getTypeOfHouse());
    }
}
