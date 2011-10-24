package org.dresign.bus.redis;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;


import com.thoughtworks.xstream.XStream;

@Service
public class XStreamSerializer implements Serializer,DeSerializer {

	XStream xstream = new XStream();
	@Override
	public byte[] serialize(Object objet) {
		return xstream.toXML(objet).getBytes();
	}

	@Override
	public Object deserialize(byte[] stream) {
		return xstream.fromXML(new ByteArrayInputStream(stream));
	}
}
