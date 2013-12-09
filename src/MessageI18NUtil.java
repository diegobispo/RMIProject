package src;

import java.util.Locale;
import java.util.ResourceBundle;

public final class MessageI18NUtil {
	public static ResourceBundle text = null;

	private MessageI18NUtil() {
	}

	public static void init() {
		Locale locale = Locale.getDefault();
		text = ResourceBundle.getBundle(
				"resource.messages", locale);
	}

	public static String getMessage(String key) {
		if (text == null) {
			init();
		}
		return text.getString(key);
	}
}