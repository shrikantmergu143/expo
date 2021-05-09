
public protocol Module {
  var name: String { get }
}

extension Module {
  public var name: String {
    return String(describing: type(of: self))
  }
}

open class BaseModule: Module {
  @ExportMethod
  var add = { (a: Int, b: Double) -> Double in
    return Double(a) + b
  }

  @ExportMethod
  var subtract = { (a: Int, b: Double) -> Double in
    return Double(a) - b
  }

  @ExportMethod
  var uno = { (a: Int) -> Int in
    return a
  }
}
