
package com.metlife.gssp.common.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.metlife.gssp.common.constant.LoggingContextHeader;
import com.metlife.gssp.common.constant.LoggingContextKey;
import com.metlife.gssp.logging.Logger;
import com.metlife.gssp.logging.LoggerFactory;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.client.ClientFactory;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.client.http.RestClient;

/**
 * This utility takes advantage of the Ribbon-aware RestTemplate to invoke REST
 * operations on internal Eureka-registered services as well as EIP services
 * that are ribbon configured by the client name "eipservice". This way clients
 * calling non-platform services can leverage Ribbon features such as auto
 * retry, read/response timeouts, and custom load balancing.
 * <p>
 * Consuming services must specify config as follows in their main app:
 *
 * <pre>
 * {@code @RibbonClients({
 * 	  &#64;RibbonClient(name = "spiservice"),})
 *    }T
 * </pre>
 * <p>
 * Example service-based configuration:
 *
 * <pre>
 *  {@code
 *
 * 	spiservice:  ##Ribbon client name
 * 	  ribbon:
 * 	    listOfServers: localhost:8300
 * 	    ReadTimeout: 5000
 * 	    MaxAutoRetries: 2
 *  }
 * </pre>
 * </p>
 *
 * @author shenry4
 * @Modified vbhamidi - 05/11/2017
 * @Modified sanumala
 */
@Component
@RefreshScope
@SuppressWarnings({"rawtypes", "unchecked"})

public class RegisteredServiceInvoker {
    private static final Logger logger = LoggerFactory.getLogger(RegisteredServiceInvoker.class);

    @Value(value = "${eip.eureka.vipAddress:eip-mock-service}")
    @Deprecated
    private String eipVipAddress;
    @Deprecated
    private static final String EIP_CUSTOM_RIBBON_CLIENT = "eipservice";
    @Value(value = "${useEurekaForEIP:true}")
    @Deprecated
    private boolean useEurekaForEIP;
    @Value(value = "${spi.username:null}")
    private String spiUsername;
    @Value(value = "${spi.password:null}")
    private String spiPassword;
    @Value(value = "${spi.authEnabled:false}")
    private boolean spiAuthEnabled;
    @Value(value = "${spi.ribbonClient:spiservice}")
    private String spiRibbonClient;
    @Value(value = "${spi.baseAddress:null}")
    private String spiBaseAddress;
    @Value(value = "${ribbon.IsSecure:false}")
    private boolean defaultRibbonClientSecure;
    @Value(value = "${spiservice.ribbon.IsSecure:false}")
    private boolean spiRibbonClientSecure;
    @Autowired(required = false)
    private DiscoveryClient discoveryClient;
    protected RestTemplate restTemplate;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */

    public <T> ResponseEntity<T> get(String vipAddress, String serviceEndpoint, Map<String, Object> params, Class<T> clazz) {
        ResponseEntity getResponse;
        HttpEntity<Object> request = this.createRequest(null);
        try {
            getResponse = this.restTemplate.exchange(this.getURL(vipAddress, serviceEndpoint, this.defaultRibbonClientSecure), HttpMethod.GET, request, clazz, params);
        } finally {
            request = null;
        }
        return getResponse;
    }


    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> ResponseEntity<T> get(String vipAddress, String serviceEndpoint, Map<String, Object> params, Class<T> clazz, Map<String, List<String>> headerMap) {

        ResponseEntity getResponse;
        HttpEntity<Object> request = this.createRequestWithCustomHeaders(null, headerMap);
        try {
            getResponse = this.restTemplate.exchange(this.getURL(vipAddress, serviceEndpoint, this.defaultRibbonClientSecure), HttpMethod.GET, request, clazz, params);
        } finally {
            request = null;
        }
        return getResponse;
    }


    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T, K> ResponseEntity<T> post(String vipAddress, String serviceEndpoint, HttpEntity<K> request, Class<T> clazz) {
        ResponseEntity postResponse;
        try {
            postResponse = this.restTemplate.postForEntity(this.getURL(vipAddress, serviceEndpoint, this.defaultRibbonClientSecure), request, clazz, new Object[0]);
        } finally {
            request = null;
        }
        return postResponse;
    }


    public <T> void put(String vipAddress, String serviceEndpoint, HttpEntity<T> request, Map<String, Object> params) {
        this.restTemplate.put(this.getURL(vipAddress, serviceEndpoint, this.defaultRibbonClientSecure), request, params);
    }


    @Deprecated
    public void delete(String vipAddress, String serviceEndpoint, Map<String, Object> params) {
        this.restTemplate.exchange(this.getURL(vipAddress, serviceEndpoint, this.defaultRibbonClientSecure), HttpMethod.DELETE, this.createRequest(null), Object.class, params);
    }


