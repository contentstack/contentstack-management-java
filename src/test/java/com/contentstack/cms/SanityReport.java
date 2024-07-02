// package com.contentstack.cms;
// import com.slack.api.Slack;
// import com.slack.api.methods.SlackApiException;
// import com.slack.api.methods.response.chat.ChatPostMessageResponse;
// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import java.io.File;
// import java.io.IOException;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.net.http.HttpRequest.BodyPublishers;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.HashMap;
// import java.util.Map;

// public class SanityReport {

//     public static String buildSlackMessage(File input) throws IOException {
//             Document doc = Jsoup.parse(input, "UTF-8");
//             Element summaryTable = doc.select("table.bodyTable").first();
//             Element summaryRow = summaryTable.select("tr.b").first();

//             String totalCount = summaryRow.select("td").get(0).text().trim();
//             String totalErrors = summaryRow.select("td").get(1).text().trim();
//             String totalFailures = summaryRow.select("td").get(2).text().trim();
//             String totalSkipped = summaryRow.select("td").get(3).text().trim();
//             String totalTime = summaryRow.select("td").get(5).text().trim();

//             int durationInMinutes = 0;
//             int durationInSeconds = 0;
//             if (!totalTime.isEmpty()) {
//                 float timeInSeconds = Float.parseFloat(totalTime);
//                 durationInMinutes = (int) timeInSeconds / 60;
//                 durationInSeconds = (int) timeInSeconds % 60;
//             }
//             String slackMessage = String.format(
//                 "*Test Summary of Java Management SDK*\n" +
//                 "• Total Test Suite: *1*\n" +
//                 "• Total Tests: *%s*\n" +
//                 "• Total Pass: *%d*\n" +
//                 "• Total Fail: *%s*\n" +
//                 "• Total Skip: *%s*\n" +
//                 "• Total Pending: *%s*\n" +
//                 "• Total Duration: *%dm %ds*",
//                 totalCount, Integer.parseInt(totalCount) - (Integer.parseInt(totalErrors) + Integer.parseInt(totalFailures)), totalFailures, totalSkipped, totalErrors, durationInMinutes, durationInSeconds
//             );
//         return slackMessage;
//     }

//     public static void publishMessage(String token, String channel, String text, File report) throws IOException, SlackApiException, InterruptedException {
//         try {
//             Slack slack = Slack.getInstance();

//             // Post the message to the Slack channel
//             ChatPostMessageResponse messageResponse = slack.methods(token).chatPostMessage(req -> req
//                     .channel(channel)
//                     .text(text)
//             );
//             // Check if posting message was successful
//             if (!messageResponse.isOk()) {
//                 System.out.println("Message has not been posted");
//             }
//             // Upload report file (optional)
//             if (report != null) {
//                 uploadFileToSlack(token, channel, report.getAbsolutePath());
//             }
//         } catch (IOException | SlackApiException e) {
//             System.out.println(e);
//         }
//     }

//     private static void uploadFileToSlack(String token, String channelName, String filePath) throws IOException, InterruptedException {
//         Path path = Paths.get(filePath);
//         String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
//         Map<String, String> params = new HashMap<>();
//         params.put("channels", channelName);
//         params.put("filename", new File(filePath).getName());
//         params.put("filetype", "text");
//         params.put("initial_comment", "Here is the report generated.");
//         params.put("title", "Reports File");

//         String body = buildMultipartBody(params, Files.readAllBytes(path), boundary);

//         HttpRequest request = HttpRequest.newBuilder()
//                 .uri(URI.create("https://slack.com/api/files.upload"))
//                 .header("Authorization", "Bearer " + token)
//                 .header("Content-Type", "multipart/form-data; boundary=" + boundary)
//                 .POST(BodyPublishers.ofString(body))
//                 .build();

//         HttpClient client = HttpClient.newHttpClient();
//         HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//     }

  
//     private static String buildMultipartBody(Map<String, String> params, byte[] fileContent, String boundary) {
//         StringBuilder sb = new StringBuilder();

//         for (Map.Entry<String, String> entry : params.entrySet()) {
//             sb.append("--").append(boundary).append("\r\n");
//             sb.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"\r\n\r\n");
//             sb.append(entry.getValue()).append("\r\n");
//         }
//         sb.append("--").append(boundary).append("\r\n");
//         sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(params.get("filename")).append("\"\r\n");
//         sb.append("Content-Type: application/octet-stream\r\n\r\n");
//         sb.append(new String(fileContent)).append("\r\n");
//         sb.append("--").append(boundary).append("--");
//         return sb.toString();
//     }
    
// }


