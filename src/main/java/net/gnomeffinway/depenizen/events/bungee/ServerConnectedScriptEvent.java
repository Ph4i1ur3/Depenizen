package net.gnomeffinway.depenizen.events.bungee;

import net.aufdemrand.denizen.BukkitScriptEntryData;
import net.aufdemrand.denizencore.objects.Element;
import net.aufdemrand.denizencore.objects.dObject;
import net.aufdemrand.denizencore.scripts.ScriptEntryData;
import net.aufdemrand.denizencore.scripts.containers.ScriptContainer;
import net.aufdemrand.denizencore.utilities.CoreUtilities;
import net.gnomeffinway.depenizen.objects.bungee.dServer;

import java.util.HashMap;
import java.util.Map;

public class ServerConnectedScriptEvent extends BungeeScriptEvent {

    // <--[event]
    // @Events
    // bungee player joins server
    // bungee player joins <server>
    //
    // @Triggers when a player fully connects to a server on the BungeeCord network.
    //
    // @Cancellable false
    //
    // @Context
    // <context.uuid> returns an Element of the player's UUID.
    // <context.name> returns an Element of the player's current name.
    // <context.server> returns the dServer the player has connected to.

    // @Determine
    // None
    //
    // @Plugin Depenizen, BungeeCord
    // -->


    public ServerConnectedScriptEvent() {
        instance = this;
    }

    public static ServerConnectedScriptEvent instance;
    public Element uuid;
    public Element name;
    public dServer server;

    @Override
    public boolean couldMatch(ScriptContainer scriptContainer, String s) {
        String lower = CoreUtilities.toLowerCase(s);
        return lower.startsWith("bungee player joins ");
    }

    @Override
    public boolean matches(ScriptContainer scriptContainer, String s) {
        return tryServer(server, CoreUtilities.getXthArg(3, s));
    }

    @Override
    public String getName() {
        return "ServerConnected";
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return new BukkitScriptEntryData(null, null);
    }

    @Override
    public HashMap<String, dObject> getContext() {
        HashMap<String, dObject> context = super.getContext();
        context.put("uuid", uuid);
        context.put("name", name);
        context.put("server", server);
        return context;
    }

    @Override
    public Map<String, String> fire(Map<String, String> context) {
        uuid = new Element(context.get("uuid"));
        name = new Element(context.get("name"));
        server = dServer.getServerFromName(context.get("server"));
        fire();
        return null;
    }
}