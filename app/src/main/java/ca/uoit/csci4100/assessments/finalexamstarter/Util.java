package ca.uoit.csci4100.assessments.finalexamstarter;

// android imports
import android.util.Log;

public final class Util {
	private static final String TAG = "MobileExam";
	/** Write a format string to the logs **/
	public static void log( String fmt, Object... params){
		String content = String.format( fmt, params);
		Log.v( TAG, content);}
}
