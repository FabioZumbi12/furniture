package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

public class table extends Furniture implements Listener {
	
	public table(Plugin plugin, ObjectID id){
		super(plugin, id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(id.getStartLocation());
	}
	public void spawn(Location loc){
		List<fArmorStand> packetL = new ArrayList<fArmorStand>();
		Location middle1 = getLutil().getCenter(loc);
		Location middle2 = getLutil().getCenter(loc);
		Location l = loc;
		l.setYaw(0);
		
		fArmorStand asp = getManager().createArmorStand(getObjID(), middle1.add(0, -2.1, 0));
		asp.getInventory().setHelmet(new ItemStack(Material.WOOD_PLATE));
		packetL.add(asp);
		asp = getManager().createArmorStand(getObjID(), middle2.add(0,-1.05,0));
		asp.getInventory().setHelmet(new ItemStack(Material.TRAP_DOOR));
		packetL.add(asp);
		Location locatio = l.clone().add(0.9,0.15,0.3);
		asp = getManager().createArmorStand(getObjID(), locatio);
		asp.setName("#ITEM#");
		asp.setPose(new EulerAngle(0.0,0.0,0.0), BodyPart.RIGHT_ARM);
		packetL.add(asp);
		locatio = locatio.clone().add(0,-0.65,0.68);
		asp = getManager().createArmorStand(getObjID(), locatio);
		asp.getInventory().setItemInHand(new ItemStack(Material.STICK));
		asp.setPose(new EulerAngle(1.38,.0,.0), BodyPart.RIGHT_ARM);
		packetL.add(asp);
		
		for(fArmorStand packet : packetL){
			packet.setInvisible(true);
			packet.setGravity(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase("#ITEM#")){
				if(packet.getInventory().getItemInHand()!=null&&!packet.getInventory().getItemInHand().getType().equals(Material.AIR)){
					ItemStack is = packet.getInventory().getItemInHand();
					getWorld().dropItem(getLocation(), is);
				}
			}
		}
		e.remove();
		delete();
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		Player p = e.getPlayer();
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		if(p.getItemInHand().getType().isBlock()&&!p.getItemInHand().getType().equals(Material.AIR)){return;}
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase("#ITEM#")){
				ItemStack Itemstack = p.getItemInHand().clone();
				Itemstack.setAmount(1);
				if(packet.getInventory().getItemInHand()!=null&&!packet.getInventory().getItemInHand().getType().equals(Material.AIR)){
					ItemStack is = packet.getInventory().getItemInHand();
					is.setAmount(1);
					getWorld().dropItem(getLocation(), is);
				}
				packet.getInventory().setItemInHand(Itemstack);
				if(p.getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) break;
				Integer i = p.getInventory().getHeldItemSlot();
				ItemStack is = p.getItemInHand();
				is.setAmount(is.getAmount()-1);
				p.getInventory().setItem(i, is);
				p.updateInventory();
				break;
			}
		}
		update();
	}
}
