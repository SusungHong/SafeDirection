import java.util.*;

public class SafeAStarSearch {
	
	private MapGraph mg = null;
	
	Comparator<MapGraph.MapVertex> vertexComparator = new Comparator<MapGraph.MapVertex>() {
         @Override
         public int compare(MapGraph.MapVertex v, MapGraph.MapVertex u) {
        	 double g1 = v.getCost() + v.getHeuristic();
        	 double g2 = u.getCost() + u.getHeuristic();
				if(g1 < g2)
					return -1;
				else if(g1 > g2)
					return 1;
				else
					return 0;
         }
     };
	private Queue<MapGraph.MapVertex> frontier = new PriorityQueue<>(vertexComparator);
    
    private List<String> explored = new ArrayList<>();
    
    public SafeAStarSearch(MapGraph mg) {
    	this.mg = mg;
    }
    
    public List<MapGraph.MapVertex> AStarSearch(String startId, String endId, boolean safetyMode) {
    	
    	MapGraph.MapVertex startv = mg.findVertexById(startId);
    	MapGraph.MapVertex endv = mg.findVertexById(endId);
    	
    	if(startv == null || endv == null)
    		return new ArrayList<>();
    	
    	mg.initCosts();
    	mg.loadReputation();
    	frontier.clear();
    	explored.clear();
    	
    	startv.updateCost(0, mg.getDistance(startv, endv), null);
    	frontier.add(startv);
    	
    	while(true) {
    		
    		if(frontier.isEmpty()) return null;
    		
    		MapGraph.MapVertex v = frontier.remove();
    		if(v.getId().equals(endId)) {
    			return mg.constructPath(endId);
    		}
    		explored.add(v.getId());
    		
    		for(MapGraph.MapVertex u : v.getNeighbors()) {
    			if(!frontier.contains(u) && !explored.contains(u.getId())) {
    				double cost;
    				if(safetyMode) {
    					cost = v.getCost() + mg.getEdgeCost(v, u) + mg.getSafetyCost(v, u);
    				}
    				else {
    					cost = v.getCost() + mg.getEdgeCost(v, u);
    				}
    				u.updateCost(cost, mg.getDistance(u, endv), v.getId());
    				frontier.add(u);
    			}
    			else if(frontier.contains(u) && u.getCost() > v.getCost() +  + mg.getSafetyCost(v, u) + mg.getEdgeCost(v, u)) {
    				frontier.remove(u);
    				double cost;
    				if(safetyMode) {
    					cost = v.getCost() + mg.getEdgeCost(v, u) + mg.getSafetyCost(v, u);
    				}
    				else {
    					cost = v.getCost() + mg.getEdgeCost(v, u);
    				}
    				u.updateCost(cost, mg.getDistance(u, endv), v.getId());
    				frontier.add(u);
    			}
    		}
    	}
    }

}