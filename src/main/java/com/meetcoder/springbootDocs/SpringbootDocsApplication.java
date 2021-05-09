package com.meetcoder.springbootDocs;

import org.springframework.boot.Banner;
import org.springframework.boot.ImageBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.UrlResource;

import java.io.PrintStream;
import java.net.MalformedURLException;

@SpringBootApplication
public class SpringbootDocsApplication {

//	public static void main (String[] args) {
//		SpringApplication app = new SpringApplication(SpringbootDocsApplication.class);
//		app.setBannerMode(Banner.Mode.OFF);
//		app.run(args);
//	}

//	public static void main (String[] args) throws MalformedURLException {
//		SpringApplication app = new SpringApplication(SpringbootDocsApplication.class);
//		UrlResource r = new UrlResource("http://pngimg.com/uploads/google/google_PNG102346.png");
//		app.setBanner(new ImageBanner(r));
//		app.run(args);
//	}

//	// /resources/banner.txt 에 원하는 배너 저장
//	public static void main (String[] args) throws MalformedURLException {
//		SpringApplication app = new SpringApplication(SpringbootDocsApplication.class);
//		app.setLogStartupInfo(false);
//		app.run(args);
//	}

//	public static void main (String[] args) throws MalformedURLException {
//		SpringApplication app = new SpringApplication(SpringbootDocsApplication.class);
//		app.setLogStartupInfo(false);
//		app.setBanner(new Banner() {
//			@Override
//			public void printBanner (Environment environment,
//									 Class<?> sourceClass,
//									 PrintStream out) {
//				out.println("--- my custom banner ---");
//			}
//		});
//		app.run(args);
//	}

	public static void main(String[] args) {

	}
}
