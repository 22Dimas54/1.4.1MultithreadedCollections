package ru.netology.honeybadger;

import java.util.concurrent.LinkedBlockingQueue;

public class AutomaticTelephoneExchange {
    private final static int NUMBER_OF_CALLS = 60;
    private final static int GENERATING_CALL = 1000;
    private final static int IMITATION_OF_A_SPECIALIST_WORK = 4000;
    private LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue();

//Выбрал LinkedBlockingQueue, так как эта очередь упорядочивает элементы FIFO (первым пришел-первым обслужен),
// то что и необходимо, первый позвонил первому и ответили и так в порядке "честной" очереди звонков

    public void generatingCallsAndCloseATS() {
        try {
            for (int i = 1; i <= NUMBER_OF_CALLS; i++) {
                Thread.sleep(GENERATING_CALL);
                this.linkedBlockingQueue.put(" звонок " + i);
                System.out.println("Поступил звонок " + i);
            }
//ждём пока все звонки будут обработаны и коллекция станет пустой
            while (!this.linkedBlockingQueue.isEmpty()) ;
//поток АТС усыпляем на время обработки последнего заказа
            Thread.sleep(IMITATION_OF_A_SPECIALIST_WORK);
//"мягко" останавливаем поток АТС
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void soundProcessing() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String call = this.linkedBlockingQueue.poll();
                if (call != null) {
                    System.out.println("Специалист " + Thread.currentThread().getName() + " ответил на" + call);
                    Thread.sleep(IMITATION_OF_A_SPECIALIST_WORK);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
