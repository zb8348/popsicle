package com.pfw.popsicle.tools;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileMonitor {

	public static void main(String[] args) {
		System.out.println(System.getProperty("catalina.home"));
		System.out.println(System.getProperty("catalina.base"));
	}
	public static void getFileChangeInfo() {
		String parentDir="";
		FileAlterationObserver observer = new FileAlterationObserver(parentDir);
		observer.addListener(new FileAlterationListener() {
			public void onStart(FileAlterationObserver observer) {
				// TODO Auto-generated method stub
			}
			public void onDirectoryCreate(File directory) {
				// TODO Auto-generated method stub
			}
			public void onDirectoryChange(File directory) {
				// TODO Auto-generated method stub
			}
			public void onDirectoryDelete(File directory) {
				// TODO Auto-generated method stub
			}
			public void onFileCreate(File file) {
				// TODO Auto-generated method stub
			}
			public void onFileChange(File file) {
				// TODO Auto-generated method stub
			}
			public void onFileDelete(File file) {
				// TODO Auto-generated method stub
			}
			public void onStop(FileAlterationObserver observer) {
				// TODO Auto-generated method stub
			}
		});

		FileAlterationMonitor monitor = new FileAlterationMonitor(TimeUnit.SECONDS.toMillis(10), observer);
		try {
			monitor.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
