package cn.brody.financing.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * HttpUtil
 *
 * @author chenyifu6
 * @since 2024/09/13 15:19
 */
@Slf4j
public class HttpUtils extends HttpUtil {

    /**
     * 获取指定URL的响应内容。
     *
     * @param urlString  需要获取内容的URL地址。
     * @param httpConfig HTTP配置参数，包括超时时间、代理设置等。
     * @return 如果请求成功，返回URL的响应内容；否则抛出异常。
     */
    public static String get(String urlString, HttpConfig httpConfig) {
        return get(urlString, null, null, httpConfig);
    }

    /**
     * 发送带有请求头的HTTP GET请求。
     *
     * @param urlString 需要访问的URL地址。
     * @param headers   需要添加的请求头信息，以键值对的形式存储在Map中。
     * @param param     需要添加的请求参数，以键值对的形式存储在Map中。
     * @return 返回服务器响应的内容，如果发生错误则抛出异常。
     */
    public static String get(String urlString, Map<String, String> headers, Map<String, Object> param) {
        return get(urlString, headers, param, null);
    }

    /**
     * 发送GET请求。
     *
     * @param urlString  需要访问的URL地址。
     * @param headers    需要设置的HTTP头信息，以键值对的形式存储。
     * @param param      需要传递的参数，以键值对的形式存储。
     * @param httpConfig HTTP配置信息，包括连接超时、读取超时等。
     * @return 如果请求成功，返回服务器响应的内容；否则抛出异常。
     */
    public static String get(String urlString, Map<String, String> headers, Map<String, Object> param, HttpConfig httpConfig) {
        HttpRequest request = HttpRequest.get(urlString);
        if (MapUtil.isNotEmpty(headers)) {
            headers.forEach(request::header);
        }
        if (MapUtil.isNotEmpty(param)) {
            request.form(param);
        }
        if (null != httpConfig) {
            request.setConfig(httpConfig);
        }
        return exec(request);
    }

    /**
     * 发送HTTP POST请求。
     *
     * @param urlString 需要访问的URL地址。
     * @return 返回服务器响应的内容，如果发生错误则抛出异常。
     */
    public static String post(String urlString) {
        return post(urlString, null, null, null);
    }

    /**
     * 发送POST请求。
     *
     * @param urlString  需要访问的URL地址。
     * @param httpConfig HTTP配置参数，包括超时时间、代理设置等。
     * @return 如果请求成功，返回服务器响应的内容；否则抛出异常。
     */
    public static String post(String urlString, HttpConfig httpConfig) {
        return post(urlString, null, null, httpConfig);
    }

    /**
     * 发送post请求
     *
     * @param urlString 网址
     * @param paramMap  post表单数据
     * @return 返回数据
     */
    public static String post(String urlString, Map<String, Object> paramMap) {
        return post(urlString, null, paramMap, null);
    }


