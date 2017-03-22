package com.singun.securelogin.sdk;

interface LoginAction {
	int getAccountType();
	String getAccountName();
	String getAccountToken();
}