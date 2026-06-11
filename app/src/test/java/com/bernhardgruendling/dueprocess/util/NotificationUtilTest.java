package com.bernhardgruendling.dueprocess.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowPendingIntent;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33)
public class NotificationUtilTest {

    @Test
    public void testShowNotification_usesFlagImmutable() {
        Context mockContext = mock(Context.class);
        NotificationManager mockNotificationManager = mock(NotificationManager.class);
        Resources mockResources = mock(Resources.class);
        ApplicationInfo mockAppInfo = new ApplicationInfo();
        mockAppInfo.targetSdkVersion = 33;

        when(mockContext.getSystemService(Context.NOTIFICATION_SERVICE)).thenReturn(mockNotificationManager);
        when(mockContext.getResources()).thenReturn(mockResources);
        when(mockContext.getString(anyInt())).thenReturn("App Name");
        when(mockContext.getPackageName()).thenReturn("com.bernhardgruendling.dueprocess");
        when(mockContext.getApplicationInfo()).thenReturn(mockAppInfo);
        when(mockContext.getApplicationContext()).thenReturn(mockContext);

        Intent intent = new Intent();

        NotificationUtil.showNotification(mockContext, "Test Title", "Test Message", 1, intent);

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(mockNotificationManager).notify(eq(1), notificationCaptor.capture());

        Notification notification = notificationCaptor.getValue();
        assertNotNull(notification);

        PendingIntent contentIntent = notification.contentIntent;
        assertNotNull(contentIntent);

        ShadowPendingIntent shadowPendingIntent = Shadows.shadowOf(contentIntent);
        assertTrue((shadowPendingIntent.getFlags() & PendingIntent.FLAG_IMMUTABLE) != 0);
    }
}
