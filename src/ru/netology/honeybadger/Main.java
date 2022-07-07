package ru.netology.honeybadger;

public class Main {

    public static void main(String[] args) {
        AutomaticTelephoneExchange automaticTelephoneExchange = new AutomaticTelephoneExchange();
        Thread threadATS = new Thread(null, () -> automaticTelephoneExchange.generatingCallsAndCloseATS());
        threadATS.setName("АТС");
        threadATS.start();

        Thread tdSpecialist1 = new Thread(null, () -> automaticTelephoneExchange.soundProcessing());
        Thread tdSpecialist2 = new Thread(null, () -> automaticTelephoneExchange.soundProcessing());
        Thread tdSpecialist3 = new Thread(null, () -> automaticTelephoneExchange.soundProcessing());

        tdSpecialist1.setName("1");
        tdSpecialist2.setName("2");
        tdSpecialist3.setName("3");

        tdSpecialist1.start();
        tdSpecialist2.start();
        tdSpecialist3.start();

        try {
            threadATS.join();
            tdSpecialist1.interrupt();
            tdSpecialist2.interrupt();
            tdSpecialist3.interrupt();
            tdSpecialist1.join();
            tdSpecialist2.join();
            tdSpecialist3.join();
            System.out.println("Специалисты обработали все звонки!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
