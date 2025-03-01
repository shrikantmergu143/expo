// Copyright 2015-present 650 Industries. All rights reserved.

package abi41_0_0.host.exp.exponent;

import android.content.Context;
import android.os.Looper;

import abi41_0_0.com.facebook.react.ReactPackage;
import abi41_0_0.com.facebook.react.bridge.NativeModule;
import abi41_0_0.com.facebook.react.bridge.ReactApplicationContext;
import abi41_0_0.com.facebook.react.uimanager.ViewManager;

import org.json.JSONException;
import org.json.JSONObject;
import abi41_0_0.org.unimodules.adapters.react.ReactModuleRegistryProvider;
import abi41_0_0.org.unimodules.core.interfaces.Package;
import org.unimodules.core.interfaces.SingletonModule;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import expo.modules.updates.manifest.raw.RawManifest;
import host.exp.exponent.Constants;
import host.exp.exponent.ExponentManifest;
import host.exp.exponent.analytics.EXL;
import abi41_0_0.host.exp.exponent.modules.api.appearance.rncappearance.RNCAppearanceModule;
import abi41_0_0.host.exp.exponent.modules.api.reanimated.ReanimatedModule;
import abi41_0_0.host.exp.exponent.modules.internal.DevMenuModule;
import host.exp.exponent.kernel.ExperienceId;
/* WHEN_VERSIONING_REMOVE_FROM_HERE
import host.exp.exponent.kernel.ExponentKernelModuleProvider;
WHEN_VERSIONING_REMOVE_TO_HERE */
import host.exp.exponent.utils.ScopedContext;
import abi41_0_0.host.exp.exponent.modules.api.KeyboardModule;
import abi41_0_0.host.exp.exponent.modules.api.PedometerModule;
import abi41_0_0.host.exp.exponent.modules.api.ScreenOrientationModule;
import abi41_0_0.host.exp.exponent.modules.api.ShakeModule;
import abi41_0_0.host.exp.exponent.modules.api.URLHandlerModule;
import abi41_0_0.host.exp.exponent.modules.api.appearance.ExpoAppearanceModule;
import abi41_0_0.host.exp.exponent.modules.api.appearance.ExpoAppearancePackage;
import abi41_0_0.host.exp.exponent.modules.api.cognito.RNAWSCognitoModule;
import abi41_0_0.host.exp.exponent.modules.api.components.datetimepicker.RNDateTimePickerPackage;
import abi41_0_0.host.exp.exponent.modules.api.components.maskedview.RNCMaskedViewPackage;
import abi41_0_0.host.exp.exponent.modules.api.components.picker.RNCPickerPackage;
import abi41_0_0.host.exp.exponent.modules.api.components.slider.ReactSliderPackage;
import abi41_0_0.host.exp.exponent.modules.api.components.gesturehandler.react.RNGestureHandlerModule;
import abi41_0_0.host.exp.exponent.modules.api.components.gesturehandler.react.RNGestureHandlerPackage;
import abi41_0_0.host.exp.exponent.modules.api.components.lottie.LottiePackage;
import abi41_0_0.host.exp.exponent.modules.api.components.maps.MapsPackage;
import abi41_0_0.host.exp.exponent.modules.api.components.svg.SvgPackage;
import abi41_0_0.host.exp.exponent.modules.api.components.viewpager.RNCViewPagerPackage;
import abi41_0_0.host.exp.exponent.modules.api.components.webview.RNCWebViewModule;
import abi41_0_0.host.exp.exponent.modules.api.components.webview.RNCWebViewPackage;
import abi41_0_0.host.exp.exponent.modules.api.components.sharedelement.RNSharedElementModule;
import abi41_0_0.host.exp.exponent.modules.api.components.sharedelement.RNSharedElementPackage;
import abi41_0_0.host.exp.exponent.modules.api.netinfo.NetInfoModule;
import abi41_0_0.host.exp.exponent.modules.api.notifications.NotificationsModule;
import abi41_0_0.host.exp.exponent.modules.api.safeareacontext.SafeAreaContextPackage;
import abi41_0_0.host.exp.exponent.modules.api.screens.RNScreensPackage;
import abi41_0_0.host.exp.exponent.modules.api.viewshot.RNViewShotModule;
import abi41_0_0.host.exp.exponent.modules.test.ExponentTestNativeModule;
import abi41_0_0.host.exp.exponent.modules.universal.ExpoModuleRegistryAdapter;
import abi41_0_0.host.exp.exponent.modules.universal.ScopedModuleRegistryAdapter;
// This is an Expo module but not a unimodule
import abi41_0_0.expo.modules.random.RandomModule;

