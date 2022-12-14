package foodlist

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.URL

object MeramTip {
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
        parseDocumentC2(document)
    }

    /**
     * Jsoup
     */
    private fun getDataByJsoup() {
        val document = Jsoup.connect(url).get()
        parseDocumentC2(document)
    }

    private fun parseDocumentC1(document: Document) {
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
        printTomorrow(infoes)
    }


    private fun parseDocumentC2(document: Document) {
        val infoes = arrayListOf<Info>()

        document.select(".table-yemeklistesi:first-of-type tbody tr")
            .map { col -> col}
            //.parallelStream()
            .map { extractData(it) }    // <5>
            .filter { it != null }
            .forEach { infoes.add(it) }

        printList(infoes)
        printTomorrow(infoes)
    }


    private fun extractData(element: Element): Info {
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

    private fun printList(infoes: ArrayList<Info>) {
        infoes.forEach {
            println("TARİH    : " + it.date)
            println("KAHVALTI : " + it.breakfast.get(0) + "  |  " + it.breakfast.get(1) + "  |  " + it.breakfast.get(2))
            println("ÖĞLE     : " + it.lunch.get(0) + "  |  " + it.lunch.get(1) + "  |  " + it.lunch.get(2))
            println("AKŞAM    : " + it.dinner.get(0) + "  |  " + it.dinner.get(1) + "  |  " + it.dinner.get(2))
            println("-----------------------------------------------------------------------------------------------------------")
        }
    }

    private fun printTomorrow(infoes: ArrayList<Info>) {
        for ((index, value) in infoes.withIndex()) {
            if (value.date == "Bugün") {
                println("The date of tomorrow is ${infoes[index+1].date} :")
                println("KAHVALTI : ${infoes[index+1].breakfast.get(0)}  |  ${infoes[index+1].breakfast.get(1)}  |  ${infoes[index+1].breakfast.get(2)}")
                println("ÖĞLE     : ${infoes[index+1].lunch.get(0)}  |  ${infoes[index+1].lunch.get(1)}  |  ${infoes[index+1].lunch.get(2)}")
                println("AKŞAM    : ${infoes[index+1].dinner.get(0)}  |  ${infoes[index+1].dinner.get(1)}  |  ${infoes[index+1].dinner.get(2)}")
            }
        }
    }


}