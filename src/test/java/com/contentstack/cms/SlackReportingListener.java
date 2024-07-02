// package com.contentstack.cms;
// import com.contentstack.cms.SanityReport;
// import io.github.cdimascio.dotenv.Dotenv;
// import java.io.File;
// import java.io.IOException;

// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.testng.ITestContext;
// import org.testng.ITestListener;
// import org.testng.ITestResult;

// public class SlackReportingListener implements ITestListener {
//     @Override
//     public void onFinish(ITestContext context) {
//         Dotenv dotenv = Dotenv.load();
//         String slackToken = dotenv.get("SLACK_BOT_TOKEN");
//             String slackChannel = dotenv.get("SLACK_CHANNEL");
//             File input = new File("./target/site/surefire-report.html");
//         try {
//             SanityReport.publishMessage(
//                     slackToken,
//                     slackChannel,
//                     SanityReport.buildSlackMessage(input),
//                     input
//             );
//         } catch (Exception e) {
//             System.out.println("Error sending Slack message: " + e.getMessage());
//         }
//     }
// }
