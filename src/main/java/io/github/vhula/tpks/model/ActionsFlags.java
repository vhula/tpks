package io.github.vhula.tpks.model;
/**
 * Клас, що містить мітки для можливих дій користувача.
 * @author Vadym Hula
 *
 */
public final class ActionsFlags {
	
	public static final boolean ENABLED = true;
	
	public static final boolean DISABLED = false;
	
	public static boolean NEW_FILE = ENABLED;
	
	public static boolean OPEN_FILE = ENABLED;
	
	public static boolean SAVE_FILE = DISABLED;
	
	public static boolean SAVE_AS_FILE = DISABLED;
	
	public static boolean CLOSE_FILE = DISABLED;
	
	public static boolean ADD_BEGIN = DISABLED;
	
	public static boolean ADD_OPERATOR = DISABLED;
	
	public static boolean ADD_CONDITION = DISABLED;
	
	public static boolean ADD_UP_ARROW = DISABLED;
	
	public static boolean ADD_DOWN_ARROW = DISABLED;
	
	public static boolean ADD_END = DISABLED;
	
	public static boolean MOVE_LEFT = DISABLED;
	
	public static boolean MOVE_RIGHT = DISABLED;

	


}
