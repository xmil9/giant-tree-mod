package xmilcode.gianttreemod;

import xmilcode.mclib.ModModule;
import xmilcode.mclib.proxies.ClientProxy;


public class GiantTreeClientProxy extends ClientProxy
{
    @Override
    public void registerRenderers(ModModule pkg)
    {
        super.registerRenderers(pkg);
        pkg.registerRenderers();
    }
}
