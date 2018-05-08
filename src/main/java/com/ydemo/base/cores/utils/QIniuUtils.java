package com.ydemo.base.cores.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.ydemo.base.cores.config.TestConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QIniuUtils {
	private static Logger log = LoggerFactory.getLogger(QIniuUtils.class);
	
	private static final long expires = 3600;

	private static TestConfig testConfig= new TestConfig();

	private static final UploadManager uploadManager = new UploadManager(new Configuration());
	
	private static String getUpTouken(QiniuBucketType bucket) {
		log.info("ak======"+testConfig.getAK());
		log.info("sk======"+testConfig.getSK());
		Auth auth = Auth.create(testConfig.getAK(), testConfig.getSK());

		String token = auth.uploadToken(bucket.getBucketName(), null, expires, new StringMap()
	            .putNotEmpty("returnBody", MyRet.getParamsStr()));
		
		log.info(token);
		
		return token;
	}
	
	public static MyRet upImageFile(byte[] image, QiniuBucketType bucket) {
		MyRet ret = new MyRet();
		 try {
		        Response res = uploadManager.put(image, null, getUpTouken(bucket));
		        ret = res.jsonToObject(MyRet.class);
		        log.info(res.toString());
		        log.info(res.bodyString());
		        return ret;
		    } catch (QiniuException e) {
		        Response r = e.response;
		        // 请求失败时简单状态信息
		        log.error(r.toString());
		        try {
		            // 响应的文本信息
		        	log.error(r.bodyString());
		        	ret.error = r.error;
		        } catch (QiniuException e1) {
		        }
		    }
		 return ret;
	}
	
	public static class MyRet {
		public String bucket;
		public String fname;
	    public long fsize;
	    public String key;
	    public String hash;
	    public int width;
	    public int height;
	    public String format;
	    public String filetype;
	    public String error="0";
	    
	    public String getUrl() {
	    	QiniuBucketType bucketType = QiniuBucketType.getByBucketName(bucket);
	    	if(bucketType == null) {
	    		return null;
	    	}
	    	
	    	return bucketType.getBucketUrl() + key;
	    }
	    
	    public static String getParamsStr() {
	    	return "{\"key\": $(key), \"hash\": $(etag), \"bucket\": $(bucket), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height), \"format\": $(imageInfo.format), \"fsize\": $(fsize)}";
	    }
	    
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}


