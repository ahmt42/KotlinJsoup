package mtip.yemeklistesi

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.URL

object Main {
    private const val url = "https://hastane.erbakan.edu.tr/insan-kaynaklari-birimi/yemek-listesi"

    @JvmStatic
    fun main(argo: Array<String>) {

        //getDataByReadText()
        getDataByJsoup()
    }

    /**
     * url。readText()
     */
    private fun getDataByReadText() {
        val html = URL(url).readText()
        val document = Jsoup.parse("utf-8", html)
        println(document.getElementsByTag("title").first())
        parseDocument(document)
    }

    /**
     * Jsoup
     */
    private fun getDataByJsoup() {
        val document = Jsoup.connect(url).get()
        parseDocument(document)
    }

    private fun parseDocument(document: Document) {
        val postItems = document.select(".table-yemeklistesi:first-of-type tbody tr")
        val infoes = arrayListOf<Info>()

        postItems.forEach {
            var date = it.select("td").get(0).text()
            val breakfast = it.select("td").get(1).toString()
            val lunch = it.select("td").get(2).toString()
            val dinner = it.select("td").get(3).toString()

            val info = Info()
            info.date = date
            info.breakfast = breakfast.replace("[</td>]".toRegex(), "").trim().split("br ")
            info.lunch = lunch.replace("[</td>]".toRegex(), "").trim().split("br ")
            info.dinner = dinner.replace("[</td>]".toRegex(), "").trim().split("br ")

            infoes.add(info)
        }

        printList(infoes)
    }

    private fun parseDocumentt(document: Document) {
        val infoes = arrayListOf<Info>()

        document.select(".table-yemeklistesi:first-of-type tbody tr")
            .map { col -> col}
            //.parallelStream()
            .map { extractData(it) }    // <5>
            .filter { it != null }
            .forEach { infoes.add(it) }

        printList(infoes)
    }


    private fun extractData(element: Element): Info {
        //println(element.select("td").get(0))

        var date = element.select("td").get(0).text()
        val breakfast = element.select("td").get(1).toString()
        val lunch = element.select("td").get(2).toString()
        val dinner = element.select("td").get(3).toString()

        val info = Info()
        info.date = date
        info.breakfast = breakfast.replace("[</td>]".toRegex(), "").trim().split("br ")
        info.lunch = lunch.replace("[</td>]".toRegex(), "").trim().split("br ")
        info.dinner = dinner.replace("[</td>]".toRegex(), "").trim().split("br ")

        return info
    }

    private fun printList(arr: List<Info>) {
        arr.forEach {
            println("TARİH : " + it.date)
            println("KAHVALTI : " + it.breakfast.get(0) + " / " + it.breakfast.get(1) + " / " + it.breakfast.get(2))
            println("ÖĞLE : " + it.lunch.get(0) + " / " + it.lunch.get(1) + " / " + it.lunch.get(2))
            println("AKŞAM : " + it.dinner.get(0) + " / " + it.dinner.get(1) + " / " + it.dinner.get(2))
            println("-----------------------------------------------------------------------------------------------------------")
        }
    }


}