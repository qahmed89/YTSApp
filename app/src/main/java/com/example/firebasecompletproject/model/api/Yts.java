package com.example.firebasecompletproject.model.api;

import com.google.gson.annotations.SerializedName;

public class Yts{

	@SerializedName("status_message")
	private String statusMessage;

	@SerializedName("data")
	private Data data;

	@SerializedName("@meta")
	private Meta meta;

	@SerializedName("status")
	private String status;

	public void setStatusMessage(String statusMessage){
		this.statusMessage = statusMessage;
	}

	public String getStatusMessage(){
		return statusMessage;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}


}