package Buildings;

import java.util.*;
import java.util.stream.Collectors;

public class BuildingCollection {
    private HashSet<UUID> _ids = new HashSet<UUID>();
    private Vector<BaseBuild> _buildings = new Vector<BaseBuild>();
    private TreeMap<UUID, Double> _bornTime = new TreeMap<UUID, Double>();
    
    public void Add(BaseBuild build)
    {
        _buildings.add(build);
        _ids.add(build._id);
        _bornTime.put(build._id, build._bornTime);
    }
    
    public void RemoveAll(List<BaseBuild> builds)
    {
        _buildings.removeAll(builds);
        _ids.removeAll(builds.stream().map(x -> x._id).collect(Collectors.toList()));
        for (UUID id :builds.stream().map(x -> x._id).collect(Collectors.toList()))
        {
            _bornTime.remove(id);
        }
    }

    public Vector<BaseBuild> GetAliveBuildings()
    {
        return _buildings;
    }

    public List<BaseBuild> GetOldRemoved(double time)
    {
        //ConcurrentModificationException 10/10
        //LINQ в джаве вроде как есть, когда-нибудь разберусь и с ним
        List<BaseBuild> buildsForRemoves = _buildings.stream().filter(build -> time - build._bornTime >= build._lifeTime).collect(Collectors.toList());
        if(!buildsForRemoves.isEmpty())
            RemoveAll(buildsForRemoves);
        return buildsForRemoves;
    }
}
