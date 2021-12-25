package my_utility

import scala.io.Source

import java.io._
import scala.io.Codec
import java.nio.charset.CodingErrorAction

object MyUtility {

   def write_file(data: String, path: String, mode: Boolean): Unit = {
      val os = new FileOutputStream(path, mode)
      val pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"))
      pw.write(data)
      pw.write("\n\n-?-?-\n\n")
      pw.close
   }

   def read_file(path: String): List[String] = {
     val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
     val source = Source.fromFile(path)(decoder)
      var list = List[String]()
      for (line <- source.getLines())
         list = line.toString :: list
      return list
   }

   def clean_file(path: String): Unit = {
      var data: Any = ""
      MyUtility.write_file(s"$data", path, false)
   }
}
