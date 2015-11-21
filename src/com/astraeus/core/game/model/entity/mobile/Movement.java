package com.astraeus.core.game.model.entity.mobile;

import java.util.Deque;
import java.util.LinkedList;

import com.astraeus.core.game.GameConstants;
import com.astraeus.core.game.model.entity.Entity;
import com.astraeus.core.game.model.entity.mobile.player.MovementPoint;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.utility.Utilities;

/**
 * @author Dylan Vicchiarelli
 *
 * @author Graham Edgecombe
 * 
 *         Handles the movement of an entity on the global palate.
 */
public final class Movement {

	/**
	 * The entity performing the movement.
	 */
	private final Entity entity;

	/**
	 * The points of focus.
	 */
	private final Deque<MovementPoint> focusPoints = new LinkedList<MovementPoint>();

	/**
	 * If the entity is currently running.
	 */
	private boolean isRunning = false;

	/**
	 * If the entity running queue is enabled.
	 */
	private boolean isRunningQueueEnabled = false;

	/**
	 * The default class constructor.
	 * 
	 * @param entity
	 *            The acting entity.
	 */
	public Movement(Entity entity) {
		this.entity = entity;
	}

	/**
	 * Checks if the Queue containing the focus points is empty.
	 * 
	 * @return The result of the check.
	 */
	public final boolean isFocusPointsEmpty() {
		return getFocusPoints().isEmpty();
	}

	/**
	 * Completes the movement path of the entity.
	 */
	public final void finishMovement() {
		getFocusPoints().removeFirst();
	}

	/**
	 * Returns the acting entity.
	 * 
	 * @return The returned entity.
	 */
	public final Entity getEntity() {
		return entity;
	}

	/**
	 * Returns the points of focus.
	 * 
	 * @return The returned focus points.
	 */
	public final Deque<MovementPoint> getFocusPoints() {
		return focusPoints;
	}

	/**
	 * Checks if the entity is running.
	 * 
	 * @return The result of the check,
	 */
	public final boolean isRunning() {
		return isRunning;
	}

	/**
	 * Modifies if the entity is running.
	 * 
	 * @param isRunning
	 *            The new modification.
	 */
	public final void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * Checks if the entity running Queue is enabled.
	 * 
	 * @return The result of the check.
	 */
	public final boolean isRunningQueueEnabled() {
		return isRunningQueueEnabled;
	}

	/**
	 * Modifies if the entity running Queue is enabled.
	 * 
	 * @param isRunningQueueEnabled
	 *            The new modification.
	 */
	public final void setRunningQueueEnabled(boolean isRunningQueueEnabled) {
		this.isRunningQueueEnabled = isRunningQueueEnabled;
	}

	/**
	 * Resets the movement variables of the entity.
	 */
	public final void resetMovement() {
		setRunningQueueEnabled(false);
		getFocusPoints().clear();
		getFocusPoints().add(new MovementPoint(getEntity().getPosition().getX(), getEntity().getPosition().getY(), -1));
	}

	/**
	 * Handles the cycling movement requests of the entity. This method receives
	 * prior processing to the main updating procedure to ensure that all
	 * movement is registered in time to be updated in synchronization with the
	 * main procedure.
	 */
	public final void handleEntityMovement() {
		MovementPoint walkingPoint = null, runningPoint = null;

		walkingPoint = getNextPoint();

		if (isRunning()) {
			runningPoint = getNextPoint();
		}

		((MobileEntity) getEntity()).setWalkingDirection(walkingPoint == null ? -1 : walkingPoint.getDirection());

		((MobileEntity) getEntity()).setRunningDirection(runningPoint == null ? -1 : runningPoint.getDirection());

		int deltaX = getEntity().getPosition().getX()
				- ((MobileEntity) getEntity()).getLastPosition().getRegionalX() * 8;

		int deltaY = getEntity().getPosition().getY()
				- ((MobileEntity) getEntity()).getLastPosition().getRegionalY() * 8;

		if (entity instanceof Player) {
			if (deltaX < 16 || deltaX >= 88 || deltaY < 16 || deltaY > 88) {
				((Player) entity).getPacketSender().sendRegionalUpdate();
			}
		}
	}

	/**
	 * Returns the next movement point in the designated path.
	 * 
	 * @return The next point that was returned.
	 */
	public final MovementPoint getNextPoint() {

		MovementPoint availableFocusPoint = getFocusPoints().poll();

		if (availableFocusPoint == null || availableFocusPoint.getDirection() == -1) {

			return null;
		} else {

			((MobileEntity) getEntity()).getPosition().setPositionAdditional(
					GameConstants.DIRECTION_DELTA_X[availableFocusPoint.getDirection()],
					GameConstants.DIRECTION_DELTA_Y[availableFocusPoint.getDirection()],
					getEntity().getPosition().getZ());

			return availableFocusPoint;
		}
	}

	/**
	 * The prior procedure before the step taken is internally Queued.
	 * 
	 * @param x
	 *            The X coordinate of the step.
	 * 
	 * @param y
	 *            The Y coordinate of the step.
	 */
	public final void addExternalStep(int x, int y) {

		if (isFocusPointsEmpty()) {

			resetMovement();
		}

		MovementPoint lastPosition = getFocusPoints().peekLast();

		int deltaX = x - lastPosition.getX();

		int deltaY = y - lastPosition.getY();

		int maximumDelta = Math.max(Math.abs(deltaX), Math.abs(deltaY));

		for (int i = 0; i < maximumDelta; i++) {
			if (deltaX < 0) {

				deltaX++;
			} else if (deltaX > 0) {

				deltaX--;
			}
			if (deltaY < 0) {

				deltaY++;
			} else if (deltaY > 0) {

				deltaY--;
			}

			addInternalStep(x - deltaX, y - deltaY);
		}
	}

	/**
	 * Adds a step into the internal memory of the movement Queue.
	 * 
	 * @param x
	 *            The X coordinate of the step.
	 * 
	 * @param y
	 *            The Y coordinate of the step.
	 */
	private final void addInternalStep(int x, int y) {

		if (getFocusPoints().size() >= 50) {

			return;
		}

		MovementPoint lastPosition = getFocusPoints().peekLast();

		int direction = Utilities.parseDirection(x - lastPosition.getX(), y - lastPosition.getY());

		if (direction > -1) {

			getFocusPoints().add(new MovementPoint(x, y, direction));
		}
	}
}
