package abc.ney.armee.enginee.net.http;

/**
 * Http请求客户端接口
 * @author neyzoter
 */
public interface HttpClientIf {
    /**
     * 通过POST发送数据
     * @param httpUrl http请求的url
     * @param auth authorization
     * @param param 要发送的数据
     * @return 请求结果
     */
    String doPost(String httpUrl, String auth, String param);

    /**
     * 通过GET请求数据
     * @param httpUrl http请求的url
     * @return 请求得到的结果
     */
    String doGet(String httpUrl);
}
