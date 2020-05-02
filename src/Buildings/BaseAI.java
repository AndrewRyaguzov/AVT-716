package Buildings;

import SubClasses.Timer;
import java.util.Random;

public abstract class BaseAI extends Thread
{
    private double _currentTime = 0;
    private volatile Timer _simulationTimer;
    private Timer _buildTimer = new Timer(_currentTime);
    private int _timerProgressValue;

    protected BuildingFactory _buildingFactory;
    private boolean _isWorking;

    public BaseAI(Timer simulationTimer, BuildingFactory buildingFactory)
    {
        super();
        _simulationTimer = simulationTimer;
        _buildingFactory = buildingFactory;

        _buildTimer.unpause(0);
        setPriority(Thread.MAX_PRIORITY);
        start();
    }

    public synchronized void run()
    {
        while (true)
        {
            if(!_isWorking) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (BaseBuild build : GetBuildings())
            {
                build.Move();
            }

            _currentTime = _simulationTimer.GetCurrentTime();
            _buildTimer.update(_currentTime);
            _timerProgressValue = (int) (_buildTimer.workTime / 10 / GetN());
            Random myRand = new Random();

            if (_timerProgressValue >= 100) {
                if (myRand.nextDouble() < GetP()) {
                    Create();
                }
                _buildTimer.restart(_currentTime);
            }
        }
    }
    public int GetProgress() {
        return _timerProgressValue;
    }

    public synchronized void Start()
    {
        _timerProgressValue = 0;
        _isWorking = true;
        notify();
    }

    public synchronized void UnPause()
    {
        _isWorking = true;
        _buildTimer.unpause(_currentTime);
        notify();
    }

    public boolean IsWorking(){return _isWorking;}

    public void Pause()
    {
        _isWorking = false;
        _buildTimer.pause(_currentTime);
    }

    public void Stop()
    {
        _timerProgressValue = 0;
        _isWorking = false;
        _buildTimer.restart(_currentTime );
    }


    protected abstract double GetN();
    protected abstract double GetP();
    protected abstract void Create();
    protected abstract BaseBuild[] GetBuildings();
}
