package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        String[] cityes = new String[]{"Ставрополь", "Махачкала", "Краснодар", "Ростов на Дону", "Черкесск", "Астрахань", "Волгоград", "Элиста"};// TODO: добавить логику для объявления переменной city и задания её значения, генерацию можно выполнить
        // с помощью Faker, либо используя массив валидных городов и класс Random
        return cityes[new Random().nextInt(cityes.length)];
    }

    public static String generateName(Faker faker) {
        return faker.name().lastName() +" " + faker.name().firstName();// TODO: добавить логику для объявления переменной name и задания её значения, для генерации можно
    }

    public static String generatePhone(Faker faker) {
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            return new UserInfo(generateCity(), generateName(faker), generatePhone(faker));// generateName(faker), generatePhone(faker)
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}