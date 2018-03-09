package org.troulemaker;

import java.util.Date;

public class TransactionData implements Comparable<TransactionData> {
	private String externalTransactionId;
	private String clientId;
	private String securityId;
	private String transactionType;
	private Date transactionDate;
	private Double marketValue;
	private String priorityFlag;
	private Integer processingFee;

	public String getExternalTransactionId() {
		return externalTransactionId;
	}

	public void setExternalTransactionId(String externalTransactionId) {
		this.externalTransactionId = externalTransactionId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date date) {
		this.transactionDate = date;
	}

	public Double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Double d) {
		this.marketValue = d;
	}

	public String getPriorityFlag() {
		return priorityFlag;
	}

	public void setPriorityFlag(String priorityFlag) {
		this.priorityFlag = priorityFlag;
	}

	public Integer getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(Integer processingFee) {
		this.processingFee = processingFee;
	}

	@Override
	public int compareTo(TransactionData o) {

		// TODO Auto-generated method stub
		if( this.clientId.compareTo(o.clientId) == 0) {
			if( this.transactionType.compareTo(o.transactionType) == 0) {
				if( this.transactionDate.compareTo(o.transactionDate) == 0) {
					return -1;
				}
				else {
					return this.transactionDate.compareTo(o.transactionDate);
				}
			}
			else {
				return this.transactionType.compareTo(o.transactionType);
			}
		}
		else {
			return this.clientId.compareTo(o.clientId);
		}
	}

}
