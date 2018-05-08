package com.ydemo.base.cores.utils; /**
 * 
 */


import org.springframework.util.StringUtils;

public enum QiniuBucketType {
	IMAGE("ycimgs","on74nfn25.bkt.clouddn.com/"),
	AUDIOADMIN("audio-admin","http://7xiwdu.com2.z0.glb.qiniucdn.com/");
	
	private String bucketName;
	private String bucketUrl;

	private QiniuBucketType(String bucket, String url) {
		this.bucketName = bucket;
		this.bucketUrl = url;
	}
	
	public String getBucketName() {
		return bucketName;
	}

	public String getBucketUrl() {
		return bucketUrl;
	}



	public static QiniuBucketType getByBucketName(String bucketName) {
		if(StringUtils.isEmpty(bucketName)) {
			return null;
		}
		QiniuBucketType[] buckets = QiniuBucketType.values();
		for (QiniuBucketType bucket : buckets) {
			if(bucket.getBucketName().equals(bucketName)) {
				return bucket;
			}
		}
		return null;
	}
}
