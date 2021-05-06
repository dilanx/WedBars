# WedBars Setup Documentation

Okay so you already know Wed Bars is the coolest game to exist ever. Here's how you set it up.

## About
Wed Bars is an open-source remake of Hypixel's Bed Wars. It's a plugin for Spigot 1.8.8 servers (same as 1.8.9).

## Installation
1. Add the Wed Bars plugin to your server (when it becomes publicly available).
2. Add the [Holographics Displays](https://dev.bukkit.org/projects/holographic-displays) plugin (a required dependency) to your server.
3. Optionally, add the [Citizens](https://www.spigotmc.org/resources/citizens.13811/) plugin to your server. You might want this if you want to have NPCs for the shops.

## Configuration
After running the server once, you can modify settings in `plugins/WedBars/config.yml` as you like.

## Setting up an arena
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
6. Now, set the maximum build height with `buildheight <y-level>`.

Now, let's set up some team values.

7. Set the initial iron generator spawn interval for team islands with `ironspeed <speed>` (speed is still the amount of seconds between spawns times 10).
8. Repeat step 6 but with `goldspeed <speed>` for the initial gold generator spawn interval.
9. Repeat step 7 but with `personalemeraldspeed <speed>` for the initial team island emerald generator spawn interval (for when the emerald forge team upgrade is unlocked).

Okay, now we'll set up the team islands themselves.

10. Type `team <teamColor>` (teamColor is the color of the team you're setting up lmao - check [here](https://github.com/dilanx/WedBars/blob/main/src/com/blockhead7360/mc/wedbars/team/Team.java) for possible team colors) to enter the team island setup wizard. Now, you can use `help`/`?` and `checklist`/`cl` and they'll display information specifically for the team setup. You can also type `cancel` to go back.
11. Stand in the exact position and face the exact direction you want players to be teleported to when they spawn at their island and type `spawn`.
12. Stand in the exact position where you want the island generator to be and type `generator` or `gen`.
13. Look at one of the halves of the island's bed (make sure your crosshair highlights it) and type `bed0`.
14. Look at the other half and type `bed1`.
15. Type `done` to save the team data. Now, type `checklist` and you'll see that the team is listed as set up.
16. Repeat steps 9-14 for every team you want in the arena. You'll need at least two.

Almost done!

17. Type `save` to save the arena. You'll exit the setup wizard automatically. All done! If you ever need to modify anything, you can run `/setup <name>` with the same arena name and it'll take you back into the wizard but with everything set already.

## Setting up NPCs
If you want to use NPCs for item and team upgrade shops, you can use Citizens. Here's how you do it.

1. Go to the location for the item shop NPC and run `/npc create <name>` (the name is what appears above their head, so you can type something like '&a&lItem Shop' as the name to set the nametag to 'Item Shop' in green and bold).
2. To set the skin of the NPC, use `/npc skin <skinName>` (skinName is the name of the player whose skin you want to use for the NPC).
3. Now, you'll need to make it so the NPC triggers the shop. Run `/npc cmd add itemshop -p` to add the command to the NPC.
4. If you right click on it, you'll notice it says 'Use one of the shop NPCs to access the shop.'. You'll need to make the NPC provide a temporary permission so that the command realizes it's through an NPC. Run `/npc cmd permissions wedbars.itemshop`. Now it should work.
5. If you'd like the NPC to look at the nearest player as they move around, you can run `/npc lookclose`.
6. Repeat steps 1-5 but change stuff as you'd like for the team upgrades shop. The main change you'll need to do is run `/npc cmd add teamshop -p` during step 3 instead of the itemshop command.

Doing this for every team would be a hastle, so let's just copy NPCs.

7. If you run `/npc list`, you'll get a list of all existing NPCs and their ID's. Select the one you want to copy with `/npc select <id>`, go to the location you want to paste the NPC, and then run `/npc copy`. If you need to remove any NPC, select the one you want to remove with that select command and run `/npc remove`.

If you don't want to use NPCs, you can also allow players to run the commands `/itemshop` and `/teamshop`, but they'll need the permission `wedbars.itemshop` to do that.

## Loading an arena and starting a game
Every time you start up your server or want to load data for a different arena, use `/load <name>` *(wedbars.admin)* (with the name of an existing arena). If you don't remember which arenas you've made, you can just run `/load` *(wedbars.admin)* and it'll list the available arenas.

As soon as an arena is loaded, the game auto start system is initiated. All online players will be teleported to the arena lobby and the team selection menu will automatically open. Any player that joins now (as well as players that close out of that menu) can use `/team` to open the menu. Once all players have selected a team (and there are at least 2 teams with players), auto start will start counting down. You can use `/end` *(wedbars.admin)* to cancel the countdown and assign teams or start the game manually if you'd like, otherwise the game will start.

## Assigning teams manually
After loading the arena, you can assign teams to players using `/team <team> <player>` *(wedbars.admin)* (with the team color and player name). All online players will see the assignments as they are made. Use `/team show` *(wedbars.admin)* to broadcast all of the current team assignments to the server.

## Auto assign teams manually
Run `/autoteam <teamOption1> <teamOption2> [<teamOption3>...]` *(wedbars.admin)* to automatically split up the server evenly into teams (the 'teamOption' arguments are the team colors that should be included in the assignment).

## Start the game manually
Run `/start` *(wedbars.admin)* to start the game.

## Player Statistics
If you checked out the config, you'll notice there's a 'mysql' section. This is for Wed Bars's [MySQL](https://www.mysql.com/)-enabled player statistics system (disabled by default). By changing the `mysql > enabled` value to `true` in the config and filling out the other fields with your database information, you'll be able to have player stats save. You'll need a new table in your database called `wb_stats` with a `uuid` column and a column for all the statistics shown [here](https://github.com/dilanx/WedBars/blob/main/src/com/blockhead7360/mc/wedbars/player/Statistic.java) (the columns will need to have the exact same names as the ones shown there except in lower case).

## Contribution
Wanna add something? Feel free to contact us or just fork this repo and submit a pull request with your new feature.