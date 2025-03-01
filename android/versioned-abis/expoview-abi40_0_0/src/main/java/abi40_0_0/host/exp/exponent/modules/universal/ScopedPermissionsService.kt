package abi40_0_0.host.exp.exponent.modules.universal

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import abi40_0_0.expo.modules.permissions.PermissionsService
import host.exp.exponent.di.NativeModuleDepsProvider
import host.exp.exponent.kernel.ExperienceId
import host.exp.exponent.kernel.services.ExpoKernelServiceRegistry
import abi40_0_0.org.unimodules.core.ModuleRegistry
import abi40_0_0.org.unimodules.interfaces.permissions.PermissionsResponseListener
import javax.inject.Inject

class ScopedPermissionsService(context: Context, val experienceId: ExperienceId) : PermissionsService(context) {

  // This variable cannot be lateinit, cause the Location module gets permissions before this module is initialized.
  @Inject
  var mExpoKernelServiceRegistry: ExpoKernelServiceRegistry? = null

  override fun onCreate(moduleRegistry: ModuleRegistry) {
    super.onCreate(moduleRegistry)
    NativeModuleDepsProvider.getInstance().inject(ScopedPermissionsService::class.java, this)
  }

  // We override this to inject scoped permissions even if the device doesn't support the runtime permissions.
  override fun askForManifestPermissions(permissions: Array<out String>, listener: PermissionsResponseListener) {
    delegateRequestToActivity(permissions, listener)
  }

  // We override this to scoped permissions in the headless mode.
  override fun getManifestPermissionFromContext(permission: String): Int {
    val globalPermissions = ContextCompat.checkSelfPermission(context, permission)
    return mExpoKernelServiceRegistry?.permissionsKernelService?.getPermissions(globalPermissions, context.packageManager, permission, experienceId)
      ?: PackageManager.PERMISSION_DENIED
  }
}
