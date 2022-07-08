package ru.netology.honeybadger;

import java.util.concurrent.LinkedBlockingQueue;

public class AutomaticTelephoneExchange {
    private final static int NUMBER_OF_CALLS = 60;
    private final static int GENERATING_CALL = 1000;
    private final static int IMITATION_OF_A_SPECIALIST_WORK = 4000;
    private LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue();

    private boolean workIsFinished;

//Выбрал LinkedBlockingQueue, так как эта очередь упорядочивает элементы FIFO (первым пришел-первым обслужен),
// то что и необходимо, первый позвонил первому и ответили и так в порядке "честной" очереди звонков

    public void generatingCallsAndCloseATS() {
        try {
            for (int i = 1; i <= NUMBER_OF_CALLS; i++) {
                Thread.sleep(GENERATING_CALL);
                linkedBlockingQueue.put(" звонок " + i);
                System.out.println("Поступил звонок " + i);
            }
//ждём пока все звонки будут обработаны и коллекция станет пустой
            while (!linkedBlockingQueue.isEmpty()) ;
            workIsFinished = true;
//поток АТС усыпляем на время обработки последнего заказа
            Thread.sleep(IMITATION_OF_A_SPECIALIST_WORK);
            System.out.println("\nСпециалисты обработали все звонки!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void soundProcessing() {
        Thread.currentThread().setName("Специалист " + (int) (Math.random() * IMITATION_OF_A_SPECIALIST_WORK));
        while (!workIsFinished) {
            try {
                String call = linkedBlockingQueue.poll();
                if (call != null) {
                    System.out.println(Thread.currentThread().getName() + " ответил на" + call);
                    Thread.sleep(IMITATION_OF_A_SPECIALIST_WORK);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
