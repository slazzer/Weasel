package org.dresign.bus.redis;

public interface DeSerializer {

	Object deserialize(byte[] stream);
}
