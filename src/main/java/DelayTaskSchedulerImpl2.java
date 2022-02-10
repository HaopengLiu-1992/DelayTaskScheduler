import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DelayTaskSchedulerImpl2 implements DelayTaskScheduler{
    private PriorityQueue<Task> pq;
    private final transient Lock lock;
    private final transient Condition condition;
    public DelayTaskSchedulerImpl2(){
        pq = new PriorityQueue<>();
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    @Override
    public void addTask(Task task) {
        lock.lock();
        try {
            pq.add(task);
            if(task == pq.peek()) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Task getTask(){
        lock.lock();
        try {
            while (true) {
                while (pq.size() == 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Task task = pq.peek();
                if (task.getWaitTime() <= 0) {
                    return pq.poll();
                } else {
                    try {
                        condition.await(task.getWaitTime(), TimeUnit.MILLISECONDS);
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
