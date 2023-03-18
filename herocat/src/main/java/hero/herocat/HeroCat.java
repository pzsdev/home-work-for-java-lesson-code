package hero.herocat;

/**
 * TODO
 *
 * @author pengzhisheng
 * @since 2023/3/18
 **/
public class HeroCat {
    public static void run(String[] args) throws Exception {
        CustomServer server = new CustomServer("com.hero.webapp");
        server.start();
    }
}
