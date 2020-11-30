package com.signify.spring.jwt.auth.response;

public enum ResponseType {

	/**200-OK*/
	SUCCESS("S"),WARNING("W"),  INFO("I"),
	/**201-CREATED*/
	CREATED("C"),
	/**400-BAD REQUEST*/
	BADREQUEST("BR"),
	/**401-UNAUTHORIZED*/
	UNAUTHORIZED("U"),
	/**403-FORBIDDEN*/
	FORBIDDEN("F"),
	/**404-NOT FOUND*/
	NOTFOUND("NF"),
	/**405-METHOD NOT ALLOWED*/
	NOALLOWED("NA"),
	/**412-PRECONDITION FAILED*/
	PRECONDITIONFAILED("PF"),
	/**500-INTERNAL SERVER ERROR*/
	ERROR("E"),FATAL("F"),
	/**503-SERVICE UNAVAILABLE*/
	SERVICEUNAVAILABLE("SU");

	private final String code;

	private ResponseType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static ResponseType getResponseType(String code) {
		for (ResponseType responseType : ResponseType.values()) {
			if (responseType.getCode().equals(code)) {
				return responseType;
			}
		}
		return null;
	}
}