    /**
     * Perform HTTP delete operation with request parameters and Get Service Response
     *
     * @param vipAddress
     * @param serviceEndpoint
     * @param params
     * @param clazz
     * @return ReponseEnitty
     */
    public <T> ResponseEntity<T> delete(String vipAddress, String serviceEndpoint, Map<String, Object> params, Class<T> clazz) {
        return this.delete(vipAddress, serviceEndpoint, params, null, clazz);
    }

    /**
     * <p>
     * Perform HTTP delete operation with request headers and request parameters
     * </p>
     *
     * @param vipAddress
     * @param serviceEndpoint
     * @param params          the request parameters
     * @param headersMap      the request header of type Map<String, String>
     * @param clazz           Response Entity Class
     */
    public <T> ResponseEntity<T> delete(String vipAddress, String serviceEndpoint, Map<String, Object> params, Map<String, String> headersMap, Class<T> clazz) {
        return this.restTemplate.exchange(this.getURL(vipAddress, serviceEndpoint, this.defaultRibbonClientSecure),
                HttpMethod.DELETE,
                this.createRequest(null, headersMap),
                clazz,
                params);

    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public <T, K> ResponseEntity<T> postUsingEIP(String serviceEndpoint, HttpEntity<K> request, Class<T> clazz) {
        ResponseEntity postResponse;
        Object[] arrobject = new Object[1];
        arrobject[0] = this.useEurekaForEIP ? "Eureka VIP" : "non-Eureka config based Ribbon client";
        logger.debug("Using {} for EIP service POST", arrobject);
        try {
            postResponse = this.restTemplate.postForEntity(this.getURL(this.useEurekaForEIP ? this.eipVipAddress : EIP_CUSTOM_RIBBON_CLIENT, serviceEndpoint), request, clazz, new Object[0]);
        } finally {
            request = null;
        }
        return postResponse;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T, K> ResponseEntity<T> postViaSPI(String serviceEndpoint, HttpEntity<K> request, Class<T> clazz) {
        ResponseEntity postResponse;
        logger.debug("Using {} for SPI POST", new Object[]{this.spiRibbonClient});
        this.printSPIRibbonConfig();
        try {
            postResponse = this.restTemplate.postForEntity(this.getURL(this.spiRibbonClient, serviceEndpoint, this.spiRibbonClientSecure), request, clazz, new Object[0]);
        } finally {
            request = null;
        }
        return postResponse;
    }

    private void printSPIRibbonConfig() {
        RestClient client = (RestClient) ClientFactory.getNamedClient((String) "spiservice");
        List<Server> serverList = client.getLoadBalancer().getServerList(true);
        StringBuilder builder = new StringBuilder();
        serverList.forEach(server -> {
                    builder.append("Server ID: ");
                    builder.append(server.getId());
                    builder.append(" [");
                    builder.append(" Host: ");
                    builder.append(server.getHost());
                    builder.append(" Port: ");
                    builder.append(server.getHostPort());
                    builder.append("]  ");
                }
        );
        logger.info(builder.toString());
    }


    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public <T, K> ResponseEntity<T> getUsingEIP(String serviceEndpoint, Class<T> clazz, Map<String, Object> params) {
        ResponseEntity getResponse;
        Object[] arrobject = new Object[1];
        arrobject[0] = this.useEurekaForEIP ? "Eureka VIP" : "non-Eureka config based Ribbon client";
        logger.debug("Using {} for EIP service GET", arrobject);
        HttpEntity<Object> request = this.createRequest(null);
        try {
            getResponse = this.restTemplate.exchange(this.getURL(this.useEurekaForEIP ? this.eipVipAddress : EIP_CUSTOM_RIBBON_CLIENT, serviceEndpoint), HttpMethod.GET, request, clazz, params);
        } finally {
            request = null;
        }
        return getResponse;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T, K> ResponseEntity<T> getViaSPI(String serviceEndpoint, Class<T> clazz, Map<String, Object> params) {
        ResponseEntity getResponse;
        logger.debug("Using {} for SPI GET", new Object[]{this.spiRibbonClient});
        this.printSPIRibbonConfig();
        HttpEntity<Object> request = this.spiRibbonClientSecure ? this.createRequest(null) : this.createAuthenticatedSPIRequest(null);
        try {
            getResponse = this.restTemplate.exchange(this.getURL(this.spiRibbonClient, serviceEndpoint, this.spiRibbonClientSecure), HttpMethod.GET, request, clazz, params);
        } finally {
            request = null;
        }
        return getResponse;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T, K> ResponseEntity<T> getViaSPI(String serviceEndpoint, Class<T> clazz, Map<String, Object> params, Map<String, List<String>> headerMap) {
        ResponseEntity getResponse;
        logger.debug("Using {} for SPI GET", new Object[]{this.spiRibbonClient});
        HttpEntity<Object> request = this.spiRibbonClientSecure ? this.createRequestWithCustomHeaders(null, headerMap) : this.createAuthenticatedSPIRequestWithCustomHeaders(null, headerMap);
        try {
            getResponse = this.restTemplate.exchange(this.getURL(this.spiRibbonClient, serviceEndpoint, this.spiRibbonClientSecure), HttpMethod.GET, request, clazz, params);
        } finally {
            request = null;
        }
        return getResponse;
    }


    @Deprecated
    public void deleteUsingEIP(String serviceEndpoint, Map<String, Object> params) {
        Object[] arrobject = new Object[1];
        arrobject[0] = this.useEurekaForEIP ? "Eureka VIP" : "non-Eureka config based Ribbon client";
        logger.debug("Using {} for EIP service DELETE", arrobject);
        this.restTemplate.exchange(this.getURL(this.useEurekaForEIP ? this.eipVipAddress : EIP_CUSTOM_RIBBON_CLIENT, serviceEndpoint), HttpMethod.DELETE, this.createRequest(null), Object.class, params);
    }

    @Deprecated
    public void deleteViaSPI(String serviceEndpoint, Map<String, Object> params) {
        logger.debug("Using {} for SPI DELETE", new Object[]{this.spiRibbonClient});
        this.printSPIRibbonConfig();
        this.restTemplate.exchange(this.getURL(this.spiRibbonClient, serviceEndpoint, this.spiRibbonClientSecure), HttpMethod.DELETE, this.spiRibbonClientSecure ? this.createRequest(null) : this.createAuthenticatedSPIRequest(null), Object.class, params);
    }


    /**
     * @param serviceEndpoint
     * @param params
     * @param headersMap
     * @param clazz
     * @return
     */
    public <T> ResponseEntity<T> deleteViaSPI(String serviceEndpoint, Map<String, Object> params, Map<String, List<String>> headersMap, Class<T> clazz) {
        logger.debug("Using {} for SPI DELETE", new Object[]{this.spiRibbonClient});
        this.printSPIRibbonConfig();
        return this.restTemplate.exchange(
                this.getURL(this.spiRibbonClient, serviceEndpoint, this.spiRibbonClientSecure),
                HttpMethod.DELETE,
                this.spiRibbonClientSecure ? this.createRequestWithCustomHeaders(null, headersMap) : this.createAuthenticatedSPIRequestWithCustomHeaders(null, headersMap),
                clazz, params);
    }


    @Deprecated
    public <T> void putUsingEIP(String serviceEndpoint, HttpEntity<T> request, Map<String, Object> params) {
        Object[] arrobject = new Object[1];
        arrobject[0] = this.useEurekaForEIP ? "Eureka VIP" : "non-Eureka config based Ribbon client";
        logger.debug("Using {} for EIP service PUT", arrobject);
        this.restTemplate.put(this.getURL(this.useEurekaForEIP ? this.eipVipAddress : EIP_CUSTOM_RIBBON_CLIENT, serviceEndpoint), request, params);
    }

    public <T> void putViaSPI(String serviceEndpoint, HttpEntity<T> request, Map<String, Object> params) {
        logger.debug("Using {} for SPI DELETE", new Object[]{this.spiRibbonClient});
        this.restTemplate.put(this.getURL(this.spiRibbonClient, serviceEndpoint, this.spiRibbonClientSecure), request, params);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ResponseEntity<Object> putViaSPIWithResponse(String serviceEndpoint, HttpEntity<Object> request, Map<String, Object> params) {
        ResponseEntity response;
        logger.debug("Using {} for SPI With response for PUT", new Object[]{this.spiRibbonClient});
        this.printSPIRibbonConfig();
        try {
            response = this.restTemplate.exchange(this.getURL(this.spiRibbonClient, serviceEndpoint, this.spiRibbonClientSecure), HttpMethod.PUT, request, Object.class, params);
        } finally {
            request = null;
        }
        return response;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T, K> ResponseEntity<T> putViaSPIWithResponse(String serviceEndpoint, HttpEntity<T> request, Map<String, Object> params, Class<T> clazz) {
        ResponseEntity response;
        logger.debug("Using {} for SPI With response for PUT", new Object[]{this.spiRibbonClient});
        this.printSPIRibbonConfig();
        try {
            response = this.restTemplate.exchange(this.getURL(this.spiRibbonClient, serviceEndpoint, this.spiRibbonClientSecure), HttpMethod.PUT, request, clazz, params);
        } finally {
            request = null;
        }
        return response;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T, K> ResponseEntity<T> patchViaSPIWithResponse(String serviceEndpoint, HttpEntity<T> request, Map<String, Object> params, Class<T> clazz) {
        ResponseEntity response;
        logger.debug("Using {} for SPI With response for PATCH", new Object[]{this.spiRibbonClient});
        this.printSPIRibbonConfig();
        try {
            response = this.restTemplate.exchange(this.getURL(this.spiRibbonClient, serviceEndpoint, this.spiRibbonClientSecure), HttpMethod.PATCH, request, clazz, params);
        } finally {
            request = null;
        }
        return response;
    }


    private String getURL(String vipAddress, String serviceEndpoint, boolean isSecure) {
        return (isSecure ? "https://" : "http://") + vipAddress + serviceEndpoint;
    }

    private String getURLFromDiscoveryClient(String vipAddress, String serviceEndpoint, boolean isSecure) {
        InstanceInfo nextServerFromEureka = this.discoveryClient.getNextServerFromEureka(vipAddress, isSecure);
        String address = nextServerFromEureka.getIPAddr() + nextServerFromEureka.getPort();
        return (isSecure ? "https://" : "http://") + address + serviceEndpoint;
    }

    @Deprecated
    private String getURL(String vipAddress, String serviceEndpoint) {
        return this.getURL(vipAddress, serviceEndpoint, false);
    }

    public <T> HttpEntity<T> createRequest(T payload) {
        return this.createRequest(payload, null);
    }

    public <T> HttpEntity<T> createRequest(T payload, Map<String, String> headersMap) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String smuser = MDC.get(LoggingContextKey.SMUSER);
        if (smuser != null)
            headers.set(LoggingContextHeader.SMUSER, smuser);

        String transactionId = MDC.get(LoggingContextKey.GSSP_TRANSACTION_ID);
        if (transactionId != null)
            headers.set(LoggingContextHeader.GSSP_TRANSACTION_ID, transactionId);

        String customerId = MDC.get(LoggingContextKey.CUSTOMER_ID);
        if (customerId != null)
            headers.set(LoggingContextHeader.GSSP_CUSTOMER_ID, customerId);

        String tenantId = MDC.get(LoggingContextKey.GSSP_TENANTID);
        if (tenantId != null)
            headers.set(LoggingContextHeader.GSSP_TENANTID, tenantId);

        if (headersMap != null)
            headers.setAll(headersMap);

        logger.debug("REQUEST_HEADERS: {}", headers);
        return new HttpEntity<T>(payload, headers);
    }

    public <T> HttpEntity<T> createRequestWithCustomHeaders(T payload, Map<String, List<String>> headersMap) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String smuser = MDC.get(LoggingContextKey.SMUSER);
        if (smuser != null)
            headers.set(LoggingContextHeader.SMUSER, smuser);

        String transactionId = MDC.get(LoggingContextKey.GSSP_TRANSACTION_ID);
        if (transactionId != null)
            headers.set(LoggingContextHeader.GSSP_TRANSACTION_ID, transactionId);

        String customerId = MDC.get(LoggingContextKey.CUSTOMER_ID);
        if (customerId != null)
            headers.set(LoggingContextHeader.GSSP_CUSTOMER_ID, customerId);

        String tenantId = MDC.get(LoggingContextKey.GSSP_TENANTID);
        if (tenantId != null)
            headers.set(LoggingContextHeader.GSSP_TENANTID, tenantId);

        if (headersMap != null)
            headers.putAll(headersMap);

        logger.debug("REQUEST_HEADERS: {}", headers);
        return new HttpEntity<T>(payload, headers);
    }

    public <T> HttpEntity<T> createAuthenticatedSPIRequest(T payload) {
        HashMap<String, String> authMap = new HashMap<String, String>();
        String plainCredentials = this.spiUsername + ":" + this.spiPassword;
        String base64Credentials = new String(Base64.encodeBase64((byte[]) plainCredentials.getBytes()));
        authMap.put("Authorization", "Basic " + base64Credentials);
        return this.createRequest(payload, authMap);
    }

    public <T> HttpEntity<T> createAuthenticatedSPIRequestWithCustomHeaders(T payload, Map<String, List<String>> headersMap) {
        String plainCredentials = this.spiUsername + ":" + this.spiPassword;
        String base64Credentials = new String(Base64.encodeBase64((byte[]) plainCredentials.getBytes()));
        headersMap.put("Authorization", Arrays.asList("Basic " + base64Credentials));
        return this.createRequestWithCustomHeaders(payload, headersMap);
    }

    @Autowired
    public void customizeRestTemplate(SpringClientFactory springClientFactory, LoadBalancerClient loadBalancerClient) {
        this.restTemplate = new RestTemplate();
        RibbonClientHttpRequestFactory lFactory = new RibbonClientHttpRequestFactory(springClientFactory);
        this.restTemplate.setRequestFactory((ClientHttpRequestFactory) lFactory);
    }
}
 
 
  