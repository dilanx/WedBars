# WedBars Documentation

Okay so you already know Wed Bars is the coolest game to exist ever. Here's how you set it up.

### About
Wed Bars is an open-source remake of Hypixel's Bed Wars. It's a plugin for Spigot 1.8.8 servers (same as 1.8.9).

### Installation
1. Add the Wed Bars plugin to your server (when it becomes publicly available).
2. Add the [Holographics Displays](https://dev.bukkit.org/projects/holographic-displays) plugin (a required dependency) to your server.
3. Optionally, add the [Citizens](https://www.spigotmc.org/resources/citizens.13811/) plugin to your server. You might want this if you want to have NPCs for the shops.

### Configuration
After running the server once, you can modify settings in `plugins/WedBars/config.yml` as you like.

### Setting up an arena
Lucky for your stupid self (haha jk dw), an easy-to-use setup wizard is included in Wed Bars. Run `/setup <name>` *(wedbars.admin)* to start setting up an arena with the specified name. The setup wizard is chat based, meaning after running that command, your chat broadcast will be disabled and you'll type stuff in chat to set stuff up.

Here are some tips before we get started:
* Running `/setup <name>` with a name that already exists will start editing that arena's data. Use this to make any changes to existing arenas if you need to.
* You can type `help` or `?` to see a list of setup chat commands.
* You can type `checklist` or `cl` to see what you've already set up and what else needs to be setup. Super helpful imo you should use this.
* You can type `cancel` at any time to discard all changes and exit the wizard.

Okay, let's set up an arena.

1. Head over to your arena lobby, stand in the exact position and face the exact direction you want players to be teleported to when being sent to the lobby, and type `lobby`.
2. Go to one of your arena's diamond generators, stand in the exact position where you want diamonds to spawn during the game, and type `diamondgen`. Repeat this for every diamond generator. If you make a mistake, you can clear all set diamond and emerald generators with `cleargens`.
3. Repeat step 2 but with `emeraldgen` for your emerald generators. Once again, you can use `cleargens` to remove all diamond and emerald generators.
4. Set the initial diamond generator spawn interval with `diamondspeed <speed>`, where the speed is the amount of seconds between diamond spawns times 10 (for example, if you want a diamond to spawn once every 30 seconds, you'd type 'diamondspeed 300').
5. Repeat step 4 but with `emeraldspeed <speed>` for the initial emerald generator spawn interval.

Now, let's set up some team values.

6. Set the initial iron generator spawn interval for team islands with `ironspeed <speed>` (speed is still the amount of seconds between spawns times 10).
7. Repeat step 6 but with `goldspeed <speed>` for the initial gold generator spawn interval.
8. Repeat step 7 but with `personalemeraldspeed <speed>` for the initial team island emerald generator spawn interval (for when the emerald forge team upgrade is unlocked).

Okay, now we'll set up the team islands themselves.

9. Type `team <teamColor>` (teamColor is the color of the team you're setting up lmao - check [here](https://github.com/dilanx/WedBars/blob/main/src/com/blockhead7360/mc/wedbars/team/Team.java) for possible team colors) to enter the team island setup wizard. Now, you can use `help`/`?` and `checklist`/`cl` and they'll display information specifically for the team setup. You can also type `cancel` to go back.
10. Stand in the exact position and face the exact direction you want players to be teleported to when they spawn at their island and type `spawn`.
11. Stand in the exact position where you want the island generator to be and type `generator` or `gen`.
12. Look at one of the halves of the island's bed (make sure your crosshair highlights it) and type `bed0`.
13. Look at the other half and type `bed1`.
14. Type `done` to save the team data. Now, type `checklist` and you'll see that the team is listed as set up.
15. Repeat steps 9-14 for every team you want in the arena. You'll need at least two.

Almost done!

16. Type `save` to save the arena. You'll exit the setup wizard automatically. All done! If you ever need to modify anything, you can run `/setup <name>` with the same arena name and it'll take you back into the wizard but with everything set already.

### Setting up NPCs
If you want to use NPCs for item and team upgrade shops, you can use Citizens. Let's start with the item shop.

1. Go to the location for the item shop NPC and run `/npc create <name>` (the name is what appears above their head, so you can type something like '&a&lItem Shop' as the name to set the nametag to 'Item Shop' in green and bold).
2. To set the skin of the NPC, use `/npc skin <skinName>` (skinName is the name of the player whose skin you want to use for the NPC).
3. Now, you'll need to make it so the NPC triggers the shop. Run `/npc cmd add itemshop -p` to add the command to the NPC.
4. If you right click on it, you'll notice it says 'Use one of the shop NPCs to access the shop.'. You'll need to make the NPC provide a temporary permission so that the command realizes it's through an NPC. Run `/npc cmd permissions wedbars.itemshop`. Now it should work.
5. If you'd like the NPC to look at the nearest player as they move around, you can run `/npc lookclose`.

Now, let's do the team upgrades shop.

6. 