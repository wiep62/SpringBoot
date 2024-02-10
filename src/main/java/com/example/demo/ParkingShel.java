package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class ParkingShel {

    private final Map<Integer, Integer> slots = new HashMap<>();
    //метод заполняющий МАПУ:
    //указываем сколько доступно парковочных мест:

    @ShellMethod(key = "i")
    public String init(@ShellOption(value = "b") int big,
                       @ShellOption(value = "m") int medium,
                       @ShellOption(value = "s") int small){
        slots.put(1, big);
        slots.put(2, medium);
        slots.put(3, small);
        //после инициализаци возвр-ем сообщение о доступных слотах:
return MessageFormat.format("Slots: [big: {0}, medium: {1}, small: {2}", big, medium, small);
    }

    @ShellMethod(key = "a")
    @ShellMethodAvailability("canAddCar")
    public  String addCar(int carType) { //отдаем тип машины Большая = 1, средняя = 2, маленькая = 3
        //проверяем что для машины есть место для парковки и паркуем ее или нет.
        int newValue = slots.get(carType) - 1;

        if (newValue >= 0) {
        slots.put(carType, newValue);    //в парковочный слот кладем по типу парков-го места новое значение
        return "Car was parking";
        }
        return "Not space for parcing";
    }

    public Availability canAddCar(){
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : slots.entrySet()){
            int v = entry.getValue();
            count += v;
        }
        return count == 0 ? Availability.unavailable("Parcing is full") : Availability.available(); //этот метод обязательно должен возвр. org.springframework.shell.Availability
        // (Availability -объект, с пом-ю которого Спринг шел понимает может ли быть вызван метод или он не доступен для вызова)
        //для того чтобы этот метод проверялся мы используем аннотацию     @ShellMethodAvailability("canAddCar") перед методом addCar(int carType)
    }

}
