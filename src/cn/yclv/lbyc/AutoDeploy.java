package cn.yclv.lbyc;

import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.yclv.constrant.CommonConstant;
import cn.yclv.util.FileUtil;

public class AutoDeploy {
	public static String buildName = "build.js";
	public static void main(String[] args) {
		rewriteIndex(CommonConstant.SCRIPT_PATTERN_BUILD, "build");
		System.out.println(buildName);
		renameBuildFile();
//		rewriteIndex(CommonConstant.SCRIPT_PATTERN_VENDOR, "vendor");
	}
	
	/**
	 * 自动化index.html
	 */
	public static void rewriteIndex(String pattern, String str) {
		String fileText = FileUtil.readTxt(CommonConstant.LBYC_INDEX_PATH);
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(fileText);
		while(matcher.find()){
			String result = matcher.group(0);
			String subResult = result.substring(result.indexOf(str), result.length());
			String randomBuild = subResult.substring(0, subResult.indexOf(".js") + 3);
			buildName = randomBuild;
			System.out.println(randomBuild);
			if(subResult.length() > 20){
				try {
					String hostAddress = Inet4Address.getLocalHost().getHostAddress();
					String newFileText = fileText.replace(result, "<script type=\"text/javascript\" src=\"http://" + hostAddress + ":8080/js/" + str + ".js\"></script>");
					FileUtil.writeToFile(newFileText, CommonConstant.LBYC_INDEX_PATH);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void renameBuildFile(){
		System.out.println("renameBuildFile里的buildName为：" + buildName);
		if(!buildName.equals("build.js")){
			File file = new File(CommonConstant.BUILD_JS_PATH);
			file.renameTo(new File(CommonConstant.BUILD_JS_PATH + new Random().nextInt(10000)));
			File randomBuildFile = new File(CommonConstant.LBYC_TEST + "js\\" + buildName);
			randomBuildFile.renameTo(new File(CommonConstant.BUILD_JS_PATH));
		}
	}
	
	
	
	/**
	 * 自动化index.html
	 */
	public static void rewritePackageJson(String pattern, String str) {
		String fileText = FileUtil.readTxt(CommonConstant.PACKAGE_JSON_PATH);
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(fileText);
		while(matcher.find()){
			String result = matcher.group(0);
			String subResult = result.substring(result.indexOf(str), result.length());
		}
	}
	
	
}