package ln

import foodlist.MeramTip
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.IOException
import java.net.URL

object JobParser {
    private const val url = "https://www.linkedin.com/jobs/view/3334684322/"

    @JvmStatic
    fun main(argo: Array<String>) {
        //val document = Jsoup.connect(JobParser.url).get()

        /*
        try {
            var response: Connection.Response = Jsoup
                .connect(JobParser.url)
                .method(Connection.Method.GET)
                .execute()
            val responseDocument: Document = response.parse()

            val title = responseDocument.select("h1").html()


        } catch (e: IOException) {
            e.printStackTrace()
        }
        */

        val job: Job = getIcerik(url)
        println(job.jobLink)
        println(job.title)
        println(job.company)
        println(job.companyLink)
        println(job.city)
        println(job.description)
    }

    fun getIcerik(url: String): Job {
        val item = Job()
        try {
            val document = Jsoup.connect(url).get()
            val jobLink = document.select("link")
                .attr("href")
            val title = document.select("h1")
                .text()
            val company = document.select("div.topcard__flavor-row span.topcard__flavor")
                .select("a")
                .text()
            val companyLink = document.select("div.topcard__flavor-row span.topcard__flavor")
                .select("a")
                .attr("href")
            val city = document.select("div.topcard__flavor-row span.topcard__flavor.topcard__flavor--bullet")
                .text()
            val description = document.select("div.description__text")
                .text()
            item.jobLink = jobLink
            item.title = title
            item.company = company
            item.companyLink = companyLink
            item.city = city
            item.description = description
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return item
    }
}