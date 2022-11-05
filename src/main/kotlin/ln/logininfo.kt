package ln

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.IOException


object logininfo {

    @JvmStatic
    fun main(argo: Array<String>) {
        try {
            val url = "https://meramtiphbys.mergentech.com.tr/mergenTechSbsHastaPortaliIstemci/giris?"
            var response: Connection.Response = Jsoup
                .connect(url)
                .method(Connection.Method.GET)
                .execute()
            val responseDocument: Document = response.parse()
            val loginCsrfParam: Element? = responseDocument
                .select("input[name=loginCsrfParam]")
                .first()
            if (loginCsrfParam != null) {
                response = Jsoup.connect("https://www.linkedin.com/uas/login-submit")
                    .cookies(response.cookies())
                    .data("loginCsrfParam", loginCsrfParam.attr("value"))
                    .data("session_key", "email")
                    .data("session_password", "password")
                    .method(Connection.Method.POST)
                    .followRedirects(true)
                    .execute()
            }
            val document: Document = response.parse()

            //            System.out.println(document)
            System.out.println(
                "Welcome "
                        + document.select(".act-set-name-split-link").html()
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}