import static host.exp.exponent.kernel.KernelConstants.INTENT_URI_KEY;
import static host.exp.exponent.kernel.KernelConstants.IS_HEADLESS_KEY;
import static host.exp.exponent.kernel.KernelConstants.LINKING_URI_KEY;

public class ExponentPackage implements ReactPackage {
  private static final String TAG = ExponentPackage.class.getSimpleName();
  private static List<SingletonModule> sSingletonModules;
  private static Set<Class> sSingletonModulesClasses;

  private final boolean mIsKernel;
  private final Map<String, Object> mExperienceProperties;
  private final RawManifest mManifest;

  private final ScopedModuleRegistryAdapter mModuleRegistryAdapter;

  private ExponentPackage(boolean isKernel, Map<String, Object> experienceProperties, RawManifest manifest, List<Package> expoPackages, List<SingletonModule> singletonModules) {
    mIsKernel = isKernel;
    mExperienceProperties = experienceProperties;
    mManifest = manifest;
    mModuleRegistryAdapter = createDefaultModuleRegistryAdapterForPackages(expoPackages, singletonModules);
  }

  public ExponentPackage(Map<String, Object> experienceProperties, RawManifest manifest, List<Package> expoPackages, ExponentPackageDelegate delegate, List<SingletonModule> singletonModules) {
    mIsKernel = false;
    mExperienceProperties = experienceProperties;
    mManifest = manifest;

    List<Package> packages = expoPackages;
    if (packages == null) {
      packages = ExperiencePackagePicker.packages(manifest);
    }
    // Delegate may not be null only when the app is detached
    mModuleRegistryAdapter = createModuleRegistryAdapter(delegate, singletonModules, packages);
  }

  private ScopedModuleRegistryAdapter createModuleRegistryAdapter(ExponentPackageDelegate delegate, List<SingletonModule> singletonModules, List<Package> packages) {
    ScopedModuleRegistryAdapter registryAdapter = null;
    if (delegate != null) {
      registryAdapter = delegate.getScopedModuleRegistryAdapterForPackages(packages, singletonModules);
    }
    if (registryAdapter == null) {
      registryAdapter = createDefaultModuleRegistryAdapterForPackages(packages, singletonModules);
    }
    return registryAdapter;
  }


  public static ExponentPackage kernelExponentPackage(Context context, RawManifest manifest, List<Package> expoPackages, @Nullable String initialURL) {
    Map<String, Object> kernelExperienceProperties = new HashMap<>();
    List<SingletonModule> singletonModules = ExponentPackage.getOrCreateSingletonModules(context, manifest, expoPackages);
    kernelExperienceProperties.put(LINKING_URI_KEY, "exp://");
    kernelExperienceProperties.put(IS_HEADLESS_KEY, false);
    if (initialURL != null) {
      kernelExperienceProperties.put(INTENT_URI_KEY, initialURL);
    }
    return new ExponentPackage(true, kernelExperienceProperties, manifest, expoPackages, singletonModules);
  }

  public static List<SingletonModule> getOrCreateSingletonModules(Context context, RawManifest manifest, List<Package> providedExpoPackages) {
    if (Looper.getMainLooper() != Looper.myLooper()) {
      throw new RuntimeException("Singleton modules must be created on the main thread.");
    }
    if (sSingletonModules == null) {
      sSingletonModules = new ArrayList<>();
    }
    if (sSingletonModulesClasses == null) {
      sSingletonModulesClasses = new HashSet<>();
    }
    List<Package> expoPackages = providedExpoPackages;
    if (expoPackages == null) {
      expoPackages = ExperiencePackagePicker.packages(manifest);
    }

    for (Package expoPackage : expoPackages) {
      // For now we just accumulate more and more singleton modules,
      // but in fact we should only return singleton modules from the requested
      // unimodules. This solution also unnecessarily creates singleton modules
      // which are going to be deallocated in a tick, but there's no better solution
      // without a bigger-than-minimal refactor. In SDK32 the only singleton module
      // is TaskService which is safe to initialize more than once.
      List<? extends SingletonModule> packageSingletonModules = expoPackage.createSingletonModules(context);
      for (SingletonModule singletonModule : packageSingletonModules) {
        if (!sSingletonModulesClasses.contains(singletonModule.getClass())) {
          sSingletonModules.add(singletonModule);
          sSingletonModulesClasses.add(singletonModule.getClass());
        }
      }
    }
    return sSingletonModules;
  }

  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    boolean isVerified = false;
    if (mManifest != null) {
      isVerified = mManifest.isVerified();
    }

