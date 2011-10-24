package org.dresign.bus.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("redisConsumer")
public class RedisConsumer implements Consumer,InitializingBean  {
	@Override
	public void afterPropertiesSet() throws Exception {
		if(host==null){
			host="localhost";
		}
		if(channel==null){
			channel="domainAsyncQueue";
		}
		client = new Jedis(host);
		pchannel=channel.getBytes();
	}

	Jedis client;

	@Autowired
	DeSerializer deserializer;
	
	@Autowired
	TaskExecutor executor;
	
	String channel;
	byte pchannel[];
	private String host;
	
	 
	@Override
	public void handleEvent(){
		byte[] res = client.rpop(pchannel);
		if(res!=null){
			Dispatcher dispatcher = (Dispatcher) deserializer.deserialize(res);
			executor.doDispatch(dispatcher);
		}
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}


}
