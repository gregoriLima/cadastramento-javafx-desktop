package com.cadastramento.singleton;

import okhttp3.OkHttpClient;

public class OkHttpSingleton {

	private static OkHttpClient client;

	// construtor privado
	private OkHttpSingleton() {

	}

	public static OkHttpClient getClient() {
		if (client == null) {
			client = new OkHttpClient().newBuilder().retryOnConnectionFailure(true).build();
		}
		return client;
	}

	public static void closeConnections() {
		client.dispatcher().cancelAll();
	}

}