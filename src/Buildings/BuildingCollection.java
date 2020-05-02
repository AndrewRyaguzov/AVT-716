package Buildings;

import java.util.*;
import java.util.stream.Collectors;

public class BuildingCollection {
    private HashSet<UUID> _ids = new HashSet<UUID>();
    private Vector<BaseBuild> _buildings = new Vector<BaseBuild>();
    private TreeMap<UUID, Double> _bornTime = new TreeMap<UUID, Double>();

    private ArrayList<WoodBuild> _woodBuildings = new ArrayList<>();
    public ArrayList<WoodBuild> WoodBuildings() {return _woodBuildings;}

    private ArrayList<CapitalBuild> _capitalBuildings = new ArrayList<>();
    public  ArrayList<CapitalBuild> CapitalBuildings(){return _capitalBuildings;}

    public synchronized void AddCapitalBuilding(CapitalBuild build)
    {
        _capitalBuildings.add(build);
        Add(build);
    }

    public synchronized void AddWoodBuilding(WoodBuild build)
    {
        _woodBuildings.add(build);
        Add(build);
    }

    private synchronized void Add(BaseBuild build)
    {
        _buildings.add(build);
        _ids.add(build._id);
        _bornTime.put(build._id, build._bornTime);
    }
    
    public synchronized void RemoveAll(List<BaseBuild> builds)
    {
        _buildings.removeAll(builds);
        _ids.removeAll(builds.stream().map(x -> x._id).collect(Collectors.toList()));
        for (UUID id :builds.stream().map(x -> x._id).collect(Collectors.toList()))
        {
            _bornTime.remove(id);
        }
    }

    public synchronized Vector<BaseBuild> GetAliveBuildings()
    {
        return _buildings;
    }

    public synchronized List<BaseBuild> GetOldRemoved(double time)
    {
        //ConcurrentModificationException 10/10
        //LINQ в джаве вроде как есть, когда-нибудь разберусь и с ним
        List<BaseBuild> buildsForRemoves = _buildings.stream().filter(build -> time - build._bornTime >= build._lifeTime).collect(Collectors.toList());
        if(!buildsForRemoves.isEmpty())
            RemoveAll(buildsForRemoves);
        return buildsForRemoves;
    }

    public void MoveAll()
    {
        for (BaseBuild build :_buildings)
        {
            build.Move();
        }
    }
}
