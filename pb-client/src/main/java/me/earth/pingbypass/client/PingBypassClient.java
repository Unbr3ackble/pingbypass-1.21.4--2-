package me.earth.pingbypass.client;

import lombok.Getter;
import me.earth.pingbypass.AbstractPingBypass;
import me.earth.pingbypass.api.command.Chat;
import me.earth.pingbypass.api.command.CommandManager;
import me.earth.pingbypass.api.config.ConfigManager;
import me.earth.pingbypass.api.event.api.EventBus;
import me.earth.pingbypass.api.files.FileManager;
import me.earth.pingbypass.api.input.KeyboardAndMouse;
import me.earth.pingbypass.api.module.ModuleManager;
import me.earth.pingbypass.api.players.PlayerRegistry;
import me.earth.pingbypass.api.plugin.PluginManager;
import me.earth.pingbypass.api.security.SecurityManager;
import me.earth.pingbypass.api.side.Side;
import net.minecraft.client.Minecraft;

// Fabric API imports
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import me.earth.pingbypass.api.network.Channels;

@Getter
public class PingBypassClient extends AbstractPingBypass implements ClientModInitializer {
    public PingBypassClient(EventBus eventBus,
                            KeyboardAndMouse keyBoardAndMouse,
                            CommandManager commandManager,
                            ModuleManager moduleManager,
                            ConfigManager configManager,
                            FileManager fileManager,
                            FileManager rootFileManager,
                            SecurityManager securityManager,
                            PluginManager pluginManager,
                            PlayerRegistry friendManager,
                            PlayerRegistry enemyManager,
                            Minecraft minecraft,
                            Chat chat) {
        super(eventBus, keyBoardAndMouse, commandManager, moduleManager,
              configManager, fileManager, rootFileManager, securityManager,
              pluginManager, friendManager, enemyManager, minecraft, chat,
              Side.CLIENT);
    }

    @Override
    public void onInitializeClient() {
        // Initialize core systems if needed:
        // super.init();

        // Register incoming "PONG" packets
        ClientPlayNetworking.registerGlobalReceiver(
            Channels.PONG,
            (client, handler, buf, responseSender) -> {
                int pongValue = buf.readInt();
                client.execute(() -> {
                    System.out.println("[PingBypass] Got PONG: " + pongValue);
                });
            }
        );
    }

    /**
     * Helper to send a PING to the server.
     */
    public static void sendPing(int value) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(value);
        ClientPlayNetworking.send(Channels.PING, buf);
    }
}

