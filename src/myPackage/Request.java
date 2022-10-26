package myPackage;

import myExeptions.MyExeption;

public class Request implements Comparable<Request> {
    private Integer countOfRooms;
    private String area;
    private String typeOfHouse;

    public Request(int countOfRooms, String area, String typeOfHouse)throws MyExeption{
        this.countOfRooms=countOfRooms;
        this.area=area;
        this.typeOfHouse=typeOfHouse;
        if(countOfRooms<=0)
            throw new MyExeption("Ошибка в числе комнат(заявки)");
        if(!area.matches("[а-я||А-я]+"))
            throw new MyExeption("Ошибка в адресе(заявки)");
        if(!typeOfHouse.equals("Кирпичный")&&!typeOfHouse.equals("Панельный"))
            throw new MyExeption("Ошибка в типе дома(заявки)");
    }

    public int getCountOfRooms(){
        return this.countOfRooms;
    }

    public String getArea(){
        return this.area;
    }

    public String getTypeOfHouse(){
        return typeOfHouse;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder= new StringBuilder("");
        stringBuilder.append("Число комнат:"+countOfRooms+"\n" +
                "Район: "+area+"\n" +
                "Тип дома: "+typeOfHouse);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Request p) {
        return countOfRooms.compareTo(p.countOfRooms);
    }
}
