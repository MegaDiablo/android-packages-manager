package ru.ucoz.megadiablo.android.apm.ui.log;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author Vladimir Baraznovsky
 *
 */
public class Message {

	private final TypeMessage mTypeMessage;
	private final Object mMessage;

	protected Message(final TypeMessage pTypeMessage, final Object pMessage) {
		super();
		mTypeMessage = pTypeMessage;
		mMessage = pMessage;
	}

	public TypeMessage getTypeMessage() {
		return mTypeMessage;
	}

	public Object getMessage() {
		return mMessage;
	}

	public static Message createMessageTime(final TypeMessage pType,
			final long pTime) {
		Date date = new Date(pTime);

		String message = DateFormat.getInstance().format(date);
		return createMessage(pType, message);
	}

	public static Message createMessage(final TypeMessage pType,
			final Object pMessage) {
		return new Message(pType, pMessage);
	}

	public static Message createMessageType(final TypeMessage pType) {
		return new Message(pType, pType);
	}

	@Override
	public String toString() {
		return mMessage.toString();
	}
}
