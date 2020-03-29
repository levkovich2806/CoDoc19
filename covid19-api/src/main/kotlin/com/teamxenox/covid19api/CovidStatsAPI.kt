package com.teamxenox.covid19api

import com.teamxenox.covid19api.apis.GlobalApi
import com.teamxenox.covid19api.apis.IndianApi
import com.teamxenox.covid19api.core.JHUCSVParser
import com.teamxenox.covid19api.models.Statistics
import com.teamxenox.covid19api.models.jhu.DeathData
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CovidStatsAPI {

    private const val DEATH_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv"

    private val indianApi by lazy {
        return@lazy Retrofit.Builder()
                .baseUrl("https://api.covid19india.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IndianApi::class.java)
    }

    private val globalApi by lazy {
        return@lazy Retrofit.Builder()
                .baseUrl("https://corona.lmao.ninja/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GlobalApi::class.java)
    }

    /**
     * To get stats by country
     */
    fun getStats(_country: String): Statistics? {

        val country = _country.toLowerCase()

        if (country == "ind" || country == "india") {

            val indianApiResponse = indianApi.getData().execute().body()!!
            val india = indianApiResponse.statewise.find { it.state == "Total" }!!

            return Statistics(
                    india.confirmed.toInt(),
                    india.deaths.toInt(),
                    india.recovered.toInt(),
                    india.active.toInt(),
                    india.delta.confirmed,
                    india.delta.deaths
            )

        } else {

            val resp = globalApi.getCountryData(country).execute()
            if (resp.code() == 200) {
                val countryData = resp.body()!!
                return Statistics(
                        countryData.cases,
                        countryData.deaths,
                        countryData.recovered,
                        countryData.active,
                        countryData.todayCases,
                        countryData.todayDeaths
                )
            }
        }

        return null
    }

    /**
     * To get global stats
     */
    fun getGlobalStats(): Statistics? {
        val globalAll = globalApi.getAll().execute().body()!!
        val resp = globalApi.getAllCountries().execute()
        if (resp.code() == 200) {
            val countriesAll = resp.body()!!
            var todayCases = 0
            var todayDeaths = 0

            for (country in countriesAll) {
                todayCases += country.todayCases
                todayDeaths += country.todayDeaths
            }

            return Statistics(
                    globalAll.cases,
                    globalAll.deaths,
                    globalAll.recovered,
                    globalAll.active,
                    todayCases,
                    todayDeaths
            )
        }

        return null
    }

    fun getDeathData(countryName: String): DeathData? {
        val client = OkHttpClient.Builder()
                .build()

        val request = Request.Builder()
                .url(DEATH_DATA_URL)
                .build()

        val response = client.newCall(request).execute().body()
        if (response != null) {
            val csvData = response.string()
            return DeathData(
                    countryName,
                    JHUCSVParser.parseData(countryName, csvData)
            )
        }
        return null
    }


}