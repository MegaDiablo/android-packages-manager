package ru.ucoz.megadiablo.android.apm;

/**
 * @author MegaDiablo
 * */
public interface IEvent extends Runnable {

	int getType();
	
	String getName();

	String getDescription();

}
