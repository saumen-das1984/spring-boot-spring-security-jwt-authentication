package com.signify.spring.jwt.auth.response;

import org.springframework.http.HttpStatus;

/**
 * @author Saumen
 *
 */
public enum GenericResponseCode implements ResponseCode {

	/** 
	 * HTTP Status Code : 200-OK
	 * Description : Successful
	 **/
	SUCCESS					("CR200100","Operation successfully completed", ResponseType.SUCCESS),
	DATA_FOUND				("CR200101","Data found", ResponseType.SUCCESS),
	FILE_UPLOAD_SUCCESS		("CR200102","File upload successfully", ResponseType.SUCCESS),
	AUTHENTICATION_SUCCESS	("CR200103","Authentication successful for, '%s'", ResponseType.SUCCESS),
	REQUEST_AUTHORIZED		("CR200104","Request has been authorized", ResponseType.SUCCESS),
	INFO					("CR200105","Information!", ResponseType.INFO),
	WARNING					("CR200106","Warning!", ResponseType.WARNING),
	
	/** 
	 * HTTP Status Code : 201 Created
	 * Description : Created
	 **/
	CREATED					("CR201100","Created!", ResponseType.CREATED),
	DATA_SAVED				("CR201101","Data saved successfully", ResponseType.CREATED),
	TASK_EXECUTED			("CR201101","Task executed successfully", ResponseType.CREATED),
	
	/** 
	 * HTTP Status Code : 400 Bad Request
	 * Description : Bad input parameter. Error message should indicate which one and why.
	 **/
	BAD_REQUEST				("CR400100","Bad input parameter", ResponseType.BADREQUEST),
	INVALID_REQUEST_FORMAT	("CR400101","Request format invalid", ResponseType.BADREQUEST),	
	DATA_INVALID_FORMAT		("CR400101","Data format invalid", ResponseType.BADREQUEST),	
	
	/** 
	 * HTTP Status Code : 401 Unauthorized
	 * Description : The client passed in the invalid Auth token. Client should refresh the token and then try again..
	 **/
	UNAUTHORIZED_REQUEST	("CR401100","Unauthorized request", ResponseType.UNAUTHORIZED),
	AUTHENTICATION_ERROR	("CR401101","Authentication or Authorization error occured", ResponseType.UNAUTHORIZED),
	AUTHENTICATION_FAILED	("CR401102","Authentication failed", ResponseType.UNAUTHORIZED),
	
	/** 
	 * HTTP Status Code : 403 Forbidden
	 * Description :* Customer doesn’t exist. 
	 * 				* Application not registered. 
	 * 				* Application try to access to properties not belong to an App. 
	 * 				* Application try to trash/purge root node. 
	 * 				* Application try to update contentProperties. 
	 * 				* Operation is blocked (for third-party apps). 
	 * 				* Customer account over quota.
	 **/
	FORBIDDEN	("CR403100","Forbidden request", ResponseType.FORBIDDEN),
	/** 
	 * HTTP Status Code : 404 Not Found
	 * Description :* Resource not found.
	 *
	  **/
	NOTFOUND				("CR404100","Not found", ResponseType.NOTFOUND),
	DATA_NOT_FOUND			("CR404101","No data found", ResponseType.NOTFOUND),
	FILE_NOT_FOUND			("CR404102","File not found", ResponseType.NOTFOUND),
	/** 
	 * HTTP Status Code : 405 Method Not Allowed
	 * Description :* The resource doesn't support the specified HTTP verb.
	 *
	  **/
	NOTALLOWED				("CR405100","Not Allowed", ResponseType.NOALLOWED),
	OPERATION_ERROR			("CR405101","Error occoured while performing the operation", ResponseType.NOALLOWED),
	FILE_UPLOAD_FAILED		("CR405102","File upload failed", ResponseType.NOALLOWED),
	TASK_FAILED				("CR405104","Task execution failed", ResponseType.NOALLOWED),
	/** 
	 * HTTP Status Code : 412 Precondition Failed
	 * Description :* Precondition failed.
	 *
	  **/
	PRECONDITIONFAILED		("CR412100","Precondition Failed", ResponseType.PRECONDITIONFAILED),
	DATA_VALIDATION_FAILED	("CR412101","Data validation Failed", ResponseType.PRECONDITIONFAILED),
	
	
	
	
	/** 
	 * HTTP Status Code : 500 Internal Server Error
	 * Description :* Servers are not working as expected. The request is probably valid but needs to be requested again later.
	 *
	  **/
	UNEXPECTED_SYSTEM_ERROR	("CR500100","Unexpected system error occured", ResponseType.FATAL),
	OBJECT_PARSE_FAILED		("CR500102","Object parse failed", ResponseType.ERROR),	
	MAPPING_DATA_ERROR		("CR500103","Object Retrieval Failure", ResponseType.ERROR),
	CONSTRAIN_EXCPETION		("CR500104","DB Constrain exception occured", ResponseType.ERROR),
	
