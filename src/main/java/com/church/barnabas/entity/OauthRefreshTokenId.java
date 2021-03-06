package com.church.barnabas.entity;
// Generated 8 May, 2021 6:19:11 PM by Hibernate Tools 5.0.6.Final

import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OauthRefreshTokenId generated by hbm2java
 */
@Embeddable
public class OauthRefreshTokenId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tokenId;
	private byte[] token;
	private byte[] authentication;

	public OauthRefreshTokenId() {
	}

	public OauthRefreshTokenId(String tokenId, byte[] token, byte[] authentication) {
		this.tokenId = tokenId;
		this.token = token;
		this.authentication = authentication;
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

	@Column(name = "authentication")
	public byte[] getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OauthRefreshTokenId))
			return false;
		OauthRefreshTokenId castOther = (OauthRefreshTokenId) other;

		return ((this.getTokenId() == castOther.getTokenId()) || (this.getTokenId() != null
				&& castOther.getTokenId() != null && this.getTokenId().equals(castOther.getTokenId())))
				&& ((this.getToken() == castOther.getToken()) || (this.getToken() != null
						&& castOther.getToken() != null && Arrays.equals(this.getToken(), castOther.getToken())))
				&& ((this.getAuthentication() == castOther.getAuthentication())
						|| (this.getAuthentication() != null && castOther.getAuthentication() != null
								&& Arrays.equals(this.getAuthentication(), castOther.getAuthentication())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTokenId() == null ? 0 : this.getTokenId().hashCode());
		result = 37 * result + (getToken() == null ? 0 : Arrays.hashCode(this.getToken()));
		result = 37 * result + (getAuthentication() == null ? 0 : Arrays.hashCode(this.getAuthentication()));
		return result;
	}

}
