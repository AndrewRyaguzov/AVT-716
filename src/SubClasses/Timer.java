package SubClasses;

public class Timer {
    public double workTime;
    public double startTime;
    private double diff;
    private boolean isPaused;

    public Timer(double currentTimeMillis) {
        restart(currentTimeMillis);
        pause(currentTimeMillis);
    }

    public void update(double currentTimeMillis) {
        if(!isPaused)
            workTime = currentTimeMillis - startTime - diff;
        else
            diff = currentTimeMillis - startTime - workTime;
    }

    public void restart(double currentTimeMillis) {
        unpause(currentTimeMillis);
        diff = 0;
        workTime = 0;
        startTime = currentTimeMillis;
    }

    public void unpause(double currentTimeMillis){
        isPaused = false;
    }

    public void pause(double currentTimeMillis){
        isPaused = true;
    }

    public boolean isPaused(double currentTimeMillis){
        return isPaused;
    }
    
    public void stop(double currentTimeMillis){
        restart(currentTimeMillis);
        pause(currentTimeMillis);
    }
}