	/** 
	 * HTTP Status Code : 503 Service Unavailable
	 * Description :* Service Unavailable.
	 *
	  **/
	SERVICE_UNAVAILABLE		("CR503100","Service Unavailable", ResponseType.SERVICEUNAVAILABLE);
	;
	
	
	private final String description;
	private final String code;
	private final String type;
	
	private HttpStatus httpStatus;
	
	private GenericResponseCode(String code, String description, ResponseType type) {
		this.code = code;
		this.description = description;
		this.type = type.getCode();
		
		this.httpStatus = getHttpStatus(type);
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String getType() {
		return this.type;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * 
	 * @param type
	 * @return
	 * 	/--200-OK-/
		SUCCESS("S"),WARNING("W"),  INFO("I"),
		/--201-CREATED-/
		CREATED("C"),
		/--400-BAD REQUEST-/
		BADREQUEST("BR"),
		/--401-UNAUTHORIZED-/
		UNAUTHORIZED("U"),
		/--403-FORBIDDEN-/
		FORBIDDEN("F"),
		/--404-NOT FOUND-/
		NOTFOUND("NF"),
		/--405-METHOD NOT ALLOWED-/
		NOALLOWED("NA"),
		/--412-PRECONDITION FAILED-/
		PRECONDITIONFAILED("PF"),
		/--500-INTERNAL SERVER ERROR-/
		ERROR("E"),FATAL("F"),
		/--503-SERVICE UNAVAILABLE-/
		SERVICEUNAVAILABLE("SU")
	 */
	private HttpStatus getHttpStatus(ResponseType type) {
		HttpStatus httpStatus = null;

		switch (type) {
		case INFO:
			httpStatus = HttpStatus.OK;
			break;
		case SUCCESS:
			httpStatus = HttpStatus.OK;
			break;
		case WARNING:
			httpStatus = HttpStatus.OK;
			break;
		case CREATED:
			httpStatus = HttpStatus.CREATED;
			break;
		case BADREQUEST:
			httpStatus = HttpStatus.BAD_REQUEST;
			break;		
		case UNAUTHORIZED:
			httpStatus = HttpStatus.UNAUTHORIZED;
			break;
		case FORBIDDEN:
			httpStatus = HttpStatus.FORBIDDEN;
			break;	
		case NOTFOUND:
			httpStatus = HttpStatus.NOT_FOUND;
			break;	
		case NOALLOWED:
			httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
			break;	
		case PRECONDITIONFAILED:
			httpStatus = HttpStatus.PRECONDITION_FAILED;
			break;		
		case ERROR:
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			break;
		case FATAL:
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			break;
		case SERVICEUNAVAILABLE:
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			break;
		default:
			httpStatus = HttpStatus.NO_CONTENT;
			break;
		}

		return httpStatus;
	}

}
