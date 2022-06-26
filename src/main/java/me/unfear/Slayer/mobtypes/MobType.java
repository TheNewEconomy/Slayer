package me.unfear.Slayer.mobtypes;

import org.bukkit.Material;
import org.bukkit.entity.Entity;

public abstract class MobType {
	
	private int id;
	private String name;
	private Material material;
	public MobType(int id, String name, Material material) {
		this.id = id;
		this.name = name;
		this.material = material;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public abstract boolean isThis(Entity entity);
}
