import scala.collection.immutable.SortedSet
import scala.collection.immutable.TreeSet
object TestApp extends Application {
  myfun()
  def myfun() {
    println("Hello")
    val list = List(3, 2, 1)
    val a = TreeSet(list: _*)
    val contained = a.contains(2)

  }
}