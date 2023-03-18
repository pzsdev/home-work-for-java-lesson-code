package hero.herocat;

import hero.servlet.HeroRequest;
import hero.servlet.HeroResponse;
import hero.servlet.HeroServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author pengzhisheng
 * @since 2023/3/16
 **/
public class CustomHandler extends ChannelInboundHandlerAdapter {
    private Map<String, HeroServlet> nameToServletMap;
    private Map<String, String> nameToClassNameMap;

    public CustomHandler(Map<String, HeroServlet> nameToServletMap, Map<String, String> nameToClassNameMap) {
        this.nameToServletMap = nameToServletMap;
        this.nameToClassNameMap = nameToClassNameMap;
        System.out.println("init one connect...");
        System.out.println("the nameToServletMap keys is " + String.join("", nameToServletMap.keySet()));
        System.out.println("the nameToClassNameMap keys is " + String.join("", nameToClassNameMap.keySet()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String uri = request.uri();

            System.out.println("the uri is " + uri);

            // 从请求中解析出要访问的Servlet名称
            //aaa/bbb/twoservlet?name=aa
            String servletName = "";
//            if (uri.contains("?") && uri.contains("/")) {
            if (uri.contains("/")) {
                if (uri.contains("?")) {
                    servletName = uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf("?")).toLowerCase();
                } else {
                    servletName = uri.substring(uri.lastIndexOf("/") + 1).toLowerCase();
                }
            }
            System.out.println("the servletName is " + servletName);

            HeroServlet servlet = new DefaultCustomServlet();
            //第一次访问，Servlet是不会被加载的
            //初始化加载的只是类全限定名称，懒加载
            //如果访问Servlet才会去初始化它对象
            if (nameToServletMap.containsKey(servletName)) {
                servlet = nameToServletMap.get(servletName);
            } else if (nameToClassNameMap.containsKey(servletName)) {
                // double-check，双重检测锁：为什么要在锁前判断一次，还要在锁后继续判断一次？
                if (nameToServletMap.get(servletName) == null) {
                    synchronized (this) {
                        if (nameToServletMap.get(servletName) == null) {
                            // 获取当前Servlet的全限定性类名
                            String className = nameToClassNameMap.get(servletName);
                            // 使用反射机制创建Servlet实例
                            servlet = (HeroServlet) Class.forName(className).newInstance();
                            // 将Servlet实例写入到nameToServletMap
                            nameToServletMap.put(servletName, servlet);
                        }
                    }
                }
            } //  end-else if

            // 代码走到这里，servlet肯定不空
            HeroRequest req = new HttpCustomRequest(request);
            HeroResponse res = new HttpCustomResponse(request, ctx);
            // 根据不同的请求类型，调用servlet实例的不同方法
            if (request.method().name().equalsIgnoreCase("GET")) {
                servlet.doGet(req, res);
            } else if (request.method().name().equalsIgnoreCase("POST")) {
                servlet.doPost(req, res);
            }
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
