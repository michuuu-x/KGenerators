package me.kryniowesegryderiusz.kgenerators;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.kryniowesegryderiusz.kgenerators.generators.generator.objects.Generator;
import me.kryniowesegryderiusz.kgenerators.generators.locations.objects.GeneratorLocation;
import me.kryniowesegryderiusz.kgenerators.lang.Lang;

public class CommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		List<String> c = new ArrayList<String>();

		/* spawn <world> <x> <y> <z> <generator> [owner] */
		if (args.length >= 3 && args[0].equals("spawn") && !args[1].equals("worldedit")) {
			switch (args.length) {
			case 3:
				return this.getCoordinate(sender, 'x');
			case 4:
				return this.getCoordinate(sender, 'y');
			case 5:
				return this.getCoordinate(sender, 'z');
			case 6:
				return this.getGenerators(args[5]);
			case 7:
				return this.getPlayers(args[6]);
			}
		}

		if (args.length == 4 && args[0].equals("spawn") && args[1].equals("worldedit")) {
			return this.getPlayers(args[3]);
		}

		if (args.length == 3) {
			if (args[0].equals("give")) {
				return this.getGenerators(args[2]);
			}

			if (args[0].equals("remove") && args[1].equals("owner")) {
				return this.getOwners(args[2]);
			}

			if (args[1].equals("owner")) {
				return this.getPlayers(args[2]);
			}
			
			if (args[0].equals("spawn") && args[1].equals("worldedit")) {
				return this.getGenerators(args[2]);
			}
		}

		if (args.length == 2) {
			if (args[0].equals("give")) {
				return this.getPlayers(args[1]);
			} else if (args[0].equals("spawn")) {
				c.add("worldedit");
				for (World w : Main.getInstance().getServer().getWorlds()) {
					c.add(w.getName());
				}
			} else if (args[0].equals("remove")) {
				c.add("worldedit");
				c.add("owner");
			}
		}

		if (args.length == 1) {
			ArrayList<String> help = Lang.getMessageStorage().getCommands(sender);
			if (isBlank(args[0]))
				c = help;
			else
				for (String s : help) {
					if (s.contains(args[0]))
						c.add(s);
				}
			return c;
		}

		return c;
	}

	ArrayList<String> getPlayers(String arg) {
		ArrayList<String> players = new ArrayList<String>();
		if (isBlank(arg)) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				players.add(p.getName());
			}
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getName().contains(arg))
					players.add(p.getName());
			}
		}
		return players;
	}

	/**
	 * Suggests coordinate of block the player is looking at (or the one he stands
	 * on, if he is not looking at any block).
	 *
	 * @param axis x, y or z
	 */
	List<String> getCoordinate(CommandSender sender, char axis) {
		List<String> result = new ArrayList<String>();
		if (!(sender instanceof Player))
			return result;

		Player p = (Player) sender;
		Block block = p.getTargetBlockExact(10);
		Location l = block != null ? block.getLocation() : p.getLocation();

		switch (axis) {
		case 'x':
			result.add(String.valueOf(l.getBlockX()));
			break;
		case 'y':
			result.add(String.valueOf(l.getBlockY()));
			break;
		case 'z':
			result.add(String.valueOf(l.getBlockZ()));
			break;
		}
		return result;
	}

	/**
	 * Online players and owners of currently loaded generators, so owners that are
	 * offline can be completed too.
	 */
	List<String> getOwners(String arg) {
		Set<String> owners = new LinkedHashSet<String>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			owners.add(p.getName());
		}
		for (GeneratorLocation gl : Main.getPlacedGenerators().getAll()) {
			if (gl.getOwner() != null && gl.getOwner().getName() != null)
				owners.add(gl.getOwner().getName());
		}

		List<String> result = new ArrayList<String>();
		for (String owner : owners) {
			if (isBlank(arg) || owner.toLowerCase().contains(arg.toLowerCase()))
				result.add(owner);
		}
		return result;
	}

	ArrayList<String> getGenerators(String arg) {
		ArrayList<String> generators = new ArrayList<String>();
		if (isBlank(arg)) {
			for (Generator g : Main.getGenerators().getAll()) {
				generators.add(g.getId());
			}
		} else {
			for (Generator g : Main.getGenerators().getAll()) {
				if (g.getId().contains(arg))
					generators.add(g.getId());
			}
		}
		return generators;
	}

	boolean isBlank(String s) {
		if (s.isEmpty() || s.equals(" "))
			return true;
		return false;
	}
}
