import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Demo{
    public static void main(String[] args) {
        int maxCore = Runtime.getRuntime().availableProcessors();
        DelayTaskScheduler delayTaskScheduler = new DelayTaskSchedulerImpl0();
        ThreadPoolExecutor prod = new ThreadPoolExecutor(2, maxCore, 4000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
        ThreadPoolExecutor cons = new ThreadPoolExecutor(2, maxCore, 4000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
        try {
            prod.execute(() -> delayTaskScheduler.addTask(new Task("TASK0", 1000)));
            cons.execute(()-> delayTaskScheduler.getTask().printTask());
            prod.execute(() -> delayTaskScheduler.addTask(new Task("TASK1", 200)));
            prod.execute(() -> delayTaskScheduler.addTask(new Task("TASK2", 400)));
            prod.execute(() -> delayTaskScheduler.addTask(new Task("TASK3", 500)));
            cons.execute(()-> delayTaskScheduler.getTask().printTask());
            prod.execute(() -> delayTaskScheduler.addTask(new Task("TASK4", 100)));
            cons.execute(()-> delayTaskScheduler.getTask().printTask());
            cons.execute(()-> delayTaskScheduler.getTask().printTask());
            cons.execute(()-> delayTaskScheduler.getTask().printTask());
        } finally {
            prod.shutdown();
            cons.shutdown();
        }
    }
}


