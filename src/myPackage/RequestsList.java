package myPackage;

import myExeptions.MyExeption;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RequestsList {
    private static final String requestsPath = "requests.txt";

    private final ArrayList<Request> requestsList;

    public RequestsList() throws MyExeption, IOException {
        requestsList = readFileWithRequests();
    }

    public Request getRequestByIndex(int index){
        return requestsList.get(index);
    }

    public int getRequestListSize(){
        return requestsList.size();
    }

    public static ArrayList<Request> readFileWithRequests() throws IOException, MyExeption {
        ArrayList<Request> list = new ArrayList<Request>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(requestsPath));
        String tempStr;
        int countOfRooms;
        String area;
        String typeOfHouse;
        while ((tempStr = bufferedReader.readLine()) != null) {
            if (tempStr.isEmpty())
                continue;
            countOfRooms = Integer.parseInt(tempStr.substring(0, 1));
            tempStr = bufferedReader.readLine();
            area = tempStr.substring(0, tempStr.indexOf(' '));
            tempStr = bufferedReader.readLine();
            typeOfHouse = tempStr.substring(0, tempStr.indexOf(' '));
            list.add(new Request(countOfRooms, area, typeOfHouse));
        }
        bufferedReader.close();
        if (list.isEmpty())
            throw new MyExeption("Файл " + requestsPath + " пуст");
        return list;
    }

    public void addRequest() throws MyExeption {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите колличество комнат в квартире-> ");
        int countOfRooms = sc.nextInt();
        System.out.print("Введите желаемый район-> ");
        String area = sc.next().trim();
        System.out.print("Введите номер типа дома(1.Кирпичный 2.Панельный)-> ");
        int type = sc.nextInt();
        String typeOfHouse;
        if (type == 1)
            typeOfHouse = "Кирпичный";
        else if (type == 2)
            typeOfHouse = "Панельный";
        else throw new MyExeption("Вы ввели неверный номер типа дома");
        requestsList.add(new Request(countOfRooms, area, typeOfHouse));
        System.out.println("Заявка добавлена" +
                "\n***************************");
    }

    public void removeRequest() throws MyExeption {
        if (requestsList.size() == 0) {
            System.out.println("Список заявок пуст");
            return;
        }
        Scanner sc = new Scanner(System.in);
        requestsList.stream().forEach((p) ->
                System.out.println("Заявка №" + (requestsList.indexOf(p) + 1) + "\n" + p));
        System.out.print("Введите номер заявки, которую хотите удалить-> ");
        int delIndex = sc.nextInt() - 1;
        if (delIndex < 0 || delIndex > requestsList.size())
            throw new MyExeption("Вы ввели неверный номер");
        requestsList.remove(delIndex);
        System.out.println("Заявка удалена" +
                "\n***************************");
    }

    public void sortRequests() {
        if (requestsList.size() == 0) {
            System.out.println("Список заявок пуст");
            return;
        }
        System.out.println("Отсортированный список заявок:");
        ArrayList<Request> sortedRequests = requestsList;
        sortedRequests.stream().sorted().forEach((p) -> System.out.println(p));
        System.out.println("***************************");
    }
}