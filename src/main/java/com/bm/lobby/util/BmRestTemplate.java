package com.bm.lobby.util;

import com.bm.lobby.dto.base.ServiceException;
import com.bm.lobby.enums.RespSystemCode;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/10/30 17:21
 */
public class BmRestTemplate extends RestTemplate {

    private static final Logger log = LoggerFactory.getLogger(BmRestTemplate.class);

    public BmRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    /**
     *  GET访问（设置请求头）
     * @param url
     * @param headers 请求头
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T getForObjectWithHeader(String url , HttpHeaders headers, Class<T> responseType, Object... uriVariables)  {
        ResponseEntity<T> entity = getForEntityWithHeader(url,headers,responseType,uriVariables);
        return getBodyFromResponseEntity(entity);
    }

    public <T> ResponseEntity<T> getForEntityWithHeader(String url , HttpHeaders headers, Class<T> responseType, Object... uriVariables)  {
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        try {
            log.info("=======>httpExchange访问,url：{},httpEntity:{},url参数：{}",url,requestEntity,uriVariables);
            ResponseEntity<T> entity = super.exchange(url, HttpMethod.GET,requestEntity,responseType,uriVariables);
            log.info("=======>httpExchange访问,url：{},响应结果：{}",url,GsonUtils.fromObject2Json(entity));
            return entity;
        } catch (Exception e) {
            processAccessException(url,requestEntity,e);
            return null;
        }
    }

    /**
     * GET访问（设置请求头）
     * @param url
     * @param headers 请求头
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T getForObjectWithHeader(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> uriVariables) {
        ResponseEntity<T> entity = getForEntityWithHeader(url,headers,responseType,uriVariables);
        return getBodyFromResponseEntity(entity);
    }

    public <T> ResponseEntity<T> getForEntityWithHeader(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> uriVariables) {
        try {
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            log.info("=======>httpExchange访问,url：{},httpEntity:{},url参数：{}",url,requestEntity,uriVariables);
            ResponseEntity<T> entity;
            if (uriVariables != null){
                entity = super.exchange(url,HttpMethod.GET,requestEntity,responseType,uriVariables);
            }else{
                entity = super.exchange(url,HttpMethod.GET,requestEntity,responseType);
            }
            log.info("=======>httpExchange访问,url：{},响应结果：{}",url,GsonUtils.fromObject2Json(entity));
            return entity;
        }catch (Exception e) {
            processAccessException(url,uriVariables,e);
            return null;
        }
    }


    /**
     *  GET访问
     * @param url
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables)  {
        ResponseEntity<T> entity = getForEntity(url,responseType,uriVariables);
        return getBodyFromResponseEntity(entity);
    }

    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables)  {
        try {
            log.info("=====>httpGet访问,url：{},url参数:{}",url,uriVariables);
            ResponseEntity<T> entity = super.getForEntity(url,responseType,uriVariables);
            log.info("=====>httpGet访问,url：{},响应结果：{}",url,GsonUtils.fromObject2Json(entity));
            return entity;
        } catch (Exception e) {
            processAccessException(url,uriVariables,e);
            return null;
        }
    }

    /**
     * GET访问
     * @param url
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables)  {
        ResponseEntity<T> entity = getForEntity(url,responseType,uriVariables);
        return getBodyFromResponseEntity(entity);
    }

    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables)  {
        try {
            log.info("======>httpGet访问,url：{},url参数:{}",url,uriVariables);
            ResponseEntity<T> entity = super.getForEntity(url,responseType,uriVariables);
            log.info("======>httpGet访问,url：{},响应结果：{}",url,GsonUtils.fromObject2Json(entity));
            return entity;
        } catch (Exception e) {
            processAccessException(url,uriVariables,e);
            return null;
        }
    }

    /**
     * POST访问
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) {
        ResponseEntity<T> entity = postForEntity(url,request,responseType,uriVariables);
        return getBodyFromResponseEntity(entity);
    }

    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) {
        try {
            log.info("=====>httpPost访问,url：{},请求参数:{},url参数:{}",url,GsonUtils.fromObject2Json(request),uriVariables);
            ResponseEntity<T> entity = super.postForEntity(url,request,responseType,uriVariables);
            log.info("=====>httpPost访问,url：{},响应结果：{}",url,GsonUtils.fromObject2Json(entity));
            return entity;
        }catch (ServiceException se){
            throw se;
        } catch (Exception e) {
            processAccessException(url,request,e);
            return null;
        }
    }

    /**
     * POST访问
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
        ResponseEntity<T> entity = postForEntity(url,request,responseType,uriVariables);
        return getBodyFromResponseEntity(entity);
    }

    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
        try {
            log.info("======>httpPost访问,url：{},请求参数:{},url参数:{}",url,GsonUtils.fromObject2Json(request),uriVariables);
            ResponseEntity<T> entity = super.postForEntity(url,request,responseType,uriVariables);
            log.info("======>httpPost访问,url：{},响应结果：{}",url,GsonUtils.fromObject2Json(entity));
            return entity;
        }catch (Exception e) {
            processAccessException(url,request,e);
            return null;
        }
    }


    /**
     * 处理访问异常结果
     * @param url
     * @param request
     * @param e
     */
    private void processAccessException(String url,Object request,Exception e){
        Throwables.propagateIfInstanceOf(e,ServiceException.class);
        processHttpException(e,url);
    }


    /**
     * 处理RestTemplate访问的异常
     * @param e
     * @throws ServiceException
     */
    private void processHttpException(Exception e,String url) throws ServiceException{
        if (e instanceof ResourceAccessException){
            if(e.getCause() instanceof SocketTimeoutException){
                log.warn("<=====PalmRestTemplate访问读取超时,url:{},e:{}",url,e.getMessage());
                throw new ServiceException(RespSystemCode.SOCKET_READ_TIMEOUT);
            }
            log.warn("<=====PalmRestTemplate访问url异常,url:"+url,e);
        }
        throw new ServiceException(RespSystemCode.ACCESS_FAIL);
    }

    private <T> T getBodyFromResponseEntity(ResponseEntity<T> entity){
        if (entity.getStatusCode() == HttpStatus.OK){
            return entity.getBody();
        }
        throw new ServiceException(RespSystemCode.ACCESS_FAIL);
    }
}
