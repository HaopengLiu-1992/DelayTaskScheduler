import java.time.Instant;

public class Task implements Comparable<Task>{
    private final String name;
    private final long executeTime;
    private final long createdAt;

    Task(String name, long delay){
        this.name = name;
        createdAt = Instant.now().toEpochMilli();
        executeTime = delay + createdAt;
    }

    public long getExecuteTime(){
        return executeTime;
    }

    public void printTask() {
        System.out.println(Thread.currentThread() + "  Task  "+ name + " that created at" + createdAt + " starts to run at " + executeTime);
    }

    public long getWaitTime() {
        return executeTime - Instant.now().toEpochMilli();
    }

    @Override
    public int compareTo(Task b) {
        return Long.compare(getExecuteTime(), b.getExecuteTime());
    }
}