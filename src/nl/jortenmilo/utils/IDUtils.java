package nl.jortenmilo.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class IDUtils {
	
	private List<UUID> uuids = new ArrayList<UUID>();
	
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

	public String getData() {
		return "[UUIDS:" + Arrays.toString(uuids.toArray()) + "]";
	}

	public List<UUID> getUUIDs() {
		return uuids;
	}

	public void setUUDIs(List<UUID> uuids) {
		this.uuids = uuids;
	}
	
}
