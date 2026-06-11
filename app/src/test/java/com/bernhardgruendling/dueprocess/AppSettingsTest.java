package com.bernhardgruendling.dueprocess;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33, manifest = Config.NONE)
public class AppSettingsTest {

    private Context context;
    private AppSettings appSettings;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        appSettings = new AppSettings(context);
    }

    @Test
    public void testGetUnlockCode_returnsExpectedList() {
        List<Integer> expectedCode = Arrays.asList(1, 2, 3, 4);
        appSettings.setUnlockCode(expectedCode);
        
        List<Integer> actualCode = appSettings.getUnlockCode();
        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void testGetUnlockCode_defaultCode() {
        List<Integer> code = appSettings.getUnlockCode();
        assertEquals(1, code.size());
        assertEquals(Integer.valueOf(-1), code.get(0));
    }

    @Test
    public void testSetUnlockCode_storesProperly() {
        List<Integer> codeToStore = Arrays.asList(9, 8, 7, 6);
        appSettings.setUnlockCode(codeToStore);
        assertEquals(codeToStore, appSettings.getUnlockCode());
    }

    @Test
    public void testUnlockAttempts() {
        assertEquals(0, appSettings.getUnlockAttempts());
        appSettings.setUnlockAttempts(5);
        assertEquals(5, appSettings.getUnlockAttempts());
    }

    @Test
    public void testNextAllowedUnlockAttemptTime() {
        assertEquals(0L, appSettings.getNextAllowedUnlockAttemptTime());
        appSettings.setNextAllowedUnlockAttemptTime(1000L);
        assertEquals(1000L, appSettings.getNextAllowedUnlockAttemptTime());
    }

    @Test
    public void testFirstIntroRun() {
        assertTrue(appSettings.isFirstIntroRun());
        appSettings.setFirstIntroRunDone();
        assertFalse(appSettings.isFirstIntroRun());
    }

    @Test
    public void testFirstRun() {
        assertTrue(appSettings.getIsFirstRun());
        appSettings.setFirstRunDone();
        assertFalse(appSettings.getIsFirstRun());
    }

    @Test
    public void testPostProvisioningDone() {
        assertFalse(appSettings.getIsPostProvisioningDone());
        appSettings.setPostProvisioningDone();
        assertTrue(appSettings.getIsPostProvisioningDone());
    }

    @Test
    public void testAppsWithGrantedStoragePermissions() {
        assertTrue(appSettings.getAppsWithGrantedStoragePermissions().isEmpty());
        Set<String> pkgs = new HashSet<>(Arrays.asList("com.example.app1", "com.example.app2"));
        appSettings.setAppsWithGrantedStoragePermissions(pkgs);
        assertEquals(pkgs, appSettings.getAppsWithGrantedStoragePermissions());
    }
}