package window.exploringGameEngine.gameEvents;

import window.exploringGameEngine.ExploringGameEngine;
import window.exploringGameEngine.simObjects.inanimates.InanimateObject;

public class RemoveInanimateEvent extends ExploringGameEvent {
    private InanimateObject inanimate;
    
    /**
     * Construct a RemoveInanimateEvent with the specified to-be-removed inanimate object.
     */
    public RemoveInanimateEvent (InanimateObject inanimate) {
        this.inanimate = inanimate;
    }
    
    public void exploringExecute(ExploringGameEngine ege) {
        ege.getSimManager().removeInanimate(inanimate);        
    }
}
