protocol AnyExportedMethod {
  var numberOrArguments: Int { get }

  func call(args: Any) -> Any
}

@propertyWrapper
struct ExportMethod<Args, ReturnType>: AnyExportedMethod {
  var wrappedValue: (Args) -> ReturnType
  var numberOrArguments: Int = String(describing: Args.self).components(separatedBy: ", ").count

  func call(args: Any) -> Any {
    if let array = args as? [Any] {
      if let tuple: Args = Conversions.arrayToTuple(array) {
        return wrappedValue(tuple)
      }
    }
    return wrappedValue(args as! Args)
  }
}
