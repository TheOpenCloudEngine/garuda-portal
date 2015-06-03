/*
 * Project : SpringTransaction
 * Package : com.fuw.common.filter
 * Class   : LoggingFilter.java
 * Author  : hork lim
 * Date    : Nov 22, 2012
 * To-do   : TODO
 *
 */
package org.uengine.garuda.client.filter;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class LoggingFilter extends Filter {

	String packageToMatch;
	boolean acceptOnMatch = true;
	
	public String getPackageToMatch() {
		return packageToMatch;
	}

	public void setPackageToMatch(String packageToMatch) {
		this.packageToMatch = packageToMatch;
	}

	public boolean isAcceptOnMatch() {
		return acceptOnMatch;
	}

	public void setAcceptOnMatch(boolean acceptOnMatch) {
		this.acceptOnMatch = acceptOnMatch;
	}

	@Override
	public int decide(LoggingEvent event) {
		String name = event.getLoggerName();

		if (name == null || packageToMatch == null)
			return Filter.DENY;

		String[] matchArray = packageToMatch.split(",");
		
		for(String match : matchArray){
			if(name.indexOf(match)>0 && acceptOnMatch)
				return Filter.ACCEPT;
		}
		
		return Filter.DENY;
	}
}