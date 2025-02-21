package com.sparta.first.project.eighteen.common.constants;

public class Constant {
	public static class Code {
		public static final int SUCCESS = 0;
		public static final int STORE_ERROR = -2;
		public static final int FOOD_ERROR = -3;
		public static final int ORDER_ERROR = -4;
		public static final int REVIEW_ERROR = -5;
		public static final int PAYMENT_ERROR = -6;
		public static final int INTERNAL_SERVER_ERROR = -500;
	}

	public static class UserCode {
		public static final String SYSTEM_CODE = "50ce7d0a-4ae2-4c47-9842-56bdc29c060d";
		public static final int USER_ERROR = -1;
	}

	public static class ErrorMessage {
		public static final String OK = "";
		public static final String NOTE_IS_TOO_LONG = "최대 50자 이하의 요청사항을 작성해주세요";
	}
}
