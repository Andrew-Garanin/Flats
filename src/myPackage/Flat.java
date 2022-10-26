package myPackage;

import myExeptions.MyExeption;

public class Flat implements Comparable<Flat>{

    private final double metrics;
    private final int countOfRooms;
    private final String[] address;
    private final int floor;
    private final String typeOfHouse;
    private final int countOfFloors;
    private final long price;

    public Flat(double metrics, int countOfRooms, String[] address, int floor, String typeOfHouse, int countOfFloors, long price) throws MyExeption
    {
        if(metrics<=0)
            throw new MyExeption("Ошибка в метраже");
        if(countOfRooms<=0)
            throw new MyExeption("Ошибка в числе комнат");
        if(!address[0].matches("[а-я||А-Я]+"))
            throw new MyExeption("Ошибка в адресе1");
        if(!address[1].matches("[а-я||А-Я]+"))
            throw new MyExeption("Ошибка в адресе2");
        if(!address[2].matches("[0-9]+"))
            throw new MyExeption("Ошибка в адресе3");
        if(floor<=0)
            throw new MyExeption("Ошибка в номере этажа");
        if(!typeOfHouse.equals("Кирпичный")&&!typeOfHouse.equals("Панельный"))
            throw new MyExeption("Ошибка в типе дома");
        if(countOfFloors<=0||countOfFloors<floor)
            throw new MyExeption("Ошибка в колличестве этажей");
        if(price<=0)
            throw new MyExeption("Ошибка в цене");
        this.metrics=metrics;
        this.countOfRooms=countOfRooms;
        this.address = address;
        this.floor=floor;
        this.typeOfHouse=typeOfHouse;
        this.countOfFloors=countOfFloors;
        this.price=price;
    }

    public long getPrice(){
        return this.price;
    }

    public int getCountOfRooms(){
        return this.countOfRooms;
    }

    public String getArea(){
        return this.address[0];
    }

    public String getTypeOfHouse(){
        return typeOfHouse;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder= new StringBuilder("");
        stringBuilder.append("Метраж: "+metrics+"\n" +
                "Число комнат:"+countOfRooms+"\n" +
                "Адрес: "+ address[0]+" р-н, ул. "+ address[1]+", д. "+ address[2]+"\n" +
                "Этаж: "+floor+"\n" +
                "Тип дома: "+typeOfHouse+"\n" +
                "Колличество этажей в доме: "+countOfFloors+"\n" +
                "Цена: "+price);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Flat p) {
        return address[0].toUpperCase().compareTo(p.address[0].toUpperCase());
    }
}