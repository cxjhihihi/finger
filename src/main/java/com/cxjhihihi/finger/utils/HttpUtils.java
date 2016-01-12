package com.cxjhihihi.finger.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUtils {

    private static Log log = LogFactory.getLog(HttpUtils.class);

    private static int DEFALUT_TIME_OUT = 10000;

    public static String AUTO_SUBMIT_JAVA_SCRIPT = "<script>\n" + "<!--\n"
        + "document.forms[0].submit();\n" + "//-->\n" + "</script>\n";

    private static String LOCAL_ENCODING = new InputStreamReader(System.in)
        .getEncoding();

    public static String formatPlain(Map properties, String encoding) {
        if (encoding == null) {
            encoding = LOCAL_ENCODING;
        }
        StringBuffer sb = new StringBuffer(4096);
        Iterator iKey = properties.keySet().iterator();
        try {
            while (iKey.hasNext()) {
                String key = (String) iKey.next();
                String value = (String) properties.get(key);
                sb.append(URLEncoder.encode(key, encoding));
                sb.append("=");
                sb.append(URLEncoder.encode(value, encoding));
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return null;
        }
        return sb.toString();
    }

    public static String formatForm(Map properties) {
        StringBuffer sb = new StringBuffer(4096);
        Iterator iKey = properties.keySet().iterator();
        while (iKey.hasNext()) {
            String key = (String) iKey.next();
            String value = (String) properties.get(key);
            sb.append("<input type='hidden' name='");
            sb.append(key);
            sb.append("' value='");
            sb.append(value);
            sb.append("'>\n");
        }
        return sb.toString();
    }

    public static String formatUrl(String url, Map properties, String encoding) {
        return url + "?" + HttpUtils.formatUrl(properties, encoding);
    }

    public static String formatUrl(Map properties, String encoding) {
        if (encoding == null) {
            encoding = LOCAL_ENCODING;
        }
        StringBuffer sb = new StringBuffer(4096);
        Iterator iKey = properties.keySet().iterator();
        try {
            while (iKey.hasNext()) {
                String key = (String) iKey.next();
                String value = null;
                if (properties.get(key) != null) {
                    value = (String) properties.get(key);
                } else {
                    value = "";
                }
                sb.append(URLEncoder.encode(key, encoding));
                sb.append("=");
                sb.append(URLEncoder.encode(value, encoding));
                sb.append("&");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return null;
        }
        return sb.toString();
    }

    public static Map parseUrl(String url, String encoding) {
        Hashtable ht = new Hashtable();
        StringTokenizer st = new StringTokenizer(url, "&");
        try {
            while (st.hasMoreTokens()) {
                String sts = st.nextToken();
                int idx = sts.indexOf("=");
                int len = sts.length();
                if (idx > 0 && idx < len) {
                    String key = URLDecoder.decode(sts.substring(0, idx),
                        encoding);
                    String value = URLDecoder.decode(
                        sts.substring(idx + 1, len), encoding);
                    ht.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return null;
        }
        return ht;
    }

    public static String sendByUrl(String urlString, Map properties,
        Cookie[] cookies, int timeOut, String encoding) throws Exception {
        return sendByUrlReal(urlString, formatUrl(properties, encoding),
            cookies, null, timeOut);
    }

    public static String sendByUrl(String urlString, Map properties,
        Cookie[] cookies) throws Exception {
        return sendByUrlReal(urlString, formatUrl(properties, null), cookies,
            null, -1);
    }

    public static String sendByUrl(String urlString, Map properties,
        int timeOut, String encoding) throws Exception {
        return sendByUrlReal(urlString, formatUrl(properties, encoding), null,
            null, timeOut);
    }

    public static String sendByUrl(String urlString, Map properties)
        throws Exception {
        return sendByUrlReal(urlString, formatUrl(properties, null), null,
            null, -1);
    }

    public static String sendByUrl(String urlString, String urlPara,
        Cookie[] cookies, int timeOut) throws Exception {
        return sendByUrlReal(urlString, urlPara, cookies, null, timeOut);
    }

    public static String sendByUrl(String urlString, String urlPara,
        Cookie[] cookies) throws Exception {
        return sendByUrlReal(urlString, urlPara, cookies, null, -1);
    }

    public static String sendByUrl(String urlString, String urlPara, int timeOut)
        throws Exception {
        return sendByUrlReal(urlString, urlPara, null, null, timeOut);
    }

    public static String sendByUrl(String urlString, String urlPara)
        throws Exception {
        return sendByUrlReal(urlString, urlPara, null, null, -1);
    }

    public static String sendByUrl(String urlString, Cookie[] cookies,
        int timeOut) throws Exception {
        return sendByUrlReal(urlString, null, cookies, null, timeOut);
    }

    public static String sendByUrl(String urlString, Cookie[] cookies)
        throws Exception {
        return sendByUrlReal(urlString, null, cookies, null, -1);
    }

    public static String sendByUrl(String urlString, int timeOut)
        throws Exception {
        return sendByUrlReal(urlString, null, null, null, timeOut);
    }

    public static String sendByUrl(String urlString) throws Exception {
        return sendByUrlReal(urlString, null, null, null, -1);
    }

    public static String sendByUrl(String urlString, Map properties,
        String responseEncoding) throws Exception {
        return sendByUrlReal(urlString, formatUrl(properties, null), null,
            responseEncoding, -1);
    }

    public static String sendByUrl(String urlString, Map properties,
        String paramEncoding, String responseEncoding) throws Exception {
        return sendByUrlReal(urlString, formatUrl(properties, paramEncoding),
            null, responseEncoding, -1);
    }

    private static String sendByUrlReal(String urlString, String urlParams,
        Cookie[] cookies, String responseEncoding, int timeOut)
        throws Exception {
        String paramsPwdProtected = urlParams;
        if (urlParams != null) {
            paramsPwdProtected = urlParams.replaceAll(
                "(^|[?&])password=([^&]*)", "$1password=***");
        }
        String info = "sendByUrlReal(" + urlString + "," + paramsPwdProtected
            + ")";
        String response = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        long begin = System.currentTimeMillis();
        try {
            URL url = new URL(urlString);
            URLConnection urlConn = url.openConnection();

            if (timeOut == -1) {
                timeOut = DEFALUT_TIME_OUT;
            }
            urlConn.setConnectTimeout(timeOut);
            urlConn.setReadTimeout(timeOut);

            if (cookies != null && cookies.length > 0) {
                StringBuffer cookiesb = new StringBuffer(1024);
                for (int i = 0, len = cookies.length; i < len; i++) {
                    cookiesb.append(cookies[i].getName()).append("=")
                        .append(cookies[i].getValue());
                    if (i != len - 1) {
                        cookiesb.append("; ");
                    }
                }
                log.debug("Cookie is:" + cookiesb.toString());
                urlConn.setRequestProperty("Cookie", cookiesb.toString());
            }

            if (urlParams != null && urlParams.length() > 0) {
                urlConn.setDoOutput(true);
                out = new BufferedWriter(new OutputStreamWriter(
                    urlConn.getOutputStream()));
                out.write(urlParams);
                out.close();
            }
            if (StringUtils.isNotBlank(responseEncoding)) {
                in = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream(), responseEncoding));
            } else {
                in = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream()));
            }

            String line = null;
            StringBuffer buffer = new StringBuffer(4096);
            while ((line = in.readLine()) != null)
                buffer.append(line + "\n");
            response = buffer.toString();
        } catch (Exception e) {
            info += "|" + e;
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }
            }
            info += "|" + response;
            log.debug(info + "|" + (System.currentTimeMillis() - begin));
        }
        return response;
    }

    public static void forwardRequest(String url, ServletRequest request,
        ServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    public static String getRequestInfo(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * behind the squid server the request.getRemoteAddr returns ip of squid
     * server the real ip was passed by http header X-Forwarded-For this
     * function using to extract real ip from request
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip;
        String rip = request.getRemoteAddr();
        String xff = request.getHeader("X-Forwarded-For");
        if ((xff != null && xff.length() != 0)
            && (rip.startsWith("192.168.") || rip.startsWith("10.120."))) {
            int px = xff.indexOf(',');
            if (px != -1) {
                ip = xff.substring(0, px);
            } else {
                ip = xff;
            }
        } else {
            ip = rip;
        }
        return ip.trim();
    }

    /**
     * ��ȡ����IP
     */
    public static String getLocalAddr() {
        String ip = "";
        InetAddress inetAddress = null;
        try {
            if (isWindowsOS()) {
                ip = InetAddress.getLocalHost().getHostAddress();
            } else {
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface
                    .getNetworkInterfaces();
                while (netInterfaces.hasMoreElements()) {
                    NetworkInterface ni = netInterfaces.nextElement();
                    Enumeration<InetAddress> ips = ni.getInetAddresses();//��������ip
                    while (ips.hasMoreElements()) {
                        inetAddress = ips.nextElement();
                        if (inetAddress.isSiteLocalAddress()
                            && !inetAddress.isLoopbackAddress()
                            && inetAddress.getHostAddress().indexOf(":") == -1) {//127.��ͷ�Ķ���lookback��ַ
                            ip = inetAddress.getHostAddress();
                            break;
                        }
                    }
                }
            }
        } catch (UnknownHostException e) {
            log.error("getLocalAddr UnknownHostException", e);
        } catch (SocketException e2) {
            log.error("getLocalAddr SocketException", e2);
        }
        return ip;
    }

    /**
     * �жϵ�ǰ�����Ƿ�Windows.
     * 
     * @return true---��Windows����ϵͳ
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    public static void responseNoCache(String html, String ContentType,
        HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(ContentType);
        PrintWriter out = response.getWriter();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setDateHeader("Expires", 0);
        out.print(html);
    }

    /**
     * Converts a properties list to a URL-encoded query string
     */
    public static String toEncodedString(Properties args, String encoding) {
        if (encoding == null) {
            encoding = LOCAL_ENCODING;
        }
        try {
            StringBuffer buf = new StringBuffer();
            Enumeration names = args.propertyNames();
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = args.getProperty(name);
                buf.append(URLEncoder.encode(name, encoding)).append("=")
                    .append(URLEncoder.encode(value, encoding));
                if (names.hasMoreElements()) {
                    buf.append("&");
                }
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String[] cases = new String[] {
            "http://reg.163.com/services/userlogin?username=wzq_test@163.com&type=0&passtype=0&userip=123.58.177.235&product=mail163&password=46f94c8de14fb36680850768ff1b7f2a",
            "&password=46f94c8de14fb36680850768ff1b7f2a",
            "?password=46f94c8de14fb36680850768ff1b7f2a",
            "?password=46f94c8de14fb36680850768ff1b7f2a&",
            "password=46f94c8de14fb36680850768ff1b7f2a&" };
        for (String c: cases) {
            System.out.println(c.replaceAll("(^|[?&])password=([^&]*)",
                "$1password=***"));
        }
        System.out.println("a" + null);
    }

}
