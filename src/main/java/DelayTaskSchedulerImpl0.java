import java.util.PriorityQueue;

public class DelayTaskSchedulerImpl0 implements DelayTaskScheduler{
    private final PriorityQueue<Task>pq;
    public DelayTaskSchedulerImpl0(){
        pq = new PriorityQueue<>();
    }

    @Override
    public synchronized void addTask(Task task) {
        pq.add(task);
    }

    @Override
    public synchronized Task getTask() {
        while(pq.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(pq.peek().getWaitTime() >= 0) {
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Task task = pq.poll();
        this.notifyAll();
        return task;
    }
}
