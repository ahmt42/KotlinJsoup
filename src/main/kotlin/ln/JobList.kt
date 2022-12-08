package ln

import org.jsoup.Jsoup
import java.io.IOException

object JobList {
    private const val url = "https://tr.linkedin.com/jobs/search?keywords=Android&location=T%C3%BCrkiye&locationId=&geoId=102105699&f_TPR=r86400&position=1&pageNum=0"

    @JvmStatic
    fun main(argo: Array<String>) {
        getEventsList(url)
    }

    fun getEventsList(url: String): MutableList<Job> {
        val listData = mutableListOf<Job>()
        try {
            val doc = Jsoup.connect(url).get()
            val events = doc.select("section.two-pane-serp-page__results-list ul.jobs-search__results-list li")
            val eventsSize = events.size
            for (i in 0 until eventsSize) {
                val title = events.select("div.base-search-card__info")
                    .select("h3")
                    .eq(i)
                    .text()
                val link = events.select("div.base-card")
                    .select("a.base-card__full-link")
                    .eq(i)
                    .attr("href")
                val company = events.select("div.base-search-card__info")
                    .select("h4 a")
                    .eq(i)
                    .text()
                val companyLink = events.select("div.base-search-card__info")
                    .select("h4 a")
                    .eq(i)
                    .attr("href")
                val companyImage = events.select("div.search-entity-media")
                    .select("img")
                    .eq(i)
                    .attr("data-delayed-url")

                println(i)
                println(title)
                println(link)
                println(company)
                println(companyLink)
                println(companyImage)
                println()
            }
            println(eventsSize)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return listData
    }
}