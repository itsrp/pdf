import org.apache.http.client.utils.URIBuilder;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class URIBuilder01 {
    public static void main(String[] args) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("www.google.com");
        builder.setPath("/myPage/home");
        builder.addParameter("param1", "value1");
        builder.addParameter("param2", "value2");
        String urlString = builder.build().toURL().toString();
        String encode = URLEncoder.encode(urlString, "UTF-8");
        System.out.println(urlString);
        System.out.println(encode);


    }
}
