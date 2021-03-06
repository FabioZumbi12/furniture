package de.Ste3et_C0st.Furniture.Objects.electric;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class billboard extends Furniture implements Listener{
	public billboard(Plugin plugin, ObjectID id){
		super(plugin, id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(id.getStartLocation());
	}

	public void spawn(Location location){
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		Location center = getLutil().getCenter(location).add(0, -1.2, 0);
		Location center2 = getLutil().getRelativ(center, getBlockFace(), 0D, -4D);
		Location center3 = getLutil().getRelativ(center, getBlockFace(), 0D, -3.3D);
		
		for(int i = 0; i<=3;i++){
			Location loc = getLutil().getRelativ(center.clone(), getBlockFace(), -.1, -.5).add(0, .88*i, 0);
			loc.setYaw(loc.getYaw()+90);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		for(int i = 0; i<=3;i++){
			Location loc = getLutil().getRelativ(center2.clone(), getBlockFace(), -.1, -.5).add(0, .88*i, 0);
			loc.setYaw(loc.getYaw()+90);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		for(int i = 0; i<=4;i++){
			Location loc = getLutil().getRelativ(center3.clone(), getBlockFace(), -.1, .88*i).add(0, .7D, 0);
			loc.setYaw(loc.getYaw()+90);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(-.17, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		for(int i = 0; i<=4;i++){
			Location loc = getLutil().getRelativ(center3.clone(), getBlockFace(), -.1, .88*i).add(0, 2.9D, 0);
			loc.setYaw(loc.getYaw()+90);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(-.17, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		for(int x = 0; x<=1;x++){
			for(int y = 0;y<=2;y++){
				Location loc = getLutil().getRelativ(location, getBlockFace(),0D,(double) -y-1).add(0, x+1, 0);
				Location loc2 = getLutil().getRelativ(location, getBlockFace(),-1D,(double) -y-1).add(0, x+1, 0);
				loc.getBlock().setType(Material.BARRIER);
				ItemFrame frame = (ItemFrame) getWorld().spawn(loc2, ItemFrame.class);
				frame.setFacingDirection(getBlockFace());
			}
		}
		
		for(fArmorStand pack : asList){
			pack.setInvisible(true);
			pack.setGravity(false);
			pack.setBasePlate(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		e.remove();
		delete();
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {}
}
