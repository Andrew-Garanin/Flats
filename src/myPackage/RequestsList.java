package myPackage;

import myExeptions.MyExeption;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RequestsList {
    private final ArrayList<Request> requestsList;

    public RequestsList(String requestsPath) throws MyExeption, IOException {
        requestsList = readFileWithRequests(requestsPath);
    }

    public Request getRequestByIndex(int index){
        return requestsList.get(index);
    }

    public int getRequestListSize(){
        return requestsList.size();
    }

    public static ArrayList<Request> readFileWithRequests(String requestsPath) throws IOException, MyExeption {
        ArrayList<Request> list = new ArrayList<Request>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(requestsPath));
        String tempStr;
        int countOfRooms;
        String area;
        String typeOfHouse;
        while ((tempStr = bufferedReader.readLine()) != null) {
            if (tempStr.isEmpty())
                continue;
            countOfRooms = extractCountOfRooms(tempStr);
            area = extractArea(bufferedReader.readLine());
            typeOfHouse = extractTypeOfHouse(bufferedReader.readLine());
            list.add(new Request(countOfRooms, area, typeOfHouse));
        }
        bufferedReader.close();
        if (list.isEmpty())
            throw new MyExeption("Файл " + requestsPath + " пуст");
        return list;
    }

    private static int extractCountOfRooms(String countOfRoomsStr){
        return Integer.parseInt(countOfRoomsStr.substring(0, 1));
    }

    private static String extractArea(String areaStr){
        return areaStr.substring(0, areaStr.indexOf(' '));
    }

    private static String extractTypeOfHouse(String typeOfHouseStr){
        return typeOfHouseStr.substring(0, typeOfHouseStr.indexOf(' '));
    }

    public void addRequest() throws MyExeption {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите колличество комнат в квартире-> ");
        int countOfRooms = sc.nextInt();
        System.out.print("Введите желаемый район-> ");
        String area = sc.next().trim();
        System.out.print("Введите номер типа дома(1.Кирпичный 2.Панельный)-> ");
        int type = sc.nextInt();
        String typeOfHouse= getTypeOfHouseName(type);
        requestsList.add(new Request(countOfRooms, area, typeOfHouse));
        System.out.println("Заявка добавлена" +
                "\n***************************");
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

    public void removeRequest() throws MyExeption {
        if (requestsList.size() == 0) {
            System.out.println("Список заявок пуст");
            return;
        }
        Scanner sc = new Scanner(System.in);
        requestsList.forEach((p) ->
                System.out.println("Заявка №" + (requestsList.indexOf(p) + 1) + "\n" + p));
        System.out.print("Введите номер заявки, которую хотите удалить-> ");
        int delIndex = sc.nextInt() - 1;
        if (!isRequestNumberExist(delIndex, requestsList.size()))
            throw new MyExeption("Вы ввели неверный номер");
        requestsList.remove(delIndex);
        System.out.println("Заявка удалена" +
                "\n***************************");
    }

    private boolean isRequestNumberExist(int requestNum, int requestsListSize){
        return Utils.isEnteredNumberExist(requestNum, requestsListSize);
    }

    public void sortRequests() {
        if (requestsList.size() == 0) {
            System.out.println("Список заявок пуст");
            return;
        }
        System.out.println("Отсортированный список заявок:");
        requestsList.stream().sorted().forEach(System.out::println);
        System.out.println("***************************");
    }
}