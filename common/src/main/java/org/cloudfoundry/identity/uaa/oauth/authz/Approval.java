/*
 * Cloud Foundry 2012.02.03 Beta
 * Copyright (c) [2009-2012] VMware, Inc. All Rights Reserved.
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product includes a number of subcomponents with
 * separate copyright notices and license terms. Your use of these
 * subcomponents is subject to the terms and conditions of the
 * subcomponent's license, as noted in the LICENSE file.
 */
package org.cloudfoundry.identity.uaa.oauth.authz;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Approval {

	private static final long THIRTY_MINUTES_IN_MILLIS = 1800000;

	private String userName;

	private String clientId;

	private String scope;

	public enum ApprovalStatus {
		APPROVED("APPROVED"),
		DENIED("DENIED");
		
		private String approvalStatus;
		
		public String toString() {
			return approvalStatus;
		}
		
		private ApprovalStatus(String approvalStatus) {
			this.approvalStatus = approvalStatus;
		}
	}
	
	private ApprovalStatus status;

	public ApprovalStatus getStatus() {
		return status;
	}

	private Date expiresAt;

	public String getUserName() {
		return userName;
	}

	public void setUserId(String userId) {
		this.userName = userId == null ? "" : userId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId == null ? "" : clientId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope == null ? "" : scope;
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt == null ? new Date(new Date().getTime() + THIRTY_MINUTES_IN_MILLIS) : expiresAt;
	}

	public Approval(String userId, String clientId, String scope, long expiresIn, ApprovalStatus status) {
		this(userId, clientId, scope, new Date(new Date().getTime() + expiresIn), status);
	}

	public Approval(String userId, String clientId, String scope, Date expiresAt, ApprovalStatus status) {
		this.userName = userId;
		this.clientId = clientId;
		this.scope = scope;
		this.expiresAt = expiresAt;
		this.status = status;
	}

	public Approval() { }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userName.hashCode();
		result = prime * result + clientId.hashCode();
		result = prime * result + scope.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Approval)) {
			return false;
		}
		Approval other = (Approval) o;
		return userName.equals(other.userName) && clientId.equals(other.clientId) && scope.equals(other.scope) && status == other.status;
	}

	@Override
	public String toString() {
		return String.format("[%s, %s, %s, %s]", userName, scope, clientId, expiresAt);
	}

	public void setStatus(ApprovalStatus status) {
		this.status = status;
	}

}