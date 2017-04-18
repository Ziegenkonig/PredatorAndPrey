import edu.kzoo.grid.Grid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;
import edu.kzoo.grid.gui.GridAppFrame;

public class Controller {
	 // Instance variable(s) for each Controller instance.
    private GridAppFrame gui;

    /** Constructs a new Controller object. **/
    public Controller(GridAppFrame gui) {
        this.gui = gui;
    }

    public void ExecuteLogic() {
    	Grid grid = gui.getGrid();
    	
    	//GridObject testObject = 0;
    	
    	//grid.add(obj, loc);
    }
}
