package com.adbhelper.adb.log;


public class PackagesFormatLog extends DefaultFormatLog {

    private static final String START_PACKAGE = "package:";
    private int countPackages = 0;
    private String filter;

    /**
     * @param logAdb
     * @param fileIgnoreFilter
          */
    public PackagesFormatLog(LogAdb logAdb,String fileIgnoreFilter) {
    super(logAdb);
	filter = fileIgnoreFilter;
    }

    private static final String STR_ERROR = "Error:";



    @Override
    public String changeLine(String line) {
	if (line.startsWith(STR_ERROR))
	{
	    error(line);
	    return null;
	}


	if (line.startsWith(START_PACKAGE)) {
	    String[] tmp = line.replaceAll(".*:", "").split("=");
	    if ((filter == null) || (!tmp[0].matches(filter))) {

		countPackages++;
		//return String.format("%4s - %s ", countPackages,tmp[1]);
		return null;
	    } else
		return null;
	}
	return super.changeLine(line);
    }

}
