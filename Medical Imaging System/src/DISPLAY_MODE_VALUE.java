
public enum DISPLAY_MODE_VALUE {
	ONE_IMAGE, FOUR_IMAGE;
	
	public static int getValue(DISPLAY_MODE_VALUE value) {
		if(value == ONE_IMAGE)
			return 1;
		else
			return 4;
	}
}
