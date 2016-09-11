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
		MESSAGE(0),
		ALARM_LEFT_ON(1),
		ALARM_LEFT_OFF(2),
		ALARM_TOP_ON(3),
		ALARM_TOP_OFF(4),
		ALARM_BOTTOM_ON(5),
		ALARM_BOTTOM_OFF(6),
		ALARM_RIGHT_ON(6),
		ALARM_RIGHT_OFF(8);

		private static final Random RND = new Random();
		private int id;

		TYPE(int id) {
			this.id = id;
		}

		public static TYPE byId(int id) {
			for (TYPE type : values()) {
				if (type.id == id) return type;
			}
			return null;
		}

		public static TYPE random() {
			TYPE[] values = values();
			return values[RND.nextInt(values.length)];
		}
	}
}
