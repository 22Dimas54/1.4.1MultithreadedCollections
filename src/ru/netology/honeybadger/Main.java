package ru.netology.honeybadger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final AutomaticTelephoneExchange automaticTelephoneExchange = new AutomaticTelephoneExchange();
    private static final int COUNT = 3;

    public static void main(String[] args) {
        AutomaticTelephoneExchange automaticTelephoneExchange = new AutomaticTelephoneExchange();
        addTasksInExecutorService();
        executorService.shutdown();
    }

    private static void addTasksInExecutorService() {
        executorService.submit(() -> automaticTelephoneExchange.generatingCallsAndCloseATS());
        for (int i = 0; i < COUNT; i++) {
            executorService.submit(() -> automaticTelephoneExchange.soundProcessing());
        }
    }
}
