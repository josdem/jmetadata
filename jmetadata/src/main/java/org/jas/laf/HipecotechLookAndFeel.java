package org.jas.laf;

import java.text.ParseException;

import javax.swing.plaf.synth.SynthLookAndFeel;

public class HipecotechLookAndFeel extends SynthLookAndFeel {
	private static final long serialVersionUID = -4532847419431106496L;

	public HipecotechLookAndFeel() throws ParseException {
		load(HipecotechLookAndFeel.class.getResourceAsStream("/style/Hipecotech.xml"), HipecotechLookAndFeel.class);
	}

	@Override
	public String getName() {
		return "Hipecotech";
	}

	@Override
	public String getID() {
		return "Hipecotech";
	}

	public String getDescription() {
		return "Hipecotech Look and Feel";
	}

}
