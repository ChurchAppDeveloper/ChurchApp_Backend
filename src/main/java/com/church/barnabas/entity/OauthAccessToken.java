package com.church.barnabas.entity;
// Generated 8 May, 2021 6:19:11 PM by Hibernate Tools 5.0.6.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OauthAccessToken generated by hbm2java
 */
@Entity
@Table(name = "oauth_access_token", catalog = "churchapp")
public class OauthAccessToken implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authenticationId;
	private String tokenId;
	private byte[] token;
	private String userName;
	private String clientId;
	private byte[] authentication;
	private String refreshToken;

	public OauthAccessToken() {
	}

	public OauthAccessToken(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public OauthAccessToken(String authenticationId, String tokenId, byte[] token, String userName, String clientId,
			byte[] authentication, String refreshToken) {
		this.authenticationId = authenticationId;
		this.tokenId = tokenId;
		this.token = token;
		this.userName = userName;
		this.clientId = clientId;
		this.authentication = authentication;
		this.refreshToken = refreshToken;
	}

	@Id

	@Column(name = "authentication_id", unique = true, nullable = false)
	public String getAuthenticationId() {
		return this.authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	@Column(name = "token_id")
	public String getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Column(name = "token")
	public byte[] getToken() {
		return this.token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "client_id")
	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Column(name = "authentication")
	public byte[] getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	@Column(name = "refresh_token")
	public String getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
