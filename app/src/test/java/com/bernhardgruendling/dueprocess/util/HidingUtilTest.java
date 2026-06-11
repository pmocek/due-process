package com.bernhardgruendling.dueprocess.util;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.bernhardgruendling.dueprocess.AppSettings;
import com.bernhardgruendling.dueprocess.model.AppInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33)
public class HidingUtilTest {

    @Mock
    private Context mockContext;

    @Mock
    private AppSettings mockAppSettings;

    @Mock
    private DevicePolicyManager mockDevicePolicyManager;

    @Mock
    private PackageManager mockPackageManager;

    @Mock
    private ComponentName mockAdminComponentName;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockContext.getApplicationContext()).thenReturn(mockContext);
        when(mockContext.getPackageManager()).thenReturn(mockPackageManager);
    }

    @Test
    public void testHideApp_callsSetAppHiddenWithTrue() {
        String packageName = "com.example.app";
        try (MockedStatic<Util> mockedUtil = mockStatic(Util.class)) {
            mockedUtil.when(() -> Util.getDevicePolicyManager(mockContext))
                    .thenReturn(mockDevicePolicyManager);
            mockedUtil.when(() -> Util.getAdminComponentName(mockContext))
                    .thenReturn(mockAdminComponentName);

            HidingUtil.hideApp(mockContext, packageName);

            verify(mockDevicePolicyManager, times(1))
                    .setApplicationHidden(mockAdminComponentName, packageName, true);
        }
    }

    @Test
    public void testShowApp_callsSetAppHiddenWithFalse() {
        String packageName = "com.example.app";
        try (MockedStatic<Util> mockedUtil = mockStatic(Util.class)) {
            mockedUtil.when(() -> Util.getDevicePolicyManager(mockContext))
                    .thenReturn(mockDevicePolicyManager);
            mockedUtil.when(() -> Util.getAdminComponentName(mockContext))
                    .thenReturn(mockAdminComponentName);

            HidingUtil.showApp(mockContext, packageName);

            verify(mockDevicePolicyManager, times(1))
                    .setApplicationHidden(mockAdminComponentName, packageName, false);
        }
    }

    @Test
    public void testRestoreStoragePermissions_usesCorrectPermissionsForAndroid13() throws Exception {
        String packageName = "com.example.app";
        Set<String> appsWithGrantedPermissions = new HashSet<>();
        appsWithGrantedPermissions.add(packageName);

        List<ApplicationInfo> appList = new ArrayList<>();
        ApplicationInfo appInfo = new ApplicationInfo();
        appInfo.packageName = packageName;
        appList.add(appInfo);

        try (MockedStatic<Util> mockedUtil = mockStatic(Util.class)) {
            mockedUtil.when(() -> Util.getDevicePolicyManager(mockContext))
                    .thenReturn(mockDevicePolicyManager);
            mockedUtil.when(() -> Util.getAdminComponentName(mockContext))
                    .thenReturn(mockAdminComponentName);
            mockedUtil.when(() -> Util.getAllInstalledApplicationsSorted(mockContext, false))
                    .thenReturn(appList);

            when(mockAppSettings.getAppsWithGrantedStoragePermissions())
                    .thenReturn(appsWithGrantedPermissions);

            // Execute private method
            Method method = HidingUtil.class.getDeclaredMethod("restoreStoragePermissions", Context.class, DevicePolicyManager.class, ComponentName.class, AppSettings.class);
            method.setAccessible(true);
            method.invoke(null, mockContext, mockDevicePolicyManager, mockAdminComponentName, mockAppSettings);

            verify(mockDevicePolicyManager, times(1))
                    .setPermissionGrantState(eq(mockAdminComponentName),
                                            eq(packageName),
                                            eq("android.permission.READ_MEDIA_IMAGES"),
                                            eq(DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED));
            verify(mockDevicePolicyManager, times(1))
                    .setPermissionGrantState(eq(mockAdminComponentName),
                                            eq(packageName),
                                            eq("android.permission.READ_MEDIA_VIDEO"),
                                            eq(DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED));
            verify(mockDevicePolicyManager, times(1))
                    .setPermissionGrantState(eq(mockAdminComponentName),
                                            eq(packageName),
                                            eq("android.permission.READ_MEDIA_AUDIO"),
                                            eq(DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED));
        }
    }
}
