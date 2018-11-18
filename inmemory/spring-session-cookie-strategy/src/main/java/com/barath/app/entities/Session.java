package com.barath.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="SESSION")
public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8466064591187705209L;

	@Id
	@Column(name="SESSION_ID")
	private String sessionId;
	
	@Column(name="SESSION_CREATION_TIME")
	private long creationTime;
	
	@Column(name="SESSION_LAST_ACCESSED_TIME")
	private long lastAccessTime;
	
	@Column(name="SESSION_MAX_INACTIVE_INTERVAL_IN_SECONDS")
	private int maxInactiveIntervalInSeconds;
	
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="SESSION_EXPIRED")
	private boolean isExpired;

	public Session() {
		super();

	}

	public Session(String sessionId, long creationTime, long lastAccessTime, int maxInactiveIntervalInSeconds,
			String userName, boolean isExpired) {
		super();
		this.sessionId = sessionId;
		this.creationTime = creationTime;
		this.lastAccessTime = lastAccessTime;
		this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
		this.userName = userName;
		this.isExpired = isExpired;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (creationTime ^ (creationTime >>> 32));
		result = prime * result + (isExpired ? 1231 : 1237);
		result = prime * result + (int) (lastAccessTime ^ (lastAccessTime >>> 32));
		result = prime * result + maxInactiveIntervalInSeconds;
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (creationTime != other.creationTime)
			return false;
		if (isExpired != other.isExpired)
			return false;
		if (lastAccessTime != other.lastAccessTime)
			return false;
		if (maxInactiveIntervalInSeconds != other.maxInactiveIntervalInSeconds)
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public int getMaxInactiveIntervalInSeconds() {
		return maxInactiveIntervalInSeconds;
	}

	public void setMaxInactiveIntervalInSeconds(int maxInactiveIntervalInSeconds) {
		this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Session{" +
				"sessionId='" + sessionId + '\'' +
				", creationTime=" + creationTime +
				", lastAccessTime=" + lastAccessTime +
				", maxInactiveIntervalInSeconds=" + maxInactiveIntervalInSeconds +
				", userName='" + userName + '\'' +
				", isExpired=" + isExpired +
				'}';
	}
}
