package org.troulemaker;

public class TransactionConstant {

	public static final String TRANSACTION_INTRADAY = "Intraday";
	public static final String TRANSACTION_NORMAL = "Normal";

	public static final String TRANSACTION_NORMAL_PRIORITY_SELL_WITHDRAW = "sell&withdraw";
	public static final String TRANSACTION_HIGH_PRIORITY = "high";
	public static final String TRANSACTION_NORMAL_PRIORITY_BUY_DEPOSIT = "buy&deposit";

	public static final String SOURCE_DATA = "resources/sampleData.xlsx";

	public static final Integer TRANSACTION_HIGH_PRIORITY_PROCESSING_FEE = 500;
	public static final Integer TRANSACTION_NORMAL_PRIORITY_BUY_DEPOSIT_PROCESSING_FEE = 50;
	public static final Integer TRANSACTION_NORMAL_PRIORITY_SELL_WITHDRAW_PROCESSING_FEE = 100;
	public static final Integer TRANSACTION_INTRADAY_PROCESSING_FEE = 10;
	public static final String TRANSACTION_TYPE_BUY = "BUY";
	public static final String TRANSACTION_TYPE_SELL = "SELL";
	public static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
	public static final String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";

	public static String[] reportColumns = { "Client ID", "Transaction Type", "Transaction Date", "Priority",
			"Processing Fee" };
}
