import java.io.Serializable;
import java.util.ArrayList;

public class SavedSimulation implements Serializable {
    ArrayList<Transport> myObjects;

    SavedSimulation(ArrayList  myObjects){
        this.myObjects = myObjects;
    }
}
