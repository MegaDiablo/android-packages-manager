package com.adbhelper.adb;

import java.io.IOException;

public class GetInfoApkFormatLog extends DefaultFormatLog {

    private static final String PACKAGE_NAME = "package: name='([^']*)'";
    private static final String VERSION_CODE = "versionCode='([^']*)'";
    private static final String VERSION_NAME = "versionName='([^']*)'";
    private static final String APPLICATIN_LABEL = "application-label:'([^']*)'";
    private static final String LAUCHABLE_ACTIVITY = "launchable-activity: name='([^']*)'";
    private String packageName=null;
    @SuppressWarnings("unused")
	private String versionCode=null;
   	@SuppressWarnings("unused")
	private String versionName=null;
   	private String label=null;
   	private String launchableActivity=null;

    public GetInfoApkFormatLog() {

    }

    private static final String STR_ERROR = "Error:";

    
    
    @Override
    public String changeLine(String line) {
    	String showLine=null;
	if (line.startsWith(STR_ERROR))
	{
	    LogAdb.error(line);
	    return null;
	}
	if (line.matches(".*"+PACKAGE_NAME+".*")) {
		String packageLine=AdbUtils.getResultPattern(line, PACKAGE_NAME);
		packageName=AdbUtils.getResultsPattern(packageLine,PACKAGE_NAME)[0];
		showLine=line;
	}
	
	if (line.matches(".*"+LAUCHABLE_ACTIVITY+".*")) {
		String tmpLine=AdbUtils.getResultPattern(line, LAUCHABLE_ACTIVITY);
		launchableActivity=AdbUtils.getResultsPattern(tmpLine,LAUCHABLE_ACTIVITY)[0];
		showLine=tmpLine;
	}
	if (line.matches(".*"+VERSION_CODE+".*")) {
		String packageLine=AdbUtils.getResultPattern(line, VERSION_CODE);
		versionCode=AdbUtils.getResultsPattern(packageLine,VERSION_CODE)[0];
		showLine=line;
	}
	if (line.matches(".*"+VERSION_NAME+".*")) {
		String packageLine=AdbUtils.getResultPattern(line, VERSION_NAME);
		versionName=AdbUtils.getResultsPattern(packageLine,VERSION_NAME)[0];
		showLine=line;
	}
	
	if (line.matches(".*"+APPLICATIN_LABEL+".*")) {
		String packageLine=AdbUtils.getResultPattern(line, APPLICATIN_LABEL);
		label=AdbUtils.getResultsPattern(packageLine,APPLICATIN_LABEL)[0];
		showLine=line;
	}
	
	if (showLine==null){
	LogAdb.debug(line);}
	return showLine;
    }

    public AdbPackage getPackage(AdbModule adb){
    	AdbPackage adbPackage=new AdbPackage(adb, packageName, null, null);
    	try {
			adbPackage.setDefaultActivity(launchableActivity);
			adbPackage.setLabel(label);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return adbPackage;
    }
    
}
