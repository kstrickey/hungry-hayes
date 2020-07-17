package window.exploringGameEngine.simObjects.inanimates;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.PaintMachine;

public enum BarrierType {
    H281 (PaintMachine.getImageFromName("inanimate/barrier h 281 on.png"),
            PaintMachine.getImageFromName("inanimate/barrier h 281 off.png")),
    V281 (PaintMachine.getImageFromName("inanimate/barrier v 281 on.png"),
            PaintMachine.getImageFromName("inanimate/barrier v 281 off.png"));
    
    public final BufferedImage onImage, offImage;
    
    BarrierType(BufferedImage onImage, BufferedImage offImage) {
        this.onImage = onImage;
        this.offImage = offImage;
    }
    
    public int getWidth() {
        return onImage.getWidth();
    }
    
    public int getHeight() {
        return onImage.getHeight();
    }
    
}