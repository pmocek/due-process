package com.bernhardgruendling.dueprocess.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.MotionEvents;

import org.hamcrest.Matcher;

import java.util.List;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Custom Espresso ViewAction to simulate pattern touch events for testing.
 * This enables UI testing of the custom pattern unlock feature.
 */
public class PatternTouchAction implements ViewAction {

    private final List<Integer> corners;

    /**
     * Creates a new action to simulate a pattern unlock
     *
     * @param corners List of corner indexes to touch in sequence
     */
    public PatternTouchAction(List<Integer> corners) {
        this.corners = corners;
    }

    @Override
    public Matcher<View> getConstraints() {
        return isDisplayed();
    }

    @Override
    public String getDescription() {
        return "Performs a pattern unlock using specified corners: " + corners;
    }

    @Override
    public void perform(UiController uiController, View view) {
        // Get view coordinates
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        
        float viewWidth = view.getWidth();
        float viewHeight = view.getHeight();
        
        // Begin by pressing down at the first corner
        if (corners.isEmpty()) {
            return;
        }
        
        // Map corner indices to coordinates
        int firstCorner = corners.get(0);
        float startX = getXCoordForCorner(firstCorner, viewLocation[0], viewWidth);
        float startY = getYCoordForCorner(firstCorner, viewLocation[1], viewHeight);
        
        // Initialize the gesture
        long downTime = System.currentTimeMillis();
        MotionEvents.DownResultHolder downEvent = MotionEvents.sendDown(
                uiController, 
                new float[] {startX, startY}, 
                new float[] {0f, 0f});
        
        // Wait to simulate a real user
        uiController.loopMainThreadForAtLeast(100);
        
        try {
            // Perform the pattern trace
            for (int i = 1; i < corners.size(); i++) {
                int corner = corners.get(i);
                float x = getXCoordForCorner(corner, viewLocation[0], viewWidth);
                float y = getYCoordForCorner(corner, viewLocation[1], viewHeight);
                
                // Move to the next corner
                MotionEvents.sendMovement(
                        uiController, 
                        downEvent.down, 
                        downTime,
                        new float[] {x, y});
                
                // Wait between moves to simulate real user behavior
                uiController.loopMainThreadForAtLeast(100);
            }
            
            // Complete the gesture by lifting the finger
            MotionEvents.sendUp(
                    uiController, 
                    downEvent.down, 
                    downTime);
                    
        } finally {
            // Ensure cleanup even if an exception occurs
            MotionEvents.sendCancel(uiController, downEvent.down);
        }
    }
    
    /**
     * Maps a corner index (0-3) to an X coordinate
     */
    private float getXCoordForCorner(int corner, float viewX, float viewWidth) {
        switch (corner) {
            case 0: // Top-left
            case 2: // Bottom-left
                return viewX + (viewWidth * 0.25f);
            case 1: // Top-right
            case 3: // Bottom-right
                return viewX + (viewWidth * 0.75f);
            default:
                return viewX + (viewWidth * 0.5f);
        }
    }
    
    /**
     * Maps a corner index (0-3) to a Y coordinate
     */
    private float getYCoordForCorner(int corner, float viewY, float viewHeight) {
        switch (corner) {
            case 0: // Top-left
            case 1: // Top-right
                return viewY + (viewHeight * 0.25f);
            case 2: // Bottom-left
            case 3: // Bottom-right
                return viewY + (viewHeight * 0.75f);
            default:
                return viewY + (viewHeight * 0.5f);
        }
    }
}