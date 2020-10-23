/*
 * (C) Copyright 2013 Scoop IT SAS (http://scoop.it/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Philippe GASSMANN
 *     Jean-Baptiste BELLET
 */
package com.water.util.upload.scoopit.weedfs.client;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.water.util.upload.scoopit.weedfs.client.catching.LookupCache;
@Component
public class WeedFSClientBuilder {

    HttpClient httpClient;

    URL masterUrl;

    LookupCache lookupCache;
    
    
    String ip;
    
    String port;

    public WeedFSClientBuilder() {

    }

    public static WeedFSClientBuilder createBuilder() {
        return new WeedFSClientBuilder();
    }

    public WeedFSClientBuilder setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public WeedFSClientBuilder setMasterUrl(URL masterUrl) {
        this.masterUrl = masterUrl;
        return this;
    }

    public WeedFSClientBuilder setLookupCache(LookupCache lookupCache) {
        this.lookupCache = lookupCache;
        return this;
    }

    public WeedFSClient build()  {
        if (masterUrl == null) {
            try {
                ip = "192.168.1.11";
                port = "9333";
                masterUrl = new URL("http://"+ip+":"+port);
            } catch (MalformedURLException e) {
                // This cannot happen by construction
                throw new Error(e);
            }
        }

        if (httpClient == null) {
            // minimal http client
            httpClient = HttpClientBuilder.create().build();
        }

        return new WeedFSClientImpl(masterUrl, httpClient, lookupCache);
    }
    
    /***
     * 
     * @方法名称: build
     * @功能描述: 通过IP 端口 动态 的配置  
     * @作者:张凯强
     * @创建时间:2018年9月25日 下午2:18:33
     * @param ip
     * @param port
     * @return WeedFSClient
     */
    public WeedFSClient build(String ip ,String port)  {
        System.out.println(ip);
        if (masterUrl == null) {
            try {
                ip = StringUtils.isEmpty(ip)?"192.168.1.11":ip;
                port = StringUtils.isEmpty(port)?"9333":port;
                masterUrl = new URL("http://"+ip+":"+port);
            } catch (MalformedURLException e) {
                // This cannot happen by construction
                throw new Error(e);
            }
        }

        if (httpClient == null) {
            // minimal http client
            httpClient = HttpClientBuilder.create().build();
        }

        return new WeedFSClientImpl(masterUrl, httpClient, lookupCache);
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
    

}
