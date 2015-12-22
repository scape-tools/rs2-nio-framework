package main.astraeus.core.game.pulse;

public final class PulseObserver implements PulseListener {

	@Override
	public void execute(Pulse pulse) {
		pulse.execute();
	}

	@Override
	public void stop(Pulse pulse) {
		pulse.stop();
	}
}