    /**
     * 发送POST请求并返回响应头。
     *
     * @param urlString  需要访问的URL地址。
     * @param param      需要发送的参数
     * @param httpConfig HTTP配置信息，包括超时时间、代理设置等。
     * @return 如果请求成功，返回响应头的Map集合；否则抛出异常。
     */
    public static Map<String, List<String>> postReturnHeader(String urlString, JSONObject param, HttpConfig httpConfig) {
        HttpRequest request = HttpRequest.post(urlString);
        if (null != httpConfig) {
            request.setConfig(httpConfig);
        }
        if (null != param) {
            request.body(param.toJSONString());
        }
        try (HttpResponse response = request.execute()) {
            int code = response.getStatus();
            boolean successful = response.isOk();
            Map<String, List<String>> responseHeaders = response.headers();
            log.debug("[{}]请求[{}]，响应状态码：{}，响应头：{}", request.getUrl(), successful ? "成功" : "失败", code, responseHeaders);
            return responseHeaders;
        } catch (Exception e) {
            log.error("发送请求失败,url[{}]", request.getUrl(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送POST请求。
     *
     * @param urlString 需要访问的URL地址。
     * @param headers   请求头信息，以键值对的形式存储。
     * @param paramMap  请求参数，以键值对的形式存储。
     * @return 如果请求成功，返回服务器响应的内容；否则抛出异常。
     */
    public static String post(String urlString, Map<String, String> headers, Map<String, Object> paramMap) {
        return post(urlString, headers, paramMap, null);
    }

    /**
     * 发送带有请求头的HTTP POST请求。
     *
     * @param urlString 需要访问的URL地址。
     * @param headers   需要添加的请求头信息，以键值对的形式存储在Map中。
     * @param paramMap  需要添加的请求参数，以键值对的形式存储在Map中。
     * @return 返回服务器响应的内容，如果发生错误则抛出异常。
     */
    public static String post(String urlString, Map<String, String> headers, Map<String, Object> paramMap, HttpConfig httpConfig) {
        HttpRequest request = HttpRequest.post(urlString);
        if (null != httpConfig) {
            request.setConfig(httpConfig);
        }
        if (MapUtil.isNotEmpty(headers)) {
            headers.forEach(request::header);
        }
        if (MapUtil.isNotEmpty(paramMap)) {
            request.form(paramMap);
        }
        return exec(request);
    }


    /**
     * 下载文件。
     *
     * @param url           需要下载的文件的URL地址。
     * @param destFileOrDir 目标文件或目录，如果为目录，则将下载的文件保存在此目录下。
     * @param headers       请求头信息，可以为空。
     */
    public static void downloadFile(String url, File destFileOrDir, Map<String, String> headers) {
        HttpResponse httpResponse = getDownloadRequest(url, headers, null);
        httpResponse.writeBody(destFileOrDir);
    }

    /**
     * 下载远程文件
     *
     * @param url 请求的url
     * @param out 将下载内容写到输出流中 {@link OutputStream}
     */
    public static void downloadFile(String url, OutputStream out, Map<String, String> headers) {
        HttpResponse httpResponse = getDownloadRequest(url, headers, null);
        httpResponse.writeBody(out, Boolean.TRUE, null);
    }

    /**
     * 下载远程文件
     *
     * @param url 请求的url
     * @param out 将下载内容写到输出流中 {@link OutputStream}
     */
    public static void downloadFile(String url, OutputStream out, Map<String, String> headers, HttpConfig httpConfig) {
        HttpResponse httpResponse = getDownloadRequest(url, headers, httpConfig);
        httpResponse.writeBody(out, Boolean.TRUE, null);
    }

    /**
     * 发起下载请求。
     *
     * @param url        需要下载的url地址。
     * @param headers    请求头信息，以键值对的形式存在Map中。
     * @param httpConfig Http配置参数，包括连接超时、读取超时等。
     * @return 如果响应正常，返回HttpResponse对象；否则抛出AuthException异常。
     */
    private static HttpResponse getDownloadRequest(String url, Map<String, String> headers, HttpConfig httpConfig) {
        HttpRequest request = HttpUtil.createGet(url, true);
        if (MapUtil.isNotEmpty(headers)) {
            headers.forEach(request::header);
        }
        if (null != httpConfig) {
            request.setConfig(httpConfig);
        }
        HttpResponse httpResponse = request.executeAsync();
        if (!httpResponse.isOk()) {
            log.error("下载文件失败，响应：{}", httpResponse);
            throw new RuntimeException("下载文件失败");
        }
        return httpResponse;
    }

    /**
     * 发送PUT请求。
     *
     * @param url      需要访问的url地址。
     * @param headers  请求头信息，键值对形式存储。
     * @param paramMap 请求参数，键值对形式存储。
     */
    public static String put(String url, Map<String, String> headers, Map<String, Object> paramMap) {
        return put(url, headers, paramMap, null);
    }


    /**
     * 发送PUT请求。
     *
     * @param url        需要访问的url地址。
     * @param headers    需要设置的请求头信息，以键值对的形式存储在Map中。
     * @param file       需要上传的文件。
     * @param httpConfig 请求的配置信息，包括超时时间、重试次数等。
     * @return 如果请求成功，返回服务器响应的结果；否则抛出异常。
     */
    public static String put(String url, Map<String, String> headers, File file, HttpConfig httpConfig) {
        HttpRequest request = HttpRequest.put(url);
        if (MapUtil.isNotEmpty(headers)) {
            headers.forEach(request::header);
        }
        if (null != httpConfig) {
            request.setConfig(httpConfig);
        }
        request.body(FileUtil.readBytes(file));
        return exec(request);
    }

    /**
     * 发送PUT请求。
     *
     * @param url        需要访问的url地址。
     * @param headers    请求头信息，以键值对的形式存在Map中。
     * @param paramMap   请求参数，以键值对的形式存在Map中。
     * @param httpConfig Http配置信息，包括连接超时、读取超时等。
     */
    public static String put(String url, Map<String, String> headers, Map<String, Object> paramMap, HttpConfig httpConfig) {
        HttpRequest request = HttpRequest.put(url);
        if (MapUtil.isNotEmpty(headers)) {
            headers.forEach(request::header);
        }
        if (null != httpConfig) {
            request.setConfig(httpConfig);
        }
        request.form(paramMap);
        return exec(request);
    }

    /**
     * 执行HTTP请求并获取响应。
     *
     * @param request 需要执行的HTTP请求。
     * @return 如果请求成功，返回响应体；否则抛出运行时异常。
     */
    private static String exec(HttpRequest request) {
        String body;
        try (HttpResponse response = request.execute()) {
            int code = response.getStatus();
            boolean successful = response.isOk();
            if (!successful) {
                throw new RuntimeException("响应状态码：" + code);
            }
            body = response.body();
            Map<String, List<String>> responseHeaders = response.headers();
            log.debug("[{}]请求成功，响应状态码：{}，响应头：{}，结果：{}", request.getUrl(), code, responseHeaders, body);
        } catch (Exception e) {
            log.error("发送请求失败,url[{}]", request.getUrl(), e);
            throw new RuntimeException(e);
        }
        return body;
    }
}
