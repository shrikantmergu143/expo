
internal class Conversions {
  static func arrayToAnyTuple(_ array: [Any]) -> Any? {
    switch (array.count) {
    case 0:
      return nil
    case 1:
      return (array[0])
    case 2:
      return (array[0], array[1])
    case 3:
      return (array[0], array[1], array[2])
    case 4:
      return (array[0], array[1], array[2], array[3])
    case 5:
      return (array[0], array[1], array[2], array[3], array[4])
    case 6:
      return (array[0], array[1], array[2], array[3], array[4], array[5])
    case 7:
      return (array[0], array[1], array[2], array[3], array[4], array[5], array[6])
    case 8:
      return (array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7])
    case 9:
      return (array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7], array[8])
    case 10:
      return (array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7], array[8], array[9])
    default:
      // TODO(@tsapeta): throw an error that we don't support more arguments
      return nil
    }
  }

  static func arrayToTuple<TupleType>(_ array: [Any]) -> TupleType? {
    return arrayToAnyTuple(array) as? TupleType
  }
}
