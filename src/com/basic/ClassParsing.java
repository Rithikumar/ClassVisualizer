package com.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * <p>
 * This checks all the files is the given folder and separates the class from
 * their superclass if superclass is present
 * </p>
 * 
 * @author rithi-zstch1028
 *
 */
public class ClassParsing {

	public static void printFiles(File folder) throws IOException {
		// System.out.println(folder.getName());
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					printFiles(file);
				} else
					// System.out.println(file.getName());
					checkAndStore(file);
			}
		}
	}

	public static void checkAndStore(File location) throws IOException {
		if (location.getName().endsWith(".class")) {
			// System.out.println("here");
			parseClass(terminalCommands("javap", location));
		} else {
			// System.out.println("No class file");
		}
	}

	public static String terminalCommands(String command, File location) throws IOException {
		// System.out.println("path = "+location.getAbsolutePath());
		String[] cmd = { "/bin/sh", "-c", "cd ;" + command + " " + location.getAbsolutePath() + "" };
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";
		String res = "";
		line = reader.readLine();

		while ((line = reader.readLine()) != null) {
			// System.out.println(line);
			line += "\n";
			res += line;
		}
		return res;
	}

	public static void parseClass(String value) {
		// System.out.println(value);
		// value = "public class com.blog.controller.UserCommentController extends
		// javax.servlet.http.HttpServlet{";
		if (value.contains("class")) {
			value = value.substring(value.indexOf("class"), value.indexOf("{"));
			//System.out.println(value);
			String superClass = "none";
			String className;
			String interfaces = "none";
			String[] arr = value.split(" extends ");
			className = arr[0].substring(6);
			if (arr.length > 1) {
				superClass = arr[1];
				// System.out.println("Super: " + superClass);
			}

			if (superClass.equals("none")) {
				String[] arr2 = className.split(" implements ");
				if (arr2.length > 1) {
					String[] interArr = arr2[1].split(",");
					interfaces = "";
					for (String a : interArr) {
						interfaces += a;
					}
				}
				className = arr2[0];
			} else {
				String[] arr2 = superClass.split(" implements ");
				if (arr2.length > 1) {
					String[] interArr = arr2[1].split(",");
					interfaces = "";
					for (String a : interArr) {
						interfaces += a;
					}
				}
				superClass = arr2[0];
			}
			System.out.println("class: "+className + "             superclass : " + superClass + "                 interfaces : "
					+ interfaces);
			// System.out.println("" + arr[0] + " superclass : " + superClass);
//		String trueSuperClass = superClass.split(" implements ")[0];
//		String[] interfaces = superClass.split(" implements ")[1].split(",");

//		if(value.matches(checkPhnum)) {
//			System.out.println(value);
//		};
		}
	}

	public static void main(String[] args) throws IOException {

		String path = "";
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choose a directory");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFolder = jfc.getSelectedFile();
			if (selectedFolder.isDirectory())
				path = String.valueOf(selectedFolder.getAbsoluteFile());
		}
		// System.out.println(path);
		File folder = new File(path);
		printFiles(folder);
	}
}
