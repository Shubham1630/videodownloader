package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.yns;

import com.google.gson.annotations.SerializedName;

public class FormatItem{

	@SerializedName("ext")
	private String ext;

	@SerializedName("size")
	private String size;

	@SerializedName("width")
	private String width;

	@SerializedName("id")
	private String id;

	@SerializedName("url")
	private String url;

	@SerializedName("height")
	private String height;

	public void setExt(String ext){
		this.ext = ext;
	}

	public String getExt(){
		return ext;
	}

	public void setSize(String size){
		this.size = size;
	}

	public String getSize(){
		return size;
	}

	public void setWidth(String width){
		this.width = width;
	}

	public String getWidth(){
		return width;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setHeight(String height){
		this.height = height;
	}

	public String getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"FormatItem{" + 
			"ext = '" + ext + '\'' + 
			",size = '" + size + '\'' + 
			",width = '" + width + '\'' + 
			",id = '" + id + '\'' + 
			",url = '" + url + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}