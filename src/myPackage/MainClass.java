package myPackage;

import myExeptions.MyExeption;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MainClass {
    private static final String flatsPath = "flats.txt";
    private static final String requestsPath = "requests.txt";
    private static final String searchFilePath = "data.txt";

    public static void main(String[] args) {
        try {
            FlatsList flatsList = new FlatsList(flatsPath);
            RequestsList requestsList = new RequestsList(requestsPath);
            Scanner sc = new Scanner(System.in);
            int action;
            while (true) {
                try {
                    System.out.print("Выберите действие, которое хотите выполнить:\n" +
                            "1.Добавить квартиру\n" +
                            "2.Добавить заявку\n" +
                            "3.Удалить квартиру\n" +
                            "4.Удалить заявку\n" +
                            "5.Найти квартиру по заявке\n" +
                            "6.Вывести на экран все продаваемые квартиры, отсортированные по районам\n" +
                            "7.Вывести на экран все заявки, отсортированные по колличеству комнат\n" +
                            "8.Вывести на экран все квартиры по заданному диапазону стоимости\n" +
                            "9.Выход\n->");
                    action = sc.nextInt();
                    switch (action) {
                        case 1:
                            flatsList.addFlat();
                            break;
                        case 2:
                            requestsList.addRequest();
                            break;
                        case 3:
                            flatsList.removeFlat();
                            break;
                        case 4:
                            requestsList.removeRequest();
                            break;
                        case 5:
                            flatsList.searchFlats(requestsList, searchFilePath);
                            break;
                        case 6:
                            flatsList.sortFlats();
                            break;
                        case 7:
                            requestsList.sortRequests();
                            break;
                        case 8:
                            flatsList.flatsFromRange();
                            break;
                        case 9:
                            return;
                    }
                }
                catch (InputMismatchException|IndexOutOfBoundsException e) {
                    System.out.println("Вы ввели некорректные даные или нарушили формат ввода"+
                            "\n***************************");
                    sc.nextLine();
                }
                catch (NumberFormatException e) {
                    System.out.println("Ошибка в данных1"+
                            "\n***************************");
                }
                catch (MyExeption e) {
                    System.out.println(e.getMessage()+
                            "\n***************************");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка в данных файла");
        } catch (IndexOutOfBoundsException|NullPointerException  e) {
            System.out.println("Недостаточно данных в файле");
        } catch (MyExeption e) {
            System.out.println("Ошибка при чтении файла ");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}