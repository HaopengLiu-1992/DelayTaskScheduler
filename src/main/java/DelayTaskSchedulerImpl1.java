import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DelayTaskSchedulerImpl1 implements DelayTaskScheduler{
    private final PriorityQueue<Task>pq;
    private final transient Lock lock;
    private final transient Condition condition;
    public DelayTaskSchedulerImpl1(){
        pq = new PriorityQueue<>();
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    @Override
    public void addTask(Task task) {
        lock.lock();
        try {
            pq.add(task);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Task getTask() {
        lock.lock();
        try {
            while(true) {
                while(pq.size() == 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Task task = pq.peek();
                if(task.getWaitTime() <= 0) {
                    return pq.poll();
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
