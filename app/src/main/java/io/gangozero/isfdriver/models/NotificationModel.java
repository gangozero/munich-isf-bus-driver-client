package io.gangozero.isfdriver.models;

import java.util.Random;

/**
 * Created by eleven on 10/09/2016.
 */
public class NotificationModel {

	public final TYPE type;
	public final String message;

	public NotificationModel(TYPE type, String message) {

		this.type = type;
		this.message = message;
	}

	public enum TYPE {
		MESSAGE,
		ALARM_LEFT_ON,
		ALARM_LEFT_OFF,
		ALARM_TOP_ON,
		ALARM_TOP_OFF,
		ALARM_BOTTOM_ON,
		ALARM_BOTTOM_OFF,
		ALARM_RIGHT_ON,
		ALARM_RIGHT_OFF;

		private static final Random RND = new Random();

		public static TYPE random() {
			TYPE[] values = values();
			return values[RND.nextInt(values.length)];
		}
	}
}
