package models

import my_utility.MyUtility

object MyWeb {

   object MyIndex {

      val data_path = sys.env("MY_DATA_PATH")

      val title = "MyWeb2"

      def read() = {
         MyUtility.read_file(s"$data_path/mummy.txt")
            .map(_.replace("-?-?-", "<br>---<br>"))
            .mkString
      }
   }
}
