package xmilcode.mclib;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;


// Keeps track of game setting and notifies registered observers if
// a setting changes.
// Extend as needed.
public class GameSettingsTracker
{
    private Set<GameSettingsObserver> observers;
    private boolean storedFancyGraphics;
    
    
    public GameSettingsTracker()
    {
        this.observers = new HashSet<GameSettingsObserver>();

        GameSettings settings = settings();
        this.storedFancyGraphics = settings.fancyGraphics;
    }
    
    
    private static GameSettings settings()
    {
        return Minecraft.getMinecraft().gameSettings;
    }
    
    
    public void registerObserver(GameSettingsObserver observer)
    {
        observers.add(observer);
    }
    
    
    public void unregisterObserver(GameSettingsObserver observer)
    {
        observers.remove(observer);
    }
    
    
    public void track()
    {
        GameSettings settings = settings();
        trackFancyGraphics(settings);
    }
    
    
    void trackFancyGraphics(GameSettings settings)
    {
        if (settings.fancyGraphics != storedFancyGraphics)
        {
            onFancyGraphicsChanged(settings.fancyGraphics);
            storedFancyGraphics = settings.fancyGraphics;
        }
    }
    
    
    private void onFancyGraphicsChanged(boolean newValue)
    {
        for (GameSettingsObserver observer : observers)
            observer.onFancyGraphicsChanged(newValue);
    }
}
