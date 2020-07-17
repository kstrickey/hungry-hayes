package window.exploringGameEngine.gameEvents;

import window.exploringGameEngine.ExploringGameEngine;
import window.exploringGameEngine.simObjects.inanimates.InanimateObject;

public class AddInanimateEvent extends ExploringGameEvent {
    
    /**
     * Construct an AddInanimateEvent with the specified to-be-added inanimate object.
     */
    public AddInanimateEvent (InanimateObject inanimate) {
        this.inanimate = inanimate;
    }
    
    public void exploringExecute(ExploringGameEngine exploringGameEngine) {
        exploringGameEngine.getSimManager().addInanimate(inanimate);        
    }
    
    private final InanimateObject inanimate;
    
}
