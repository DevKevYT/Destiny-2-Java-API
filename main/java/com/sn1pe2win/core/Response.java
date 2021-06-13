package com.sn1pe2win.core;

/**Payload is mostly in the form of a JsonObject.
 * Sometimes as a DestinyMember or sth.
 * If the errorCode == 0, the payload will be 0.
 * Also useful for standart function responses, since the payload can be any type.
 * 
 * In the context of this program, the Response class is returned by ANY method, that uses an API request anywhere*/
public class Response<T> {
	
	public final int httpStatus;
	public final int errorCode;
	public final String errorStatus;
	private final T payload;
	public final String errorMessage;
	
	/**Imitates an error response with specified errors*/
	public Response(T payload, int httpStatus, String errorStatus, String errorMessage, int errorCode) {
		this.payload = payload;
		this.httpStatus = httpStatus;
		this.errorStatus = errorStatus;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	/**Imitates a success response*/
	public Response(T payload) {
		this.payload = payload;
		this.httpStatus = 200;
		this.errorStatus = "Success";
		this.errorCode = 1;
		this.errorMessage = "Success";
	}
	
	public T getResponseData() {
		return payload;
	}
	
	public boolean containsPayload() {
		return payload != null;
	}
	
	public boolean success() {
		return errorCode == 1 && httpStatus == 200;
	}
	
	public String toString() {
		return "Status:\nHTTP:" + httpStatus + ",\nBungie:" + errorCode + ",\nError:" + errorStatus + ",\nMessage:" + errorMessage + ",\nHasPayload?" + containsPayload();
	}
}
