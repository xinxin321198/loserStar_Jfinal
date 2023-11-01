package com.loserstar.ldap;

import com.jfinal.kit.PropKit;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

/**
 *
 * @author Once
 * @version 1.0.0
 */
public class LDAPSearcher {
	
	private static String HOST;
	private static int PORT;
	private static String BINDING;
	private static String PASSWORD;
	
	private static String USER_FILTER;
	private static String USER_BASE;
	private static String USER_BPM_BASE;
	
	static {
		loadConfiguration();
	}
//	static void loadConfiguration() {
//		InputStream in = null;
//		try  {
//			in = LDAPSearcher.class.getClassLoader().getResourceAsStream("ldap.properties");
//			Properties props = new Properties();
//			props.load(in);
//			
//			HOST = props.getProperty("ldap.server");
//			PORT = Integer.parseInt(props.getProperty("ldap.port"));
//			BINDING =  props.getProperty("ldap.bind");
//			PASSWORD = props.getProperty("ldap.password");
//			USER_FILTER = props.getProperty("ldap.search.user.filter");
//			USER_BASE = props.getProperty("ldap.search.user.base");
//			USER_BPM_BASE = props.getProperty("ldap.search.bpm.user.base");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try { in.close(); } catch (Exception e){}
//		}
//	}
	
	static void loadConfiguration() {
//		InputStream in = null;
		try  {
//			in = LDAPSearcher.class.getClassLoader().getResourceAsStream("ldap.properties");
//			Properties props = new Properties();
//			props.load(in);
			
			HOST = PropKit.get("ldap.server");
			PORT = PropKit.getInt("ldap.port");
			BINDING =  PropKit.get("ldap.bind");
			PASSWORD = PropKit.get("ldap.password");
			USER_FILTER = PropKit.get("ldap.search.user.filter");
			USER_BASE = PropKit.get("ldap.search.user.base");
			USER_BPM_BASE = PropKit.get("ldap.search.bpm.user.base");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try { in.close(); } catch (Exception e){}
		}
	}
	
	//
	@SuppressWarnings("deprecation")
	public static LDAPUser searchUser(String uid)  {
		LDAPUser user = null;
		LDAPConnection con = null;
		
		try {
			String searchFilter = USER_FILTER.replaceAll("\\{userid\\}", uid);
			con = new LDAPConnection();   
			con.connect(HOST, PORT); 
			con.bind(BINDING,PASSWORD);
			LDAPSearchResults searchResults = con.search(USER_BASE, LDAPConnection.SCOPE_SUB,
                   searchFilter, // search filter
                   null, // "1.1" returns entry 
                   false); // no attributes 
		
			while(searchResults.hasMore()) {
				LDAPEntry entity = searchResults.next();
				user = new LDAPUser();
				user.setUserId(uid);
				user.setPassword(entity.getAttribute("userpassword")
						.getStringValue());
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {con.disconnect();};
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}
		
		return user;
		
	}
	
	@SuppressWarnings("deprecation")
	public static LDAPUser searchBPMUser(String uid)  {
		LDAPUser user = null;
		LDAPConnection con = null;
		
		try {
			String searchFilter = USER_FILTER.replaceAll("\\{userid\\}", uid);
			con = new LDAPConnection();   
			con.connect(HOST, PORT); 
			con.bind(BINDING,PASSWORD);
			LDAPSearchResults searchResults = con.search(USER_BPM_BASE, LDAPConnection.SCOPE_SUB,
                   searchFilter, // search filter
                   null, // "1.1" returns entry 
                   false); // no attributes 
		
			while(searchResults.hasMore()) {
				LDAPEntry entity = searchResults.next();
				user = new LDAPUser();
				user.setUserId(uid);
				user.setPassword(entity.getAttribute("userpassword")
						.getStringValue());
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {con.disconnect();};
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}
		
		return user;
		
	}
}
