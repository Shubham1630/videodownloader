package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.yns;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class YnsModel{

	@SerializedName("preview")
	private String preview;

	@SerializedName("copyright")
	private String copyright;

	@SerializedName("responseTime")
	private String responseTime;

	@SerializedName("format")
	private List<FormatItem> format;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	@SerializedName("needProxy")
	private String needProxy;

	@SerializedName("url")
	private String url;

	@SerializedName("quality")
	private List<String> quality;

	@SerializedName("duration")
	private String duration;

	@SerializedName("license")
	private List<String> license;

	@SerializedName("isProtected")
	private String isProtected;

	@SerializedName("subtitle")
	private String subtitle;

	@SerializedName("audioOnly")
	private String audioOnly;

	@SerializedName("thumbnails")
	private String thumbnails;

	@SerializedName("status")
	private String status;

	public void setPreview(String preview){
		this.preview = preview;
	}

	public String getPreview(){
		return preview;
	}

	public void setCopyright(String copyright){
		this.copyright = copyright;
	}

	public String getCopyright(){
		return copyright;
	}

	public void setResponseTime(String responseTime){
		this.responseTime = responseTime;
	}

	public String getResponseTime(){
		return responseTime;
	}

	public void setFormat(List<FormatItem> format){
		this.format = format;
	}

	public List<FormatItem> getFormat(){
		return format;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setNeedProxy(String needProxy){
		this.needProxy = needProxy;
	}

	public String isNeedProxy(){
		return needProxy;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setQuality(List<String> quality){
		this.quality = quality;
	}

	public List<String> getQuality(){
		return quality;
	}

	public void setDuration(String duration){
		this.duration = duration;
	}

	public String getDuration(){
		return duration;
	}

	public void setLicense(List<String> license){
		this.license = license;
	}

	public List<String> getLicense(){
		return license;
	}

	public void setIsProtected(String isProtected){
		this.isProtected = isProtected;
	}

	public String getIsProtected(){
		return isProtected;
	}

	public void setSubtitle(String subtitle){
		this.subtitle = subtitle;
	}

	public String getSubtitle(){
		return subtitle;
	}

	public void setAudioOnly(String audioOnly){
		this.audioOnly = audioOnly;
	}

	public String isAudioOnly(){
		return audioOnly;
	}

	public void setThumbnails(String thumbnails){
		this.thumbnails = thumbnails;
	}

	public String getThumbnails(){
		return thumbnails;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"YnsModel{" + 
			"preview = '" + preview + '\'' + 
			",copyright = '" + copyright + '\'' + 
			",responseTime = '" + responseTime + '\'' + 
			",format = '" + format + '\'' + 
			",description = '" + description + '\'' + 
			",title = '" + title + '\'' + 
			",needProxy = '" + needProxy + '\'' + 
			",url = '" + url + '\'' + 
			",quality = '" + quality + '\'' + 
			",duration = '" + duration + '\'' + 
			",license = '" + license + '\'' + 
			",isProtected = '" + isProtected + '\'' + 
			",subtitle = '" + subtitle + '\'' + 
			",audioOnly = '" + audioOnly + '\'' + 
			",thumbnails = '" + thumbnails + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}