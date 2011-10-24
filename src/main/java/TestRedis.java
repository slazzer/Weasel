import redis.clients.jedis.Jedis;


public class TestRedis {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestRedis test= new TestRedis();
		test.run();
	}

	public void run(){
		Jedis jedis = new Jedis("localhost");
	//	jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
	}
}
