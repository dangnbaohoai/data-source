package com.c1se44.school_connect.utils;



//@Component
//public class mailGun {
//	public static String API_KEY = " b7db089a572a2edbe2f40082c0004b10-30b9cd6d-fe07c935"; //TODO put your Private API Key here
//	private static final String YOUR_DOMAIN_NAME = "sandbox9c755868cea249a3a19efd53a70de841.mailgun.org"; //TODO put your domain name here
//	public JsonNode sendSimpleMessage(String email,String title,String text) throws UnirestException {
//
//		HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
//					.basicAuth("api", API_KEY)
//					.field("from", "Excited User thangphu102@gmail.com")
//					.field("to", email)//TODO put your emails here
//					.field("subject", title)
//					.field("text", text)
//					.asJson();
//
//		return request.getBody();
//	}
//}
