package nl.jortenmilo.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class IDUtils extends Utils {
	
	private List<UUID> uuids;
	
	@Override
	public void create() {
		uuids = new ArrayList<UUID>();
	}

	@Override
	public Utils clone() {
		IDUtils clone = new IDUtils();
		clone.create();
		clone.setUUDIs(uuids);
		
		return clone;
	}
	
	public UUID getRandomUUID() {
		UUID uuid = UUID.randomUUID();
		
		if(uuids.contains(uuid)) {
			return getRandomUUID();
		}
		
		uuids.add(uuid);
		return uuid;
	}
	
	public String UUIDtoString(UUID uuid) {
		return uuid.toString();
	}
	
	public UUID StringtoUUID(String uuid) {
		return UUID.fromString(uuid);
	}

	public List<UUID> getUUIDs() {
		return uuids;
	}

	protected void setUUDIs(List<UUID> uuids) {
		this.uuids = uuids;
	}
	
	@Override
	public String getData() {
		return "[UUIDS: " + Arrays.toString(uuids.toArray()) + "]";
	}

	@Override
	public String getName() {
		return "IDUtils";
	}
	
}
