package me.earth.pingbypass.api.network;
package me.earth.pingbypass.api.network;

import net.minecraft.util.Identifier;

public final class Channels {
    public static final Identifier PING         = new Identifier("pingbypass", "ping");
    public static final Identifier PONG         = new Identifier("pingbypass", "pong");
    public static final Identifier YOUR_CUSTOM  = new Identifier("pingbypass", "your_custom");
    private Channels() {}
}