    List<NativeModule> nativeModules = new ArrayList<>(Arrays.<NativeModule>asList(
        new URLHandlerModule(reactContext),
        new ShakeModule(reactContext),
        new KeyboardModule(reactContext)
    ));

    if (mIsKernel) {
      /* WHEN_VERSIONING_REMOVE_FROM_HERE
      nativeModules.add((NativeModule) ExponentKernelModuleProvider.newInstance(reactContext));
      WHEN_VERSIONING_REMOVE_TO_HERE */
    }
    if (!mIsKernel && !Constants.isStandaloneApp()) {
      // We need DevMenuModule only in non-home and non-standalone apps.
      nativeModules.add(new DevMenuModule(reactContext, mExperienceProperties, mManifest));
    }

    if (isVerified) {
      try {
        ExperienceId experienceId = ExperienceId.create(mManifest.getID());
        ScopedContext scopedContext = new ScopedContext(reactContext, experienceId.getUrlEncoded());

        nativeModules.add(new NotificationsModule(reactContext, mManifest, mExperienceProperties));
        nativeModules.add(new RNViewShotModule(reactContext, scopedContext));
        nativeModules.add(new RandomModule(reactContext));
        nativeModules.add(new ExponentTestNativeModule(reactContext));
        nativeModules.add(new PedometerModule(reactContext));
        nativeModules.add(new ScreenOrientationModule(reactContext));
        nativeModules.add(new RNGestureHandlerModule(reactContext));
        nativeModules.add(new RNAWSCognitoModule(reactContext));
        nativeModules.add(new ReanimatedModule(reactContext));
        nativeModules.add(new RNCWebViewModule(reactContext));
        nativeModules.add(new NetInfoModule(reactContext));
        nativeModules.add(new RNSharedElementModule(reactContext));

        // @tsapeta: Using ExpoAppearanceModule in home app causes some issues with the dev menu,
        // when home's setting is set to automatic and the system theme is different
        // than this supported by the experience in which we opened the dev menu.
        if (mIsKernel) {
          nativeModules.add(new RNCAppearanceModule(reactContext));
        } else {
          nativeModules.add(new ExpoAppearanceModule(reactContext));
        }

        SvgPackage svgPackage = new SvgPackage();
        nativeModules.addAll(svgPackage.createNativeModules(reactContext));

        MapsPackage mapsPackage = new MapsPackage();
        nativeModules.addAll(mapsPackage.createNativeModules(reactContext));

        RNDateTimePickerPackage dateTimePickerPackage = new RNDateTimePickerPackage();
        nativeModules.addAll(dateTimePickerPackage.createNativeModules(reactContext));

        // Call to create native modules has to be at the bottom --
        // -- ExpoModuleRegistryAdapter uses the list of native modules
        // to create Bindings for internal modules.
        nativeModules.addAll(mModuleRegistryAdapter.createNativeModules(scopedContext, experienceId, mExperienceProperties, mManifest, nativeModules));
      } catch (JSONException | UnsupportedEncodingException e) {
        EXL.e(TAG, e.toString());
      }
    }

    return nativeModules;
  }

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    List<ViewManager> viewManagers = new ArrayList<>();

    // Add view manager from 3rd party library packages.
    addViewManagersFromPackages(reactContext, viewManagers, Arrays.<ReactPackage>asList(
        new SvgPackage(),
        new MapsPackage(),
        new LottiePackage(),
        new RNGestureHandlerPackage(),
        new RNScreensPackage(),
        new RNCWebViewPackage(),
        new SafeAreaContextPackage(),
        new RNSharedElementPackage(),
        new RNDateTimePickerPackage(),
        new RNCMaskedViewPackage(),
        new RNCPickerPackage(),
        new ReactSliderPackage(),
        new RNCViewPagerPackage(),
        new ExpoAppearancePackage()
    ));

    viewManagers.addAll(mModuleRegistryAdapter.createViewManagers(reactContext));

    return viewManagers;
  }

  private void addViewManagersFromPackages(ReactApplicationContext reactContext,
                                           List<ViewManager> viewManagers,
                                           List<ReactPackage> packages) {
    for (ReactPackage pack : packages) {
      viewManagers.addAll(pack.createViewManagers(reactContext));
    }
  }

  private ExpoModuleRegistryAdapter createDefaultModuleRegistryAdapterForPackages(List<Package> packages, List<SingletonModule> singletonModules) {
    return new ExpoModuleRegistryAdapter(new ReactModuleRegistryProvider(packages, singletonModules));
  }
}
