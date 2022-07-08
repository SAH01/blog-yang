package com.reliable.yang;

/**
 * @author Administrator
 * @date 2022-06-20 16:32
 */
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
@Slf4j
@SpringBootApplication
public class BlogApp {
	public static void main(String[] args) throws UnknownHostException, UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(BlogApp.class, args);
		log.info("..######..##.....##..######...######..########..######...######.\n" +
				".##....##.##.....##.##....##.##....##.##.......##....##.##....##\n" +
				".##.......##.....##.##.......##.......##.......##.......##......\n" +
				"..######..##.....##.##.......##.......######....######...######.\n" +
				".......##.##.....##.##.......##.......##.............##.......##\n" +
				".##....##.##.....##.##....##.##....##.##.......##....##.##....##\n" +
				"..######...#######...######...######..########..######...######.");
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		if (StringUtils.isEmpty(path)) {
			path = "";
		}
		log.info("\n----------------------------------------------------------\n\t" +
				"Application  is running! Access URLs:\n\t" +
				"Local访问网址: \t\thttp://localhost:" + "8080" + "\n\t" +
//				"External访问网址: \thttp://" + ip + ":" + port + path + "\n\t" +
				"----------------------------------------------------------");
	}
}