
@objc
public class SwiftInteropBridge: NSObject {
  let registry = ModuleRegistry()

  @objc
  public func has(moduleWithName moduleName: String) -> Bool {
    return registry.has(moduleWithName: moduleName)
  }

  @objc
  public func callMethod(_ methodName: String, onModule moduleName: String, withArgs args: Any) -> Any? {
    let moduleHolder = registry.get(moduleHolderForName: moduleName)
    return moduleHolder?.call(method: methodName, args: args)
  }

  @objc
  public func constantsToExport() -> [String: [[String: Any]]] {
    var constants = [String: [[String: Any]]]()

    for holder in registry {
      var index = -1

      constants[holder.module.name] = holder.methods.map({ (methodName, method) in
        index += 1
        return [
          "name": methodName,
          "argumentsCount": method.numberOrArguments,
          "key": index,
        ]
      })
    }

    return constants
  }
}
